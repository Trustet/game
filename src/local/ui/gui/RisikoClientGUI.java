package local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Window;
import java.io.IOException;
import java.awt.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import local.domain.Spielfeld;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Mission;
import local.valueobjects.Spieler;


public class RisikoClientGUI extends Frame{
	private Spielfeld sp;
	private TextField spielerAuflistung = null;
	private List spielerListe = new List();
	JTextField speicherEingabe = new JTextField();
	JTextField spielerName = new JTextField();
	JLabel fehlerLab = new JLabel("");
	JButton erstellen = new JButton("Erstellen");
	
	public RisikoClientGUI(String title){
		super(title);
		sp = new Spielfeld();
		this.start();
//		try {
//			sp.erstelleSpieler("Yannik");
//			sp.erstelleSpieler("Darian");
//			sp.erstelleSpieler("Jesse");
//		} catch (SpielerExistiertBereitsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try{
//			sp.laenderErstellen();
//			sp.laenderverbindungenUndKontinenteErstellen();
//			sp.missionsListeErstellen();
//		}catch(IOException a){
//			System.out.println("Das spiel konnte nicht erstellt werden " + a.getMessage());
//		}
//		sp.missionenVerteilen();
//		sp.laenderAufteilen();
//		//this.speichern();
//		this.initialisieren();
	}
	public static void main(String[] args) {
		Frame fenster = new RisikoClientGUI("Spieler erstellen");

	}
	public void start(){
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(400, 400));
		this.setPreferredSize(new Dimension(400, 400));
		this.setMaximumSize(new Dimension(400,400));
		Panel hauptContainer = new Panel();
		hauptContainer.setLayout(new BoxLayout(hauptContainer, BoxLayout.Y_AXIS));
		Label spielerNameLab = new Label("Spieler:");
		spielerNameLab.setMaximumSize(new Dimension(200,30));
		spielerNameLab.setPreferredSize(new Dimension(200,30));
		spielerName.setMaximumSize(new Dimension(270,30));
		spielerName.setPreferredSize(new Dimension(200,30));
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
		hauptContainer.add(erstellen);
		hauptContainer.add(spielStarten);
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
		fehlerLab.setText(mapWahl);
		if(sp.getSpielerList().size() >= 2){
			try{
				sp.laenderErstellen();
				sp.laenderverbindungenUndKontinenteErstellen();
				sp.missionsListeErstellen();
			}catch(IOException a){
				System.out.println("Das spiel konnte nicht erstellt werden " + a.getMessage());
			}
			sp.missionenVerteilen();
			sp.laenderAufteilen();
			Frame spielfeld = new Frame("Risiko");
			//Hauptfenster
			spielfeld.setLayout(new BoxLayout(spielfeld,BoxLayout.Y_AXIS));
			spielfeld.setMinimumSize(new Dimension(800, 600));
			spielfeld.setPreferredSize(new Dimension(1920, 1080));
			spielfeld.setMaximumSize(new Dimension(5000,1080));
			
			//Container oben
			Panel containerOben = new Panel();
			containerOben.setLayout(new BoxLayout(containerOben, BoxLayout.X_AXIS));
			containerOben.setMaximumSize(new Dimension(5000,930));
			containerOben.setBackground(Color.GREEN);
		
			
			//Container für Spielerausgabe
			Panel spielerAusgabe = new Panel();
			spielerAusgabe.setLayout(new BoxLayout(spielerAusgabe, BoxLayout.Y_AXIS));
			spielerAusgabe.setPreferredSize(new Dimension(150, 800));
			spielerAusgabe.setMaximumSize(new Dimension(150,800));
			spielerAusgabe.setBackground(Color.BLUE);
			containerOben.add(spielerAusgabe);
	
			//Label im Spielerausgabe Container
			Label spielerLab = new Label("Spieler");
			spielerLab.setMaximumSize(new Dimension(100,30));
			spielerAusgabe.add(spielerLab);
	
			
			//Liste für die Spielerausgabe
			spielerAuflisten();
			spielerListe.setMaximumSize(new Dimension(100,150));
			spielerListe.setMinimumSize(new Dimension(100,150));
			spielerAusgabe.add(spielerListe);
			
			//Platzhalter
			Label lab1 = new Label("");
			spielerAusgabe.add(lab1);

			lab1.setMaximumSize(new Dimension(100,800));
		
			//Container unten
			Panel containerUnten = new Panel();
			containerUnten.setLayout(new BoxLayout(containerUnten, BoxLayout.X_AXIS));
			containerUnten.setMinimumSize(new Dimension(1920,180));
			containerUnten.setPreferredSize(new Dimension(1920, 180));
			containerUnten.setMaximumSize(new Dimension(5000,180));
			containerUnten.setBackground(Color.RED);
			
			//Missionsausgabe
			Spieler spieler = sp.getAktiverSpieler();
			Mission m = sp.getSpielerMission(spieler);
			JTextArea mission = new JTextArea("Mission:\n" + m.getBeschreibung() );
			mission.setLineWrap(true);
			mission.setEditable(false);
			mission.setMaximumSize(new Dimension(200,180));
			containerUnten.add(mission);
			
			//Karten
			Panel karten = new Panel();
			karten.setLayout(new BoxLayout(karten, BoxLayout.X_AXIS));
			karten.setMaximumSize(new Dimension(1500,180));
			karten.setMinimumSize(new Dimension(1500,180));
			karten.setBackground(Color.ORANGE);
			containerUnten.add(karten);
			
			//Buttoncontainer
			Panel buttons = new Panel();
			buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
			buttons.setMinimumSize(new Dimension(200,180));
			buttons.setPreferredSize(new Dimension(200,180));
			containerUnten.add(buttons);
			
			//Button speichern
			JButton speicherBtn = new JButton("Speichern");
			buttons.add(speicherBtn);
			speicherBtn.addActionListener(speichern -> speichern());
			
			//Button schließen
			JButton schliessenBtn = new JButton("Schließen");
			buttons.add(schliessenBtn);
			schliessenBtn.addActionListener(schliessen -> System.exit(0));
			
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
			containerOben.add(karteAusgabe);
			
			
			//Fügt die Container dem Hauptfenster zu
		     spielfeld.add(containerOben);
		     spielfeld.add(containerUnten);
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
		Frame speicherFenster = new Frame("Speichern");
		speicherFenster.setLayout(new BoxLayout(speicherFenster, BoxLayout.Y_AXIS));
		speicherFenster.setMinimumSize(new Dimension(200,200));
		speicherFenster.setMaximumSize(new Dimension(200,200));
		JLabel speicherLab = new JLabel("Speichern:");
		speicherLab.setMaximumSize(new Dimension(100,30));
		speicherFenster.add(speicherLab);
		speicherEingabe.setMaximumSize(new Dimension(200,30));
		speicherFenster.add(speicherEingabe);
		JButton speicherBtn = new JButton("Speichern");
		speicherBtn.addActionListener(event -> spielSpeichern(speicherEingabe.getText()));
		speicherFenster.add(speicherBtn);
		JButton schließenBtn = new JButton("Schließen");
		schließenBtn.addActionListener(schließen -> speicherFenster.dispose());
		speicherFenster.add(schließenBtn);
		speicherFenster.setLocation(300, 300);
		speicherFenster.setVisible(true);
		
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
	

}
