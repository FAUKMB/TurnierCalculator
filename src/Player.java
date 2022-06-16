
//Player for the programm
public class Player{
	private String name;
	private int number;
	private String team;
	private int goals;
	
	// Konstruktor fuer einen Spieler
	public Player(String name, int number, String team, int goals){
		this.name = name;
		this.number = number;
		this.team = team;
		this.goals = goals;
	}
	
	//Getter und Setter
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public String getTeam(){
		return team;
	}
	
	public void setTeam(String team){
		this.team = team;
	}
	
	public int getGoals(){
		return goals;
	}
	
	public void setGoals(int goals){
		this.goals = goals;
	}
	
	public String toString(){
		return number + "  " + name + "  " + goals;
	}
}