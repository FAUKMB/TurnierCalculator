package matchplan;

import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.List;

public class MatchplanSelector {
	public static AbstractMatchplan createMatchplan(List<Team> teams, TurnierConfiguration configuration) {
		return switch (teams.size()) {
			case 3 -> new Matchplan3(teams, configuration);
			case 4 -> new Matchplan4(teams, configuration);
			case 5 -> new Matchplan5(teams, configuration);
			case 6 -> new Matchplan6(teams, configuration);
			case 7 -> new Matchplan7(teams, configuration);
			case 8 -> new Matchplan8(teams, configuration);
			case 9 -> new Matchplan9(teams, configuration);
			case 10 -> new Matchplan10(teams, configuration);
			default -> throw new IllegalStateException();
		};
	}
}
