package turnier;

import lombok.Getter;

@Getter
public class Time {
	private final int hours;
	private final int minutes;
	private final int seconds;

	public Time(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		if (isInvalid()) {
			throw new IllegalArgumentException("Invalid Time");
		}
	}

	public Time(String time) {
		String[] times = time.split(":");
		if (times.length != 3) {
			throw new IllegalArgumentException("Invalid Time");
		}

		this.hours = Integer.parseInt(times[0]);
		this.minutes = Integer.parseInt(times[1]);
		this.seconds = Integer.parseInt(times[2]);
		if (isInvalid()) {
			throw new IllegalArgumentException("Invalid Time");
		}
	}

	private boolean isInvalid() {
		return hours < 0 || hours >= 24 || minutes < 0 || minutes >= 60 || seconds < 0 || seconds >= 60;
	}

	public String toLog() {
		return hours + ":" + minutes + ":" + seconds;
	}

	public String toString() {
		String format = "%02d:%02d:%02d";
		return String.format(format, hours, minutes, seconds);
	}


}
