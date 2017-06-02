package client;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.miginfocom.swing.MigLayout;

public class MissionPanel extends JPanel{
		JLabel m1Art = new JLabel();
		JLabel mBeschreibung1 = new JLabel();
		JLabel m2Art = new JLabel();
		JLabel mBeschreibung2 = new JLabel();
		JLabel m3Art = new JLabel();
		JLabel mBeschreibung3 = new JLabel();
		
	public MissionPanel(){
		initialize();
	}
	
	public void initialize(){
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		JComponent mission1 = new JPanel();
		mission1.setLayout(new MigLayout("wrap1","[]","[][][]"));
		JComponent mission2 = new JPanel();
		mission2.setLayout(new MigLayout("wrap1","[]","[][][]"));
		JComponent mission3 = new JPanel();
		mission3.setLayout(new MigLayout("wrap1","[]","[][][]"));
		
		
		mission1.add(new JLabel("Mission:"),"center");
		mission1.add(m1Art,"center");
		mission1.add(mBeschreibung1,"center");
		mission2.add(new JLabel("Mission:"),"center");
		mission2.add(m2Art,"center");
		mission2.add(mBeschreibung2,"center");
		mission3.add(new JLabel("Mission:"),"center");
		mission3.add(m3Art,"center");
		mission3.add(mBeschreibung3,"center");
		mission1.setPreferredSize(new Dimension(230,130));
		mission2.setPreferredSize(new Dimension(230,130));
		mission3.setPreferredSize(new Dimension(230,130));
		
		tabbedPane.addTab("Mission1",mission1);
		tabbedPane.addTab("Mission2", mission2);
		tabbedPane.addTab("Mission3", mission3);
		
		this.add(tabbedPane);
			
	}
	
	public void setMBeschreibung(int nummer, String art, String beschreibung){
		switch(nummer){
		case 1: m1Art.setText(art);
				mBeschreibung1.setText(beschreibung);
				break;
		case 2: m2Art.setText(art);
				mBeschreibung2.setText(beschreibung);
				break;
		case 3: m3Art.setText(art);
				mBeschreibung3.setText(beschreibung);
				break;
		}
	}
}
