package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class SpielerPanel extends JPanel{
	JLabel spieler1 = new JLabel();
	JLabel spieler2 = new JLabel();
	JLabel spieler3 = new JLabel();
	JLabel spieler4 = new JLabel();
	JLabel spieler5 = new JLabel();
	JLabel spieler6 = new JLabel();
	JLabel spieler1Fahne = new JLabel();
	JLabel spieler2Fahne = new JLabel();
	JLabel spieler3Fahne = new JLabel();
	JLabel spieler4Fahne = new JLabel();
	JLabel spieler5Fahne = new JLabel();
	JLabel spieler6Fahne = new JLabel();
	private BufferedImage fahneRotImg;
	private BufferedImage fahneBlauImg;
	private BufferedImage fahneGruenImg;
	
	public SpielerPanel(){
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap2","[][]","[][][][][][][]"));
		JLabel header = new JLabel("Spieler:");
		header.setFont(new Font("Impact", Font.PLAIN,30));
		spieler1.setFont(new Font("Impact", Font.PLAIN,30));
		spieler2.setFont(new Font("Impact", Font.PLAIN,30));
		spieler3.setFont(new Font("Impact", Font.PLAIN,30));
		spieler4.setFont(new Font("Impact", Font.PLAIN,30));
		spieler5.setFont(new Font("Impact", Font.PLAIN,30));
		spieler6.setFont(new Font("Impact", Font.PLAIN,30));
		
		try{
			fahneRotImg = ImageIO.read(new File("./Fahne_Rot.png"));
			fahneGruenImg = ImageIO.read(new File("./Fahne_Gruen.png"));
			fahneBlauImg = ImageIO.read(new File("./Fahne_Blau.png"));
			}catch (IOException e){
				
			}
		
		this.add(header,"center,growx,wrap");
		this.add(spieler1,"center");
		this.add(spieler1Fahne, "left");
		this.add(spieler2,"center");
		this.add(spieler2Fahne, "left");
		this.add(spieler3,"center");
		this.add(spieler3Fahne, "left");
		this.add(spieler4,"center");
		this.add(spieler4Fahne, "left");
		this.add(spieler5,"center");
		this.add(spieler5Fahne, "left");
		this.add(spieler6,"center");
		this.add(spieler6Fahne, "left");
		
		
//		this.setPreferredSize(new Dimension(200,350));
	}
	
	public void setLabel(int nummer, String text, String farbe){
		ImageIcon fahne = null;
		switch(farbe){
		case "rot":		fahne = new ImageIcon(fahneRotImg.getScaledInstance(30, 30, Image.SCALE_FAST));
		break;
		case "blau":	fahne = new ImageIcon(fahneBlauImg.getScaledInstance(30, 30, Image.SCALE_FAST));
		break;
		case "gruen":	fahne = new ImageIcon(fahneGruenImg.getScaledInstance(30, 30, Image.SCALE_FAST));
		break;
		}
		
		switch(nummer){
			case 1: spieler1.setText(text);
					spieler1Fahne.setIcon(fahne);
					break;
			case 2: spieler2.setText(text);
					spieler2Fahne.setIcon(fahne);
					break;
			case 3: spieler3.setText(text);
					spieler3Fahne.setIcon(fahne);
					break;
			case 4: spieler4.setText(text);
					spieler4Fahne.setIcon(fahne);
					break;
			case 5: spieler5.setText(text);
					spieler5Fahne.setIcon(fahne);
					break;
			case 6: spieler6.setText(text);
					spieler6Fahne.setIcon(fahne);
					break;
		}
	}
	
}
