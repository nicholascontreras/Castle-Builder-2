package unaligned;

import java.awt.Graphics2D;
import java.io.Serializable;

import interfaces.Drawable;
import util.Constants;
import util.Util;

/**
 * @author Nicholas Contreras
 */

abstract public class UnalignedDrawableObject implements Serializable, Drawable {
	private static final long serialVersionUID = 1;

	private double xPos, yPos;

	public UnalignedDrawableObject(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getXPos() {
		return (int) xPos;
	}

	public int getYPos() {
		return (int) yPos;
	}

	public int getRow() {
		return (int) (yPos / Constants.GRID_PIXEL_SIZE);
	}

	public int getCol() {
		return (int) (xPos / Constants.GRID_PIXEL_SIZE);
	}

	protected void moveTo(double xPos, double yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	protected void move(double deltaX, double deltaY) {
		this.xPos += deltaX;
		this.yPos += deltaY;
	}

	public boolean isCenteredOnTile(int row, int col) {
		int targetX = col * Constants.GRID_PIXEL_SIZE + Constants.GRID_PIXEL_SIZE / 2;
		int targetY = row * Constants.GRID_PIXEL_SIZE + Constants.GRID_PIXEL_SIZE / 2;

		return Util.getDist(getXPos(), getYPos(), targetX, targetY) < 1;
	}
}
