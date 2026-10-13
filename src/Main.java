
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        sc.useLocale(Locale.ENGLISH);

        MemoryController mcontroller = new MemoryController();
        int opcion;

        do {
            menu();
            opcion = devolver();

            if (opcion == 1) {
                try {
                    String[] datos = leerdatos();
                    mcontroller.crearLibro(datos[0], datos[1], Float.parseFloat(datos[2]), datos[3], Integer.parseInt(datos[4]), datos[5]);
                    System.out.println("El Libro se ha añadido correctamente. ");
                } catch (Exception e) {
                    System.out.println("Error al añadir el libro: " + e.getMessage());
                }
            } else if (opcion == 2) {
                int buscar = buscarLibro();
                if (buscar == 1) {
                    imprimirLibro(mcontroller.buscarTodosLibros());
                } else if (buscar == 2) {
                    String isbn = buscarISBN();
                    Libros l = mcontroller.buscarUnLibro(isbn);
                    if (l != null) {
                        imprimirLibros(l);
                    } else {
                        libroNoEncontrado();
                    }
                }
            } else if (opcion == 3) {
                try {
                    String IsbnA = actualizarlibro();
                    if (mcontroller.buscarUnLibro(IsbnA) != null) {
                        String[] nuevosDatos = leerdatosActualizar();
                        mcontroller.actualizarLibro(IsbnA, nuevosDatos);
                        libroactualizado();
                    } else {
                        libroNoEncontrado();
                    }
                } catch (Exception e) {
                    System.out.println("Error al modificar: " + e.getMessage());
                }
            } else if (opcion == 4) {
                try {
                    String IsbnE = buscarISBN();
                    boolean elimina = mcontroller.eliminarLibro(IsbnE);
                    if (elimina) {
                        librooelimindo();
                    } else {
                        libroNoEncontrado();
                    }
                } catch (Exception e) {
                    System.out.println("Error al eliminar: " + e.getMessage());
                }
            } else if (opcion == 5) {
                finalitzaprograma();
            } else {
                opcionincorrecto();
            }

        } while (opcion != 5);
    }
    // Es el menu principal que aparece al iniciar el programa.
    public static void menu() {
        System.out.println("\n------ GESTOR DE LIBROS ------");
        System.out.println("1. Añadir libro: ");
        System.out.println("2. Biblioteca: ");
        System.out.println("3. Actualizar libro: ");
        System.out.println("4. Eliminar libro: ");
        System.out.println("5. Salir: ");
        System.out.print("Opción: ");
    }
    // Este basicamente devuelve la opcion que escoges si es añadir etc .
    public static int devolver() {
        try {
            int val = sc.nextInt();
            sc.nextLine();
            return val;
        } catch (InputMismatchException e) {
            System.out.println("Tienes que introducir un número válido:");
            sc.nextLine(); // Limpiar entrada errónea
            return devolver();
        }
    }
    // Esta es la frase que aparece cuando empiezas a añadir libros.
    public static String pedirdatos(String datos) {
        System.out.print("Introduce el " + datos + ": ");
        return sc.nextLine();
    }
    // Este sirve para leer la informacion escrita.
    public static String[] leerdatos() {
        String[] crear = new String[6];
        crear[0] = pedirdatos("ISBN");
        crear[1] = pedirdatos("título");
        crear[2] = pedirdatos("precio");
        crear[3] = pedirdatos("autor");
        crear[4] = pedirdatos("año de publicación");
        crear[5] = pedirdatos("género");
        return crear;
    }
    // Lo mismo que el de arriba pero en este caso cuando haces modificaciones de un libro.
    public static String[] leerdatosActualizar() {
        String[] actualizar = new String[5];
        actualizar[0] = pedirdatos("nuevo título");
        actualizar[1] = pedirdatos("nuevo precio");
        actualizar[2] = pedirdatos("nuevo autor");
        actualizar[3] = pedirdatos("nuevo año");
        actualizar[4] = pedirdatos("nuevo género");
        return actualizar;
    }
    // Este es el de buscar por ejemplo puedes ver toda la lista entera o por el ISBN.
    public static int buscarLibro() {
        System.out.println("1. Mostrar todos");
        System.out.println("2. Mostrar uno por ISBN");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
    }
    // Basicamente es lo de arriba pero lo buscas por el ISBN.
    public static String buscarISBN() {
        System.out.print("Introduce el ISBN: ");
        return sc.nextLine();
    }
    // Cuando escribes un ISBN no exsistente
    public static void libroNoEncontrado() {
        System.out.println("ISBN no encontrado.");
    }
    // Para actualizar un libro te pide añadir el isbn del contacto que quieres hacer los cambios.
    public static String actualizarlibro() {
        System.out.print("Introduce el ISBN del libro a actualizar: ");
        return sc.nextLine();
    }
    // El mensaje que aparece cuando haces la actualizacion correctamente.
    public static void libroactualizado() {
        System.out.println("Libro actualizado correctamente.");
    }
    // Cuando borras un libro.
    public static void librooelimindo() {
        System.out.println("Libro eliminado correctamente.");
    }
    // Cuando escoges una opcion que no se encuentra en el programa.
    public static void opcionincorrecto() {
        System.out.println("Has escogido una opcion que no se encuetra en este programa.");
    }
    // Cuando escoges la opcion 5.
    public static void finalitzaprograma() {
        System.out.println("Finalitza el programa.");
    }
    // Imprime los datos de los libros.
    public static void imprimirLibros(Libros l) {
        System.out.println("ISBN: " + l.getISBN() +
                " | Título: " + l.getTitol() +
                " | Precio: " + l.getPreu() +
                " | Autor: " + l.getAutor() +
                " | Año: " + l.getAny() +
                " | Género: " + l.getGenere());
    }
    // Lo que confirma si se ha imprimpido correctamente el libro o no.
    public static void imprimirLibro(List<Libros> librosImprimir) {
        if (librosImprimir.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        for (Libros l : librosImprimir) {
            imprimirLibros(l);
        }
    }
}