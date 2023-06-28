package sudoku.board;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

/**
 * @author juliabutler
 *
 * The main responsibility of the SudokuGameFetcher class is to get a sudoku puzzle
 * for this program to solve.
 */
public class SudokuBoardFetcher {

    private JsonObject boardResponse;

    // Initializer
    public SudokuBoardFetcher() {
        try {
            this.getGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The init method will create and send a GET request to the Dosuku API.
     * The JSON response will be saved to a class property called boardResponse.
     *
     * @throws Exception
     */
    private void getGame() throws Exception {

        // Link to API's website: https://sudoku-api.vercel.app/

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://sudoku-api.vercel.app/api/dosuku"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //System.out.println(response.body());

        Gson g = new Gson();
        JsonElement element = g.fromJson (response.body(), JsonElement.class);
        this.boardResponse = element.getAsJsonObject();

        //System.out.println(boardResponse + "\n" + boardResponse.getClass());
    }

    /**
     * getBlankBoard will locate the blank Sudoku sudoku board in the JSON response
     * and return it as a 2D list to its callee
     *
     * @return the blank Sudoku board
     */
    public int[][] getBlankBoard() {
        JsonArray blank = boardResponse.getAsJsonObject("newboard")
                            .getAsJsonArray("grids")
                            .get(0).getAsJsonObject()
                            .get("value").getAsJsonArray();

        int[][] blankBoard = JsonArrayTo2DList(blank);

        return blankBoard;
    }

    /**
     * getSolution will locate the solution to the Sudoku board in the JSON response
     * and return it as a 2D list to its callee
     *
     * @return the solution to the Sudoku
     */
    public int[][] getSolution() {
        JsonArray solutions = boardResponse.getAsJsonObject("newboard")
                            .getAsJsonArray("grids")
                            .get(0).getAsJsonObject()
                            .get("solution").getAsJsonArray();

        int[][] solutionBoard = JsonArrayTo2DList(solutions);

        //System.out.println(solutionBoard);

        return solutionBoard;
    }

    /**
     * getDifficulty will locate the difficulty rating in the JSON repsonse and
     * will return it as a string to its callee
     *
     * @return
     */
    public String getDifficulty() {
        return boardResponse.getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0).getAsJsonObject()
                .get("difficulty").getAsString();
    }

    /**
     * This private method JsonArrayToArrayList takes a JsonArray object as a
     * parameter and will convert it to a 2D integer list
     *
     * @param board as a JsonArray
     * @return the sudoku board as a 2D list
     */
    private int[][] JsonArrayTo2DList(JsonArray board) {
        int[][] resultBoard = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                resultBoard[i][j] = board.get(i).getAsJsonArray().get(j).getAsInt();
            }
        }

        //System.out.println(deepToString(resultBoard));

        return resultBoard;
    }
}
