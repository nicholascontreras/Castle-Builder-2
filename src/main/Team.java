package main;

import java.awt.Color;
import java.io.Serializable;

import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class Team implements Serializable {
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

	private int teamNumber;
	private Color teamColor;

	public Team(int teamNumber, Color teamColor) {
		this.teamNumber = teamNumber;
		this.teamColor = teamColor;
	}

	public Color getColor() {
		return teamColor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Team) {
			return this.teamNumber == ((Team) obj).teamNumber;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return teamNumber;
	}
}
