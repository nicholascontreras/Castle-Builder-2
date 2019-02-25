package main;

import javax.swing.SwingUtilities;

import gridAligned.structures.Nexus;
import gridAligned.structures.Wall;
import jobs.ConstructionJob;
import unaligned.units.workers.Builder;

/**
 * @author Nicholas Contreras
 */

public class Main {

	public static void main(String[] args) {
		Game.setGame(new Game());
		SwingUtilities.invokeLater(new GamePanel());

		Game.getGame().addJobToQueue(new ConstructionJob(new Wall(1, 1, Game.getGame().getPlayerTeam())));
		Game.getGame().addGameObject(new Builder(350, 450, Game.getGame().getPlayerTeam()));
		Game.getGame().addGameObject(new Builder(350, 450, Game.getGame().getPlayerTeam()));
	}
}
