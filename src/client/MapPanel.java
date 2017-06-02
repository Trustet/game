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

public class MapPanel extends JPanel {

	public interface MapClickHandler {
		public void processMouseClick(int x, int y);
	}
	
	private JLabel spielfeld = null;
	private MapClickHandler handler = null;
	
	public MapPanel(MapClickHandler handler) {
		this.handler = handler;
		initialize();
	}
	
	public void initialize() {
        BufferedImage myPicture;
        try {
			myPicture = ImageIO.read(new File("./weltkarte.jpg"));
			spielfeld = new JLabel(new ImageIcon(myPicture.getScaledInstance(1050, 550, Image.SCALE_FAST)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        spielfeld.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				handler.processMouseClick(e.getX(), e.getY());
			}
		});
        
        this.add(spielfeld);
	}
}
