import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MatchplanFrame extends JFrame{

	private ArrayList<Match> matches;
	public MatchplanFrame(ArrayList<Match> matches, String name) {
		this.matches = matches;
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(700, 50 * (matches.size() + 1) + 50);
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			String matchS = m.getType() + "  Feld " + m.getField() + "  " + m.showFrame();
			if(m.played()) {
				matchS += "   " + m.getGoal1() + " : " + m.getGoal2();
			}
			JLabel match = new JLabel(matchS);
			match.setBounds(50, 30*i+50, 700, 40);
			add(match);
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Spielplan " + name + ".txt"));
			bw.write("Spielplan fuer " + name);
			bw.newLine();
			bw.newLine();
			for(int i = 0; i < matches.size(); i++) {
				bw.write(matches.get(i).toString());
				bw.newLine();
			}
			bw.close();
		}catch(Exception e) {
			System.err.println("error writing file");
		}
		
	}
}
