package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan7 extends AbstractMatchplan {
	public Matchplan7(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res = new ArrayList<>();
		if (configuration.getPlaytype() != TurnierConfiguration.PLAYTYPE.ROUND_ROBIN) {
			ArrayList<Match> tmp = group4(teams.subList(0, 4), 1, "A");
			ArrayList<Match> res2;

			res2 = group3(teams.subList(4, 7), configuration.getNumberOfFields(), Match.TYPE.GROUP, "B");
			if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP) {
				res2.addAll(group3reverse(teams.subList(4, 7), configuration.getNumberOfFields(), "B"));
				for (int i = 0; i < res2.size(); i++) {
					res.add(tmp.get(i));
					res.add(res2.get(i));
				}
			} else {
				for (int i = 0; i < res2.size(); i++) {
					res.add(tmp.get(i * 2));
					res.add(tmp.get(i * 2 + 1));
					res.add(res2.get(i));
				}
			}

		} else {
			res = group7(teams, configuration.getNumberOfFields());
		}
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 7);
		res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(groupB.get(0), groupA.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(0), groupB.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
		}
		res.add(new Match(groupB.get(2), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
		} else {
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
		}
		res.add(new Match(groupA.get(3), groupA.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupB.get(0), groupA.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}

	private ArrayList<Match> group7(List<Team> teams, int numberOfFields) {
		ArrayList<Match> res = new ArrayList<>();
		Match.TYPE type = Match.TYPE.GROUP;
		res.add(new Match(teams.get(0), teams.get(1), 1, type));
		res.add(new Match(teams.get(2), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(2), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(0), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(1), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(2), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(0), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(1), res.size() % numberOfFields + 1, type));
		return res;
	}
}
