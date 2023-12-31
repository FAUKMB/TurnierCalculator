package turnier;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TurnierConfiguration {
	private static final int MAX_NUMBER_OF_TEAMS = 10;

	private String turnierName;
	private int numberOfFields;
	private int numberOfTeams;
	private boolean hasSemi;
	private Time startTime;
	private Time gameTime;
	private Time pauseTime;
	private PLAYTYPE playtype;
	private Map<Integer, List<PLAYTYPE>> typeOptions = new HashMap<>();

	public TurnierConfiguration() {
		for (int i = 0; i <= MAX_NUMBER_OF_TEAMS; i++) {
			typeOptions.put(i, new ArrayList<>());
		}
		typeOptions.get(3).add(PLAYTYPE.DOUBLE_ROUND_ROBIN);

		typeOptions.get(4).add(PLAYTYPE.DOUBLE_ROUND_ROBIN);

		typeOptions.get(5).add(PLAYTYPE.ROUND_ROBIN);
		typeOptions.get(5).add(PLAYTYPE.DOUBLE_ROUND_ROBIN);

		typeOptions.get(6).add(PLAYTYPE.ROUND_ROBIN);
		typeOptions.get(6).add(PLAYTYPE.KNOCKOUT);

		typeOptions.get(7).add(PLAYTYPE.ROUND_ROBIN);
		typeOptions.get(7).add(PLAYTYPE.KNOCKOUT);
		typeOptions.get(7).add(PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP);

		typeOptions.get(8).add(PLAYTYPE.KNOCKOUT);

		typeOptions.get(9).add(PLAYTYPE.KNOCKOUT);

		typeOptions.get(10).add(PLAYTYPE.KNOCKOUT);
	}

	public void setTurnierName(String name) {
		if (name.isBlank()) {
			throw new IllegalArgumentException();
		}
		this.turnierName = name;
	}

	public void setNumberOfFields(int numberOfFields) {
		if (numberOfFields != 1 && numberOfFields != 2) {
			throw new IllegalArgumentException();
		}
		this.numberOfFields = numberOfFields;
	}

	public void setNumberOfTeams(int numberOfTeams) {
		if (numberOfTeams < 3 || numberOfTeams > 10) {
			throw new IllegalArgumentException();
		}
		this.numberOfTeams = numberOfTeams;
	}

	public boolean isKnockout() {
		return playtype == PLAYTYPE.KNOCKOUT || playtype == PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP;
	}

	public enum PLAYTYPE {
		ROUND_ROBIN("Round Robin"),
		DOUBLE_ROUND_ROBIN("Double Round Robin"),
		DOUBLE_ROUND_ROBIN_SMALL_GROUP("Double Round Robin in Dreiergruppe"),
		KNOCKOUT("Standard Groups with Knockout");
		public final String type;

		PLAYTYPE(String type) {
			this.type = type;
		}
	}
}
