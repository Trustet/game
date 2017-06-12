package client;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

public class ConsolePanel extends JPanel{

	public ConsolePanel()
	{
		initialize();
	}
	
	public void initialize(){
		Border schwarz = BorderFactory.createLineBorder(Color.black);
		this.setLayout(new MigLayout("wrap1","[640]","[][130]"));
		this.setBorder(schwarz);
		
		JLabel header = new JLabel("Hier stehen irgendwelche Benachrichtigungen, die im Spielverlauf wichtig sind.");
		JScrollBar consoleScrollBar = new JScrollBar();
		
		
		this.add(header,"left, growx,growy");
		this.add(consoleScrollBar,"right,growy");
	}
}
