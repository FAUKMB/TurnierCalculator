package matchplan;

import turnier.Match;
import turnier.TurnierConfiguration;

import java.util.List;
import java.util.function.Function;

public enum Matchplan {
	MATCHPLAN3(MatchplanCreator::loadGroupstage3, configuration -> {
		return null;
	}),
	MATCHPLAN4(MatchplanCreator::loadGroupstage4, configuration -> {
		return null;
	}),
	MATCHPLAN5(MatchplanCreator::loadGroupstage5, configuration -> {
		return null;
	}),
	MATCHPLAN6(MatchplanCreator::loadGroupstage6, MatchplanCreator::loadKnockoutStage6),
	MATCHPLAN7(MatchplanCreator::loadGroupstage7, MatchplanCreator::loadKnockoutStage7),
	MATCHPLAN8(MatchplanCreator::loadGroupstage8, MatchplanCreator::loadKnockoutStage8),
	MATCHPLAN9(MatchplanCreator::loadGroupstage9, MatchplanCreator::loadKnockoutStage9),
	MATCHPLAN10(MatchplanCreator::loadGroupstage10, MatchplanCreator::loadKnockoutStage10);

	private final Function<TurnierConfiguration, List<Match>> loadGroupstage;
	private final Function<TurnierConfiguration, List<Match>> loadKnockout;

	Matchplan(Function<TurnierConfiguration, List<Match>> loadGroupstage, Function<TurnierConfiguration, List<Match>> loadKnockout) {
		this.loadGroupstage = loadGroupstage;
		this.loadKnockout = loadKnockout;
	}

	public List<Match> loadGroupstageMatches(TurnierConfiguration configuration) {
		return this.loadGroupstage.apply(configuration);
	}

	public List<Match> loadKnockoutStageMatches(TurnierConfiguration configuration) {
		return this.loadKnockout.apply(configuration);
	}
}
