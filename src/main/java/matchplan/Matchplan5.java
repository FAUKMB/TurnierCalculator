package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan5 extends AbstractMatchplan {
	public Matchplan5(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res;
		if (configuration.getNumberOfFields() == 2) {
			res = group5mixed(teams);
			if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN) {
				res.addAll(group5mixedreverse(teams));
			}
		} else {
			res = group5(teams, 1);
			if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN) {
				res.addAll(group5reverse(teams, configuration.getNumberOfFields()));
			}
		}
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		return null;
	}
}
