package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan8 extends AbstractMatchplan {
	public Matchplan8(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res2;
		ArrayList<Match> tmp;
		ArrayList<Match> res = new ArrayList<>();
		if (configuration.getNumberOfFields() == 2) {
			tmp = group4mixed(teams.subList(0, 4), "A");
			res2 = group4mixed(teams.subList(4, 8), "B");
		} else {
			tmp = group4(teams.subList(0, 4), 1, "A");
			res2 = group4(teams.subList(4, 8), 1, "B");
		}
		for (int i = 0; i < res2.size(); i += 2) {
			res.add(tmp.get(i));
			res.add(tmp.get(i + 1));
			res.add(res2.get(i));
			res.add(res2.get(i + 1));
		}
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 8);
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(3), groupA.get(3), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(null, null, 2, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(3), groupB.get(3), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(2), groupA.get(2), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(0), groupA.get(0), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
		}
		return res;
	}
}
