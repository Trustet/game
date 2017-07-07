package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import local.valueobjects.Einheitenkarten;
import local.valueobjects.Spieler;
import net.miginfocom.swing.MigLayout;

public class MissionPanel extends JPanel{
	public interface KarteClickedHandler{
		public void karteEintauschen(List<String> tauschKarten);
		public void tauschFehlgeschlagen();
	}
	private JLabel mArt = new JLabel();
	//private JTextArea mBeschreibung = new JTextArea();
	private List<JLabel> kartenListe = new Vector<JLabel>();
	private KarteClickedHandler handler;
	private JLabel mBeschreibung = new JLabel();
	private JLabel kBeschreibung = new JLabel();
	private Font schrift;
	private Font uberschrift;
	private ImageIcon icon2 = null;
	private ImageIcon icon3 = null;
	private ImageIcon icon4 = null;
	private JComponent karten;
	private List<Spieler> spielerListe;
	private int wert = 0;
	private Border border = null;
	private int kartenZahl = 0;
	private List<String> kartenWahl = new Vector<String>();
	private String kartenSpeicher1 = null;
	private String kartenSpeicher2 = null;
	private String kartenSpeicher3 = null;
	BufferedImage iconEinheiten;
	BufferedImage iconPferd;
	BufferedImage iconKanone;
	public MissionPanel(Font uberschrift, Font schrift, KarteClickedHandler handler){
		this.spielerListe = spielerListe;
		this.handler = handler;
		this.schrift = schrift;
		this.uberschrift = uberschrift;
		initialize();
	}

	public void initialize(){
		//könnte weg, sieht aber eig ganz cool aus
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(uberschrift);
		JComponent mission = new JPanel();
		mission.setLayout(new MigLayout("wrap1","[]","[]"));
		//		mBeschreibung.setEditable(false);
		//		mBeschreibung.setLineWrap(true);
		mBeschreibung.setText("Missionsbeschreibung");
		mBeschreibung.setFont(schrift);
		//		mission.add(new JLabel("Mission:"),"left");
		//		mission.add(mArt,"center");
		mission.add(mBeschreibung,"center, growx, growy");
		mission.setPreferredSize(new Dimension(280,140));

		karten = new JPanel();
		karten.setLayout(new MigLayout("debug, wrap5","[][][][][]","[][][]"));


		try{
			iconEinheiten = ImageIO.read(new File("./soldat.jpg"));
			iconPferd = ImageIO.read(new File("./land.jpg"));
			iconKanone = ImageIO.read(new File("./karten.png"));

		} catch (IOException e){

		}

		icon2 = new ImageIcon(iconEinheiten.getScaledInstance(40, 40, Image.SCALE_FAST));
		icon3 = new ImageIcon(iconPferd.getScaledInstance(40, 40, Image.SCALE_FAST));
		icon4 = new ImageIcon(iconKanone.getScaledInstance(40, 40, Image.SCALE_FAST));
		border = BorderFactory.createLineBorder(Color.BLUE, 2);


		//
		//		icon2.addMouseListener(new MouseAdapter() {
		//
		//			@Override
		//			public void mouseClicked(MouseEvent e) {
		//				handler.karteEintauschen("Soldat");
		//				if(icon2.getBorder() != border){
		//					icon2.setBorder(border);
		//				}else{
		//					icon2.setBorder(null);
		//				}
		//			}
		//		});
		//		icon3.addMouseListener(new MouseAdapter() {
		//
		//			@Override
		//			public void mouseClicked(MouseEvent e) {
		//				handler.karteEintauschen("Pferd");
		//				if(icon3.getBorder() != border){
		//					icon3.setBorder(border);
		//				}else{
		//					icon3.setBorder(null);
		//				}
		//			}
		//		});
		//		icon4.addMouseListener(new MouseAdapter() {
		//
		//			@Override
		//			public void mouseClicked(MouseEvent e) {
		//				handler.karteEintauschen("Panzer");
		//				if(icon4.getBorder() != border){
		//					icon4.setBorder(border);
		//				}else{
		//					icon4.setBorder(null);
		//				}
		//				
		//			}
		//		});


		karten.add(new JLabel("Du hast folgende Karten:"),"center,spanx 5");

		//		karten.add(kBeschreibung);
		karten.setPreferredSize(new Dimension(280,140));

		tabbedPane.addTab("Mission",mission);
		tabbedPane.addTab("Karten", karten);
		this.add(tabbedPane);

	}

	public void setMBeschreibung(String beschreibung){

		//		mArt.setText(art);
		mBeschreibung.setText(beschreibung);


	}
	public void kartenTauschen(){
		System.out.println("Hier bin ich");
		
		for(String kartenString : kartenWahl){
			String karte = kartenString;
			if(kartenSpeicher1 == null){
				kartenSpeicher1 = karte;
				System.out.println("Speicher1");
			}else if(kartenSpeicher2 == null){
				kartenSpeicher2 = karte;
				System.out.println("Speicher2");
			}else if(kartenSpeicher3 == null){
				kartenSpeicher3 = karte;
				System.out.println("Speicher3");
				System.out.println(kartenSpeicher1 + " " + kartenSpeicher2 + " " + kartenSpeicher3);
				if(kartenSpeicher1 == kartenSpeicher2 && kartenSpeicher1 == kartenSpeicher3){


				}else if(kartenSpeicher1 != kartenSpeicher2 && kartenSpeicher2 != kartenSpeicher3 && kartenSpeicher1 != kartenSpeicher3){

				}else{
					handler.tauschFehlgeschlagen();
					
				}
			}
		}
		kartenZahl = 0;
		for(JLabel l : kartenListe){
			l.setBorder(null);
		}
		kartenSpeicher1 = null;
		kartenSpeicher2 = null;
		kartenSpeicher3 = null;
		while(kartenWahl.size() > 0){
			kartenWahl.remove(0);
		}
	}

	public void kartenAusgeben(Spieler spieler){
		for(JLabel k : kartenListe){
			karten.remove(k);
		}
		while(kartenListe.size() > 0){
			kartenListe.remove(0);
		}

		List<Einheitenkarten> kartenStapel = spieler.getEinheitenkarten();
		for(int i = 0; i < kartenStapel.size(); i++){
			int s = i;
			switch(kartenStapel.get(i).getKartenwert()){
			case "Soldat":
				kartenListe.add(new JLabel(icon2));
				kartenListe.get(i).addMouseListener(new MouseAdapter() {					
					@Override
					public void mouseClicked(MouseEvent e) {
						kartenZahl++;
						if(kartenListe.get(s).getBorder() != border){
							kartenListe.get(s).setBorder(border);
						}else{
							kartenListe.get(s).setBorder(null);
						}
						if(kartenWahl.size() == 2){
							kartenWahl.add("Soldat");
							kartenTauschen();
						}else{
							kartenWahl.add("Soldat");
						}

					}
				});

				break;
			case "Pferd":
				kartenListe.add(new JLabel(icon3));
				kartenListe.get(i).addMouseListener(new MouseAdapter() {					
					@Override
					public void mouseClicked(MouseEvent e) {
						kartenZahl++;
						if(kartenListe.get(s).getBorder() != border){
							kartenListe.get(s).setBorder(border);
						}else{
							kartenListe.get(s).setBorder(null);
						}
						if(kartenWahl.size() == 2){
							kartenWahl.add("Pferd");
							kartenTauschen();
						}else{
							kartenWahl.add("Pferd");
						}
					}
				});
				break;
			case "Panzer":
				kartenListe.add(new JLabel(icon4));
				kartenListe.get(i).addMouseListener(new MouseAdapter() {					
					@Override
					public void mouseClicked(MouseEvent e) {
						kartenZahl++;
						if(kartenListe.get(s).getBorder() != border){
							kartenListe.get(s).setBorder(border);
						}else{
							kartenListe.get(s).setBorder(null);
						}
						if(kartenWahl.size() == 2){
							kartenWahl.add("Panzer");
							kartenTauschen();
						}else{
							kartenWahl.add("Panzer");
						}
					}
				});
				break;
			}
			wert = 1;

		}
		for(JLabel l : kartenListe){
			karten.add(l);
		}

		karten.repaint();
	}
}