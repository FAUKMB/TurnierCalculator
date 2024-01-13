package turnier;
//Team for the programm

import lombok.Getter;

@Getter
public class Team {
	//Getter and Setter
	private String name;
	private int points;
	private int goals;
	private int minusgoals;
	private int position;

	public Team(String name) {
		this.name = name;
		this.points = 0;
		this.goals = 0;
		this.minusgoals = 0;
		setPosition(-1);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setGoals(int goals) {
		this.goals = goals;
	}

	public void setMinusgoals(int minusgoals) {
		this.minusgoals = minusgoals;
	}


	//format to print in file with tabstop 8
	public String toString() {
		String ret = name;
		for (int i = name.length(); i < Turnier.maxNameLen; i++) {
			ret += " ";
		}
		ret += "   ";
		if (goals < 10) {
			ret += " ";
		}
		ret += goals + " : " + minusgoals + "   " + points;
		return ret;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getNamePrint() {
		String ret = name;
		for (int i = name.length(); i < Turnier.maxNameLen; i++) {
			ret += " ";
		}
		return ret;
	}
}