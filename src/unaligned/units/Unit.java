package unaligned.units;

import main.Team;
import unaligned.UnalignedDrawableObject;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

abstract public class Unit extends UnalignedDrawableObject {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private Team team;
	private int health;

	public Unit(int xPos, int yPos, Team team) {
		super(xPos, yPos);
		this.team = team;
		health = getMaxHealth();
	}

	public Team getTeam() {
		return team;
	}

	abstract public void update();

	abstract public int getMaxHealth();

	abstract public double getMovementSpeed();

}
