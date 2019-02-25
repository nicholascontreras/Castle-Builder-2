package jobs;

import java.awt.Graphics2D;

import gridAligned.GridAlignedDrawableObject;
import unaligned.units.WorkerUnit;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

abstract public class Job extends GridAlignedDrawableObject {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private int totalWork;
	private int workDone;

	public Job(int row, int col, int work) {
		super(row, col);
		totalWork = work;
		workDone = 0;
	}

	public void doWork() {
		workDone++;
	}

	public boolean isDone() {
		return workDone >= totalWork;
	}

	abstract public boolean workBeside();

	abstract public boolean canDoJob(WorkerUnit unit);

	abstract public void finishJob();

	@Override
	public void draw(Graphics2D g2d) {
	}

}
