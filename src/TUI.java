import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TUI {
    static Scanner sc = new Scanner(System.in);

public  TUI() {
}
    // Es el menu principal que aparece al iniciar el programa.
    public static void menu() {
        System.out.println("\n------ GESTOR DE LIBROS ------");
        System.out.println("1. Añadir libro ");
        System.out.println("2. Biblioteca ");
        System.out.println("3. Actualizar libro ");
        System.out.println("4. Eliminar libro ");
        System.out.println("5. Salir  ");
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

