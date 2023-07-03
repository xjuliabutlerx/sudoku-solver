package sudoku.boards;

import sudoku.boards.blocks.Blocks;

public class SudokuBoard {

    private int[][] board;
    private int[][] solution;
    private String difficulty;
    private Blocks blocks;

    public SudokuBoard() {
        blocks = Blocks.getBlocks();
    }

    public boolean fetchBoard() {
        try {
            SudokuBoardFetcher fetcher = new SudokuBoardFetcher();

            setBoard(fetcher.getBlankBoard());
            setSolution(fetcher.getSolution());

            setDifficulty(fetcher.getDifficulty());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setBoard(int[][] blankBoard) {
        this.board = blankBoard;
    }

    public void setSolution(int[][] solutionsBoard) {
        this.solution = solutionsBoard;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getBoardByCols() {
        int[][] result = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                result[i][j] = board[j][i];
            }
        }

        return result;
    }

    public int[][] getSolution() {
        return solution;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getCell(int row, int col) {
        return board[row][col];
    }

    /**
     * This method will return a particular row of the Sudoku board. The rows have ids from 0-8.
     * The top row is 0 and the bottom row is 8.
     *
     * @param rowId
     * @return a list of the row requested
     */
    public int[] getRowById(int rowId) {
        return board[rowId];
    }

    /**
     * This method will return a particular col of the Sudoku board. The cols have ids from 0-8.
     * The left-most row is 0 and the right-most col is 8.
     *
     * @param colId
     * @return a list of the col requested
     */
    public int[] getColById(int colId) {
        return getBoardByCols()[colId];
    }

    /**
     * This method will return 9 digits in a 3x3 ArrayList that corresponds to a block in the
     * Sudoku board. The blocks' indices are as follows:
     *
     * Block 1 - Rows 0-2, Cols 0-2 (top left block)
     * Block 2 - Rows 0-2, Cols 3-5
     * Block 3 - Rows 0-2, Cols 6-8 (top right block)
     * Block 4 - Rows 3-5, Cols 0-2
     * Block 5 - Rows 3-5, Cols 3-5 (center block)
     * Block 6 - Rows 3-5, Cols 6-8
     * Block 7 - Rows 6-8, Cols 0-2 (bottom left block)
     * Block 8 - Rows 6-8, Cols 3-5
     * Block 9 - Rows 6-8, Cols 6-8 (bottom right block)
     *
     * @return a 3x3 list
     */
    public int[][] getBlockById(int blockId) {
        int[][] block = new int[3][3];

        if (!blocks.isValidBlockId(blockId)) {
            return new int[][] {};
        }

        int initRow = blocks.getBlockIndices(blockId)[0];
        int finalRow = blocks.getBlockIndices(blockId)[1];
        int initCol = blocks.getBlockIndices(blockId)[2];
        int finalCol = blocks.getBlockIndices(blockId)[3];

        int blockRow = 0;
        int blockCol = 0;

        // The numbers from that block are copied into a 2D list
        for (int r = initRow; r <= finalRow; r++) {
            for (int c = initCol; c <= finalCol; c++) {
                block[blockRow][blockCol] = board[r][c];
                blockCol++;
            }
            blockRow++;
            blockCol = 0;
        }

        return block;
    }

    /**
     * numInRow indicates if a particular number is in a specified row
     *
     * @param rowId
     * @param num
     * @return true/false
     */
    public boolean numInRow(int rowId, int num) {
        int[] row = getRowById(rowId);

        for (int i = 0; i < 9; i++) {
            if (row[i] == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * numInCol indicates if a particular number is in a specified col
     *
     * @param colId
     * @param num
     * @return true/false
     */
    public boolean numInCol(int colId, int num) {
        int[] col = getColById(colId);

        for (int i = 0; i < 9; i++) {
            if (col[i] == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * numInBlock indicates if a particular number is in a specified block
     *
     * @param blockId
     * @param num
     * @return true/false
     */
    public boolean numInBlock(int blockId, int num) {
        int[][] block = getBlockById(blockId);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (block[r][c] == num) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean writeNumber(int num, int row, int col) {
        if (num < 1 || num > 9 ||
            row < 0 || row > 8 ||
            col < 0 || col > 8) {
            return false;
        }

        board[row][col] = num;

        return true;
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    output += "_ ";
                    //System.out.print("_ ");
                } else {
                    output += board[i][j] + " ";
                    //System.out.print(board[i][j + " ");
                }
            }
            output += "\n";
            //System.out.println();
        }

        return output;
    }

    /**
     * Determines if two Sudoku Boards are the same or not
     *
     * @param otherBoard (another Sudoku board)
     * @return true or false
     */
    public boolean equals(SudokuBoard otherBoard) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (this.getCell(row, col) != otherBoard.getCell(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

}
