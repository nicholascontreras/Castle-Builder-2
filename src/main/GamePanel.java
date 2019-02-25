package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import ui.UI;

/**
 * @author Nicholas Contreras
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	private JFrame frame;

	@Override
	public void run() {

		setUIFont(new FontUIResource("SansSerif", Font.PLAIN, 20));

		frame = new JFrame("Castle Builder");

		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(InputManager.getInst(this));
		this.addMouseListener(InputManager.getInst(this));
		this.addMouseMotionListener(InputManager.getInst(this));
		this.addMouseWheelListener(InputManager.getInst(this));

		JPanel outerPanel = new JPanel(new BorderLayout());

		outerPanel.add(this, BorderLayout.CENTER);
		outerPanel.add(new UI(), BorderLayout.EAST);

		frame.add(outerPanel);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		frame.setPreferredSize(screenSize);
		frame.setMinimumSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Camera.startCamera();

		new Timer(true).schedule(new TimerTask() {
			@Override
			public void run() {
				Game.getGame().update();
				repaint();
			}
		}, 0, 1000 / 30);

//		new Timer(true).scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				Game.getGame().update();
//			}
//		}, 0, 1000 / 30);
	}

	@Override
	protected void paintComponent(Graphics g) {

		this.requestFocusInWindow();

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		AffineTransform camera = Camera.getCameraTransform(getWidth(), getHeight());
		g2d.transform(camera);

		for (int row = 0; row < Game.getGame().getGridRows(); row++) {
			for (int col = 0; col < Game.getGame().getGridCols(); col++) {
				Game.getGame().getGridTileAt(row, col).draw(g2d);
			}
		}

		for (int i = 0; i < Game.getGame().getGameObjects().size(); i++) {
			Game.getGame().getGameObjects().get(i).draw(g2d);
		}
	}

	private void setUIFont(javax.swing.plaf.FontUIResource f) {
		java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
	}

	public void mouseClick(int x, int y, int button) {
		try {
			AffineTransform inverse = Camera.getCameraTransform(getWidth(), getHeight()).createInverse();
			Point2D p = inverse.transform(new Point(x, y), null);
			x = (int) (p.getX());
			y = (int) (p.getY());

			if (button == MouseEvent.BUTTON1) {
				Game.getGame().leftMouseClick(x, y);
			} else if (button == MouseEvent.BUTTON3) {
				Game.getGame().rightMouseClick(x, y);
			}
		} catch (NoninvertibleTransformException e) {
			UI.showErrorMessage("If you ever see this error something has gone really, really wrong...", e, "", this);
		}
	}
}
