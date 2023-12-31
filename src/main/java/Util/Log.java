package Util;

import frames.MainFrame;
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
	boolean load = false;
	private int numberOfTeams;
	private int numberOfFields;

	public Log(String filename, boolean load) {
		this.filename = filename;
		this.load = load;
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
			bw.write(Turnier.fieldname1);
			bw.newLine();
			if (numberOfFields == 2) {
				bw.write(Turnier.fieldname2);
				bw.newLine();
			}
			for (int i = 0; i < teams.length; i++) {
				bw.write(teams[i]);
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

		Turnier.fieldname1 = s.nextLine();
		if (configuration.getNumberOfFields() == 2) {
			Turnier.fieldname2 = s.nextLine();
		}
		ArrayList<Team> teams = new ArrayList<>();
		for (int i = 0; i < configuration.getNumberOfTeams(); i++) {
			String teamname = s.nextLine();
			if (teamname.length() > Turnier.maxNameLen) {
				Turnier.maxNameLen = teamname.length();
			}
			teams.add(new Team(teamname));
		}
		ArrayList<Match> matches = Matchplan.loadGroupstage(teams, configuration);

		new MainFrame(matches, teams, configuration, this);
		s.close();
		return 0;
	}

	public boolean isLoad() {
		return load;
	}

	public void loadMatches(ArrayList<Match> matches) {
		Scanner s = null;
		try {
			s = new Scanner(new FileReader(filename));
			s.nextLine();
			s.nextLine();
			s.nextLine();
			if (numberOfFields == 2) {
				s.nextLine();
			}
			for (int i = 0; i < numberOfTeams; i++) {
				s.nextLine();
			}
			while (true) {
				int id = s.nextInt();
				int g1 = s.nextInt();
				int g2 = s.nextInt();
				Match m = null;
				if (id < matches.size()) {
					for (int i = 0; i < matches.size(); i++) {
						m = matches.get(i);
						if (matches.get(i).getId() == id) {
							break;
						}

					}
					m.addResult(g1, g2);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("error logging");
		} catch (NoSuchElementException e) {
			if (s != null) {
				s.close();
			}
			return;
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
