package client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import local.valueobjects.Land;
import net.miginfocom.swing.MigLayout;

public class StatistikPanel extends JPanel{

	private List<Land> laenderliste;
	BufferedImage iconLand;
	BufferedImage iconEinheiten;
	BufferedImage iconKarten;
	JLabel sp1Laender;
	JLabel sp1Einheiten;
	JLabel sp1Karten;
	JLabel sp2Laender;
	JLabel sp2Einheiten;
	JLabel sp2Karten;
	JLabel sp3Laender;
	JLabel sp3Einheiten;
	JLabel sp3Karten;
	JLabel sp4Laender;
	JLabel sp4Einheiten;
	JLabel sp4Karten;
	JLabel sp5Laender;
	JLabel sp5Einheiten;
	JLabel sp5Karten;
	JLabel sp6Laender;
	JLabel sp6Einheiten;
	JLabel sp6Karten;
	
	public StatistikPanel(List<Land> laenderliste){
	this.laenderliste = laenderliste;
	
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
		
		sp1Laender = new JLabel("10");
		sp1Einheiten = new JLabel("20");
		sp1Karten = new JLabel("2");
		
		sp2Laender = new JLabel("13");
		sp2Einheiten = new JLabel("40");
		sp2Karten = new JLabel("5");
		
		sp3Laender = new JLabel("10");
		sp3Einheiten = new JLabel("100");
		sp3Karten = new JLabel("2");
		
		sp4Laender = new JLabel("10");
		sp4Einheiten = new JLabel("20");
		sp4Karten = new JLabel("2");
		
		sp5Laender = new JLabel("10");
		sp5Einheiten = new JLabel("20");
		sp5Karten = new JLabel("2");
		
		sp6Laender = new JLabel("10");
		sp6Einheiten = new JLabel("20");
		sp6Karten = new JLabel("2");
		
		this.add(header,"spanx 3,left");
		this.add(icon1,"left");
		this.add(icon2,"center");
		this.add(icon3,"right");
		
		this.add(sp1Laender,"left");
		this.add(sp1Einheiten,"center");
		this.add(sp1Karten,"right");
		
		this.add(sp2Laender,"left");
		this.add(sp2Einheiten,"center");
		this.add(sp2Karten,"right");
		
		this.add(sp3Laender,"left");
		this.add(sp3Einheiten,"center");
		this.add(sp3Karten,"right");
		
		this.add(sp4Laender,"left");
		this.add(sp4Einheiten,"center");
		this.add(sp4Karten,"right");
		
		this.add(sp5Laender,"left");
		this.add(sp5Einheiten,"center");
		this.add(sp5Karten,"right");
		
		this.add(sp6Laender,"left");
		this.add(sp6Einheiten,"center");
		this.add(sp6Karten,"right");
		
	}
	
	public void statistikAktualisieren()
	{
		
	}
}
