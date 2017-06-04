package client;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.imageio.ImageIO;
import java.awt.Color;

public class MapPanel extends JPanel {

	public interface MapClickHandler {
		public void processMouseClick(int x, int y, Color color);
	}
	
	private JLabel spielfeld = null;
	private JLabel weltKarteBuntLab = null;
	private MapClickHandler handler = null;
	private BufferedImage weltKarteBunt;
	
	public MapPanel(MapClickHandler handler) {
		this.handler = handler;
		initialize();
	}
	
	public void initialize() {
        BufferedImage myPicture;
        
        try {
			myPicture = ImageIO.read(new File("./weltkarte.jpg"));
			spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 550, Image.SCALE_FAST)));
			weltKarteBunt = ImageIO.read(new File("./weltkarte_bunt.jpg"));
			weltKarteBuntLab = new JLabel(new ImageIcon(weltKarteBunt));
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
				case "8932189": System.out.println("Ukraine");
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
        
        spielfeld.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setBounds(0, 0, 1050, 550);
        weltKarteBuntLab.setVisible(false);
        this.add(spielfeld);
        this.add(weltKarteBuntLab);
	}
}
