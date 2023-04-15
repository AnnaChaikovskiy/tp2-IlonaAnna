package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * La classe "Client" prend en considération toutes les informations et les
 * méthodes nécessaire pour s'inscrire à un cours sans interface graphique.
 */
public class Client {

    /**
     * Permet d'assigner à "REGISTER_COMMAND" la valeur "INSCRIRE" de
     * manière finale. Celle-ci ne pourra être changée ultérieurement.
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * Permet d'assigner à "LOAD_COMMAND" la valeur "CHARGER" de
     * manière finale. Celle-ci ne pourra être changée ultérieurement.
     */
    public final static String LOAD_COMMAND = "CHARGER";


    /**
     * La méthode "CourseRequest" permet de retourner une liste des cours
     * pour la session sélectionnée par l'utilisateur du programme.
     *
     * @param session La session sélectionnée par l'utilisateur du programme
     * @return Une liste contenant les cours ainsi que leurs informations de la session choisie
     * @throws IOException
     * @throws ClassNotFoundException Est prise en compte si aucune classe n'est trouvée
     */
    public static ArrayList<Course> CourseRequest(String session) throws IOException, ClassNotFoundException {

        Socket server = new Socket("localhost", 1337);
        ArrayList<Course> courseList = new ArrayList<Course>();
        String p = (LOAD_COMMAND + " " + session);


        ObjectOutputStream  objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(p);

        ObjectInputStream  objectInputStream = new ObjectInputStream(server.getInputStream());
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

    /**
     * La méthode "RegistrationRequest" permet de s'inscrire
     * au cours choisit par l'utilisateur du programme.
     *
     * @param name Prénom de l'élève qui remplie le formulaire d'inscription
     * @param familyName Nom de famille de l'élève qui remplie le formulaire d'inscription
     * @param email Email de l'élève qui remplie le formulaire d'inscription
     * @param studentNumber Numéro de matricule de l'élève qui remplie le formulaire d'inscription
     * @param course Cours choisit par l'élève qui remplie le formulaire d'inscription
     * @throws IOException
     * @throws ClassNotFoundException Est prise en compte si aucune classe n'est trouvée
     */
    public static void RegistrationRequest(String name, String familyName, String email, String studentNumber, Course course) throws IOException, ClassNotFoundException {
        String p = REGISTER_COMMAND;

        Socket server = new Socket("localhost", 1337);

        ObjectOutputStream  objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(p);

        RegistrationForm InscriptionForm = new RegistrationForm(name, familyName, email, studentNumber,course);
        objectOutputStream.writeObject(InscriptionForm);

        ObjectInputStream  objectInputStream = new ObjectInputStream(server.getInputStream());
        Object object = objectInputStream.readObject();

        System.out.println((String)object);

        objectOutputStream.close();
        objectInputStream.close();
        server.close();
    }

    /**
     * Méthode "main" interactive entre le client et le serveur lors
     * de l'inscription de l'utilisateur à ses cours. Aucune interface graphique.
     *
     * @param args la rêquete envoyée par l'utilisateur du programme
     * @throws ClassNotFoundException Est prise en compte si aucune classe n'est trouvée
     * @throws IOException
     */
    public static void main(String args[]) throws ClassNotFoundException, IOException {

        Boolean consultSession = true;
        Boolean verification = true;
        Scanner scanner = new Scanner(System.in);
        int choice;
        String name;
        String familyName;
        String email;
        String studentNumber;
        String courseCode;
        ArrayList<Course> crs;

        System.out.println("***Bienvenue au portail d'inscription de cours de l'UDEM***");

        do {
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Ete");

            choice = scanner.nextInt();
            System.out.println("> Choix:" + String.valueOf(choice));
            String strChoice;
            switch (choice) {
                case 1:
                    strChoice = "Automne";
                    break;
                case 2:
                    strChoice = "Hiver";
                    break;
                case 3:
                    strChoice = "Ete";
                    break;
                default:
                    strChoice = "unknown";
            }

            System.out.println("Les cours offerts pendant la session " + String.valueOf(strChoice) + " sont:");

            crs = CourseRequest (strChoice);

            System.out.println(">Choix:");
            System.out.println("1. Consulter les cours offerts pour une autre session");
            System.out.println("2. Inscription à un cours");

            choice = scanner.nextInt();

            System.out.println(">Choix:" + String.valueOf(choice));

            if( choice == 2)
                consultSession = false;


        } while (consultSession);


        scanner.nextLine();
        System.out.println("Veuillez saisir votre prénom : ");
        name = scanner.nextLine();
        System.out.println("Veuillez saisir votre nom: ");
        familyName = scanner.nextLine();
        System.out.println("Veuillez saisir votre email: ");
        email = scanner.nextLine();

        do {
            System.out.println("Veuillez saisir votre matricule: ");
            studentNumber = scanner.nextLine();
            String regex = "[0-9]+";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(studentNumber);
            boolean matches = m.matches();

            if (studentNumber.length() != 6 || matches == false) {
                System.out.println("Le matricule doit contenir 6 chiffres.");
            }
            else verification = false;
        } while (verification);

        System.out.println("Veuillez saisir le code du cours: ");
        courseCode = scanner.nextLine();

        int i;
        for(  i =0; i< crs.size(); i++){

            if (crs.get(i).getCode().equals(courseCode) )
                break;
        }

        RegistrationRequest( name, familyName, email, studentNumber, crs.get(i)  );


        scanner.close();
    }
}

