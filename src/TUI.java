import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TUI {
    private Scanner sc = new Scanner(System.in);

    public TUI() {
    }

    public void menu() {
        System.out.println("\n------ GESTOR DE LIBROS ------");
        System.out.println("1. Añadir libro ");
        System.out.println("2. Biblioteca ");
        System.out.println("3. Actualizar libro ");
        System.out.println("4. Eliminar libro ");
        System.out.println("5. Salir  ");
        System.out.print("Opción: ");
    }

    public int devolver() {
        try {
            int valor = sc.nextInt();
            sc.nextLine();
            return valor;
        } catch (InputMismatchException e) {
            System.out.println("Tienes que introducir un número válido:");
            sc.nextLine();
            return devolver();
        }
    }

    public String pedirdatos(String datos) {
        System.out.print("Introduce el " + datos + ": ");
        return sc.nextLine();
    }

    public String[] leerdatos() {
        String[] crear = new String[6];
        crear[0] = pedirdatos("ISBN");
        crear[1] = pedirdatos("título");
        crear[2] = pedirdatos("precio");
        crear[3] = pedirdatos("autor");
        crear[4] = pedirdatos("año de publicación");
        crear[5] = pedirdatos("género");
        return crear;
    }

    public String[] leerdatosActualizar() {
        String[] actualizar = new String[5];
        actualizar[0] = pedirdatos("nuevo título");
        actualizar[1] = pedirdatos("nuevo precio");
        actualizar[2] = pedirdatos("nuevo autor");
        actualizar[3] = pedirdatos("nuevo año");
        actualizar[4] = pedirdatos("nuevo género");
        return actualizar;
    }

    public int buscarLibro() {
        System.out.println("1. Mostrar todos");
        System.out.println("2. Mostrar uno por ISBN");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
    }

    public String buscarISBN() {
        System.out.print("Introduce el ISBN: ");
        return sc.nextLine();
    }

    public void libroNoEncontrado() {
        System.out.println("ISBN no encontrado.");
    }

    public String actualizarlibro() {
        System.out.print("Introduce el ISBN del libro a actualizar: ");
        return sc.nextLine();
    }

    public void libroactualizado() {
        System.out.println("Libro actualizado correctamente.");
    }

    public void librooelimindo() {
        System.out.println("Libro eliminado correctamente.");
    }

    public void opcionincorrecto() {
        System.out.println("Has escogido una opcion que no se encuetra en este programa.");
    }

    public void finalitzaprograma() {
        System.out.println("Finalitza el programa.");
    }

    public void imprimirLibros(Libros l) {
        System.out.println("ISBN: " + l.getISBN() +
                " | Título: " + l.getTitol() +
                " | Precio: " + l.getPreu() +
                " | Autor: " + l.getAutor() +
                " | Año: " + l.getAny() +
                " | Género: " + l.getGenere());
    }

    public void imprimirLibro(List<Libros> librosImprimir) {
        if (librosImprimir.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        for (Libros l : librosImprimir) {
            imprimirLibros(l);
        }
    }

    public void mostrarerror(String m) {
        System.out.println("Error: " + m);
    }
}