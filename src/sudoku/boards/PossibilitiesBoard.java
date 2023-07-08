package sudoku.boards;

import sudoku.boards.blocks.Blocks;

import java.util.Arrays;

public class PossibilitiesBoard {

    private PossibilityList[][] possibilitiesBoard = new PossibilityList[9][9];
    private Blocks blocks;

    public PossibilitiesBoard() {
        blocks = Blocks.getBlocks();

        // Instantiates a 9 x 9 2D list of NumberPossibilityLists
        for (int i = 0; i < 9; i++) {
            possibilitiesBoard[i] = new PossibilityList[] {new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList(),
                                                                 new PossibilityList()};
        }
    }

    // TESTING ONLY
    public PossibilitiesBoard(PossibilityList[][] board) {
        this.blocks = Blocks.getBlocks();
        this.possibilitiesBoard = board;
    }

    public PossibilityList[][] getPossibilitiesBoard() {
        return possibilitiesBoard;
    }

    public PossibilityList[][] getPossibilitiesBoardByCol() {
        PossibilityList[][] result = new PossibilityList[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                result[r][c] = possibilitiesBoard[c][r];
            }
        }

        return result;
    }

    public int[] getCellPossibility(int rowId, int colId) {
        return possibilitiesBoard[rowId][colId].getPossibilities();
    }

    public PossibilityList[] getRowPossibilities(int rowId) {
        return possibilitiesBoard[rowId];
    }

    public PossibilityList[] getColPossibilities(int colId) {
        return getPossibilitiesBoardByCol()[colId];
    }

    public PossibilityList[][] getBlockPossibilities(int blockId) {
        PossibilityList[][] blockPossibilities = new PossibilityList[3][3];

        int initRow = blocks.getBlockIndices(blockId)[0];
        int finalRow = blocks.getBlockIndices(blockId)[1];
        int initCol = blocks.getBlockIndices(blockId)[2];
        int finalCol = blocks.getBlockIndices(blockId)[3];

        int blockRow = 0;
        int blockCol = 0;

        // The numbers from that block are copied into a 2D list
        for (int r = initRow; r <= finalRow; r++) {
            for (int c = initCol; c <= finalCol; c++) {
                blockPossibilities[blockRow][blockCol] = possibilitiesBoard[r][c];
                blockCol++;
            }
            blockRow++;
            blockCol = 0;
        }

        return blockPossibilities;
    }

    // TODO test
    public PossibilityList[] getBlockPossibilitiesAsArray(int blockId) {
        PossibilityList[] blockPossibilities = new PossibilityList[9];

        int initRow = blocks.getBlockIndices(blockId)[0];
        int finalRow = blocks.getBlockIndices(blockId)[1];
        int initCol = blocks.getBlockIndices(blockId)[2];
        int finalCol = blocks.getBlockIndices(blockId)[3];

        int index = 0;

        for (int r = initRow; r <= finalRow; r++) {
            for (int c = initCol; c <= finalCol; c++) {
                blockPossibilities[index] = possibilitiesBoard[r][c];
                index++;
            }
        }

        //System.out.println("The flattened block array: " + Arrays.toString(blockArray));
        return blockPossibilities;
    }

    /**
     * This method removes all numbers from the list of possible numbers for a cell. This usually
     * means that the cell's number was given or solved.
     *
     * @param rowId the row of the cell where the number should be removed
     * @param colId the col of the cell where the number should be removed
     */
    public void clearCellPossibilities(int rowId, int colId) {
        possibilitiesBoard[rowId][colId].clear();
    }

    /**
     * This method removes a number 1-9 from the list of possible numbers for a cell
     *
     * @param num the number to be removed
     * @param rowId the row of the cell where the number should be removed
     * @param colId the col of the cell where the number should be removed
     */
    public void removeCellPossibility(int num, int rowId, int colId) {
        possibilitiesBoard[rowId][colId].remove(num);
    }

    /**
     * This method removes a number 1-9 from the list of possible numbers for an entire row
     *
     * @param num the number to be removed
     * @param rowId the row of the cells where the number should be removed
     */
    public void removeRowPossibility(int num, int rowId) {
        for (int i = 0; i < possibilitiesBoard[rowId].length; i++) {
            possibilitiesBoard[rowId][i].remove(num);
        }
    }

    /**
     * This method removes a number 1-9 from the list of possible numbers for an entire col
     *
     * @param num the number to be removed
     * @param colId the col of the cells where the number should be removed
     */
    public void removeColPossibility(int num, int colId) {
        for (int i = 0; i < getPossibilitiesBoardByCol()[colId].length; i++) {
            possibilitiesBoard[i][colId].remove(num);
        }
    }

    /**
     * This method removes a number 1-9 from the list of possible numbers for an entire block
     *
     * @param num the number to be removed
     * @param blockId the row of the cells where the number should be removed
     */
    public void removeBlockPossibility(int num, int blockId) {
        int initRow = blocks.getBlockIndices(blockId)[0];
        int finalRow = blocks.getBlockIndices(blockId)[1];
        int initCol = blocks.getBlockIndices(blockId)[2];
        int finalCol = blocks.getBlockIndices(blockId)[3];

        for (int r = initRow; r <= finalRow; r++) {
            for (int c = initCol; c <= finalCol; c++) {
                possibilitiesBoard[r][c].remove(num);
            }
        }
    }

    public String printCellPossibilities(int rowId, int colId) {
        return Arrays.toString(getCellPossibility(rowId, colId));
    }

    public void printRowPossibility(int rowId) {
        System.out.println("Row " + rowId + ": " + Arrays.toString(getRowPossibilities(rowId)));
    }

    @Override
    public String toString() {
        String possibilitiesString = "";

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                possibilitiesString += "Row " + row + ", Col " + col + " Poss. Values: " +
                        Arrays.toString(getCellPossibility(row, col)) + "\n";
            }
            possibilitiesString += "\n";
        }

        return possibilitiesString;
    }
}
