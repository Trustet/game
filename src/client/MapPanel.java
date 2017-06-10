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
				String colorSwitch = Integer.toString(color.getRed()) + "" + Integer.toString(color.getGreen()) + "" + Integer.toString(color.getBlue());
				switch(colorSwitch){
				case "3917935": System.out.println("Alaska");
					try {
						sp.landWaehlen("Alaska", sp.getAktiverSpieler());
					} catch (KannLandNichtBenutzenException e1) {
						JOptionPane.showMessageDialog(null,e1.getMessage(),"Fehler",JOptionPane.WARNING_MESSAGE);
					}
					break;
				case "212161104": System.out.println("NordwestAfrika");
					break;
				case "25119745": System.out.println("Brasilien");
					break;
				case "7862184": System.out.println("Mittel Europa");
					break;
				}
				System.out.println(Integer.toString(color.getRed()) + " " + Integer.toString(color.getGreen()) + " " + Integer.toString(color.getBlue()));
				handler.processMouseClick(e.getX(), e.getY(), color);
			}
		});



//        fahneLab.setBounds(46, 64, 20, 20);
        spielfeld.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setVisible(false);
        this.add(spielfeld,new Integer(2), 1);
        this.add(weltKarteBuntLab);
//        this.add(fahneLab,new Integer(2), 0);

        this.setPreferredSize(new Dimension(1050,550));
        
	}
	
	public void fahnenVerteilen(){
		String farbe = "";
		JLabel fahne = null;
		for(Land l : sp.getLaenderListe()){
			l.setFahne(l.getBesitzer().getFarbe());
			fahne = l.getFahne();	
			fahnen.add(fahne);
			this.add(fahne, new Integer(2), 0);
		}
		

	}
	
	public List<JLabel> getFahnenList(){
		return this.fahnen;
	}
}
