package gridAligned;

import java.awt.Color;
import java.awt.Graphics2D;

import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class Grass extends GridAlignedDrawableObject {
	private static final long serialVersionUID = 1;

	private static final Color MAX_GREEN = new Color(0, 255, 0);
	private static final Color MIN_GREEN = new Color(100, 150, 100);

	private Color color;

	public Grass(int row, int col) {
		super(row, col);

		float[] max = MAX_GREEN.getRGBColorComponents(null);
		float[] min = MIN_GREEN.getRGBColorComponents(null);

		float[] newColor = new float[3];
		for (int i = 0; i < newColor.length; i++) {
			newColor[i] = (float) (Math.random() * (max[i] - min[i]) + min[i]);
		}

		color = new Color(newColor[0], newColor[1], newColor[2]);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect(getCol() * Constants.GRID_PIXEL_SIZE, getRow() * Constants.GRID_PIXEL_SIZE,
				Constants.GRID_PIXEL_SIZE, Constants.GRID_PIXEL_SIZE);
	}

}
