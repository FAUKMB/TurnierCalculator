package matchplan;

import turnier.Match;
import turnier.Team;
import turnier.TurnierConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MatchplanCreator {

	public static Matchplan selectMatchplan(TurnierConfiguration configuration) {
		int numberOfTeams = configuration.getTeams().size();
		return switch (numberOfTeams) {
			case 3 -> Matchplan.MATCHPLAN3;
			case 4 -> Matchplan.MATCHPLAN4;
			case 5 -> Matchplan.MATCHPLAN5;
			case 6 -> Matchplan.MATCHPLAN6;
			case 7 -> Matchplan.MATCHPLAN7;
			case 8 -> Matchplan.MATCHPLAN8;
			case 9 -> Matchplan.MATCHPLAN9;
			case 10 -> Matchplan.MATCHPLAN10;
			default -> throw new IllegalStateException();
		};
	}

	private static ArrayList<Match> group5(List<Team> teams, int field, String group) {
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

	private static ArrayList<Match> group5(List<Team> teams, int field) {
		return group5(teams, field, null);
	}

	private static ArrayList<Match> group5reverse(List<Team> teams, int field) {
		return group5reverse(teams, field, null);
	}

	private static ArrayList<Match> group5reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = group5(teams, field, group);
		reverseMatches(res);
		return res;
	}

	private static ArrayList<Match> group5mixed(List<Team> teams) {
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

	private static ArrayList<Match> group5mixedreverse(List<Team> teams) {
		ArrayList<Match> res = group5mixed(teams);
		reverseMatches(res);
		return res;
	}

	private static void reverseMatches(ArrayList<Match> matches) {
		for (Match m : matches) {
			Team t1 = m.getT1();
			m.setT1(m.getT2());
			m.setT2(t1);
		}
	}

	private static ArrayList<Match> group4(List<Team> teams, int field) {
		return group4(teams, field, null);
	}

	private static ArrayList<Match> group4(List<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), field, Match.TYPE.GROUP, group));
		return res;
	}

	private static ArrayList<Match> group4mixed(List<Team> teams, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), 2, Match.TYPE.GROUP, group));
		return res;
	}

	private static ArrayList<Match> group4mixedreverse(List<Team> teams, String group) {
		ArrayList<Match> res = group4mixed(teams, group);
		reverseMatches(res);
		return res;
	}

	private static ArrayList<Match> group4reverse(List<Team> teams, int field) {
		return group4reverse(teams, field, null);
	}

	private static ArrayList<Match> group4reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(1), field, Match.TYPE.GROUP, group));
		return res;
	}

	private static ArrayList<Match> group3(List<Team> teams, int field, Match.TYPE type) {
		return group3(teams, field, type, null);
	}

	private static ArrayList<Match> group3(List<Team> teams, int field, Match.TYPE type, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, type, group));
		res.add(new Match(teams.get(1), teams.get(2), field, type, group));
		res.add(new Match(teams.get(2), teams.get(0), field, type, group));
		return res;
	}

	private static ArrayList<Match> group3reverse(List<Team> teams, int field, String group) {
		ArrayList<Match> res = group3(teams, field, Match.TYPE.GROUP, group);
		reverseMatches(res);
		return res;
	}

	private static ArrayList<Match> group3reverse(List<Team> teams, int field) {
		return group3reverse(teams, field, null);
	}


	protected static List<Match> loadGroupstage3(TurnierConfiguration configuration) {
		ArrayList<Match> res = group3(configuration.getTeams(), 1, Match.TYPE.GROUP);
		res.addAll(group3reverse(configuration.getTeams(), 1));
		return res;
	}

	protected static List<Match> loadGroupstage4(TurnierConfiguration configuration) {
		ArrayList<Match> res;
		List<Team> teams = configuration.getTeams();
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

	protected static List<Match> loadGroupstage5(TurnierConfiguration configuration) {
		ArrayList<Match> res;
		List<Team> teams = configuration.getTeams();
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

	protected static List<Match> loadGroupstage6(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res;
		if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.ROUND_ROBIN) {
			res = group6(configuration);
		} else {
			ArrayList<Match> tmp = group3(teams.subList(0, 5), 1, Match.TYPE.GROUP, "A");
			res = new ArrayList<>();
			ArrayList<Match> res2;
			res2 = group3(teams.subList(3, 6), configuration.getNumberOfFields(), Match.TYPE.GROUP, "B");
			for (int i = 0; i < tmp.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
			tmp = group3reverse(teams.subList(0, 3), 1, "A");
			res2 = group3reverse(teams.subList(3, 6), configuration.getNumberOfFields(), "B");
			for (int i = 0; i < tmp.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
		}
		return res;
	}

	private static ArrayList<Match> group6(TurnierConfiguration configuration) {
		ArrayList<Match> res = new ArrayList<>();
		int numberOfFields = configuration.getNumberOfFields();
		List<Team> teams = configuration.getTeams();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(0), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(1), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(4), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(0), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(5), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(4), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(5), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(1), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(3), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(2), res.size() % numberOfFields + 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(5), res.size() % numberOfFields + 1, Match.TYPE.GROUP));

		return res;
	}

	protected static List<Match> loadKnockoutStage6(TurnierConfiguration configuration) {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> teams = configuration.getTeams();
		List<Team> groupA = teams.subList(0, 3);
		List<Team> groupB = teams.subList(3, 6);
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
		}
		res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}

	protected static List<Match> loadGroupstage7(TurnierConfiguration configuration) {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> teams = configuration.getTeams();
		if (configuration.getPlaytype() != TurnierConfiguration.PLAYTYPE.ROUND_ROBIN) {
			ArrayList<Match> tmp = group4(teams.subList(0, 4), 1, "A");
			ArrayList<Match> res2;

			res2 = group3(teams.subList(4, 7), configuration.getNumberOfFields(), Match.TYPE.GROUP, "B");
			if (configuration.getPlaytype() == TurnierConfiguration.PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP) {
				res2.addAll(group3reverse(teams.subList(4, 7), configuration.getNumberOfFields(), "B"));
				for (int i = 0; i < res2.size(); i++) {
					res.add(tmp.get(i));
					res.add(res2.get(i));
				}
			} else {
				for (int i = 0; i < res2.size(); i++) {
					res.add(tmp.get(i * 2));
					res.add(tmp.get(i * 2 + 1));
					res.add(res2.get(i));
				}
			}

		} else {
			res = group7(teams, configuration.getNumberOfFields());
		}
		return res;
	}

	private static ArrayList<Match> group7(List<Team> teams, int numberOfFields) {
		ArrayList<Match> res = new ArrayList<>();
		Match.TYPE type = Match.TYPE.GROUP;
		res.add(new Match(teams.get(0), teams.get(1), 1, type));
		res.add(new Match(teams.get(2), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(2), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(0), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(1), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(5), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(3), teams.get(1), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(2), teams.get(0), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(3), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(4), teams.get(6), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(0), teams.get(4), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(5), teams.get(2), res.size() % numberOfFields + 1, type));
		res.add(new Match(teams.get(6), teams.get(1), res.size() % numberOfFields + 1, type));
		return res;
	}

	protected static List<Match> loadKnockoutStage7(TurnierConfiguration configuration) {
		ArrayList<Match> res = new ArrayList<>();
		List<Team> teams = configuration.getTeams();
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 7);
		res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(groupB.get(0), groupA.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(0), groupB.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
		}
		res.add(new Match(groupB.get(2), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
		} else {
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
		}
		res.add(new Match(groupA.get(3), groupA.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupB.get(0), groupA.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}

	protected static List<Match> loadGroupstage8(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res2;
		ArrayList<Match> tmp;
		ArrayList<Match> res = new ArrayList<>();
		if (configuration.getNumberOfFields() == 2) {
			tmp = group4mixed(teams.subList(0, 4), "A");
			res2 = group4mixed(teams.subList(4, 8), "B");
		} else {
			tmp = group4(teams.subList(0, 4), 1, "A");
			res2 = group4(teams.subList(4, 8), 1, "B");
		}
		for (int i = 0; i < res2.size(); i += 2) {
			res.add(tmp.get(i));
			res.add(tmp.get(i + 1));
			res.add(res2.get(i));
			res.add(res2.get(i + 1));
		}
		return res;
	}

	static List<Match> loadKnockoutStage8(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 4);
		List<Team> groupB = teams.subList(4, 8);
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(3), groupA.get(3), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(null, null, 2, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(3), groupB.get(3), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(2), groupA.get(2), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(0), groupA.get(0), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
		}
		return res;
	}

	static List<Match> loadGroupstage9(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res = new ArrayList<>();
		ArrayList<Match> tmp = group5(teams.subList(0, 5), 1, "A");
		ArrayList<Match> res2;
		res2 = group4(teams.subList(5, 9), configuration.getNumberOfFields(), "B");
		for (int i = 0; i < res2.size(); i++) {
			res.add(tmp.get(i));
			res.add(res2.get(i));
		}
		for (int i = 6; i < tmp.size(); i++) {
			res.add(tmp.get(i));
		}
		return res;
	}

	static List<Match> loadKnockoutStage9(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 5);
		List<Team> groupB = teams.subList(5, 9);
		res.add(new Match(groupB.get(3), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(2), groupB.get(2), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(groupB.get(1), groupA.get(1), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}

	static List<Match> loadGroupstage10(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res = new ArrayList<>();
		ArrayList<Match> tmp = group5(teams.subList(0, 5), 1, "A");
		ArrayList<Match> res2;
		res2 = group5(teams.subList(5, 10), configuration.getNumberOfFields(), "B");
		for (int i = 0; i < res2.size(); i++) {
			res.add(tmp.get(i));
			res.add(res2.get(i));
		}
		return res;
	}

	static List<Match> loadKnockoutStage10(TurnierConfiguration configuration) {
		List<Team> teams = configuration.getTeams();
		ArrayList<Match> res = new ArrayList<>();
		List<Team> groupA = teams.subList(0, 5);
		List<Team> groupB = teams.subList(5, 10);
		if (configuration.isHasSemi()) {
			res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
			res.add(new Match(groupB.get(0), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.SEMIFINAL));
			res.add(new Match(groupA.get(4), groupB.get(4), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(3), groupA.get(3), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			res.add(new Match(groupA.get(2), groupB.get(2), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(null, null, 1, Match.TYPE.FINAL));
		} else {
			res.add(new Match(groupA.get(4), groupB.get(4), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(3), groupA.get(3), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			res.add(new Match(groupB.get(1), groupA.get(1), configuration.getNumberOfFields(), Match.TYPE.PLACEMENT));
			res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
		}
		return res;
	}

}
