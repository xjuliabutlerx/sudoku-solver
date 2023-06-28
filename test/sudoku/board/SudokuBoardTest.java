package sudoku.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    @Test
    void notCallingFetchBoardGivesNullObjects() {
        SudokuBoard board = new SudokuBoard();

        Assertions.assertNull(board.getBoard());
        Assertions.assertNull(board.getSolution());
        Assertions.assertNull(board.getDifficulty());
    }

    @Test
    void callingFetchBoardGivesNonNullObjects() {
        SudokuBoard board = new SudokuBoard();

        Assertions.assertTrue(board.fetchBoard());
        Assertions.assertNotNull(board.getBoard());
        Assertions.assertNotNull(board.getSolution());
        Assertions.assertNotNull(board.getDifficulty());
    }

    @Test
    void setBoardOfOnes() {
        SudokuBoard board = new SudokuBoard();

        int[][] testBoard = new int[9][9];
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard[i][j] = 1;
            }
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.length);

        board.setBoard(testBoard);

        Assertions.assertEquals(expectedString, board.toString());
        Assertions.assertEquals(testBoard, board.getBoard());
    }

    @Test
    void returnsRowOfOnes() {
        SudokuBoard board = new SudokuBoard();

        int[][] testBoard = new int[9][9];
        int[] solution = {1, 1, 1, 1, 1, 1, 1, 1, 1};

        for (int i = 0 ; i < 9; i++) {
            if (i == 1) {
                for (int j = 0; j < 9; j++) {
                    testBoard[i][j] = 1;
                }
            } else {
                for (int j = 0; j < 9; j++) {
                    testBoard[i][j] = 2;
                }
            }
        }

        Assertions.assertEquals(9, testBoard.length);

        board.setBoard(testBoard);

        Assertions.assertArrayEquals(solution, board.getRowById(1));
    }

    @Test
    void returnsColOfOnes() {
        SudokuBoard board = new SudokuBoard();

        int[][] testBoard = new int[9][9];
        int[] solution = {1, 1, 1, 1, 1, 1, 1, 1, 1};

        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j == 4) {
                    testBoard[i][j] = 1;
                } else {
                    testBoard[i][j] = 2;
                }
            }
        }

        Assertions.assertEquals(9, testBoard.length);

        board.setBoard(testBoard);

        Assertions.assertArrayEquals(solution, board.getColById(4));
    }

    @Test
    void writeValidNumbersInValidCells() {
        SudokuBoard board = new SudokuBoard();

        int[][] testBoard = new int[9][9];
        int[] row = {1, 1, 1, 1, 1, 1, 1, 1, 1};
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard[i][j] = 1;
            }
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.length);

        board.setBoard(testBoard);
        Assertions.assertTrue(board.writeNumber(2, 0, 0));
        Assertions.assertTrue(board.writeNumber(9, 8, 8));
        Assertions.assertTrue(board.writeNumber(6, 4, 4));

        Assertions.assertEquals(2, board.getCell(0, 0));
        Assertions.assertEquals(9, board.getCell(8, 8));
        Assertions.assertEquals(6, board.getCell(4, 4));
    }

    @Test
    void failToWriteNumbersInCells() {
        SudokuBoard board = new SudokuBoard();

        int[][] testBoard = new int[9][9];
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testBoard[i][j] = 1;
            }
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.length);

        board.setBoard(testBoard);

        Assertions.assertFalse(board.writeNumber(10, 1, 1));
        Assertions.assertFalse(board.writeNumber(-5, 5, 8));
        Assertions.assertFalse(board.writeNumber(2, -1, -1));
    }

    @Test
    void getBlock1() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {8, 2, 7};
        solutionBlock[1] = new int[] {9, 6, 5};
        solutionBlock[2] = new int[] {3, 4, 1};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(1));
    }

    @Test
    void getBlock2() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {1, 5, 4};
        solutionBlock[1] = new int[] {3, 2, 7};
        solutionBlock[2] = new int[] {6, 8, 9};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(2));
    }

    @Test
    void getBlock3() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {3, 9, 6};
        solutionBlock[1] = new int[] {1, 4, 8};
        solutionBlock[2] = new int[] {7, 5, 2};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(3));
    }

    @Test
    void getBlock4() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {5, 9, 3};
        solutionBlock[1] = new int[] {4, 7, 2};
        solutionBlock[2] = new int[] {6, 1, 8};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(4));
    }

    @Test
    void getBlock5() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {4, 6, 8};
        solutionBlock[1] = new int[] {5, 1, 3};
        solutionBlock[2] = new int[] {9, 7, 2};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(5));
    }

    @Test
    void getBlock6() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {2, 7, 1};
        solutionBlock[1] = new int[] {6, 8, 9};
        solutionBlock[2] = new int[] {4, 3, 5};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(6));
    }

    @Test
    void getBlock7() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {7, 8, 6};
        solutionBlock[1] = new int[] {1, 5, 4};
        solutionBlock[2] = new int[] {2, 3, 9};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(7));
    }

    @Test
    void getBlock8() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {2, 3, 5};
        solutionBlock[1] = new int[] {7, 9, 6};
        solutionBlock[2] = new int[] {8, 4, 1};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(8));
    }

    @Test
    void getBlock9() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        int[][] solutionBlock = new int[3][3];

        solutionBlock[0] = new int[] {9, 1, 4};
        solutionBlock[1] = new int[] {8, 2, 3};
        solutionBlock[2] = new int[] {5, 6, 7};

        Assertions.assertArrayEquals(solutionBlock, board.getBlockById(9));
    }

    @Test
    void getInvalidBlock() {
        SudokuBoard board = new SudokuBoard();

        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        board.setBoard(blankBoard);

        Assertions.assertNull(board.getBlockById(0));
        Assertions.assertNull(board.getBlockById(10));
        Assertions.assertNull(board.getBlockById(-10));
    }

}