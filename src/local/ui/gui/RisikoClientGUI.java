package local.ui.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.List;
import java.util.Vector;

import javax.swing.BoxLayout;

import local.domain.Spielfeld;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Spieler;


public class RisikoClientGUI extends Frame{
	private Spielfeld sp;
	private TextField spielerAuflistung = null;
	private List spieler = null;
	
	
	public RisikoClientGUI(String title){
		super(title);
		sp = new Spielfeld();
		try {
			sp.erstelleSpieler("Yannik");
			sp.erstelleSpieler("Darian");
			sp.erstelleSpieler("Jesse");
		} catch (SpielerExistiertBereitsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initialisieren();
	}
	public static void main(String[] args) {
		Frame fenster = new RisikoClientGUI("Risiko");

	}
	
	public void initialisieren(){
		Panel addPanel = new Panel();
		addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
		addPanel.add(new Label("Spieler:"));
		spielerAuflistung = new TextField();
		spieler = new List();
		for (Spieler s: sp.getSpielerList()) {
			spieler.add(s.getName());
		}
		spieler.setPreferredSize(new Dimension(100, 400));
		addPanel.add(spieler, BorderLayout.CENTER);
		
		Panel spielKarten = new Panel();
		spielKarten.setLayout(new BoxLayout(spielKarten, BoxLayout.Y_AXIS));
		
		
		
		Panel spielerAnzeige = new Panel();
		spielerAnzeige.setLayout(new BoxLayout(spielerAnzeige, BoxLayout.X_AXIS));
		spielerAnzeige.add(new Label("Spieler:"));
		spielerAuflistung = new TextField();
		spielerAuflistung.setPreferredSize(new Dimension(200, 10));
		spielerAnzeige.add(spielerAuflistung);
		//searchButton.addActionListener(new SuchButtonListener());
		this.setLayout(new BorderLayout());
		this.add(spielerAnzeige, BorderLayout.EAST);
		this.add(addPanel, BorderLayout.WEST);
		this.add(spielKarten, BorderLayout.SOUTH);
	
		
		// Das Fenster soll mindestens 400x400 Pixel groß sein.
		// Wunschgröße ist 640x480 Pixel.
		this.setMinimumSize(new Dimension(400, 400));
		this.setPreferredSize(new Dimension(640, 480));

		// "Kompaktes" Layout berechnen lassen und Fenster anzeigen
		this.pack();
		this.setVisible(true);
		
	}

}
