package util;

import java.awt.Color;
import java.io.File;

import javax.swing.filechooser.FileSystemView;

/**
 * @author Nicholas Contreras
 */

public class Constants {

	public static final int SERIAL_VERSION_UID = 1;

	public static final int GRID_PIXEL_SIZE = 100;

	public static final Color TEAM_COLOR_FLAG = new Color(221, 160, 221);

	public static final int NUM_ROWS = 20;

	public static final int NUM_COLS = 20;

	public static final String FILE_FORMAT_EXTENSION = ".castle";

	public static final File DEFAULT_SAVE_LOCATION = new File(FileSystemView.getFileSystemView().getDefaultDirectory(),
			"Castle Builder Saves");
}
