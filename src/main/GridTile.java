package main;

import java.awt.Graphics2D;
import java.io.Serializable;

import gridAligned.Blueprint;
import gridAligned.Grass;
import gridAligned.Structure;
import interfaces.Drawable;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class GridTile implements Serializable, Drawable {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private int row, col;

	private Grass terrain;
	private Structure structure;
	private Blueprint blueprint;

	public GridTile(int row, int col) {
		this.row = row;
		this.col = col;

		terrain = new Grass(row, col);
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Grass getTerrainLayer() {
		return terrain;
	}

	public Structure getStructureLayer() {
		return structure;
	}

	public void setStructureLayer(Structure structure) {
		this.structure = structure;
	}

	public Blueprint getBlueprintLayer() {
		return blueprint;
	}

	public void setBlueprintLayer(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	@Override
	public void draw(Graphics2D g2d) {
		terrain.draw(g2d);

		if (structure != null) {
			structure.draw(g2d);
		}

		if (blueprint != null) {
			blueprint.draw(g2d);
		}
	}

	public double getMovementSpeedModifier() {
		if (structure != null) {
			return structure.getMovementSpeedModifier();
		} else {
			return 1;
		}
	}

	public boolean isTraverseable() {
		if (structure != null) {
			return structure.getMovementSpeedModifier() != -1;
		} else {
			return true;
		}
	}
}
