package org.quinn.sudoku.eng;

/**
 * Puzzle definition access and state load/save support.
 */
public class Puzzles {

	/**
	 * Predefined puzzles. Each string is one puzzle, each full row separated by |'s.
	 */
	private static final String[] puzzles = {
"..6...2..|4..197..6|3..6.5..4|..15689..|.........|..94726..|8..2.9..7|6..341..9|..5...1..",
".......4.|.9..83...|2..6.79..|1.3.9..6.|..4...8..|.5....1.7|8.6..4..9|...51....|.7......2",
"...75.2..|1..9.8.4.|.9.321.6.|..2.9..7.|4.......3|.5..1.8..|.7.836.5.|.3.5.2..9|..8.79...",
"8.1.2...7|.6.8.495.|.2...3..4|.59....4.|6.......3|.7....62.|2..9...1.|.187.6.3.|4...3.5.8",
"..58.29..|.31...65.|4..5.6..7|.5.3.1.8.|.........|.6.9.7.2.|7..6.5..1|.86...47.|..92.43..",
"1...9...5|.2..3..7.|..6.7.3..|...389...|8396.7241|...241...|..7.2.5..|.4..6..2.|9...1...8",
".6...9...|.2.86.541|.7.4.....|2..3.179.|.4.....3.|.359.8..4|.....3.6.|894.25.1.|...7...5.",
"8.7.23.64|54.9...2.|...5....1|9.....43.|1.......9|.26.....5|3....4...|.5...2.43|26.31.7.8",
".9.......|5.3.1....|2.15.7.4.|9..3.65.8|14.8..6.3|.3825.7..|..7.6823.|.....4.61|........9",
".31......|5...79...|86.1...45|67483.1..|...79426.|.9....487|4.5.1....|...2.6.7.|......3.8"
	};

	public static int puzzleCount() {
		return puzzles.length;
	}

	public static Puzzle newPuzzle(int index) {
		return new Puzzle(puzzles[index % puzzles.length]);
	}
}
