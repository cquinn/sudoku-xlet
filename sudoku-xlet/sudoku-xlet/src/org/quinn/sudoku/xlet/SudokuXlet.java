package org.quinn.sudoku.xlet;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.quinn.sudoku.ui.SudokuPanel;

/**
 * Our private xlet subclass that builds our one screen UI. It creates an
 * internal frame with a title, places our main app panel in the center, and
 * creates a menubar with and "exit" item and a panel-defined item.
 */
public class SudokuXlet extends SimpleXlet {
	protected JInternalFrame newScreen() {
		JInternalFrame screen = new JInternalFrame();

		screen.setTitle("Micro Sudoku!");
		final SudokuPanel panel = new SudokuPanel();
		screen.getContentPane().add(panel, BorderLayout.CENTER);
		
		JMenuBar menubar = new JMenuBar();
		menubar.add(new JMenu(new AbstractAction("Exit") {
			public void actionPerformed(final ActionEvent e) {
				//panel.savePuzzle();
				System.out.println("Exiting...");
				exit();
			}
		}));
		menubar.add(panel.newGameMenu());
		screen.setJMenuBar(menubar);

		return screen;
	}

}

