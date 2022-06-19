package turnier;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//Main screen for the programm

public class MainFrame extends JFrame{
	private JLabel head;
	private JButton insertTeam;
	private JButton setGoal;
	private JButton endGame;
	private JButton showTable;
	private JButton showTeam;
	private JButton showGoals;
	private JButton matchplan;
	private JButton fix;
	private JLabel referee;
	private JLabel curGame1;
	private Match curMatch1;
	private Match curMatch2;
	private JLabel nextGame1;
	private JLabel curGame2;
	private JLabel nextGame2;
	private boolean semi;
	private boolean ref = false;
	private ArrayList<Match> matches;
	private ArrayList<Match> knockout;
	private ArrayList<Match> groupphase;
	private ArrayList<Team> teams;
	private boolean is_knockout;
	private String name;
	private Log log;

	private int fields;

	private LinkedList<Player> sListe = new LinkedList<>();

	void updateGames() {
		doUpdateGame(1);
		if(fields == 2) {
			doUpdateGame(2);
		}
	}
	
	void doUpdateGame(int field) {
		Match cur = null;
		Match next = null;
		for(int i = 0; i < matches.size(); i++) {
			if(!matches.get(i).played() && matches.get(i).getField() == field) {
				if(cur == null) {
					cur = matches.get(i);
				}else {
					next = matches.get(i);
					break;
				}
			}
		}
		if(cur != null) {
			
			if(field == 1) {
				curGame1.setText("Aktuelle Partie Feld 1 (" + cur.getType() + "): " + cur.showFrame());
				curMatch1 = cur;
			}else {
				curMatch2 = cur;
				curGame2.setText("Aktuelle Partie Feld 2 (" + cur.getType() + "): " + cur.showFrame());
			}
		}else {
			if(field == 1) {
				curMatch1= null;
				curGame1.setText("Aktuelle Partie Feld 1: Pause...");
			}else {
				curMatch2 = null;
				curGame2.setText("Aktuelle Partie Feld 2: Pause...");
			}
		}
		if(next != null) {
			if(field == 1) {
				nextGame1.setText("Naechste Partie Feld 1 ("+ next.getType() + "): " + next.showFrame());
			}else {
				nextGame2.setText("Naechste Partie Feld 2 ("+ next.getType() + "): " + next.showFrame());
			}
		}else {
			if(field == 1) {
				nextGame1.setText("Naechsete Partie Feld 1: Pause...");
			}else {
				nextGame2.setText("Naechsete Partie Feld 2: Pause...");
			}
		}
	}


	//Init Main menue
	public MainFrame(ArrayList<Match> matches, ArrayList<Team> teams, boolean semi, int fields, String name, boolean is_knockout, Log log){
		this.matches = matches;
		groupphase = matches;
		this.teams = teams;
		this.semi = semi;
		this.fields = fields;
		this.is_knockout = is_knockout;
		this.name = name;
		this.log = log;
		//create frame
		
		setSize(1350, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Turnier fuer " + teams.size() + " Mannschaften");
		setResizable(true);
		setLayout(null);

		Font normalFont = new Font("Arial", Font.BOLD, 20);
		Font haedFont = new Font("Arial", Font.BOLD, 50);
		if(ref) {
			referee = new JLabel("Schiedsrichter: " + "TODO");
			referee.setBounds(50 , 100, 800, 30);
			referee.setFont(normalFont);
			add(referee);
		}
		curGame1 = new JLabel("Aktuelle Partie Feld 1: Ungenutzt");
		curGame1.setBounds(50, 100, 1200, 30);
		curGame1.setFont(normalFont);
		add(curGame1);

		nextGame1 = new JLabel("Naechste Partie Feld 1: Ungenutzt");
		nextGame1.setBounds(50, 150, 1200, 30);
		nextGame1.setFont(normalFont);
		add(nextGame1);
		
		if(fields == 2) {
			curGame2 = new JLabel("Aktuelle Partie Feld 2: Ungenutzt");
			curGame2.setBounds(50, 250, 1200, 30);
			curGame2.setFont(normalFont);
			add(curGame2);

			nextGame2 = new JLabel("Naechste Partie Feld 2: Ungenutzt");
			nextGame2.setBounds(50, 300, 1200, 30);
			nextGame2.setFont(normalFont);
			add(nextGame2);
		}
		updateGames();
		if(log.isLoad()) {
			log.loadMatches(matches);
			updateGames();
			
			if(curMatch1 == null && curMatch2 == null && knockout == null) {
				if(is_knockout && knockout == null) {
					ArrayList<Team> groupA = new ArrayList<>();
					ArrayList<Team> groupB = new ArrayList<>();
				
					for(int i = 0; i < teams.size(); i++) {
						if(i < (teams.size()+1)/2) {
							groupA.add(teams.get(i));
						}else {
							groupB.add(teams.get(i));
						}
					}
					TableCalculator.clear(groupA);
					TableCalculator.calcTable(matches, groupA, true);
					TableCalculator.clear(groupB);
					TableCalculator.calcTable(matches, groupB, true);
					for(int i = 0; i < groupB.size(); i++) {
						groupA.get(i).setPosition(i*2+1);
						groupB.get(i).setPosition(i*2+1);
					}
					if(groupA.size() != groupB.size()) {
						groupA.get(groupA.size()-1).setPosition(teams.size()-2);
					}
					knockout = Matchplan.knockout(groupA, groupB, fields, semi);
					this.matches = knockout;
					log.loadMatches(matches);
					semidone();
					updateGames();
				}
			}
		}


		head = new JLabel(name);
		head.setBounds(150, 30, 900, 50);
		head.setFont(haedFont);
		add(head);

		insertTeam = new JButton("Spieler einfuegen");
		insertTeam.setBounds(50, 400, 200, 50);
		insertTeam.addActionListener(new ButtonListener());
		insertTeam.setEnabled(false);
		add(insertTeam);

		setGoal = new JButton("Tor eintragen");
		setGoal.setBounds(300, 400, 200, 50);
		setGoal.addActionListener(new ButtonListener());
		setGoal.setEnabled(false);
		add(setGoal);

		endGame = new JButton("Endergebnis");
		endGame.setBounds(550, 400, 200, 50);
		endGame.addActionListener(new ButtonListener());
		add(endGame);

		matchplan = new JButton("Spielplan");
		matchplan.setBounds(50, 500, 200, 50);
		matchplan.addActionListener(new ButtonListener());
		add(matchplan);

		fix = new JButton("Korrektur");
		fix.setBounds(800, 500, 200, 50);
		fix.addActionListener(new ButtonListener());
		add(fix);

		showTable = new JButton("Tabellen anzeigen");
		showTable.setBounds(800, 400, 200, 50);
		showTable.addActionListener(new ButtonListener());
		add(showTable);

		showTeam = new JButton("Mannschaft anzeigen");
		showTeam.setBounds(300, 500, 200, 50);
		showTeam.addActionListener(new ButtonListener());
		showTeam.setEnabled(false);
		add(showTeam);

		showGoals = new JButton("Torschuetzen anzeigen");
		showGoals.setBounds(550, 500, 200, 50);
		showGoals.addActionListener(new ButtonListener());
		showGoals.setEnabled(false);
		add(showGoals);

		setVisible(true);
	}

	private class ButtonListener implements ActionListener{

		//Do actions for different Buttons
		@Override
		public void actionPerformed(ActionEvent arg0) {

			if(arg0.getActionCommand().equals("Endergebnis")){
				setEntry();
			}else if(arg0.getActionCommand().equals("Tor eintragen")){
				insertGoal();
			}else if(arg0.getActionCommand().equals("Tabellen anzeigen")){
				showTable();
			}else if(arg0.getActionCommand().equals("Spieler einfuegen")){
				insertPlayer(0);
			}else if(arg0.getActionCommand().equals("Mannschaft anzeigen")){
				insertPlayer(1);
			}else if(arg0.getActionCommand().equals("Torschuetzen anzeigen")){
				showGoalgetters();
			}else if(arg0.getActionCommand().equals("Spielplan")) {
				showMatchplan();
			}else if (arg0.getActionCommand().equals("Korrektur")) {
				correct();
			}
		}

	}

	private void correct() {
		new CorrectionFrame(matches, this);
	}
	//Actions depending on button pressed
	private void showMatchplan() {
		ArrayList<Match> list = new ArrayList<>();
		list.addAll(groupphase);
		if(knockout != null) {
			list.addAll(matches);
		}
		new MatchplanFrame(list, name);
	}
	
	public void semidone() {
		if(knockout == null) {
			return;
		}
			Match semi1 = null;
			Match semi2 = null;
			int size = knockout.size();
			for(int i = 0; i < size; i++) {
				Match cur = knockout.get(i);
				if(cur.isSemi() && cur.played()) {
					if(semi1 == null) {
						semi1 = cur;
					}else {
						semi2 = cur;
					}
					if(semi1 != null && semi2 != null) {
						Matchplan.setFinals(semi1.winner(), semi2.winner(), semi1.looser(), semi2.looser(), knockout);
					}
				}
			}
	}

	private void setEntry(){
		int field = 0;
		if(fields > 1) {
			field = Dialog.optionDialog(new String[]{"1", "2"}, "Welches Feld ist fertig?");
		}
		field += 1;
		if(field == 1 && curMatch1 != null) {
			doEntry(curMatch1);
			if(semi) {
				semidone();
			}
		}else if(field == 2 && curMatch2 != null){
			doEntry(curMatch2);
			if(semi) {
				semidone();
			}
		}
		
		
		updateGames();
		
		if(curMatch1 == null && curMatch2 == null && knockout == null) {
			if(is_knockout && knockout == null) {
				ArrayList<Team> groupA = new ArrayList<>();
				ArrayList<Team> groupB = new ArrayList<>();
				showMatchplan();
				int res = JOptionPane.showConfirmDialog(null, "Sind alle Ergebnisse korrekt?");
				if(res != JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(null, "Korrigiere das Ergebnis ueber die Korrekturfunktion. Danach erneut Ergebnis eintragen klicken!");
					return;
				}

				for(int i = 0; i < teams.size(); i++) {
					if(i < (teams.size()+1)/2) {
						groupA.add(teams.get(i));
					}else {
						groupB.add(teams.get(i));
					}
				}
				TableCalculator.clear(groupA);
				TableCalculator.calcTable(matches, groupA, true);
				TableCalculator.clear(groupB);
				TableCalculator.calcTable(matches, groupB, true);
				for(int i = 0; i < groupB.size(); i++) {
					groupA.get(i).setPosition(i*2+1);
					groupB.get(i).setPosition(i*2+1);
				}
				if(groupA.size() != groupB.size()) {
					groupA.get(groupA.size()-1).setPosition(teams.size()-2);
				}
				knockout = Matchplan.knockout(groupA, groupB, fields, semi);
				matches = knockout;
				updateGames();
			} else {
				JOptionPane.showMessageDialog(null, "Turnier beendet!");
			}
		}
	}

	private void insertPlayer(int flag){
		String[] names = new String[8];
		for(int i = 0; i < 8; i++){
			names[i] = teams.get(i).getName();
		}
		new SelectTeamFrame(names, sListe, flag);
	}

	private void showTable(){
		int d = 0;
		if(is_knockout) {
			String [] ops = {"Gruppe A", "Gruppe B", "Alle"};
			d = JOptionPane.showOptionDialog(null, "Waehle Gruppe:", null, 0, JOptionPane.INFORMATION_MESSAGE, null, ops, null);
			if(d == 0){
				new TableFrame(groupphase, Matchplan.subList(teams, 0, (teams.size()+1)/2), name + " Gruppe A", true);
			}else if( d== 1){
				new TableFrame(groupphase, Matchplan.subList(teams, (teams.size()+1)/2, teams.size()), name + " Gruppe B", true);
			}else if(d == 2){
				new TableFrame(matches, teams, "Endstand " + name, false);
			}
		}else {
			new TableFrame(matches, teams, name + " Tabelle", true);
		}

	}

	private void insertGoal(){
		new GoalFrame(sListe, "fff", "fff");
	}

	private void doEntry(Match match){
		int [] result = Dialog.resultDialog(match.getT1(), match.getT2());
		if(result != null && valid(result, match)) {
			match.addResult(result[0], result[1]);
			log.logMatch(match);
			JOptionPane.showMessageDialog(null, "Ergebnis eingetragen");
		}else {
			JOptionPane.showMessageDialog(null, "Fehler! Ungueltiges Ergebnis");
		}
	}

	private boolean valid(int[] result, Match match) {
		return !(result[0] == result[1] && match.isKO());
	}


	private void showGoalgetters(){
		new GoalgetterFrame(sListe);
	}
}
