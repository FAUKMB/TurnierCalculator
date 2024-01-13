package turnier;

import lombok.Getter;

//Player for the programm
@Getter
public class Player {
	//Getter und Setter
	private String name;
	private int number;
	private String team;
	private int goals;

	// Konstruktor fuer einen Spieler
	public Player(String name, int number, String team, int goals) {
		this.name = name;
		this.number = number;
		this.team = team;
		this.goals = goals;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public String toString() {
		return number + "  " + name + "  " + goals;
	}
}