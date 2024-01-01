package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan4 extends AbstractMatchplan {

	public Matchplan4(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res;
		if (configuration.getNumberOfFields() == 2) {
			res = group4mixed(teams, null);
			res.addAll(group4mixedreverse(teams, null));
		} else {
			res = group4(teams, 1);
			res.addAll(group4reverse(teams, 1));
		}
		Match first = res.get(0);
		res.set(0, res.get(1));
		res.set(1, first);
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		return null;
	}
}
