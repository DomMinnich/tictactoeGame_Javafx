import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private static final int GRID_SIZE = 3;
    private static final int BUTTON_SIZE = 100;

    private Button[][] buttonGrid = new Button[GRID_SIZE][GRID_SIZE];
    private boolean xTurn = true;
    private Text turnText = new Text();

    @Override
    public void start(Stage primaryStage) {
        // Create a grid of buttons for the game board
        GridPane gridPane = new GridPane();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Button button = new Button();
                button.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);
                button.setFont(Font.font("Arial", FontWeight.BOLD, 40));
                button.setOnAction(event -> handleButtonClick(button));
                buttonGrid[row][col] = button;
                gridPane.add(button, col, row);
            }
        }

        // Create a text node to display whose turn it is
        turnText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        // set position of turn toext to down 200
        turnText.setTranslateY(140);
        updateTurnText();

        // Create a stack pane to center the grid and the turn text
        StackPane root = new StackPane();
        root.getChildren().addAll(gridPane, turnText);

        // Create the scene and show the stage
        Scene scene = new Scene(root, GRID_SIZE * BUTTON_SIZE, GRID_SIZE * BUTTON_SIZE + 50);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        int row = GridPane.getRowIndex(button);
        int col = GridPane.getColumnIndex(button);

        if (button.getText().isEmpty()) {
            button.setText(xTurn ? "X" : "O");
            xTurn = !xTurn;
            updateTurnText();
            checkForWin(row, col);
        }
    }

    private void updateTurnText() {
        turnText.setText(xTurn ? "X's turn" : "O's turn");
    }

    private void checkForWin(int row, int col) {
        String symbol = buttonGrid[row][col].getText();

        // Check for horizontal win
        boolean horizontalWin = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (!buttonGrid[row][i].getText().equals(symbol)) {
                horizontalWin = false;
                break;
            }
        }
        if (horizontalWin) {
            announceWinner(symbol);
            return;
        }

        // Check for vertical win
        boolean verticalWin = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (!buttonGrid[i][col].getText().equals(symbol)) {
                verticalWin = false;
                break;
            }
        }
        if (verticalWin) {
            announceWinner(symbol);
            return;
        }

        // Check for diagonal win
        if (row == col || row + col == GRID_SIZE - 1) {
            boolean diagonalWin1 = true;
            boolean diagonalWin2 = true;
            for (int i = 0; i < GRID_SIZE; i++) {
                if (!buttonGrid[i][i].getText().equals(symbol)) {
                    diagonalWin1 = false;
                }
                if (!buttonGrid[i][GRID_SIZE - 1 - i].getText().equals(symbol)) {
                    diagonalWin2 = false;
                }
            }

            if (diagonalWin1 || diagonalWin2) {
                announceWinner(symbol);
                return;
            }

            // this comment

        }
        // if no one wins
        boolean full = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (buttonGrid[i][j].getText().isEmpty()) {
                    full = false;
                }
            }
        }
        if (full) {
            turnText.setText("Draw!");
        }

    }

    private void announceWinner(String symbol) {
        turnText.setText(symbol + " wins!");
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttonGrid[row][col].setDisable(true);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
