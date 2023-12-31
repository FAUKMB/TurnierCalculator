package turnier;

public class Time {
	private int hours;
	private int minutes;
	private int seconds;

	public Time(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		if (!isValid()) {
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
		if (!isValid()) {
			throw new IllegalArgumentException("Invalid Time");
		}
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public boolean isValid() {
		return hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60 && seconds >= 0 && seconds < 60;
	}

	public String toString() {
		return hours + ":" + minutes + ":" + seconds;
	}


}
