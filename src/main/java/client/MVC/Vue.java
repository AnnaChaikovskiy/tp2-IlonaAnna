package client.MVC;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.models.Course;


/**
 * La classe "Vue" permet de créer l'interface
 * graphique d'un site d'inscription à des cours
 * de l'Université de Montréal
 */
public class Vue extends Application {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Vue.launch(args);
    }

    @Override
    /**
     * La méthode "start" permet d'inizialiser
     * les éléments qui se retrouve dans le "Stage"
     * ainsi que la "Scene" de l'interface
     */
    public void start(Stage primaryStage) throws Exception {


        /**
         *
         */
        Pane root = new Pane();

        /**
         * Initialisation de la scène avec ses dimensions
         */
        Scene scene = new Scene(root, 800, 550);

        //controleur = new Controleur(new Modele(), scene);

        /**
         * Change de couleur la scène de l'interface
         */
        root.setStyle("-fx-background-color: #EFECDF");


        /**
         * Initialisation du titre pour la
         * partie gauche de l'interface.
         */
        Text titre1 = new Text("Liste des cours");
        titre1.setFont(Font.font("Purisa", 20));
        titre1.setLayoutX(135);
        titre1.setLayoutY(35);
        root.getChildren().add(titre1);


        /**
         * Initialisation du tableau interactif présentant
         * la liste de cours selon la session sélectionnée
         */
        TableView<Course> screen = new TableView<Course>();
        screen.setEditable(true);

        TableColumn code = new TableColumn<Course, String>("Code");
        code.setCellValueFactory(new PropertyValueFactory<Course,String>("code"));

        TableColumn name = new TableColumn<Course, String>("Cours");
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        screen.getColumns().add(code);
        screen.getColumns().add(name);



        screen.setLayoutX(20);
        screen.setLayoutY(50);
        screen.setPrefHeight(425);
        screen.setPrefWidth(360);

        screen.setColumnResizePolicy(screen.CONSTRAINED_RESIZE_POLICY);
        root.getChildren().add(screen);


        /**
         * Initialisation des deux séparateurs
         * (vertical et horizontal)
         */
        Line sepV = new Line();
        sepV.setStartX(400);
        sepV.setEndX(400);
        sepV.setStartY(0);
        sepV.setEndY(550);
        sepV.setStrokeWidth(3);
        sepV.setStroke(Color.WHITE);
        root.getChildren().add(sepV);

        Line sepH = new Line();
        sepH.setStartX(0);
        sepH.setEndX(400);
        sepH.setStartY(490);
        sepH.setEndY(490);
        sepH.setStrokeWidth(3);
        sepH.setStroke(Color.WHITE);
        root.getChildren().add(sepH);


        /**
         * Inisialisation du bouton interactif "charger"
         * ainsi que de la liste déroulante mettant en
         * avant les choix de sessions possible
         */
        Button charger = new Button("charger");
        charger.setLayoutX(275);
        charger.setLayoutY(508);
        root.getChildren().add(charger);

        ObservableList<String> items = FXCollections.observableArrayList("Hiver", "Ete", "Automne");
        ChoiceBox<String> session = new ChoiceBox<>(items);

        session.setLayoutX(50);
        session.setLayoutY(508);
        session.setPrefWidth(100);
        root.getChildren().add(session);

        /**
         * Initialisation du titre de la
         * partie droite de l'interface
         */
        Text titre2 = new Text("Formulaire d'inscription");
        titre2.setFont(Font.font("Purisa", 20));
        titre2.setLayoutX(500);
        titre2.setLayoutY(35);
        root.getChildren().add(titre2);

        /**
         * Initialisation de la zone de remplissage
         * du formulaire d'inscription. Partie
         * droite de l'interface graphique
         */
        ArrayList<String> liste = new ArrayList<String>(4);
        liste.add("Prénom");
        liste.add("Nom");
        liste.add("Email");
        liste.add("Matricule");

        int addition = 75;
        int index = 0;
        for (int i = 0; i <=3; i++) {
            TextField whiteSpace = new TextField();
            whiteSpace.setLayoutX(575);
            whiteSpace.setLayoutY(addition);

            String element = liste.get(index);
            Text newTitle = new Text(element);
            newTitle.setLayoutX(475);
            newTitle.setLayoutY(addition+15);

            addition = addition + 35;
            index = index + 1;

            root.getChildren().add(whiteSpace);
            root.getChildren().add(newTitle);
        }

        /**
         * Initialisation du bouton d'envoie
         * du formulaire d'inscription
         */
        Button envoyer = new Button("envoyer");
        envoyer.setLayoutX(600);
        envoyer.setLayoutY(225);
        envoyer.setPrefWidth(100);
        root.getChildren().add(envoyer);

        /**
         *
         */

        //controleur = new Controleur(new Modele(), scene, screen);

        /**
         * Mise à jour des modifications apportées à la scène
         * ainsi que son apparaition à l'écran du client
         */
        primaryStage.setTitle("Inscription UdeM");
        primaryStage.setScene(scene);
        primaryStage.show();

        final ArrayList<Course>[] courseArrayList = new ArrayList[]{new ArrayList<>()};
        charger.setOnAction((actionEvent) -> {
            String yourSession = session.getValue();
            try {
                courseArrayList[0] = Controleur.CourseRequest(yourSession);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(courseArrayList[0].isEmpty());
            for (int i=0; i < courseArrayList[0].size() ; i++) {
                screen.getItems().add(courseArrayList[0].get(i));
            }
        });
    }

}
