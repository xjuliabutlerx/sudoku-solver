package sudoku.board;

import java.util.ArrayList;

public class SudokuBoard {

    ArrayList<ArrayList<Integer>> board;
    ArrayList<ArrayList<Integer>> solution;
    String difficulty;

    public SudokuBoard() {
        try {
            SudokuBoardFetcher fetcher = new SudokuBoardFetcher();
            this.board = fetcher.getBlankBoard();
            this.solution = fetcher.getSolution();
            this.difficulty = fetcher.getDifficulty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    public ArrayList<ArrayList<Integer>> getBoardByCols() {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> aux = new ArrayList<Integer>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                aux.add(board.get(j).get(i));
            }
            result.add((ArrayList<Integer>) aux.clone());
            aux.clear();
        }

        System.out.println(result);

        return result;
    }

    public ArrayList<ArrayList<Integer>> getSolution() {
        return solution;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean writeNumber(int num, int row, int col) {
        if (num < 1 || num > 9) {
            return false;
        }

        board.get(row).set(col, num);

        return true;
    }

    public void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i).get(j) == 0) {
                    System.out.print("_ ");
                } else {
                    System.out.print(board.get(i).get(j) + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i).get(j) != solution.get(i).get(j)) {
                    return false;
                }
            }
        }
        return true;
    }

}
