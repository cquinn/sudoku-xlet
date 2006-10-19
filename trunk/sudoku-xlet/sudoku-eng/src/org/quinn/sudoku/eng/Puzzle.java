package org.quinn.sudoku.eng;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;

public class Puzzle {

	public final int[][] grid = new int[9][9];
	public final boolean[][] given = new boolean[9][9];
	public final int[][] answer = new int[9][9];

	public Puzzle(String text) {
		Reader reader = new CharArrayReader(text.toCharArray()); 
		for (int r = 0; r < 9; r++)
			for (int c = 0; c < 9; c++) {
				grid[r][c] = nextDigit(reader);
				given[r][c] = grid[r][c] > 0;
				if (given[r][c])
					answer[r][c] = grid[r][c];
			}
	}
	
	private static int nextDigit(Reader reader) {
		char c;
		try {
			do {
				c = (char) reader.read();
			} while (c == '|' || c == '\n');
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid puzzle text length.");
			//return -1;
		}
		if (Character.isDigit(c))
			return Character.digit(c, 10);
		if (c == '.' || c == ' ')
			return 0;
		throw new IllegalArgumentException("Invalid puzzle text char: " + c);		
	}
}
