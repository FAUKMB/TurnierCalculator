import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class CorrectionFrame extends JFrame{
	public CorrectionFrame(ArrayList<Match> matches, MainFrame mainFrame) {
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(600, 50 * (matches.size() + 1) + 50);
		for(int i = 0; i < matches.size(); i++) {
			if(!matches.get(i).played()) {
				continue;
			}
			JButton match = new JButton(matches.get(i).toString());
			match.setBounds(50, 40*i+50, 400, 30);
			match.addActionListener(new ButtonListener(matches.get(i), mainFrame));
			add(match);
		}
	}
	
	private class ButtonListener implements ActionListener{

		private Match match;
		private MainFrame mainFrame;
		
		public ButtonListener(Match match, MainFrame mainFrame) {
			this.match = match;
			this.mainFrame = mainFrame;
		}
		//Do actions for different Buttons
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int[] res = Dialog.resultDialog(match.getT1(), match.getT2());
			if(res != null) {
				match.addResult(res[0], res[1]);
				mainFrame.semidone();
			}else {
				JOptionPane.showMessageDialog(null, "Fehler. Ungueltiges Ergebnis");
			}
		}

	}
}
