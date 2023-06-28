package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolverTest {

    int[][] solution = new int[9][9];

    @BeforeEach
    void createSolution() {
        solution[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        solution[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        solution[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        solution[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        solution[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        solution[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        solution[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        solution[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        solution[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};
    }

    @Test
    void initializationFetchesBoard() {
        Solver solver = new Solver();

        Assertions.assertNotNull(solver.getBoard());
    }

    @Test
    void checkMissingOneRowTrue() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 0, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(true);

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneColTrue() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 0};

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(false);

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneRowFalse() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(true);

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneColFalse() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(false);

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

}