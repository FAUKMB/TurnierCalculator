package turnier;

import java.util.Comparator;

public class TeamComparator implements Comparator<Team>{
	@Override public int compare(Team t1, Team t2){
		if(t1.getPosition() != t2.getPosition()) {
			return Integer.compare(t1.getPosition(), t2.getPosition());
		}
		if(t1.getPoints()!= t2.getPoints()){
			return Integer.compare( t2.getPoints(), t1.getPoints());
		}else if(t1.getGoals() - t1.getMinusgoals() != t2.getGoals() - t2.getMinusgoals()) {
			return Integer.compare( t2.getGoals() - t2.getMinusgoals(), t1.getGoals() - t1.getMinusgoals());
		}else{
			return Integer.compare( t2.getGoals() , t1.getGoals());
		}
	}
}