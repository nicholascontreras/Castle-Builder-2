package unaligned.units.workers;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Team;
import unaligned.units.WorkerUnit;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class Builder extends WorkerUnit {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	public Builder(int x, int y, Team team) {
		super(x, y, team);
	}

	@Override
	public void draw(Graphics2D g2d) {
		int topLeftX = getXPos() - Constants.GRID_PIXEL_SIZE / 4;
		int topLeftY = getYPos() - Constants.GRID_PIXEL_SIZE / 4;
		g2d.setColor(Color.ORANGE);
		g2d.fillOval(topLeftX, topLeftY, Constants.GRID_PIXEL_SIZE / 2, Constants.GRID_PIXEL_SIZE / 2);
	}

	@Override
	protected void updateSub() {
	}

	@Override
	public int getMaxHealth() {
		return 50;
	}

	@Override
	public double getMovementSpeed() {
		return 4;
	}
}
