package client.MVC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.models.Course;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controleur implements Initializable {
    private Modele modele;
    private Scene scene;
    private TableView<Course> screen;
    private TableColumn<Course, String> code;
    private TableColumn<Course, String> name;

    ObservableList<Course> showCourses = FXCollections.observableArrayList(
            new Course("hello", "nice", "lol")
    );


    public Controleur(Modele m, Scene s, TableView screen, TableColumn code, TableColumn name) {
        this.modele = m;
        this.scene = s;
        this.screen = screen;
        this.code = code;
        this.name = name;

    }

    public void charger(String yourSession) throws IOException, ClassNotFoundException {
        ArrayList<Course> x = new ArrayList<Course>();
        x = (ArrayList<Course>) Modele.CourseRequest(yourSession);
        ObservableList list = FXCollections.observableList(x);
       // x.get(0).getCode();
        //x.get(0).getName();
        //screen.getColumns().add(x);
        //code.getColumns().add(x.get(0).getCode());
        //name.getColumns().add(x.get(0).getName());
        //ObservableList<Course> showCourses = screen.getItems();
        //showCourses.add(list);
        //screen.setItems(showCourses);
        Course show = new Course(
                Modele.CourseRequest(yourSession).get(0).getName(),
                Modele.CourseRequest(yourSession).get(0).getCode(),
                Modele.CourseRequest(yourSession).get(0).getSession());
        ObservableList<Course> idk = screen.getItems();
        idk.add(show);
        screen.setItems(idk);
        //Modele.CourseRequest(yourSession).get(0).getCode();




        //this.screen.setItems(list);
        System.out.print(x);
        System.out.print(x.get(0).getName());
        //ObservableList<>
        //showCourses();

        //Modele.CourseRequest(yourSession);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        code.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
        name.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        screen.setItems(showCourses);
    }

    //public void showCourses() {

    //}





}

