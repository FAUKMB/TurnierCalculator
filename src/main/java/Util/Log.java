package Util;

import frames.MainFrame;
import matchplan.Matchplan;
import matchplan.MatchplanCreator;
import turnier.Match;
import turnier.Team;
import turnier.Turnier;
import turnier.TurnierConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Log {
	private final Path filename;
	private static final Path LOGFOLDER = Path.of("logs");

	public Log(String filename) {
		this.filename = LOGFOLDER.resolve(Path.of(filename));
	}

	public void logInit(TurnierConfiguration configuration) throws IOException {
		Files.createDirectories(LOGFOLDER);
		int numberOfFields = configuration.getNumberOfFields();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename.toString()))) {
			configuration.writeConfigToLog(bw);
			for (int i = 0; i < numberOfFields; i++) {
				bw.write(Turnier.fieldname[i]);
				bw.newLine();
			}
			for (Team team : configuration.getTeams()) {
				bw.write(team.getName());
				bw.newLine();
			}
		} catch (Exception e) {
			System.err.println("logging failed");
		}
	}

	public int load() {
		Scanner s;
		try {
			s = new Scanner(new FileReader(filename.toString()));
		} catch (FileNotFoundException e) {
			return -1;
		}
		TurnierConfiguration configuration = TurnierConfiguration.loadConfig(s);
		Turnier.fieldname = new String[configuration.getNumberOfFields()];
		for (int i = 0; i < Turnier.fieldname.length; i++) {
			Turnier.fieldname[i] = s.nextLine();
		}
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		loadMatches(matches, configuration);
		new MainFrame(matches, configuration, this, matchplan);
		s.close();
		return 0;
	}

	public void loadMatches(List<Match> matches, TurnierConfiguration configuration) {
		Scanner s = null;
		try {
			s = new Scanner(new FileReader(filename.toString()));
			s.nextLine();
			for (int i = 0; i < configuration.getTeams().size() + configuration.getNumberOfFields(); i++) {
				s.nextLine();
			}
			while (s.hasNext()) {
				int id = s.nextInt();
				int g1 = s.nextInt();
				int g2 = s.nextInt();
				if (id < matches.size()) {
					for (Match match : matches) {
						if (match.getId() == id) {
							match.addResult(g1, g2);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("error logging");
		} catch (NoSuchElementException e) {
			if (s != null) {
				s.close();
			}
		}
	}

	public void logMatch(Match m) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename.toString(), true));
			bw.append(m.getId() + " " + m.getGoalT1() + " " + m.getGoalT2());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.err.println("Error logging");
		}
	}
}
