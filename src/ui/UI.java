package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.GamePanel;

/**
 * @author Nicholas Contreras
 */

@SuppressWarnings("serial")
public class UI extends JPanel {

	public UI() {
		setLayout(new BorderLayout(5, 5));

		add(new SaveLoadUI(), BorderLayout.SOUTH);
	}

	public static void showErrorMessage(String headerText, Exception e, String footerText, Component c) {

		String st = e.toString();

		if (e != null) {
			for (StackTraceElement ste : e.getStackTrace()) {
				st += System.lineSeparator() + "\tat ";
				st += ste;
			}
		}

		st = headerText + System.lineSeparator() + st + System.lineSeparator() + System.lineSeparator() + footerText;

		JTextArea ta = new JTextArea(st);
		ta.setEditable(false);
		ta.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(ta);
		JDialog dialog = new JDialog(getFrame(c));
		dialog.setTitle("Error");
		dialog.add(scrollPane);
		dialog.pack();
		dialog.setVisible(true);
	}

	protected static JFrame getFrame(Component c) {
		while (!(c instanceof JFrame)) {
			c = c.getParent();
		}
		return (JFrame) c;
	}
}
