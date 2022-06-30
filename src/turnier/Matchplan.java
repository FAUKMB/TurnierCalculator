package turnier;
import java.util.ArrayList;

public class Matchplan {

	static ArrayList<Team> subList(ArrayList<Team> teams, int from , int to){
		ArrayList<Team> res = new ArrayList<>();
		for(int i = from; i < to; i++) {
			res.add(teams.get(i));
		}
		return res;
	}

	public static ArrayList<Match> loadGroupstage(int numberOfFields, ArrayList<Team> teams, int option){
		ArrayList<Match> res = new ArrayList<>();
		if(teams.size() == 3) {
			res = group3(teams, 1, Match.TYPE.GROUP);
			res.addAll(group3reverse(teams, 1));
		} else if(teams.size() == 4) {
			if(numberOfFields == 2) {
				res = group4mixed(teams, null);
				res.addAll(group4mixedreverse(teams, null));
			}else {
				res = group4(teams, 1);
				res.addAll(group4reverse(teams, 1));
			}
			Match first = res.get(0);
			res.set(0, res.get(1));
			res.set(1, first);
		} else if(teams.size() == 5) {
			if(numberOfFields == 2) {
				res = group5mixed(teams);
				if(option == 2) {
					res.addAll(group5mixedreverse(teams));
				}
			} else {
				res = group5(teams, 1);
				if(option == 2) {
					res.addAll(group5reverse(teams, numberOfFields));
				}
			}


		} else if(teams.size() == 6) {
			if(option == 1) {
				res = group6(teams, numberOfFields, Match.TYPE.GROUP);
			}else {
				ArrayList<Match> tmp = group3(subList(teams, 0,3), 1, Match.TYPE.GROUP, "A");
				ArrayList<Match> res2;
				res2 = group3(subList(teams, 3, 6), numberOfFields, Match.TYPE.GROUP, "B");
				for(int i = 0; i< tmp.size(); i++) {
					res.add(tmp.get(i));
					res.add(res2.get(i));
				}
				tmp = group3reverse(subList(teams, 0,3), 1, "A");
				res2 = group3reverse(subList(teams, 3, 6), numberOfFields, "B");
				for(int i = 0; i< tmp.size(); i++) {
					res.add(tmp.get(i));
					res.add(res2.get(i));
				}
			}
		} else if(teams.size() == 7) {
			if(option != 3) {
				ArrayList<Match> tmp = group4(subList(teams, 0,4), 1, "A");
				ArrayList<Match> res2;

				res2 = group3(subList(teams, 4, 7), numberOfFields, Match.TYPE.GROUP, "B");
				if(option == 2) {
					res2.addAll(group3reverse(subList(teams, 4, 7), numberOfFields, "B"));
				}
				for(int i = 0; i< res2.size(); i++) {
					res.add(tmp.get(i));
					res.add(res2.get(i));
				}
				for(int i = res2.size(); i < tmp.size(); i++) {
					res.add(tmp.get(i));
				}
			}else {
				res = group7(teams, numberOfFields, Match.TYPE.GROUP);
			}

		} else if (teams.size() == 8) {
			ArrayList<Match> res2;
			ArrayList<Match> tmp;
			if(numberOfFields == 2) {
				tmp = group4mixed(subList(teams, 0, 4), "A");
				res2 = group4mixed(subList(teams, 4,8), "B");
			}else {
				tmp = group4(subList(teams, 0, 4), 1, "A");
				res2 = group4(subList(teams, 4, 8), 1, "B");
			}
			for(int i = 0; i< res2.size(); i+=2) {
				res.add(tmp.get(i));
				res.add(tmp.get(i+1));
				res.add(res2.get(i));
				res.add(res2.get(i+1));
			}
		} else if (teams.size() == 9) {
			ArrayList<Match> tmp = group5(subList(teams, 0, 5), 1, "A");
			ArrayList<Match> res2;
			res2 = group4(subList(teams, 5, 9), numberOfFields, "B");
			for(int i = 0; i< res2.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
			for(int i = 6; i < tmp.size(); i++) {
				res.add(tmp.get(i));
			}
		} else {
			ArrayList<Match> tmp = group5(subList(teams, 0, 5), 1, "A");
			ArrayList<Match> res2;
			res2 = group5(subList(teams, 5, 10), numberOfFields, "B");
			for(int i = 0; i< res2.size(); i++) {
				res.add(tmp.get(i));
				res.add(res2.get(i));
			}
		}
		return res;
	}

	private static ArrayList<Match> group6(ArrayList<Team> teams, int numberOfFields, Match.TYPE type) {
		ArrayList<Match> res = new ArrayList<>();
		if(numberOfFields == 2) {
			res.add(new Match(teams.get(0), teams.get(1), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(2), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(0), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(1), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(3), teams.get(4), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(1), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(3), teams.get(0), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(0), teams.get(5), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(2), teams.get(4), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(1), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(1), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(0), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(5), res.size() % 2 + 1, type));
		}else {
			res.add(new Match(teams.get(0), teams.get(1), 1, type));
			res.add(new Match(teams.get(2), teams.get(3), 1, type));
			res.add(new Match(teams.get(4), teams.get(0), 1, type));
			res.add(new Match(teams.get(5), teams.get(2), 1, type));
			res.add(new Match(teams.get(5), teams.get(1), 1, type));
			res.add(new Match(teams.get(3), teams.get(4), 1, type));
			res.add(new Match(teams.get(1), teams.get(2), 1, type));
			res.add(new Match(teams.get(3), teams.get(0), 1, type));
			res.add(new Match(teams.get(0), teams.get(5), 1, type));
			res.add(new Match(teams.get(2), teams.get(4), 1, type));
			res.add(new Match(teams.get(5), teams.get(3), 1, type));
			res.add(new Match(teams.get(4), teams.get(1), 1, type));
			res.add(new Match(teams.get(1), teams.get(3), 1, type));
			res.add(new Match(teams.get(0), teams.get(2), 1, type));
			res.add(new Match(teams.get(4), teams.get(5), 1, type));
		}
		return res;
	}

	private static ArrayList<Match> group7(ArrayList<Team> teams, int numberOfFields, Match.TYPE type) {
		ArrayList<Match> res = new ArrayList<>();
		if(numberOfFields == 2) {
			res.add(new Match(teams.get(0), teams.get(1), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(2), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(5), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(6), teams.get(0), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(1), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(3), teams.get(4), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(0), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(3), teams.get(6), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(1), teams.get(5), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(2), teams.get(6), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(0), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(1), teams.get(4), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(6), teams.get(5), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(3), teams.get(1), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(2), teams.get(0), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(3), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(4), teams.get(6), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(0), teams.get(4), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(5), teams.get(2), res.size() % 2 + 1, type));
			res.add(new Match(teams.get(6), teams.get(1), res.size() % 2 + 1, type));
		}else {
			res.add(new Match(teams.get(0), teams.get(1), 1, type));
			res.add(new Match(teams.get(2), teams.get(3), 1, type));
			res.add(new Match(teams.get(4), teams.get(5), 1, type));
			res.add(new Match(teams.get(6), teams.get(0), 1, type));
			res.add(new Match(teams.get(1), teams.get(2), 1, type));
			res.add(new Match(teams.get(3), teams.get(4), 1, type));
			res.add(new Match(teams.get(5), teams.get(0), 1, type));
			res.add(new Match(teams.get(3), teams.get(6), 1, type));
			res.add(new Match(teams.get(1), teams.get(5), 1, type));
			res.add(new Match(teams.get(4), teams.get(2), 1, type));
			res.add(new Match(teams.get(2), teams.get(6), 1, type));
			res.add(new Match(teams.get(0), teams.get(3), 1, type));
			res.add(new Match(teams.get(1), teams.get(4), 1, type));
			res.add(new Match(teams.get(6), teams.get(5), 1, type));
			res.add(new Match(teams.get(3), teams.get(1), 1, type));
			res.add(new Match(teams.get(2), teams.get(0), 1, type));
			res.add(new Match(teams.get(5), teams.get(3), 1, type));
			res.add(new Match(teams.get(4), teams.get(6), 1, type));
			res.add(new Match(teams.get(0), teams.get(4), 1, type));
			res.add(new Match(teams.get(5), teams.get(2), 1, type));
			res.add(new Match(teams.get(6), teams.get(1), 1, type));
		}
		return res;
	}

	static ArrayList<Match> group5mixed(ArrayList<Team> teams) {
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

	static ArrayList<Match> group5mixedreverse(ArrayList<Team> teams) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(3), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(4), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(1), teams.get(2), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(3), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(0), teams.get(2), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(1), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(2), teams.get(4), 2, Match.TYPE.GROUP));
		res.add(new Match(teams.get(3), teams.get(0), 1, Match.TYPE.GROUP));
		res.add(new Match(teams.get(4), teams.get(1), 2, Match.TYPE.GROUP));
		return res;
	}

	static ArrayList<Match> group5(ArrayList<Team> teams, int field, String group) {
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
	static ArrayList<Match> group5(ArrayList<Team> teams, int field) {
		return group5(teams, field, null);
	}

	static ArrayList<Match> group5reverse(ArrayList<Team> teams, int field) {
		return group5reverse(teams, field, null);
	}
	static ArrayList<Match> group5reverse(ArrayList<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(4), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(4), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(4), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(4), teams.get(1), field, Match.TYPE.GROUP, group));
		return res;
	}

	static ArrayList<Match> group4(ArrayList<Team> teams, int field) {
		return group4(teams, field, null);
	}

	static ArrayList<Match> group4(ArrayList<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), field, Match.TYPE.GROUP, group));
		return res;
	}

	static ArrayList<Match> group4mixed(ArrayList<Team> teams, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(3), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(2), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(1), teams.get(3), 2, Match.TYPE.GROUP, group));
		return res;
	}
	static ArrayList<Match> group4mixedreverse(ArrayList<Team> teams, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(2), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(3), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), 2, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), 1, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(1), 2, Match.TYPE.GROUP, group));
		return res;
	}

	static ArrayList<Match> group4reverse(ArrayList<Team> teams, int field){
		return group4reverse(teams, field, null);
	}

	static ArrayList<Match> group4reverse(ArrayList<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(3), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(3), teams.get(1), field, Match.TYPE.GROUP, group));
		return res;
	}

	static ArrayList<Match> group3(ArrayList<Team> teams, int field, Match.TYPE type){
		return group3(teams, field, type, null);
	}

	static ArrayList<Match> group3(ArrayList<Team> teams, int field, Match.TYPE type, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(0), teams.get(1), field, type, group));
		res.add(new Match(teams.get(1), teams.get(2), field, type, group));
		res.add(new Match(teams.get(2), teams.get(0), field, type, group));
		return res;
	}

	static ArrayList<Match> group3reverse(ArrayList<Team> teams, int field, String group) {
		ArrayList<Match> res = new ArrayList<>();
		res.add(new Match(teams.get(1), teams.get(0), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(2), teams.get(1), field, Match.TYPE.GROUP, group));
		res.add(new Match(teams.get(0), teams.get(2), field, Match.TYPE.GROUP, group));
		return res;
	}

	static ArrayList<Match> group3reverse(ArrayList<Team> teams, int field) {
		return group3reverse(teams, field, null);
	}

	public static ArrayList<Match> knockout(ArrayList<Team> groupA, ArrayList<Team> groupB, int numberFields, boolean semi) {
		ArrayList<Match> res = new ArrayList<>();
		int numberOfTeams = groupA.size() + groupB.size();
		if(numberOfTeams == 6) {
			if(semi) {
				res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupB.get(0), groupA.get(1), numberFields, Match.TYPE.SEMIFINAL));
			}
			res.add(new Match (groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
			if(semi) {
				res.add(new Match(null, null, 1, Match.TYPE.THIRD));
				res.add(new Match(null, null, 1, Match.TYPE.FINAL));
			}else {
				res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
			}
		}else if (numberOfTeams == 7){
			res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
			if(semi) {
				res.add(new Match(groupB.get(0), groupA.get(1), 1, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupA.get(0), groupB.get(1), numberFields, Match.TYPE.SEMIFINAL));
			}
			res.add(new Match(groupB.get(2), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			if(semi) {
				res.add(new Match(null, null, 1, Match.TYPE.THIRD));
			}else {
				res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
			}
			res.add(new Match(groupA.get(3), groupA.get(2), 1, Match.TYPE.GROUP_PLACEMENT));
			if(semi) {
				res.add(new Match(null, null, 1, Match.TYPE.FINAL));
			}else {
				res.add(new Match(groupB.get(0), groupA.get(0), 1, Match.TYPE.PLACEMENT));
			}
		}else if(numberOfTeams == 8) {
			if(semi) {
				res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupB.get(0), groupA.get(1), numberFields, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(3), groupA.get(3), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(null, null, 1, Match.TYPE.THIRD));
				res.add(new Match(null, null, 2, Match.TYPE.FINAL));
			}else {
				res.add(new Match(groupA.get(3), groupB.get(3), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(2), groupA.get(2), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(1), groupB.get(1), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(0), groupA.get(0), numberFields, Match.TYPE.PLACEMENT));
			}
		}else if(numberOfTeams == 9) {
			res.add(new Match(groupB.get(3), groupA.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
			if(semi) {
				res.add(new Match(groupA.get(2), groupB.get(2), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupB.get(0), groupA.get(1), numberFields, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
				res.add(new Match(null, null, 1, Match.TYPE.THIRD));
				res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
				res.add(new Match(null, null, 1, Match.TYPE.FINAL));
			}else {
				res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(4), groupB.get(3), 1, Match.TYPE.GROUP_PLACEMENT));
				res.add(new Match(groupB.get(1), groupA.get(1), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(3), groupA.get(4), 1, Match.TYPE.GROUP_PLACEMENT));
				res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
			}
		}else {
			if(semi) {
				res.add(new Match(groupA.get(0), groupB.get(1), 1, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupB.get(0), groupA.get(1), numberFields, Match.TYPE.SEMIFINAL));
				res.add(new Match(groupA.get(4), groupB.get(4), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(3), groupA.get(3), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(null, null, 1, Match.TYPE.THIRD));
				res.add(new Match(groupA.get(2), groupB.get(2), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(null, null, 1, Match.TYPE.FINAL));
			}else {
				res.add(new Match(groupA.get(4), groupB.get(4), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(3), groupA.get(3), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(2), groupB.get(2), 1, Match.TYPE.PLACEMENT));
				res.add(new Match(groupB.get(1), groupA.get(1), numberFields, Match.TYPE.PLACEMENT));
				res.add(new Match(groupA.get(0), groupB.get(0), 1, Match.TYPE.PLACEMENT));
			}
		}

		return res;
	}

	static void setFinals(Team fin1, Team fin2, Team third1, Team third2, ArrayList<Match> matches) {
		for(int i =0 ; i< matches.size(); i++) {
			if(matches.get(i).getTYPE() == Match.TYPE.THIRD) {
				matches.get(i).setT1(third1);
				matches.get(i).setT1(third2);
			}else if (matches.get(i).getTYPE() == Match.TYPE.FINAL){
				matches.get(i).setT1(fin1);
				matches.get(i).setT2(fin2);
			}
		}
	}

}
