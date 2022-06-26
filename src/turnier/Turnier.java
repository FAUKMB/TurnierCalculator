package turnier;

import java.util.ArrayList;

import javax.swing.*;

//Program to simulate a soccer competition with 8 teams 

/**
 * 
 * @author KMB<kili.bender@t-online.de>
 *
 */

/*
 * 
 * !THIS IS STILL A BETAVERSION! 
 * 
 * SEND BUGS TO KMB WITH STACKTRACE FOR UNHANDLED ERRORS OR DESCRIPTION OF THE MISBEHAVIOUR
 * PARTS OF THIS PROGRAMM ARE IN GERMAN BECAUSE IT WAS WRITTEN FOR GERMAN USERS
 * 
 */


//main class to start up the process

public class Turnier {
	public static int maxNameLen = 0;
	public static int numberOfFields = 0;
	public static String fieldname1;
	public static String fieldname2;

	public static void main(String args[]){
		int numberOfTeams;
		boolean semi = false;
		int option = 0;
		String name;
		
		String logfile = JOptionPane.showInputDialog("load from logfile?");
		if(logfile != null) {
			Log log = new Log(logfile, true);
			if(-1 == log.load()) {
				JOptionPane.showMessageDialog(null, "logfile nicht gefunden");
			}
			return;
		}

		name = JOptionPane.showInputDialog("Turniername:");
		if(name == null) {
			return;
		}
		numberOfTeams = Dialog.integerDialog("Anzahl der Mannschaften");
		if(numberOfTeams == -1) {
			return;
		}
		if(numberOfTeams < 3 || numberOfTeams > 10) {
			JOptionPane.showMessageDialog(null, "Waehle 3-10 teams");
		}
		numberOfFields = Dialog.integerDialog("Anzahl der Spielfelder");
		if(numberOfFields == -1) {
			return;
		}
		if(numberOfFields != 1 && numberOfFields != 2) {
			JOptionPane.showMessageDialog(null, "Es sind nur ein oder zwei Felder moeglich");
			return;
		}
		fieldname1 = JOptionPane.showInputDialog("Feldname 1");
		if(fieldname1 == null || fieldname1.length() > 6) {
			JOptionPane.showMessageDialog(null, "Ungueltiger Feldname");
			return;
		}
		for(int i = fieldname1.length(); i < 6; i++) {
			fieldname1 += " ";
		}
		if(numberOfFields == 2) {
			fieldname2 = JOptionPane.showInputDialog("Feldname 2");
			if(fieldname2 == null || fieldname2.length() > 6) {
				JOptionPane.showMessageDialog(null, "Ungueltiger Feldname");
				return;
			}
			for(int i = fieldname2.length(); i < 6; i++) {
				fieldname2 += " ";
			}
		}
		if(numberOfTeams == 5) {
			option = Dialog.optionDialog(new String[]{"round robin",  "doppel round robin"}, "Waehle Modus");
			if(option != 0 && option != 1) {
				JOptionPane.showMessageDialog(null, "Option muss gewaehlt werden");
				return;
			}
			option++;
		}
		if(numberOfTeams == 7) {
			option = Dialog.optionDialog(new String[]{"gruppe 3 round robin",  "gruppe 3 doppel round robin", "round robin"}, "Waehle Modus");
			if(option != 0 && option != 1 && option != 2) {
				JOptionPane.showMessageDialog(null, "Option muss gewaehlt werden");
				return;
			}
			option++;
		}
		if(numberOfTeams > 5 && option != 3) {
			int ret = JOptionPane.showConfirmDialog(null, "Mit Halbfinale?");
			if(ret == JOptionPane.YES_OPTION) {
				semi = true;
			}else if( ret == JOptionPane.CANCEL_OPTION || ret == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}
		int [] starttime = Dialog.timeDialog();
		if(starttime == null) {
			JOptionPane.showMessageDialog(null, "Ungueltige Zeit");
		}
		int gametime = Dialog.integerDialog("Spielzeit:");
		if(gametime == -1) {
			return;
		}
		int pausetime = Dialog.integerDialog("Pausenzeit:");
		if(pausetime == -1) {
			return;
		}
		ArrayList<Team> teams = new ArrayList<>();
		for(int i = 0; i < numberOfTeams; i++) {
			String s = JOptionPane.showInputDialog("Mannschaft " + (i+1) + " eingeben:");
			if(s == null) {
				return;
			}
			if(s.length() > maxNameLen) {
				maxNameLen = s.length();
			}
			teams.add(new Team(s));
		}
		ArrayList<Match> matches = Matchplan.loadGroupstage(numberOfFields, teams, option);
		Log log = new Log(name + "_log.txt", false);
		String[] teamss = new String[teams.size()];
		for(int i = 0; i < teams.size(); i++) {
			teamss[i] = teams.get(i).getName();
		}
		log.logInit(name, semi, teamss, option, numberOfFields, starttime[0], starttime[1], pausetime, gametime);

		new MainFrame(matches, teams , semi, numberOfFields, name, starttime[0], starttime[1], pausetime, gametime, numberOfTeams >= 6 && option != 3, log);
	}
}
