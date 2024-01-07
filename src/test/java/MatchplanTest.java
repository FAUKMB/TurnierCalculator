import matchplan.Matchplan;
import matchplan.MatchplanCreator;
import org.testng.Assert;
import org.testng.annotations.Test;
import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MatchplanTest {

	private int id(Team team) {
		char base = 'a';
		Assert.assertEquals(team.getName().length(), 1);
		char name = team.getName().charAt(0);
		Assert.assertTrue(name - base < 10);
		return name - base;
	}

	private ArrayList<Team> createTeams(int numberOfTeams) {
		ArrayList<Team> teams = new ArrayList<>();
		char name = 'a';
		for (int i = 0; i < numberOfTeams; i++) {
			teams.add(new Team(String.valueOf((char) (name + i))));
		}
		return teams;
	}

	private TurnierConfiguration createConfig(int numberOfTeams, TurnierConfiguration.PLAYTYPE type) {
		ArrayList<Team> teams = createTeams(numberOfTeams);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setTeams(teams);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(type);
		return configuration;
	}

	@Test
	public void matchtest3() {
		TurnierConfiguration configuration = createConfig(3, TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		List<Team> teams = configuration.getTeams();
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 2);
			Assert.assertEquals(rc[i], 2);
		}
	}

	@Test
	public void matchtest4() {
		TurnierConfiguration configuration = createConfig(4, TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 3);
			Assert.assertEquals(rc[i], 3);
		}

	}

	@Test
	public void matchtest5() {
		TurnierConfiguration configuration = createConfig(5, TurnierConfiguration.PLAYTYPE.ROUND_ROBIN);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 2);
			Assert.assertEquals(rc[i], 2);
		}
	}

	@Test
	public void matchtest5Double() {
		TurnierConfiguration configuration = createConfig(5, TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 4);
			Assert.assertEquals(rc[i], 4);
		}
	}

	@Test
	public void matchtest6() {
		TurnierConfiguration configuration = createConfig(6, TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			Assert.assertTrue((id(m.getT1()) < teams.size() / 2 && id(m.getT2()) < teams.size() / 2) ||
									  (id(m.getT1()) >= teams.size() / 2 && id(m.getT2()) >= teams.size() / 2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 2);
			Assert.assertEquals(rc[i], 2);
		}
	}

	@Test
	public void matchtest7() {
		TurnierConfiguration configuration = createConfig(7, TurnierConfiguration.PLAYTYPE.ROUND_ROBIN);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());

			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 3);
			Assert.assertEquals(rc[i], 3);
		}
	}

	@Test
	public void matchtest7DoubleRoundRobinSmallGroup() {
		TurnierConfiguration configuration = createConfig(7, TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			Assert.assertTrue((id(m.getT1()) <= teams.size() / 2 && id(m.getT2()) <= teams.size() / 2) ||
									  (id(m.getT1()) > teams.size() / 2 && id(m.getT2()) > teams.size() / 2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 4; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 2);
			Assert.assertEquals(rc[i], 2);
		}
		for (int i = 0; i < 4; i++) {
			Assert.assertEquals(lc[i] + rc[i], 3);
			Assert.assertTrue(lc[i] >= 1);
			Assert.assertTrue(rc[i] >= 1);
		}
	}

	@Test
	public void matchtest8() {
		TurnierConfiguration configuration = createConfig(8, TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			Assert.assertTrue((id(m.getT1()) < teams.size() / 2 && id(m.getT2()) < teams.size() / 2) ||
									  (id(m.getT1()) >= teams.size() / 2 && id(m.getT2()) >= teams.size() / 2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i] + rc[i], 3);
			Assert.assertTrue(lc[i] >= 1);
			Assert.assertTrue(rc[i] >= 1);
		}
	}

	@Test
	public void matchtest9() {
		TurnierConfiguration configuration = createConfig(9, TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			Assert.assertTrue((id(m.getT1()) <= teams.size() / 2 && id(m.getT2()) <= teams.size() / 2) ||
									  (id(m.getT1()) > teams.size() / 2 && id(m.getT2()) > teams.size() / 2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 5; i < teams.size(); i++) {
			Assert.assertEquals(lc[i] + rc[i], 3);
			Assert.assertTrue(lc[i] >= 1);
			Assert.assertTrue(rc[i] >= 1);
		}
		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(2, lc[i]);
			Assert.assertEquals(2, rc[i]);
		}
	}

	@Test
	public void matchtest10() {
		TurnierConfiguration configuration = createConfig(10, TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		List<Team> teams = configuration.getTeams();
		Matchplan matchplan = MatchplanCreator.selectMatchplan(configuration);
		List<Match> matches = matchplan.loadGroupstageMatches(configuration);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (Match m : matches) {
			Assert.assertNotEquals(m.getT1().getName(), m.getT2().getName());
			Assert.assertTrue((id(m.getT1()) < teams.size() / 2 && id(m.getT2()) < teams.size() / 2) ||
									  (id(m.getT1()) >= teams.size() / 2 && id(m.getT2()) >= teams.size() / 2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(2, lc[i]);
			Assert.assertEquals(2, rc[i]);
		}
	}
}
