package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Matchplan3 extends AbstractMatchplan {
	public Matchplan3(List<Team> teams, TurnierConfiguration configuration) {
		super(teams, configuration);
	}

	@Override
	public ArrayList<Match> loadGroupstage() {
		ArrayList<Match> res = group3(teams, 1, Match.TYPE.GROUP);
		res.addAll(group3reverse(teams, 1));
		return res;
	}

	@Override
	protected ArrayList<Match> knockoutMatches() {
		return null;
	}

}
