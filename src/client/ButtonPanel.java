package client;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class ButtonPanel extends JPanel{
	private ButtonClickHandler handler = null;
	private JButton nextTurn;
	private JLabel aLand;
	private JLabel vLand;
	private JButton angreifen;
	private JLabel land1;
	private JLabel land2;
	private JTextField anzahlEinheitenVerschieben;
	private JButton verschieben;
	private JLabel anzahlEinheitenVerteilen;
	
	public interface ButtonClickHandler {
		public void buttonClicked();
	}
	
	public ButtonPanel(ButtonClickHandler handler){
		this.handler = handler;
		initialize();	
	}
	
	public void initialize(){
		this.setLayout(new MigLayout("wrap1","[]","[][]"));
		
		//Angreifen
		this.aLand = new JLabel("aLand");
		this.vLand = new JLabel("vLand");
		this.angreifen = new JButton("Angreifen");
				
		//Verschieben
		this.land1 = new JLabel("land1");
		this.land2 = new JLabel("land2");
		this.anzahlEinheitenVerschieben = new JTextField();
		this.verschieben = new JButton("Verschieben");
		
		//Verteilen
		this.anzahlEinheitenVerteilen = new JLabel("anzahl Einheiten");
		nextTurn = new JButton("Naechste Phase");
		
		nextTurn.addActionListener(next -> handler.buttonClicked());
		nextTurn.setEnabled(false);
		this.add(nextTurn,"center,grow");
		this.add(aLand,"center,grow");
		this.add(vLand,"center,grow");
		this.add(angreifen,"center,grow");
		this.add(land1,"center,grow");
		this.add(land2,"center,grow");
		this.add(anzahlEinheitenVerschieben,"center,grow");
		this.add(verschieben,"center,grow");
		this.add(anzahlEinheitenVerteilen,"center,grow");
		inaktiv();
	}
	public void buttonFreigeben(){
		nextTurn.setEnabled(true);
	}

	public void angreifenAktiv(String angriffsLand,String verteidigungsLand)	{
		removeAll();
		this.add(aLand,"center,grow");
		aLand.setText(angriffsLand);
		this.add(vLand,"center,grow");
		vLand.setText(verteidigungsLand);
		this.add(angreifen,"center,grow");
		this.add(nextTurn,"center,grow");
		this.repaint();
	}
	
	public void verschiebenNachAngreifenAktiv()	{

	}
	
	public void verschiebenAktiv(String erstesLand, String zweitesLand)	{
		removeAll();
		this.add(land1,"center,grow");
		land1.setText(erstesLand);
		this.add(land2,"center,grow");
		land2.setText(zweitesLand);
		this.add(anzahlEinheitenVerschieben,"center,grow");
		this.add(verschieben,"center,grow");
		this.add(nextTurn,"center,grow");
		
		this.repaint();
	}
	
	public void verteilenAktiv(int einheiten)	{		
		removeAll();
		this.add(anzahlEinheitenVerteilen,"center,grow");
		anzahlEinheitenVerteilen.setText(einheiten + "");
		this.add(nextTurn,"center,grow");
		this.repaint();
	}
	
	public void inaktiv()	{
		removeAll();
		this.add(nextTurn,"center,grow");
		nextTurn.setEnabled(false);
		this.repaint();
	}
	
	public void removeAll()	{
		this.remove(nextTurn);
		this.remove(aLand);
		this.remove(vLand);
		this.remove(angreifen);
		this.remove(land1);
		this.remove(land2);
		this.remove(anzahlEinheitenVerschieben);
		this.remove(verschieben);
		this.remove(anzahlEinheitenVerteilen);
		this.repaint();
	}

}
