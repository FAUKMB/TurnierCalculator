package turnier;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Dialog {
	static int integerDialog(String text) {
		while(true) {
			try {
				String s = JOptionPane.showInputDialog(text);
				if(s == null) {
					return -1;
				}
				int ret = Integer.parseInt(s);
				return ret;
			}catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Not a number. Try again!");
			}
		}
	}

	static int optionDialog(String[] options, String message) {
		int ret = JOptionPane.showOptionDialog(null, message, null, 0, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
		if(ret == JOptionPane.CLOSED_OPTION) {
			return -1;
		}else {
			return ret;
		}
	}

	static int[] resultDialog(Team t1, Team t2) {
		int [] ret = new int[2];
		JTextField xField = new JTextField(3);
		JTextField yField = new JTextField(3);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel(t1.getName() + " : " + t2.getName()));
		myPanel.add(xField);
		myPanel.add(new JLabel(":"));
		myPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Ergebnis eintragen", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			try {
				ret[0] = Integer.parseInt(xField.getText());
				ret[1] = Integer.parseInt(yField.getText());
				if(ret[0] < 0 || ret[1] < 0) {
					ret = null;
				}
			}catch(NumberFormatException e) {
				ret = null;
			}
		}else {
			ret = null;
		}
		return ret;
	}
	
	static int[] timeDialog() {
		int [] ret = new int[2];
		JTextField xField = new JTextField(3);
		JTextField yField = new JTextField(3);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Startzeit: "));
		myPanel.add(xField);
		myPanel.add(new JLabel(":"));
		myPanel.add(yField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, 
				"Turnierstart angeben", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION) {
			try {
				ret[0] = Integer.parseInt(xField.getText());
				ret[1] = Integer.parseInt(yField.getText());
				if(ret[0] < 0 || ret[1] < 0) {
					ret = null;
				}
			}catch(NumberFormatException e) {
				ret = null;
			}
		}else {
			ret = null;
		}
		return ret;
	}
}
