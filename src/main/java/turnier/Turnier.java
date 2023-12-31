package turnier;

import Util.Dialog;
import Util.Log;
import Util.Matchplan;
import frames.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

//Program to simulate a soccer competition with 8 teams 

/**
 * @author KMB<kili.bender @ t-online.de>
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
	public static String fieldname1;
	public static String fieldname2;

	public static int numberOfFields;

	public static void main(String[] args) {


		String logfile = JOptionPane.showInputDialog("load from logfile?");
		if (logfile != null) {
			Log log = new Log(logfile, true);
			if (-1 == log.load()) {
				JOptionPane.showMessageDialog(null, "logfile nicht gefunden");
			}
			return;
		}
		TurnierConfiguration configuration = Dialog.configurationDialog();
		Dialog.showPlaytypeDialog(configuration);
		if (configuration.isKnockout()) {
			Dialog.showSemifinalDialog(configuration);
		}
		numberOfFields = configuration.getNumberOfFields();
		if (configuration.getNumberOfFields() != 1) {
			fieldname1 = JOptionPane.showInputDialog("Feldname 1");
			if (fieldname1 == null || fieldname1.length() > 6) {
				JOptionPane.showMessageDialog(null, "Ungueltiger Feldname");
				return;
			}
			for (int i = fieldname1.length(); i < 6; i++) {
				fieldname1 += " ";
			}
			fieldname2 = JOptionPane.showInputDialog("Feldname 2");
			if (fieldname2 == null || fieldname2.length() > 6) {
				JOptionPane.showMessageDialog(null, "Ungueltiger Feldname");
				return;
			}
			for (int i = fieldname2.length(); i < 6; i++) {
				fieldname2 += " ";
			}
		}

		ArrayList<Team> teams = new ArrayList<>();
		for (int i = 0; i < configuration.getNumberOfTeams(); i++) {
			String s = JOptionPane.showInputDialog("Mannschaft " + (i + 1) + " eingeben:");
			if (s == null) {
				return;
			}
			if (s.length() > maxNameLen) {
				maxNameLen = s.length();
			}
			teams.add(new Team(s));
		}

		ArrayList<Match> matches = Matchplan.loadGroupstage(teams, configuration);
		Log log = new Log(configuration.getTurnierName() + "_log.txt", false);
		String[] teamss = teams.stream().map(Team::getName).toArray(String[]::new);
		log.logInit(teamss, configuration);

		new MainFrame(matches, teams, configuration, log);
	}
}
