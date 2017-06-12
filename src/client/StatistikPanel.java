package client;

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

import local.valueobjects.Land;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;

public class StatistikPanel extends JPanel{

	private List<Land> laenderListe;
	private List<Spieler> spielerListe;
	BufferedImage iconLand;
	BufferedImage iconEinheiten;
	BufferedImage iconKarten;
//	JLabel sp1Laender;
//	JLabel sp1Einheiten;
//	JLabel sp1Karten;
//	JLabel sp2Laender;
//	JLabel sp2Einheiten;
//	JLabel sp2Karten;
//	JLabel sp3Laender;
//	JLabel sp3Einheiten;
//	JLabel sp3Karten;
//	JLabel sp4Laender;
//	JLabel sp4Einheiten;
//	JLabel sp4Karten;
//	JLabel sp5Laender;
//	JLabel sp5Einheiten;
//	JLabel sp5Karten;
//	JLabel sp6Laender;
//	JLabel sp6Einheiten;
//	JLabel sp6Karten;
	List<JLabel> laenderVonSpielerLabel;
	List<JLabel> einheitenVonSpielerLabel;
	List<JLabel> kartenVonSpielerLabel;	
	List<Integer> laenderVonSpieler;
	List<Integer> einheitenVonSpieler;
	List<Integer> kartenVonSpieler;
	
	
	public StatistikPanel(List<Spieler> spielerListe, List<Land> laenderListe){
	this.laenderListe = laenderListe;
	this.spielerListe = spielerListe;
	initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap3","[][][]","[][][][][][][][]"));
		
		JLabel header = new JLabel("Statistik:");
		
		try{
		iconLand = ImageIO.read(new File("./land.jpg"));
		iconEinheiten = ImageIO.read(new File("./soldat.jpg"));
		iconKarten = ImageIO.read(new File("./karten.png"));
		}catch (IOException e){
			
		}
		
		JLabel icon1 = new JLabel(new ImageIcon(iconLand.getScaledInstance(40, 40, Image.SCALE_FAST)));
		JLabel icon2 = new JLabel(new ImageIcon(iconEinheiten.getScaledInstance(40, 40, Image.SCALE_FAST)));
		JLabel icon3 = new JLabel(new ImageIcon(iconKarten.getScaledInstance(40, 40, Image.SCALE_FAST)));
		
		laenderVonSpieler = new Vector<>();
		einheitenVonSpieler = new Vector<>();
		kartenVonSpieler = new Vector<>();
		
		laenderVonSpielerLabel = new Vector<>();
		einheitenVonSpielerLabel = new Vector<>();
		kartenVonSpielerLabel = new Vector<>();
		
		statistikAktualisieren();


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

		
		
//		sp1Laender = new JLabel("10");
//		sp1Einheiten = new JLabel("20");
//		sp1Karten = new JLabel("2");
//		
//		sp2Laender = new JLabel("13");
//		sp2Einheiten = new JLabel("40");
//		sp2Karten = new JLabel("5");
//		
//		sp3Laender = new JLabel("10");
//		sp3Einheiten = new JLabel("100");
//		sp3Karten = new JLabel("2");
//		
//		sp4Laender = new JLabel("10");
//		sp4Einheiten = new JLabel("20");
//		sp4Karten = new JLabel("2");
//		
//		sp5Laender = new JLabel("10");
//		sp5Einheiten = new JLabel("20");
//		sp5Karten = new JLabel("2");
//		
//		sp6Laender = new JLabel("10");
//		sp6Einheiten = new JLabel("20");
//		sp6Karten = new JLabel("2");
		
		this.add(header,"spanx 3,left");
		this.add(icon1,"left");
		this.add(icon2,"center");
		this.add(icon3,"right");
		
		for(int i = 0;i < laenderVonSpielerLabel.size();i++)
		{
			this.add(laenderVonSpielerLabel.get(i),"left");
			this.add(einheitenVonSpielerLabel.get(i),"center");
			this.add(kartenVonSpielerLabel.get(i),"right");
		}
		
//		this.add(sp1Laender,"left");
//		this.add(sp1Einheiten,"center");
//		this.add(sp1Karten,"right");
//		
//		this.add(sp2Laender,"left");
//		this.add(sp2Einheiten,"center");
//		this.add(sp2Karten,"right");
//		
//		this.add(sp3Laender,"left");
//		this.add(sp3Einheiten,"center");
//		this.add(sp3Karten,"right");
//		
//		this.add(sp4Laender,"left");
//		this.add(sp4Einheiten,"center");
//		this.add(sp4Karten,"right");
//		
//		this.add(sp5Laender,"left");
//		this.add(sp5Einheiten,"center");
//		this.add(sp5Karten,"right");
//		
//		this.add(sp6Laender,"left");
//		this.add(sp6Einheiten,"center");
//		this.add(sp6Karten,"right");
		
	}
	
	public void statistikAktualisieren(){
		laenderVonSpieler.clear();
		einheitenVonSpieler.clear();
		kartenVonSpieler.clear();
		
		int anzahlLaender;
		int anzahlEinheiten;
		for(int i = 0; i < spielerListe.size(); i++)
		{
			anzahlLaender = 0;
			anzahlEinheiten = 0;
			for(Land l: laenderListe)
			{
				if(l.getBesitzer().equals(spielerListe.get(i)))
				{
					anzahlLaender++;
					anzahlEinheiten += l.getEinheiten();
				}
				laenderVonSpieler.add(anzahlLaender);
				einheitenVonSpieler.add(anzahlEinheiten);
				kartenVonSpieler.add(3);
			}
			
		}
	}
	
	public void statistikPanelAktualisieren(){
		statistikAktualisieren();
		
		for(int i = 0; i < laenderVonSpielerLabel.size(); i++)
		{
			laenderVonSpielerLabel.get(i).setText(laenderVonSpieler.get(i) + "");
		}
	}
}
