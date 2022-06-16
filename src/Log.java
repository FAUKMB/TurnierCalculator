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
	
	public Log(String filename, boolean load) {
		this.filename = filename;
		this.load = load;
	}
	
	public void logInit(String name, boolean semi, String[] teams, int option, int numberOfFields) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			bw.write(name);
			bw.newLine();
			bw.write(semi + " " + option + " " + numberOfFields + " " + teams.length);
			bw.newLine();
			for(int i = 0; i < teams.length; i++) {
				bw.write(teams[i]);
				bw.newLine();
			}
		
			bw.close();
		}catch(Exception e) {
			System.err.println("logging failed");
		}
	}
	
	public void load() {
		String name;
		Scanner s = null;
		try {
			s = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = s.nextLine();
		boolean semi = s.nextBoolean();
		int option = s.nextInt();
		int numberOfFields = s.nextInt();
		numberOfTeams = s.nextInt();
		s.nextLine();
		ArrayList<Team> teams = new ArrayList<>();
		for(int i = 0; i < numberOfTeams; i++) {
			teams.add(new Team(s.nextLine()));
		}
		ArrayList<Match> matches = Matchplan.loadGroupstage(numberOfFields,  teams,  option);
		new MainFrame(matches, teams, semi, numberOfFields,  name, numberOfTeams >= 6 && option != 3, this);
		s.close();
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
			for(int i = 0; i < numberOfTeams; i++) {
				s.nextLine();
			}
			while(true) {
				int id = s.nextInt();
				int g1 = s.nextInt();
				int g2 = s.nextInt();
				if(id < matches.size()) {
					matches.get(id).addResult(g1, g2);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("error logging");
		} catch (NoSuchElementException e) {
			if(s != null) {
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
		}catch(Exception e) {
			System.err.println("Error logging");
		}
	}
}
