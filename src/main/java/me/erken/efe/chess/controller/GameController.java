package me.erken.efe.chess.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import me.erken.efe.chess.model.*;
import me.erken.efe.chess.view.Constants;

import java.util.ListIterator;

public class GameController {
    private Game game;
    @FXML
    private GridPane gameGrid;
    private ImageView[][] gameGridImages;
    private Rectangle[][] gameGridRectangles;
    private FadeTransition fade;

    public GameController() {
        game = new Game();
    }

    public void setUpController() {
        ListIterator<Node> iter = gameGrid.getChildren().listIterator();
        gameGridImages = new ImageView[Board.HEIGHT][Board.WIDTH];
        gameGridRectangles = new Rectangle[Board.HEIGHT][Board.WIDTH];
        for (int y = 0; y < Board.HEIGHT && iter.hasNext(); y++) {
            for (int x = 0; x < Board.WIDTH && iter.hasNext(); x++) {
                for (Node node :
                        ((StackPane) iter.next()).getChildren()) {
                    if (node instanceof Rectangle) {
                        gameGridRectangles[y][x] = (Rectangle) node;
                    } else if (node instanceof ImageView) {
                        gameGridImages[y][x] = (ImageView) node;
                    }
                }
            }
        }
        loadGame();
    }

    private void loadPiece(int x, int y, PieceType pt) {
        String pathToSprite = Constants.SpriteMap.getSprite(pt);
        gameGridImages[y][x].setImage((pathToSprite == null) ? null : new Image(pathToSprite));
    }

    private void loadHighlight(int x, int y, SquareState sq) {
        gameGridRectangles[y][x].setFill(Constants.ColorPalette.getColor(sq));
        gameGridRectangles[y][x].setOpacity(Constants.ColorPalette.getOpacity(sq));
    }

    private void loadAllHighlights() {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                loadHighlight(x, y, game.getSquareState(x, y));
            }
        }
    }

    private void loadGame() {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                loadPiece(x, y, game.getPieceType(x, y));
                loadHighlight(x, y, game.getSquareState(x, y));
            }
        }
    }

    private void makeSelection(int x, int y) {
        try {
            game.makeSelection(x, y);
        } catch (EndOfGameException e) {
            endGame();
        } finally {
            if (fade != null && game.getSquareState(getColumnIndex(fade.getNode().getParent()), getRowIndex(fade.getNode().getParent())) != SquareState.NORMAL) {
                fade.stop();
                fade = null;
            }
            loadAllHighlights();
        }
    }

    private void makeMove(int x, int y) {
        try {
            int oldX = game.selectionX();
            int oldY = game.selectionY();
            game.makeMove(x, y);
            loadPiece(oldX, oldY, game.getPieceType(oldX, oldY));
            loadPiece(x, y, game.getPieceType(x, y));
            if (game.isEnded()) {
                endGame();
            }
        } catch (IllegalMoveException e) {
            gameGridRectangles[y][x].setFill(Constants.ColorPalette.RED);
            fade = new FadeTransition(Duration.seconds(1), gameGridRectangles[y][x]);
            fade.setFromValue(1d);
            fade.setToValue(0d);
            fade.play();
        } catch (EndOfGameException e) {
            endGame();
        } finally {
            loadAllHighlights();
        }
    }

    private int getColumnIndex(Node node) {
        Integer tmpX = GridPane.getColumnIndex(node);
        return (tmpX == null) ? 0 : tmpX;
    }

    private int getRowIndex(Node node) {
        Integer tmpY = GridPane.getRowIndex(node);
        return (tmpY == null) ? 0 : tmpY;
    }

    public void selectOrMove(MouseEvent event) {
        int x = getColumnIndex((StackPane) event.getSource());
        int y = getRowIndex((StackPane) event.getSource());
        if (game.selectionExists()) {
            if (game.getPieceType(x, y) != PieceType.NONE && game.getPieceColor(x, y) == game.getCurrentPlayerColor()) {
                makeSelection(x, y);
            } else {
                makeMove(x, y);
            }
        } else {
            makeSelection(x, y);
        }
    }

    private void endGame() {
        String endCause = Constants.EndGameExplanations.getExplanation(game.endReason());

        // Create an Alert dialog with CONFIRMATION AlertType
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin du jeu");
        alert.setHeaderText(endCause);
        alert.setContentText("Voulez-vous recommencer ?");

        // Add two buttons to the Alert dialog
        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Show the Alert dialog and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                game = new Game();
                loadGame();
            }
        });
    }
}
