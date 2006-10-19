package org.quinn.sudoku.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

//import org.quinn.sudoku.eng.Generator;
import org.quinn.sudoku.eng.Puzzle;
import org.quinn.sudoku.eng.Puzzles;

/**
 * Simple Sudoku game UI panel implementation. Handles keyboard navigation and
 * game state rendering. Can be used within any Swing frame on Java SE or Java
 * ME CDC (AGUI) platforms.
 */
public class SudokuPanel extends JPanel {

	// Margins
	public static final int MW = 5;
	public static final int MH = 5;

	// Prefered cell size
	public static final int PCW = 32;
	public static final int PCH = 32;

	// Current puzzle state and selection
	Puzzle puzzle;
	int puzIndex = 0;
	int selr = 0;
	int selc = 0;

	public SudokuPanel() {
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:  if (selc > 0) selc--; break;
				case KeyEvent.VK_RIGHT: if (selc < 8) selc++; break;
				case KeyEvent.VK_UP:    if (selr > 0) selr--; break;
				case KeyEvent.VK_DOWN:  if (selr < 8) selr++; break;
				case KeyEvent.VK_DELETE:
				case KeyEvent.VK_BACK_SPACE:
				case KeyEvent.VK_0:
					puzzle.answer[selr][selc] = 0;
				case KeyEvent.VK_1:
				case KeyEvent.VK_2:
				case KeyEvent.VK_3:
				case KeyEvent.VK_4:
				case KeyEvent.VK_5:
				case KeyEvent.VK_6:
				case KeyEvent.VK_7:
				case KeyEvent.VK_8:
				case KeyEvent.VK_9:
					if (!puzzle.given[selr][selc]) {
						puzzle.answer[selr][selc] = e.getKeyCode() - KeyEvent.VK_1 + 1;
					}
				}
				repaint();
			}
		});
		puzzle = Puzzles.newPuzzle(puzIndex);
	}

	void newPuzzle() {
		puzzle = Puzzles.newPuzzle(++puzIndex);
		invalidate();
		repaint();
	}

	public void loadPuzzle() {
	}

	public void savePuzzle() {
	}

	public Dimension getPreferredSize() {
        return new Dimension(9*PCW+2*MW+3, 9*PCW+2*MH+3);
	}

	/**
	 * reports which digits have been used in the same row, column or block
	 * in the form of a bitvector.
	 */
	private static boolean isMoveValid(int[][] s, int r, int c) {
		int v = s[r][c]; 
		int bbr = r - r%3;
		int bbc = c - c%3;
		for (int i = 0; i < 9; i++) {
			int br = bbr+i%3;
			int bc = bbc+i/3;
			if (i != c && s[r][i] == v ||
				i != r && s[i][c] == v ||
				br != r && bc != c && s[br][bc] == v)
				return false;
		}
		return true;
	}

	public void paintComponent(final Graphics g) {
		final Graphics2D graphics = (Graphics2D) g;
		final int panelw = getWidth();
		final int panelh = getHeight();

		// board dimensions
		final int boardw = (panelw - 3) / 9 * 9;
		final int boardh = (panelh - 3) / 9 * 9;
		final int origx = (panelw - boardw - 3) / 2;
		final int origy = (panelh - boardh - 3) / 2;

		// cell dimensions
		final int cellw = boardw / 9;
		final int cellh = boardh / 9;
		//System.out.println("Cell W="+cellw+" H="+cellh);

		// background rectangle
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, panelw, panelh);

		// selection rectangle
		int sx = origx + selc*cellw;
		int sy = origy + selr*cellh;
		graphics.setColor(Color.YELLOW);
		graphics.fillRect(sx,sy,cellw,cellh);

		// grid lines
		graphics.setColor(Color.BLACK);
		for (int v = 0; v < 10; v++) {
			graphics.drawLine(origx+v*cellw,origy,origx+v*cellw,origy+boardh);
			if (v%3 == 0) {
				graphics.drawLine(origx+v*cellw+1,origy,origx+v*cellw+1,origy+boardh+1);
				graphics.drawLine(origx+v*cellw+2,origy,origx+v*cellw+2,origy+boardh+1);
			}
		}
		for (int h = 0; h < 10; h++) {
			graphics.drawLine(origx,origy+h*cellh,origx+boardw,origy+h*cellh);
			if (h%3 == 0) {
				graphics.drawLine(origx,origy+h*cellh+1,origx+boardw+1,origy+h*cellh+1);
				graphics.drawLine(origx,origy+h*cellh+2,origx+boardw+1,origy+h*cellh+2);
			}
		}

		// cell labels
		graphics.setFont(new Font("Arial", Font.BOLD, (Math.min(cellh,cellw)*8)/10));
		FontMetrics fm = graphics.getFontMetrics();
		int lh = fm.getHeight() - fm.getLeading() - fm.getDescent();
		//System.out.println("Font h="+fm.getHeight()+" l="+fm.getLeading()+" a="+fm.getAscent()+" d="+fm.getDescent());

		// paint the cells
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				int val = puzzle.answer[r][c];
				if (val > 0) {
					String label = Integer.toString(val);
					int lw = fm.stringWidth(label);
					int lx = origx + c*cellw + cellw/2 - lw/2;
					int ly = origy + r*cellh + cellh/2 + lh/2;
					Color tcolor = puzzle.given[r][c] 
					    ? Color.BLACK
						: (r == selr && c == selc && !isMoveValid(puzzle.answer, r, c)) 
							? Color.RED : Color.BLUE;
					graphics.setColor(tcolor);
					graphics.drawString(label, lx, ly);
				}
			}
		}
	}

	public JMenu newGameMenu() {
		JMenu gameMenu = new JMenu("Game");
		gameMenu.add(new JMenuItem(new AbstractAction("New") {
			public void actionPerformed(final ActionEvent e) {
				newPuzzle();
			}
		}));
		gameMenu.add(new JMenuItem(new AbstractAction("Other") {
			public void actionPerformed(final ActionEvent e) {
				System.out.println("Game menu...");
			}
		}));
		return gameMenu;
	}
}
