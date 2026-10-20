import java.io.*;
import java.util.Scanner;

public class FileController extends MemoryController {
    private File dataFolder = new File("data");
    private File bookFile = new File(dataFolder, "bibilio.txt");

    public FileController() throws IOException {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (!bookFile.exists()) bookFile.createNewFile();
        cargarLibros();
    }

    @Override
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException {
        // 1. Creamos el libro en memoria
        Libros creador = super.crearLibro(ISBN, titol, preu, autor, any, genere);

        // 2. Probamos a escribirlo al final del fichero (true = añadir sin borrar lo anterior)
        try (FileWriter fw = new FileWriter(bookFile, true)) {
            fw.write(ISBN + "\n" + titol + "\n" + preu + "\n" + autor + "\n" + any + "\n" + genere + "\n");
        } catch (IOException e) {
            // Si falla, lo borramos de la memoria y lanzamos la excepción.
            super.eliminarLibro(ISBN);
            throw e;
        }
        return creador;
    }

    @Override
    public boolean eliminarLibro(String isbn) throws IOException {
        // Guardamos el libro por si hay que restaurarlo.
        Libros viejo = super.buscarUnLibro(isbn);
        if (viejo == null) return false;

        // Lo quitamos de la memoria y reescribimos el fichero sin él.
        super.eliminarLibro(isbn);
        try {
            guardarTodo();
        } catch (IOException e) {
            // Si falla el disco, lo volvemos a meter en memoria.
            super.crearLibro(viejo.getISBN(), viejo.getTitol(), viejo.getPreu(),
                    viejo.getAutor(), viejo.getAny(), viejo.getGenere());
            throw e;
        }
        return true;
    }

    @Override
    public void actualizarLibro(String isbn, String[] datosNuevos) throws IOException {
        // Primero guardamos el estado actual por si hay que restaurarlo.
        Libros a = super.buscarUnLibro(isbn);
        if (a == null) return;

        // Los datos antiguos para el rollback (mismo orden que MemoryController)
        String[] datosViejos = {a.getTitol(), String.valueOf(a.getPreu()), a.getAutor(),
                String.valueOf(a.getAny()), a.getGenere()};

        // Actualizamos la memoria con los datos nuevos.
        super.actualizarLibro(isbn, datosNuevos);

        // Reescribimos el fichero con todos los libros.
        try {
            guardarTodo();
        } catch (IOException e) {
            // Si falla el disco, dejamos los datos como estaban.
            super.actualizarLibro(isbn, datosViejos);
            throw e;
        }
    }

    // Escribe TODOS los libros que hay en memoria en el fichero.
    private void guardarTodo() throws IOException {
        try (FileWriter fw = new FileWriter(bookFile)) {
            for (Libros l : super.buscarTodosLibros()) {
                fw.write(l.getISBN() + "\n" + l.getTitol() + "\n" + l.getPreu() + "\n"
                        + l.getAutor() + "\n" + l.getAny() + "\n" + l.getGenere() + "\n");
            }
        }
    }

    // Lee el fichero: cada libro ocupa 6 líneas seguidas.
    public void cargarLibros() throws IOException {
        try (Scanner sc = new Scanner(bookFile)) {
            while (sc.hasNextLine()) {
                String ISBN = sc.nextLine();
                String titol = sc.nextLine();
                float preu = Float.parseFloat(sc.nextLine());
                String autor = sc.nextLine();
                int any = Integer.parseInt(sc.nextLine());
                String genere = sc.nextLine();

                super.crearLibro(ISBN, titol, preu, autor, any, genere);
            }
        }
    }
}