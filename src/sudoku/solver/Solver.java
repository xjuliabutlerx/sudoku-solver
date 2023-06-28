package sudoku.solver;

import sudoku.board.SudokuBoard;
import sudoku.exceptions.LogicException;

import java.util.ArrayList;

public class Solver {

    private final SudokuBoard board;

    public Solver() {
        this.board = new SudokuBoard();
        board.fetchBoard();

        /*
        System.out.println();

        System.out.println(board.toString());
        board.getBoardByCols();
         */
    }

    // For testing
    public Solver(int[][] blankBoard) {
        this.board = new SudokuBoard();
        board.setBoard(blankBoard);
    }

    public int[][] getBoard() {
        return this.board.getBoard();
    }

    public void start() {
        checkMissingOne(true);
        checkMissingOne(false);
    }

    /**
     * This method checks each row or col to see if there's only one number missing.
     * If there is, then it will go ahead and fill in the correct number.
     *
     * @param byRow (a boolean to indicate if the method should check by row [true] or
     *              by col [false])
     */
    public void checkMissingOne(boolean byRow) {
        NumberChecklist checklist = new NumberChecklist();
        int[][] auxBoard;

        if (byRow) {
            auxBoard = board.getBoard();
        } else {
            auxBoard = board.getBoardByCols();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    checklist.marksExists(auxBoard[i][j]);
                } catch (LogicException e) {
                    e.printStackTrace();
                }
            }

            // If there's only one missing, then write the correct number in
            if (checklist.missingOne()) {
                int num = checklist.getMissingNum();

                for (int j = 0; j < 9; j ++) {
                    if (auxBoard[i][j] == 0) {
                        if (byRow) {
                            board.writeNumber(num, i, j);
                        } else {
                            board.writeNumber(num, j, i);
                        }
                        //System.out.println(board.toString());
                    }
                }
            }

            checklist.reset();
        }
    }

    // 1st horizontal block: Rows 0-2
    // 2nd hor. block: Rows 3-5
    // 3rd hor. block: Rows 6-8
    // 1st vertical block: Cols 0-2
    // 2nd vert. block: Cols 3-5
    // 3rd vert. block: Cols 6-8
    public void checkBlocks() {

    }

    // @TODO Re-write this so that it takes into consideration the logic (rows, columns, blocks)
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getCell(i, j) != board.getSolution()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
