package jobs;

import java.awt.Graphics2D;

import gridAligned.Blueprint;
import gridAligned.Structure;
import main.Game;
import unaligned.units.WorkerUnit;
import unaligned.units.workers.Builder;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class ConstructionJob extends Job {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private Structure structure;

	public ConstructionJob(Structure structure) {
		super(structure.getRow(), structure.getCol(), structure.getMaxHealth());
		this.structure = structure;
		Game.getGame().getGridTileAt(getRow(), getCol()).setBlueprintLayer(new Blueprint(structure));
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
		Game.getGame().getGridTileAt(getRow(), getCol()).setStructureLayer(structure);
		Game.getGame().getGridTileAt(getRow(), getCol()).setBlueprintLayer(null);
	}
}
