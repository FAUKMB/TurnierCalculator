package Util;

import turnier.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player>{
	@Override public int compare(Player s1, Player s2){
		return Integer.compare(s2.getGoals(), s1.getGoals());
	}
}