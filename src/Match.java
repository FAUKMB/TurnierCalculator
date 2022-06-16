public class Match {
	private static int idcounter = 0;
	private Team t1;
	private Team t2;
	private int goalT1 = -1;
	private int goalT2 = -1;
	private int field;
	private TYPE type;
	private int id;
	enum TYPE {
		SEMIFINAL,
		GROUP,
		PLACEMENT,
		GROUP_PLACEMENT,
		FINAL,
		THIRD
	}

	void addResult(int t1, int t2) {
		goalT1 = t1;
		goalT2 = t2;
		if(type == TYPE.SEMIFINAL) {
			winner().setPosition(1);
			looser().setPosition(3);
		}
	}

	void confirmResult() {
		if(type == TYPE.SEMIFINAL) {
			return;
		}
		getT1().setGoals(getT1().getGoals() + goalT1);
		getT2().setGoals(getT2().getGoals() + goalT2);
		getT1().setMinusgoals(getT1().getMinusgoals() + goalT2);
		getT2().setMinusgoals(getT2().getMinusgoals() + goalT1);
		if(goalT1 > goalT2) {
			getT1().setPoints(getT1().getPoints() + 3);
		} else if (goalT2 > goalT1) {
			getT2().setPoints(getT2().getPoints() + 3);
		} else {
			getT1().setPoints(getT1().getPoints() + 1);
			getT2().setPoints(getT2().getPoints() + 1);
		}

	}

	public Match(Team t1, Team t2, TYPE type) {
		this(t1, t2, 1, type);
	}

	public Match(Team t1, Team t2, int field, TYPE type) {
		this.setT1(t1);
		this.setT2(t2);
		this.field = field;
		this.type = type;
		id = idcounter++;
	}
	
	public int getId() {
		return id;
	}

	public String toString() {
		if(goalT1 == -1) {
			if(getT1() == null) {
				if(type == TYPE.FINAL) {
					return getType() + " Feld " + field + " Sieger hf1\t\t\t - \tSieger hf2";
				}else {
					return getType() + " Feld " + field +  " Verlierer hf1\t\t\t - \tVerlierer hf2";
				}
			}
			return getType() + " Feld " +  field + " " + getT1().getNamePrint() + " - \t" + getT2().getNamePrint();
		} else {
			return getType() + " Feld " +  field + " " + getT1().getNamePrint() + " - \t" + getT2().getNamePrint() + " " + goalT1 + " : " + goalT2;
		}
	}

	public String printNextGame() {
		if(getT1() == null) {
			if(type == TYPE.FINAL) {
				return "Sieger hf1 - Sieger hf2";
			}else {
				return "Verlierer hf1 - Verlierer hf2";
			}
		}else {
			return getT1().getName() + " - " + getT2().getName();
		}
	}

	public Team getT1() {
		return t1;
	}

	public void setT1(Team t1) {
		this.t1 = t1;
	}

	public Team getT2() {
		return t2;
	}

	public void setT2(Team t2) {
		this.t2 = t2;
	}

	public boolean played() {
		return goalT1 != -1;
	}

	public int getField() {
		// TODO Auto-generated method stub
		return field;
	}
	
	public int getGoal1() {
		return goalT1;
	}
	
	public int getGoal2() {
		return goalT2;
	}

	public String getType() {
		if(type == TYPE.GROUP) {
			return "Gruppenspiel\t\t";
		}else if(type == TYPE.SEMIFINAL) {
			return "Halbfinale\t\t";
		}else if(type == TYPE.GROUP_PLACEMENT){
			return "Platzierungsspiel\t";
		}else if(type == TYPE.FINAL){
			return "Finale\t\t\t";
		}else if(type == TYPE.THIRD) {
			return "Spiel um Platz 3\t";
		}else if(type == TYPE.PLACEMENT) {
			if(getT1() != null && getT1().getPosition() != 0) {
				return "Spiel um Platz " + getT1().getPosition()+"\t";
			}else {
				return "Platzierungsspiel\t";
			}
		}
		return null;
	}

	public boolean isSemi() {
		return type == TYPE.SEMIFINAL;
	}

	public Team winner() {
		if(goalT1 > goalT2) {
			return t1;
		}else {
			return t2;
		}
	}

	public Team looser() {
		if(goalT1 > goalT2) {
			return t2;
		}else {
			return t1;
		}
	}

	public boolean isKO() {
		return type == TYPE.PLACEMENT || type == TYPE.SEMIFINAL;
	}

	public TYPE getTYPE() {
		// TODO Auto-generated method stub
		return type;
	}
}
