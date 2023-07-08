package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sudoku.boards.PossibilitiesBoard;
import sudoku.boards.PossibilityList;

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
    void checkAndFillInMissingOneRowTrue() {
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
    void checkAndFillInMissingOneColTrue() {
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
    void checkAndFillInMissingOneRowFalse() {
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
    void checkAndFillInMissingOneColFalse() {
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

    @Test
    void checkAndFillInMissingBlocks() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 0, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 0};

        blankBoard[3] = new int[] {5, 0, 3, 4, 6, 0, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 0, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};

        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 0, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 0, 4, 1, 5, 0, 7};

        Solver solver = new Solver(blankBoard);
        solver.checkBlocks();

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void processOfEliminationEasy() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 0, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 0, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 0};

        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 0, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 0, 6, 8, 9};
        blankBoard[5] = new int[] {0, 1, 8, 9, 7, 2, 4, 3, 5};

        blankBoard[6] = new int[] {7, 8, 0, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 0, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 0, 7};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void processOfEliminationMedium() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 0, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 0, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 0, 9, 7, 5, 2};

        blankBoard[3] = new int[] {5, 9, 3, 0, 0, 0, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 0, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 0, 0, 0, 4, 3, 5};

        blankBoard[6] = new int[] {7, 8, 6, 2, 0, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 0, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 0, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void processOfEliminationDifficult() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 0, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 0, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 0, 9, 7, 5, 2};

        blankBoard[3] = new int[] {5, 9, 3, 0, 0, 0, 2, 7, 1};
        blankBoard[4] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        blankBoard[5] = new int[] {6, 1, 8, 0, 0, 0, 4, 3, 5};

        blankBoard[6] = new int[] {7, 8, 6, 2, 0, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 0, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 0, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.processOfEliminationByCell();

        Assertions.assertArrayEquals(solution, solver.getBoard());
    }

    @Test
    void analyzeRowPossibilitiesA() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        blankBoard[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 2, 0, 0};
        blankBoard[5] = new int[] {0, 0, 0, 0, 0, 2, 0, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {0, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        solver.analyzeRowColPossibilities(true);

        int[] solution = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 2};

        Assertions.assertArrayEquals(solution, solver.getBoard()[1]);
    }

    @Test
    void analyzeRowPossibilitiesB() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {5, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 5, 0, 0};

        blankBoard[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 2, 0, 0};
        blankBoard[5] = new int[] {0, 0, 0, 0, 0, 2, 0, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {0, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        solver.analyzeRowColPossibilities(true);
        solver.analyzeRowColPossibilities(true);

        int[] solution = new int[] {9, 7, 4, 3, 5, 1, 6, 8, 2};

        Assertions.assertArrayEquals(solution, solver.getBoard()[1]);
    }

    @Test
    void analyzeColPossibilitiesA() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        blankBoard[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 0, 0, 0};
        blankBoard[5] = new int[] {0, 0, 8, 0, 0, 2, 1, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {1, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        solver.analyzeRowColPossibilities(false);

        // Col 3
        int[] solution = new int[] {8, 3, 0, 5, 1, 0, 7, 4, 0};

        int[] col3 = new int[9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (c == 3) {
                    col3[r] = solver.getBoard()[r][c];
                }
            }
        }

        Assertions.assertArrayEquals(solution, col3);
    }

    @Test
    void analyzeColPossibilitiesB() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        blankBoard[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 0, 0, 0};
        blankBoard[5] = new int[] {0, 0, 8, 0, 0, 2, 1, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {1, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();

        solver.analyzeRowColPossibilities(false);
        solver.analyzeRowColPossibilities(false);

        // Col 3
        int[] solution = new int[] {8, 3, 0, 5, 1, 0, 7, 4, 2};

        int[] col3 = new int[9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (c == 3) {
                    col3[r] = solver.getBoard()[r][c];
                }
            }
        }

        Assertions.assertArrayEquals(solution, col3);
    }

    @Test
    void analyzeBlockPossibilitiesA() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        blankBoard[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 0, 0, 0};
        blankBoard[5] = new int[] {0, 0, 8, 0, 0, 2, 0, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {0, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.analyzeBlockPossibilities();

        int[][] block1sol = new int[3][3];

        block1sol[0] = new int[] {0, 0, 2};
        block1sol[1] = new int[] {0, 7, 4};
        block1sol[2] = new int[] {8, 0, 0};

        int[][] results = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                results[i][j] = solver.getBoard()[i][j];
            }
        }

        Assertions.assertArrayEquals(block1sol, results);
    }

    @Test
    void analyzeBlockPossibilitiesB() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        blankBoard[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        blankBoard[3] = new int[] {6, 0, 1, 5, 0, 0, 9, 0, 0};
        blankBoard[4] = new int[] {0, 0, 0, 0, 8, 0, 0, 0, 0};
        blankBoard[5] = new int[] {0, 0, 8, 0, 0, 2, 0, 0, 5};

        blankBoard[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        blankBoard[8] = new int[] {1, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.analyzeBlockPossibilities();
        solver.analyzeBlockPossibilities();

        int[][] block1sol = new int[3][3];

        block1sol[0] = new int[] {0, 0, 2};
        block1sol[1] = new int[] {0, 7, 4};
        block1sol[2] = new int[] {8, 1, 0};

        int[][] results = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                results[i][j] = solver.getBoard()[i][j];
            }
        }

        Assertions.assertArrayEquals(block1sol, results);
    }

    @Test
    void forkWithTwoCellsRow() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0};
        blankBoard[1] = new int[] {8, 0, 0, 6, 0, 0, 0, 3, 4};
        blankBoard[2] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};

        blankBoard[3] = new int[] {0, 1, 0, 0, 2, 9, 0, 0, 0};
        blankBoard[4] = new int[] {0, 9, 0, 0, 0, 7, 0, 0, 0};
        blankBoard[5] = new int[] {0, 2, 0, 0, 0, 0, 0, 0, 0};

        blankBoard[6] = new int[] {0, 0, 0, 0, 9, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        blankBoard[8] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.sudokuForkRowCol(true);
        solver.analyzeRowColPossibilities(true);

        int[] row1sol = new int[] {8, 0, 0, 6, 0, 2, 0, 3, 4};

        Assertions.assertArrayEquals(row1sol, solver.getBoard()[1]);
    }

    @Test
    void forkWithTwoCellsCol() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {0, 4, 0, 0, 0, 0, 0, 0, 0};
        blankBoard[1] = new int[] {0, 3, 0, 0, 0, 0, 0, 0, 0};
        blankBoard[2] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};

        blankBoard[3] = new int[] {0, 0, 0, 9, 7, 0, 0, 0, 0};
        blankBoard[4] = new int[] {1, 0, 0, 2, 0, 0, 9, 0, 0};
        blankBoard[5] = new int[] {0, 6, 0, 0, 0, 0, 0, 0, 0};

        blankBoard[6] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        blankBoard[7] = new int[] {0, 0, 0, 1, 9, 2, 0, 0, 0};
        blankBoard[8] = new int[] {0, 8, 0, 0, 0, 0, 0, 0, 0};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.sudokuForkRowCol(false);
        solver.analyzeRowColPossibilities(false);

        int[] col1sol = new int[] {4, 3, 0, 2, 0, 6, 0, 0, 8};

        int[] result = new int[9];

        for (int rowId = 0; rowId < 9; rowId++) {
            result[rowId] = solver.getBoard()[rowId][1];
        }

        Assertions.assertArrayEquals(col1sol, result);
    }

    @Test
    void forkWithTwoCellsBlock() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {4, 0, 0, 8, 0, 9, 1, 0, 0};
        blankBoard[1] = new int[] {0, 0, 7, 0, 0, 0, 0, 9, 0};
        blankBoard[2] = new int[] {9, 5, 0, 0, 2, 0, 0, 0, 7};

        blankBoard[3] = new int[] {1, 0, 0, 0, 9, 0, 0, 0, 3};
        blankBoard[4] = new int[] {3, 9, 2, 4, 0, 7, 8, 0, 0};
        blankBoard[5] = new int[] {6, 0, 0, 0, 3, 0, 0, 0, 9};

        blankBoard[6] = new int[] {7, 2, 0, 0, 8, 0, 0, 6, 0};
        blankBoard[7] = new int[] {0, 1, 0, 0, 0, 0, 2, 0, 0};
        blankBoard[8] = new int[] {0, 0, 3, 1, 0, 2, 0, 0, 4};

        Solver solver = new Solver(blankBoard);
        solver.processOfEliminationByCell();
        solver.sudokuForkBlock();
        solver.analyzeRowColPossibilities(false);

        int[] col1sol = new int[] {0, 0, 5, 0, 9, 0, 2, 1, 6};

        int[] result = new int[9];

        for (int rowId = 0; rowId < 9; rowId++) {
            result[rowId] = solver.getBoard()[rowId][1];
        }

        Assertions.assertArrayEquals(col1sol, result);
    }

    @Test
    void isSolvedTrue() {
        Solver solver = new Solver(solution);

        Assertions.assertTrue(solver.isSolved());
    }

    @Test
    void isSolvedFalseMissingNum() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 1, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 0, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);

        Assertions.assertFalse(solver.isSolved());
    }

    @Test
    void isSolvedFalseBadLogic() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {8, 2, 7, 1, 5, 4, 3, 9, 6};
        blankBoard[1] = new int[] {9, 6, 5, 3, 2, 7, 1, 4, 8};
        blankBoard[2] = new int[] {3, 4, 2, 6, 8, 9, 7, 5, 2};
        blankBoard[3] = new int[] {5, 9, 3, 4, 6, 8, 2, 7, 1};
        blankBoard[4] = new int[] {4, 7, 2, 5, 1, 3, 6, 8, 9};
        blankBoard[5] = new int[] {6, 1, 8, 9, 7, 2, 4, 3, 5};
        blankBoard[6] = new int[] {7, 8, 6, 2, 3, 5, 9, 1, 4};
        blankBoard[7] = new int[] {1, 5, 4, 7, 9, 6, 8, 2, 3};
        blankBoard[8] = new int[] {2, 3, 9, 8, 4, 1, 5, 6, 7};

        Solver solver = new Solver(blankBoard);

        Assertions.assertFalse(solver.isSolved());
    }

    @Test
    void updatePossBoard() {
        int[][] blankBoard = new int[9][9];

        blankBoard[0] = new int[] {4, 0, 0, 3, 1, 9, 0, 0, 6};
        blankBoard[1] = new int[] {0, 0, 1, 0, 0, 0, 9, 0, 0};
        blankBoard[2] = new int[] {0, 6, 7, 4, 0, 0, 0, 2, 1};

        blankBoard[3] = new int[] {7, 0, 0, 0, 5, 0, 0, 0, 4};
        blankBoard[4] = new int[] {0, 0, 0, 1, 4, 2, 0, 0, 0};
        blankBoard[5] = new int[] {2, 0, 0, 0, 7, 0, 0, 0, 8};

        blankBoard[6] = new int[] {0, 2, 0, 0, 0, 0, 0, 6, 0};
        blankBoard[7] = new int[] {0, 0, 4, 0, 0, 0, 8, 0, 0};
        blankBoard[8] = new int[] {1, 0, 0, 5, 0, 8, 0, 0, 7};

        Solver solver = new Solver(blankBoard);

        solver.updatePossBoard();

        PossibilitiesBoard possibilitiesBoard = solver.getPossibilitiesBoard();

        // Checking against row 6
        Assertions.assertArrayEquals(new int[] {3, 5, 8, 9}, possibilitiesBoard.getCellPossibility(6, 0));
        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(6, 1));
        Assertions.assertArrayEquals(new int[] {3, 5, 8, 9}, possibilitiesBoard.getCellPossibility(6, 2));
        Assertions.assertArrayEquals(new int[] {7, 9}, possibilitiesBoard.getCellPossibility(6, 3));
        Assertions.assertArrayEquals(new int[] {3, 9}, possibilitiesBoard.getCellPossibility(6, 4));
        Assertions.assertArrayEquals(new int[] {1, 3, 4, 7}, possibilitiesBoard.getCellPossibility(6, 5));
        Assertions.assertArrayEquals(new int[] {1, 3, 4, 5}, possibilitiesBoard.getCellPossibility(6, 6));
        Assertions.assertArrayEquals(new int[] {}, possibilitiesBoard.getCellPossibility(6, 7));
        Assertions.assertArrayEquals(new int[] {3, 5, 9}, possibilitiesBoard.getCellPossibility(6, 8));
    }

    @Test
    void solveEasySudoku() {
        // Link to Puzzle:
        // https://www.shutterstock.com/image-vector/vector-sudoku-puzzle-solution-very-260nw-1711421314.jpg
        int[][] puzzle = new int[9][9];

        puzzle[0] = new int[] {0, 2, 1, 3, 8, 0, 7, 0, 0};
        puzzle[1] = new int[] {0, 7, 5, 0, 0, 0, 8, 0, 0};
        puzzle[2] = new int[] {8, 3, 0, 0, 0, 0, 0, 6, 0};

        puzzle[3] = new int[] {7, 5, 0, 1, 0, 6, 9, 0, 0};
        puzzle[4] = new int[] {1, 9, 0, 0, 0, 0, 0, 3, 7};
        puzzle[5] = new int[] {0, 0, 2, 9, 0, 4, 0, 5, 8};

        puzzle[6] = new int[] {0, 8, 0, 0, 0, 0, 0, 1, 2};
        puzzle[7] = new int[] {0, 0, 3, 0, 0, 0, 5, 8, 0};
        puzzle[8] = new int[] {0, 0, 6, 0, 5, 8, 3, 7, 0};

        Solver solver = new Solver(puzzle);
        solver.start();

        int[][] easySol = new int[9][9];

        easySol[0] = new int[] {6, 2, 1, 3, 8, 9, 7, 4, 5};
        easySol[1] = new int[] {4, 7, 5, 2, 6, 1, 8, 9, 3};
        easySol[2] = new int[] {8, 3, 9, 5, 4, 7, 2, 6, 1};

        easySol[3] = new int[] {7, 5, 8, 1, 3, 6, 9, 2, 4};
        easySol[4] = new int[] {1, 9, 4, 8, 2, 5, 6, 3, 7};
        easySol[5] = new int[] {3, 6, 2, 9, 7, 4, 1, 5, 8};

        easySol[6] = new int[] {5, 8, 7, 6, 9, 3, 4, 1, 2};
        easySol[7] = new int[] {9, 4, 3, 7, 1, 2, 5, 8, 6};
        easySol[8] = new int[] {2, 1, 6, 4, 5, 8, 3, 7, 9};

        Assertions.assertArrayEquals(easySol, solver.getBoard());
    }

    @Test
    void solveMediumSudokuA() {
        // Link to Puzzle:
        // https://www.shutterstock.com/image-vector/vector-sudoku-puzzle-solution-medium-600w-1113614954.jpg
        int[][] puzzle = new int[9][9];

        puzzle[0] = new int[] {0, 0, 0, 8, 3, 2, 0, 9, 0};
        puzzle[1] = new int[] {0, 0, 0, 0, 0, 5, 7, 0, 6};
        puzzle[2] = new int[] {1, 0, 0, 6, 0, 0, 0, 0, 0};

        puzzle[3] = new int[] {3, 0, 0, 0, 0, 0, 0, 0, 0};
        puzzle[4] = new int[] {6, 7, 4, 0, 0, 0, 8, 5, 1};
        puzzle[5] = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 7};

        puzzle[6] = new int[] {0, 0, 0, 0, 0, 1, 0, 0, 5};
        puzzle[7] = new int[] {9, 0, 2, 5, 0, 0, 0, 0, 0};
        puzzle[8] = new int[] {0, 3, 0, 2, 7, 6, 0, 0, 0};

        Solver solver = new Solver(puzzle);
        solver.start();

        int[][] medSol = new int[9][9];

        medSol[0] = new int[] {7, 5, 6, 8, 3, 2, 1, 9, 4};
        medSol[1] = new int[] {2, 9, 3, 4, 1, 5, 7, 8, 6};
        medSol[2] = new int[] {1, 4, 8, 6, 9, 7, 5, 2, 3};

        medSol[3] = new int[] {3, 8, 1, 7, 5, 4, 9, 6, 2};
        medSol[4] = new int[] {6, 7, 4, 3, 2, 9, 8, 5, 1};
        medSol[5] = new int[] {5, 2, 9, 1, 6, 8, 3, 4, 7};

        medSol[6] = new int[] {4, 6, 7, 9, 8, 1, 2, 3, 5};
        medSol[7] = new int[] {9, 1, 2, 5, 4, 3, 6, 7, 8};
        medSol[8] = new int[] {8, 3, 5, 2, 7, 6, 4, 1, 9};

        Assertions.assertArrayEquals(medSol, solver.getBoard());
    }

    @Test
    void solveMediumSudokuB() {
        // Link to Puzzle:
        // https://www.shutterstock.com/image-vector/vector-sudoku-puzzle-solution-medium-260nw-1113614972.jpg
        int[][] puzzle = new int[9][9];

        puzzle[0] = new int[] {0, 0, 2, 8, 0, 0, 0, 1, 0};
        puzzle[1] = new int[] {0, 7, 4, 3, 0, 1, 0, 8, 0};
        puzzle[2] = new int[] {0, 0, 0, 0, 2, 4, 0, 0, 0};

        puzzle[3] = new int[] {6, 0, 0, 5, 0, 0, 9, 0, 0};
        puzzle[4] = new int[] {0, 0, 0, 0, 8, 0, 0, 0, 0};
        puzzle[5] = new int[] {0, 0, 8, 0, 0, 2, 0, 0, 5};

        puzzle[6] = new int[] {0, 0, 0, 7, 3, 0, 0, 0, 0};
        puzzle[7] = new int[] {0, 8, 0, 4, 0, 6, 7, 2, 0};
        puzzle[8] = new int[] {0, 4, 0, 0, 0, 8, 3, 0, 0};

        Solver solver = new Solver(puzzle);
        solver.start();

        int[][] medSol = new int[9][9];

        medSol[0] = new int[] {5, 6, 2, 8, 7, 9, 4, 1, 3};
        medSol[1] = new int[] {9, 7, 4, 3, 5, 1, 6, 8, 2};
        medSol[2] = new int[] {8, 1, 3, 6, 2, 4, 5, 9, 7};

        medSol[3] = new int[] {6, 2, 1, 5, 4, 7, 9, 3, 8};
        medSol[4] = new int[] {7, 5, 9, 1, 8, 3, 2, 6, 4};
        medSol[5] = new int[] {4, 3, 8, 9, 6, 2, 1, 7, 5};

        medSol[6] = new int[] {2, 9, 6, 7, 3, 5, 8, 4, 1};
        medSol[7] = new int[] {3, 8, 5, 4, 1, 6, 7, 2, 9};
        medSol[8] = new int[] {1, 4, 7, 2, 9, 8, 3, 5, 6};

        Assertions.assertArrayEquals(medSol, solver.getBoard());
    }

}