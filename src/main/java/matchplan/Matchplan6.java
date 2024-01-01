package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan6 extends AbstractMatchplan {
	public Matchplan6(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res = new ArrayList<>();
		if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.ROUND_ROBIN) {
			res = group6();
		} else {
			ArrayList<Match> tmp = group3(teams.subList(0, 5), 1, Match.TYPE.GROUP, "A");
			ArrayList<Match> res2;
			res2 = group3(teams.subList(3, 6), configuration.getNumberOfFields(), Match.TYPE.GROUP, "B");
			for (int i = 0; i < tmp.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
			tmp = group3reverse(teams.subList(0, 3), 1, "A");
			res2 = group3reverse(teams.subList(3, 6), configuration.getNumberOfFields(), "B");
			for (int i = 0; i < tmp.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
		}
		return res;
	}

	private ArrayList<Match> group6() {
		ArrayList<Match> res = new ArrayList<>();
		int numberOfFields = configuration.getNumberOfFields();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(0), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(1), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(4), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(0), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(5), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(4), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(1), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(5), res.size() % numberOfFields + 1, Match.TYPE.GROUP));

		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 3);
		List<Team> groupB = teams.subList(3, 6);
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
		}
		res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}
}
