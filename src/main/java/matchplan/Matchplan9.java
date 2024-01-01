package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan9 extends AbstractMatchplan {
	public Matchplan9(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res = new ArrayList<>();
		ArrayList<Match> tmp = group5(teams.subList(0, 5), 1, "A");
		ArrayList<Match> res2;
		res2 = group4(teams.subList(5, 9), configuration.getNumberOfFields(), "B");
		for (int i = 0; i < res2.size(); i++) {
			res.add(tmp.get(i));
			res.add(res2.get(i));
		}
		for (int i = 6; i < tmp.size(); i++) {
			res.add(tmp.get(i));
		}
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 5);
		List<Team> groupB = teams.subList(5, 9);
		res.add(new Match(groupB.get(3), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(2), groupB.get(2), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(groupB.get(1), groupA.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}
}
