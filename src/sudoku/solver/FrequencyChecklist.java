package sudoku.solver;

import sudoku.exceptions.LogicException;

/**
 * @author juliabutler
 *
 * The FrequencyChecklist class is a checklist that represents the frequency of a number. It is used by the Solver
 * and will be used to keep track of the number of possible numbers in a row, col, or block.
 *
 * The indexes represent the numbers 1-9 as follows:
 * Index 0 -> 1
 * Index 1 -> 2
 * Index 2 -> 3
 * Index 3 -> 4
 */
public class FrequencyChecklist {

    private final int[] numFrequency = new int[9];

    public FrequencyChecklist() {
        // The numFrequency list should be instantiated with 0s for each number
        for (int i = 0; i < 9; i ++) {
            numFrequency[i] = 0;
        }
    }

    public int[] getFreq() {
        return numFrequency;
    }

    /**
     * incrementFreq will increase the frequency of a number
     *
     * @param num the number whose frequency is to be incremented
     */
    public void incrementFreq(int num) {
        numFrequency[num - 1]++;
    }

    /**
     * decrementFreq will decrement the frequency of a number. If the frequency is already 0, then it will set the
     * freq to 0.
     *
     * @param num the number whose frequency is to be decremented
     */
    public void decrementFreq(int num) {
        if (numFrequency[num - 1] - 1 < 0) {
            numFrequency[num - 1] = 0;
        } else {
            numFrequency[num - 1]--;
        }
    }

    /**
     * decrementAllFreqs will decrement the frequencies of all the numbers
     */
    public void decrementAllFreqs() {
        for (int i = 1; i < 10; i++) {
            decrementFreq(i);
        }
    }

    /**
     * Determines if there is a number with a frequency of one; meaning that there is only one number possible
     *
     * @return true or false (there is only one number possible)
     */
    public boolean freqIsOne() {
        for (int i = 0; i < 9; i++) {
            if (numFrequency[i] == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * getNumWithFreqOfOne will find the number with a frequency of 1 in the checklist and return (index + 1),
     * which is the number that cell represents.
     *
     * @return the number with the lowest frequency
     */
    public int getNumWithFreqOfOne() {
        for (int i = 0; i < 9; i++) {
            if (numFrequency[i] == 1) {
                return i + 1;
            }
        }

        return 0;
    }

    /**
     * Sets all the frequencies back to 0
     */
    public void reset() {
        for (int i = 0; i < 9; i ++) {
            numFrequency[i] = 0;
        }
    }

}
