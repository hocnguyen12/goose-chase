package fr.ensicaen.ecole.genielogiciel.view;

import fr.ensicaen.ecole.genielogiciel.LoginMain;
import fr.ensicaen.ecole.genielogiciel.model.character.AbstractFactoryStudent;
import fr.ensicaen.ecole.genielogiciel.presenter.GamePresenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


import static java.util.Objects.isNull;

public final class GameView {
    private GamePresenter _gamePresenter;
    private Stage _stage;
    @FXML
    private TilePane container ;
    @FXML
    private GridPane grid_anchor;
    private String _player1;

    private String _player2;
    private static String _path;

    private Color[] colorList_basicBoard = new Color[]{
            Color.TOMATO, Color.WHITE, Color.PURPLE, Color.WHITE, Color.PINK, Color.ORANGE, Color.BROWN, Color.TURQUOISE,
            Color.WHITE, Color.YELLOW, Color.WHITE, Color.PURPLE, Color.BROWN, Color.GREY, Color.PINK, Color.ORANGE,
            Color.WHITE, Color.TURQUOISE, Color.YELLOW, Color.DARKGREEN, Color.WHITE, Color.PURPLE, Color.PINK, Color.ORANGE,
            Color.WHITE, Color.TURQUOISE, Color.WHITE, Color.YELLOW, Color.WHITE, Color.PINK, Color.WHITE,Color.LIGHTGREEN,
            Color.GREY, Color.WHITE, Color.PURPLE, Color.WHITE, Color.YELLOW, Color.GOLD, Color.GOLD,Color.GOLD,
            Color.PINK, Color.WHITE, Color.LIGHTBLUE, Color.WHITE, Color.WHITE, Color.YELLOW, Color.WHITE,Color.TURQUOISE,
            Color.GREY, Color.WHITE, Color.ORANGE, Color.WHITE, Color.MAGENTA, Color.WHITE, Color.YELLOW,Color.WHITE,
            Color.TURQUOISE, Color.WHITE, Color.BLACK, Color.PURPLE, Color.WHITE, Color.ORANGE, Color.WHITE, Color.TEAL};

    private Color[] colorList_randomboard = new Color[]{
            Color.TOMATO, Color.PURPLE, Color.WHITE, Color.TURQUOISE, Color.PINK, Color.BROWN, Color.ORANGE, Color.WHITE,
            Color.PURPLE, Color.PINK, Color.WHITE, Color.TURQUOISE, Color.WHITE, Color.YELLOW, Color.ORANGE, Color.PINK,
            Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.YELLOW, Color.PINK, Color.WHITE, Color.WHITE,
            Color.WHITE, Color.LIGHTBLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GOLD, Color.WHITE, Color.WHITE,
            Color.PINK, Color.WHITE, Color.GREY, Color.WHITE, Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE,
            Color.LIGHTBLUE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GREY, Color.WHITE, Color.WHITE, Color.YELLOW,
            Color.LIGHTGREEN, Color.WHITE, Color.WHITE, Color.WHITE, Color.DARKGREEN, Color.WHITE, Color.WHITE, Color.LIGHTBLUE,
            Color.WHITE, Color.WHITE, Color.MAGENTA, Color.WHITE, Color.WHITE, Color.BLACK, Color.WHITE, Color.TEAL
    };
    private static final int MAX_LENGTH = 8;
    private static final int MAX_HEIGHT = 8;
    private boolean isPlayer1 = true;

    @FXML
    private Button _restart;

    @FXML
    private Button _lancer;

    private ArrayList<Integer> dice;
    private int diceTotal;
    private ArrayList<String> squareNames;
    private List<Integer> positions;
    private int skilllevel1;
    private int skilllevel2;
    private static ArrayList<String> _hardskill = new ArrayList<>(Arrays.asList("Prepa", "Prepa"));
    private ArrayList<String> _softSkills;

    public static GameView createView(String nickName1, String path, ArrayList<String> hardskill) throws IOException {
        _hardskill = hardskill;
        _path = path;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GameView.class.getResource("Board.fxml"));
        Parent root = fxmlLoader.load();
        final GameView view = fxmlLoader.getController();
        fxmlLoader.setController(view);
        Scene scene = new Scene(root, 1000, 1000);
        Stage stage = new Stage();
        stage.setScene(scene);
        view._stage = stage;
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> view.onKeyPressed(event.getCode()));
        return view;
    }

    public static GameView createView(String nickName1,String nickName2, String path, ArrayList<String> hardskill) throws IOException {
        _hardskill = hardskill;
        _path = path;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(GameView.class.getResource("Board.fxml"));
        Parent root = fxmlLoader.load();
        final GameView view = fxmlLoader.getController();
        fxmlLoader.setController(view);
        Scene scene = new Scene(root, 1000, 1000);
        Stage stage = new Stage();
        stage.setScene(scene);
        view._stage = stage;
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> view.onKeyPressed(event.getCode()));
        return view;
    }
    public void setPresenter( GamePresenter gamePresenter ) {
        _gamePresenter = gamePresenter;
        this._player1 = _gamePresenter.getNickname1();
        this._player2 = _gamePresenter.getNickname2();
        Button btn_restart = new Button(LoginMain.getMessageBundle().getString("restart.button.text"));
        btn_restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                restart();
            }
        });
        grid_anchor.getChildren().add(btn_restart);
        grid_anchor.setRowIndex(btn_restart,8);
        grid_anchor.setColumnIndex(btn_restart,0);
        grid_anchor.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        Circle pawn1 = new Circle(30);
        pawn1.setFill(Color.GREEN);
        Circle pawn2 = new Circle(30);
        pawn2.setFill(Color.RED);
        Map<Integer,StackPane> stack_array = new HashMap<>();
        int[][] array = SpiralPath.computeSpiralPath(MAX_HEIGHT);
        drawBoard(array,stack_array,pawn1,pawn2);
        Text whosturn = new Text(LoginMain.getMessageBundle().getString("whosturn.text"));
        placeNode(grid_anchor,whosturn,0,9);
        Popup popup = new Popup();

        Text dice_result = new Text();
        grid_anchor.getChildren().add(dice_result);
        grid_anchor.setRowIndex(dice_result,5);
        grid_anchor.setColumnIndex(dice_result,10);


        Text text_skilllevel1 = new Text();
        grid_anchor.getChildren().add(text_skilllevel1);
        grid_anchor.setRowIndex(text_skilllevel1,2);
        grid_anchor.setColumnIndex(text_skilllevel1,10);

        Text text_skilllevel2 = new Text();
        grid_anchor.getChildren().add(text_skilllevel2);
        grid_anchor.setRowIndex(text_skilllevel2,4);
        grid_anchor.setColumnIndex(text_skilllevel2,10);

        Text player = new Text();
        player.setText(_player1);
        grid_anchor.getChildren().add(player);
        grid_anchor.setRowIndex(player,0);
        grid_anchor.setColumnIndex(player,10);

        Text squareName = new Text();
        grid_anchor.getChildren().add(squareName);
        grid_anchor.setRowIndex(squareName,6);
        grid_anchor.setColumnIndex(squareName,9);

        Button btn_singlePlayer = new Button(LoginMain.getMessageBundle().getString("dice.button.text"));
        btn_singlePlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dice = _gamePresenter.throwDice();

                positions = _gamePresenter.executePlayer(dice);
                diceTotal = _gamePresenter.getDiceTotal();
                dice_result.setText(dice.toString() + " Total : " + diceTotal);
                squareNames = _gamePresenter.getSquareNames((ArrayList<Integer>) positions);
                squareName.setText(squareNames.toString());
                skilllevel1 = _gamePresenter.getSkillLevel1();
                text_skilllevel1.setText(Integer.toString(skilllevel1));


                for (int i = 0; i < positions.size(); i++) {
                    stack_array.get(positions.get(i)).getChildren().add(pawn1);

                    if (i == positions.size() - 1 && positions.get(i).equals(63)) {
                        if (!popup.isShowing()) {
                            Text wage = new Text();
                            wage.setText(LoginMain.getMessageBundle().getString("victory.text").concat(_gamePresenter.endGameandGetWages().get(0)));
                            wage.setFont(new Font("Arial",20));
                            popup.getContent().add(wage);
                            popup.show(_stage,800,150);
                        }
                    }
                }
                dice = _gamePresenter.throwDice();
                positions = _gamePresenter.executePlayer(dice);
                return;
            }
        });
        Button btn_multiPlayer = new Button(LoginMain.getMessageBundle().getString("dice.button.text"));
        btn_multiPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dice = _gamePresenter.throwDice();

                positions = _gamePresenter.executePlayer(dice);
                diceTotal = _gamePresenter.getDiceTotal();
                dice_result.setText(dice.toString() + " Total : " + diceTotal);
                squareNames = _gamePresenter.getSquareNames((ArrayList<Integer>) positions);
                squareName.setText(squareNames.toString());
                skilllevel1 = _gamePresenter.getSkillLevel1();
                text_skilllevel1.setText(Integer.toString(skilllevel1));
                skilllevel2 = _gamePresenter.getSkillLevel2();
                text_skilllevel2.setText(Integer.toString(skilllevel2));
                if (isPlayer1){
                    for (int i = 0; i < positions.size();i++) {
                        Pane currentCell = stack_array.get(positions.get(i)); // Supposons que vous utilisez un Pane
                        if (!currentCell.getChildren().contains(pawn1)) {
                            currentCell.getChildren().add(pawn1);
                        }

                        if (i == positions.size() - 1 && positions.get(i).equals(63)) {
                            if (!popup.isShowing()) {
                                Text wage = new Text();
                                wage.setText(LoginMain.getMessageBundle().getString("victory.text").concat(_gamePresenter.endGameandGetWages().get(0)));
                                wage.setFont(new Font("Arial",20));
                                popup.getContent().add(wage);
                                popup.show(_stage,800,150);
                            }
                        }
                    }
                    player.setText(_player1);
                    isPlayer1 = false;
                    return;
                } else{
                        player.setText(_player2);
                        for (int i = 0; i < positions.size(); i++) {
                            Pane currentCell = stack_array.get(positions.get(i)); // Supposons que vous utilisez un Pane
                            if (!currentCell.getChildren().contains(pawn2)) {
                                currentCell.getChildren().add(pawn2);
                            }

                            if (i == positions.size() - 1 && positions.get(i).equals(63)) {
                                if (!popup.isShowing()) {
                                    Text wage = new Text();
                                    wage.setText(LoginMain.getMessageBundle().getString("victory.text").concat(_gamePresenter.endGameandGetWages().get(1)));
                                    wage.setFont(new Font("Arial",20));
                                    popup.getContent().add(wage);
                                    popup.show(_stage,800,150);
                                }
                            }
                        }
                    isPlayer1 = true;
                    return;
                    }
                }
        });
        Text dice_throw = new Text(LoginMain.getMessageBundle().getString("dice.result.text"));
        grid_anchor.getChildren().add(dice_throw);
        grid_anchor.setRowIndex(dice_throw,5);
        grid_anchor.setColumnIndex(dice_throw,9);
        Text skill_level = new Text(LoginMain.getMessageBundle().getString("skill.level.text"));
        grid_anchor.getChildren().add(skill_level);
        grid_anchor.setRowIndex(skill_level,2);
        grid_anchor.setColumnIndex(skill_level,9);
        Text player_name1 = new Text(LoginMain.getMessageBundle().getString("player1.text") + _player1 + " [" + _hardskill.get(0) + "]");
        player_name1.setFill(Color.GREEN);
        grid_anchor.getChildren().add(player_name1);
        grid_anchor.setRowIndex(player_name1,1);
        grid_anchor.setColumnIndex(player_name1,9);

        if (!isNull(_player2)){
            Text player_name2 = new Text(LoginMain.getMessageBundle().getString("player2.text") + _player2 + " [" + _hardskill.get(1) + "]");
            player_name2.setFill(Color.RED);
            grid_anchor.getChildren().add(player_name2);
            grid_anchor.setRowIndex(player_name2,3);
            grid_anchor.setColumnIndex(player_name2,9);
            grid_anchor.getChildren().add(btn_multiPlayer);
            grid_anchor.setRowIndex(btn_multiPlayer,8);
            grid_anchor.setColumnIndex(btn_multiPlayer,2);
            Text skill_level2 = new Text(LoginMain.getMessageBundle().getString("skill.level.text"));
            grid_anchor.getChildren().add(skill_level2);
            grid_anchor.setRowIndex(skill_level2,4);
            grid_anchor.setColumnIndex(skill_level2,9);

        }
        else {
            grid_anchor.getChildren().add(btn_singlePlayer);
            grid_anchor.setRowIndex(btn_singlePlayer,8);
            grid_anchor.setColumnIndex(btn_singlePlayer,2);
        }
    }

    private void restart() {
        _stage.close();
        Platform.runLater(() -> {
            try {
                new LoginMain().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void drawBoard(int[][] array,Map<Integer,StackPane> stack_array,Circle pawn1,Circle pawn2){
        for (int i = 0;i < MAX_LENGTH;i++){
            for (int j = 0;j < MAX_HEIGHT;j++){
                Rectangle rect = new Rectangle(80,80);
                if (_path.equals("fr/ensicaen/ecole/genielogiciel/board_config_1.json")) {
                    rect.setFill(colorList_randomboard[array[i][j]]);
                } else {
                    rect.setFill(colorList_basicBoard[array[i][j]]);
                }
                rect.setStroke(Color.BLACK);
                System.out.println("i j :"+i+" "+j);
                Text text = new Text(Integer.toString(array[i][j]));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(rect,text);
                if (i==0 && j == 0) {
                    stack.getChildren().add(pawn1);
                    if (!isNull(_player2)){
                        stack.getChildren().add(pawn2);
                    }
                }
                stack_array.put(array[i][j],stack);
                container.getChildren().add(stack);
            }
        }
    }

    public void placeNode(GridPane gridpane, Node node,int row,int col){
        grid_anchor.getChildren().add(node);
        grid_anchor.setRowIndex(node,row);
        grid_anchor.setColumnIndex(node,col);
    }
    public void show() {
        _stage.show();
    }

    private void onKeyPressed( KeyCode code ) {
        if (code == KeyCode.SPACE) {
            _gamePresenter.runGameLoop();
        }
    }
}