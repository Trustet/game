package local.ui.gui;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FensterSchliessen extends WindowAdapter {

	
		public void fensterSchliesser(WindowEvent e) {
			
			Window gui = e.getWindow();

			gui.setVisible(false);
			gui.dispose();
			System.exit(0);
		}
}
