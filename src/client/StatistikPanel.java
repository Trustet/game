package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.css.RGBColor;

import local.valueobjects.Land;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;

public class StatistikPanel extends JPanel{

	private List<Land> laenderListe;
	private List<Spieler> spielerListe;
	BufferedImage iconLand;
	BufferedImage iconEinheiten;
	BufferedImage iconKarten;
	List<JLabel> laenderVonSpielerLabel;
	List<JLabel> einheitenVonSpielerLabel;
	List<JLabel> kartenVonSpielerLabel;	
	List<Integer> laenderVonSpieler;
	List<Integer> einheitenVonSpieler;
	List<Integer> kartenVonSpieler;
	Font schrift;
	Font uberschrift;
	
	
	public StatistikPanel(List<Spieler> spielerListe, List<Land> laenderListe,Font schrift,Font uberschrift){
	this.laenderListe = laenderListe;
	this.spielerListe = spielerListe;
	this.schrift = schrift;
	this.uberschrift = uberschrift;
	initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap3","[][][]","[][][][][][][][]"));
		
		JLabel header = new JLabel("Statistik:");
		header.setFont(uberschrift);
		
		try{
		iconLand = ImageIO.read(new File("./land.jpg"));
		iconEinheiten = ImageIO.read(new File("./soldat.jpg"));
		iconKarten = ImageIO.read(new File("./karten.png"));
		}catch (IOException e){
			
		}
		
		JLabel icon1 = new JLabel(new ImageIcon(iconLand.getScaledInstance(40, 40, Image.SCALE_FAST)));
		JLabel icon2 = new JLabel(new ImageIcon(iconEinheiten.getScaledInstance(40, 40, Image.SCALE_FAST)));
		JLabel icon3 = new JLabel(new ImageIcon(iconKarten.getScaledInstance(40, 40, Image.SCALE_FAST)));
		
		laenderVonSpieler = new Vector<Integer>();
		einheitenVonSpieler = new Vector<Integer>();
		kartenVonSpieler = new Vector<Integer>();
		
		laenderVonSpielerLabel = new Vector<JLabel>();
		einheitenVonSpielerLabel = new Vector<JLabel>();
		kartenVonSpielerLabel = new Vector<JLabel>();

		this.add(header,"spanx 3,left");
		this.add(icon1,"left");
		this.add(icon2,"center");
		this.add(icon3,"right");

		this.repaint();
		this.revalidate();		
	}
	
	public void statistikAktualisieren(){
		
		laenderVonSpieler.clear();
		einheitenVonSpieler.clear();
		kartenVonSpieler.clear();
		
		int anzahlLaender;
		int anzahlEinheiten;
		for(Spieler s : spielerListe)
		{
			anzahlLaender = 0;
			anzahlEinheiten = 0;
			for(Land l: laenderListe)
			{
				if(l.getBesitzer().equals(s))
				{
					anzahlLaender++;
					anzahlEinheiten += l.getEinheiten();
				}
			}
			laenderVonSpieler.add(anzahlLaender);
			einheitenVonSpieler.add(anzahlEinheiten);
			kartenVonSpieler.add(3);
			
		}
		//TODO Schriftfarbe ändern
		for(int laenderAnzahl : laenderVonSpieler)
		{
			laenderVonSpielerLabel.add(new JLabel(laenderAnzahl + ""));
		}
		
		for(int einheitenAnzahl : einheitenVonSpieler)
		{
			einheitenVonSpielerLabel.add(new JLabel(einheitenAnzahl + ""));
		}
		
		for(int kartenAnzahl : kartenVonSpieler)
		{
			kartenVonSpielerLabel.add(new JLabel(kartenAnzahl + ""));
		}

		List<Color> farben = new Vector<>();
		farben.add(new Color(175,42,0));
		farben.add(new Color(133, 219, 24));
		farben.add(new Color(38, 50, 237));
		farben.add(Color.blue);
		farben.add(Color.blue);
		farben.add(Color.blue);
		
		int i = 0;
		for(JLabel lab : laenderVonSpielerLabel){
			this.add(laenderVonSpielerLabel.get(i),"center");
			this.add(einheitenVonSpielerLabel.get(i),"center");
			this.add(kartenVonSpielerLabel.get(i),"center");
					
			laenderVonSpielerLabel.get(i).setFont(schrift);
			einheitenVonSpielerLabel.get(i).setFont(schrift);
			kartenVonSpielerLabel.get(i).setFont(schrift);
			laenderVonSpielerLabel.get(i).setForeground(farben.get(i));
			einheitenVonSpielerLabel.get(i).setForeground(farben.get(i));
			kartenVonSpielerLabel.get(i).setForeground(farben.get(i));
			i++;
		}
	}
	
	public void statistikPanelAktualisieren(){
//		statistikAktualisieren();
		
		for(int i = 0; i < laenderVonSpielerLabel.size(); i++)
		{
			laenderVonSpielerLabel.get(i).setText(laenderVonSpieler.get(i) + "");
		}
		this.repaint();
		this.revalidate();
	}
}
