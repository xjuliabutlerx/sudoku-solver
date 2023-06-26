package sudoku.solver;

import sudoku.exceptions.LogicException;

/**
 * @author juliabutler
 *
 * This class will be used by the Solver class and will keep a record of which numbers 1-9 exist from
 * the area of the board that the Solver is looking at.
 */
public class NumberChecklist {
    // The indices of the Boolean list correspond with the number it represents
    // The initial list shows that all numbers 1-9 do not exist
    Boolean[] numbers = {false, false, false, false, false, false, false, false, false};

    public NumberChecklist() {}

    /**
     * As the Solver looks through the row or col, it will mark the index representing the number
     * as true.
     *
     * @param number to be marked as exists
     * @throws LogicException to be thrown if the number has already been marked as true
     */
    public void marksExists (int number) throws LogicException {
        int index = number - 1;

        if (!numbers[index]) {
            numbers[index] = true;
        } else {
            throw new LogicException("The number already exists!");
        }
    }

    public boolean missingOne() {
        int falseCount = 0;

        for (Boolean number : numbers) {
            if (!number) {
                falseCount++;
            }

            if (falseCount > 1) {
                return false;
            }
        }

        return true;
    }

    public int getMissingNum() {
        for (int i = 0; i < numbers.length; i++) {
            if (!numbers[i]) {
                return (i+1);
            }
        }
        return 0;
    }

    public void reset() {
        numbers = new Boolean[] {false, false, false, false, false, false, false, false, false};
    }
}
