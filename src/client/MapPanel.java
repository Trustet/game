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

	private List<JLabel> fahnenLabs = new Vector<JLabel>();
	private List<JLabel> einheitenLabs = new Vector<JLabel>();
	
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
  
	public JLabel landLab = null;
	public JLabel einheitenLab = null;
	public JLabel besitzerLab = null;
	
	
	public MapPanel(MapClickHandler handler) {
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
	
	public void fahnenVerteilen(List<Land> laender){
		String farbe = "";
		JLabel fahne = null;
		JLabel einheiten = null;
		for(Land l : laender){
			l.setFahne(l.getBesitzer().getFarbe());
			fahne = l.getFahne();	
			fahnenLabs.add(fahne);
			einheiten = l.getEinheitenLab();
			einheitenLabs.add(einheiten);
			this.add(fahne, new Integer(2), 0);
			this.add(einheiten, new Integer(2),0);
		}
		

	}
	
	public void fahneEinheit(JLabel einheitenNeu){
		for(JLabel l : einheitenLabs){
			if(l.equals(einheitenNeu)){
				l = einheitenNeu;
			}
		}
	}
	
	public List<JLabel> getFahnenList(){
		return this.fahnenLabs;
	}
	
	public void labelsSetzen(String lName, int lEinheiten, String lBesitzer){
		if(lName.length() > 0){
			landLab.setText("Land: " + lName);
		}
		if(lEinheiten != 9119){
			einheitenLab.setText("Einheiten: " + lEinheiten);
		}
		if(lBesitzer.length() > 0){
			besitzerLab.setText("Besitzer: " + lBesitzer);
		}
		
	}
	
	
}
