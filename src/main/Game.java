package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;

import gridAligned.Blueprint;
import gridAligned.Grass;
import gridAligned.GridAlignedDrawableObject;
import gridAligned.Structure;
import gridAligned.structures.Nexus;
import gridAligned.structures.Wall;
import interfaces.Buyable;
import jobs.ConstructionJob;
import jobs.DemolitionJob;
import jobs.Job;
import pathfinder.Pathfinder;
import unaligned.UnalignedDrawableObject;
import unaligned.units.Unit;
import unaligned.units.WorkerUnit;
import unaligned.units.workers.Builder;
import util.Constants;
import util.Util;

/**
 * @author Nicholas Contreras
 */

public class Game implements Serializable {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private static Game game;

	private GridTile[][] gameGrid;
	private ArrayList<UnalignedDrawableObject> gameObjects;
	private ArrayList<Job> jobQueue;

	private Team playerTeam;

	private Buyable itemToBuy;

	private boolean paused;

	public static void setGame(Game g) {
		game = g;
	}

	public static Game getGame() {
		return game;
	}

	public Game() {
		gameGrid = new GridTile[Constants.NUM_ROWS][Constants.NUM_COLS];
		gameObjects = new ArrayList<UnalignedDrawableObject>();

		jobQueue = new ArrayList<Job>();
		WorkerUnit.setJobQueue(jobQueue);

		playerTeam = new Team(1, Color.CYAN);

		itemToBuy = new Wall(-1, -1, playerTeam);

		Pathfinder.startPathfinder();

		Color[][] grassColors = new Color[getGridRows()][getGridCols()];

		for (int row = 0; row < getGridRows(); row++) {
			for (int col = 0; col < getGridCols(); col++) {
				gameGrid[row][col] = new GridTile(row, col);
				grassColors[row][col] = gameGrid[row][col].getTerrainLayer().getColor();
			}
		}

		for (int i = 0; i < 5; i++) {
			grassColors = Util.blur(grassColors, 0.75f);
		}

		for (int row = 0; row < getGridRows(); row++) {
			for (int col = 0; col < getGridCols(); col++) {
				gameGrid[row][col].getTerrainLayer().setColor(grassColors[row][col]);
			}
		}
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void update() {
		if (!paused) {
			for (int i = 0; i < gameObjects.size(); i++) {
				UnalignedDrawableObject udo = gameObjects.get(i);

				if (udo instanceof Unit) {
					((Unit) udo).update();
				}
			}
		}
	}

	public GridTile[][] getGameGrid() {
		return gameGrid;
	}

	public GridTile getGridTileAt(int row, int col) {
		return gameGrid[row][col];
	}

	public ArrayList<UnalignedDrawableObject> getGameObjects() {
		return gameObjects;
	}

	public int getGridRows() {
		return gameGrid.length;
	}

	public int getGridCols() {
		return gameGrid[0].length;
	}

	public void leftMouseClick(int x, int y) {
		if (itemToBuy == null) {
			selectItemAt(x, y);
		} else {
			buyItem(x, y);
		}
	}

	public void rightMouseClick(int x, int y) {
		// TODO Auto-generated method stub

	}

	public boolean isOnGrid(int row, int col) {
		return row >= 0 && row < gameGrid.length && col >= 0 && col < gameGrid[row].length;
	}

	public void setItemToBuy(Buyable b) {
		itemToBuy = b;
	}

	public void addJobToQueue(Job job) {
		jobQueue.add(job);
	}

	public void addGameObject(UnalignedDrawableObject udo) {
		gameObjects.add(udo);
	}

	public Team getPlayerTeam() {
		return playerTeam;
	}

	private void buyItem(int x, int y) {
		int row = y / Constants.GRID_PIXEL_SIZE;
		int col = x / Constants.GRID_PIXEL_SIZE;

		if (getGridTileAt(row, col).getStructureLayer() == null) {
			if (itemToBuy instanceof Structure) {
				Structure buying = (Structure) itemToBuy;
				buying.moveTo(row, col);
				jobQueue.add(new ConstructionJob(buying));
			} else {
				gameObjects.add((UnalignedDrawableObject) itemToBuy);
			}
		} else {
			if (itemToBuy instanceof Structure) {

			}
		}

		itemToBuy = itemToBuy.getDisplayVersion(playerTeam);
	}

	private void selectItemAt(int x, int y) {
		int row = y / Constants.GRID_PIXEL_SIZE;
		int col = x / Constants.GRID_PIXEL_SIZE;

		if (getGridTileAt(row, col).getStructureLayer() != null) {
			jobQueue.add(new DemolitionJob(row, col));
		}
	}
}
