package client;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class InfoPanel extends JPanel{
	
	private String phaseString;
	private String spieler;
	private JLabel phaseLab;
	private JLabel spielerLab;
	public InfoPanel(String phase, String spieler){
		this.phaseString = phase;
		this.spieler = spieler;
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[][][][]"));
		
		JLabel header = new JLabel("Aktuelle Phase:");
		phaseLab = new JLabel(phaseString);
		JLabel sheader = new JLabel("Aktueller Spieler");
		spielerLab = new JLabel(spieler);
		this.add(header,"center");
		this.add(phaseLab,"center");
		this.add(sheader,"center");
		this.add(spielerLab,"center");
	}
	
	public void setInfo(String phase, String spieler){
		phaseLab.setText(phase);
		spielerLab.setText(spieler);
	}
	public void changePanel(String phase){
		switch(phase){
		case "VERTEILEN":
			break;
		case "ANGRIFF":
			this.removeAll();
			this.add(new JLabel("Hier bin ich"));
			this.repaint();
			this.revalidate();
			break;
		case "VERSCHIEBEN":
			break;
		}
	}
}
