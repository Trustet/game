package client;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ButtonPanel extends JPanel{
	private ButtonClickHandler handler = null;
	private JButton nextTurn;
	public interface ButtonClickHandler {
		public void buttonClicked();
	}
	
	public ButtonPanel(ButtonClickHandler handler){
		this.handler = handler;
		initialize();	
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[][]"));
		
		nextTurn = new JButton("Naechste Phase");
		
		nextTurn.addActionListener(next -> handler.buttonClicked());
		nextTurn.setEnabled(false);
		
		this.add(nextTurn,"center,growx,growy");
	}
	public void buttonFreigeben(){
		nextTurn.setEnabled(true);
	}
	

	public void angreifenAktiv()	{
		
	}
	
	public void verschiebenNachAngreifenAktiv()	{
		
	}
	
	public void verschiebenAktiv()	{
		
	}
	
	public void verteilenAktiv()	{
		
	}
	
	public void inaktiv()	{
		
	}

}
