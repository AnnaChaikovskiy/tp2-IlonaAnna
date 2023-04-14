package server;

/**
 * Permet de définir la classe "ServerLauncher", une
 * "public final statics" méthode de type "int" ainsi
 * qu'une méthode "main" de type "String[]"
 */
public class ServerLauncher {

    /**
     * On assigne à la variable "PORT"
     * la valeur "1337" qui ne peut
     * être modifiée ultérieurement
     */
    public final static int PORT = 1337;

    /**
     * Point de départ du serveur. Il définit le port par
     * lequel le client et le serveur pourront échanger.
     * Quant à la méthode "run", c'est elle qui permet au
     * serveur de lire et d'exécuter les requêtes du client.
     *
     * @param args rêquete du client en format "String"
     *             stocké dans un tableau
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}