import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class JsoupApi {

    /**
     * Méthode pour extraitre les informations sur les
     * films depuis les pages HTML avec l’API JSoup
     */
    public void jsoupParse() throws IOException {
        //Tableau contenant les genres des films
        String[] genres = {"Adventure", "Comedy", "Drama", "Action","Thriller-or-Suspense","Romantic-Comedy"};
        for(int j=0;j<6;j++) {
            //chemin pour créer les fichiers genre " csv"
            String csvFile = "/opt/talend_fr/workspace/"+genres[j]+".csv";
            FileWriter writer = new FileWriter(csvFile);
            CSVUtils.writeLine(writer, Arrays.asList("Genre", "Title", "Distributors"));
            //CSVUtils.writeLine(writer, Arrays.asList("Genre", "Titre", "Distributeur"), '\t');
            for (int i = 2000; i < 2016; i++) {//Parcours de l'année 2000 à 2015
                //constuire le lien pour content les informations année + genre
                // exemple : http://www.the-numbers.com/market/2000/genre/Adventure
                Document doc = Jsoup.connect("http://www.the-numbers.com/market/" + i + "/genre/"+genres[j]).get();
                //Selectionne la table -> tr
                Elements tables = doc.select("table").get(1).select("tr");
                for (Element headline : tables) {
                    Elements tds = headline.select("td");
                    if (tds.size() != 0) {
                        if (tds.get(0).text().equals("Total Gross of All Movies") || tds.get(0).text().equals("Total Tickets Sold"))
                            continue;
                    } else continue;
                    System.out.println(i);
                    System.out.println(genres[j]);
                    System.out.println(tds.get(1).text());
                    System.out.println(tds.get(3).text());
                    System.out.println();
                    //écrire les données extraites de la page html dans le fichier csv
                    CSVUtils.writeLine(writer, Arrays.asList(genres[j], tds.get(1).text(), tds.get(3).text()), '\t', ' ');
                }
            }
            writer.flush();
            writer.close();
        }
    }



}
