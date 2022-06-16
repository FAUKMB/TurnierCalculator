//Team for the programm

public class Team{
	private String name;
	private int points;
	private int goals;
	private int minusgoals;
	private int position;
	
	public Team(String name){
			this.name = name;
			this.points = 0;
			this.goals = 0;
			this.minusgoals = 0;
			setPosition(-1);
	}
	
	//Getter and Setter
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public int getPoints(){
		return points;
	}
	public void setPoints(int points){
		this.points = points;
	}
	
	public int getGoals(){
		return goals;
	}
	public void setGoals(int goals){
		this.goals = goals;
	}
	
	public int getMinusgoals(){
		return minusgoals;
	}
	public void setMinusgoals(int minusgoals){
		this.minusgoals = minusgoals;
	}
	
	
	//format to print in file with tabstop 8
	public String toString(){
		String ret = name;
		for(int i = 0; i <= (36 - name.length())/8; i++){
			ret += "\t";
		}
		if(goals < 10) {
			ret += " ";
		}
		ret += goals + " : " + minusgoals + "\t" + points;
		return ret;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getNamePrint() {
		String ret = name;
		for(int i = 0; i <= (32 - name.length())/8; i++){
			ret += "\t";
		}
		return ret;
	}
}