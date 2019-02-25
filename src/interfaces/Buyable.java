package interfaces;

import main.Team;

/**
 * @author Nicholas Contreras
 */

public interface Buyable {

	public int getGoldPrice();

	public int getFoodPrice();

	public Buyable getDisplayVersion(Team team);
}
