package client;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

public class ConsolePanel extends JPanel{

	Font schrift;
	
	public ConsolePanel(Font schrift)
	{
		this.schrift = schrift;
		initialize();
	}
	
	public void initialize(){
//		Border schwarz = BorderFactory.createLineBorder(Color.black);
		this.setLayout(new MigLayout("wrap1","[640]","[][120]"));
//		this.setBorder(schwarz);
		
		JLabel header = new JLabel("Hier stehen irgendwelche Benachrichtigungen, die im Spielverlauf wichtig sind.");
		JTextArea consoleText = new JTextArea();
		JScrollBar consoleScrollBar = new JScrollBar();
		
		header.setFont(schrift);
		consoleText.setFont(schrift);
		this.add(header,"left, growx");
		this.add(consoleText,"growx,growy,split2");
		this.add(consoleScrollBar,"right,growy,split2");
	}
}
