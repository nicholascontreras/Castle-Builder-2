package main;

import static java.awt.event.KeyEvent.*;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Nicholas Contreras
 */

public class Camera {

	private static final double MIN_ZOOM = 0.1;
	private static final double MAX_ZOOM = 2;
	private static final double KEYBOARD_PAN_SPEED = 20;
	private static final double KEYBOARD_ZOOM_SPEED = 0.02;
	private static final double MOUSE_PAN_SPEED = 1;
	private static final double MOUSE_ZOOM_SPEED = -0.1;

	private static double cameraX = 0, cameraY = 0;
	private static double zoom = 1;

	public static void startCamera() {

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (InputManager.isKeyPressed(VK_S)) {
					cameraY -= KEYBOARD_PAN_SPEED;
				} else if (InputManager.isKeyPressed(VK_W)) {
					cameraY += KEYBOARD_PAN_SPEED;
				}
				if (InputManager.isKeyPressed(VK_D)) {
					cameraX -= KEYBOARD_PAN_SPEED;
				} else if (InputManager.isKeyPressed(VK_A)) {
					cameraX += KEYBOARD_PAN_SPEED;
				}

				if (InputManager.isMouseButtonPressed(MouseEvent.BUTTON3)) {
					cameraX += InputManager.getMouseMovementX() * MOUSE_PAN_SPEED * (1 / zoom);
					cameraY += InputManager.getMouseMovementY() * MOUSE_PAN_SPEED * (1 / zoom);
				} else {
					InputManager.clearMouseMovement();
				}

				if (InputManager.isKeyPressed(VK_Q)) {
					zoom -= KEYBOARD_ZOOM_SPEED;
				} else if (InputManager.isKeyPressed(VK_E)) {
					zoom += KEYBOARD_ZOOM_SPEED;
				}

				zoom += InputManager.getMouseWheelRotation() * MOUSE_ZOOM_SPEED;

				zoom = Math.max(zoom, MIN_ZOOM);
				zoom = Math.min(zoom, MAX_ZOOM);
			}
		}, 0, 20);
	}

	public static AffineTransform getCameraTransform(int panelWidth, int panelHeight) {
		AffineTransform at = new AffineTransform();
		at.translate(panelWidth / 2, panelHeight / 2);
		at.scale(zoom, zoom);
		at.translate(-panelWidth / 2, -panelHeight / 2);
		at.translate(cameraX, cameraY);
		at.translate(panelWidth / 2, panelHeight / 2);
		return at;
	}
}
