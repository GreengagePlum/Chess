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
import javafx.scene.text.Text;
import javafx.util.Duration;
import me.erken.efe.chess.model.*;
import me.erken.efe.chess.view.Constants;

import java.util.*;

public class GameController {
    private final List<FadeTransition> fades = new ArrayList<>();
    private Game game;
    @FXML
    private Text moveCount;
    @FXML
    private GridPane gameGrid;
    private ImageView[][] gameGridImages;
    private Rectangle[][] gameGridRectangles;

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

    public void newGame() {
        game = new Game();
        loadGame();
    }

    private void loadPiece(int x, int y, PieceType pt) {
        String pathPre = Constants.SpriteMap.getSprite(pt);
        String pathToSprite;
        if (pathPre == null) {
            pathToSprite = null;
        } else {
            pathToSprite = Objects.requireNonNull(getClass().getResource(Constants.SpriteMap.getSprite(pt))).toString();
        }
        gameGridImages[y][x].setImage((pathToSprite == null) ? null : new Image(pathToSprite));
    }

    private void loadPieces(List<Coordinates> concernedCoords) {
        for (Coordinates c :
                concernedCoords) {
            loadPiece(c.x, c.y, game.getPieceType(c.x, c.y));
        }
    }

    private void loadHighlight(int x, int y, SquareState sq) {
        gameGridRectangles[y][x].setFill(Constants.ColorPalette.getColor(sq));
        gameGridRectangles[y][x].setOpacity(Constants.ColorPalette.getOpacity(sq));
    }

    private void loadAllHighlights() {
        for (int y = 0; y < Board.HEIGHT; y++) {
            for (int x = 0; x < Board.WIDTH; x++) {
                if (gameGridRectangles[y][x].getFill() != Constants.ColorPalette.RED) {
                    loadHighlight(x, y, game.getSquareState(x, y));
                }
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
        updateMoveCount();
    }

    private void updateMoveCount() {
        moveCount.setText(String.valueOf(game.getMoveCount()));
    }

    private void makeSelection(int x, int y) {
        try {
            game.makeSelection(x, y);
        } catch (EndOfGameException e) {
            endGame();
        } finally {
            for (FadeTransition fade :
                    fades) {
                if (game.getSquareState(getColumnIndex(fade.getNode().getParent()), getRowIndex(fade.getNode().getParent())) != SquareState.NORMAL) {
                    fade.jumpTo("end");
                }
            }
            loadAllHighlights();
        }
    }

    private void makeMove(int x, int y, String rank) {
        try {
            loadPieces(game.makeMove(x, y, rank));
            if (game.isEnded()) {
                endGame();
            }
        } catch (IllegalMoveException e) {
            gameGridRectangles[y][x].setFill(Constants.ColorPalette.RED);
            FadeTransition fade = new FadeTransition(Duration.seconds(1), gameGridRectangles[y][x]);
            fade.setFromValue(1d);
            fade.setToValue(0d);
            fade.setOnFinished(event -> {
                fades.remove(fade);
                loadHighlight(x, y, game.getSquareState(x, y));
            });
            fades.add(fade);
            fade.play();
        } catch (PawnPromotionException e) {
            makeMove(x, y, pawnPromotionChoice());
        } catch (EndOfGameException e) {
            endGame();
        } finally {
            updateMoveCount();
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
                makeMove(x, y, null);
            }
        } else {
            makeSelection(x, y);
        }
    }

    public void undoMove() {
        loadPieces(game.undoMove());
        updateMoveCount();
        loadAllHighlights();
    }

    public void redoMove() {
        try {
            loadPieces(game.redoMove());
        } catch (IllegalMoveException e) {
            // can be improved but technically impossible to get here anyway
            throw new RuntimeException(e);
        } finally {
            updateMoveCount();
            loadAllHighlights();
        }
    }

    private String pawnPromotionChoice() {
        // Create an Alert dialog with CONFIRMATION AlertType
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Promotion de pion");
        alert.setHeaderText("Quelle promotion voulez-vous ?");
        alert.setContentText("Faites votre choix de pièce");

        // Add two buttons to the Alert dialog
        ButtonType buttonTypeQueen = new ButtonType("Dame");
        ButtonType buttonTypeRook = new ButtonType("Tour");
        ButtonType buttonTypeBishop = new ButtonType("Fou");
        ButtonType buttonTypeKnight = new ButtonType("Cavalier");

        alert.getButtonTypes().setAll(buttonTypeQueen, buttonTypeRook, buttonTypeBishop, buttonTypeKnight);
        Optional<ButtonType> choice = alert.showAndWait();
        String result = choice.map(ButtonType::getText).orElse(null);
        if (result == null) {
            return null;
        }
        return switch (result) {
            case "Dame" -> Queen.class.getSimpleName();
            case "Tour" -> Rook.class.getSimpleName();
            case "Fou" -> Bishop.class.getSimpleName();
            case "Cavalier" -> Knight.class.getSimpleName();
            default -> result;
        };
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
                newGame();
            }
        });
    }

    public void newGameDialog() {
        // Create an Alert dialog with CONFIRMATION AlertType
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Recommencer la partie");
        alert.setContentText("Êtes-vous sûr de recommencer ?");

        // Add two buttons to the Alert dialog
        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Show the Alert dialog and wait for user response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                newGame();
            }
        });
    }
}
