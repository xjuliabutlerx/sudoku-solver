package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FreqAndLocChecklistTest {

    @Test
    void hashmapsNotNullAtStart() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        Assertions.assertNotNull(checklist.getNumberFreqs());
        Assertions.assertNotNull(checklist.getNumberLocs());
    }

    @Test
    void addNumber() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        checklist.addNumber(1, 0);

        Assertions.assertEquals(1, checklist.getNumberFrequency(1));
        Assertions.assertArrayEquals(new int[] {0}, checklist.getNumberLocations(1));
        Assertions.assertEquals(checklist.getNumberFrequency(1), checklist.getNumberLocations(1).length);
    }

    @Test
    void addTwoDifferentNumbers() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        checklist.addNumber(8, 1);

        Assertions.assertEquals(1, checklist.getNumberFrequency(8));
        Assertions.assertArrayEquals(new int[] {1}, checklist.getNumberLocations(8));
        Assertions.assertEquals(checklist.getNumberFrequency(8), checklist.getNumberLocations(8).length);

        checklist.addNumber(4, 6);

        Assertions.assertEquals(1, checklist.getNumberFrequency(4));
        Assertions.assertArrayEquals(new int[] {6}, checklist.getNumberLocations(4));
        Assertions.assertEquals(checklist.getNumberFrequency(4), checklist.getNumberLocations(4).length);
    }

    @Test
    void addSameNumberTwice() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        checklist.addNumber(8, 1);
        checklist.addNumber(8, 3);
        checklist.addNumber(1, 0);

        Assertions.assertEquals(2, checklist.getNumberFrequency(8));
        Assertions.assertArrayEquals(new int[] {1, 3}, checklist.getNumberLocations(8));
        Assertions.assertEquals(checklist.getNumberFrequency(8), checklist.getNumberLocations(8).length);

        Assertions.assertEquals(1, checklist.getNumberFrequency(1));
        Assertions.assertArrayEquals(new int[] {0}, checklist.getNumberLocations(1));
        Assertions.assertEquals(checklist.getNumberFrequency(1), checklist.getNumberLocations(1).length);
    }

    @Test
    void forkExists() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        // Creating fork between 1 and 4 in cells 1 and 8
        checklist.addNumber(1, 1);
        checklist.addNumber(4, 1);
        checklist.addNumber(1, 8);
        checklist.addNumber(4, 8);

        // Adding extra numbers for noise
        checklist.addNumber(2, 8);
        checklist.addNumber(6, 0);
        checklist.addNumber(9, 1);

        Assertions.assertEquals(2, checklist.getNumberFrequency(1));
        Assertions.assertEquals(2, checklist.getNumberFrequency(4));

        Assertions.assertArrayEquals(new int[] {1, 4, 1, 8}, checklist.getFork());
    }

    @Test
    void forkDoesNotExist() {
        FreqAndLocChecklist checklist = new FreqAndLocChecklist();

        // Creating fork between 1 and 4 in cells 1 and 8
        checklist.addNumber(1, 1);
        checklist.addNumber(4, 2);
        checklist.addNumber(1, 8);
        checklist.addNumber(4, 8);

        // Adding extra numbers for noise
        checklist.addNumber(2, 8);
        checklist.addNumber(6, 0);
        checklist.addNumber(9, 1);

        Assertions.assertNull(checklist.getFork());
    }

}