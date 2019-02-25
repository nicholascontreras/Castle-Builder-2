package gridAligned;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class Blueprint extends GridAlignedDrawableObject {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private Structure structure;

	public Blueprint(Structure structure) {
		super(structure.getRow(), structure.getCol());
		this.structure = structure;
	}

	@Override
	public void draw(Graphics2D g2d) {
		Composite c = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		if (structure != null) {
			structure.draw(g2d);
		} else {
			g2d.setColor(Color.RED);
			g2d.fillRect(getX(), getY(), Constants.GRID_PIXEL_SIZE, Constants.GRID_PIXEL_SIZE);
		}
		g2d.setComposite(c);
	}
}
