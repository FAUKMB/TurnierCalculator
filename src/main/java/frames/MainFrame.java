package frames;

import Util.Dialog;
import Util.Log;
import Util.Matchplan;
import Util.TableCalculator;
import turnier.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

//Main screen for the programm

public class MainFrame extends JFrame {
	private final JLabel curGame1;
	private Match curMatch1;
	private Match curMatch2;
	private final JLabel nextGame1;
	private JLabel curGame2;
	private JLabel nextGame2;
	private ArrayList<Match> matches;
	private ArrayList<Match> knockout;
	private final ArrayList<Match> groupphase;
	private final ArrayList<Team> teams;
	private final Log log;

	private final TurnierConfiguration configuration;

	private final LinkedList<Player> sListe = new LinkedList<>();

	void updateGames() {
		doUpdateGame(1);
		if (configuration.getNumberOfFields() == 2) {
			doUpdateGame(2);
		}
	}

	void doUpdateGame(int field) {
		Match cur = null;
		Match next = null;
		for (Match match : matches) {
			if (!match.played() && match.getField() == field) {
				if (cur == null) {
					cur = match;
				} else {
					next = match;
					break;
				}
			}
		}
		if (cur != null) {

			if (field == 1) {
				curGame1.setText("Aktuelle Partie " + Turnier.fieldname1 + " (" + cur.getType() + "): " + cur.showFrame());
				curMatch1 = cur;
			} else {
				curMatch2 = cur;
				curGame2.setText("Aktuelle Partie " + Turnier.fieldname2 + " (" + cur.getType() + "): " + cur.showFrame());
			}
		} else {
			if (field == 1) {
				curMatch1 = null;
				curGame1.setText("Aktuelle Partie " + Turnier.fieldname1 + ": Pause...");
			} else {
				curMatch2 = null;
				curGame2.setText("Aktuelle Partie " + Turnier.fieldname2 + "2: Pause...");
			}
		}
		if (next != null) {
			if (field == 1) {
				nextGame1.setText("Naechste Partie " + Turnier.fieldname1 + " (" + next.getType() + "): " + next.showFrame());
			} else {
				nextGame2.setText("Naechste Partie " + Turnier.fieldname2 + " (" + next.getType() + "): " + next.showFrame());
			}
		} else {
			if (field == 1) {
				nextGame1.setText("Naechsete Partie " + Turnier.fieldname1 + ": Pause...");
			} else {
				nextGame2.setText("Naechsete Partie " + Turnier.fieldname2 + ": Pause...");
			}
		}
	}


	//Init Main menue
	public MainFrame(ArrayList<Match> matches, ArrayList<Team> teams, TurnierConfiguration configuration, Log log) {
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

		curGame1 = new JLabel("Aktuelle Partie Feld 1: Ungenutzt");
		curGame1.setBounds(50, 100, 1200, 30);
		curGame1.setFont(normalFont);
		add(curGame1);

		nextGame1 = new JLabel("Naechste Partie Feld 1: Ungenutzt");
		nextGame1.setBounds(50, 150, 1200, 30);
		nextGame1.setFont(normalFont);
		add(nextGame1);

		if (configuration.getNumberOfFields() == 2) {
			curGame2 = new JLabel("Aktuelle Partie Feld 2: Ungenutzt");
			curGame2.setBounds(50, 250, 1200, 30);
			curGame2.setFont(normalFont);
			add(curGame2);

			nextGame2 = new JLabel("Naechste Partie Feld 2: Ungenutzt");
			nextGame2.setBounds(50, 300, 1200, 30);
			nextGame2.setFont(normalFont);
			add(nextGame2);
		}
		updateGames();
		if (log.isLoad()) {
			log.loadMatches(matches);
			updateGames();

			if (curMatch1 == null && curMatch2 == null && knockout == null) {
				if (configuration.isKnockout() && knockout == null) {
					ArrayList<Team> groupA = new ArrayList<>();
					ArrayList<Team> groupB = new ArrayList<>();

					for (int i = 0; i < teams.size(); i++) {
						if (i < (teams.size() + 1) / 2) {
							groupA.add(teams.get(i));
						} else {
							groupB.add(teams.get(i));
						}
					}
					TableCalculator.clear(groupA);
					TableCalculator.calcTable(matches, groupA, true);
					TableCalculator.clear(groupB);
					TableCalculator.calcTable(matches, groupB, true);
					for (int i = 0; i < groupB.size(); i++) {
						groupA.get(i).setPosition(i * 2 + 1);
						groupB.get(i).setPosition(i * 2 + 1);
					}
					if (groupA.size() != groupB.size()) {
						groupA.get(groupA.size() - 1).setPosition(teams.size() - 2);
					}
					knockout = Matchplan.knockout(groupA, groupB, configuration.getNumberOfFields(), configuration.isHasSemi());
					this.matches = knockout;
					log.loadMatches(matches);
					semidone();
					updateGames();
				}
			}
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
				setEntry();
			}
		});
		add(endGame);

		JButton matchplan = new JButton("Spielplan");
		matchplan.setBounds(50, 500, 200, 50);
		matchplan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showMatchplan();
			}
		});
		add(matchplan);

		JButton fix = new JButton("Korrektur");
		fix.setBounds(800, 500, 200, 50);
		fix.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				correct();
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

		setVisible(true);
	}

	private void correct() {
		new CorrectionFrame(matches, this, log);
	}

	//Actions depending on button pressed
	private void showMatchplan() {
		ArrayList<Match> list = new ArrayList<>(groupphase);
		if (knockout != null) {
			list.addAll(matches);
		}
		new MatchplanFrame(list, configuration);
	}

	public void semidone() {
		if (knockout == null) {
			return;
		}
		Match semi1 = null;
		Match semi2 = null;
		int size = knockout.size();
		for (int i = 0; i < size; i++) {
			Match cur = knockout.get(i);
			if (cur.isSemi() && cur.played()) {
				if (semi1 == null) {
					semi1 = cur;
				} else {
					semi2 = cur;
				}
				if (semi2 != null) {
					Matchplan.setFinals(semi1.winner(), semi2.winner(), semi1.looser(), semi2.looser(), knockout);
				}
			}
		}
	}

	private void setEntry() {
		int field = 0;
		if (configuration.getNumberOfFields() > 1) {
			field = Dialog.optionDialog(new String[]{Turnier.fieldname1, Turnier.fieldname2}, "Welches Feld ist fertig?");
		}
		field += 1;
		if (field == 1 && curMatch1 != null) {
			doEntry(curMatch1);
			if (configuration.isHasSemi()) {
				semidone();
			}
		} else if (field == 2 && curMatch2 != null) {
			doEntry(curMatch2);
			if (configuration.isHasSemi()) {
				semidone();
			}
		}


		updateGames();

		if (curMatch1 == null && curMatch2 == null && knockout == null) {
			if (configuration.isKnockout()) {
				ArrayList<Team> groupA = new ArrayList<>();
				ArrayList<Team> groupB = new ArrayList<>();
				showMatchplan();
				int res = JOptionPane.showConfirmDialog(null, "Sind alle Ergebnisse korrekt?");
				if (res != JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(null, "Korrigiere das Ergebnis ueber die Korrekturfunktion. Danach erneut Ergebnis eintragen klicken!");
					return;
				}

				for (int i = 0; i < teams.size(); i++) {
					if (i < (teams.size() + 1) / 2) {
						groupA.add(teams.get(i));
					} else {
						groupB.add(teams.get(i));
					}
				}
				TableCalculator.clear(groupA);
				TableCalculator.calcTable(matches, groupA, true);
				TableCalculator.clear(groupB);
				TableCalculator.calcTable(matches, groupB, true);
				for (int i = 0; i < groupB.size(); i++) {
					groupA.get(i).setPosition(i * 2 + 1);
					groupB.get(i).setPosition(i * 2 + 1);
				}
				if (groupA.size() != groupB.size()) {
					groupA.get(groupA.size() - 1).setPosition(teams.size() - 2);
				}
				knockout = Matchplan.knockout(groupA, groupB, configuration.getNumberOfFields(), configuration.isHasSemi());
				matches = knockout;
				updateGames();
			} else {
				JOptionPane.showMessageDialog(null, "Turnier beendet!");
			}
		}
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
				new TableFrame(groupphase, Matchplan.subList(teams, 0, (teams.size() + 1) / 2), configuration.getTurnierName() + "_Gruppe_A", true);
			} else if (d == 1) {
				new TableFrame(groupphase, Matchplan.subList(teams, (teams.size() + 1) / 2, teams.size()), configuration.getTurnierName() + "_Gruppe_B", true);
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

	private void doEntry(Match match) {
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
