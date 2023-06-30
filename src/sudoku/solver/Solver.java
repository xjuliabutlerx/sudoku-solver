package sudoku.solver;

import sudoku.boards.PossibilitiesBoard;
import sudoku.boards.PossibilityList;
import sudoku.boards.SudokuBoard;
import sudoku.boards.blocks.Blocks;
import sudoku.exceptions.LogicException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Solver {

    private final SudokuBoard board;
    private PossibilitiesBoard possibilitiesBoard;
    private Blocks blocks;

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

            calcCellPossibilitiesByRow();
            calcCellPossibilitiesByCol();
            calcCellPossibilitiesByBlock();
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
                            possibilitiesBoard.removeRowPossibility(auxBlock[(r + adj[0])][(c + adj[1])], (r + adj[0]));
                            possibilitiesBoard.removeColPossibility(auxBlock[(r + adj[0])][(c + adj[1])], (c + adj[1]));
                            possibilitiesBoard.removeBlockPossibility(auxBlock[(r + adj[0])][(c + adj[1])],
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
                    PossibilityList possibilityList = new PossibilityList();

                    // Determine if numbers 1-9 are in the cell's respective row, col, or block
                    // If so, remove it from the possibilities list
                    for (int num = 1; num < 10; num++) {
                        if (board.numInRow(row, num) || board.numInCol(col, num) ||
                                board.numInBlock(blocks.getBlockId(row, col), num)) {
                            // TODO when possibilities board is implemented, delete
                            possibilityList.remove(num);

                            possibilitiesBoard.removeCellPossibility(num, row, col);
                        }
                    }

                    if (possibilityList.onlyOneNumPossible()) {
                        board.writeNumber(possibilityList.getPossibilities()[0], row, col);
                        //System.out.println(board.toString());

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

    // TODO with possibilitiesBoard in place, this may not be necessary anymore
    public void calcCellPossibilitiesByRow() {
        int[][] auxBoard = board.getBoard();

        // Iterate through each row...
        for (int row = 0; row < 9; row++) {
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
            for (int col = 0; col < 9; col++) {
                if (auxBoard[row][col] == 0) {
                    for (int num = 1; num < 10; num++) {
                        if(board.numInRow(row, num) || board.numInCol(col, num) ||
                                board.numInBlock(blocks.getBlockId(row, col), num)) {
                            cellPossibilities[col].remove(num);
                        }
                    }
                }
            }

            // If a cellPossibilities unit has only 1 possible number, then write the number
            // in the cell
            for (int col = 0; col < 9; col++) {
                if (cellPossibilities[col].getPossibilities().length == 1) {
                    //System.out.println("Writing " + cellPossibilities[col].getPossibilities()[0] + " in " + row + ", " + col);
                    board.writeNumber(cellPossibilities[col].getPossibilities()[0], row, col);

                    // Clear that cell's possibilities
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

    // TODO with possibilitiesBoard in place, this may not be necessary anymore
    public void calcCellPossibilitiesByCol() {
        int[][] auxBoard = board.getBoardByCols();

        // Iterate through each row...
        for (int col = 0; col < 9; col++) {
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
            for (int row = 0; row < 9; row++) {
                if (auxBoard[col][row] == 0) {
                    for (int num = 1; num < 10; num++) {
                        if(board.numInRow(row, num) || board.numInCol(col, num) ||
                                board.numInBlock(blocks.getBlockId(row, col), num)) {
                            cellPossibilities[row].remove(num);
                        }
                    }
                }
            }

            // If a cellPossibilities unit has only 1 possible number, then write the number
            // in the cell
            for (int row = 0; row < 9; row++) {
                if (cellPossibilities[row].getPossibilities().length == 1) {
                    //System.out.println("Writing " + cellPossibilities[col].getPossibilities()[0] + " in " + row + ", " + col);
                    board.writeNumber(cellPossibilities[row].getPossibilities()[0], row, col);

                    // Clear the cell of its possibilities
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

    // TODO with possibilitiesBoard in place, the second half of this method might not be necessary
    public void calcCellPossibilitiesByBlock() {
        int[][] auxBoard = board.getBoard();

        for (int blockId = 1; blockId < 10; blockId++) {
            int[][] auxBlock = board.getBlockById(blockId);

            PossibilityList[][] cellPossibilities = new PossibilityList[3][3];
            cellPossibilities[0] = new PossibilityList[] {new PossibilityList(),
                                                                new PossibilityList(),
                                                                new PossibilityList()};
            cellPossibilities[1] = new PossibilityList[] {new PossibilityList(),
                                                                new PossibilityList(),
                                                                new PossibilityList()};
            cellPossibilities[2] = new PossibilityList[] {new PossibilityList(),
                                                                new PossibilityList(),
                                                                new PossibilityList()};

            int[] adj = blocks.calculateIndexPadding(blockId);

            // Determine what numbers are possible for a cell in a particular block
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (auxBlock[r][c] == 0) {
                        for (int num = 1; num < 10; num++) {
                            if (board.numInRow(r + adj[0], num)) {
                                possibilitiesBoard.removeCellPossibility(num, r + adj[0], c + adj[1]);
                                for (int i = 0; i < 3; i++) {
                                    // TODO delete after possibilitiesBoard implemented
                                    cellPossibilities[r][i].remove(num);
                                }
                            }

                            if (board.numInCol(c + adj[1], num)) {
                                possibilitiesBoard.removeCellPossibility(num, r + adj[0], c + adj[1]);
                                for (int i = 0; i < 3; i++) {
                                    // TODO delete after possibilitiesBoard implemented
                                    cellPossibilities[i][c].remove(num);
                                }
                            }

                            if (board.numInBlock(blockId, num)) {
                                possibilitiesBoard.removeCellPossibility(num, (r + adj[0]), (c + adj[1]));
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 3; j++) {
                                        // TODO delete after possibilitiesBoard implemented
                                        cellPossibilities[i][j].remove(num);
                                    }
                                }
                            }
                        }
                    } else {
                        // TODO delete after possibilitiesBoard implemented
                        cellPossibilities[r][c].clear();

                        // TODO check to see if this is redundant
                        // Clear the cell's possibilities
                        possibilitiesBoard.clearCellPossibilities((r + adj[0]), (c + adj[1]));

                        // Then remove this number from its row, col, and block's possibilities
                        possibilitiesBoard.removeRowPossibility(auxBoard[(r + adj[0])][(c + adj[1])], (r + adj[0]));
                        possibilitiesBoard.removeColPossibility(auxBoard[(r + adj[0])][(c + adj[1])], (c + adj[1]));
                        possibilitiesBoard.removeBlockPossibility(auxBoard[(r + adj[0])][(c + adj[1])],
                                blocks.getBlockId((r + adj[0]), (c + adj[1])));
                    }
                }
            }

            // TODO from this point on, see if the following functionality is necessary
            // If there is a unique value out of all the 9 cellPossibilities, then
            // write it in the cell
            Set<Integer> blockPoss = new HashSet<Integer>();
            ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();

            // Add each cell's possible numbers to a master set
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    int[] currentNums = cellPossibilities[r][c].getPossibilities();

                    for (int a = 0; a < currentNums.length; a++) {
                        if (blockPoss.contains(currentNums[a])) {
                            toBeRemoved.add(currentNums[a]);
                        } else {
                            blockPoss.add(currentNums[a]);
                        }
                    }
                }
            }

            for (int a = 0; a < toBeRemoved.size(); a++) {
                blockPoss.remove(toBeRemoved.get(a));
            }

            // If there's a unique element, then find which cell has it in its possibility
            // list, and write it in the cell
            if (blockPoss.size() == 1) {

                // Get the remaining number as and integer
                int num = (int) blockPoss.toArray()[0];
                //System.out.println("Remaining Number: " + num);

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (cellPossibilities[i][j].isNumPossible(num)) {
                            board.writeNumber(num, i + adj[0], j + adj[1]);

                            // Clear the cell's possibilities
                            possibilitiesBoard.clearCellPossibilities((i + adj[0]), (j + adj[1]));

                            // Then remove this number from its row, col, and block's possibilities
                            possibilitiesBoard.removeRowPossibility(auxBoard[(i + adj[0])][(j + adj[1])], (i + adj[0]));
                            possibilitiesBoard.removeColPossibility(auxBoard[(i + adj[0])][(j + adj[1])], (j + adj[1]));
                            possibilitiesBoard.removeBlockPossibility(auxBoard[(i + adj[0])][(j + adj[1])],
                                    blocks.getBlockId((i + adj[0]), (j + adj[1])));
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

    public void sudokuForkBlock() {
        int[][] auxBoard = board.getBoard();
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
