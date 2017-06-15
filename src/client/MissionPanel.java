package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MissionPanel extends JPanel{
		private JLabel mArt = new JLabel();
		private JTextField mBeschreibung = new JTextField();
		private JLabel kBeschreibung = new JLabel();
		private BufferedImage iconEinheiten;
		private Font schrift;
		private Font uberschrift;
		
	public MissionPanel(Font uberschrift, Font schrift){
		this.schrift = schrift;
		this.uberschrift = uberschrift;
		initialize();
	}
	
	public void initialize(){
		//könnte weg, sieht aber eig ganz cool aus
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(uberschrift);
		JComponent mission = new JPanel();
		mission.setLayout(new MigLayout("wrap1","[]","[]"));
		
		mBeschreibung.setText("Missionsbeschreibung");
		mBeschreibung.setFont(schrift);
//		mission.add(new JLabel("Mission:"),"left");
//		mission.add(mArt,"center");
		mission.add(mBeschreibung,"center, grow");
		mission.setPreferredSize(new Dimension(230,130));
		
		JComponent karten = new JPanel();
		karten.setLayout(new MigLayout("wrap1","[]","[][][]"));
		try{
			iconEinheiten = ImageIO.read(new File("./soldat.jpg"));
		} catch (IOException e){
			
		}
		
		JLabel icon2 = new JLabel(new ImageIcon(iconEinheiten.getScaledInstance(20, 20, Image.SCALE_FAST)));
		
		icon2.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Du hast die Soldatenkarten eingetauscht");
			}
		});
		
		karten.add(new JLabel("Du hast folgende Karten:"),"center");
		karten.add(icon2);
		karten.add(kBeschreibung,"center");
		karten.setPreferredSize(new Dimension(230,130));
		
		tabbedPane.addTab("Mission",mission);
		tabbedPane.addTab("Karten", karten);
		this.add(tabbedPane);
			
	}
	
	public void setMBeschreibung(String beschreibung){
				
//		mArt.setText(art);
		mBeschreibung.setText(beschreibung);
	}
}
