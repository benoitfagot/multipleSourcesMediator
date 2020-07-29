import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Connexion à la base de données JDBC
 */
public class ConnexionJDBC {
    private static String host = "localhost:3306";
    private static String base = "projetIED"; //Nom de la base de données
    private static String user = "yoba"; //le nom d'utilisateur
    private static String password = "toto"; //Le mot de pass
    private static String url = "jdbc:mysql://" + host + "/" + base;

    private static Connection connection;

    /**
     * créer la connexion à la base de données
     * @return
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                System.err.println("Connection failed : " + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * remplie les informations concernant un film à partir de la base de données locale
     * @param title
     * @return
     */
    public static InfoMovie getMovieInfo(String title)  {

        InfoMovie infoMovie = new InfoMovie();

        try {

            String selectAddressQuery = "SELECT * FROM movie WHERE Movie LIKE \""+ title+"\"";
            Connection dbConnection = ConnexionJDBC.getConnection();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

            ResultSet rs = preparedStatement.executeQuery();

            while ( rs.next() ) {

                infoMovie.setTitle(rs.getString("Movie"));//titre
                infoMovie.setGenre(rs.getString("Genre"));//genre
                infoMovie.setDistributor(rs.getString("Distributeur"));//distributeur
                infoMovie.setBudget(rs.getString("ProductionBudget")); //budget
                infoMovie.setUsEarnings(rs.getString("DomesticGross"));//US Budget
                infoMovie.setWorldEarnings(rs.getString("WorldwideGross"));//Budget Monde
                infoMovie.setReleaseDate(rs.getString("ReleaseDate"));//Date

            }
            //preparedStatement.close();
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());

        }
        return infoMovie;

    }
}
