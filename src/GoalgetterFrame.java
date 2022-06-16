import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JLabel;

//Show the best Goalgetters with this frame

public class GoalgetterFrame extends JFrame{
	
	public GoalgetterFrame(LinkedList<Player> list){
		setVisible(true);
		setLayout(null);
		setSize(300, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		LinkedList<Player> copy = (LinkedList<Player>) list.clone();
		copy.sort(new PlayerComparator());
		JLabel[] lbls = new JLabel[10];
		try{
		BufferedWriter bw = new BufferedWriter(new FileWriter("Torschuetzen.txt"));
		bw.write("Mannschaft\t\tName\t\tTore");
		bw.newLine();
		for(int i = 0; i < 10 && i < copy.size(); i++){
			lbls[i] = new JLabel(copy.get(i).getTeam() + "  " + copy.get(i).getName() + "  " + copy.get(i).getGoals());
			lbls[i].setBounds(20, 20 + i*40, 180, 30);
			add(lbls[i]);
			bw.write(copy.get(i).getTeam() + "  " + copy.get(i).getName() + "  " + copy.get(i).getGoals());
		}
		bw.close();
		}catch(IOException e){
			System.err.println("Severe error occured during writing the file!! Exit the programm and check your hard disk or RAM!");
		}
	}

}
