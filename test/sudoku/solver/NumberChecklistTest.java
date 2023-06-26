package sudoku.solver;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.LogicException;

class NumberChecklistTest {

    NumberChecklist checklist;

    @BeforeEach
    void createNumberChecklist() {
        checklist = new NumberChecklist();
    }

    @Test
    void marksExists() throws LogicException {
        checklist.marksExists(4);
        Assertions.assertArrayEquals(new Boolean[] {false, false, false,
                                                    true, false, false,
                                                    false, false, false},
                                            checklist.getNumbers());
    }

    @Test
    void marksZeroExistsNoError() throws LogicException {
        checklist.marksExists(0);
        Assertions.assertArrayEquals(new Boolean[] {false, false, false,
                                                    false, false, false,
                                                    false, false, false},
                                            checklist.getNumbers());
    }

    @Test
    void marksExistsError() throws LogicException {
        checklist.marksExists(4);
        Assertions.assertEquals(checklist.getNumbers()[3], true);
        Assert.assertThrows(LogicException.class, () -> checklist.marksExists(4));
    }

    @Test
    void missingOneTrueA() throws LogicException {
        for (int i = 0; i < 8; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertTrue(checklist.missingOne());
    }

    @Test
    void missingOneTrueB() throws LogicException {
        for (int i = 1; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertTrue(checklist.missingOne());
    }

    @Test
    void missingOneTrueC() throws LogicException {
        for (int i = 0; i < 9; i++) {
            if (i != 4) {
                checklist.marksExists(i+1);
            }
        }

        Assertions.assertTrue(checklist.missingOne());
    }

    @Test
    void missingOneFalseA() throws LogicException {
        for (int i = 0; i < 7; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertFalse(checklist.missingOne());
    }

    @Test
    void missingOneFalseB() throws LogicException {
        for (int i = 2; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertFalse(checklist.missingOne());
    }

    @Test
    void getMissingNumA() throws LogicException {
        for (int i = 0; i < 8; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertTrue(checklist.missingOne());
        Assertions.assertEquals(9, checklist.getMissingNum());
    }

    @Test
    void getMissingNumB() throws LogicException {
        for (int i = 1; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertTrue(checklist.missingOne());
        Assertions.assertEquals(1, checklist.getMissingNum());
    }

    @Test
    void getMissingNumC() throws LogicException {
        for (int i = 0; i < 9; i++) {
            if (i != 4) {
                checklist.marksExists(i+1);
            }
        }

        Assertions.assertTrue(checklist.missingOne());
        Assertions.assertEquals(5, checklist.getMissingNum());
    }

    @Test
    void getMissingNumButAllExist() throws LogicException {
        for (int i = 0; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assertions.assertFalse(checklist.missingOne());
        Assertions.assertEquals(0, checklist.getMissingNum());
    }

    @Test
    void reset() throws LogicException {
        for (int i = 0; i < 9; i ++) {
            checklist.marksExists(i+1);
        }

        checklist.reset();

        Assertions.assertArrayEquals(new Boolean[] {false, false, false,
                                                    false, false, false,
                                                    false, false, false},
                                     checklist.getNumbers());
    }
}