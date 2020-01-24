import org.xml.sax.SAXException;


import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    static final String pathURL = "http://gencat.cat/llengua/cinema/provacin.xml";
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, JAXBException {
        URL url = new URL(pathURL);
        Scanner in = new Scanner(System.in);
            List<Film> films = JAXB.unmarshal(pathURL,Films.class).filmList;
        int option=1;
        while(option!=0) {
            menu();
            option=in.nextInt();
            in.nextLine();
            switch (option) {
                case 0:
                    System.out.println("Adios");
                    break;
                case 1:
                    System.out.println("INTRODUCE UN AÑO PARA BUSCAR PELICULAS: ");
                    int year=in.nextInt();
                    in.nextLine();
                    films.stream().filter(film -> film.any==year).forEach(System.out::println);
                    break;
                case 2:
                    System.out.println("ESCRIBE UNO DE LOS SIGUIENTES DIRECTORES: ");
                    for (Film f:films) {
                        System.out.print(f.direccion+" | ");
                    }
                    System.out.println();
                    String director = in.nextLine();
                    int pel_dir = (int) films.stream().filter((film -> film.direccion.equals(director))).count();
                    System.out.println("NUMERO DE PELICULAS DEL DIRECTOR "+director+": "+pel_dir);
                    break;
                case 3:
                    System.out.println("INTRODUCE EL TITULO DE LA PELICULA A BUSCAR: ");
                    String titolp=in.nextLine();
                    films.stream().filter(film -> film.titol.toLowerCase().startsWith(titolp.toLowerCase())).forEach(film -> System.out.println(film.titol));
                    break;
                case 4:
                    Film max = films.stream().max(Comparator.comparing(Film::getAny)).get();
                    System.out.println(max.getAny());
                    break;
                case 5:
                    Film min = films.stream().min(Comparator.comparing(Film::getAny)).get();
                    System.out.println(min.getAny());
                    break;
                case 6:
                    films.stream().sorted(Comparator.comparing(Film::getTitol)).forEach(film -> System.out.println(film.titol));
                    break;
                case 7:
                    System.out.print("INTRODUCE UN NIVEL DE PRIORIDAD DEL 0 AL 100: ");
                    int pri=in.nextInt();
                    in.nextLine();
                    films.stream().filter(film -> film.prioritat==pri).forEach(film -> System.out.println(film.prioritat+" : "+film.titol));
            }
        }
    }
    private static void menu() {
        System.out.println("0 SALIR | 1 PELICULAS DEL AÑO QUE INTRODUCES | 2 NUM PELICULAS DE UN DIRECTOR |\n" +
                "3 FILTRA POR DIRECTOR | 4 MUESTRA LA PELICULA MÁS MODERNA | 5 MUESTRA LA PELICULA MÁS ANTIGUA |\n" +
                "6 MUESTRA EL NOMBRE DE TODAS LAS PELICULADAS ORDENADAS | 7 FILTRA POR PRIODIDAD");
    }
}

