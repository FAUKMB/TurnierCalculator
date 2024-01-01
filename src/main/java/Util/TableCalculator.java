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

	public static void calcTable(ArrayList<Match> matches, List<Team> teams, boolean headToHead) {
		clear(teams);
		doCalcTable(matches, teams, headToHead);
		clear(teams);
		for (Match match : matches) {
			if (match.played() && teams.contains(match.getT1()) && teams.contains(match.getT2())) {
				match.confirmResult();
			}
		}
	}

	private static void doCalcTable(ArrayList<Match> matches, List<Team> teams, boolean headToHead) {
		clear(teams);
		for (Match m : matches) {
			if (teams.contains(m.getT1()) && teams.contains(m.getT2()) && m.played()) {
				m.confirmResult();
			}
		}
		//normal table
		teams.sort(new TeamComparator());
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
}
