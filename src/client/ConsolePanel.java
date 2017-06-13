package client;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
		this.setLayout(new MigLayout("wrap1","[600]","[][140]"));
//		this.setBorder(schwarz);
		
		JLabel header = new JLabel("Hier stehen irgendwelche Benachrichtigungen, die im Spielverlauf wichtig sind.");
		JTextArea consoleText = new JTextArea();
		JScrollPane consoleScrollBar = new JScrollPane(consoleText);
		consoleText.setLineWrap(true);
		consoleText.setText("Dies \nist \nein\nvorläufiger\ntest\nText\nzum\nscrollen");
		header.setFont(schrift);
		consoleText.setFont(schrift);
		this.add(header,"left, growx");
		this.add(consoleScrollBar,"growx,growy");
		//this.add(consoleScrollBar,"right,growy,split2");
	}
}
