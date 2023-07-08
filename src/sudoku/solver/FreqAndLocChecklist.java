package sudoku.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author juliabutler
 *
 * FreqAndLocChecklist (stands for frequency and location checklist) will keep track of the frequency of numbers in
 * a row, col, or block, as well as the cell they're located in by index (0-8).
 *
 * NOTE: when dealing with blocks, the block must be given as an 1D array rather than 2D
 */
public class FreqAndLocChecklist {

    private HashMap<Integer, Integer> numberFreqs = new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> numberLocs = new HashMap<>();

    public FreqAndLocChecklist() {}

    public HashMap<Integer, Integer> getNumberFreqs() {
        return numberFreqs;
    }

    public HashMap<Integer, ArrayList<Integer>> getNumberLocs() {
        return numberLocs;
    }

    public void addNumber(int number, int cellId) {
        incrementFrequency(number);
        addLocation(number, cellId);
    }

    public int getNumberFrequency(int number) {
        return numberFreqs.get(number);
    }

    public int[] getNumberLocations(int number) {
        int[] result = new int[numberLocs.get(number).size()];

        for (int i = 0; i < numberLocs.get(number).size(); i++) {
            result[i] = numberLocs.get(number).get(i);
        }

        return result;
    }

    public int[] getFork() {
        ArrayList<Integer> forkedNumbers = new ArrayList<>();

        for (Integer num : numberFreqs.keySet()) {
            if (numberFreqs.get(num) == 2) {
                forkedNumbers.add(num);
            }
        }

        for (int value = 0; value < forkedNumbers.size(); value++) {
            for (int otherValue = value + 1; otherValue < forkedNumbers.size(); otherValue++) {
                if (numberLocs.get(forkedNumbers.get(value)).equals(numberLocs.get(forkedNumbers.get(otherValue)))) {
                    return new int[] {forkedNumbers.get(value),
                                      forkedNumbers.get(otherValue),
                                      getNumberLocations(forkedNumbers.get(value))[0],
                                      getNumberLocations(forkedNumbers.get(value))[1]};
                }
            }
        }

        return null;
    }

    private void incrementFrequency(int number) {
        if (numberFreqs.containsKey(number)) {
            numberFreqs.put(number, numberFreqs.get(number) + 1);
        } else {
            numberFreqs.put(number, 1);
        }
    }

    private void addLocation(int number, int cellId) {
        if (numberLocs.containsKey(number)) {
            ArrayList<Integer> locations = numberLocs.get(number);
            locations.add(cellId);

            numberLocs.put(number, locations);
        } else {
            numberLocs.put(number, new ArrayList<>(Collections.singletonList(cellId)));
        }
    }

}
