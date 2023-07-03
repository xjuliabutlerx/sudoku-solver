package sudoku.solver;

import sudoku.boards.PossibilitiesBoard;
import sudoku.boards.PossibilityList;
import sudoku.boards.SudokuBoard;
import sudoku.boards.blocks.Blocks;
import sudoku.exceptions.LogicException;

public class Solver {

    private final SudokuBoard board;
    private final PossibilitiesBoard possibilitiesBoard;
    private final Blocks blocks;

    public Solver() {
        this.board = new SudokuBoard();
        board.fetchBoard();

        this.possibilitiesBoard = new PossibilitiesBoard();

        blocks = Blocks.getBlocks();
    }

    // For testing
    public Solver(int[][] blankBoard) {
        this.board = new SudokuBoard();
        board.setBoard(blankBoard);

        this.possibilitiesBoard = new PossibilitiesBoard();

        blocks = Blocks.getBlocks();
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

            processOfEliminationByCell();
            analyzeRowPossibilities();
            analyzeColPossibilities();
            analyzeBlockPossibilities();

            iters--;

            System.out.println("Iteration " + (100 - iters));
            System.out.println(board.toString());
            System.out.println(possibilitiesBoard.toString());
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

    /**
     * The checkBlocks method will see if there's one missing number from a 3 x 3 block. If there is, it will
     * fill it in.
     */
    public void checkBlocks() {
        NumberChecklist checklist = new NumberChecklist();
        int[][] auxBlock;

        for (int i = 1; i < 10; i++) {
            auxBlock = board.getBlockById(i);

            // Convert the indices of the returned 3 x 3 block to correctly map to a 9 x 9 2D list
            int[] adj = blocks.calculateIndexPadding(i);

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

                            possibilitiesBoard.clearCellPossibilities((r + adj[0]), (c + adj[1]));

                            // Then remove this number from its row, col, and block's possibilities
                            possibilitiesBoard.removeRowPossibility(auxBlock[r][c], (r + adj[0]));
                            possibilitiesBoard.removeColPossibility(auxBlock[r][c], (c + adj[1]));
                            possibilitiesBoard.removeBlockPossibility(auxBlock[r][c],
                                    blocks.getBlockId((r + adj[0]), (c + adj[1])));
                        }
                    }
                }
            }

            checklist.reset();
        }
    }

    /**
     * processOfElimination will iterate through the sudoku board cell by cell and find the empty
     * ones. It will look through the cell's row, column, and block, analyze the possibilities of
     * other numbers being in a particular space.
     */
    public void processOfEliminationByCell() {
        int[][] auxBoard = board.getBoard();

        // Iterate through all the cells in the Sudoku board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                // For each blank cell...
                if (auxBoard[row][col] == 0) {

                    // Determine if numbers 1-9 are in the cell's respective row, col, or block
                    // If so, remove it from the possibilities list
                    for (int num = 1; num < 10; num++) {
                        if (board.numInRow(row, num) || board.numInCol(col, num) ||
                                board.numInBlock(blocks.getBlockId(row, col), num)) {

                            possibilitiesBoard.removeCellPossibility(num, row, col);
                        }
                    }

                    if (possibilitiesBoard.getCellPossibility(row, col).length == 1) {
                        board.writeNumber(possibilitiesBoard.getCellPossibility(row, col)[0], row, col);

                        possibilitiesBoard.clearCellPossibilities(row, col);

                        // Then remove this number from its row, col, and block's possibilities
                        possibilitiesBoard.removeRowPossibility(auxBoard[row][col], row);
                        possibilitiesBoard.removeColPossibility(auxBoard[row][col], col);
                        possibilitiesBoard.removeBlockPossibility(auxBoard[row][col],
                                blocks.getBlockId(row, col));
                    }

                } else {
                    // If the cell is filled (doesn't contain 0), then clear that cell's possibilities
                    possibilitiesBoard.clearCellPossibilities(row, col);

                    // Then remove this number from its row, col, and block's possibilities
                    possibilitiesBoard.removeRowPossibility(auxBoard[row][col], row);
                    possibilitiesBoard.removeColPossibility(auxBoard[row][col], col);
                    possibilitiesBoard.removeBlockPossibility(auxBoard[row][col],
                            blocks.getBlockId(row, col));
                }

            }
        }
    }

    // TODO MERGE ROW AND COL ANALYSIS METHODS
    /**
     * analyzeRowPossibilities will use information from the entire Sudoku board and process of elimination to
     * determine if it can write a number to the board in a row.
     *
     * The checkMissingOne method only uses information within the block.
     */
    public void analyzeRowPossibilities() {
        // Iterate through all 9 rows
        for (int rowId = 0; rowId < 9; rowId++) {
            // Get the possibility list for the current row and create a frequency checklist
            PossibilityList[] rowPoss = possibilitiesBoard.getRowPossibilities(rowId);
            FrequencyChecklist numberFreq = new FrequencyChecklist();

            // Get the possibility lists for each cell in the row
            for (int k = 0; k < rowPoss.length; k++) {
                int[] cellPoss = rowPoss[k].getPossibilities();

                // For each of the numbers possible for the cell, increment their frequencies
                for (int r = 0; r < cellPoss.length; r++) {
                    numberFreq.incrementFreq(cellPoss[r]);
                }
            }

            // If there is a number that only appears once in the entire block's possibility lists, then that
            // number must be the only one possible for the cell
            if (numberFreq.freqIsOne()) {
                // Get the number with a frequency of 1
                int num = numberFreq.getNumWithFreqOfOne();

                // Find where the number appears
                for (int col = 0; col < 9; col++) {
                    int[] cellPoss = rowPoss[col].getPossibilities();

                    for (int k = 0; k < cellPoss.length; k++) {
                        if (cellPoss[k] == num) {
                            board.writeNumber(num, rowId, col);

                            // If the cell is filled (doesn't contain 0), then clear that cell's possibilities
                            possibilitiesBoard.clearCellPossibilities(rowId, col);

                            possibilitiesBoard.removeRowPossibility(num, rowId);
                            possibilitiesBoard.removeColPossibility(num, col);
                            possibilitiesBoard.removeBlockPossibility(num,
                                    blocks.getBlockId(rowId, col));
                        }
                    }
                }
            }
        }
    }

    /**
     * analyzeColPossibilities will use information from the entire Sudoku board and process of elimination to
     * determine if it can write a number to the board in a col.
     *
     * The checkMissingOne method only uses information within the block.
     */
    public void analyzeColPossibilities() {
        // Iterate through all 9 rows
        for (int colId = 0; colId < 9; colId++) {
            // Get the possibility list for the current row and create a frequency checklist
            PossibilityList[] colPoss = possibilitiesBoard.getColPossibilities(colId);
            FrequencyChecklist numberFreq = new FrequencyChecklist();

            // Get the possibility lists for each cell in the row
            for (int k = 0; k < colPoss.length; k++) {
                int[] cellPoss = colPoss[k].getPossibilities();

                // For each of the numbers possible for the cell, increment their frequencies
                for (int r = 0; r < cellPoss.length; r++) {
                    numberFreq.incrementFreq(cellPoss[r]);
                }
            }

            // If there is a number that only appears once in the entire block's possibility lists, then that
            // number must be the only one possible for the cell
            if (numberFreq.freqIsOne()) {
                // Get the number with a frequency of 1
                int num = numberFreq.getNumWithFreqOfOne();

                // Find where the number appears
                for (int row = 0; row < 9; row++) {
                    int[] cellPoss = colPoss[row].getPossibilities();

                    for (int k = 0; k < cellPoss.length; k++) {
                        if (cellPoss[k] == num) {
                            board.writeNumber(num, row, colId);

                            // If the cell is filled (doesn't contain 0), then clear that cell's possibilities
                            possibilitiesBoard.clearCellPossibilities(row, colId);

                            possibilitiesBoard.removeRowPossibility(num, row);
                            possibilitiesBoard.removeColPossibility(num, colId);
                            possibilitiesBoard.removeBlockPossibility(num,
                                    blocks.getBlockId(row, colId));
                        }
                    }
                }
            }
        }
    }

    /**
     * analyzeBlockPossibilities will use information from the entire Sudoku board and process of elimination to
     * determine if it can write a number to the board in a 3 x 3 block.
     *
     * The checkBlocks method only uses information within the block.
     */
    public void analyzeBlockPossibilities() {
        // Iterate through all 9 blocks of the possibilities board
        for (int blockId = 1; blockId < 10; blockId++) {
            PossibilityList[][] blockPoss = possibilitiesBoard.getBlockPossibilities(blockId);
            FrequencyChecklist numberFreq = new FrequencyChecklist();

            // Count the number of solved (empty) PossibilityLists and analyze which numbers are leftover
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // If a cell is not solved, increment the number of iterations; ignore solved ones
                    if (blockPoss[i][j].getPossibilities().length != 0) {
                        int[] cellPoss = blockPoss[i][j].getPossibilities();

                        for (int k = 0; k < cellPoss.length; k++) {
                            numberFreq.incrementFreq(cellPoss[k]);
                        }

                    }
                }
            }

            // If there is a number that only appears once in the entire block's possibility lists, then that
            // number must be the only one possible for the cell
            if (numberFreq.freqIsOne()) {
                // Get the number with a frequency of 1
                int num = numberFreq.getNumWithFreqOfOne();
                int[] adj = blocks.calculateIndexPadding(blockId);

                // Find where the number appears
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        int[] cellPoss = blockPoss[r][c].getPossibilities();

                        for (int k = 0; k < cellPoss.length; k++) {
                            if (cellPoss[k] == num) {
                                board.writeNumber(num, r + adj[0], c + adj[1]);

                                // If the cell is filled (doesn't contain 0), then clear that cell's possibilities
                                possibilitiesBoard.clearCellPossibilities((r + adj[0]), (c + adj[1]));

                                possibilitiesBoard.removeRowPossibility(num, (r + adj[0]));
                                possibilitiesBoard.removeColPossibility(num, (c + adj[1]));
                                possibilitiesBoard.removeBlockPossibility(num,
                                        blocks.getBlockId((r + adj[0]), (c + adj[1])));
                            }
                        }
                    }
                }
            }

        }
    }

    public void sudokuForkRow(boolean byRow) {
        int[][] auxBoard = board.getBoard();

        // Iterate through each row...
        for (int i = 0; i < 9; i++) {
            // Create a list of 9 number possibility lists (1 for each cell in the row)
            PossibilityList[] cellPossibilities =
                    new PossibilityList[] {new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList(),
                            new PossibilityList()};

            // Determine which numbers could be in each blank cell
            for (int j = 0; j < 9; j++) {
                if (auxBoard[i][j] == 0) {
                    for (int num = 1; num < 10; num++) {
                        if (board.numInRow(i, num) || board.numInCol(j, num) ||
                                board.numInBlock(blocks.getBlockId(i, j), num)) {
                            cellPossibilities[j].remove(num);
                        }
                    }
                }
            }

            // If cellPossibilities has a number possibility list with only 3 numbers,
            // then
        }
    }

    /**
     * This method will iterate through every cell and ensure the number in the current cell isn't present in
     * the same row, col, or block.
     *
     * @return true/false (is it solved?)
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
                                !board.numInBlock(blocks.getBlockId(row, col), num)) {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }
}
