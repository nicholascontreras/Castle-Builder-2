package gridAligned.structures;

import java.awt.Graphics2D;

import gridAligned.Structure;
import main.Team;
import util.Constants;
import util.ImageManager;

/**
 * @author Nicholas Contreras
 */

public class Nexus extends Structure {

	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public Nexus(int row, int col, Team team) {
		super(row, col, team);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(ImageManager.getImageForTeam("nexus", getTeam()), getX(), getY(), null);
	}

	@Override
	public double getMovementSpeedModifier() {
		return -1;
	}

	@Override
	public int getMaxHealth() {
		return 100;
	}
}
