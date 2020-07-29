import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;

import javax.xml.xpath.XPathConstants;
import java.util.ArrayList;


/**
 * Etant donné un titre de film, afficher toutes les informations disponibles sur le film : date de
 * sortie, genre, distributeur, budget, revenus aux Etats Unis, revenus mondiaux, réalisateur,
 * résumé, ainsi que la liste des acteurs.
 */
public class ResultatSparqlTitle {

    public String title;
    public String releaseDate;
    public String genre;
    public String distributor;
    public String budget;
    public String usEarnings;
    public String worldEarnings;
    public String director;
    public ArrayList<String> actors = new ArrayList<String>();
    public ArrayList<String> producers = new ArrayList<String>();
    public String plot;

    public ResultatSparqlTitle(){}

    public void setPlotByTitle(String title){
        XPathMovieDatabase instance = new XPathMovieDatabase();
        Object o = instance.XPathMovieDatabase("http://www.omdbapi.com/?apikey=6ea8b522&t="+title+"&r=xml","/root/movie/@plot", XPathConstants.STRING);
        String node = (String) o;
        this.setPlot(node);
    }

    /**
     *chercher un film par son titre
     * @param title : titre film
     */
    public void setActorsByTitle(String title){
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
                "SELECT ?nr WHERE {\n" +
                "    ?f a dbo:Film;\n" +
                "        foaf:name \""+title+"\"@en;\n" +
                "        dbo:starring  ?ac.\n" +
                "    ?ac foaf:name ?nr.\n" +
                "}";

        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.next() ;
                RDFNode n = soln.get("nr") ; // "x" is a variable in the query
                this.getActors().add(n.toString().replace("@en",""));
            }
        } finally {
            qexec.close();
        }
    }

    /**
     * recuperer le directeur par le titre du film
     * @param title : titre film
     */
    public void setDirectorByTitle(String title){
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
                "SELECT ?nr WHERE {\n" +
                "    ?f a dbo:Film;\n" +
                "        foaf:name \""+title+"\"@en;\n" +
                "        dbo:director ?d.\n" +
                "    ?d foaf:name ?nr.\n" +
                "}";

        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.next() ;
                RDFNode n = soln.get("nr") ; // "x" is a variable in the query
                this.setDirector(n.toString().replace("@en",""));
            }
        } finally {
            qexec.close();
        }
    }

    /**
     *recuperer le producteur par le titre du film
     * @param title : titre film
     */
    public void setProducersByTitle(String title){
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
                "SELECT ?nr WHERE {\n" +
                "    ?f a dbo:Film;\n" +
                "        foaf:name \""+title+"\"@en;\n" +
                "        dbo:producer ?d.\n" +
                "    ?d foaf:name ?nr.\n" +
                "}";

        // now creating query object
        Query query = QueryFactory.create(queryString);
        // initializing queryExecution factory with remote service.
        // **this actually was the main problem I couldn't figure out.**
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        //after it goes standard query execution and result processing which can
        // be found in almost any Jena/SPARQL tutorial.
        try {
            ResultSet results = qexec.execSelect();
            for (; results.hasNext(); ) {
                QuerySolution soln = results.next() ;
                RDFNode n = soln.get("nr") ; // "x" is a variable in the query
                this.getProducers().add(n.toString().replace("@en",""));
            }
        } finally {
            qexec.close();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getUsEarnings() {
        return usEarnings;
    }

    public void setUsEarnings(String usEarnings) {
        this.usEarnings = usEarnings;
    }

    public String getWorldEarnings() {
        return worldEarnings;
    }

    public void setWorldEarnings(String worldEarnings) {
        this.worldEarnings = worldEarnings;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }


    public ArrayList<String> getActors() {
        return actors;
    }


    public ArrayList<String> getProducers() {
        return producers;
    }


    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }


}