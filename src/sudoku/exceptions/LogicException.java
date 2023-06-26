package sudoku.exceptions;

/**
 * @author juliabutler
 *
 * LogicException means that there was a problem inserting a number into the Sudoku board due to some
 * kind of logic error. Specifically, this could be indicate that the number already exists in the row,
 * column, or third.
 */
public class LogicException extends Exception {
    public LogicException(String errorMessage) {
        super(errorMessage);
    }
}
