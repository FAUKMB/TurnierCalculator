package turnier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;

//print table with this frame and put it into a file as well

public class TableFrame extends JFrame{

	public TableFrame(ArrayList<Match> matches, ArrayList<Team> teams, String header, boolean headToHead){
		TableCalculator.clear(teams);
		TableCalculator.calcTable(matches, teams, headToHead);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);
		setSize(450, 100 * (teams.size() + 1) + 50);
		JLabel points = new JLabel("Punkte");
		points.setBounds(350, 50, 75, 40);
		add(points);
		JLabel goals = new JLabel("Tore");
		goals.setBounds(300, 50, 50, 40);
		add(goals);
		JLabel name = new JLabel("Name");
		name.setBounds(100, 50, 100, 40);
		add(name);
		JLabel num = new JLabel("Platz");
		num.setBounds(50, 50, 50, 40);
		add(num);
		JLabel head = new JLabel("Tabelle " + header);
		head.setBounds(100, 10, 300, 50);
		add(head);
		JLabel[] lbNames = new JLabel[teams.size()];
		JLabel[] lbGoals = new JLabel[teams.size()];
		JLabel[] lbPoints = new JLabel[teams.size()];
		JLabel[] lbNum = new JLabel[teams.size()];
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("Tabelle" + header + ".txt"));
			bw.write(head.getText());
			bw.newLine();
			bw.newLine();
			for(int i = 0; i < teams.size(); i++){
				lbNames[i] = new JLabel(teams.get(i).getName());
				lbNames[i].setBounds(100, (i + 1) * 100 + 25, 200, 50);
				add(lbNames[i]);
				lbNum[i] = new JLabel(new Integer((i + 1)).toString());
				lbNum[i].setBounds(50, (i + 1) * 100 + 25, 200, 50);
				add(lbNum[i]);
				lbGoals[i] = new JLabel(teams.get(i).getGoals() + " : " + teams.get(i).getMinusgoals());
				lbGoals[i].setBounds(300, (i + 1) * 100 + 25, 200, 50);
				add(lbGoals[i]);
				lbPoints[i] = new JLabel(new Integer(teams.get(i).getPoints()).toString());
				lbPoints[i].setBounds(350, (i + 1) * 100 + 25, 200, 50);
				add(lbPoints[i]);

				bw.write((i+1) + ". " + teams.get(i).toString());
				bw.newLine();
			}

			bw.close();
			
			Process p = Runtime.getRuntime().exec("python3 pdfconvert.py \"Tabelle" + header+ "\"");
			p.waitFor();
		}catch(Exception e){
			System.err.println("Severe error occured during writing the file!! Exit the programm and check your hard disk or RAM!");
		}
	}

}
