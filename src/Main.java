import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Tapez 't' pour requête par titre, 'a' pour requête par acteur :");
            String mode = sc.nextLine();
            if(!mode.equals("t") && !mode.equals("a")) continue;
            if(mode.equals("t"))System.out.println("Entrez le titre du film");
            else System.out.println("Entrez le nom de l'acteur");
            String key = sc.nextLine();
            System.out.println("vous avez choisi le mode "+mode+" et l'information "+StringUtils.capitalize(key));
            String log4jConfPath = "../package/log4j.properties";
            PropertyConfigurator.configure(log4jConfPath);
            switch(mode){
                case "t":

                    InfoMovie infoMovie = ConnexionJDBC.getMovieInfo(key);

                    ResultatSparqlTitle res = new ResultatSparqlTitle();
                    res.setTitle(key);
                    res.setDirectorByTitle(res.getTitle());
                    res.setActorsByTitle(res.getTitle());
                    res.setProducersByTitle(res.getTitle());
                    res.setPlotByTitle(res.getTitle());

                    //res.setGenre(infoMovie.getGenre());
                    if(infoMovie.getGenre() != null){
                        res.setGenre(infoMovie.getGenre());
                    }
                    else{
                        res.setGenre("Not Found");
                    }
                    //res.setReleaseDate(infoMovie.getReleaseDate());
                    if(infoMovie.getReleaseDate() != null){
                        res.setReleaseDate(infoMovie.getReleaseDate());
                    }
                    else{
                        res.setReleaseDate("Not Found");
                    }
                    //res.setUsEarnings(infoMovie.getUsEarnings());
                    if(infoMovie.getUsEarnings() != null){
                        res.setUsEarnings(infoMovie.getUsEarnings());
                    }
                    else{
                        res.setUsEarnings("Not Found");
                    }
                    //res.setWorldEarnings(infoMovie.getWorldEarnings());
                    if(infoMovie.getWorldEarnings() != null){
                        res.setWorldEarnings(infoMovie.getWorldEarnings());
                    }
                    else{
                        res.setUsEarnings("Not Found");
                    }
                    //res.setBudget(infoMovie.getBudget());
                    if(infoMovie.getBudget() != null){
                        res.setBudget(infoMovie.getBudget());
                    }
                    else{
                        res.setBudget("Not Found");
                    }
                    //res.setDistributor(infoMovie.getDistributor());
                    if(infoMovie.getDistributor() != null){
                        res.setDistributor(infoMovie.getDistributor());
                    }
                    else{
                        res.setDistributor("Not Found");
                    }


                    //AFFICHAGE TITRE
                    System.out.println("Title : "+res.getTitle());

                    //AFFICHAGE REALISATEUR
                    System.out.println("Director : "+res.getDirector());

                    //AFFICHAGE ACTEURS
                    System.out.println("Actors :");
                    for(int i =0;i<res.getActors().size();i++){
                        System.out.println("- " + res.getActors().get(i));
                    }

                    //AFFICHAGE PRODUCTEURS
                    System.out.println("Producers :");
                    for(int i =0;i<res.getProducers().size();i++) {
                        System.out.println("- "+ res.getProducers().get(i));
                    }

                    //AFFICHAGE PLOT
                    System.out.println("Plot : "+res.getPlot());

                    //AFFICHAGE GENRE
                    System.out.println("Genre : "+res.getGenre());

                    //AFFICHAGE DISTRIBUTEUR
                    System.out.println("Distributor : "+res.getDistributor());

                    //AFFICHAGE BUDGET
                    System.out.println("Budget : "+res.getBudget());

                    //AFFICHAGE USEARNINGS
                    System.out.println("US Earnings : "+res.getUsEarnings());

                    //AFFICHAGE WorldEarnings
                    System.out.println("World Earnings : "+res.getWorldEarnings());

                    //AFFICHAGE ReleaseDate
                    System.out.println("Release Date : "+res.getReleaseDate());

                    break;
                case "a":
                    ResultatSparqlActor res2 = new ResultatSparqlActor();
                    res2.setName(key);
                    res2.setMoviesByActor(res2.getName());
                    for(int i=0;i<res2.getMovies().size();i++){
                        ResultatSparqlTitle result = new ResultatSparqlTitle();
                        result.setTitle(res2.getMovies().get(i));
                        InfoMovie infoMovie1 = ConnexionJDBC.getMovieInfo(result.getTitle());
                        result.setDirectorByTitle(result.getTitle());
                        result.setProducersByTitle(result.getTitle());

                        if(infoMovie1.getGenre() != null){
                            result.setGenre(infoMovie1.getGenre());
                        }
                        else{
                            result.setGenre("Not Found");
                        }

                        if(infoMovie1.getReleaseDate() != null){
                            result.setReleaseDate(infoMovie1.getReleaseDate());
                        }
                        else{
                            result.setReleaseDate("Not Found");
                        }

                        if(infoMovie1.getDistributor() != null){
                            result.setDistributor(infoMovie1.getDistributor());
                        }
                        else{
                            result.setDistributor("Not Found");
                        }
                        /*result.setGenre(infoMovie1.getGenre());
                        result.setReleaseDate(infoMovie1.getReleaseDate());
                        result.setDistributor(infoMovie1.getDistributor());*/


                        res2.getResults().add(result);
                    }
                    //AFFICHAGE ACTEUR
                    System.out.println("Nom : "+res2.getName());
                    System.out.println("A joué dans : \n");
                    for(int i=0;i<res2.getResults().size();i++){
                        ResultatSparqlTitle r = res2.getResults().get(i);
                        //AFFICHAGE TITRE
                        System.out.println("Title : "+r.getTitle());
                        //AFFICHAGE REALISATEUR
                        System.out.println("Director : "+r.getDirector());
                        //AFFICHAGE PRODUCTEURS
                        System.out.println("Producers :");
                        for(int j =0;j<r.getProducers().size();j++) {
                            System.out.println("- "+ r.getProducers().get(j));
                        }

                        //AFFICHAGE GENRE
                        System.out.println("Genre : "+r.getGenre());

                        //AFFICHAGE DISTRIBUTEUR
                        System.out.println("Distributor : "+r.getDistributor());

                        //AFFICHAGE ReleaseDate
                        System.out.println("Release Date : "+r.getReleaseDate());

                        //SEPARATEUR
                        System.out.println("--------------------------------------------");
                    }
                    break;

            }

        }
    }
}
