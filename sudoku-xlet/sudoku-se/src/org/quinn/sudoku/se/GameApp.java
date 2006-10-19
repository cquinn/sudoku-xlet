package org.quinn.sudoku.se;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.quinn.sudoku.ui.SudokuPanel;


public class GameApp {
	
	JMenu newFileMenu(final JFrame frame) {
		JMenu menu = new JMenu("File");
		menu.add(new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Exiting...");
				frame.setVisible(false);
				frame.dispose();
			}
		}));
		return menu;
	}

	private void createAndShowGUI() {
		JFrame frame = new JFrame("Micro Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SudokuPanel panel = new SudokuPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		JMenuBar menubar = new JMenuBar();
		menubar.add(newFileMenu(frame));
		menubar.add(panel.newGameMenu());
		frame.setJMenuBar(menubar);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameApp().createAndShowGUI();
			}
		});
	}
}

