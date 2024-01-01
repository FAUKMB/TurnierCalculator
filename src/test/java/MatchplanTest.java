import matchplan.AbstractMatchplan;
import matchplan.MatchplanSelector;
import org.testng.Assert;
import org.testng.annotations.Test;
import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;

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

	@Test
	public void matchtest3() {
		ArrayList<Team> teams = createTeams(3);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(3);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
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
		ArrayList<Team> teams = createTeams(4);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(4);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
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
		ArrayList<Team> teams = createTeams(5);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(5);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.ROUND_ROBIN);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for (int i = 0; i < teams.size(); i++) {
			Assert.assertEquals(lc[i], 2);
			Assert.assertEquals(rc[i], 2);
		}
		configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(5);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN);
		matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		matches = matchplan.loadGroupstage();
		lc = new int[teams.size()];
		rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
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
		ArrayList<Team> teams = createTeams(6);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(6);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
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
		ArrayList<Team> teams = createTeams(7);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(7);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.ROUND_ROBIN);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
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
		configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(10);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP);
		matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		matches = matchplan.loadGroupstage();
		lc = new int[teams.size()];
		rc = new int[teams.size()];
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			Assert.assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
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
		ArrayList<Team> teams = createTeams(8);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(8);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
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
		ArrayList<Team> teams = createTeams(9);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(9);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
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
		ArrayList<Team> teams = createTeams(10);
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setNumberOfTeams(10);
		configuration.setNumberOfFields(2);
		configuration.setPlaytype(TurnierConfiguration.PLAYTYPE.KNOCKOUT);
		AbstractMatchplan matchplan = MatchplanSelector.createMatchplan(teams, configuration);
		ArrayList<Match> matches = matchplan.loadGroupstage();
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
