package client;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class SpielerPanel extends JPanel{
	JLabel spieler1 = new JLabel();
	JLabel spieler2 = new JLabel();
	JLabel spieler3 = new JLabel();
	JLabel spieler4 = new JLabel();
	JLabel spieler5 = new JLabel();
	JLabel spieler6 = new JLabel();
	public SpielerPanel(){
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[][][][][][][]"));
		JLabel header = new JLabel("Spieler:");
		//FRAGE: was genau passiert hier?
		spieler1.setFont(new Font("Impact", Font.PLAIN,30));
		spieler2.setFont(new Font("Impact", Font.PLAIN,30));
		spieler3.setFont(new Font("Impact", Font.PLAIN,30));
		spieler4.setFont(new Font("Impact", Font.PLAIN,30));
		spieler5.setFont(new Font("Impact", Font.PLAIN,30));
		spieler6.setFont(new Font("Impact", Font.PLAIN,30));
		
		this.add(header,"center,growx");
		this.add(spieler1,"center");
		this.add(spieler2,"center");
		this.add(spieler3,"center");
		this.add(spieler4,"center");
		this.add(spieler5,"center");
		this.add(spieler6,"center");
	}
	
	public void setLabel(int nummer, String text){
		switch(nummer){
			case 1: spieler1.setText(text);
					break;
			case 2: spieler2.setText(text);
					break;
			case 3: spieler3.setText(text);
					break;
			case 4: spieler4.setText(text);
					break;
			case 5: spieler5.setText(text);
					break;
			case 6: spieler6.setText(text);
					break;
		}
	}
	
}
