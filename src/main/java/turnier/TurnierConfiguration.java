package turnier;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
public class TurnierConfiguration {
	private static final int MAX_NUMBER_OF_TEAMS = 10;

	private String turnierName;
	private int numberOfFields;
	private boolean hasSemi;
	private Time startTime;
	private Time gameTime;
	private Time pauseTime;
	private PLAYTYPE playtype;
	private List<Team> teams;
	private Map<Integer, List<PLAYTYPE>> typeOptions = new HashMap<>();

	private static final String CONFIG_LOG_PATTERN = "%s %d %d %s %s %s %s %s";

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

	public void setPlaytype(PLAYTYPE type) {
		if (!getTypeOptions().get(teams.size()).contains(type)) {
			throw new IllegalArgumentException();
		}
		this.playtype = type;
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

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public boolean isKnockout() {
		return playtype == PLAYTYPE.KNOCKOUT || playtype == PLAYTYPE.DOUBLE_ROUND_ROBIN_SMALL_GROUP;
	}

	public void writeConfigToLog(BufferedWriter bufferedWriter) throws IOException {
		String logstring = String.format(CONFIG_LOG_PATTERN, turnierName, numberOfFields, teams.size(), hasSemi, startTime, gameTime, pauseTime, playtype.name());
		bufferedWriter.write(logstring);
		bufferedWriter.newLine();
		for (Team team : teams) {
			bufferedWriter.write(team.getName());
			bufferedWriter.newLine();
		}
	}

	public static TurnierConfiguration loadConfig(Scanner scanner) {
		String line = scanner.nextLine();
		String[] elements = line.split(" ");
		TurnierConfiguration configuration = new TurnierConfiguration();
		configuration.setTurnierName(elements[0]);
		configuration.setNumberOfFields(Integer.parseInt(elements[1]));
		int numberOfTeams = Integer.parseInt(elements[2]);
		configuration.setHasSemi(Boolean.parseBoolean(elements[3]));
		configuration.setStartTime(new Time(elements[4]));
		configuration.setGameTime(new Time(elements[5]));
		configuration.setPauseTime(new Time(elements[6]));
		ArrayList<Team> teams = new ArrayList<>();
		for (int i = 0; i < numberOfTeams; i++) {
			teams.add(new Team(scanner.nextLine()));
		}
		configuration.setTeams(teams);
		configuration.setPlaytype(PLAYTYPE.valueOf(elements[7]));
		return configuration;
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
