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
		
	public MissionPanel(){
		initialize();
	}
	
	public void initialize(){
		//könnte weg, sieht aber eig ganz cool aus
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		
		JComponent mission1 = new JPanel();
		mission1.setLayout(new MigLayout("wrap1","[]","[][][]"));
		
		
		mission1.add(new JLabel("Mission:"),"center");
		mission1.add(m1Art,"center");
		mission1.add(mBeschreibung1,"center");
		mission1.setPreferredSize(new Dimension(230,130));
		
		tabbedPane.addTab("Mission1",mission1);
		
		this.add(tabbedPane);
			
	}
	
	public void setMBeschreibung(int nummer, String art, String beschreibung){
				
		m1Art.setText(art);
		mBeschreibung1.setText(beschreibung);
	}
}
