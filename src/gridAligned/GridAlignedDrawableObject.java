package gridAligned;

import java.awt.Graphics2D;
import java.io.Serializable;

import interfaces.Drawable;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

abstract public class GridAlignedDrawableObject implements Serializable, Drawable {
	private static final long serialVersionUID = 1;

	private int row, col;

	public GridAlignedDrawableObject(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getX() {
		return col * Constants.GRID_PIXEL_SIZE;
	}

	public int getY() {
		return row * Constants.GRID_PIXEL_SIZE;
	}

	public void moveTo(int row, int col) {
		this.row = row;
		this.col = col;
	}

	abstract public void draw(Graphics2D g2d);
}
