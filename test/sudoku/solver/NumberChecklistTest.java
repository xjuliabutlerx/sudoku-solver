package sudoku.solver;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.LogicException;

class NumberChecklistTest {

    @Test
    void marksExists() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        checklist.marksExists(4);
        Assert.assertArrayEquals(new Boolean[] {false, false, false,
                                                true, false, false,
                                                false, false, false},
                                checklist.numbers);
    }

    @Test
    void marksExistsError() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        checklist.marksExists(4);
        Assert.assertEquals(checklist.numbers[3], true);
        Assert.assertThrows(LogicException.class, () -> checklist.marksExists(4));
    }

    @Test
    void missingOneTrueA() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 8; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(true, checklist.missingOne());
    }

    @Test
    void missingOneTrueB() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 1; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(true, checklist.missingOne());
    }

    @Test
    void missingOneTrueC() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 9; i++) {
            if (i != 4) {
                checklist.marksExists(i+1);
            }
        }

        Assert.assertEquals(true, checklist.missingOne());
    }

    @Test
    void missingOneFalseA() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 7; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(false, checklist.missingOne());
    }

    @Test
    void missingOneFalseB() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 2; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(false, checklist.missingOne());
    }

    @Test
    void getMissingNumA() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 8; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(true, checklist.missingOne());
        Assert.assertEquals(9, checklist.getMissingNum());
    }

    @Test
    void getMissingNumB() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 1; i < 9; i++) {
            checklist.marksExists(i+1);
        }

        Assert.assertEquals(true, checklist.missingOne());
        Assert.assertEquals(1, checklist.getMissingNum());
    }

    @Test
    void getMissingNumC() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 9; i++) {
            if (i != 4) {
                checklist.marksExists(i+1);
            }
        }

        Assert.assertEquals(true, checklist.missingOne());
        Assert.assertEquals(5, checklist.getMissingNum());
    }

    @Test
    void reset() throws LogicException {
        NumberChecklist checklist = new NumberChecklist();
        for (int i = 0; i < 9; i ++) {
            checklist.marksExists(i+1);
        }

        checklist.reset();

        Assertions.assertArrayEquals(new Boolean[] {false, false, false,
                                                    false, false, false,
                                                    false, false, false},
                                     checklist.numbers);
    }
}