import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void init(){
        System.out.println("METODA INIT");
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("METODA START");
        // załadowanie widoku z pliku fxml do obiektu parent
        Parent parent = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
        // stawienie właściwości okna aplikacji
        primaryStage.setTitle("Logowanie");
//        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    // po zmaknięciu aplikacji
    @Override
    public void stop(){
        System.out.println("METODA STOP");
    }
    public static void main(String[] args) {
        launch(args);   // wywołanie metody statycznej uruchamiającej cykl życia aplikacji
    }
}