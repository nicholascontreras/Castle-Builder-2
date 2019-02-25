package ui;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Game;
import main.GameIO;
import util.Constants;
import util.ImageManager;

/**
 * @author Nicholas Contreras
 */

@SuppressWarnings("serial")
public class SaveLoadUI extends JPanel {

	private JButton saveButton, loadButton;

	public SaveLoadUI() {
		this.setLayout(new GridLayout(1, 2, 5, 5));

		saveButton = new JButton("Save Game", new ImageIcon(ImageManager.getImage("saveicon")));
		loadButton = new JButton("Load Game", new ImageIcon(ImageManager.getImage("loadicon")));

		saveButton.addActionListener((ActionEvent) -> saveButtonPress());
		loadButton.addActionListener((ActionEvent) -> loadButtonPress());

		saveButton.setFocusTraversalKeysEnabled(false);
		loadButton.setFocusTraversalKeysEnabled(false);

		saveButton.setFocusPainted(false);
		loadButton.setFocusPainted(false);

		this.add(saveButton);
		this.add(loadButton);
	}

	private void saveButtonPress() {

		Game.getGame().setPaused(true);

		JFileChooser fileChooser = setupFileChooser();
		int result = fileChooser.showDialog(UI.getFrame(this), "Save Game");

		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			if (!file.getName().endsWith(Constants.FILE_FORMAT_EXTENSION)) {
				file = new File(file.getParentFile(), file.getName() + Constants.FILE_FORMAT_EXTENSION);
			}

			try {
				GameIO.save(file);
			} catch (IOException e) {
				UI.showErrorMessage("Unable to save the game:", e, "", this);
			}
		}
		Game.getGame().setPaused(false);
	}

	private void loadButtonPress() {
		Game.getGame().setPaused(true);

		JFileChooser fileChooser = setupFileChooser();
		int result = fileChooser.showDialog(UI.getFrame(this), "Load Game");

		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();

			if (!file.isFile() || !file.canRead()) {
				UI.showErrorMessage("Unable to load selected file", null, "The file does not exist or is not readable",
						this);
			} else {
				try {
					GameIO.load(file);
				} catch (ClassNotFoundException | IOException e) {
					UI.showErrorMessage("Unable to load selected file:", e, "", this);
				}
			}
		}
		Game.getGame().setPaused(false);
	}

	private JFileChooser setupFileChooser() {
		createDefaultFolderIfMissing();

		JFileChooser fileChooser = new JFileChooser(Constants.DEFAULT_SAVE_LOCATION);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(
				new FileNameExtensionFilter("Castle Builder Saves (" + Constants.FILE_FORMAT_EXTENSION + ")",
						Constants.FILE_FORMAT_EXTENSION.substring(1)));
		return fileChooser;
	}

	private void createDefaultFolderIfMissing() {
		if (!Constants.DEFAULT_SAVE_LOCATION.isDirectory()) {
			Constants.DEFAULT_SAVE_LOCATION.mkdir();
		}
	}
}
