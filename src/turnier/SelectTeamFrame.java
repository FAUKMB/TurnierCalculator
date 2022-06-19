package turnier;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//Select team and put player on the list or print team into file(depends on flag)

public class SelectTeamFrame extends JFrame{
	private JButton[] buttons;
	private List<Player> playerList;
	private JTextField name;
	private JTextField number;
	private int flag;
	
	public SelectTeamFrame(String[] names, List<Player> playerList, int flag){
		this.playerList = playerList;
		this.flag = flag;
		
		setVisible(true);
		setSize(400, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Mannschaft auswaehlen!");
		setResizable(false);
		setLayout(null);
		buttons = new JButton[8];
		for(int i = 0; i < 8; i++){
			buttons[i] = new JButton(names[i]);
			buttons[i].setBounds(50, 25 + i * 75, 300, 50);
			add(buttons[i]);
			buttons[i].addActionListener(new MannListener());
		}
	}
	
	private class MannListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(flag ==1){
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(arg0.getActionCommand() + ".txt"));
					for(int i = 0; i < playerList.size(); i++){
						if(playerList.get(i).getTeam().equals(arg0.getActionCommand())){
							bw.write(playerList.get(i).toString());
							bw.newLine();
						}
					}
					bw.close();
				} catch (IOException e) {
					System.err.println("Severe error occured during writing the file!! Exit the programm and check your hard disk or RAM!");
				}
				return;
			}
			//Screen with PayerDates and add Player to Team
			JFrame inFrame = new JFrame();
			inFrame.setVisible(true);
			inFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			inFrame.setSize(400, 500);
			inFrame.setLayout(null);
			
			JLabel header = new JLabel("Spieler der Mannschaft " + arg0.getActionCommand() + " eingeben");
			header.setBounds(50, 50, 350 , 50);
			inFrame.add(header);
			name = new JTextField();
			name.setBounds(75, 150, 300, 30);
			inFrame.add(name);
			JLabel num = new JLabel("Nummer: ");
			JLabel nam = new JLabel("Name: ");
			nam.setBounds(10, 150, 40, 30);
			num.setBounds(10, 250, 65, 30);
			inFrame.add(nam);
			inFrame.add(num);
			number = new JTextField();
			number.setBounds(75, 250, 300, 30);
			inFrame.add(number);
			JButton ok = new JButton("OK");
			ok.setBounds(50, 350, 100, 50);
			ok.addActionListener(new OKListener(arg0.getActionCommand()));
			inFrame.add(ok);
			
		}
		
	}
	
	private class OKListener implements ActionListener{
		
		private String team;
		
		public OKListener(String team){
			this.team = team;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Player s = parsePlayer(name.getText(), number.getText(), team);
			if(s != null){
				playerList.add(s);
				JOptionPane.showMessageDialog(null,  "Spieler hinzugefuegt!");
				name.setText(null);
				number.setText(null);
			}
			
		}
		
		private Player parsePlayer(String name, String number, String team){
			int num = 0;
			try{
				num = Integer.parseInt(number);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Falsche eingabe");
				return null;
			}
			return new Player(name, num, team, 0);
		}
	}
}
