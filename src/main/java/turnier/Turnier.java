package turnier;

import Util.Dialog;
import Util.Log;
import frames.MainFrame;
import matchplan.Matchplan;
import matchplan.MatchplanCreator;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

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
	public static String[] fieldname;
	public static int numberOfFields;

	public static void main(String[] args) throws IOException {


		String logfile = JOptionPane.showInputDialog("load from logfile?");
		if (logfile != null) {
			Log log = new Log(logfile);
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
		fieldname = new String[numberOfFields];
		for (int i = 0; i < numberOfFields; i++) {
			fieldname[i] = "Feld" + (i + 1);
		}

		for (Team t : configuration.getTeams()) {
			if (maxNameLen < t.getName().length()) {
				maxNameLen = t.getName().length();
			}
		}
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		Log log = new Log(configuration.getTurnierName() + "_log.txt");
		log.logInit(configuration);

		new MainFrame(matches, configuration, log, matchplan);
	}
}
