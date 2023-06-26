package sudoku.board;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.google.gson.*;

/**
 * @author juliabutler
 *
 * The main responsibility of the SudokuGameFetcher class is to get a sudoku puzzle
 * for this program to solve.
 */
public class SudokuBoardFetcher {

    private JsonObject boardResponse;
    private JsonArray blank;
    private ArrayList<ArrayList<Integer>> blankBoard;
    private JsonArray solutions;
    private ArrayList<ArrayList<Integer>> solutionBoard;

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
     * getBlankBoard will locate the blank Sudoku sudoku.board in the JSON response
     * and return it as an ArrayList to its callee
     *
     * @return the blank Sudoku sudoku.board
     */
    public ArrayList<ArrayList<Integer>> getBlankBoard() {
        this.blank = boardResponse.getAsJsonObject("newboard")
                            .getAsJsonArray("grids")
                            .get(0).getAsJsonObject()
                            .get("value").getAsJsonArray();

        //System.out.println(sudoku.board);

        this.blankBoard = JsonArrayToArrayList(blank);

        //System.out.println(blankBoard);

        return blankBoard;
    }

    /**
     * getSolution method will locate the blank Sudoku sudoku.board in the JSON response
     * and return it as an ArrayList to its callee
     *
     * @return the solved Sudoku sudoku.board
     */
    public ArrayList<ArrayList<Integer>> getSolution() {
        this.solutions = boardResponse.getAsJsonObject("newboard")
                .getAsJsonArray("grids")
                .get(0).getAsJsonObject()
                .get("solution").getAsJsonArray();

        this.solutionBoard = JsonArrayToArrayList(solutions);

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
     * parameter and will convert it to a 2D integer array
     *
     * @param board as a JsonArray
     * @return the sudoku.board as an ArrayList
     */
    private ArrayList<ArrayList<Integer>> JsonArrayToArrayList(JsonArray board) {
        ArrayList<ArrayList<Integer>> resultBoard = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> aux = new ArrayList<Integer>();

        // Since I'm working in the context of a general Sudoku sudoku.board
        // I used 9 as the parameter for row and col length
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                aux.add(board.get(i).getAsJsonArray().get(j).getAsInt());
            }
            //System.out.println(aux);
            resultBoard.add((ArrayList<Integer>) aux.clone());
            aux.clear();
        }

        return resultBoard;
    }
}
