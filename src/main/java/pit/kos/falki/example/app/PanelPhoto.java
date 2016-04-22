package pit.kos.falki.example.app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class PanelPhoto extends JPanel {

	private static final long serialVersionUID = 6043915371957014116L;
	
	private BufferedImage bufferedImage;

	@Override
	public void paint(Graphics g) {
		g.drawImage(bufferedImage, 0, 0, this.getWidth(), this.getHeight(),null);
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
		
	
	

}
