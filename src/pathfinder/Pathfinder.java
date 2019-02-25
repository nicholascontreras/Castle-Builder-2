package pathfinder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import main.Game;
import main.GridTile;
import util.Constants;

/**
 * @author Nicholas Contreras
 */

public class Pathfinder {

	private static ArrayList<PathfindingRequest> requests = new ArrayList<PathfindingRequest>();

	private static HashMap<Object, Path> pathsFound = new HashMap<Object, Path>();

	public static void startPathfinder() {
		Thread t = new Thread(() -> run(), "Pathfinding-Thread");
		t.start();
	}

	public static void requestPath(Object requestor, int startRow, int startCol, int endRow, int endCol,
			boolean beside) {
		requests.add(new PathfindingRequest(requestor, startRow, startCol, endRow, endCol, beside));
	}

	public static boolean isRequestWaitingFor(Object o) {
		for (int i = 0; i < requests.size(); i++) {
			if (requests.get(i).requestor.equals(o)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPathReadyFor(Object o) {
		return pathsFound.containsKey(o);
	}

	public static Path getPathFor(Object o) {
		return pathsFound.remove(o);
	}

	private static void run() {
		while (true) {
			if (requests.isEmpty()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				PathfindingRequest curRequest = requests.get(0);
				Path p = getPath(curRequest);
				pathsFound.put(curRequest.requestor, p);
				requests.remove(0);

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Path getPath(PathfindingRequest request) {
		GridTile[][] gameGrid = Game.getGame().getGameGrid();

		double[][] pathfindingMesh = new double[gameGrid.length][gameGrid[0].length];

		pathfindingMesh[request.endRow][request.endCol] = 1;

		while (true) {

			boolean meshChanged = false;

			double[][] newMesh = new double[pathfindingMesh.length][0];
			for (int row = 0; row < pathfindingMesh.length; row++) {
				newMesh[row] = pathfindingMesh[row].clone();
			}

			for (int row = 0; row < pathfindingMesh.length; row++) {
				for (int col = 0; col < pathfindingMesh[row].length; col++) {
					if (pathfindingMesh[row][col] > 0) {
						boolean madeChange = propagatePathMesh(pathfindingMesh, row, col, newMesh);

						if (madeChange) {
							meshChanged = true;
						}
					}
				}
			}

			if (!meshChanged) {
				return null;
			}

			pathfindingMesh = newMesh;

			if (pathfindingMesh[request.startRow][request.startCol] > 0) {
				return createPathFromMesh(request, pathfindingMesh);
			}
		}
	}

	private static boolean propagatePathMesh(double[][] pathfindingMesh, int row, int col, double[][] newMesh) {
		int[][] tilesToUpdate = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		double curIndex = pathfindingMesh[row][col];

		boolean madeChange = false;

		for (int[] curTile : tilesToUpdate) {
			int newRow = row + curTile[0];
			int newCol = col + curTile[1];
			if (Game.getGame().isOnGrid(newRow, newCol)) {
				GridTile cur = Game.getGame().getGridTileAt(newRow, newCol);
				double oldValue = pathfindingMesh[newRow][newCol];
				double newValue = curIndex + cur.getMovementSpeedModifier();
				if (cur.isTraverseable()) {
					if (oldValue == 0 || newValue < oldValue) {
						newMesh[newRow][newCol] = newValue;
						madeChange = true;
					}
				}
			}
		}
		return madeChange;
	}

	private static Path createPathFromMesh(PathfindingRequest request, double[][] pathfindingMesh) {
		int curRow = request.startRow;
		int curCol = request.startCol;

		Path path = new Path();
		path.addStep(curRow, curCol);

		while (true) {
			int[] nextStep = getNextPathStep(pathfindingMesh, curRow, curCol);
			curRow = nextStep[0];
			curCol = nextStep[1];

			if (curRow == request.endRow && curCol == request.endCol) {
				if (!request.beside) {
					path.addStep(curRow, curCol);
				}
				return path;
			} else {
				path.addStep(curRow, curCol);
			}
		}
	}

	private static void printMesh(double[][] mesh) {
		for (double[] row : mesh) {
			String s = "";
			for (double cur : row) {
				String x = ((int) cur) + "";

				if (x.length() == 1) {
					x = "0" + x;
				}

				s += x + " ";
			}
			System.out.println(s);
		}
	}

	private static int[] getNextPathStep(double[][] pathfindingMesh, int row, int col) {
		int[][] tilesToCheck = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

		int[] bestTile = null;
		double bestTileCost = Double.MAX_VALUE;

		for (int[] curTile : tilesToCheck) {
			int newRow = row + curTile[0];
			int newCol = col + curTile[1];
			if (Game.getGame().isOnGrid(newRow, newCol)) {
				double curValue = pathfindingMesh[newRow][newCol];
				if (curValue > 0) {
					if (bestTile == null || curValue < bestTileCost) {
						bestTile = new int[] { newRow, newCol };
						bestTileCost = curValue;
					}
				}
			}
		}
		return bestTile;
	}

	private static class PathfindingRequest implements Serializable {
		private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

		private Object requestor;
		private int startRow, startCol;
		private int endRow, endCol;
		private boolean beside;

		private PathfindingRequest(Object requestor, int startRow, int startCol, int endRow, int endCol,
				boolean beside) {
			this.requestor = requestor;
			this.startRow = startRow;
			this.startCol = startCol;
			this.endRow = endRow;
			this.endCol = endCol;
			this.beside = beside;
		}
	}

	public static class Path implements Serializable {
		private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;

		private ArrayList<int[]> path;

		private Path() {
			path = new ArrayList<int[]>();
		}

		private void addStep(int row, int col) {
			path.add(new int[] { row, col });
		}

		public void nextStep() {
			if (!isEmpty()) {
				path.remove(0);
			}
		}

		public int curStepRow() {
			return path.get(0)[0];
		}

		public int curStepCol() {
			return path.get(0)[1];
		}

		public boolean isEmpty() {
			return path.isEmpty();
		}
	}
}
