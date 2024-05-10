package main;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.*;
import java.io.Serial;

public class Pantalla_Inicio {
	public static void main(String[] args) {
//	    Thread cargaPartidaThread = new Thread(() -> Control_de_datos.cargarPartida());
//	    cargaPartidaThread.start();

		
//		Thread iniciar_partida = new Thread(() -> Marco mimimarco = new Marco());
		
	    SwingUtilities.invokeLater(() -> {
	        Marco mimimarco = new Marco();
	        mimimarco.setVisible(true);
	    });
	}

}

class Marco extends JFrame{
    /**
	 * 
	 */
	@Serial
    private static final long serialVersionUID = 1L;
	public Marco(){
		setTitle("Pandemic");
        setIconImage(new ImageIcon("src//img//icon.png").getImage());
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (graphicsDevice.isFullScreenSupported()) {
            setUndecorated(true);
            setResizable(false);
            graphicsDevice.setFullScreenWindow(this);
        } else {
            System.out.println("The full-screen mode is not supported by this device.");
        }

        menu menu = main.menu.getInstance();
        
        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(menu);

        setVisible(true);
	}
}

class CustomProgressBarUI extends BasicProgressBarUI {
    private Image backgroundImage;

    public CustomProgressBarUI(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        super.paintDeterminate(g, c);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(backgroundImage, 0, 0, c.getWidth(), c.getHeight(), null);

        g2d.dispose();
    }
}