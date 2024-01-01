package turnier;

import lombok.Getter;

public class Match {
	private static int idcounter = 0;
	@Getter
	private Team t1;
	@Getter
	private Team t2;
	private int goalT1 = -1;
	private int goalT2 = -1;
	// TODO Auto-generated method stub
	@Getter
	private int field;
	private final TYPE type;
	@Getter
	private int id;
	private final String groupname;

	public enum TYPE {
		SEMIFINAL,
		GROUP,
		PLACEMENT,
		GROUP_PLACEMENT,
		FINAL,
		THIRD
	}

	public void addResult(int t1, int t2) {
		goalT1 = t1;
		goalT2 = t2;
		if (type == TYPE.SEMIFINAL) {
			winner().setPosition(1);
			looser().setPosition(3);
		}
	}

	public void confirmResult() {
		if (type == TYPE.SEMIFINAL) {
			return;
		}
		getT1().setGoals(getT1().getGoals() + goalT1);
		getT2().setGoals(getT2().getGoals() + goalT2);
		getT1().setMinusgoals(getT1().getMinusgoals() + goalT2);
		getT2().setMinusgoals(getT2().getMinusgoals() + goalT1);
		if (goalT1 > goalT2) {
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
		this(t1, t2, field, type, null);
	}

	public Match(Team t1, Team t2, TYPE type, String groupname) {
		this(t1, t2, 1, type, groupname);
	}

	public Match(Team t1, Team t2, int field, TYPE type, String groupname) {
		this.setT1(t1);
		this.setT2(t2);
		this.field = field;
		this.type = type;
		id = idcounter++;
		this.groupname = groupname;
	}

	private String alignMaxNameLen(String s) {
		String ret = s;
		for (int i = s.length(); i < Turnier.maxNameLen; i++) {
			s += " ";
		}
		return ret;
	}

	public String toString() {
		String typeS = getType();
		int maxTypeLen = 11;
		for (int i = typeS.length(); i < maxTypeLen; i++) {
			typeS += " ";
		}
		String ret = typeS;
		if (Turnier.numberOfFields == 2) {
			ret += " ";
			ret += Turnier.fieldname[field];
		}
		if (goalT1 == -1) {
			if (getT1() == null) {
				if (type == TYPE.FINAL) {
					return ret + " " + alignMaxNameLen("Sieger hf1") + " - " + alignMaxNameLen("Sieger hf2");
				} else {
					return ret + " " + alignMaxNameLen("Verlierer hf1") + " - " + alignMaxNameLen("Verlierer hf2");
				}
			}
			ret += " " + getT1().getNamePrint() + " - " + getT2().getNamePrint();
			return ret;
		} else {
			ret += " " + getT1().getNamePrint() + " - " + getT2().getNamePrint() + "   " + goalT1 + " : " + goalT2;
			return ret;
		}
	}

	public String showFrame() {
		if (getT1() == null || getT2() == null) {
			return "TBD";
		} else {
			return getT1().getName() + " - " + getT2().getName();
		}
	}

	public void setT1(Team t1) {
		this.t1 = t1;
	}

	public void setT2(Team t2) {
		this.t2 = t2;
	}

	public boolean played() {
		return goalT1 != -1;
	}

	public String getFieldname() {
		return Turnier.fieldname[field - 1];
	}

	public int getGoal1() {
		return goalT1;
	}

	public int getGoal2() {
		return goalT2;
	}

	public String getType() {
		String ret = "";
		if (type == TYPE.GROUP) {
			ret = "Gruppe";
			if (groupname != null) {
				ret += " " + groupname;
			}
		} else if (type == TYPE.SEMIFINAL) {
			ret = "Halbfinale";
		} else if (type == TYPE.GROUP_PLACEMENT) {
			ret = "Platzierung";
		} else if (type == TYPE.FINAL) {
			ret = "Finale";
		} else if (type == TYPE.THIRD) {
			ret = "Platz 3";
		} else if (type == TYPE.PLACEMENT) {
			if (getT1() != null && getT1().getPosition() != 0) {
				ret = "Platz " + getT1().getPosition();
			} else {
				ret = "Platzierung";
			}
		}
		return ret;
	}

	public boolean isSemi() {
		return type == TYPE.SEMIFINAL;
	}

	public Team winner() {
		if (goalT1 > goalT2) {
			return t1;
		} else {
			return t2;
		}
	}

	public Team looser() {
		if (goalT1 > goalT2) {
			return t2;
		} else {
			return t1;
		}
	}

	public boolean isKO() {
		return type == TYPE.PLACEMENT || type == TYPE.SEMIFINAL || type == TYPE.FINAL || type == TYPE.THIRD;
	}

	public TYPE getTYPE() {
		// TODO Auto-generated method stub
		return type;
	}
}
