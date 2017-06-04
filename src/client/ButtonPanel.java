package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import local.domain.Spielfeld;
import net.miginfocom.swing.MigLayout;

public class ButtonPanel extends JPanel{
	

	private Spielfeld sp;
	private InfoPanel ip;
	public ButtonPanel(InfoPanel ip, Spielfeld sp){
		this.ip = ip;
		this.sp = sp;
		this.setLayout(new MigLayout("wrap1","[]","[][]"));
		
		JButton nextTurn = new JButton("Naechste Phase");
		
		nextTurn.addActionListener(next -> testMethode());
		
		
		this.add(nextTurn,"center,growx,growy");
	}
	
	public void testMethode(){	
		sp.nextTurn();
		ip.setInfo(sp.getTurn()+"", sp.getAktiverSpieler().getName());
		System.out.println(sp.getTurn() + "     " + sp.getAktiverSpieler().getName());
		
	}
}
