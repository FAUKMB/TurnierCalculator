package Util;

import turnier.Team;
import turnier.Time;
import turnier.TurnierConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Dialog {

	public static int optionDialog(String[] options, String message) {
		int ret = JOptionPane.showOptionDialog(null, message, null, 0, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
		if (ret == JOptionPane.CLOSED_OPTION) {
			return -1;
		} else {
			return ret;
		}
	}

	public static int[] resultDialog(Team t1, Team t2) {
		int[] ret = new int[2];
		JTextField xField = new JTextField(3);
		JTextField yField = new JTextField(3);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel(t1.getName() + " : " + t2.getName()));
		myPanel.add(xField);
		myPanel.add(new JLabel(":"));
		myPanel.add(yField);
		while (true) {
			int result = JOptionPane.showConfirmDialog(null, myPanel,
					"Ergebnis eintragen", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					ret[0] = Integer.parseInt(xField.getText());
					ret[1] = Integer.parseInt(yField.getText());
					if (ret[0] >= 0 || ret[1] >= 0) {
						return ret;
					} else {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Wrong result.");
				}
			} else {
				return null;
			}
		}

	}

	public static TurnierConfiguration configurationDialog() {
		TurnierConfiguration conf = new TurnierConfiguration();
		while (true) {
			JPanel panel = new JPanel();
			GridLayout layout = new GridLayout(0, 2);
			panel.setLayout(layout);
			JTextField nameField = addInputLineToPanel(panel, "Turniername");
			JTextField numberOfTeams = addInputLineToPanel(panel, "Anzahl der Teams");
			JTextField numberOfFields = addInputLineToPanel(panel, "Anzahl der Felder");
			JTextField startTime = addTimeInputLine(panel, "Startzeit");
			JTextField spielzeit = addTimeInputLine(panel, "Spielzeit");
			JTextField pausenzeit = addTimeInputLine(panel, "Pausenzeit");
			int ret = JOptionPane.showConfirmDialog(null, panel);
			if (ret != JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			try {
				conf.setGameTime(new Time(spielzeit.getText()));
				conf.setPauseTime(new Time(pausenzeit.getText()));
				conf.setStartTime(new Time(startTime.getText()));
				conf.setTurnierName(nameField.getText());
				conf.setNumberOfFields(Integer.parseInt(numberOfFields.getText()));
				ArrayList<Team> teams = new ArrayList<>();
				for (int i = 0; i < Integer.parseInt(numberOfTeams.getText()); i++) {
					String teamname = JOptionPane.showInputDialog("Mannschaft " + (i + 1) + " eingeben:");
					if (teamname == null) {
						throw new IllegalArgumentException();
					}
					teams.add(new Team(teamname));
				}
				conf.setTeams(teams);
				break;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Invalid Config");
			}
		}

		return conf;
	}

	private static JTextField addInputLineToPanel(JPanel panel, String description) {
		JTextField field = new JTextField(10);
		JLabel descriptionField = new JLabel(description + ": ");

		panel.add(descriptionField);
		panel.add(field);
		return field;
	}

	private static JTextField addTimeInputLine(JPanel panel, String description) {
		JLabel descriptionLabel = new JLabel(description + "(hh:mm:ss): ");
		JTextField time = new JTextField(10);

		panel.add(descriptionLabel);
		panel.add(time);
		return time;
	}

	public static void showPlaytypeDialog(TurnierConfiguration configuration) {
		List<TurnierConfiguration.PLAYTYPE> types = configuration.getTypeOptions().get(configuration.getTeams().size());
		if (types.size() == 1) {
			configuration.setPlaytype(types.get(0));
		} else {
			String[] options = types.stream().map(v -> v.type).toArray(String[]::new);
			int ret = JOptionPane.showOptionDialog(null, "Spielplan", null, 0, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
			if (ret == JOptionPane.CLOSED_OPTION) {
				throw new IllegalArgumentException();
			}
			configuration.setPlaytype(types.get(ret));
		}
	}

	public static void showSemifinalDialog(TurnierConfiguration configuration) {
		int ret = JOptionPane.showConfirmDialog(null, "Mit Halbfinale?");
		if (ret == JOptionPane.YES_OPTION) {
			configuration.setHasSemi(true);
		} else if (ret == JOptionPane.CANCEL_OPTION || ret == JOptionPane.CLOSED_OPTION) {
			throw new IllegalArgumentException();
		}
		configuration.setHasSemi(false);
	}

}
