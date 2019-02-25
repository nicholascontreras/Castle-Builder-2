package gridAligned;

import main.Team;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

abstract public class Structure extends GridAlignedDrawableObject {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private int health;

	private Team team;

	public Structure(int row, int col, Team team) {
		super(row, col);
		this.team = team;
		this.health = getMaxHealth();
	}

	public Team getTeam() {
		return team;
	}
	
	abstract public int getMaxHealth();

	abstract public double getMovementSpeedModifier();
}
