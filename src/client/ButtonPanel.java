package client;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import local.domain.Spielfeld;
import local.valueobjects.Land;
import net.miginfocom.swing.MigLayout;

public class ButtonPanel extends JPanel{
	private ButtonClickHandler handler = null;
	private JButton nextTurn;
	public interface ButtonClickHandler {
		public void buttonClicked();
	}
	

	public ButtonPanel(ButtonClickHandler handler){
		this.handler = handler;
		
		this.setLayout(new MigLayout("wrap1","[]","[][]"));
		
		nextTurn = new JButton("Naechste Phase");
		
		nextTurn.addActionListener(next -> handler.buttonClicked());
		nextTurn.setEnabled(false);
		
		this.add(nextTurn,"center,growx,growy");
	}
	
	public void buttonFreigeben(){
		nextTurn.setEnabled(true);
	}
	
//	public void testMethode(){	
//		sp.nextTurn();
//		ip.setInfo(sp.getTurn()+"", sp.getAktiverSpieler().getName());
//		System.out.println(sp.getTurn() + "     " + sp.getAktiverSpieler().getName());
//		
//		
//	}
}
