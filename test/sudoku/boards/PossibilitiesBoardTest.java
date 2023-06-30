package sudoku.boards;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.boards.blocks.Blocks;

class PossibilitiesBoardTest {

    PossibilitiesBoard possibilitiesBoard;
    protected Blocks blocks;

    @BeforeEach
    void setUp() {
        possibilitiesBoard = new PossibilitiesBoard();
        blocks = Blocks.getBlocks();
    }

    @Test
    void atStartBoardNotNull() {
        Assertions.assertNotNull(possibilitiesBoard.getPossibilitiesBoard());
    }

    @Test
    void atStartEachCellHas9PossibleNums() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(row, col).length);
            }
        }
    }

    @Test
    void getRow3Possibilities() {
        PossibilityList[][] testBoard = new PossibilityList[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                // Get rid of all the possibilities for cells in row 3 except for the one in col 3
                if (r == 3 && c != 3) {
                    testBoard[r][c] = new PossibilityList();
                    testBoard[r][c].clear();
                } else {
                    testBoard[r][c] = new PossibilityList();
                }
            }
        }

        this.possibilitiesBoard = new PossibilitiesBoard(testBoard);

        PossibilityList[] row3 = possibilitiesBoard.getRowPossibilities(3);

        Assertions.assertArrayEquals(new int[] {}, row3[0].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[1].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[2].getPossibilities());
        Assertions.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, row3[3].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[4].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[5].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[6].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[7].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, row3[8].getPossibilities());
    }

    @Test
    void getCol8Possibilities() {
        PossibilityList[][] testBoard = new PossibilityList[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                // Get rid of all the possibilities for cells in col 8 except for the one in row 8
                if (c == 8 && r != 8) {
                    testBoard[r][c] = new PossibilityList();
                    testBoard[r][c].clear();
                } else {
                    testBoard[r][c] = new PossibilityList();
                }
            }
        }

        this.possibilitiesBoard = new PossibilitiesBoard(testBoard);

        PossibilityList[] col8 = possibilitiesBoard.getColPossibilities(8);

        Assertions.assertArrayEquals(new int[] {}, col8[0].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[1].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[2].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[3].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[4].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[5].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[6].getPossibilities());
        Assertions.assertArrayEquals(new int[] {}, col8[7].getPossibilities());
        Assertions.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, col8[8].getPossibilities());
    }

    @Test
    void getBlock5Possibilities() {
        PossibilityList[][] testBoard = new PossibilityList[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (blocks.getBlockId(r, c) == 5) {
                    testBoard[r][c] = new PossibilityList();
                } else {
                    testBoard[r][c] = new PossibilityList();
                    testBoard[r][c].clear();
                }
            }
        }

        this.possibilitiesBoard = new PossibilitiesBoard(testBoard);

        PossibilityList[][] block5 = possibilitiesBoard.getBlockPossibilities(5);

        Assertions.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, block5[0][0].getPossibilities());
        Assertions.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, block5[1][1].getPossibilities());
        Assertions.assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, block5[2][2].getPossibilities());

        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(0, 0));
        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(8, 8));
        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(3, 2));
        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(6, 6));
    }

    @Test
    void clearCellPossibility() {
        Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(3, 5).length);
        Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(0, 2).length);
        Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(7, 6).length);

        possibilitiesBoard.clearCellPossibilities(3, 5);
        possibilitiesBoard.clearCellPossibilities(0, 2);
        possibilitiesBoard.clearCellPossibilities(7, 6);

        Assertions.assertEquals(0, possibilitiesBoard.getCellPossibility(3, 5).length);
        Assertions.assertEquals(0, possibilitiesBoard.getCellPossibility(0, 2).length);
        Assertions.assertEquals(0, possibilitiesBoard.getCellPossibility(7, 6).length);
    }

    @Test
    void removeCellPossibility() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
            }
        }

        possibilitiesBoard.removeCellPossibility(1, 3, 4);
        possibilitiesBoard.removeCellPossibility(7, 3, 4);

        possibilitiesBoard.removeCellPossibility(2, 4, 8);

        Assertions.assertEquals(7, possibilitiesBoard.getCellPossibility(3, 4).length);
        Assertions.assertEquals(8, possibilitiesBoard.getCellPossibility(4, 8).length);
    }

    @Test
    void removeRowPossibilities() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
            }
        }

        possibilitiesBoard.removeRowPossibility(2, 0);
        possibilitiesBoard.removeRowPossibility(5, 0);
        possibilitiesBoard.removeRowPossibility(8, 0);

        possibilitiesBoard.removeRowPossibility(1, 8);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (r == 0) {
                    Assertions.assertEquals(6, possibilitiesBoard.getCellPossibility(r, c).length);
                } else if (r == 8) {
                    Assertions.assertEquals(8, possibilitiesBoard.getCellPossibility(r, c).length);
                } else {
                    Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
                }
            }
        }
    }

    @Test
    void removeCowPossibilities() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
            }
        }

        possibilitiesBoard.removeColPossibility(2, 3);
        possibilitiesBoard.removeColPossibility(5, 3);
        possibilitiesBoard.removeColPossibility(8, 3);

        possibilitiesBoard.removeColPossibility(1, 6);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (c == 3) {
                    Assertions.assertEquals(6, possibilitiesBoard.getCellPossibility(r, c).length);
                } else if (c == 6) {
                    Assertions.assertEquals(8, possibilitiesBoard.getCellPossibility(r, c).length);
                } else {
                    Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
                }
            }
        }
    }

    @Test
    void removeBlockPossibilities() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
            }
        }

        possibilitiesBoard.removeBlockPossibility(2, 2);
        possibilitiesBoard.removeBlockPossibility(5, 2);
        possibilitiesBoard.removeBlockPossibility(8, 2);

        possibilitiesBoard.removeBlockPossibility(1, 3);
        possibilitiesBoard.removeBlockPossibility(7, 3);

        possibilitiesBoard.removeBlockPossibility(1, 7);

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (blocks.getBlockId(r, c) == 2) {
                    Assertions.assertEquals(6, possibilitiesBoard.getCellPossibility(r, c).length);
                } else if (blocks.getBlockId(r, c) == 3) {
                    Assertions.assertEquals(7, possibilitiesBoard.getCellPossibility(r, c).length);
                } else if (blocks.getBlockId(r, c) == 7) {
                    Assertions.assertEquals(8, possibilitiesBoard.getCellPossibility(r, c).length);
                } else {
                    Assertions.assertEquals(9, possibilitiesBoard.getCellPossibility(r, c).length);
                }
            }
        }
    }

    @Test
    void printOutputAtStart() {
        String expected = "Row 0, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 0, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 1, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 1, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 2, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 2, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 3, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 3, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 4, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 4, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 5, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 5, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 6, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 6, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 7, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 7, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n" +
                "Row 8, Col 0 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 1 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 2 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 3 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 4 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 5 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 6 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 7 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "Row 8, Col 8 Poss. Values: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n" +
                "\n";

        Assertions.assertEquals(expected, possibilitiesBoard.toString());
    }

}