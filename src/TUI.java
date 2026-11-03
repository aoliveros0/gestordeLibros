import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TUI {
    private Scanner sc = new Scanner(System.in);

    public TUI() {
    }
    // Este metodo basicamente es el del menu.
    public void menu() {
        System.out.println("\n------ GESTOR DE LIBROS ------");
        System.out.println("1. Añadir libro ");
        System.out.println("2. Biblioteca ");
        System.out.println("3. Actualizar libro ");
        System.out.println("4. Eliminar libro ");
        System.out.println("5. Salir  ");
        System.out.print("Opción: ");
    }
// Este lo utlizamos para devolver la opcion escogida del menu y si en vez de colocar un numero colocamos una letra salta la excepcion.
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
// Lo que aparece cuando le damos a la opcion 1 para crear un dato nos pide los datos correspondientes.
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
    // Este se utiliza para leer los datos actualizados
    public String[] leerdatosActualizar() {
        String[] actualizar = new String[5];
        actualizar[0] = pedirdatos("nuevo título");
        actualizar[1] = pedirdatos("nuevo precio");
        actualizar[2] = pedirdatos("nuevo autor");
        actualizar[3] = pedirdatos("nuevo año");
        actualizar[4] = pedirdatos("nuevo género");
        return actualizar;
    }
// Para la segunda opcion tiene 2 opciones la de mostrar todos los libros y la de mostar  uno por su ISBN.
    public int buscarLibro() {
        System.out.println("1. Mostrar todos");
        System.out.println("2. Mostrar uno por ISBN");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
    }
// La de buscar por el ISBN
    public String buscarISBN() {
        System.out.print("Introduce el ISBN: ");
        return sc.nextLine();
    }
// Cuando busco un libro que no tengo guardado.
    public void libroNoEncontrado() {
        System.out.println("ISBN no encontrado.");
    }

    // En este metodo String de actualizarlibro que es la 3 opcion lo que te pide es el ISBN del ibro que quieres modifcar los datos
    public String actualizarlibro() {
        System.out.print("Introduce el ISBN del libro a actualizar: ");
        return sc.nextLine();
    }
// Este metodo sirve para imprimir el mensaje de que el libro se actualizado correctamente.
    public void libroactualizado() {
        System.out.println("Libro actualizado correctamente.");
    }

// Este metodo sirve para imprimir el mensaje de que el libro se ha eliminado correctamente.
    public void librooelimindo() {
        System.out.println("Libro eliminado correctamente.");
    }

    // Cuando escoges un numero que no corresponde con las opciones que tengo.
    public void opcionincorrecto() {
        System.out.println("Has escogido una opcion que no se encuetra en este programa.");
    }

    // Cuando le das a la opcion 5 que es la de Salir el mensaje de despedida  que aparece.
    public void finalitzaprograma() {
        System.out.println("Finalitza el programa.");
    }

    // Este metodo sirve para imprrimir los datos cuando has añadido el libro.
    public void imprimirLibros(Libros l) {
        System.out.println("ISBN: " + l.getISBN() +
                " | Título: " + l.getTitol() +
                " | Precio: " + l.getPreu() +
                " | Autor: " + l.getAutor() +
                " | Año: " + l.getAny() +
                " | Género: " + l.getGenere());
    }

    // Cuando quiero buscar libros pero al no a ver creado ninguno no aprece nada.
    public void imprimirLibro(List<Libros> librosImprimir) {
        if (librosImprimir.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        for (Libros l : librosImprimir) {
            imprimirLibros(l);
        }
    }
  // Para mostrar los mensajes de las excepciones.
    public void mostrarerror(String m) {
        System.out.println("Error: " + m);
    }
}
