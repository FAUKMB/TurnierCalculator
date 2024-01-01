import Util.TableCalculator;
import matchplan.AbstractMatchplan;
import matchplan.MatchplanSelector;
import org.testng.Assert;
import org.testng.annotations.Test;
import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class TableTest {


	ArrayList<Team> createTeams(int numberOfTeams) {
		ArrayList<Team> teams = new ArrayList<>();
		char c = 'a';
		for (int i = 0; i < numberOfTeams; i++) {
			teams.add(new Team(String.valueOf((char) (c + i)).repeat(i + 1)));
		}
		return teams;
	}


	private void result81(ArrayList<Match> matches) {
		matches.get(0).addResult(3, 3);
		matches.get(1).addResult(1, 1);
		matches.get(2).addResult(1, 0);
		matches.get(3).addResult(2, 3);
		matches.get(4).addResult(3, 3);
		matches.get(5).addResult(3, 3);
		matches.get(6).addResult(3, 1);
		matches.get(7).addResult(3, 1);
		matches.get(8).addResult(3, 3);
		matches.get(9).addResult(2, 2);
		matches.get(10).addResult(3, 3);
		matches.get(11).addResult(3, 3);
	}

	private void test81(List<Team> groupA, List<Team> groupB) {
		Assert.assertEquals(groupA.get(0).getName(), "a");
		Assert.assertEquals(groupA.get(1).getName(), "bb");
		Assert.assertEquals(groupA.get(2).getName(), "ccc");
		Assert.assertEquals(groupA.get(3).getName(), "dddd");
		Assert.assertEquals(groupB.get(0).getName(), "hhhhhhhh");
		Assert.assertEquals(groupB.get(1).getName(), "eeeee");
		Assert.assertEquals(groupB.get(2).getName(), "ffffff");
		Assert.assertEquals(groupB.get(3).getName(), "ggggggg");

	}

	@Test
	public void test8() {
		ArrayList<Team> teams = createTeams(8);
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 8);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfFields(2);
		configuration.setNumberOfTeams(8);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		configuration.setHasSemi(true);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		result81(matches);
		TableCalculator.calcTable(matches, groupA, true);
		TableCalculator.calcTable(matches, groupB, true);
		test81(groupA, groupB);
		ArrayList<Match> k = matchplan.loadKnockoutStage();
		Assert.assertEquals(k.get(0).getT1().getName(), "a");
		Assert.assertEquals(k.get(0).getT2().getName(), "eeeee");
		Assert.assertEquals(k.get(1).getT1().getName(), "hhhhhhhh");
		Assert.assertEquals(k.get(1).getT2().getName(), "bb");
		Assert.assertEquals(k.get(2).getT1().getName(), "ccc");
		Assert.assertEquals(k.get(2).getT2().getName(), "ffffff");
		Assert.assertEquals(k.get(3).getT1().getName(), "ggggggg");
		Assert.assertEquals(k.get(3).getT2().getName(), "dddd");
	}

}
