public class TableTest {
	/*
	
	ArrayList<Team> createTeams(int numberOfTeams){
		ArrayList<Team> teams = new ArrayList<>();
		char c = 'a';
		for(int i = 0; i < numberOfTeams; i++) {
			String name = "";
			for(int j = 0; j < i+1; j++) {
				name += (char)(c+i);
			}
			teams.add(new Team(name));
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
	
	private void test81(ArrayList<Team> groupA, ArrayList<Team> groupB) {
		assertEquals(groupA.get(0).getName(), "a");
		assertEquals(groupA.get(1).getName(), "bb");
		assertEquals(groupA.get(2).getName(), "ccc");
		assertEquals(groupA.get(3).getName(), "dddd");
		assertEquals(groupB.get(0).getName(), "hhhhhhhh");
		assertEquals(groupB.get(1).getName(), "eeeee");
		assertEquals(groupB.get(2).getName(), "ffffff");
		assertEquals(groupB.get(3).getName(), "ggggggg");
		
	}

	@Test
	public void test8() {
		ArrayList<Team> teams = createTeams(8);
		ArrayList<Team> groupA = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			groupA.add(teams.get(i));
		}
		ArrayList<Team> groupB = new ArrayList<>();
		for(int i = 4; i < 8; i++) {
			groupB.add(teams.get(i));
		}
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		result81(matches);
		TableCalculator.calcTable(matches, groupA, true);
		TableCalculator.calcTable(matches, groupB, true);
		test81(groupA, groupB);
		ArrayList<Match> k = Matchplan.knockout(groupA, groupB, 2, true);
		assertEquals(k.get(0).getT1().getName(), "a");
		assertEquals(k.get(0).getT2().getName(), "eeeee");
		assertEquals(k.get(1).getT1().getName(), "hhhhhhhh");
		assertEquals(k.get(1).getT2().getName(), "bb");
		assertEquals(k.get(2).getT1().getName(), "ccc");
		assertEquals(k.get(2).getT2().getName(), "ffffff");
		assertEquals(k.get(3).getT1().getName(), "ggggggg");
		assertEquals(k.get(3).getT2().getName(), "dddd");
	}
	
	@Test
	public void test9() {
		ArrayList<Team> teams = createTeams(9);
		ArrayList<Team> groupA = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			groupA.add(teams.get(i));
		}
		ArrayList<Team> groupB = new ArrayList<>();
		for(int i = 5; i < 8; i++) {
			groupB.add(teams.get(i));
		}
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		
	}
*/
}
