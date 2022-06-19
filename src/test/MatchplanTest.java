package test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import turnier.Match;
import turnier.Matchplan;
import turnier.Team;


public class MatchplanTest {
	
	int id(Team t) {
		if(t.getName().equals("a")) {
			return 0;
		}
		if(t.getName().equals("b")) {
			return 1;
		}
		if(t.getName().equals("c")) {
			return 2;
		}
		if(t.getName().equals("d")) {
			return 3;
		}
		if(t.getName().equals("e")) {
			return 4;
		}
		if(t.getName().equals("f")) {
			return 5;
		}
		if(t.getName().equals("g")) {
			return 6;
		}
		if(t.getName().equals("h")) {
			return 7;
		}
		if(t.getName().equals("i")) {
			return 8;
		}
		if(t.getName().equals("j")) {
			return 9;
		}
		return -1;
	}
	
	@Test
	public void matchtest3() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 2);
			assertEquals(rc[i], 2);
		}
	}
	@Test
	public void matchtest4() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 3);
			assertEquals(rc[i], 3);
		}
		
	}
	@Test
	public void matchtest5() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 1);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 2);
			assertEquals(rc[i], 2);
		}
		matches = Matchplan.loadGroupstage(2, teams, 2);
		lc = new int[teams.size()];
		rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 4);
			assertEquals(rc[i], 4);
		}
	}
	@Test
	public void matchtest6() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		teams.add(new Team("f"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			assertTrue((id(m.getT1()) < teams.size()/2 && id(m.getT2()) < teams.size()/2) || 
					(id(m.getT1()) >= teams.size()/2 && id(m.getT2()) >= teams.size()/2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 2);
			assertEquals(rc[i], 2);
		}
	}
	@Test
	public void matchtest7() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		teams.add(new Team("f"));
		teams.add(new Team("g"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 3);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i], 3);
			assertEquals(rc[i], 3);
		}
		matches = Matchplan.loadGroupstage(2, teams, 2);
		lc = new int[teams.size()];
		rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			assertTrue((id(m.getT1()) <= teams.size()/2 && id(m.getT2()) <= teams.size()/2) || 
					(id(m.getT1()) > teams.size()/2 && id(m.getT2()) > teams.size()/2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 4; i < teams.size(); i++) {
			assertEquals(lc[i], 2);
			assertEquals(rc[i], 2);
		}
		for(int i = 0; i < 4; i++) {
			assertEquals(lc[i] + rc[i], 3);
			assertTrue(lc[i] >= 1);
			assertTrue(rc[i] >= 1);
		}
	}
	@Test
	public void matchtest8() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		teams.add(new Team("f"));
		teams.add(new Team("g"));
		teams.add(new Team("h"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			assertTrue((id(m.getT1()) < teams.size()/2 && id(m.getT2()) < teams.size()/2) || 
					(id(m.getT1()) >= teams.size()/2 && id(m.getT2()) >= teams.size()/2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertEquals(lc[i] + rc[i], 3);
			assertTrue(lc[i] >= 1);
			assertTrue(rc[i] >= 1);
		}
	}
	@Test
	public void matchtest9() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		teams.add(new Team("f"));
		teams.add(new Team("g"));
		teams.add(new Team("h"));
		teams.add(new Team("i"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			assertTrue((id(m.getT1()) <= teams.size()/2 && id(m.getT2()) <= teams.size()/2) || 
					(id(m.getT1()) > teams.size()/2 && id(m.getT2()) > teams.size()/2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 5; i < teams.size(); i++) {
			assertEquals(lc[i] + rc[i], 3);
			assertTrue(lc[i] >= 1);
			assertTrue(rc[i] >= 1);
		}
		for(int i = 0; i < 5; i++) {
			assertTrue(lc[i] == 2);
			assertTrue(rc[i] == 2);
		}
	}
	@Test
	public void matchtest10() {
		ArrayList<Team> teams = new ArrayList<>();
		teams.add(new Team("a"));
		teams.add(new Team("b"));
		teams.add(new Team("c"));
		teams.add(new Team("d"));
		teams.add(new Team("e"));
		teams.add(new Team("f"));
		teams.add(new Team("g"));
		teams.add(new Team("h"));
		teams.add(new Team("i"));
		teams.add(new Team("j"));
		ArrayList<Match> matches = Matchplan.loadGroupstage(2, teams, 0);
		int[] lc = new int[teams.size()];
		int[] rc = new int[teams.size()];
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			assertNotEquals(matches.get(i).getT1().getName(), matches.get(i).getT2().getName());
			assertTrue((id(m.getT1()) < teams.size()/2 && id(m.getT2()) < teams.size()/2) || 
					(id(m.getT1()) >= teams.size()/2 && id(m.getT2()) >= teams.size()/2));
			lc[id(m.getT1())]++;
			rc[id(m.getT2())]++;
		}
		for(int i = 0; i < teams.size(); i++) {
			assertTrue(lc[i] == 2);
			assertTrue(rc[i] == 2);
		}
		
	}
	
}
