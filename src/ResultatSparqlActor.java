import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import java.util.ArrayList;

/**
 *Etant donné un nom d’acteur, afficher la liste des films où il a joué. Pour chaque film on
 * affiche le titre, la date de sortie, le genre, le distributeur, le réalisateur et le producteur.
 */
public class ResultatSparqlActor {

    public ArrayList<String> movies = new ArrayList<String>();
    public ArrayList<ResultatSparqlTitle> results = new ArrayList<ResultatSparqlTitle>();
    public String name;

    public ResultatSparqlActor(){}

    /**
     * chercher un film par nom d'acteur
     * @param actor : nom de l'acteur
     */
    public void setMoviesByActor(String actor){
        String queryString = "\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
                "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n" +
                "PREFIX : <http://dbpedia.org/resource/>\n" +
                "PREFIX dbpedia2: <http://dbpedia.org/property/>\n" +
                "PREFIX dbpedia: <http://dbpedia.org/>\n" +
                "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n" +
                "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
                "SELECT ?t WHERE {\n" +
                "    ?f a dbo:Film;\n" +
                "        foaf:name ?t;\n" +
                "        dbo:starring ?a.\n" +
                "    ?a foaf:name \""+actor+"\"@en\n" +
                "}";

        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.next() ;
                RDFNode n = soln.get("t") ; // "x" is a variable in the query
                this.getMovies().add(n.toString().replace("@en",""));
            }
        } finally {
            qexec.close();
        }
    }

    public ArrayList<String> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    public ArrayList<ResultatSparqlTitle> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultatSparqlTitle> results) {
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}