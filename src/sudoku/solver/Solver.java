package sudoku.solver;

import sudoku.board.SudokuBoard;
import sudoku.exceptions.LogicException;

import java.util.ArrayList;

public class Solver {

    SudokuBoard board;

    public Solver() {
        this.board = new SudokuBoard();

        System.out.println();

        board.printBoard();
        board.getBoardByCols();
    }

    public void start() {
        checkMissingOneRow();
    }

    private void checkMissingOneRow() {
        NumberChecklist checklist = new NumberChecklist();
        ArrayList<ArrayList<Integer>> auxBoard = board.getBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    checklist.marksExists(auxBoard.get(i).get(j));
                } catch (LogicException e) {
                    e.printStackTrace();
                }
            }

            if (checklist.missingOne()) {
                int num = checklist.getMissingNum();

                for (int j = 0; j < 9; j ++) {
                    if (auxBoard.get(i).get(j) == 0) {
                        board.writeNumber(num, i, j);
                        board.printBoard();
                    }
                }
            }
        }
    }

    private void checkRows() {

    }

    private void checkCols() {

    }

    // 1st horizontal third: Rows 0-2
    // 2nd hor. third: Rows 3-5
    // 3rd hor. third: Rows 6-8
    // 1st vertical third: Cols 0-2
    // 2nd vert. third: Cols 3-5
    // 3rd vert. third: Cols 6-8
    private void checkThirds() {

    }
}
