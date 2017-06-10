package local.valueobjects;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Land {
	private String name;
	private Spieler besitzer;
	private int einheiten;
	private String kurzel;
	private int fahneX;
	private int fahneY;
	private JLabel fahne;
	private BufferedImage fahneRotImg;
	private BufferedImage fahneBlauImg;
	private BufferedImage fahneGruenImg;
	

	public Land(String name, Spieler besitzer, int einheiten, String kurzel, int fahneX, int fahneY) {
		this.name = name;
		this.besitzer = besitzer;
		this.einheiten = einheiten;
		this.kurzel = kurzel;
		this.fahneX = fahneX;
		this.fahneY = fahneY;
		try{
		fahneRotImg = ImageIO.read(new File("./Fahne_Rot.png"));
		fahneGruenImg = ImageIO.read(new File("./Fahne_Gruen.png"));
		fahneBlauImg = ImageIO.read(new File("./Fahne_Blau.png"));
		}catch (IOException e){
			
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Spieler getBesitzer() {
		return besitzer;
	}

	public void setBesitzer(Spieler besitzer) {
		this.besitzer = besitzer;
	}

	public int getEinheiten() {
		return einheiten;
	}

	public void setEinheiten(int einheiten) {
		this.einheiten = einheiten;
	}
	public String getKuerzel(){
		return kurzel;
	}
	
	public void setFahne(int x, int y){
		this.fahneX = x;
		this.fahneY = y;
	}
	
	public int getFahneX(){
		return fahneX;
	}
	
	public int getFahneY(){
		return fahneY;
	}
	
	public void setFahne(String farbe){
		
		switch(farbe){
		case "rot":		fahne = new JLabel(new ImageIcon(fahneRotImg.getScaledInstance(20, 20, Image.SCALE_FAST)));
		break;
		case "blau":	fahne = new JLabel(new ImageIcon(fahneBlauImg.getScaledInstance(20, 20, Image.SCALE_FAST)));
		break;
		case "gruen":	fahne = new JLabel(new ImageIcon(fahneGruenImg.getScaledInstance(20, 20, Image.SCALE_FAST)));
		break;
		}
		fahne.setBounds(fahneX, fahneY, 20, 20);
	}
	
	public JLabel getFahne(){
		return this.fahne;
	}
	
	
}
