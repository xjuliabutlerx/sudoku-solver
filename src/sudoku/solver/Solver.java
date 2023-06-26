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
    public Solver(ArrayList<ArrayList<Integer>> blankBoard) {
        this.board = new SudokuBoard();
        board.setBoard(blankBoard);
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return this.board.getBoard();
    }

    public void start() {
        checkMissingOne(true);
        checkMissingOne(false);
    }

    public void checkMissingOne(boolean byRow) {
        NumberChecklist checklist = new NumberChecklist();
        ArrayList<ArrayList<Integer>> auxBoard;

        if (byRow) {
            auxBoard = board.getBoard();
        } else {
            auxBoard = board.getBoardByCols();
        }

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

    // 1st horizontal third: Rows 0-2
    // 2nd hor. third: Rows 3-5
    // 3rd hor. third: Rows 6-8
    // 1st vertical third: Cols 0-2
    // 2nd vert. third: Cols 3-5
    // 3rd vert. third: Cols 6-8
    public void checkThirds() {

    }

    // @TODO Re-write this so that it takes into consideration the logic (rows, columns, blocks)
    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getCell(i, j) != board.getSolution().get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
