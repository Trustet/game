//TODO Angriffsphase erweitern (Yannik)
//TODO Verschieben vervollständigen (Jesse)
//TODO Missionen ausgeben
//TODO Missionenlogik
//TODO Karten ausgeben
//TODO Kartenlogik
//TODO Aktiver spieler anzeigen (Yannik)
//TODO GUI komplett aufraeumen
//TODO Exceptions mit text umschreiben (wie Teschke)
//TODO viele viele Bugs
//TODO Angreiffen mit nur einer Einheit funtkioniert nicht
//TODO Javadoc
package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.ButtonPanel.ButtonClickHandler;
import client.ErstellenPanel.ErstellenButtonClicked;
import client.MapPanel.MapClickHandler;
import client.StartPanel.StartButtonClickHandler;
import local.domain.Spielfeld;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.domain.exceptions.KeinGegnerException;
import local.domain.exceptions.KeinNachbarlandException;
import local.domain.exceptions.LandBereitsBenutztException;
import local.domain.exceptions.NichtGenugEinheitenException;
import local.domain.exceptions.SpielerExistiertBereitsException;
import local.valueobjects.Angriff;
import local.valueobjects.AngriffRueckgabe;
import local.valueobjects.Land;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;

public class RisikoClientGUI extends JFrame
implements MapClickHandler, ButtonClickHandler, StartButtonClickHandler, ErstellenButtonClicked {
	Spielfeld sp = new Spielfeld();

	int anzahlSpieler;
	private SpielerPanel spielerListPanel;
	private MissionPanel missionPanel;
	private MapPanel spielfeld;
	private InfoPanel infoPanel;
	private ButtonPanel buttonPanel;
	private StatistikPanel statistikPanel;
	private ConsolePanel consolePanel;
	private StartPanel startPanel;
	private ErstellenPanel erstellenPanel;
	private MenuBar menu;
	private Font schrift;
	private Font uberschrift;
	private Land land1 = null;
	private Land land2 = null;
	private int anzahlSetzbareEinheiten;
	private Spieler spieler;
	// private Socket socket = null;
	// private BufferedReader in;
	// private PrintStream out;

	public RisikoClientGUI() {
		this.start();
	}

	public static void main(String[] args) {
		new RisikoClientGUI();
	}

	public void start() {
		uberschrift = new Font(Font.SERIF, Font.BOLD, 25);
		schrift = new Font(Font.SANS_SERIF, Font.PLAIN, 17);

		// Frame und Layout
		this.setTitle("Spiel starten");
		this.setSize(330, 350);
		this.setLocationRelativeTo(null);
		// panel.setBackground(new Color(220,175,116));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		startPanel = new StartPanel(this);
		this.add(startPanel);
		this.setResizable(true);
		this.setVisible(true);

	}

	public void spielErstellen() {

		// Frame und Layout
		this.setTitle("Spiel erstellen");

		// panel.setBackground(new Color(220,175,116));
		this.setSize(280, 200); // von 300 auf 280 gestellt //FRAGE: kann man
		// panel zentrieren?
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		erstellenPanel = new ErstellenPanel(this);

		this.add(erstellenPanel);
		this.setVisible(true);
		this.repaint();
		this.revalidate();

	}

	public void spiel(String name, int anzahlSpieler) {
		// verbindungAufbauen(ip,port);
		this.anzahlSpieler = anzahlSpieler;

		try {
			sp.erstelleSpieler(name);
			this.remove(erstellenPanel);
			for (int i = 1; i < anzahlSpieler; i++) {
				neuerSpieler();
			}
			this.setTitle("Risiko");
			this.setSize(1250, 817);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);

			// JPanel panel = new JPanel(new MigLayout("
			// wrap2","[][]","[][][]")); // hier "debug,wrap2" schreiben für
			// Debug-Modus
			this.setLayout(new MigLayout(" wrap2", "[][]", "[][][]"));
			// this.add(panel);

			spielfeld = new MapPanel(this, schrift);
			spielerListPanel = new SpielerPanel(schrift, uberschrift);
			missionPanel = new MissionPanel(uberschrift, schrift);
			infoPanel = new InfoPanel(sp.getTurn() + "", sp.getAktiverSpieler().getName(), schrift, uberschrift);
			buttonPanel = new ButtonPanel(this);
			statistikPanel = new StatistikPanel(sp.getSpielerList(), sp.getLaenderListe(), schrift, uberschrift);
			consolePanel = new ConsolePanel(schrift);

			// Menu auslagern
			menu = new MenuBar();
			Menu datei = new Menu("Datei");
			menu.add(datei);
			MenuItem speichern = new MenuItem("Speichern");
			MenuItem laden = new MenuItem("Laden");
			MenuItem schliessen = new MenuItem("Schließen");
			datei.add(speichern);
			datei.add(laden);
			datei.add(schliessen);
			menu.setFont(schrift);
			// MenuBarBorder menuBorder = new MenuBarBorder(Color.black,
			// Color.white);
			// getContentPane();
			this.setMenuBar(menu);

			this.add(spielfeld, "left,spany 3,grow,hmin 550, wmin 1050");
			this.add(infoPanel, "left,growx");
			this.add(spielerListPanel, "growx");
			this.add(statistikPanel, "left,top,growx,spany 2");
			this.add(missionPanel, "left,top,split3");
			this.add(consolePanel, "left, top");
			this.add(buttonPanel, "right,growy");
			this.setResizable(false);
			this.setVisible(true);
			this.pack();

			sp.setTurn("STARTPHASE");
			anzahlSetzbareEinheiten = sp.checkAnfangsEinheiten();
			consolePanel.textSetzen(sp.getAktiverSpieler().getName()
					+ " du kannst nun deine ersten Einheiten setzen. Es sind " + anzahlSetzbareEinheiten);
		} catch (SpielerExistiertBereitsException sebe) {
			JOptionPane.showMessageDialog(null, sebe.getMessage(), "Name vergeben", JOptionPane.WARNING_MESSAGE);
		}

		// missionPanel.setMBeschreibung(sp.getMissionVonAktivemSpieler().getBeschreibung());
	}

	public void neuerSpieler() {
		JFrame frame = new JFrame("Spieler erstellen");
		frame.setSize(150, 300);
		frame.setLayout(new MigLayout("wrap 2", "[][100]", "[][]"));
		JLabel nameLab = new JLabel("Name:");
		JTextField nameText = new JTextField();
		JButton erstellenBtn = new JButton("Erstellen");

		erstellenBtn.addActionListener(erstellen -> spielerErstellen(frame, nameText.getText()));

		frame.add(nameLab, "right");
		frame.add(nameText, "left,growx");
		frame.add(erstellenBtn, "center,spanx 2");
		frame.setVisible(true);
	}

	public void spielerErstellen(JFrame frame, String name) {
		try {
			sp.erstelleSpieler(name);
			frame.dispose();

		} catch (SpielerExistiertBereitsException sebe) {
			JOptionPane.showMessageDialog(null, sebe.getMessage(), "Name vergeben", JOptionPane.WARNING_MESSAGE);
		}

		if (sp.getSpielerList().size() == anzahlSpieler) {
			try {
				sp.laenderErstellen();
				sp.laenderverbindungenUndKontinenteErstellen();
				sp.missionsListeErstellen();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Fehler", JOptionPane.WARNING_MESSAGE);
			}
			sp.missionenVerteilen();
			sp.laenderAufteilen();
			farbenVerteilen();
			spielfeld.fahnenVerteilen(sp.getLaenderListe());
			int spielerNr = 1;
			for (Spieler s : sp.getSpielerList()) {
				spielerListPanel.setLabel(spielerNr, s.getName(), s.getFarbe());
				spielerNr++;
			}

			statistikPanel.statistikAktualisieren();

		}
	}

	public void farbenVerteilen() {
		List<String> farben = new Vector<String>();
		farben.add("rot");
		farben.add("gruen");
		farben.add("blau");
		farben.add("gelb");
		farben.add("orange");
		farben.add("cyan");

		for (Spieler s : sp.getSpielerList()) {
			s.setFarbe(farben.get(0));
			farben.remove(0);
		}
	}

	@Override
	public void processMouseClick(Color color) {
		String landcode = color.getRed() + "" + color.getGreen() + "" + color.getBlue();
		landWaehlen(landcode);

	}

	public void landWaehlen(String landcode) {

		String landstring = sp.getLandVonFarbcode(landcode);
		Land land = sp.stringToLand(landstring);
		if (land != null) {
			spielfeld.labelsSetzen(land.getName(), land.getEinheiten(), land.getBesitzer().getName());
			Spieler spieler = sp.getAktiverSpieler();
			switch (sp.getTurn()) {
			case STARTPHASE:
				startphase(landstring, land);
				break;
			case ANGRIFF:
				angreifen(landstring, land, spieler);
				break;
			case VERTEILEN:
				verteilen(landstring, land);
				break;
			case VERSCHIEBEN:
				verschieben(landstring, land);
				break;
			}
		}
		statistikPanel.statistikPanelAktualisieren();
	}

	public void startphase(String landstring, Land land) {

		try {
			sp.landWaehlen(landstring, sp.getAktiverSpieler());
			if (anzahlSetzbareEinheiten > 0) {
				sp.einheitenPositionieren(1, land);
				anzahlSetzbareEinheiten--;
				spielfeld.labelsSetzen("", land.getEinheiten(), "");
				spielfeld.fahneEinheit(land.getEinheitenLab());
				statistikPanel.statistikPanelAktualisieren();
			} else {

				consolePanel.textSetzen("Du hast alle Einheiten gesetzt. Drücke auf den Button.");
			}
			if (anzahlSetzbareEinheiten == 0) {
				buttonPanel.phaseEnable();
			}
		} catch (KannLandNichtBenutzenException lene) {
			consolePanel.textSetzen(lene.getMessage());
		}
	}

	public void verteilen(String landstring, Land land) {
		try {
			sp.landWaehlen(landstring, sp.getAktiverSpieler());
			if (anzahlSetzbareEinheiten > 0) {
				sp.einheitenPositionieren(1, land);
				anzahlSetzbareEinheiten--;
				spielfeld.labelsSetzen("", land.getEinheiten(), "");
				spielfeld.fahneEinheit(land.getEinheitenLab());
				statistikPanel.statistikPanelAktualisieren();
				buttonPanel.setEinheitenVerteilenLab(anzahlSetzbareEinheiten);
			} else {
				consolePanel.textSetzen("Du hast alle Einheiten gesetzt.");
			}
			if (anzahlSetzbareEinheiten == 0) {
				buttonPanel.phaseEnable();
			}
		} catch (KannLandNichtBenutzenException lene) {
			consolePanel.textSetzen(lene.getMessage());
		}
	}

	public void angreifen(String landstring, Land land, Spieler spieler) {

		if (land1 == null) {
			try {
				sp.landWaehlen(landstring, spieler);
				sp.checkEinheiten(landstring, 1);
				land1 = land;
				// erstmal nur zum testen
				buttonPanel.angreifenAktiv(land1.getName(), "verteidigendes land");
			} catch (KannLandNichtBenutzenException lene) {
				consolePanel.textSetzen(lene.getMessage());
			} catch (NichtGenugEinheitenException e) {
				consolePanel.textSetzen(e.getMessage());
			}
		} else {

			try {
				sp.istNachbar(land1, land, spieler);
				sp.istGegner(landstring, spieler);
				land2 = land;
				// erstmal nur zum testen
				buttonPanel.angreifenAktiv(land1.getName(), land2.getName());
				buttonPanel.angriffEnable();

			} catch (KeinNachbarlandException knle) {
				try {
					sp.landWaehlen(landstring, spieler);
					land1 = land;
				} catch (KannLandNichtBenutzenException lene) {
					consolePanel.textSetzen(knle.getMessage());
				}
			} catch (KeinGegnerException kge) {
				try {
					sp.landWaehlen(landstring, spieler);
				} catch (KannLandNichtBenutzenException lene) {
					consolePanel.textSetzen(lene.getMessage());
				}
			}

		}
	}

	public void verschieben(String landstring, Land land) {

		if (land1 == null) {
			try {
				sp.landWaehlen(landstring, sp.getAktiverSpieler());
				sp.benutzeLaender(land);
				sp.checkEinheiten(landstring, 1);
				land1 = land;
			} catch (KannLandNichtBenutzenException lene) {
				consolePanel.textSetzen(lene.getMessage());
			} catch (NichtGenugEinheitenException ngee) {
				consolePanel.textSetzen(ngee.getMessage());
			} catch (LandBereitsBenutztException lbbe) {
				consolePanel.textSetzen(lbbe.getMessage());
			}

		} else {

			try {
				sp.landWaehlen(landstring, sp.getAktiverSpieler());
				sp.istNachbar(land1, land, sp.getAktiverSpieler());
				land2 = land;
				buttonPanel.verschiebenAktiv(land1.getName(), land2.getName());
				buttonPanel.verschiebenEnabled();
			} catch (KannLandNichtBenutzenException klnbe) {
				consolePanel.textSetzen(klnbe.getMessage());
			} catch (KeinNachbarlandException kne) {
				try {
					sp.landWaehlen(landstring, sp.getAktiverSpieler());
					sp.checkEinheiten(landstring, 1);
					land1 = land;
				} catch (KannLandNichtBenutzenException lene) {
					consolePanel.textSetzen(lene.getMessage());
				} catch (NichtGenugEinheitenException ngee) {
					consolePanel.textSetzen(ngee.getMessage());
				}
			}
		}
	}

	private void angriff(boolean genugEinheiten, Spieler aSpieler) throws KeinNachbarlandException {
		Land aLand = land1;
		Land vLand = land2;
		AngriffRueckgabe angriffRueckgabe = sp.befreiungsAktion(new Angriff(aLand, vLand));

		spielfeld.wuerfelAnzeigen(angriffRueckgabe.getWuerfelAngreifer(), angriffRueckgabe.getWuerfelVerteidiger());

		if (angriffRueckgabe.isErobert() != true) {

			if (angriffRueckgabe.hatGewonnen().equals("V")) {
				consolePanel.textSetzen(vLand.getBesitzer().getName() + " hat gewonnen.");
			} else if (angriffRueckgabe.hatGewonnen().equals("A")) {
				consolePanel.textSetzen(aLand.getBesitzer().getName() + " hat gewonnen.");
			} else {
				consolePanel.textSetzen("Ihr habt unentschieden gespielt, beide verlieren eine Einheit.");
			}
			spielfeld.fahneEinheit(land1.getEinheitenLab());
			spielfeld.fahneEinheit(land2.getEinheitenLab());
			land1 = null;
			land2 = null;
			buttonPanel.angreifenAktiv("angreifendes Land", "verteidigendes Land");
		} else {
			vLand.setFahne(aSpieler.getFarbe());
			spielfeld.fahnenVerteilen(sp.getLaenderListe());
			consolePanel.textSetzen(aLand.getBesitzer().getName() + " hat das Land erobert.");
			genugEinheiten = false;
			// wird das ab hier genutzt?
			if (aLand.getEinheiten() == 2) {
				consolePanel.textSetzen("Eine Einheit wird auf " + vLand.getName() + " gesetzt.");
				sp.eroberungBesetzen(aLand, vLand, 1);
				genugEinheiten = true;
				spielfeld.fahneEinheit(land1.getEinheitenLab());
				spielfeld.fahneEinheit(land2.getEinheitenLab());
				land1 = null;
				land2 = null;
				buttonPanel.angreifenAktiv("angreifendes Land", "verteidigendes Land");
			} else {
				// ausgabe += "Wie viele Einheiten m\u00F6chtest du auf " +
				// vLand.getName() + " setzen?";
				// ausgabe += aLand.getEinheiten() - 1 + " Einheiten kannst du
				// setzen";
				// int einheiten = 1;
				// if(einheiten < aLand.getEinheiten() && einheiten > 0){
				// sp.eroberungBesetzen(aLand, vLand, einheiten);
				// ausgabe += sp.einheitenAusgabe(aLand, vLand);
				// genugEinheiten = true;
				// }else{
				// ausgabe += "Bitte gebe eine Korrekte Zahl ein";
				buttonPanel.verschiebenNachAngreifenAktiv(aLand.getName(), vLand.getName());
			}
		}
	}

	@Override
	public void startButtonClicked() {
		this.remove(startPanel);
		spielErstellen();

	}

	@Override
	public void spielErstellen(String name, int anzahl) {
		spiel(name, anzahl);

	}

	@Override
	public void buttonClicked() {
		sp.nextTurn();
		spieler = sp.getAktiverSpieler();
		spielerListPanel.setAktiverSpieler(sp.getSpielerList().indexOf(spieler) + 1);
		switch (sp.getTurn()) {
		case STARTPHASE:
			buttonPanel.phaseDisable();
			anzahlSetzbareEinheiten = sp.checkAnfangsEinheiten();
			consolePanel.textSetzen(sp.getAktiverSpieler().getName()
					+ " du kannst nun deine ersten Einheiten setzen. Es sind " + anzahlSetzbareEinheiten);
			break;
		case ANGRIFF:
			consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " du kannst nun angreifen.");
			buttonPanel.angreifenAktiv("angreifendes Land", "verteidigendes Land");
			break;
		case VERTEILEN:
			buttonPanel.phaseDisable();
			anzahlSetzbareEinheiten = sp.bekommtEinheiten(sp.getAktiverSpieler());
			consolePanel.textSetzen(
					sp.getAktiverSpieler().getName() + " du kannst " + anzahlSetzbareEinheiten + " Einheiten setzen.");
			buttonPanel.verteilenAktiv(anzahlSetzbareEinheiten);
			missionPanel.setMBeschreibung(sp.getMissionVonAktivemSpieler().getBeschreibung());
			break;
		case VERSCHIEBEN:
			spielfeld.wuerfelEntfernen();
			consolePanel.textSetzen(sp.getAktiverSpieler().getName() + " verschiebe nun deine Einheiten.");
			buttonPanel.verschiebenAktiv("erstes Land", "zweites Land");
			break;
		}

		infoPanel.changePanel(sp.getTurn() + "");

	}

	@Override
	public void angriffClicked() {
		try {
			angriff(true, sp.getAktiverSpieler());

		} catch (KeinNachbarlandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void verschiebenClicked(int einheiten) {
		try {
			sp.checkEinheiten(land1.getName(), einheiten);
			sp.einheitenPositionieren(-einheiten, land1);
			sp.einheitenPositionieren(einheiten, land2);
			spielfeld.labelsSetzen("", land1.getEinheiten(), "");
			spielfeld.fahneEinheit(land1.getEinheitenLab());
			spielfeld.labelsSetzen("", land2.getEinheiten(), "");
			spielfeld.fahneEinheit(land2.getEinheitenLab());
			land1 = null;
			land2 = null;
			buttonPanel.verschiebenAktiv("erstes Land", "zweites Land");
			buttonPanel.verschiebenDisabled();
		} catch (NichtGenugEinheitenException ngee) {
			consolePanel.textSetzen(ngee.getMessage());
		}

	}

	@Override
	public void verschiebenNAClicked(int einheiten) {
		try {
			sp.checkEinheiten(land1.getName(), einheiten);
			sp.eroberungBesetzen(land1, land2, einheiten);
			spielfeld.fahneEinheit(land1.getEinheitenLab());
			spielfeld.fahneEinheit(land2.getEinheitenLab());
			land1 = null;
			land2 = null;
			buttonPanel.angreifenAktiv("erstes Land", "zweites Land");
		} catch (NichtGenugEinheitenException ngee) {
			consolePanel.textSetzen(ngee.getMessage());
	}
		
	}
}	