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
    int anzahl;
    SpielerPanel spielerListPanel;
    MissionPanel missionPanel;
    JFrame spielFrame;
    
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
		JPanel panel = new JPanel(new MigLayout("wrap2","[][150]","[][][][][]"));
		frame.setLocationRelativeTo(null);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Objekte erstellen
		JLabel nameLab = new JLabel("Name:");
		JTextField nameText = new JTextField();
		nameText.setSize(150, 30);
		JLabel ipLab = new JLabel("IP:");
		JTextField ipText = new JTextField();
		JLabel portLab = new JLabel("Port:");
		JTextField portText = new JTextField();
		String[] zahlen = {"2","3","4","5","6"};
		JLabel anzahlLab = new JLabel("Spieler Anzahl:");
		JComboBox<String> anzahlCBox = new JComboBox<String>(zahlen);
		JButton startBtn = new JButton("Spiel starten");
		
		//Actionlistener
		startBtn.addActionListener(start -> spiel(nameText.getText(), Integer.parseInt((String)anzahlCBox.getSelectedItem()),frame,ipText.getText(),Integer.parseInt(portText.getText())));
	
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
    public void spiel(String name, int anzahl,JFrame frameStart,String ip, int port) {
    	//verbindungAufbauen(ip,port);
    	this.anzahl = anzahl;
    	try{
    		sp.erstelleSpieler("Yannik");
    		sp.erstelleSpieler(name);
	    	
	    	frameStart.dispose();
	    	
	        spielFrame = new JFrame("Risiko");
	        spielFrame.setLocationRelativeTo(null);
	        spielFrame.setSize(1400,750);
	        spielFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	        JPanel panel = new JPanel(new MigLayout("debug,wrap2","[][]","[][][][]"));
	        spielFrame.add(panel);
	        //JTextArea spielfeld = new JTextArea("Weltkarte",30,20);
	        JTextArea karten = new JTextArea("Karten",10,20);
	        JTextArea statistik = new JTextArea("Statistik",15,20);


//	        BufferedImage myPicture;
//			try {
//				myPicture = ImageIO.read(new File("./weltkarte.jpg"));
//				spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 550, Image.SCALE_FAST)));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	        MapPanel spielfeld = new MapPanel(this);
	        spielerListPanel = new SpielerPanel();
	        missionPanel = new MissionPanel();
	        InfoPanel infoPanel = new InfoPanel(sp.getTurn()+"",sp.getAktiverSpieler().getName());
	        ButtonPanel buttonPanel = new ButtonPanel(infoPanel,sp);
	        
//			next.addActionListener(phase -> phaseAusgeben());
			

	        missionen.setBorder(schwarz);
	        karten.setBorder(schwarz);
	        statistik.setBorder(schwarz);
	        spielfeld.setBorder(schwarz);
	        
	  
	        panel.add(spielfeld,"left,spany 3,growx,growy");
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
        	if(spieler1Lab.getText().equals("Warten auf Spieler...")){
        		spieler1Lab.setText(name);
        	}else if(spieler2Lab.getText().equals("Warten auf Spieler...")){
        		spieler2Lab.setText(name);
        	}else if(spieler3Lab.getText().equals("Warten auf Spieler...")){
        		spieler3Lab.setText(name);
        	}else if(spieler4Lab.getText().equals("Warten auf Spieler...")){
        		spieler4Lab.setText(name);
        	}else if(spieler5Lab.getText().equals("Warten auf Spieler...")){
        		spieler5Lab.setText(name);
        	}else if(spieler6Lab.getText().equals("Warten auf Spieler...")){
        		spieler6Lab.setText(name);
        	}
        
    	}catch (SpielerExistiertBereitsException sebe) {
    		 JOptionPane.showMessageDialog(null,sebe.getMessage(),"Name vergeben",JOptionPane.WARNING_MESSAGE);
    	}
    	if(sp.getSpielerList().size() == anzahl){
    		try{
				sp.laenderErstellen();
				sp.laenderverbindungenUndKontinenteErstellen();
				sp.missionsListeErstellen();
			}catch(IOException e){
				JOptionPane.showMessageDialog(null,e.getMessage(),"Fehler",JOptionPane.WARNING_MESSAGE);
			}
			sp.missionenVerteilen();
			sp.laenderAufteilen();
			missionen.setText(sp.getSpielerMission(sp.getAktiverSpieler()).getBeschreibung());
    	}
    	
    	
    }
    
    public void phaseAusgeben(){
    	spielerListPanel.setLabel(1, "Yannik");
    	missionPanel.setMBeschreibung(1, "�bernehme volgende Laender", "Asien und Europa");
    	
//    	out.println("Phase");
//    	
//    	try{
//    		String phase = in.readLine();
//    		System.out.println(phase);
//    	}catch(IOException e){
//    		
//    	}
    }
//    public void verbindungAufbauen(String host, int port){
//    	try{
//			socket = new Socket(host,port);
//			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			out = new PrintStream(socket.getOutputStream());
//		}catch(IOException e){
//			
//		}
//		try{
//			String message = in.readLine();
//			System.out.print(message);
//		}catch(IOException e){
//			
//		}
//    }


		//spielStarten.addActionListener(starten -> this.risiko(mapWahl.getSelectedItem().toString()));
    class ImagePanel extends JComponent {
        private Image image;
        private int b;
        private int h;
        public ImagePanel(Image image,int b, int h) {
            this.image = image;
            this.b = b;
            this.h = h;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, b, h, this);
        }
    }


	@Override
	public void processMouseClick(int x, int y, Color color) {
		
		System.out.println("Farbcode an der Stelle [" + x + "/" + y + "] = " + color.getRed() + color.getGreen() + color.getBlue());
	}

}
	




