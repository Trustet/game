package client;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

public class MissionPanel extends JPanel{
		JLabel mArt = new JLabel();
		JLabel mBeschreibung = new JLabel();
		JLabel kBeschreibung = new JLabel();
		
	public MissionPanel(){
		initialize();
	}
	
	public void initialize(){
		//könnte weg, sieht aber eig ganz cool aus
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		JComponent mission = new JPanel();
		mission.setLayout(new MigLayout("wrap1","[]","[][][]"));
		
		mBeschreibung.setText("Erobere die Kontinente Afrika.");
		mission.add(new JLabel("Mission:"),"left");
		mission.add(mArt,"center");
		mission.add(mBeschreibung,"center");
		mission.setPreferredSize(new Dimension(230,130));
		
		JComponent karten = new JPanel();
		karten.setLayout(new MigLayout("wrap1","[]","[][][]"));
		
		karten.add(new JLabel("Du hast folgende Karten:"),"center");
		karten.add(kBeschreibung,"center");
		karten.setPreferredSize(new Dimension(230,130));
		
		tabbedPane.addTab("Mission",mission);
		tabbedPane.addTab("Karten", karten);
		this.add(tabbedPane);
			
	}
	
	public void setMBeschreibung(int nummer, String art, String beschreibung){
				
		mArt.setText(art);
		mBeschreibung.setText(beschreibung);
	}
}
