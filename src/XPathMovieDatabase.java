import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class XPathMovieDatabase {

    public static Object XPathMovieDatabase(String uri, String requete, QName typeRetour){
        //Le dernier paramètre indique le type de résultat souhaité
        //XPathConstants.STRING: chaîne de caractères (String)
        //XPathConstants.NODESET: ensemble de noeuds DOM (NodeList)
        //XPathConstants.NODE: noeud DOM (Node) - le premier de la liste
        //XPathConstants.BOOLEAN: booléen (Boolean) - vrai si la liste n'est pas vide
        //XPathConstants.NUMBER: numérique (Double) - le contenu du noeud sélectionné transformé en Double

        try{
            //Transformation en document DOM du contenu XML
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder parseur = fabrique.newDocumentBuilder();
            Document document = parseur.parse(uri);

            //création de l'objet XPath
            XPathFactory xfabrique = XPathFactory.newInstance();
            javax.xml.xpath.XPath xpath = xfabrique.newXPath();

            //évaluation de l'expression XPath
            XPathExpression exp = xpath.compile(requete);
            return exp.evaluate(document, typeRetour);

        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args){

        Object o = XPathMovieDatabase("http://www.omdbapi.com/?apikey=5fceb67e&t=Titanic&r=xml","/root/movie/@plot",XPathConstants.STRING);
        String node = (String) o;
        System.out.println(node) ;
    }
}