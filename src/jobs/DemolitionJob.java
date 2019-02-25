package jobs;

import main.Game;
import unaligned.units.WorkerUnit;
import unaligned.units.workers.Builder;

/**
 * @author Nicholas Contreras
 */

public class DemolitionJob extends Job {

	public DemolitionJob(int row, int col) {
		super(row, col, Game.getGame().getGridTileAt(row, col).getStructureLayer().getMaxHealth());
	}

	@Override
	public boolean workBeside() {
		return true;
	}

	@Override
	public boolean canDoJob(WorkerUnit unit) {
		return unit instanceof Builder;
	}

	@Override
	public void finishJob() {
		Game.getGame().getGridTileAt(getRow(), getCol()).setStructureLayer(null);
	}
}
