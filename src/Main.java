
import excepciones.*;

import java.io.IOException;

import java.util.Locale;
import java.util.Scanner;


public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, RuntimeException, GenereException, PreuException, TitolException, AnyException, AutorException {
        sc.useLocale(Locale.ENGLISH);

        MemoryController mcontroller = new FileController();
        int opcion;

        do {
            TUI.menu();
            opcion = TUI.devolver();

            if (opcion == 1) {
                crear(mcontroller);
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
                    String IsbnA = TUI.actualizarlibro();
                    if (mcontroller.buscarUnLibro(IsbnA) != null) {
                        String[] nuevosDatos = TUI.leerdatosActualizar();
                        mcontroller.actualizarLibro(IsbnA, nuevosDatos);
                        TUI.libroactualizado();
                    } else {
                        TUI.libroNoEncontrado();
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

    public static void crear(MemoryController m) throws IOException, TitolException, PreuException, AnyException, AutorException {

            String ISBN = TUI.pedirdatos("ISBN");
            String titol = TUI.pedirdatos("titol");
            float preu = Float.parseFloat(TUI.pedirdatos("preu"));
            String autor = TUI.pedirdatos("autor");
            int any = Integer.parseInt(TUI.pedirdatos("any"));

            String genere = TUI.pedirdatos("genere");
            try {
                Libros nuevoLibro = m.crearLibro(ISBN, titol, preu, autor, any, genere);
                TUI.imprimirLibros(nuevoLibro);
            } catch (TitolException tl){
                System.out.println("Titol no encontrado: " + tl.getMessage());
            } catch (PreuException pr) {
                System.out.println("Error: el precio  no es valido." + pr.getMessage());
            } catch (AutorException at) {
                System.out.println("Perdona pero ese campo esta incorrecto  " + at.getMessage());
            } catch (AnyException | GenereException ane){
                System.out.println("Error: no se puede ingresar los datos: " + ane.getMessage());
            }
    }
    }

