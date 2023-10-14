package fr.ensicaen.ecole.genielogiciel;

import fr.ensicaen.ecole.genielogiciel.presenter.LoginPresenter;
import fr.ensicaen.ecole.genielogiciel.view.LoginView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;



public final class LoginMain extends Application {


    public static void main( String[] args ) {
        launch(args);
    }
    public static ResourceBundle getMessageBundle() {

        if (LoginView.getLanguage().equals("Français")) {
            return ResourceBundle.getBundle("fr.ensicaen.ecole.genielogiciel.MessageBundle");
        }
        return ResourceBundle.getBundle("fr.ensicaen.ecole.genielogiciel.MessageBundle_en_US");
    }

    @Override
    public void start( final Stage primaryStage ) throws Exception {
        LoginView view = LoginView.createView(primaryStage, "LoginDialog.fxml");
        LoginPresenter presenter = new LoginPresenter();
        view.setPresenter(presenter);
        presenter.setView(view);
        view.show();
    }
}

        //Run example

       /* Game g = new Game();

        int playerCount = 2;
        List<String> l = new ArrayList<>();
        l.add("Prepa");
        l.add("DUT");
        //l.add("Licence");
        //l.add("Prepa");

        String path = "fr/ensicaen/ecole/genielogiciel/board_config_2.json";
        g.start(playerCount, l, path);

        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            positions = g.executePlayer();
            System.out.println(positions);
        }
        while (positions != null) {
            for (int i = 0; i < 2; i++) {
                positions = g.executePlayer();
                System.out.println(positions);
            }
        }*/

