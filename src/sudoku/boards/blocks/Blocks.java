package sudoku.boards.blocks;

import java.util.HashMap;

public class Blocks {

    private static Blocks blocks = null;

    private final HashMap<Integer, int[]> blockIds = new HashMap<Integer, int[]>();

    private Blocks() {
        // Block 1: Rows 0-2 and Cols 0-2
        blockIds.put(1, new int[] {0, 2, 0, 2});

        // Block 2: Rows 0-2 and Cols 3-5
        blockIds.put(2, new int[] {0, 2, 3, 5});

        // Block 3: Rows 0-2 and Cols 6-8
        blockIds.put(3, new int[] {0, 2, 6, 8});

        // Block 4: Rows 3-5 and Cols 0-2
        blockIds.put(4, new int[] {3, 5, 0, 2});

        // Block 5: Rows 3-5 and Cols 3-5
        blockIds.put(5, new int[] {3, 5, 3, 5});

        // Block 6: Rows 3-5 and Cols 6-8
        blockIds.put(6, new int[] {3, 5, 6, 8});

        // Block 7: Rows 6-8 and Cols 0-2
        blockIds.put(7, new int[] {6, 8, 0, 2});

        // Block 8: Rows 6-8 and Cols 3-5
        blockIds.put(8, new int[] {6, 8, 3, 5});

        // Block 9: Rows 6-8 and Cols 6-8
        blockIds.put(9, new int[] {6, 8, 6, 8});
    }

    public static synchronized Blocks getBlocks() {
        if (blocks == null) {
            blocks = new Blocks();
        }
        return blocks;
    }

    /**
     * This method will take the cell's row and col and will return the id for the block the cell is in, as follows:
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
     * @param row
     * @param col
     * @return blockId
     */
    public int getBlockId(int row, int col) {
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            return 0;
        }

        // Rows 0-2
        if (row >= 0 && row <= 2) {

            if (col >= 0 && col <= 2) {
                return 1;
            } else if (col >= 3 && col <= 5) {
                return 2;
            } else if (col >= 6 && col <= 8) {
                return 3;
            }

            // Rows 3-5
        } else if (3 <= row && row <= 5) {

            if (col >= 0 && col <= 2) {
                return 4;
            } else if (col >= 3 && col <= 5) {
                return 5;
            } else if (col >= 6 && col <= 8) {
                return 6;
            }

        } else if (6 <= row && row <= 8) {

            if (col >= 0 && col <= 2) {
                return 7;
            } else if (col >= 3 && col <= 5) {
                return 8;
            } else if (col >= 6 && col <= 8) {
                return 9;
            }

        }

        return 0;
    }

    public int[] getBlockIndices(int blockId) {
        if (!isValidBlockId(blockId)) {
            return new int[] {};
        }

        return blockIds.get(blockId);
    }

    public boolean isValidBlockId(int blockId) {
        if (blockId < 1 || blockId > 9) {
            return false;
        }
        return true;
    }

    /**
     * calculateIndexPadding calculates how much padding should be added to the indices of the
     * block so those values can be mapped to the correct cell in the 9 x 9 2D list
     *
     * (The blocks are 3 x 3 in dimension, so their indices are 0-2 for rows and cols, but the
     * sudoku board have indices 0-8 for rows and cols. Blocks 2-9 need to have some padding
     * added to them so the missing cells can be correctly identified in the larger board).
     *
     * @param blockId (an integer 1-9 to indicate the block)
     * @return adjustments/padding to be added to indices
     */
    public int[] calculateIndexPadding(int blockId) {
        // adjustments[0] = row padding
        // adjustments[1] = col padding
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

}
