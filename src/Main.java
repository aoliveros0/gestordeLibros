
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException,IllegalArgumentException,RuntimeException {
        sc.useLocale(Locale.ENGLISH);

        MemoryController mcontroller = new FileController();
        int opcion;

        do {
            TUI.menu();
            opcion = TUI.devolver();

            if (opcion == 1) {
                try {
                    String[] datos = TUI.leerdatos();
                    mcontroller.crearLibro(datos[0], datos[1], Float.parseFloat(datos[2]), datos[3], Integer.parseInt(datos[4]), datos[5]);
                    System.out.println("El Libro se ha añadido correctamente. ");
                } catch (Exception e) {
                    System.out.println("Error al añadir el libro: " + e.getMessage());
                }
            } else if (opcion == 2) {
                int buscar = TUI.buscarLibro();
                if (buscar == 1) {
                    TUI.imprimirLibro(mcontroller.buscarTodosLibros());
                } else if (buscar == 2) {
                    String isbn = TUI.buscarISBN();
                    Libros l = mcontroller.buscarUnLibro(isbn);
                    if (l != null) {
                        TUI.imprimirLibros(l);
                    } else {
                        TUI.libroNoEncontrado();
                    }
                }
            } else if (opcion == 3) {
                try {
                    String IsbnA = TUI.actualizarlibro();
                    if (mcontroller.buscarUnLibro(IsbnA) != null) {
                        String[] nuevosDatos = TUI.leerdatosActualizar();
                        mcontroller.actualizarLibro(IsbnA, nuevosDatos);
                        TUI.libroactualizado();
                    } else {
                        TUI.libroNoEncontrado();
                    }
                } catch (Exception e) {
                    System.out.println("Error al modificar: " + e.getMessage());
                }
            } else if (opcion == 4) {
                try {
                    String IsbnE = TUI.buscarISBN();
                    boolean elimina = mcontroller.eliminarLibro(IsbnE);
                    if (elimina) {
                        TUI.librooelimindo();
                    } else {
                        TUI.libroNoEncontrado();
                    }
                } catch (Exception e) {
                    System.out.println("Error al eliminar: " + e.getMessage());
                }
            } else if (opcion == 5) {
                TUI.finalitzaprograma();
            } else {
                TUI.opcionincorrecto();
            }

        } while (opcion != 5);
    }
   public  static void crear(MemoryController m, TUI t) throws IOException{
        String ISBN = null, titol = null,  autor = null,  genere = null;
        float preu = 0.0f;
        int any = 0;
        while (true){
            if(ISBN == null){
                ISBN = TUI.pedirdatos("ISBN");
            } if(titol == null){
                titol = TUI.pedirdatos("titol");
            } if(preu == 0.0f){
                preu = TUI.pedirdatos("preu");
            } if(autor == null){
                autor = TUI.pedirdatos("autor");
            } if(any == 0 ){
                any = TUI.pedirdatos("any");
            } if(genere == null){
                genere = TUI.pedirdatos("genere");
            }
            try {
                Libros nuevoLibro = m.crearLibro(ISBN,titol,preu,autor,any,genere);
                TUI.imprimirLibros(nuevoLibro);
                break;
            } catch (RuntimeException e){
                TUI.mostrarerror(e.getMessage());
                titol = null;
            } catch (IllegalArgumentException a){
                TUI.mostrarerror(a.getMessage());
                preu = null;
            } catch (IllegalArgumentException i){
                TUI.mostrarerror(i.getMessage());
                autor = null;
            } catch (IllegalArgumentException n){
                TUI.mostrarerror(n.getMessage());
                any = null;
            } catch (IllegalArgumentException g){
                TUI.mostrarerror(g.getMessage());
                genere = null;
            }
        }
     }

    }
