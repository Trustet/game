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
	private JButton verschiebenNA;
	private JLabel anzahlEinheitenVerteilen;
	
	public interface ButtonClickHandler {
		public void buttonClicked();
		public void angriffClicked();
		public void verschiebenClicked(int einheiten);
		public void verschiebenNAClicked(int einheiten);
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
		this.verschiebenNA = new JButton("Verschieben");
		
		//Verteilen
		this.anzahlEinheitenVerteilen = new JLabel("anzahl Einheiten");
		nextTurn = new JButton("Naechster Spieler");
		
		nextTurn.addActionListener(next -> handler.buttonClicked());
		angreifen.addActionListener(angriff -> handler.angriffClicked());
		verschieben.addActionListener(verschieben -> handler.verschiebenClicked(Integer.parseInt(anzahlEinheitenVerschieben.getText())));
		verschiebenNA.addActionListener(verschiebenNA -> handler.verschiebenNAClicked(Integer.parseInt(anzahlEinheitenVerschieben.getText())));
		
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
		startphase();
	}
	public void angreifenAktiv(String angriffsLand,String verteidigungsLand)	{
		angreifen.setEnabled(false);
		removeAll();
		this.add(aLand,"center,grow");
		aLand.setText(angriffsLand);
		this.add(vLand,"center,grow");
		vLand.setText(verteidigungsLand);
		this.add(angreifen,"center,grow");
		this.add(nextTurn,"center,grow");
		nextTurn.setText("Naechste Phase");
		this.repaint();
	}
	
	public void verschiebenNachAngreifenAktiv(String erstesLand, String zweitesLand)	{
		removeAll();
		this.add(land1,"center,grow");
		land1.setText(erstesLand);
		this.add(land2,"center,grow");
		land2.setText(zweitesLand);
		this.add(anzahlEinheitenVerschieben,"center,grow");
		this.add(verschiebenNA,"center,grow");
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
		nextTurn.setText("Naechste Phase");
		verschieben.setEnabled(false);

		
		this.repaint();
	}
	
	public void verteilenAktiv(int einheiten)	{		
		removeAll();
		this.add(anzahlEinheitenVerteilen,"center,grow");
		anzahlEinheitenVerteilen.setText(einheiten + "");
		this.add(nextTurn,"center,grow");
		nextTurn.setText("Naechste Phase");
		this.repaint();
	}
	public void phaseDisable(){
		nextTurn.setEnabled(false);
	}
	
	public void phaseEnable(){
		nextTurn.setEnabled(true);
	}
	public void angriffEnable(){
		angreifen.setEnabled(true);
	}
	public void angriffDisable(){
		angreifen.setEnabled(false);
	}
	public void verschiebenEnabled(){
		verschieben.setEnabled(true);
	}
	public void verschiebenDisabled(){
		verschieben.setEnabled(false);
	}
	public void resetTextbox(){
		anzahlEinheitenVerschieben.setText("");
	}
	public void startphase()	{
		removeAll();
		this.add(nextTurn,"center,grow");
		nextTurn.setEnabled(false);
		this.repaint();
	}
	public void setEinheitenVerteilenLab(int einheiten){
		anzahlEinheitenVerteilen.setText(einheiten + "");
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
		this.remove(verschiebenNA);
		this.repaint();
	}

}
