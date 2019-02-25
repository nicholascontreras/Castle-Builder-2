package util;

import java.awt.Color;

import gridAligned.Grass;

/**
 * @author Nicholas Contreras
 */

public class Util {
	public static int gridToPixel(int rowOrCol) {
		return rowOrCol * Constants.GRID_PIXEL_SIZE + Constants.GRID_PIXEL_SIZE / 2;
	}

	public static double getDist(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public static boolean isInBounds(int row, int col) {
		return row >= 0 && col >= 0 && row < Constants.NUM_ROWS && col < Constants.NUM_COLS;
	}

	public static Color[][] blur(Color[][] colors, float strength) {
		Color[][] newColors = new Color[colors.length][colors[0].length];

		for (int row = 0; row < colors.length; row++) {
			for (int col = 0; col < colors[row].length; col++) {
				Color cur = colors[row][col];
				Color avg = getAverageColorAround(colors, row, col);

				float[] newColor = new float[3];
				for (int i = 0; i < newColor.length; i++) {
					float diff = avg.getRGBColorComponents(null)[i] - cur.getRGBColorComponents(null)[i];
					newColor[i] = cur.getRGBColorComponents(null)[i] + (diff * strength);
				}
				newColors[row][col] = new Color(newColor[0], newColor[1], newColor[2]);
			}
		}
		return newColors;
	}

	private static Color getAverageColorAround(Color[][] colors, int row, int col) {
		int[][] tilesToAverage = { { 0, 0 }, { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, -1 }, { 1, 1 }, { -1, 1 },
				{ 1, -1 } };

		float[] avg = new float[3];

		int numTiles = 0;
		for (int[] tile : tilesToAverage) {
			Color curColor = colors[Math.floorMod(row + tile[0], colors.length)][Math.floorMod(col + tile[1],
					colors[0].length)];

			int i = 0;
			for (float cur : curColor.getRGBColorComponents(null)) {
				avg[i] += cur;
				i++;
			}
			numTiles++;
		}

		for (int i = 0; i < avg.length; i++) {
			avg[i] = avg[i] / numTiles;
		}

		return new Color(avg[0], avg[1], avg[2]);
	}
}
