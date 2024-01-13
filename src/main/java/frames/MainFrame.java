package frames;

import Util.Dialog;
import Util.Log;
import Util.TableCalculator;
import matchplan.Matchplan;
import turnier.Match;
import turnier.Team;
import turnier.Turnier;
import turnier.TurnierConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

//Main screen for the programm

public class MainFrame extends JFrame {
	private final Match[] curMatches;
	private final JLabel[] curGames;
	private final JLabel[] nextGames;
	private List<Match> matches;
	private List<Match> knockout;
	private final List<Match> groupphase;
	private final List<Team> teams;
	private final Log log;
	private final TurnierConfiguration configuration;
	private final Matchplan matchplan;

	public MainFrame(List<Match> matches, TurnierConfiguration configuration, Log log, Matchplan matchplan) {
		this.matches = matches;
		groupphase = matches;
		this.teams = configuration.getTeams();
		this.log = log;
		this.matchplan = matchplan;
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

		curGames[0] = drawLabel("Aktuelle Partie Feld 1: Ungenutzt", 50, 100, 1200, 30, normalFont);
		nextGames[0] = drawLabel("Naechste Partie Feld 1: Ungenutzt", 50, 150, 1200, 30, normalFont);

		if (configuration.getNumberOfFields() == 2) {
			curGames[1] = drawLabel("Aktuelle Partie Feld 2: Ungenutzt", 50, 250, 1200, 30, normalFont);
			nextGames[1] = drawLabel("Naechste Partie Feld 1: Ungenutzt", 50, 300, 1200, 30, normalFont);
		}

		drawLabel(configuration.getTurnierName(), 150, 30, 900, 50, haedFont);
		drawLabel("Spielzeit: " + configuration.getGameTime() + "min", 1230, 50, 200, 50, normalFont);
		drawLabel("Pause: " + configuration.getPauseTime() + "min", 1230, 75, 200, 50, normalFont);

		drawButton("Endergebnis", 300, 500, this::setEntry);
		drawButton("Spielplan", 50, 500, this::showMatchplan);
		drawButton("Korrektur", 550, 500, this::correct);
		drawButton("Tabellen anzeigen", 800, 500, this::showTable);

		if (gamesFinished()) {
			if (configuration.isKnockout()) {
				calculateKnockoutStage();
				log.loadMatches(matches, configuration);
				updateGames();
			} else {
				JOptionPane.showMessageDialog(null, "Turnier done.");
			}
		}

		updateGames();
		setVisible(true);
	}

	private JLabel drawLabel(String text, int x, int y, int width, int height, Font font) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setBounds(x, y, width, height);
		this.add(label);
		return label;
	}

	private void drawButton(String text, int x, int y, Consumer<Void> action) {
		JButton button = new JButton(text);
		button.setBounds(x, y, 200, 50);
		button.addActionListener(e -> action.accept(null));
		this.add(button);
	}

	public void updateGames() {
		updateKnockoutMatches();
		for (int i = 0; i < configuration.getNumberOfFields(); i++) {
			Matchpair currentGames = getCurrentGames(i);
			updateGameFrames(currentGames, i);
		}
		if (gamesFinished()) {
			if (knockout == null && configuration.isKnockout()) {
				perpareKnockoutStage();
				updateGames();
			} else {
				JOptionPane.showMessageDialog(null, "Turnier done.");
			}
		}
	}

	public void updateKnockoutMatches() {
		Team thirdFirst = null;
		Team thirdSecond = null;
		Team finalFirst = null;
		Team finalSecond = null;

		if (knockout == null) {
			return;
		}

		for (Match m : knockout) {
			if (m.isSemi()) {
				if (thirdFirst == null) {
					thirdFirst = m.looser();
					finalFirst = m.winner();
				} else {
					thirdSecond = m.looser();
					finalSecond = m.winner();
				}
			}
			if (m.getType() == Match.TYPE.THIRD) {
				m.setT1(thirdFirst);
				m.setT2(thirdSecond);
			}
			if (m.getType() == Match.TYPE.FINAL) {
				m.setT1(finalFirst);
				m.setT2(finalSecond);
			}
		}
	}

	private void perpareKnockoutStage() {
		showMatchplan(null);
		int res = JOptionPane.showConfirmDialog(null, "Sind alle Ergebnisse korrekt?");
		if (res != JOptionPane.OK_OPTION) {
			JOptionPane.showMessageDialog(null, "Korrigiere das Ergebnis ueber die Korrekturfunktion. Danach erneut Ergebnis eintragen klicken!");
			return;
		}

		calculateKnockoutStage();
	}

	private void calculateKnockoutStage() {
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
		matches = matchplan.loadKnockoutStageMatches(configuration);
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

	private void updateGameFrames(Matchpair currentGames, int field) {
		if (currentGames.cur != null) {
			curGames[field].setText("Aktuelle Partie " + Turnier.fieldname[field] + " (" + currentGames.cur.getTypeString() + "): " + currentGames.cur.showFrame());
			curMatches[field] = currentGames.cur;
		} else {
			curMatches[field] = null;
			curGames[field].setText("Aktuelle Partie " + Turnier.fieldname[field] + ": Pause...");
		}
		if (currentGames.next != null) {
			nextGames[field].setText("Naechste Partie " + Turnier.fieldname[field] + " (" + currentGames.next.getTypeString() + "): " + currentGames.next.showFrame());
		} else {
			nextGames[field].setText("Naechsete Partie " + Turnier.fieldname[field] + ": Pause...");
		}
	}

	private void correct(Void unused) {
		new CorrectionFrame(matches, this, log);
	}

	//Actions depending on button pressed
	private void showMatchplan(Void unused) {
		ArrayList<Match> list = new ArrayList<>(groupphase);
		if (knockout != null) {
			list.addAll(matches);
		}
		new MatchplanFrame(list, configuration);
	}

	private void setEntry(Void unused) {
		updateGames();
		int field = 0;
		if (configuration.getNumberOfFields() > 1) {
			field = Dialog.optionDialog(Turnier.fieldname, "Welches Feld ist fertig?");
		}
		doEntry(field);
		updateGames();
	}

	private void showTable(Void unused) {
		if (configuration.isKnockout()) {
			String[] ops = {"Gruppe A", "Gruppe B", "Alle"};
			int d = JOptionPane.showOptionDialog(null, "Waehle Gruppe:", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, ops, null);
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

}
