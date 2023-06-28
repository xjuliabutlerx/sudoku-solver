package sudoku.solver;

import sudoku.board.SudokuBoard;
import sudoku.exceptions.LogicException;

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
        int iters = 100;

        while (!isSolved() && iters > 0) {
            checkMissingOne(true);
            checkMissingOne(false);
            checkBlocks();
            processOfElimination();
            iters--;

            System.out.println("Iteration " + (100 - iters));
            System.out.println(board.toString());
        }

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
        NumberChecklist checklist = new NumberChecklist();
        int[][] auxBlock;

        for (int i = 1; i < 10; i++) {
            auxBlock = board.getBlockById(i);

            // Convert the indices of the returned 3 x 3 block to correctly map to a 9 x 9 2D list
            int[] adj = calculateIndexPadding(i);

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    try {
                        checklist.marksExists(auxBlock[r][c]);
                    } catch (LogicException e) {
                        e.printStackTrace();
                    }
                }
            }

            // If there's only one missing, then write the correct number in
            if (checklist.missingOne()) {
                int num = checklist.getMissingNum();

                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (auxBlock[r][c] == 0) {
                            board.writeNumber(num, (r + adj[0]), (c + adj[1]));
                            //System.out.println(board.toString());
                        }
                    }
                }
            }

            checklist.reset();
        }
    }

    /**
     * calculateIndexPadding calculates how much padding should be added to the indices of the
     * block so those values can be mapped to the correct cell in the 9 x 9 2D list
     *
     * (The blocks are 3 x 3 in dimension, so their indices are 0-2 for rows and cols, but the
     * sudoku board have indices 0-8 for rows and cols. Blocks 2-9 need to have some padding
     * added to them so the missing cells can be correctly identified in the larger board).
     *
     * @param blockId
     * @return
     */
    private int[] calculateIndexPadding(int blockId) {
        // adjustments[0] = row padding
        // adjustments[2] = col padding
        int[] adjustments = new int[2];

        //System.out.println("Initial Adjustments List (all 0's): " + Arrays.toString(adjustments));

        switch (blockId) {
            case 1:
                break;
            case 2:
                adjustments[1] = 3;
                break;
            case 3:
                adjustments[1] = 6;
                break;
            case 4:
                adjustments[0] = 3;
                break;
            case 5:
                adjustments[0] = 3;
                adjustments[1] = 3;
                break;
            case 6:
                adjustments[0] = 3;
                adjustments[1] = 6;
                break;
            case 7:
                adjustments[0] = 6;
                break;
            case 8:
                adjustments[0] = 6;
                adjustments[1] = 3;
                break;
            case 9:
                adjustments[0] = 6;
                adjustments[1] = 6;
        }

        return adjustments;
    }

    /**
     * processOfElimination will iterate through the sudoku board cell by cell and find the empty
     * ones. It will look through the cell's row, column, and block, analyze the possibilities of
     * other numbers being in a particular space.
     */
    public void processOfElimination() {
        int[][] auxBoard = board.getBoard();

        // Iterate through all the cells in the Sudoku board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                // For each blank cell...
                if (auxBoard[row][col] == 0) {
                    NumberPossibilityList possibilityList = new NumberPossibilityList();

                    // Determine if numbers 1-9 are in the cell's respective row, col, or block
                    // If so, remove it from the possibilities list
                    for (int num = 1; num < 10; num++) {
                        if (board.numInRow(row, num) || board.numInCol(col, num) ||
                                board.numInBlock(board.getBlockId(row, col), num)) {
                            possibilityList.remove(num);
                        }
                    }

                    if (possibilityList.onlyOneNumPossible()) {
                        board.writeNumber(possibilityList.getPossibilities()[0], row, col);
                        //System.out.println(board.toString());
                    }

                }
            }
        }
    }

    public void sudokuFork() {
        int[][] auxBoard = board.getBoard();

    }

    /**
     * This method will iterate through every cell and ensure the number in the current cell isn't present in
     * the same row, col, or block.
     *
     * @return
     */
    public boolean isSolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                int currentNum = board.getCell(row, col);

                if (currentNum == 0) {
                    return false;
                }

                for (int num = 1; num < 10; num++) {
                    if (num != currentNum) {
                        if (!board.numInRow(row, num) || !board.numInCol(col, num) ||
                                !board.numInBlock(board.getBlockId(row, col), num)) {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }
}
