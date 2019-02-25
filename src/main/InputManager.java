package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Nicholas Contreras
 */

public class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	private static final HashSet<Integer> keysPressed = new HashSet<Integer>();;
	private static final HashSet<Integer> mouseButtonsPressed = new HashSet<Integer>();

	private static double mouseWheelRotation;

	private static int mouseX, mouseY, prevMouseX, prevMouseY;

	private static GamePanel gamePanel;

	private static InputManager inst;

	public static InputManager getInst(GamePanel gp) {
		gamePanel = gp;
		if (inst == null) {
			inst = new InputManager();
		}
		return inst;
	}

	public static boolean isKeyPressed(int keyCode) {
		return keysPressed.contains(keyCode);
	}

	public static boolean isMouseButtonPressed(int buttonNum) {
		return mouseButtonsPressed.contains(buttonNum);
	}

	public static double getMouseWheelRotation() {
		double temp = mouseWheelRotation;
		mouseWheelRotation = 0;
		return temp;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static int getMouseMovementX() {
		int deltaX = mouseX - prevMouseX;
		prevMouseX = mouseX;
		return deltaX;
	}

	public static int getMouseMovementY() {
		int deltaY = mouseY - prevMouseY;
		prevMouseY = mouseY;
		return deltaY;
	}

	public static void clearMouseMovement() {
		prevMouseX = mouseX;
		prevMouseY = mouseY;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysPressed.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysPressed.remove(e.getKeyCode());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelRotation += e.getPreciseWheelRotation();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		gamePanel.mouseClick(e.getX(), e.getY(), e.getButton());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButtonsPressed.add(e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButtonsPressed.remove(e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
