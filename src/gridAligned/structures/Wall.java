package gridAligned.structures;

import java.awt.Graphics2D;

import gridAligned.GridAlignedDrawableObject;
import gridAligned.Structure;
import interfaces.Buyable;
import main.Team;
import util.Constants;
import util.ImageManager;

/**
 * @author Nicholas Contreras
 */

public class Wall extends Structure implements Buyable {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public Wall(int row, int col, Team team) {
		super(row, col, team);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(ImageManager.getImage("wall"), getX(), getY(), null);
	}

	@Override
	public double getMovementSpeedModifier() {
		return -1;
	}

	@Override
	public int getMaxHealth() {
		return 100;
	}

	@Override
	public int getGoldPrice() {
		return 10;
	}

	@Override
	public int getFoodPrice() {
		return 0;
	}

	@Override
	public Buyable getDisplayVersion(Team team) {
		return new Wall(-1, -1, team);
	}
}
