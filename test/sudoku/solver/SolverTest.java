package sudoku.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class SolverTest {

    ArrayList<ArrayList<Integer>> solution;

    @BeforeEach
    void createSolution() {
        solution = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        solution.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        solution.add((ArrayList<Integer>) row.clone());
    }

    @Test
    void initializationFetchesBoard() {
        Solver solver = new Solver();

        Assertions.assertNotNull(solver.getBoard());
    }

    @Test
    void checkMissingOneRowTrue() {
        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 0, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(true);

        Assertions.assertEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneColTrue() {
        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 0));
        blankBoard.add((ArrayList<Integer>) row.clone());

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(false);

        Assertions.assertEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneRowFalse() {
        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(true);

        Assertions.assertEquals(solution, solver.getBoard());
    }

    @Test
    void checkMissingOneColFalse() {
        ArrayList<ArrayList<Integer>> blankBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> row;

        row = new ArrayList<Integer>(Arrays.asList(8, 2, 7, 1, 5, 4, 3, 9, 6));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(9, 6, 5, 3, 2, 7, 1, 4, 8));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(3, 4, 1, 6, 8, 9, 7, 5, 2));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(5, 9, 3, 4, 6, 8, 2, 7, 1));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(4, 7, 2, 5, 1, 3, 6, 8, 9));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(6, 1, 8, 9, 7, 2, 4, 3, 5));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(7, 8, 6, 2, 3, 5, 9, 1, 4));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(1, 5, 4, 7, 9, 6, 8, 2, 3));
        blankBoard.add((ArrayList<Integer>) row.clone());
        row = new ArrayList<Integer>(Arrays.asList(2, 3, 9, 8, 4, 1, 5, 6, 7));
        blankBoard.add((ArrayList<Integer>) row.clone());

        Solver solver = new Solver(blankBoard);
        solver.checkMissingOne(false);

        Assertions.assertEquals(solution, solver.getBoard());
    }

}