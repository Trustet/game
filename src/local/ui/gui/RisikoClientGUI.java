package local.ui.gui;

import java.awt.Color;
<<<<<<< HEAD
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
=======
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.List;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
>>>>>>> origin/master

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
<<<<<<< HEAD
=======
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
>>>>>>> origin/master
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import local.domain.Spielfeld;
import net.miginfocom.swing.MigLayout;


public class RisikoClientGUI extends JFrame{
	private Spielfeld sp;
	private List spielerListe = new List();
	JTextField speicherEingabe = new JTextField();
	JTextField spielerName = new JTextField();
	JLabel fehlerLab = new JLabel("");
	JButton erstellen = new JButton("Erstellen");
	
	public RisikoClientGUI(){
		this.start();
	}
	public static void main(String[] args) {
		JFrame fenster = new RisikoClientGUI();

	}
<<<<<<< HEAD
    public void start() {
    	Border schwarz = BorderFactory.createLineBorder(Color.black);
        JFrame frame = new JFrame("Finden");
        frame.setSize(1400,950);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new MigLayout("wrap2","[][]","[][][][]"));
        frame.add(panel);
        //JTextArea spielfeld = new JTextArea("Weltkarte",30,20);
        JTextArea platzhalter = new JTextArea("platzhalter",10,20);
        JTextArea spieler = new JTextArea("Spielerliste",20,20);
        JTextArea missionen = new JTextArea("Missionen",10,20);
        JTextArea karten = new JTextArea("Karten",10,20);
        JTextArea statistik = new JTextArea("Statistik",15,20);
        JButton next = new JButton("N�chste Phase");
        JLabel spielfeld = null;
        BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("./welt.jpg"));
			spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 750, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		platzhalter.setBorder(schwarz);
        spieler.setBorder(schwarz);
        missionen.setBorder(schwarz);
        karten.setBorder(schwarz);
        statistik.setBorder(schwarz);
        spielfeld.setBorder(schwarz);
        
        
        panel.add(spielfeld,"left,spany 3,growx");
        panel.add(platzhalter,"left");
        panel.add(spieler,"left,top");
        panel.add(statistik,"left,top,growy");
        panel.add(missionen,"left,split2");
        panel.add(karten,"left,growx");
        panel.add(next,"center,growy,growx");
        frame.setResizable(false);
        frame.setVisible(true);
    }


		//spielStarten.addActionListener(starten -> this.risiko(mapWahl.getSelectedItem().toString()));

=======
	public void start(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout());
		this.setMinimumSize(new Dimension(400, 200));
		this.setPreferredSize(new Dimension(400, 200));
		this.setMaximumSize(new Dimension(400,200));
		JPanel hauptContainer = new JPanel();
		hauptContainer.setLayout(new BoxLayout(hauptContainer, BoxLayout.PAGE_AXIS));
		Label spielerNameLab = new Label("Spieler:");
		spielerNameLab.setMaximumSize(new Dimension(200,30));
		spielerNameLab.setPreferredSize(new Dimension(200,30));
		spielerName.setMaximumSize(new Dimension(200,30));
		spielerName.setPreferredSize(new Dimension(200,30));
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.setMaximumSize(new Dimension(250,100));
		erstellen.addActionListener(add -> spielerErstellen(spielerName.getText()));
		JButton spielStarten = new JButton("Spiel starten");
		erstellen.setMaximumSize(new Dimension(200,30));
		erstellen.setPreferredSize(new Dimension(200,30));
		spielStarten.setMaximumSize(new Dimension(200,30));
		spielStarten.setPreferredSize(new Dimension(200,30));
		String[] auswahl = {"Erde","Mordor"};
		final JComboBox<String> mapWahl = new JComboBox<String>(auswahl);
		spielStarten.addActionListener(starten -> this.risiko(mapWahl.getSelectedItem().toString()));
		mapWahl.setMaximumSize(new Dimension(200,30));
		hauptContainer.add(spielerNameLab);
		hauptContainer.add(spielerName);
		buttons.add(erstellen);
		buttons.add(spielStarten);
		hauptContainer.add(buttons);
		hauptContainer.add(mapWahl);
		hauptContainer.add(fehlerLab);
		this.setLocation(200,200);
		this.add(hauptContainer);
		this.pack();
		this.setVisible(true);
	}
	public void spielerErstellen(String name){

			if(name.length() > 0){
				try {
					sp.erstelleSpieler(name);
					spielerName.setText("");
					if(sp.getSpielerList().size() == 6){
						spielerName.setEnabled(false);
						spielerName.setText("Maximale Anzahl erreicht");
						erstellen.setEnabled(false);
						spielerName.setSize(new Dimension(270,30));
					}
				} catch (SpielerExistiertBereitsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		
	}
	
	public void risiko(String mapWahl){
		MenuBar bar = new MenuBar();
		this.setMenuBar(bar);
		
		Menu fileMenu = new FileMenu();
		bar.add(fileMenu);
		
		fehlerLab.setText(mapWahl);
		if(sp.getSpielerList().size() >= 2){
			this.dispose();
			try{
				sp.laenderErstellen();
				sp.laenderverbindungenUndKontinenteErstellen();
				sp.missionsListeErstellen();
			}catch(IOException a){
				System.out.println("Das spiel konnte nicht erstellt werden " + a.getMessage());
			}
			sp.missionenVerteilen();
			sp.laenderAufteilen();
			JFrame spielfeld = new JFrame("Risiko");
			//Hauptfenster
			spielfeld.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(new BorderLayout());
//			spielfeld.setMinimumSize(new Dimension(800, 600));
			spielfeld.setPreferredSize(new Dimension(1920, 1080));
//			spielfeld.setMaximumSize(new Dimension(5000,1080));
			
			//Container oben
			JPanel containerOben = new JPanel();
			containerOben.setLayout(new BoxLayout(containerOben, BoxLayout.X_AXIS));
//			containerOben.setMaximumSize(new Dimension(1920,730));
			containerOben.setBackground(Color.GREEN);
		
			
			//Container für Spielerausgabe
			JPanel spielerAusgabe = new JPanel();
			spielerAusgabe.setLayout(new BoxLayout(spielerAusgabe, BoxLayout.Y_AXIS));
			spielerAusgabe.setPreferredSize(new Dimension(150, 730));
//			spielerAusgabe.setMaximumSize(new Dimension(150,730));
			spielerAusgabe.setBackground(Color.BLUE);
			containerOben.add(spielerAusgabe);
	
			//Label im Spielerausgabe Container
			JLabel spielerLab = new JLabel("Spieler");
			spielerLab.setPreferredSize(new Dimension(100,30));
//			spielerLab.setMaximumSize(new Dimension(100,30));
			spielerAusgabe.add(spielerLab);
	
			
			//Liste für die Spielerausgabe
			spielerAuflisten();
			spielerListe.setMaximumSize(new Dimension(100,150));
			spielerListe.setMinimumSize(new Dimension(100,150));
			spielerAusgabe.add(spielerListe);
			
			//Platzhalter
			JLabel lab1 = new JLabel("");
			spielerAusgabe.add(lab1);

			lab1.setMaximumSize(new Dimension(100,800));
		
			//Container unten
			JPanel containerUnten = new JPanel();
			containerUnten.setLayout(new BoxLayout(containerUnten, BoxLayout.X_AXIS));
//			containerUnten.setMinimumSize(new Dimension(1920,180));
			containerUnten.setPreferredSize(new Dimension(1920, 300));
//			containerUnten.setMaximumSize(new Dimension(1920,300));
			containerUnten.setBackground(Color.RED);
			
			//MissionsPanel
			JPanel missionsPanel = new JPanel();
			missionsPanel.setLayout(new BoxLayout(missionsPanel, BoxLayout.LINE_AXIS));
			missionsPanel.setPreferredSize(new Dimension(200,300));
//			missionsPanel.setMaximumSize(new Dimension(200,300));
			missionsPanel.setBackground(Color.BLACK);
			containerUnten.add(missionsPanel);
			
			//Missionsausgabe
			Spieler spieler = sp.getAktiverSpieler();
			Mission m = sp.getSpielerMission(spieler);
			JTextArea mission = new JTextArea("Mission:\n" + m.getBeschreibung() );
			mission.setLineWrap(true);
			mission.setEditable(false);
			mission.setPreferredSize(new Dimension(100,100));
//			mission.setMaximumSize(new Dimension(100,100));
			mission.setMinimumSize(new Dimension(100,100));
			mission.setBackground(Color.CYAN);
			missionsPanel.add(mission);
			mission.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			mission.add(Box.createVerticalGlue());
//			
			//Karten
			JPanel karten = new JPanel();
			karten.setLayout(new BoxLayout(karten, BoxLayout.X_AXIS));
//			karten.setMaximumSize(new Dimension(1000,300));
			karten.setPreferredSize(new Dimension(1000,300));
			karten.setBackground(Color.ORANGE);
			containerUnten.add(karten);
			
			//Buttoncontainer
			JPanel buttons = new JPanel();
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
			//buttons.setMaximumSize(new Dimension(250,300));
			buttons.setPreferredSize(new Dimension(250,300));
			containerUnten.add(buttons);
			buttons.setBackground(Color.GRAY);
			buttons.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
			buttons.add(Box.createVerticalGlue());

			//Button speichern
			JButton speicherBtn = new JButton("Speichern");
			buttons.add(speicherBtn);
			speicherBtn.addActionListener(speichern -> speichern());
			speicherBtn.setPreferredSize(new Dimension(150,40));
			speicherBtn.setVisible(true);
			
			
			//Button schließen
			JButton schliessenBtn = new JButton("Schließen");
			buttons.add(schliessenBtn);
			schliessenBtn.addActionListener(schliessen -> System.exit(0));
			schliessenBtn.setPreferredSize(new Dimension(150,40));
			
			//Weltcontainer
			JPanel weltContainer = new JPanel();
			weltContainer.setLayout(new BoxLayout(weltContainer, BoxLayout.PAGE_AXIS));
//			weltContainer.setMaximumSize(new Dimension(1000,800));
			containerOben.add(weltContainer);
			
			//Weltkarte
			JLabel karteAusgabe;
			ImageIcon karte;
			if(mapWahl.equals("Mordor")){
				karte = new ImageIcon ("./mordor.jpg");
				karteAusgabe = new JLabel (karte);
			}else{
				karte = new ImageIcon ("./welt.jpg");
				karteAusgabe = new JLabel (karte);
			}
			
			weltContainer.add(karteAusgabe);
			
			
			//Fügt die Container dem Hauptfenster zu
		     spielfeld.add(containerOben,BorderLayout.NORTH);
		     spielfeld.add(containerUnten,BorderLayout.SOUTH);
			//Größe und visible für das Fenster setzen
		     spielfeld.pack();
		     spielfeld.setVisible(true);
		}else{
			fehlerLab.setText("Du musst erst genug Spieler eintragen");
		}
		
		
	}
	public void spielerAuflisten(){
		for (Spieler s: sp.getSpielerList()) {
			spielerListe.add(s.getName());
		}
	}
	public void speichern(){
		JFrame speicherFenster = new JFrame("Speichern");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		speicherFenster.setLayout(new BorderLayout());
		speicherFenster.setMinimumSize(new Dimension(600,600));
		speicherFenster.setMaximumSize(new Dimension(600,600));
		JLabel speicherLab = new JLabel("Speichern:");
		speicherLab.setMaximumSize(new Dimension(100,30));
		speicherFenster.add(speicherLab);
		speicherEingabe.setMaximumSize(new Dimension(200,30));
		speicherFenster.add(speicherEingabe);
		JButton speicherBtn = new JButton("Speichern");
		speicherBtn.addActionListener(event -> spielSpeichern(speicherEingabe.getText()));
		speicherFenster.add(speicherBtn);
		JButton schliessenBtn = new JButton("Schließen");
		schliessenBtn.addActionListener(schließen -> speicherFenster.dispose());
		schliessenBtn.setMaximumSize(new Dimension(200,30));
		speicherFenster.add(schliessenBtn);
		speicherFenster.addWindowListener(new FensterSchliessen());
		speicherFenster.setLocation(300, 300);
		speicherFenster.setVisible(true);
		
	}
	public void speichern2(){
		//String s = (String)JOptionPane.showInputDialog(null,"Spiel speichern?",JOptionPane.PLAIN_MESSAGE);

	}
	public void testFenster(){
		JFrame fenster = new JFrame("Test");
		fenster.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JLabel lab1 = new JLabel("Spieler");
		JList list1 = new JList();
		JList list2 = new JList();
		JButton btn1 = new JButton("Drücken");
		
	}
	public void spielSpeichern(String datei){
		try{
			sp.spielSpeichern(datei + ".txt");
			speicherEingabe.setText("Gespeichert");
			speicherEingabe.setEnabled(false);
		}catch(IOException e){
			System.out.println("Spiel konnte nicht gespeichert werden" + e.getMessage());
		}
		
	}

	class FileMenu extends Menu{
		
		public FileMenu() {
			super("File");
			
			MenuItem mi = new MenuItem("Save");
			mi.addActionListener(save -> speichern2());
			add(mi);
			
			addSeparator();
			
			mi = new MenuItem("Quit");
			mi.addActionListener(quit -> System.exit(0));
			add(mi);
		}
		
	}
>>>>>>> origin/master
	
		
}
	


