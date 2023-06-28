package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberPossibilityListTest {

    @Test
    void initializationCreatesArrayListOf9() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();
        int[] expected = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

        Assertions.assertArrayEquals(expected, possibilityList.getPossibilities());
        Assertions.assertFalse(possibilityList.onlyOneNumPossible());
    }

    @Test
    void atInitAllNumsPossible() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        Assertions.assertTrue(possibilityList.isNumPossible(1));
        Assertions.assertTrue(possibilityList.isNumPossible(2));
        Assertions.assertTrue(possibilityList.isNumPossible(3));
        Assertions.assertTrue(possibilityList.isNumPossible(4));
        Assertions.assertTrue(possibilityList.isNumPossible(5));
        Assertions.assertTrue(possibilityList.isNumPossible(6));
        Assertions.assertTrue(possibilityList.isNumPossible(7));
        Assertions.assertTrue(possibilityList.isNumPossible(8));
        Assertions.assertTrue(possibilityList.isNumPossible(9));
    }

    @Test
    void removePossibility() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        possibilityList.remove(1);

        Assertions.assertArrayEquals(new int[]{2, 3, 4, 5, 6, 7, 8, 9},
                                     possibilityList.getPossibilities());
        Assertions.assertFalse(possibilityList.isNumPossible(1));
        Assertions.assertTrue(possibilityList.isNumPossible(2));
    }

    @Test
    void removePossibilities() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        possibilityList.remove(1);

        Assertions.assertArrayEquals(new int[]{2, 3, 4, 5, 6, 7, 8, 9},
                                     possibilityList.getPossibilities());
        Assertions.assertFalse(possibilityList.isNumPossible(1));
        Assertions.assertTrue(possibilityList.isNumPossible(9));
        Assertions.assertTrue(possibilityList.isNumPossible(4));

        possibilityList.remove(9);
        possibilityList.remove(4);

        Assertions.assertArrayEquals(new int[]{2, 3, 5, 6, 7, 8},
                possibilityList.getPossibilities());
        Assertions.assertFalse(possibilityList.isNumPossible(9));
        Assertions.assertFalse(possibilityList.isNumPossible(4));
        Assertions.assertTrue(possibilityList.isNumPossible(5));
    }

    @Test
    void removePossibilitiesTwiceDoesNotThrowException() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        possibilityList.remove(3);
        possibilityList.remove(8);

        Assertions.assertArrayEquals(new int[]{1, 2, 4, 5, 6, 7, 9},
                                     possibilityList.getPossibilities());
        Assertions.assertFalse(possibilityList.isNumPossible(3));
        Assertions.assertFalse(possibilityList.isNumPossible(8));
        Assertions.assertTrue(possibilityList.isNumPossible(1));

        Assertions.assertDoesNotThrow(() -> possibilityList.remove(8));
    }

    @Test
    void oneNumPossibleFalse() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        possibilityList.remove(2);
        possibilityList.remove(4);
        possibilityList.remove(3);
        possibilityList.remove(8);
        possibilityList.remove(1);
        possibilityList.remove(5);
        possibilityList.remove(7);

        Assertions.assertFalse(possibilityList.onlyOneNumPossible());
        Assertions.assertArrayEquals(new int[] {6, 9}, possibilityList.getPossibilities());
    }

    @Test
    void oneNumPossibleTrue() {
        NumberPossibilityList possibilityList = new NumberPossibilityList();

        possibilityList.remove(2);
        possibilityList.remove(4);
        possibilityList.remove(3);
        possibilityList.remove(8);
        possibilityList.remove(9);
        possibilityList.remove(1);
        possibilityList.remove(5);
        possibilityList.remove(7);

        Assertions.assertTrue(possibilityList.onlyOneNumPossible());
        Assertions.assertEquals(6, possibilityList.getPossibilities()[0]);
    }

}