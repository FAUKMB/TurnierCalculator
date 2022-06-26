package turnier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MatchplanFrame extends JFrame{

	public MatchplanFrame(ArrayList<Match> matches, String name, int gametime, int pausetime, int starttimeh, int starttimem) {
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		JLabel head = new JLabel("Spielplan fuer " + name);
		head.setBounds(50, 20, 700, 70);
		add(head);
		setSize(700, 50 * (matches.size() + 1) + 50);
		for(int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			String matchS = m.getType() + "  " + m.getFieldname() + "  " + m.showFrame();
			if(m.played()) {
				matchS += "   " + m.getGoal1() + " : " + m.getGoal2();
			}
			JLabel match = new JLabel(matchS);
			match.setBounds(50, 30*i+80, 700, 40);
			add(match);
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("Spielplan_" + name + ".txt"));
			bw.write("Spielplan fuer " + name);
			bw.newLine();
			bw.newLine();
			bw.write("Startzeit: " + starttimeh + ":" + starttimem + "Uhr");
			bw.newLine();
			bw.write("Spielzeit: " + gametime + "min    " + "Pause: " + pausetime + "min");
			bw.newLine();
			bw.newLine();
			for(int i = 0; i < matches.size(); i++) {
				bw.write(matches.get(i).toString());
				bw.newLine();
			}
			bw.close();
			Process p = Runtime.getRuntime().exec("python3 pdfconvert.py Spielplan_" + name);
			p.waitFor();
		}catch(Exception e) {
			System.err.println("error writing file");
		}
		
	}
}
