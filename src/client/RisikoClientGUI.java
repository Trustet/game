//TODO Angriffsphase erweitern (Yannik)
//TODO Verschieben vervollständigen (Jesse)
//TODO Missionen ausgeben
//TODO Missionenlogik
//TODO Mehr missionen erstellen
//TODO mehr Farben und Fahnen implementieren
//TODO Karten ausgeben
//TODO Kartenlogik
//TODO Wuerfel im MapPanel anzeigen 
//TODO Aktiver spieler anzeigen (Yannik)
//TODO GUI komplett aufr�umen (Vllt alles in ein Frame)

package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.ButtonPanel.ButtonClickHandler;
import client.MapPanel.MapClickHandler;
import local.domain.Spielfeld;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinGegnerException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.NichtGenugEinheitenException;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Angriff;
import local.valueobjects.AngriffRueckgabe;
import local.valueobjects.Land;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;


public class RisikoClientGUI extends JFrame implements MapClickHandler, ButtonClickHandler {
	Spielfeld sp = new Spielfeld();

    int anzahlSpieler; //von anzahl in anzahlSpieler umbenannt
    private SpielerPanel spielerListPanel;
    private MissionPanel missionPanel;
    private JFrame spielFrame;
    private MapPanel spielfeld;
    private InfoPanel infoPanel;
    private ButtonPanel buttonPanel;
    private StatistikPanel statistikPanel;
    private ConsolePanel consolePanel;
    private Font schrift;
    private Font uberschrift;
    private Land land1 = null;
    private Land land2 = null;
    private int anzahlSetzbareEinheiten;
    boolean startphase;
    int spielerDieVerteiltHaben;
    
//    private Socket socket = null;
//    private BufferedReader in;
//    private PrintStream out;
    
    
	public RisikoClientGUI(){
		this.start();
	}
	public static void main(String[] args) {
		new RisikoClientGUI();
	}
	public void start() {
		uberschrift = new Font(Font.SERIF, Font.BOLD,25);
		schrift = new Font(Font.SANS_SERIF, Font.PLAIN,20);
		
		//Frame und Layout
		JFrame frame = new JFrame("Spiel starten");
		JPanel panel = new JPanel(new MigLayout("wrap1","[]","[][][][][][]"));
		frame.setLocationRelativeTo(null);
		frame.setSize(500,700);
//		panel.setBackground(new Color(220,175,116));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		//Logo wird eingebunden
		BufferedImage logoImg;
		JLabel logo = new JLabel();
		try {
			logoImg = ImageIO.read(new File("./logo.jpeg"));
			logo = new JLabel(new ImageIcon(logoImg.getScaledInstance(300, 150, Image.SCALE_FAST)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Objekte erstellen
		JButton startBtn = new JButton("Spiel erstellen");
		JButton ladenBtn = new JButton("Spiel laden");
		JButton optionBtn = new JButton("Optionen");
		JButton beendenBtn = new JButton("Beenden");
		
		
		//ActionListener
		startBtn.addActionListener(start -> spielErstellen(frame));
		beendenBtn.addActionListener(close -> System.exit(0));
		
		//Objekte hinzufügen
		panel.add(logo,"center");
		panel.add(startBtn,"center,growx");
		panel.add(ladenBtn,"center,growx");
		panel.add(optionBtn,"center,growx");
		panel.add(beendenBtn,"center,growx");
		frame.add(panel);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		


		
	}

	public void spielErstellen(JFrame frameStart){
		
		//Schließt das vorherige Fenster
		frameStart.dispose();
		
		//Frame und Layout
		JFrame frame = new JFrame("Spiel erstellen");
		JPanel panel = new JPanel(new MigLayout("debug, wrap2","[][150]","[][][][][]")); 
		frame.setLocationRelativeTo(null); 
//		panel.setBackground(new Color(220,175,116));
		frame.setSize(280, 200); //von 300 auf 280 gestellt //FRAGE: kann man panel zentrieren?
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//Objekte erstellen
		JLabel nameLab = new JLabel("Name:");
		JTextField nameText = new JTextField();
		JLabel ipLab = new JLabel("IP:");
		JTextField ipText = new JTextField();
		JLabel portLab = new JLabel("Port:");
		JTextField portText = new JTextField();
		String[] zahlen = {"2","3","4","5","6"};
		JLabel anzahlLab = new JLabel("Spieler Anzahl:");
		JComboBox<String> anzahlCBox = new JComboBox<String>(zahlen);
		JButton startBtn = new JButton("Spiel starten");
		
		//Actionlistener
		startBtn.addActionListener(start -> spiel(nameText.getText(), Integer.parseInt((String)anzahlCBox.getSelectedItem()),frame));
		
		
		//Objekte hinzufügen
		panel.add(nameLab,"right");
		panel.add(nameText,"left,growx");
		panel.add(ipLab,"right");
		panel.add(ipText,"left,growx");
		panel.add(portLab,"right");
		panel.add(portText,"left,growx");
		panel.add(anzahlLab,"left");
		panel.add(anzahlCBox,"left");
		panel.add(startBtn,"center,spanx2");
		frame.add(panel);
		frame.setVisible(true);
	
	}
    public void spiel(String name, int anzahlSpieler,JFrame frameStart) {
    	//verbindungAufbauen(ip,port);
    	this.anzahlSpieler = anzahlSpieler;
    	for(int i = 1; i < anzahlSpieler; i++ ){
    		neuerSpieler();
    	}
    	try{
    		sp.erstelleSpieler(name); 
	    	
	    	frameStart.dispose();
	    	
	        spielFrame = new JFrame("Risiko");
	        spielFrame.setLocationRelativeTo(null);
	        spielFrame.setPreferredSize(new Dimension(1250,780));
	        spielFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

	        JPanel panel = new JPanel(new MigLayout(" wrap2","[][]","[][][]")); // hier "debug,wrap2" schreiben für Debug-Modus
			
	        spielFrame.add(panel);

	        spielfeld = new MapPanel(this, schrift);
	        spielerListPanel = new SpielerPanel(schrift, uberschrift);
	        missionPanel = new MissionPanel(uberschrift, schrift);
	        infoPanel = new InfoPanel(sp.getTurn()+"",sp.getAktiverSpieler().getName(), schrift, uberschrift);
	        buttonPanel = new ButtonPanel(this);
	        statistikPanel = new StatistikPanel(sp.getSpielerList(), sp.getLaenderListe(), schrift, uberschrift);
	        consolePanel = new ConsolePanel(schrift);
		
	  
	        panel.add(spielfeld,"left,spany 3,grow,hmin 550, wmin 1050");
	        panel.add(infoPanel,"left,growx");
	        panel.add(spielerListPanel,"growx");
	        panel.add(statistikPanel,"left,top,growx,spany 2");
	        panel.add(missionPanel,"left,top,split3");
	        panel.add(consolePanel,"left, top");
	        panel.add(buttonPanel,"right,growy");
	        spielFrame.setResizable(false);
	        spielFrame.setVisible(true);
	        spielFrame.pack();
	        

	  	        
    	}catch(SpielerExistiertBereitsException sebe){
    		JOptionPane.showMessageDialog(null,sebe.getMessage(),"Name vergeben",JOptionPane.WARNING_MESSAGE);
    	}
    		startphase = true;
    		
    		
    		sp.setTurn("VERTEILEN");
    		spielerDieVerteiltHaben = 0;
        	anzahlSetzbareEinheiten = sp.checkAnfangsEinheiten();
        	consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " du kannst nun deine ersten Einheiten setzen. Es sind " + anzahlSetzbareEinheiten);
		}
 
    public void neuerSpieler(){
    	JFrame frame = new JFrame("Spieler erstellen");
    	frame.setSize(150, 300);
    	frame.setLayout(new MigLayout("wrap 2","[][100]","[][]"));
    	JLabel nameLab = new JLabel("Name:");
    	JTextField nameText = new JTextField();
    	JButton erstellenBtn = new JButton("Erstellen");
    	
    	erstellenBtn.addActionListener(erstellen -> spielerErstellen(frame,nameText.getText()));
    	
    	frame.add(nameLab,"right");
    	frame.add(nameText, "left,growx");
    	frame.add(erstellenBtn, "center,spanx 2");
    	frame.setVisible(true);
    }
    public void spielerErstellen(JFrame frame, String name){
    	try{
    		sp.erstelleSpieler(name);
    		frame.dispose();
      
    	}catch (SpielerExistiertBereitsException sebe) {
    		 JOptionPane.showMessageDialog(null,sebe.getMessage(),"Name vergeben",JOptionPane.WARNING_MESSAGE);
    	}
    	
    	if(sp.getSpielerList().size() == anzahlSpieler){
    		try{
				sp.laenderErstellen();
				sp.laenderverbindungenUndKontinenteErstellen();
				sp.missionsListeErstellen();
			}catch(IOException e){
				JOptionPane.showMessageDialog(null,e.getMessage(),"Fehler",JOptionPane.WARNING_MESSAGE);
			}
			sp.missionenVerteilen();
			sp.laenderAufteilen();
			farbenVerteilen();
			spielfeld.fahnenVerteilen(sp.getLaenderListe());
			int spielerNr = 1;
    		for(Spieler s : sp.getSpielerList()){
    			spielerListPanel.setLabel(spielerNr, s.getName(), s.getFarbe());
    			spielerNr++;
    		}
    		
    		statistikPanel.statistikAktualisieren();
		
    	}
    }
    public void farbenVerteilen(){
    	List<String> farben = new Vector<String>();
    	farben.add("rot");
    	farben.add("gruen");
    	farben.add("blau");
    	farben.add("gelb");
    	farben.add("orange");
    	farben.add("cyan");
    	
    	for(Spieler s : sp.getSpielerList()){
    		s.setFarbe(farben.get(0));
    		farben.remove(0);
    	}
    }

	@Override
	public void processMouseClick(Color color) {
		String landcode = color.getRed() + "" + color.getGreen() + "" + color.getBlue();
		landWaehlen(landcode);
		
	}
	public void landWaehlen(String landcode){
		
		String landstring = sp.getLandVonFarbcode(landcode);
		Land land = sp.stringToLand(landstring);
		if(land != null){
			spielfeld.labelsSetzen(land.getName(), land.getEinheiten(), land.getBesitzer().getName());
			Spieler spieler = sp.getAktiverSpieler();
			switch(sp.getTurn()){
			case ANGRIFF:
				angreifen(landstring, land, spieler);
				break;
			case VERTEILEN:
				
				verteilen(landstring, land);
				break;
			case VERSCHIEBEN:
				verschieben(landstring, land);
				break;
			}
		}
	}
	@Override
	public void buttonClicked() {
		sp.nextTurn();
		
		switch(sp.getTurn()){
		case ANGRIFF:
			consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " du kannst nun angreifen.");
			buttonPanel.angreifenAktiv("angreifendes Land","verteidigendes Land");
			break;
		case VERTEILEN:
			anzahlSetzbareEinheiten = sp.bekommtEinheiten(sp.getAktiverSpieler());
			consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " du kannst " + anzahlSetzbareEinheiten + " Einheiten setzen.");
			buttonPanel.verteilenAktiv(anzahlSetzbareEinheiten);
			break;
		case VERSCHIEBEN:
			consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " verschiebe nun deine Einheiten.");
			buttonPanel.verschiebenAktiv("erstes land","zweites land");
			break;
		}
		
		infoPanel.changePanel(sp.getTurn()+"");
		
	}
	
	public void verteilen(String landstring, Land land)	{
		try{
			sp.landWaehlen(landstring,sp.getAktiverSpieler());
			if(anzahlSetzbareEinheiten > 0)
			{
				sp.einheitenPositionieren(1, land);
				anzahlSetzbareEinheiten--;
				spielfeld.labelsSetzen("", land.getEinheiten(), "");
				spielfeld.fahneEinheit(land.getEinheitenLab());
				statistikPanel.statistikPanelAktualisieren();
			} else {
				consolePanel.textSetzen("Du hast alle Einheiten gesetzt.");
				
			}
		}catch(KannLandNichtBenutzenException lene ){
			consolePanel.textSetzen(lene.getMessage());
		}
		
    	if((startphase == true) && (anzahlSetzbareEinheiten == 0))
    	{
    		spielerDieVerteiltHaben++;
    		
    		if(spielerDieVerteiltHaben == anzahlSpieler)
    		{
    			startphase = false;
    			sp.naechsterSpieler();
    			buttonClicked();
    			buttonPanel.buttonFreigeben();
    		} else {
    		anzahlSetzbareEinheiten = sp.checkAnfangsEinheiten();
    		sp.naechsterSpieler();
        	consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " du kannst nun deine ersten Einheiten setzen. Es sind " + anzahlSetzbareEinheiten);
    		}
    	}
	}
	
	public void angreifen(String landstring, Land land, Spieler spieler)	{
		
		if(land1 == null){	
			try{
				sp.landWaehlen(landstring,spieler);
				sp.checkEinheiten(landstring, 2);
				land1 = land;
			} catch(KannLandNichtBenutzenException lene){
				consolePanel.textSetzen(lene.getMessage());
			} catch (NichtGenugEinheitenException e) {
				consolePanel.textSetzen(e.getMessage());
			}
		}else{
			
				try {
					sp.istNachbar(land1, land, spieler);
					sp.istGegner(landstring, spieler);
					land2 = land;
					angriff(true, spieler);
					spielfeld.fahneEinheit(land1.getEinheitenLab());
					spielfeld.fahneEinheit(land2.getEinheitenLab());
					land1 = null;
					land2 = null;
				} catch (KeinNachbarlandException knle) {
					try{
						sp.landWaehlen(landstring, spieler);
						land1 = land;
					} catch(KannLandNichtBenutzenException lene){
						consolePanel.textSetzen(knle.getMessage());
					}							
				} catch(KeinGegnerException kge){
					try{
						sp.landWaehlen(landstring, spieler);
					} catch(KannLandNichtBenutzenException lene){
						consolePanel.textSetzen(lene.getMessage());
					}
				}
			
		}
	}
	
	public void verschieben(String landstring, Land land)	{
		if(land1 == null){
			try{
				sp.landWaehlen(landstring,sp.getAktiverSpieler());
				land1 = land;
			} catch(KannLandNichtBenutzenException lene){
				consolePanel.textSetzen(lene.getMessage());
			}
		}else{
			
				try {
					sp.istNachbar(land1, land, sp.getAktiverSpieler());
					sp.landWaehlen(landstring,sp.getAktiverSpieler());
					land2 = land;
					sp.einheitenPositionieren(-1, land1);
					sp.einheitenPositionieren(1, land2);
					spielfeld.labelsSetzen("", land1.getEinheiten(), "");
					spielfeld.fahneEinheit(land1.getEinheitenLab());
					spielfeld.labelsSetzen("", land2.getEinheiten(), "");
					spielfeld.fahneEinheit(land2.getEinheitenLab());
					land1 = null;
					land2 = null;
				} catch (KeinNachbarlandException knle) {
					try{
						sp.landWaehlen(landstring,sp.getAktiverSpieler());
						land1 = land;
					} catch(KannLandNichtBenutzenException lene){
						consolePanel.textSetzen(lene.getMessage());
					}							
				} catch (KannLandNichtBenutzenException e) {
					consolePanel.textSetzen(e.getMessage());
					
				}
			
		}
	}
	
	private void angriff( boolean genugEinheiten, Spieler aSpieler) throws KeinNachbarlandException{
		Angriff angriff;
		AngriffRueckgabe angriffRueckgabe;
			Land aLand = land1;
			Land vLand = land2;
			angriff = new Angriff(aLand, vLand);
			angriffRueckgabe = sp.befreiungsAktion(angriff);
			String ausgabe;
			
			ausgabe = aLand.getBesitzer().getName() + " hat ";
			
			if(angriffRueckgabe.getWuerfelAngreifer().size() == 2)
			{
				ausgabe += angriffRueckgabe.getWuerfelAngreifer().get(0) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(1);
			} else if (angriffRueckgabe.getWuerfelAngreifer().size() == 3)
			{
				ausgabe += angriffRueckgabe.getWuerfelAngreifer().get(0) + ", " + angriffRueckgabe.getWuerfelAngreifer().get(1) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(2);
			}
				ausgabe += (" gew�rfelt.\n");
			
			ausgabe += vLand.getBesitzer().getName() + " hat ";
				
			if(angriffRueckgabe.getWuerfelVerteidiger().size() == 1)
			{
				ausgabe += " eine " + angriffRueckgabe.getWuerfelVerteidiger().get(0);
			} else if (angriffRueckgabe.getWuerfelVerteidiger().size() == 2)
			{
				ausgabe += angriffRueckgabe.getWuerfelVerteidiger().get(0) + " und " + angriffRueckgabe.getWuerfelAngreifer().get(1);
			}
			
			ausgabe += " gew�rfelt.\n";
				
			if(angriffRueckgabe.isErobert() != true){
					
				if (angriffRueckgabe.getVerlusteVerteidiger() < angriffRueckgabe.getVerlusteAngreifer()){
						ausgabe += vLand.getBesitzer().getName() + " hat gewonnen." ;
						ausgabe += aLand.getBesitzer().getName() + " hat " + angriffRueckgabe.getVerlusteAngreifer() + " verloren.";
						
				}else if(angriffRueckgabe.getVerlusteVerteidiger() > angriffRueckgabe.getVerlusteAngreifer()){
						ausgabe += aLand.getBesitzer().getName() + " hat gewonnen." ;
						ausgabe += vLand.getBesitzer().getName() + " hat " + angriffRueckgabe.getVerlusteVerteidiger() + " verloren.";
						
				}else if(angriffRueckgabe.getVerlusteVerteidiger() == angriffRueckgabe.getVerlusteAngreifer()){
						ausgabe += "Ihr habt unentschieden gespielt, beide verlieren eine Einheit." ;
				}
				
				
			} else {
				vLand.setFahne(aSpieler.getFarbe());
				spielfeld.fahnenVerteilen(sp.getLaenderListe());
				ausgabe += aLand.getBesitzer().getName() + " hat das Land erobert." ;
				genugEinheiten = false;
					if(aLand.getEinheiten() == 2) {
						ausgabe += "Eine Einheit wird auf " + vLand.getName() + " gesetzt.";
						sp.eroberungBesetzen(aLand, vLand, 1); 
						ausgabe += sp.einheitenAusgabe(aLand, vLand);
						genugEinheiten = true;
					} else {
					
						ausgabe += "Wie viele Einheiten m\u00F6chtest du auf " + vLand.getName() + " setzen?";
						ausgabe += aLand.getEinheiten() - 1 + " Einheiten kannst du setzen";
						int einheiten = 1;
						if(einheiten < aLand.getEinheiten() && einheiten > 0){
							sp.eroberungBesetzen(aLand, vLand, einheiten); 
							ausgabe += sp.einheitenAusgabe(aLand, vLand);
							genugEinheiten = true;
						}else{
							ausgabe += "Bitte gebe eine Korrekte Zahl ein";
						}
					
						
						
				}
			}
			
			consolePanel.textSetzen(ausgabe);
	}


}
	




