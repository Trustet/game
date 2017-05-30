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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;


public class RisikoClientGUI extends JFrame{
	Border schwarz = BorderFactory.createLineBorder(Color.black);
	
	public RisikoClientGUI(){
		this.spiel();
	}
	public static void main(String[] args) {
		JFrame fenster = new RisikoClientGUI();

	}
	public void start(){
		JFrame frame = new JFrame("Spiel starten");
		frame.setSize(500,700);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLayout(new MigLayout("debug,insets 0,wrap1","[]","[][][][]"));
		//JPanel panel = new JPanel(new MigLayout("wrap1","[]","[][][][]"));
		BufferedImage background;
		BufferedImage logoImg;
		JLabel logo = new JLabel();
		try {
			background = ImageIO.read(new File("./holz.jpg"));
			JLabel backgroundImg = new JLabel(new ImageIcon(background.getScaledInstance(500, 700, Image.SCALE_FAST)));
			frame.getContentPane().add(backgroundImg,"cell 0 0,grow");
			
			logoImg = ImageIO.read(new File("./logo.jpeg"));
			logo = new JLabel(new ImageIcon(logoImg.getScaledInstance(200, 100, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.add(logo);
		frame.pack();
		frame.setVisible(true);

		
		
	}

    public void spiel() {
    	
        JFrame frame = new JFrame("Finden");
        frame.setSize(1400,750);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new MigLayout("debug,wrap2","[][]","[][][][]"));
        frame.add(panel);
        //JTextArea spielfeld = new JTextArea("Weltkarte",30,20);
        JTextArea platzhalter = new JTextArea("platzhalter",10,20);
        JTextArea spieler = new JTextArea("Spielerliste",20,20);
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
        spieler.setBorder(schwarz);
        missionen.setBorder(schwarz);
        karten.setBorder(schwarz);
        statistik.setBorder(schwarz);
        spielfeld.setBorder(schwarz);
        
        
        panel.add(spielfeld,"left,spany 3,growx,growy");
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
	




