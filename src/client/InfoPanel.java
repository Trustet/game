package client;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class InfoPanel extends JPanel{
	
	private String phaseString;
	private String spieler;
	private JLabel phaseLab;
	private JLabel spielerLab;
	Font schrift;
	Font uberschrift;
	
	public InfoPanel(String phase, String spieler,Font schrift,Font uberschrift){
		this.phaseString = phase;
		this.spieler = spieler;
		this.schrift = schrift;
		this.uberschrift = uberschrift;
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[][][][]"));
		
		JLabel header = new JLabel("Phase:");
		header.setFont(uberschrift);
		phaseLab = new JLabel(phaseString);
		phaseLab.setFont(schrift);
//		JLabel sheader = new JLabel("Spieler");
//		sheader.setFont(uberschrift);
//		spielerLab = new JLabel(spieler);
//		spielerLab.setFont(schrift);
		this.add(header,"left");
		this.add(phaseLab,"left");
//		this.add(sheader,"center");
//		this.add(spielerLab,"center");
	}
	
	public void setInfo(String phase, String spieler){
		phaseLab.setText(phase);
		spielerLab.setText(spieler);
	}
	public void changePanel(String phase, String spieler){
		switch(phase){
		case "VERTEILEN":
			this.setInfo("Verteilen", spieler);
			this.repaint();
			this.revalidate();
			break;
		case "ANGRIFF":
			this.setInfo("Angreifen", spieler);
			this.repaint();
			this.revalidate();
			break;
		case "VERSCHIEBEN":
			this.setInfo("Verschieben", spieler);
			this.repaint();
			this.revalidate();
			break;
		}
	}
}
