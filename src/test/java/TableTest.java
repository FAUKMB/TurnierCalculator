import Util.TableCalculator;
import matchplan.Matchplan;
import matchplan.MatchplanCreator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableTest {


	ArrayList<Team> createTeams(int numberOfTeams) {
		ArrayList<Team> teams = new ArrayList<>();
		char c = 'a';
		for (int i = 0; i < numberOfTeams; i++) {
			teams.add(new Team(String.valueOf((char) (c + i)).repeat(i + 1)));
		}
		return teams;
	}

	TurnierConfiguration createConfig(int numberOfTeams, TurnierConfiguration.PLAYTYPE type) {
		TurnierConfiguration configuration = new TurnierConfiguration();
		List<Team> teams = createTeams(numberOfTeams);
		configuration.setTeams(teams);
		configuration.setNumberOfFields(2);
		configuration.setHasSemi(true);
		configuration.setPlaytype(type);
		return configuration;
	}


	private void result81(List<Match> matches) {
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
		TurnierConfiguration configuration = createConfig(8, TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		List<Team> teams = configuration.getTeams();
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 8);
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		result81(matches);
		TableCalculator.calcTable(matches, groupA, true);
		TableCalculator.calcTable(matches, groupB, true);
		test81(groupA, groupB);
		List<Match> k = matchplan.loadKnockoutStageMatches(configuration);
		Assert.assertEquals(k.get(0).getT1().getName(), "a");
		Assert.assertEquals(k.get(0).getT2().getName(), "eeeee");
		Assert.assertEquals(k.get(1).getT1().getName(), "hhhhhhhh");
		Assert.assertEquals(k.get(1).getT2().getName(), "bb");
		Assert.assertEquals(k.get(2).getT1().getName(), "ccc");
		Assert.assertEquals(k.get(2).getT2().getName(), "ffffff");
		Assert.assertEquals(k.get(3).getT1().getName(), "ggggggg");
		Assert.assertEquals(k.get(3).getT2().getName(), "dddd");
	}

	private int randomGoal() {
		return (int) (Math.random() * 5);
	}

	record Pair(int k, TurnierConfiguration.PLAYTYPE val) {

	}

	@DataProvider(name = "matchresultsRoundRobin")
	public Object[][] group4Results() {
		List<Pair> types = new ArrayList<>();
		Map<Integer, List<TurnierConfiguration.PLAYTYPE>> typeMap = new TurnierConfiguration().getTypeOptions();
		typeMap.forEach((k, v) -> {
			v.forEach(t -> {
				if (t == TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN || t == TurnierConfiguration.PLAYTYPE.ROUND_ROBIN) {
					types.add(new Pair(k, t));
				}
			});
		});

		Object[][] result = new Object[1000 * types.size()][2];
		for (int k = 0; k < types.size(); k++) {
			Pair type = types.get(k);
			for (int i = k * 1000; i < 1000 * k + 1000; i++) {
				TurnierConfiguration configuration = createConfig(type.k, type.val);
				Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
				List<Match> matches = matchplan.loadGroupstageMatches(configuration);
				for (Match m : matches) {
					m.addResult(randomGoal(), randomGoal());
				}
				result[i][0] = matches;
				result[i][1] = configuration.getTeams();
			}
		}
		return result;
	}

	@Test(dataProvider = "matchresultsRoundRobin")
	public void testGroup4Table(List<Match> matches, List<Team> teams) {
		TableCalculator.calcTable(matches, teams, true);
		for (int i = 0; i < teams.size() - 1; i++) {
			Assert.assertTrue(teams.get(i).getPoints() >= teams.get(i + 1).getPoints());
		}
		int points = teams.stream().map(Team::getPoints).reduce(0, Integer::sum);
		Assert.assertEquals(points, expectedPoints(matches));
		int goals = teams.stream().map(Team::getGoals).reduce(0, Integer::sum);
		int minumsGoals = teams.stream().map(Team::getMinusgoals).reduce(0, Integer::sum);
		Assert.assertEquals(goals, minumsGoals);
		int expectedGoals = matches.stream().map(m -> m.getGoalT2() + m.getGoalT1()).reduce(0, Integer::sum);
		Assert.assertEquals(goals, expectedGoals);
	}

	private int expectedPoints(List<Match> matches) {
		int sum = 0;
		for (Match m : matches) {
			if (m.getGoalT1() != m.getGoalT2()) {
				sum += 3;
			} else {
				sum += 2;
			}
		}
		return sum;
	}

}
