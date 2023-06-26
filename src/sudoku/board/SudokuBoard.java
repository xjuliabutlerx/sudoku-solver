package sudoku.board;

import java.util.ArrayList;

public class SudokuBoard {

    private ArrayList<ArrayList<Integer>> board;
    private ArrayList<ArrayList<Integer>> solution;
    private String difficulty;

    public SudokuBoard() {}

    public boolean fetchBoard() {
        try {
            SudokuBoardFetcher fetcher = new SudokuBoardFetcher();
            setBoard(fetcher.getBlankBoard());
            setSolution(fetcher.getSolution());
            setDifficulty(fetcher.getDifficulty());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setBoard(ArrayList<ArrayList<Integer>> blankBoard) {
        this.board = blankBoard;
    }

    public void setSolution(ArrayList<ArrayList<Integer>> solutionBoard) {
        this.solution = solutionBoard;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

        //System.out.println(result);

        return result;
    }

    public ArrayList<ArrayList<Integer>> getSolution() {
        return solution;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getCell(int row, int col) {
        return board.get(row).get(col);
    }

    /**
     * This method will return a particular row of the Sudoku board. The rows have ids from 0-8.
     * The top row is 0 and the bottom row is 8.
     *
     * @param id
     * @return an ArrayList of the row requested
     */
    public ArrayList<Integer> getRowById(int id) {
        return board.get(id);
    }

    /**
     * This method will return a particular col of the Sudoku board. The cols have ids from 0-8.
     * The left-most row is 0 and the right-most col is 8.
     *
     * @param id
     * @return an ArrayList of the col requested
     */
    public ArrayList<Integer> getColById(int id) {
        return getBoardByCols().get(id);
    }

    /**
     * This method will return 9 digits in a 3x3 ArrayList that corresponds to a block in the
     * Sudoku board. The blocks' indices are as follows:
     *
     * Block 1 - Rows 0-2, Cols 0-2 (top left block)
     * Block 2 - Rows 0-2, Cols 3-5
     * Block 3 - Rows 0-2, Cols 6-8 (top right block)
     * Block 4 - Rows 3-5, Cols 0-2
     * Block 5 - Rows 3-5, Cols 3-5 (center block)
     * Block 6 - Rows 3-5, Cols 6-8
     * Block 7 - Rows 6-8, Cols 0-2 (bottom left block)
     * Block 8 - Rows 6-8, Cols 3-5
     * Block 9 - Rows 6-8, Cols 6-8 (bottom right block)
     *
     * @return a 3x3 ArrayList
     */
    public ArrayList<ArrayList<Integer>> getBlockById(int id) {
        ArrayList<ArrayList<Integer>> block = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> auxRow = new ArrayList<Integer>();

        int initRow = 0;
        int finalRow = 0;
        int initCol = 0;
        int finalCol = 0;

        if (id < 1 || id > 9) {
            return null;
        }

        switch(id) {
            case 1:
                finalRow = 2;
                finalCol = 2;
                break;
            case 2:
                finalRow = 2;
                initCol = 3;
                finalCol = 5;
                break;
            case 3:
                finalRow = 2;
                initCol = 6;
                finalCol = 8;
                break;
            case 4:
                initRow = 3;
                finalRow = 5;
                finalCol = 2;
                break;
            case 5:
                initRow = 3;
                finalRow = 5;
                initCol = 3;
                finalCol = 5;
                break;
            case 6:
                initRow = 3;
                finalRow = 5;
                initCol = 6;
                finalCol = 8;
                break;
            case 7:
                initRow = 6;
                finalRow = 8;
                finalCol = 2;
                break;
            case 8:
                initRow = 6;
                finalRow = 8;
                initCol = 3;
                finalCol = 5;
                break;
            case 9:
                initRow = 6;
                finalRow = 8;
                initCol = 6;
                finalCol = 8;
                break;
        }

        for (int r = initRow; r <= finalRow; r++) {
            for (int c = initCol; c <= finalCol; c++) {
                auxRow.add(getCell(r, c));
            }
            block.add((ArrayList<Integer>) auxRow.clone());
            auxRow.clear();
        }

        System.out.println(block.toString());

        return block;
    }

    public boolean writeNumber(int num, int row, int col) {
        if (num < 1 || num > 9 ||
            row < 0 || row > 8 ||
            col < 0 || col > 8) {
            return false;
        }

        board.get(row).set(col, num);

        return true;
    }

    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i).get(j) == 0) {
                    output += "_ ";
                    //System.out.print("_ ");
                } else {
                    output += board.get(i).get(j) + " ";
                    //System.out.print(board.get(i).get(j) + " ");
                }
            }
            output += "\n";
            //System.out.println();
        }

        return output;
    }

}
