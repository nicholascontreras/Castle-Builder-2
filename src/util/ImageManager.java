package util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import main.Team;

/**
 * @author Nicholas Contreras
 */

public class ImageManager {

	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	private static HashMap<Team, HashMap<String, BufferedImage>> teamImages = new HashMap<Team, HashMap<String, BufferedImage>>();

	public static BufferedImage getImage(String name) {
		if (images.containsKey(name)) {
			return images.get(name);
		} else {
			try {
				BufferedImage img = ImageIO.read(ImageManager.class.getResourceAsStream("/" + name + ".png"));
				return img;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BufferedImage getImageForTeam(String name, Team team) {

		if (!teamImages.containsKey(team)) {
			teamImages.put(team, new HashMap<String, BufferedImage>());
		}

		if (!teamImages.get(team).containsKey(name)) {
			BufferedImage img = getImage(name);
			img = colorImageForTeam(img, team);
			teamImages.get(team).put(name, img);
		}

		return teamImages.get(team).get(name);
	}

	private static BufferedImage colorImageForTeam(BufferedImage img, Team team) {
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		int[] rgbArray = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());

		int curX = 0, curY = 0;

		for (int curRGB : rgbArray) {
			if (new Color(curRGB).equals(Constants.TEAM_COLOR_FLAG)) {
				newImg.setRGB(curX, curY, team.getColor().getRGB());
			} else {
				newImg.setRGB(curX, curY, curRGB);
			}
			curX++;
			if (curX == newImg.getWidth()) {
				curX = 0;
				curY++;
			}
		}
		return newImg;
	}
}
