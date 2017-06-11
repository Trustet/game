package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import local.domain.Spielfeld;
import local.domain.exceptions.KannLandNichtBenutzenException;
import local.valueobjects.Land;

public class MapPanel extends JLayeredPane {

	private List<JLabel> fahnen = new Vector<JLabel>();
	public interface MapClickHandler {
		public void processMouseClick(int x, int y, Color color);
	}
	
	private JLabel spielfeld = null;
	private JLabel weltKarteBuntLab = null;
	private JLabel  fahneRotLab = null;
	private JLabel  fahneBlauLab = null;
	private JLabel  fahneGruenLab = null;
	private MapClickHandler handler = null;
	private BufferedImage weltKarteBunt;
	private Spielfeld sp;
  
	private JLabel landLab = null;
	private JLabel einheitenLab = null;
	private JLabel besitzerLab = null;
	
	
	public MapPanel(MapClickHandler handler, Spielfeld sp) {
		this.sp = sp;
		this.handler = handler;
		initialize();
	}
	
	public void initialize() {
        BufferedImage myPicture;
   
        
        try {
			myPicture = ImageIO.read(new File("./weltkarte.jpg"));
			spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 550, Image.SCALE_FAST)));
			weltKarteBunt = ImageIO.read(new File("./weltkarte_bunt.png"));
			weltKarteBuntLab = new JLabel(new ImageIcon(weltKarteBunt));
			
//			fahneBlauLab = new JLabel(new ImageIcon(fahneBlauImg.getScaledInstance(20, 20, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        spielfeld.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int farbenInt =  weltKarteBunt.getRGB(e.getX(), e.getY());
				Color color = new Color(farbenInt, true);
				String landcode = Integer.toString(color.getRed()) + "" + Integer.toString(color.getGreen()) + "" + Integer.toString(color.getBlue());
				landWaehlen(landcode);
//				System.out.println(farbenInt);
//				System.out.println(Integer.toString(color.getRed()) + " " + Integer.toString(color.getGreen()) + " " + Integer.toString(color.getBlue()));
				handler.processMouseClick(e.getX(), e.getY(), color);
			}
		});

        
//        JTextArea ta = new JTextArea("Infos",10,20);
//        ta.setBounds(500, 0, 100, 50);
        landLab = new JLabel("Land:");
        einheitenLab = new JLabel("Einheiten:");
        besitzerLab = new JLabel("Besitzer:");
        landLab.setBounds(400,0,200,15);
        einheitenLab.setBounds(400, 15, 200, 15);
        besitzerLab.setBounds(400, 30, 200, 15);
        
        
//        fahneLab.setBounds(46, 64, 20, 20);
        spielfeld.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setVisible(false);
        this.add(spielfeld,new Integer(2), 1); 

        this.add(weltKarteBuntLab);
//      this.add(fahneLab,new Integer(2), 0);
        this.add(landLab,new Integer(2), 0);
        this.add(einheitenLab,new Integer(2), 0);
        this.add(besitzerLab, new Integer(2), 0);
        this.setPreferredSize(new Dimension(1050,550));
        
	}
	
	public void fahnenVerteilen(){
		String farbe = "";
		JLabel fahne = null;
		JLabel einheiten = null;
		for(Land l : sp.getLaenderListe()){
			l.setFahne(l.getBesitzer().getFarbe());
			fahne = l.getFahne();	
			fahnen.add(fahne);
			einheiten = l.getEinheitenLab();
			
			this.add(fahne, new Integer(2), 0);
			this.add(einheiten, new Integer(2),0);
		}
		

	}
	
	public List<JLabel> getFahnenList(){
		return this.fahnen;
	}
	
	public void landWaehlen(String landcode){
		String landstring = "";
		switch(landcode){
		//Alaska
		case "3917935":
			landstring = "Alaska";
			break;
		//Nordwest-Terretorium
		case "1791340":
			landstring = "Nordwest-Territorium";
			break;
		//Alberta
		case "0162179":
			landstring = "Alberta";
			break;
		//Ontario
		case "1791680":
			landstring = "Ontario";
			break;
		//Weststaaten
		case "17917879":
			landstring = "Weststaaten";
			break;
		//Quebeck
		case "241790":
			landstring = "Quebec";
			break;
		//Groenland
		case "1793926":
			landstring = "Groenland";
			break;
		//Oststaaten
		case "117179111":
			landstring = "Oststaaten";
			break;
		//Mittelamerika
		case "1791510":
			landstring = "Mittelamerika";
			break;
		//Venezuela
		case "17915858":
			landstring = "Venezuela";
			break;
		//Peru
		case "179160100":
			landstring = "Peru";
			break;
		//Argentinien
		case "1791160":
			landstring = "Argentinien";
			break;
		//Brasilien
		case "17913757":
			landstring = "Brasilien";
			break;
		//Nordwestafrika
		case "179350":
			landstring = "Nordwestafrika";
			break;
		//Aegypten
		case "1798555":
			landstring = "Aegypten";
			break;
		//Ostafrika
		case "17911097":
			landstring = "Ostafrika";
			break;
		//Kongo
		case "1799764":
			landstring = "Kongo";
			break;
		//Suedafrika
		case "17910042":
			landstring = "Suedafrika";
			break;
		//Madagaskar
		case "179900":
			landstring = "Madagaskar";
			break;
		//Westeuropa
		case "179062":
			landstring = "West-Europa";
			break;
		//Suedeuropa
		case "8750179":
			landstring = "Sued-Europa";
			break;
		//Mitteleuropa
		case "13247179":
			landstring = "Mittel-Europa";
			break;
		//Grossbritannien
		case "17960":
			landstring = "Grossbritannien";
			break;
		//Island
		case "2552442":
			landstring = "Island";
			break;
		//Skandinavien
		case "17931158":
			landstring = "Skandinavien";
			break;
		//Ukraine
		case "13982179":
			landstring = "Ukraine";
			break;
		//Mittlerer Osten
		case "174110179":
			landstring = "Mittlerer Osten";
			break;
		//Indien
		case "52121179":
			landstring = "Indien";
			break;
		//Siam
		case "0128179":
			landstring = "Siam";
			break;
		//China
		case "61168179":
			landstring = "China";
			break;
		//Afghanistan
		case "179135160":
			landstring = "Afghanistan";
			break;
		//Ural
		case "170111179":
			landstring = "Ural";
			break;
		//Mongolei
		case "40179123":
			landstring = "Mongolei";
			break;
		//Sibirien
		case "151121179":
			landstring = "Sibirien";
			break;
		//Irtusk
		case "1031790":
			landstring = "Irtusk";
			break;
		//Jakutien
		case "7017925":
			landstring = "Jakutien";
			break;
		//Kamtschatka
		case "3817950":
			landstring = "Kamtschatka";
			break;
		//Japan
		case "21179158":
			landstring = "Japan";
			break;
		//Indonesien
		case "061179":
			landstring = "Indonesien";
			break;
		//Neuguniea
		case "010179":
			landstring = "Neu-Guinea";
			break;
		//Westaustralien
		case "109107179":
			landstring = "Westaustralien";
			break;
		//Ostaustralien
		case "6765179":
			landstring = "Ostaustralien";
			break;
		}
		Land land = sp.stringToLand(landstring);
		if(land != null){
			landLab.setText("Land: " + land.getName());
			einheitenLab.setText("Einheiten: " + land.getEinheiten());
			besitzerLab.setText("Besitzer: " + land.getBesitzer().getName());
			switch(sp.getTurn()){
			case ANGRIFF:
				break;
			case VERTEILEN:
				try{
					boolean kannLandBenutzen = sp.landWaehlen(landstring,sp.getAktiverSpieler());
					sp.einheitenPositionieren(1, land);
					einheitenLab.setText("Einheiten: " + land.getEinheiten());
				}catch(KannLandNichtBenutzenException lene ){
					System.out.println(lene.getMessage());
				}
				break;
			case VERSCHIEBEN:
				break;
			}
		}
	}
}
