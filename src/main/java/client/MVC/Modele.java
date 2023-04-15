package client.MVC;

import server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Modele {
    public final static String LOAD_COMMAND = "CHARGER";


    public static ArrayList<Course> CourseRequest(String session) throws IOException, ClassNotFoundException {

        Socket server = new Socket("localhost", 1337);
        ArrayList<Course> courseList = new ArrayList<Course>();
        String p = (LOAD_COMMAND + " " + session);


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(p);

        ObjectInputStream objectInputStream = new ObjectInputStream(server.getInputStream());
        Object object = objectInputStream.readObject();

        courseList = (ArrayList<Course>) object;
        for (int i = 0; i < courseList.size(); i++) {
            String line = String.valueOf(i+1) + ".   " + courseList.get(i).getCode() + "     " + courseList.get(i).getName();
            System.out.println(line);
        }
        objectOutputStream.close();
        objectInputStream.close();
        server.close();

        return courseList;
    }

}
