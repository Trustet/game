package client;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.miginfocom.swing.MigLayout;

public class ConsolePanel extends JPanel{

	public ConsolePanel()
	{
		initialize();
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[]"));
		
		JLabel header = new JLabel("Hier stehten irgendwelche Benachrichtigungen, die im Spielverlauf wichtig sind.");
		JScrollBar consoleScrollBar = new JScrollBar();
		
		this.add(header,"center");
		this.add(consoleScrollBar,"right,growy");
	}
}
