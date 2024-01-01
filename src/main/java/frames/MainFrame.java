package frames;

import Util.Dialog;
import Util.Log;
import Util.TableCalculator;
import matchplan.AbstractMatchplan;
import turnier.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Main screen for the programm

public class MainFrame extends JFrame {
	private final Match[] curMatches;
	private final JLabel[] curGames;
	private final JLabel[] nextGames;
	private ArrayList<Match> matches;
	private ArrayList<Match> knockout;
	private final ArrayList<Match> groupphase;
	private final ArrayList<Team> teams;
	private final Log log;
	private final TurnierConfiguration configuration;
	private final LinkedList<Player> sListe = new LinkedList<>();

	void updateGames(AbstractMatchplan matchplan) {
		matchplan.updateKnockoutMatches();
		for (int i = 0; i < configuration.getNumberOfFields(); i++) {
			Matchpair currentGames = getCurrentGames(i);
			updateGameFrames(currentGames, i);
		}
		if (gamesFinished()) {
			if (knockout == null && configuration.isKnockout()) {
				perpareKnockoutStage(matchplan);
				updateGames(matchplan);
			} else {
				JOptionPane.showMessageDialog(null, "Turnier done.");
			}
		}
	}

	private void perpareKnockoutStage(AbstractMatchplan matchplan) {

		showMatchplan();
		int res = JOptionPane.showConfirmDialog(null, "Sind alle Ergebnisse korrekt?");
		if (res != JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(null, "Korrigiere das Ergebnis ueber die Korrekturfunktion. Danach erneut Ergebnis eintragen klicken!");
			return;
		}

		calculateKnockoutStage(matchplan);
	}

	private void calculateKnockoutStage(AbstractMatchplan matchplan) {
		List<Team> groupA = teams.subList(0, (teams.size() + 1) / 2);
		List<Team> groupB = teams.subList((teams.size() + 1) / 2, teams.size());
		TableCalculator.calcTable(matches, groupA, true);
		TableCalculator.calcTable(matches, groupB, true);
		for (int i = 0; i < groupB.size(); i++) {
			groupA.get(i).setPosition(i * 2 + 1);
			groupB.get(i).setPosition(i * 2 + 1);
		}
		if (groupA.size() != groupB.size()) {
			groupA.get(groupA.size() - 1).setPosition(teams.size() - 2);
		}
		matches = matchplan.loadKnockoutStage();
		knockout = matches;
	}

	private boolean gamesFinished() {
		for (Match m : matches) {
			if (!m.played()) {
				return false;
			}
		}
		return true;
	}

	record Matchpair(Match cur, Match next) {

	}

	Matchpair getCurrentGames(int field) {
		Match cur = null;
		Match next = null;
		for (Match match : matches) {
			if (!match.played() && match.getField() == field + 1) {
				if (cur == null) {
					cur = match;
				} else {
					next = match;
					break;
				}
			}
		}
		return new Matchpair(cur, next);
	}

	void updateGameFrames(Matchpair currentGames, int field) {
		if (currentGames.cur != null) {
			curGames[field].setText("Aktuelle Partie " + Turnier.fieldname[field] + " (" + currentGames.cur.getType() + "): " + currentGames.cur.showFrame());
			curMatches[field] = currentGames.cur;
		} else {
			curMatches[field] = null;
			curGames[field].setText("Aktuelle Partie " + Turnier.fieldname[field] + ": Pause...");
		}
		if (currentGames.next != null) {
			nextGames[field].setText("Naechste Partie " + Turnier.fieldname[field] + " (" + currentGames.next.getType() + "): " + currentGames.next.showFrame());
		} else {
			nextGames[field].setText("Naechsete Partie " + Turnier.fieldname[field] + ": Pause...");
		}
	}


	//Init Main menue
	public MainFrame(ArrayList<Match> matches, ArrayList<Team> teams, TurnierConfiguration configuration, Log log, AbstractMatchplan matchplan) {
		this.matches = matches;
		groupphase = matches;
		this.teams = teams;
		this.log = log;
		this.configuration = configuration;
		//create frame

		setSize(1450, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(configuration.getTurnierName());
		setResizable(true);
		setLayout(null);

		Font normalFont = new Font("Arial", Font.BOLD, 20);
		Font haedFont = new Font("Arial", Font.BOLD, 50);

		curGames = new JLabel[configuration.getNumberOfFields()];
		curMatches = new Match[configuration.getNumberOfFields()];
		nextGames = new JLabel[configuration.getNumberOfFields()];

		curGames[0] = new JLabel("Aktuelle Partie Feld 1: Ungenutzt");
		curGames[0].setBounds(50, 100, 1200, 30);
		curGames[0].setFont(normalFont);
		add(curGames[0]);

		nextGames[0] = new JLabel("Naechste Partie Feld 1: Ungenutzt");
		nextGames[0].setBounds(50, 150, 1200, 30);
		nextGames[0].setFont(normalFont);
		add(nextGames[0]);

		if (configuration.getNumberOfFields() == 2) {
			curGames[1] = new JLabel("Aktuelle Partie Feld 2: Ungenutzt");
			curGames[1].setBounds(50, 250, 1200, 30);
			curGames[1].setFont(normalFont);
			add(curGames[1]);

			nextGames[1] = new JLabel("Naechste Partie Feld 2: Ungenutzt");
			nextGames[1].setBounds(50, 300, 1200, 30);
			nextGames[1].setFont(normalFont);
			add(nextGames[1]);
		}
		JLabel head = new JLabel(configuration.getTurnierName());
		head.setBounds(150, 30, 900, 50);
		head.setFont(haedFont);
		add(head);

		JLabel gametimeLabel = new JLabel("Spielzeit: " + configuration.getGameTime() + "min");
		gametimeLabel.setBounds(1230, 50, 200, 50);
		gametimeLabel.setFont(normalFont);
		add(gametimeLabel);

		JLabel pausetimeLabel = new JLabel("Pause: " + configuration.getPauseTime() + "min");
		pausetimeLabel.setBounds(1230, 75, 200, 50);
		pausetimeLabel.setFont(normalFont);
		add(pausetimeLabel);

		JButton insertTeam = new JButton("Spieler einfuegen");
		insertTeam.setBounds(50, 400, 200, 50);
		insertTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertPlayer(0);
			}
		});
		insertTeam.setEnabled(false);
		add(insertTeam);

		JButton setGoal = new JButton("Tor eintragen");
		setGoal.setBounds(300, 400, 200, 50);
		setGoal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertGoal();
			}
		});
		setGoal.setEnabled(false);
		add(setGoal);

		JButton endGame = new JButton("Endergebnis");
		endGame.setBounds(550, 400, 200, 50);
		endGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setEntry(matchplan);
			}
		});
		add(endGame);

		JButton matchplanLabel = new JButton("Spielplan");
		matchplanLabel.setBounds(50, 500, 200, 50);
		matchplanLabel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMatchplan();
			}
		});
		add(matchplanLabel);

		JButton fix = new JButton("Korrektur");
		fix.setBounds(800, 500, 200, 50);
		fix.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				correct(matchplan);
			}
		});
		add(fix);

		JButton showTable = new JButton("Tabellen anzeigen");
		showTable.setBounds(800, 400, 200, 50);
		showTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTable();
			}
		});
		add(showTable);

		JButton showTeam = new JButton("Mannschaft anzeigen");
		showTeam.setBounds(300, 500, 200, 50);
		showTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertPlayer(1);
			}
		});
		showTeam.setEnabled(false);
		add(showTeam);

		JButton showGoals = new JButton("Torschuetzen anzeigen");
		showGoals.setBounds(550, 500, 200, 50);
		showGoals.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showGoalgetters();
			}
		});
		showGoals.setEnabled(false);
		add(showGoals);


		if (gamesFinished()) {
			if (configuration.isKnockout()) {
				calculateKnockoutStage(matchplan);
				log.loadMatches(matches, configuration);
				updateGames(matchplan);
			} else {
				JOptionPane.showMessageDialog(null, "Turnier done.");
			}
		}

		updateGames(matchplan);
		setVisible(true);
	}

	private void correct(AbstractMatchplan matchplan) {
		new CorrectionFrame(matches, this, log, matchplan);
	}

	//Actions depending on button pressed
	private void showMatchplan() {
		ArrayList<Match> list = new ArrayList<>(groupphase);
		if (knockout != null) {
			list.addAll(matches);
		}
		new MatchplanFrame(list, configuration);
	}

	private void setEntry(AbstractMatchplan matchplan) {
		updateGames(matchplan);
		int field = 0;
		if (configuration.getNumberOfFields() > 1) {
			field = Dialog.optionDialog(Turnier.fieldname, "Welches Feld ist fertig?");
		}
		doEntry(field);
		updateGames(matchplan);
	}

	private void insertPlayer(int flag) {
		String[] names = new String[8];
		for (int i = 0; i < 8; i++) {
			names[i] = teams.get(i).getName();
		}
		new SelectTeamFrame(names, sListe, flag);
	}

	private void showTable() {
		int d = 0;
		if (configuration.isKnockout()) {
			String[] ops = {"Gruppe A", "Gruppe B", "Alle"};
			d = JOptionPane.showOptionDialog(null, "Waehle Gruppe:", null, 0, JOptionPane.INFORMATION_MESSAGE, null, ops, null);
			if (d == 0) {
				new TableFrame(groupphase, teams.subList(0, (teams.size() + 1) / 2), configuration.getTurnierName() + "_Gruppe_A", true);
			} else if (d == 1) {
				new TableFrame(groupphase, teams.subList((teams.size() + 1) / 2, teams.size()), configuration.getTurnierName() + "_Gruppe_B", true);
			} else if (d == 2) {
				new TableFrame(matches, teams, configuration.getTurnierName() + "_Endstand", false);
			}
		} else {
			new TableFrame(matches, teams, configuration.getTurnierName(), true);
		}

	}

	private void insertGoal() {
		new GoalFrame(sListe, "fff", "fff");
	}

	private void doEntry(int field) {
		Match match = curMatches[field];
		int[] result = Dialog.resultDialog(match.getT1(), match.getT2());
		if (result != null && valid(result, match)) {
			match.addResult(result[0], result[1]);
			log.logMatch(match);
			JOptionPane.showMessageDialog(null, "Ergebnis eingetragen");
		} else {
			JOptionPane.showMessageDialog(null, "Fehler! Ungueltiges Ergebnis");
		}
	}

	private boolean valid(int[] result, Match match) {
		return !(result[0] == result[1] && match.isKO());
	}


	private void showGoalgetters() {
		new GoalgetterFrame(sListe);
	}
}
