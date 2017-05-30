package local.ui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;


public class RisikoClientGUI extends JFrame{
	Border schwarz = BorderFactory.createLineBorder(Color.black);
	
	public RisikoClientGUI(){
		this.start();
	}
	public static void main(String[] args) {
		JFrame fenster = new RisikoClientGUI();

	}
	public void start(){
		JFrame frame = new JFrame("Spiel starten");
		frame.setSize(500,700);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBackground(Color.GRAY);
		JPanel panel = new JPanel(new MigLayout("wrap1","[]","[][][][][]"));
		BufferedImage background;
		BufferedImage logoImg;
		JLabel logo = new JLabel();
		try {
			logoImg = ImageIO.read(new File("./logo.jpeg"));
			logo = new JLabel(new ImageIcon(logoImg.getScaledInstance(300, 150, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JButton startBtn = new JButton("Spiel erstellen");
		JButton ladenBtn = new JButton("Spiel laden");
		JButton optionBtn = new JButton("Optionen");
		JButton beendenBtn = new JButton("Beenden");
		
		startBtn.addActionListener(start -> spielErstellen(frame));
		beendenBtn.addActionListener(close -> System.exit(0));
		
		panel.add(logo,"center");
		panel.add(startBtn,"center,growx");
		panel.add(ladenBtn,"center,growx");
		panel.add(optionBtn,"center,growx");
		panel.add(beendenBtn,"center,growx");
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);


		
	}

	public void spielErstellen(JFrame frameStart){
		frameStart.dispose();
		String[] zahlen = {"2","3","4","5","6"};
		JFrame frame = new JFrame("Spiel erstellen");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		JPanel panel = new JPanel(new MigLayout("wrap2","[][150]","[40][][40][]"));
		JLabel nameLab = new JLabel("Name:");
		JTextField nameText = new JTextField();
		nameText.setSize(150, 30);
		JLabel ipLab = new JLabel("IP:");
		JTextField ipText = new JTextField();
		JLabel anzahlLab = new JLabel("Spieler Anzahl:");
		JComboBox anzahlCBox = new JComboBox(zahlen);
		JButton startBtn = new JButton("Spiel starten");
		
		startBtn.addActionListener(start -> spiel(nameText.getText(), Integer.parseInt((String)anzahlCBox.getSelectedItem())));
		panel.add(nameLab,"right");
		panel.add(nameText,"left,growx");
		panel.add(ipLab,"right");
		panel.add(ipText,"left,growx");
		panel.add(anzahlLab,"left");
		panel.add(anzahlCBox,"left");
		panel.add(startBtn,"center,spanx2");
		
		frame.add(panel);
		frame.setVisible(true);
	}
    public void spiel(String name, int anzahl) {
    	
        JFrame frame = new JFrame("Risiko");
        frame.setSize(1400,750);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new MigLayout("debug,wrap2","[][]","[][30][30][30][30][30][30][][][]"));
        frame.add(panel);
        //JTextArea spielfeld = new JTextArea("Weltkarte",30,20);
        JTextArea platzhalter = new JTextArea("platzhalter",10,20);
        JLabel spieler1Lab = new JLabel("Warten auf Spieler...");
        JLabel spieler2Lab = new JLabel("Warten auf Spieler...");
        JLabel spieler3Lab = new JLabel("Warten auf Spieler...");
        JLabel spieler4Lab = new JLabel("Warten auf Spieler...");
        JLabel spieler5Lab = new JLabel("Warten auf Spieler...");
        JLabel spieler6Lab = new JLabel("Warten auf Spieler...");
        System.out.print(anzahl);
        spieler1Lab.setText(name);
        for(int i = 2; i <= anzahl; i++){
        	switch(i){
        		case 2: 
        			spieler2Lab.setText("Hans");
        			break;
        		case 3: 
        			spieler3Lab.setText("Peter");
        			break;
        		case 4: 
        			spieler4Lab.setText("Mark");
        			break;
        	}
        }
        
        JTextArea missionen = new JTextArea("Missionen",10,20);
        JTextArea karten = new JTextArea("Karten",10,20);
        JTextArea statistik = new JTextArea("Statistik",15,20);
        JButton next = new JButton("Naechste Phase");
        JLabel spielfeld = null;
        BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("./welt.jpg"));
			spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 550, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
		platzhalter.setBorder(schwarz);
        missionen.setBorder(schwarz);
        karten.setBorder(schwarz);
        statistik.setBorder(schwarz);
        spielfeld.setBorder(schwarz);
        
        
        panel.add(spielfeld,"left,spany 8,growx,growy");
        panel.add(platzhalter,"left");
        panel.add(spieler1Lab,"center");
        panel.add(spieler2Lab,"center");
        panel.add(spieler3Lab,"center");
        panel.add(spieler4Lab,"center");
        panel.add(spieler5Lab,"center");
        panel.add(spieler6Lab,"center");
        panel.add(statistik,"left,top,growy");
        panel.add(missionen,"left,split2");
        panel.add(karten,"left,growx");
        panel.add(next,"center,growy,growx");
        frame.setResizable(false);
        frame.setVisible(true);
    }


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

}
	




