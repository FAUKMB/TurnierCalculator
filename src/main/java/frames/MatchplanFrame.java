package frames;

import turnier.Match;
import turnier.TurnierConfiguration;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MatchplanFrame extends JFrame {

	private static final Path SPIELPLAN_FOLDER = Path.of("Spielplaene");

	public MatchplanFrame(ArrayList<Match> matches, TurnierConfiguration configuration) {
		try {
			Files.createDirectories(SPIELPLAN_FOLDER);
		} catch (Exception e) {
			System.err.println("Directory could not be created.");
		}

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		JLabel head = new JLabel("Spielplan fuer " + configuration.getTurnierName());
		head.setBounds(50, 20, 700, 70);
		add(head);
		setSize(700, 50 * (matches.size() + 1) + 50);
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			String matchS = m.getTypeString() + "  " + m.getFieldname() + "  " + m.showFrame();
			if (m.played()) {
				matchS += "   " + m.getGoalT1() + " : " + m.getGoalT2();
			}
			JLabel match = new JLabel(matchS);
			match.setBounds(50, 30 * i + 80, 700, 40);
			add(match);
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(SPIELPLAN_FOLDER.resolve("Spielplan_" + configuration.getTurnierName() + ".txt").toFile()));) {
			bw.write("Spielplan fuer " + configuration.getTurnierName());
			bw.newLine();
			bw.newLine();
			bw.write("Startzeit: " + configuration.getStartTime() + " Uhr");
			bw.newLine();
			bw.write("Spielzeit: " + configuration.getGameTime() + " Pause: " + configuration.getPauseTime());
			bw.newLine();
			bw.newLine();
			for (Match match : matches) {
				bw.write(match.toString());
				bw.newLine();
			}
		} catch (Exception e) {
			System.err.println("error writing file");
		}
		try {
			Process p = Runtime.getRuntime().exec(new String[]{"python3", "pdfconvert.py", SPIELPLAN_FOLDER.resolve("Spielplan_" + configuration.getTurnierName()).toString()});
			p.waitFor();
		} catch (Exception e) {
			System.err.println("Could not convert to pdf.");
		}

	}
}
