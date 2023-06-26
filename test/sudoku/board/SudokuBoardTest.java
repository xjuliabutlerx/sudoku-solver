package sudoku.board;

import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<ArrayList<Integer>> testBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row =
                new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            testBoard.add(row);
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.size());

        board.setBoard(testBoard);

        Assertions.assertEquals(expectedString, board.toString());
        Assertions.assertEquals(testBoard, board.getBoard());
    }

    @Test
    void returnsRowOfOnes() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> testBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row =
                new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 2, 2, 2, 2, 2));
        ArrayList<Integer> onesRow =
                new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));

        for (int i = 0 ; i < 9; i++) {
            if (i == 1) {
                testBoard.add(onesRow);
            } else {
                testBoard.add(row);
            }
        }

        Assertions.assertEquals(9, testBoard.size());

        board.setBoard(testBoard);

        Assertions.assertEquals(onesRow, board.getRowById(1));
    }

    @Test
    void returnsColOfOnes() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> testBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row =
                new ArrayList<Integer>(Arrays.asList(2, 2, 2, 2, 1, 2, 2, 2, 2));

        for (int i = 0 ; i < 9; i++) {
            testBoard.add(row);
        }

        Assertions.assertEquals(9, testBoard.size());

        board.setBoard(testBoard);

        Assertions.assertEquals(new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1)),
                                board.getColById(4));
    }

    @Test
    void writeValidNumbersInValidCells() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> testBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row =
                new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            testBoard.add(row);
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.size());

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

        ArrayList<ArrayList<Integer>> testBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row =
                new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
        String expectedString = "";

        for (int i = 0 ; i < 9; i++) {
            testBoard.add(row);
            expectedString += "1 1 1 1 1 1 1 1 1 \n";
        }

        Assertions.assertEquals(9, testBoard.size());

        board.setBoard(testBoard);

        Assertions.assertFalse(board.writeNumber(10, 1, 1));
        Assertions.assertFalse(board.writeNumber(-5, 5, 8));
        Assertions.assertFalse(board.writeNumber(2, -1, -1));
    }

    @Test
    void getBlock1() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(8, 2, 7));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(9, 6, 5));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(3, 4, 1));

        ArrayList<ArrayList<Integer>> block1 = new ArrayList<ArrayList<Integer>>();

        block1.add(rowA);
        block1.add(rowB);
        block1.add(rowC);

        Assertions.assertEquals(block1, board.getBlockById(1));
    }

    @Test
    void getBlock2() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(1, 5, 4));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(3, 2, 7));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(6, 8, 9));

        ArrayList<ArrayList<Integer>> block2 = new ArrayList<ArrayList<Integer>>();

        block2.add(rowA);
        block2.add(rowB);
        block2.add(rowC);

        Assertions.assertEquals(block2, board.getBlockById(2));
    }

    @Test
    void getBlock3() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(3, 9, 6));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(1, 4, 8));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(7, 5, 2));

        ArrayList<ArrayList<Integer>> block3 = new ArrayList<ArrayList<Integer>>();

        block3.add(rowA);
        block3.add(rowB);
        block3.add(rowC);

        Assertions.assertEquals(block3, board.getBlockById(3));
    }

    @Test
    void getBlock4() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(5, 9, 3));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(4, 7, 2));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(6, 1, 8));

        ArrayList<ArrayList<Integer>> block4 = new ArrayList<ArrayList<Integer>>();

        block4.add(rowA);
        block4.add(rowB);
        block4.add(rowC);

        Assertions.assertEquals(block4, board.getBlockById(4));
    }

    @Test
    void getBlock5() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(5, 1, 3));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(9, 7, 2));

        ArrayList<ArrayList<Integer>> block5 = new ArrayList<ArrayList<Integer>>();

        block5.add(rowA);
        block5.add(rowB);
        block5.add(rowC);

        Assertions.assertEquals(block5, board.getBlockById(5));
    }

    @Test
    void getBlock6() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(2, 7, 1));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(6, 8, 9));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(4, 3, 5));

        ArrayList<ArrayList<Integer>> block6 = new ArrayList<ArrayList<Integer>>();

        block6.add(rowA);
        block6.add(rowB);
        block6.add(rowC);

        Assertions.assertEquals(block6, board.getBlockById(6));
    }

    @Test
    void getBlock7() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(7, 8, 6));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(1, 5, 4));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(2, 3, 9));

        ArrayList<ArrayList<Integer>> block7 = new ArrayList<ArrayList<Integer>>();

        block7.add(rowA);
        block7.add(rowB);
        block7.add(rowC);

        Assertions.assertEquals(block7, board.getBlockById(7));
    }

    @Test
    void getBlock8() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(2, 3, 5));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(7, 9, 6));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(8, 4, 1));

        ArrayList<ArrayList<Integer>> block8 = new ArrayList<ArrayList<Integer>>();

        block8.add(rowA);
        block8.add(rowB);
        block8.add(rowC);

        Assertions.assertEquals(block8, board.getBlockById(8));
    }

    @Test
    void getBlock9() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        ArrayList<Integer> rowA = new ArrayList<Integer>(Arrays.asList(9, 1, 4));
        ArrayList<Integer> rowB = new ArrayList<Integer>(Arrays.asList(8, 2, 3));
        ArrayList<Integer> rowC = new ArrayList<Integer>(Arrays.asList(5, 6, 7));

        ArrayList<ArrayList<Integer>> block9 = new ArrayList<ArrayList<Integer>>();

        block9.add(rowA);
        block9.add(rowB);
        block9.add(rowC);

        Assertions.assertEquals(block9, board.getBlockById(9));
    }

    @Test
    void getInvalidBlock() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        Assertions.assertNull(board.getBlockById(0));
        Assertions.assertNull(board.getBlockById(10));
        Assertions.assertNull(board.getBlockById(-10));
    }

    /*
    @Test
    void isSolvable() {
        SudokuBoard board = new SudokuBoard();

        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        board.setBoard(blankBoard);

        // TODO Run test after the method isSolved has been re-written
        Assertions.assertTrue(board.isSolved());
    }
     */
}