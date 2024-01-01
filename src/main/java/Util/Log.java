package Util;

import frames.MainFrame;
import matchplan.AbstractMatchplan;
import matchplan.MatchplanSelector;
import turnier.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Log {
	private String filename;
	private int numberOfFields;

	public Log(String filename, boolean load) {
		this.filename = filename;
	}

	public void logInit(String[] teams, TurnierConfiguration configuration) {
		numberOfFields = configuration.getNumberOfFields();
		Time starttime = configuration.getStartTime();
		Time pausetime = configuration.getPauseTime();
		Time gametime = configuration.getGameTime();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
			bw.write(configuration.getTurnierName());
			bw.newLine();
			bw.write(String.valueOf(configuration.isHasSemi()));
			bw.newLine();
			bw.write(configuration.getPlaytype().name());
			bw.newLine();
			bw.write(numberOfFields + " " + teams.length);
			bw.newLine();
			bw.write(starttime.toString());
			bw.newLine();
			bw.write(pausetime.toString());
			bw.newLine();
			bw.write(gametime.toString());
			bw.newLine();
			for (int i = 0; i < numberOfFields; i++) {
				bw.write(Turnier.fieldname[i]);
				bw.newLine();
			}
			for (String team : teams) {
				bw.write(team);
				bw.newLine();
			}
		} catch (Exception e) {
			System.err.println("logging failed");
		}
	}

	public int load() {
		TurnierConfiguration configuration = new TurnierConfiguration();

		Scanner s;
		try {
			s = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			return -1;
		}
		String line = s.nextLine();
		configuration.setTurnierName(line);
		boolean b = s.nextBoolean();
		s.nextLine();
		configuration.setHasSemi(b);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.valueOf(s.nextLine()));
		configuration.setNumberOfFields(s.nextInt());
		Turnier.numberOfFields = configuration.getNumberOfFields();
		configuration.setNumberOfTeams(s.nextInt());
		s.nextLine();
		configuration.setStartTime(new Time(s.nextLine()));
		configuration.setPauseTime(new Time(s.nextLine()));
		configuration.setGameTime(new Time(s.nextLine()));

		Turnier.fieldname = new String[configuration.getNumberOfFields()];
		for (int i = 0; i < Turnier.fieldname.length; i++) {
			Turnier.fieldname[i] = s.nextLine();
		}
		ArrayList<Team> teams = new ArrayList<>();
		for (int i = 0; i < configuration.getNumberOfTeams(); i++) {
			String teamname = s.nextLine();
			if (teamname.length() > Turnier.maxNameLen) {
				Turnier.maxNameLen = teamname.length();
			}
			teams.add(new Team(teamname));
		}
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		loadMatches(matches, configuration);
		new MainFrame(matches, teams, configuration, this, matchplan);
		s.close();
		return 0;
	}

	public void loadMatches(ArrayList<Match> matches, TurnierConfiguration configuration) {
		Scanner s = null;
		try {
			s = new Scanner(new FileReader(filename));
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			for (int i = 0; i < configuration.getNumberOfTeams() + configuration.getNumberOfFields(); i++) {
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
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
			bw.append(m.getId() + " " + m.getGoal1() + " " + m.getGoal2());
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.err.println("Error logging");
		}
	}
}
