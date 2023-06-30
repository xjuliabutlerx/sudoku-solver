package sudoku.boards;

import java.util.ArrayList;

public class PossibilityList {
    private final ArrayList<Integer> possibilities = new ArrayList<Integer>(){};

    public PossibilityList() {
        for (int i = 1; i < 10; i++) {
            possibilities.add(i);
        }
    }

    public int[] getPossibilities() {
        int[] poss = new int[possibilities.size()];

        for (int i = 0; i < possibilities.size(); i++) {
            poss[i] = possibilities.get(i);
        }

        return poss;
    }

    public boolean isNumPossible(int num) {
        for (int i = 0; i < possibilities.size(); i++) {
            if (possibilities.get(i) == num) {
                return true;
            }
        }
        return false;
    }

    public void remove(int num) {
        int originalLength = possibilities.size();

        for (int i = 0; i < originalLength; i++) {
            if (possibilities.get(i) == num) {
                possibilities.remove(i);
                originalLength--;
            }
        }
    }

    public boolean onlyOneNumPossible() {
        if (possibilities.size() == 1) {
            return true;
        }
        return false;
    }

    public void clear() {
        possibilities.clear();
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < possibilities.size(); i++) {
            result += possibilities.get(i).toString() + " ";
        }

        return result;
    }
}
