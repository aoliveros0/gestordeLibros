
import excepciones.*;

import java.io.IOException;

import java.util.Locale;
import java.util.Scanner;


public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException, RuntimeException, GenereException, PreuException, TitolException, AnyException, AutorException, ISBNException {
        sc.useLocale(Locale.ENGLISH);
        TUI t = new TUI();
        MemoryController mcontroller = new FileController();
        int opcion;

        do {
            t.menu();
            opcion = t.devolver();

            if (opcion == 1) {
                crear(mcontroller,t);
            } else if (opcion == 2) {
                int buscar = t.buscarLibro();
                if (buscar == 1) {
                    t.imprimirLibro(mcontroller.buscarTodosLibros());
                } else if (buscar == 2) {
                    String isbn = t.buscarISBN();
                    Libros l = mcontroller.buscarUnLibro(isbn);
                    if (l != null) {
                        t.imprimirLibros(l);
                    } else {
                        t.libroNoEncontrado();
                    }
                }
            } else if (opcion == 3) {
                    String ISBN = t.actualizarlibro();
                    if (mcontroller.buscarUnLibro(ISBN) != null) {
                        String[] nuevosDatos = t.leerdatosActualizar();
                        mcontroller.actualizarLibro(ISBN, nuevosDatos);
                        t.libroactualizado();
                    } else {
                        t.libroNoEncontrado();
                    }

            } else if (opcion == 4) {
                try {
                    String IsbnE = t.buscarISBN();
                    boolean elimina = mcontroller.eliminarLibro(IsbnE);
                    if (elimina) {
                        t.librooelimindo();
                    } else {
                        t.libroNoEncontrado();
                    }
                } catch (Exception e) {
                    System.out.println("Error al eliminar: " + e.getMessage());
                }
            } else if (opcion == 5) {
                t.finalitzaprograma();
            } else {
                t.opcionincorrecto();
            }

        } while (opcion != 5);
    }

    public static void crear(MemoryController m, TUI t) throws IOException, TitolException, PreuException, AnyException, AutorException {

            String ISBN = t.pedirdatos("ISBN");
            String titol = t.pedirdatos("titol");
            float preu = Float.parseFloat(t.pedirdatos("preu"));
            String autor = t.pedirdatos("autor");
            int any = Integer.parseInt(t.pedirdatos("any"));
            String genere = t.pedirdatos("genere");
            try {
                Libros nuevoLibro = m.crearLibro(ISBN, titol, preu, autor, any, genere);
                t.imprimirLibros(nuevoLibro);
            } catch (TitolException tl){
                t.mostrarerror(tl.getMessage());
            } catch (PreuException pr) {
                t.mostrarerror(pr.getMessage());
            } catch (AutorException at) {
                t.mostrarerror(at.getMessage());
            } catch (AnyException ane){
                t.mostrarerror(ane.getMessage());
            } catch (GenereException g){
                t.mostrarerror(g.getMessage());
            }
            catch (ISBNException isbn) {
                t.mostrarerror(isbn.getMessage());
            }
    }
    }



