package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMatchplan {
	protected List<Team> teams;
	protected TurnierConfiguration configuration;
	protected ArrayList<Match> knockoutStageMatches;

	public AbstractMatchplan(List<Team> teams, TurnierConfiguration configuration) {
		this.configuration = configuration;
		this.teams = teams;
	}

	public abstract ArrayList<Match> loadGroupstage();

	public ArrayList<Match> loadKnockoutStage() {
		if (!configuration.isKnockout()) {
			return null;
		} else {
			knockoutStageMatches = knockoutMatches();
			return knockoutStageMatches;
		}
	}

	protected abstract ArrayList<Match> knockoutMatches();

	public void updateKnockoutMatches() {
		Team thirdFirst = null;
		Team thirdSecond = null;
		Team finalFirst = null;
		Team finalSecond = null;

		if (knockoutStageMatches == null) {
			return;
		}

		for (Match m : knockoutStageMatches) {
			if (m.isSemi()) {
				if (thirdFirst == null) {
					thirdFirst = m.looser();
					finalFirst = m.winner();
				} else {
					thirdSecond = m.looser();
					finalSecond = m.winner();
				}
			}
			if (m.getTYPE() == Match.TYPE.THIRD) {
				m.setT1(thirdFirst);
				m.setT2(thirdSecond);
			}
			if (m.getTYPE() == Match.TYPE.FINAL) {
				m.setT1(finalFirst);
				m.setT2(finalSecond);
			}
		}
	}

	protected ArrayList<Match> group5(List<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(4), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(4), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(4), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(4), field, Match.TYPE.GROUP, group));
		return res;
	}

	protected ArrayList<Match> group5(List<Team> teams, int field) {
		return group5(teams, field, null);
	}

	protected ArrayList<Match> group5reverse(List<Team> teams, int field) {
		return group5reverse(teams, field, null);
	}

	protected ArrayList<Match> group5reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = group5(teams, field, group);
		reverseMatches(res);
		return res;
	}

	protected ArrayList<Match> group5mixed(List<Team> teams) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(2), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(0), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(1), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(4), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(0), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(3), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(2), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(3), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(4), 2, Match.TYPE.GROUP));
		return res;
	}

	protected ArrayList<Match> group5mixedreverse(List<Team> teams) {
		ArrayList<Match> res = group5mixed(teams);
		reverseMatches(res);
		return res;
	}

	protected void reverseMatches(ArrayList<Match> matches) {
		for (Match m : matches) {
			Team t1 = m.getT1();
			m.setT1(m.getT2());
			m.setT2(t1);
		}
	}

	protected ArrayList<Match> group4(List<Team> teams, int field) {
		return group4(teams, field, null);
	}

	protected ArrayList<Match> group4(List<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), field, Match.TYPE.GROUP, group));
		return res;
	}

	protected ArrayList<Match> group4mixed(List<Team> teams, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), 2, Match.TYPE.GROUP, group));
		return res;
	}

	protected ArrayList<Match> group4mixedreverse(List<Team> teams, String group) {
		ArrayList<Match> res = group4mixed(teams, group);
		reverseMatches(res);
		return res;
	}

	protected ArrayList<Match> group4reverse(List<Team> teams, int field) {
		return group4reverse(teams, field, null);
	}

	protected ArrayList<Match> group4reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(1), field, Match.TYPE.GROUP, group));
		return res;
	}

	protected ArrayList<Match> group3(List<Team> teams, int field, Match.TYPE type) {
		return group3(teams, field, type, null);
	}

	protected ArrayList<Match> group3(List<Team> teams, int field, Match.TYPE type, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, type, group));
		res.add(new Match(teams.get(1), teams.get(2), field, type, group));
		res.add(new Match(teams.get(2), teams.get(0), field, type, group));
		return res;
	}

	protected ArrayList<Match> group3reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = group3(teams, field, Match.TYPE.GROUP, group);
		reverseMatches(res);
		return res;
	}

	protected ArrayList<Match> group3reverse(List<Team> teams, int field) {
		return group3reverse(teams, field, null);
	}

}
