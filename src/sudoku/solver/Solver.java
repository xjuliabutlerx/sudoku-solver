package sudoku.solver;

import sudoku.boards.PossibilitiesBoard;
import sudoku.boards.PossibilityList;
import sudoku.boards.SudokuBoard;
import sudoku.boards.blocks.Blocks;
import sudoku.exceptions.LogicException;

import java.util.*;
import java.util.stream.Collectors;

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

    // For testing
    public int[][] getBoard() {
        return this.board.getBoard();
    }

    // For testing
    public PossibilitiesBoard getPossibilitiesBoard() { return this.possibilitiesBoard; }

    public void start() {
        int iters = 100;

        System.out.println("Initial Sudoku Board:\n" + board.toString());
        updatePossBoard();

        while (!isSolved() && iters > 0) {
            checkMissingOne(true);
            checkMissingOne(false);
            checkBlocks();

            processOfEliminationByCell();
            analyzeRowColPossibilities(true);
            analyzeRowColPossibilities(false);
            analyzeBlockPossibilities();

            sudokuForkRowCol(true);
            sudokuForkRowCol(false);
            sudokuForkBlock();

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
                    System.out.println("The error occured at row " + i + ", col " + j);
                    e.printStackTrace();
                }
            }

            // If there's only one missing, then write the correct number in
            if (checklist.missingOne()) {
                int num = checklist.getMissingNum();

                for (int j = 0; j < 9; j ++) {
                    if (auxBoard[i][j] == 0) {
                        if (byRow) {
                            removeNumberFromPossibilities(num, i, j);
                            updatePossBoard();
                        } else {
                            removeNumberFromPossibilities(num, j, i);
                            updatePossBoard();
                        }
                    }
                }
            }

            // Set every cell of the checklist to false to reset for the next row or col
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
                        System.out.println("The error occured at row " + r + ", col " + c);
                        e.printStackTrace();
                    }
                }
            }

            // If there's only one missing...
            if (checklist.missingOne()) {
                int num = checklist.getMissingNum();

                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (auxBlock[r][c] == 0) {
                            // Then write the correct number in and remove it from the possibilities board
                            try {
                                removeNumberFromPossibilities(num, (r + adj[0]), (c + adj[1]));
                                updatePossBoard();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            // Set every cell of the checklist to false to reset for the next block
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
                        try {
                            removeNumberFromPossibilities(possibilitiesBoard.getCellPossibility(row, col)[0],
                                    row, col);
                            updatePossBoard();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    // If the cell is filled (doesn't contain 0), then clear that cell's possibilities
                    //removeNumberFromPossibilities(auxBoard[row][col], row, col, false);
                    possibilitiesBoard.clearCellPossibilities(row, col);
                }

            }
        }
    }

    /**
     * analyzeRowColPossibilities will use information from the entire Sudoku board and process of elimination to
     * determine if it can write a number to the board in a row or col.
     *
     * The checkMissingOne method is simpler and only uses information within the row or col.
     *
     * @param byRow (whether to analyze the board's rows or cols)
     */
    public void analyzeRowColPossibilities(boolean byRow) {
        // Iterate through all 9 rows or cols
        for (int id = 0; id < 9; id++) {
            // Depending on row or col, get the possibilities for those 9 cells
            PossibilityList[] linePoss;
            if (byRow) {
                linePoss = possibilitiesBoard.getRowPossibilities(id);
            } else {
                linePoss = possibilitiesBoard.getColPossibilities(id);
            }

            FrequencyChecklist numberFreq = new FrequencyChecklist();

            // Get the possibility lists for each cell in the row or col
            for (int k = 0; k < linePoss.length; k++) {
                int[] cellPoss = linePoss[k].getPossibilities();

                // For each of the numbers possible for the cell, increment their frequencies
                for (int l = 0; l < cellPoss.length; l++) {
                    numberFreq.incrementFreq(cellPoss[l]);
                }
            }

            // If there is a number that only appears once in the entire row's/col's possibility lists, then that
            // number must be the only one possible for the cell
            if (numberFreq.freqIsOne()) {
                // Get the number with a frequency of 1
                int num = numberFreq.getNumWithFreqOfOne();

                // Find where the number appears
                for (int i = 0; i < 9; i++) {
                    int[] cellPoss = linePoss[i].getPossibilities();

                    for (int j = 0; j < cellPoss.length; j++) {
                        if (cellPoss[j] == num ) {
                            if (byRow) {
                                try {
                                    removeNumberFromPossibilities(num, id, i);
                                    updatePossBoard();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    removeNumberFromPossibilities(num, i, id);
                                    updatePossBoard();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
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
                                try {
                                    removeNumberFromPossibilities(num,
                                            r + adj[0], c + adj[1]);
                                    updatePossBoard();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    /**
     * sudokuForkRowCol analyzes the board's rows and cols to see if any other numbers could be eliminated from a
     * row or col. It looks to see if two cells in a row or col share the same 2 numbers. If so, then the leftover
     * numbers are not possible and should be removed from those two cells' possibility lists.
     *
     * For another explanation: https://www.conceptispuzzles.com/index.aspx?uri=puzzle/sudoku/techniques
     *
     * @param byRow (whether to analyze the board's rows or cols)
     */
    public void sudokuForkRowCol(boolean byRow) {
        // Iterate over the rows or cols
        for (int id = 0; id < 9; id++) {
            // Depending on row or col, get the possibilities for those 9 cells
            PossibilityList[] linePoss;
            if (byRow) {
                linePoss = possibilitiesBoard.getRowPossibilities(id);
            } else {
                linePoss = possibilitiesBoard.getColPossibilities(id);
            }

            FreqAndLocChecklist checklist = new FreqAndLocChecklist();

            // Iterate through linePoss (each cell's possibility list of the row or col)
            for (int cell = 0; cell < 9; cell++) {
                int[] currentCellPoss = linePoss[cell].getPossibilities();

                // Add the numbers from all 9 possibility lists to the checklist
                for (int i = 0; i < currentCellPoss.length; i++) {
                    checklist.addNumber(currentCellPoss[i], cell);
                }
            }

            if (checklist.getFork() != null) {
                int[] forkInfo = checklist.getFork();

                for (int num = 1; num < 10; num++) {
                    if (num != forkInfo[0] && num != forkInfo[1]) {
                        if (byRow) {
                            possibilitiesBoard.removeCellPossibility(num, id, forkInfo[2]);
                            possibilitiesBoard.removeCellPossibility(num, id, forkInfo[3]);
                        } else {
                            possibilitiesBoard.removeCellPossibility(num, forkInfo[2], id);
                            possibilitiesBoard.removeCellPossibility(num, forkInfo[3], id);
                        }
                    }
                }
            }
        }
    }

    public void sudokuForkBlock() {
        // Iterate through all 9 blocks
        for (int blockId = 1; blockId < 10; blockId++) {
            // Get the possibility lists for the block as a 1D array
            PossibilityList[] blockPoss = possibilitiesBoard.getBlockPossibilitiesAsArray(blockId);
            FreqAndLocChecklist checklist = new FreqAndLocChecklist();

            for (int cell = 0; cell < blockPoss.length; cell++) {
                int[] currentCellPoss = blockPoss[cell].getPossibilities();

                // Add the numbers from all 9 possibility lists to the checklist
                for (int i = 0; i < currentCellPoss.length; i++) {
                    checklist.addNumber(currentCellPoss[i], cell);
                }
            }

            if (checklist.getFork() != null) {
                int[] forkInfo = checklist.getFork();
                int[] cellA = convertCellIdToRowCol(blockId, forkInfo[2]);
                int[] cellB = convertCellIdToRowCol(blockId, forkInfo[3]);

                for (int num = 1; num < 10; num++) {
                    if (num != forkInfo[0] && num != forkInfo[1]) {
                        possibilitiesBoard.removeCellPossibility(num, cellA[0], cellA[1]);
                        possibilitiesBoard.removeCellPossibility(num, cellB[0], cellB[1]);
                    }
                }
            }
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

    public void updatePossBoard() {
        // Iterate through every cell of the board
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                // If the cell has a number already...
                if (board.getCell(row, col) != 0) {
                    // Clear that cell's possibilities (since it has been solved)
                    possibilitiesBoard.clearCellPossibilities(row, col);

                    // Remove the number from the possibility lists of its row, col, and 3 x 3 block
                    possibilitiesBoard.removeRowPossibility(board.getCell(row, col), row);
                    possibilitiesBoard.removeColPossibility(board.getCell(row, col), col);
                    possibilitiesBoard.removeBlockPossibility(board.getCell(row, col), blocks.getBlockId(row, col));
                } else {
                    // Check to see what unique numbers are in its row, col, and block
                    Set<Integer> numsToRemove = Arrays.stream(board.getRowById(row))
                                                        .boxed()
                                                        .collect(Collectors.toSet());
                    numsToRemove.addAll(Arrays.stream(board.getColById(col))
                                            .boxed()
                                            .collect(Collectors.toSet()));
                    numsToRemove.addAll(Arrays.stream(board.getBlockAsArrayById(blocks.getBlockId(row, col)))
                                            .boxed()
                                            .collect(Collectors.toSet()));

                    //System.out.println("Numbers not possible in row " + row + ", col " + col + ": " + numsToRemove.toString());

                    // Remove them from the cell's possibilities
                    for (int num : numsToRemove) {
                        if (num != 0) {
                            possibilitiesBoard.removeCellPossibility(num, row, col);
                        }
                    }

                    //System.out.println("Numbers possible: " + Arrays.toString(possibilitiesBoard.getCellPossibility(row, col)) + "\n");
                }
            }
        }

        //System.out.println("Initial Possibilities Board:\n" + possibilitiesBoard.toString());
    }

    /**
     * removeNumberFromPossibilities updates the possibilities table by taking a number, writing to the board,
     * and removing it from its corresponding row, col, and 3 x 3 block.
     *
     * @param num (the number to be removed)
     * @param rowId (the row the number is in)
     * @param colId (the col the number is in)
     */
    private void removeNumberFromPossibilities(int num, int rowId, int colId) {
        if (board.getCell(rowId, colId) == 0 && !board.numInRow(rowId, num) && !board.numInCol(colId, num)
            && !board.numInBlock(blocks.getBlockId(rowId, colId), num)) {
            // Write the number in the cell
            board.writeNumber(num, rowId, colId);

            // Clear that cell's possibilities (since it has been solved)
            possibilitiesBoard.clearCellPossibilities(rowId, colId);
        }
    }

    /**
     * This method determines the coordinates of a cell using the block's id and the cell number (1-9) that refers
     * to a cell within a 3 x 3 block (as follows):
     *
     * 0 1 2
     * 3 4 5
     * 6 7 8
     *
     * @param blockId (the block id of the cell)
     * @param cellId
     * @return
     */
    private int[] convertCellIdToRowCol(int blockId, int cellId) {
        int[] rowCol = new int[2];

        int initRow = blocks.getBlockIndices(blockId)[0];
        int finalRow = blocks.getBlockIndices(blockId)[1];
        int initCol = blocks.getBlockIndices(blockId)[2];
        int finalCol = blocks.getBlockIndices(blockId)[3];

        switch(cellId) {
            case 0:
                rowCol[0] = initRow;
                rowCol[1] = initCol;
                break;
            case 1:
                rowCol[0] = initRow;
                rowCol[1] = initCol + 1;
                break;
            case 2:
                rowCol[0] = initRow;
                rowCol[1] = finalCol;
                break;
            case 3:
                rowCol[0] = initRow + 1;
                rowCol[1] = initCol;
                break;
            case 4:
                rowCol[0] = initRow + 1;
                rowCol[1] = initCol + 1;
                break;
            case 5:
                rowCol[0] = initRow + 1;
                rowCol[1] = finalCol;
                break;
            case 6:
                rowCol[0] = finalRow;
                rowCol[1] = initCol;
                break;
            case 7:
                rowCol[0] = finalRow;
                rowCol[1] = initCol + 1;
                break;
            case 8:
                rowCol[0] = finalRow;
                rowCol[1] = finalCol;
                break;
        }

        return rowCol;
    }
}
