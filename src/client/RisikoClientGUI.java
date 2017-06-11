package client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import client.MapPanel.MapClickHandler;
import local.domain.Spielfeld;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;


public class RisikoClientGUI extends JFrame implements MapClickHandler {
	Spielfeld sp = new Spielfeld();
	Border schwarz = BorderFactory.createLineBorder(Color.black);
	JLabel spieler1Lab = new JLabel();
    JLabel spieler2Lab = new JLabel();
    JLabel spieler3Lab = new JLabel();
    JLabel spieler4Lab = new JLabel();
    JLabel spieler5Lab = new JLabel();
    JLabel spieler6Lab = new JLabel();
    JLabel missionen = new JLabel();
    int anzahlSpieler; //von anzahl in anzahlSpieler umbenannt
    SpielerPanel spielerListPanel;
    MissionPanel missionPanel;
    JFrame spielFrame;
    private MapPanel spielfeld;
    
    private Socket socket = null;
    private BufferedReader in;
    private PrintStream out;
    
	public RisikoClientGUI(){
		this.start();
	}
	public static void main(String[] args) {
		JFrame fenster = new RisikoClientGUI();
	}
	public void start() {
		
		//Frame und Layout
		JFrame frame = new JFrame("Spiel starten");
		JPanel panel = new JPanel(new MigLayout("wrap1","[]","[][][][][][]"));
		frame.setLocationRelativeTo(null);
		frame.setSize(500,700);
		frame.setBackground(Color.GRAY);
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
	        spielFrame.setSize(1400,750);
	        spielFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        JPanel panel = new JPanel(new MigLayout("debug,wrap2","[][]","[][][][]")); // hier "debug,wrap2" schreiben für Debug-Modus
	        spielFrame.add(panel);
	        //JTextArea spielfeld = new JTextArea("Weltkarte",30,20);
	        JTextArea karten = new JTextArea("Karten",10,20);
	        JTextArea statistik = new JTextArea("Statistik",8,20);


	        spielfeld = new MapPanel(this, sp);
	        spielerListPanel = new SpielerPanel();
	        missionPanel = new MissionPanel();
	        InfoPanel infoPanel = new InfoPanel(sp.getTurn()+"",sp.getAktiverSpieler().getName());
	        ButtonPanel buttonPanel = new ButtonPanel(infoPanel,sp);
	        
//			next.addActionListener(phase -> phaseAusgeben());
			

	        missionen.setBorder(schwarz);
	        karten.setBorder(schwarz);
	        statistik.setBorder(schwarz);
	        spielfeld.setBorder(schwarz);
	        
	  
	        panel.add(spielfeld,"left,spany 3,growx,growy,hmin 550, wmin 1050");
	        panel.add(infoPanel,"left");
	        panel.add(spielerListPanel,"growx,growy");
	        panel.add(statistik,"left,top,growy");
	        panel.add(missionPanel,"left,split2");
	        panel.add(karten,"left,growx");
	        panel.add(buttonPanel,"center,growy,growx");
	        spielFrame.setResizable(false);
	        spielFrame.setVisible(true);

	  	        
    	}catch(SpielerExistiertBereitsException sebe){
    		JOptionPane.showMessageDialog(null,sebe.getMessage(),"Name vergeben",JOptionPane.WARNING_MESSAGE);
    	}
    	
    	
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
			spielfeld.fahnenVerteilen();
			int spielerNr = 1;
    		for(Spieler s : sp.getSpielerList()){
    			spielerListPanel.setLabel(spielerNr, s.getName(), s.getFarbe());
    			spielerNr++;
    		}
			
			missionen.setText(sp.getSpielerMission(sp.getAktiverSpieler()).getBeschreibung());
    	}
    }
    
  
    public void farbenVerteilen(){
    	List<String> farben = new Vector<String>();
    	farben.add("rot");
    	farben.add("gruen");
    	farben.add("blau");
    	
    	for(Spieler s : sp.getSpielerList()){
    		s.setFarbe(farben.get(0));
    		farben.remove(0);
    	}
    }
		//spielStarten.addActionListener(starten -> this.risiko(mapWahl.getSelectedItem().toString()));
    


	@Override
	public void processMouseClick(int x, int y, Color color) {
		
		System.out.println("Farbcode an der Stelle [" + x + "/" + y + "] = " + color.getRed() + color.getGreen() + color.getBlue());
	}

}
	




