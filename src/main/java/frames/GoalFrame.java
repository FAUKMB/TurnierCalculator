package frames;

import turnier.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//Insert a goal with this frame

public class GoalFrame extends JFrame {

	private final List<Player> players;
	private final JTextField input;
	private final String left;
	private final String right;

	public GoalFrame(List<Player> l, String left, String right) {
		players = l;
		this.left = left;
		this.right = right;

		setSize(300, 300);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel number = new JLabel("Spielernummer: ");
		number.setBounds(20, 20, 180, 30);
		add(number);

		input = new JTextField();
		input.setBounds(20, 60, 170, 30);
		add(input);

		JButton ok = new JButton("OK");
		ok.setBounds(40, 100, 100, 50);
		ok.addActionListener(new OKListener());
		add(ok);
	}

	private class OKListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int number = 0;
			while (true) {
				try {
					number = Integer.parseInt(input.getText());
					break;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Falsche Eingabe!");
				}
			}
			String[] ops = {left, right};
			int lr = JOptionPane.showOptionDialog(null, left + " oder " + right, null, 0, 0, null, ops, null);
			if (lr == JOptionPane.CLOSED_OPTION)
				return;

			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getTeam().equals(ops[lr]) && players.get(i).getNumber() == number) {
					players.get(i).setGoals(players.get(i).getGoals() + 1);
				}
			}
			JOptionPane.showMessageDialog(null, "Eingetragen");
			dispose();
		}

	}

}
