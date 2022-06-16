import java.util.ArrayList;

public class TableCalculator {

	static void clear(ArrayList<Team> teams) {
		for(int i =0 ; i < teams.size(); i++) {
			teams.get(i).setGoals(0);
			teams.get(i).setMinusgoals(0);
			teams.get(i).setPoints(0);
		}
	}
	
	static void calcTable(ArrayList<Match> matches, ArrayList<Team> teams, boolean headToHead) {
		doCalcTable(matches, teams, headToHead);
		clear(teams);
		for(int i = 0; i < matches.size(); i++) {
			if(matches.get(i).played())
				matches.get(i).confirmResult();
		}
	}
	static void doCalcTable(ArrayList<Match> matches, ArrayList<Team> teams, boolean headToHead) {
		ArrayList<Match> newMatches = new ArrayList<>();
		clear(teams);
		for(int i = 0;i < matches.size(); i++) {
			Match m = matches.get(i);
			if(teams.contains(m.getT1()) && teams.contains(m.getT2()) && m.played()) {
				newMatches.add(m);
				m.confirmResult();
			}
		}
		//normal table
		teams.sort(new TeamComparator());
		if(!headToHead) {
			return;
		}
		
		//head to head
		for(int i =0; i < teams.size(); i++) {
			int points = teams.get(i).getPoints();
			ArrayList<Team> newTeams = new ArrayList<>();
			for(int j = i; j < teams.size(); j++) {
				if(teams.get(j).getPoints() == points) {
					newTeams.add(teams.get(j));
				}
			}
			if(newTeams.size() > 1 && newTeams.size() < teams.size()) {
				doCalcTable(matches, newTeams, true);
				for(int k = 0; k < newTeams.size(); k++) {
					teams.set(k + i, newTeams.get(k));
				}
			}
			i += newTeams.size() - 1;
		}
	}
}
