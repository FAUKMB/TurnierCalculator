package Util;

import turnier.Match;
import turnier.Team;

import java.util.ArrayList;
import java.util.List;

public class TableCalculator {

	private static void clear(List<Team> teams) {
		for (Team team : teams) {
			team.setGoals(0);
			team.setMinusgoals(0);
			team.setPoints(0);
		}
	}

	public static void calcTable(List<Match> matches, List<Team> teams, boolean headToHead) {
		doCalcTable(matches, teams, headToHead);
		clear(teams);
		confirmResults(matches, teams);
	}

	private static void confirmResults(List<Match> matches, List<Team> teams) {
		for (Match m : matches) {
			if (teams.contains(m.getT1()) && teams.contains(m.getT2()) && m.played()) {
				m.confirmResult();
			}
		}
	}

	private static void doCalcTable(List<Match> matches, List<Team> teams, boolean headToHead) {
		clear(teams);
		confirmResults(matches, teams);
		//normal table
		teams.sort(TableCalculator::compareTeams);
		if (!headToHead) {
			return;
		}

		//head to head
		for (int i = 0; i < teams.size(); i++) {
			int points = teams.get(i).getPoints();
			ArrayList<Team> newTeams = new ArrayList<>();
			for (int j = i; j < teams.size(); j++) {
				if (teams.get(j).getPoints() == points) {
					newTeams.add(teams.get(j));
				}
			}
			if (newTeams.size() > 1 && newTeams.size() < teams.size()) {
				doCalcTable(matches, newTeams, true);
				for (int k = 0; k < newTeams.size(); k++) {
					teams.set(k + i, newTeams.get(k));
				}
			}
			i += newTeams.size() - 1;
		}
	}

	private static int compareTeams(Team t1, Team t2) {
		if (t1.getPosition() != t2.getPosition()) {
			return Integer.compare(t1.getPosition(), t2.getPosition());
		}
		if (t1.getPoints() != t2.getPoints()) {
			return Integer.compare(t2.getPoints(), t1.getPoints());
		} else if (t1.getGoals() - t1.getMinusgoals() != t2.getGoals() - t2.getMinusgoals()) {
			return Integer.compare(t2.getGoals() - t2.getMinusgoals(), t1.getGoals() - t1.getMinusgoals());
		} else {
			return Integer.compare(t2.getGoals(), t1.getGoals());
		}
	}
}

