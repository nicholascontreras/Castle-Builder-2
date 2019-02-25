package unaligned.units;

import java.util.ArrayList;

import jobs.Job;
import main.Game;
import main.Team;
import pathfinder.Pathfinder;
import pathfinder.Pathfinder.Path;
import util.Constants;
import util.Util;

/**
 * @author Nicholas Contreras
 */

abstract public class WorkerUnit extends Unit {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private static ArrayList<Job> jobQueue;

	private Job currentJob;
	private Path curPath;
	private int targetRow, targetCol;

	public WorkerUnit(int x, int y, Team team) {
		super(x, y, team);
		targetRow = getRow();
		targetCol = getCol();
	}

	public static void setJobQueue(ArrayList<Job> queue) {
		jobQueue = queue;
	}

	public void update() {
		if (currentJob == null) {
			getNewJob();
			curPath = null;
		} else if (curPath == null) {
			if (Pathfinder.isPathReadyFor(this)) {
				curPath = Pathfinder.getPathFor(this);

				if (curPath == null) {
					jobQueue.add(currentJob);
					currentJob = null;
				}
			} else if (Pathfinder.isRequestWaitingFor(this)) {
				// do nothing; we're waiting for the path
			} else {
				Pathfinder.requestPath(this, getRow(), getCol(), currentJob.getRow(), currentJob.getCol(),
						currentJob.workBeside());
			}
		} else {
			if (isCenteredOnTile(targetRow, targetCol)) {
				curPath.nextStep();
				if (!curPath.isEmpty()) {
					targetRow = curPath.curStepRow();
					targetCol = curPath.curStepCol();
				} else {
					currentJob.doWork();

					if (currentJob.isDone()) {
						currentJob.finishJob();
						currentJob = null;
					}
				}
			} else {
				moveTowardsTarget();
			}
		}

		updateSub();
	}

	private void moveTowardsTarget() {
		double movementSpeedOnTile = getMovementSpeed()
				* Game.getGame().getGridTileAt(getRow(), getCol()).getMovementSpeedModifier();

		int targX = Util.gridToPixel(targetCol);
		int targY = Util.gridToPixel(targetRow);

		if (Util.getDist(getXPos(), getYPos(), targX, targY) <= movementSpeedOnTile) {
			moveTo(targX, targY);
		} else {
			int deltaX = targX - getXPos();
			int deltaY = targY - getYPos();

			if (Math.abs(deltaX) > movementSpeedOnTile) {
				move(Math.copySign(movementSpeedOnTile, deltaX), 0);
			} else {
				move(0, Math.copySign(movementSpeedOnTile, deltaY));
			}
		}
	}

	protected abstract void updateSub();

	public boolean hasJob() {
		return currentJob != null;
	}

	protected void getNewJob() {
		for (int i = 0; i < jobQueue.size(); i++) {
			if (jobQueue.get(i).canDoJob(this)) {
				currentJob = jobQueue.remove(i);
				return;
			}
		}
	}
}
