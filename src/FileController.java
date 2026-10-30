import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import excepciones.*;

public class FileController extends MemoryController {
    // Aqui creamos la carpeta data y que dentro de esa carpeta se cree el arhicvo libros.txt
    private File dataFolder = new File("data");
    private File bookFile = new File(dataFolder, "libros.txt");

    // Aqui comprovamos que se ha creado correctamnete la carpeta y el archivo
    // Y si ya hemos creado un libro pues que lo cargue en el archivo.
    public FileController() throws IOException {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (!bookFile.exists()) bookFile.createNewFile();
        cargarLibros();
    }

    @Override
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException {
        // Creamos un libro.
        Libros creador = super.crearLibro(ISBN, titol, preu, autor, any, genere);
        // Si ya tenemos un libro guardado en el fichero ponemos el true para que si hay un libro ya no se elimine y se ponga de bajo del mismo.
        try (FileWriter fw = new FileWriter(bookFile, true)) {
            fw.write(ISBN +  titol +   preu +  autor + any + genere + "\n");
        } catch (IOException e) {
            // Si por algun casual no se crea correctamente el libro lanzo una excepcion avisando de que ha habido un problema.
            super.eliminarLibro(ISBN);
            throw e;
        }
        return creador;
    }

    @Override
    public boolean eliminarLibro(String isbn) throws IOException {
        // Guardamos los datos del libro por si lo tenemos que restaurar.
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
    public void actualizarLibro(String isbn, String[] datosNuevos) throws IOException, GenereException, PreuException, TitolException, AnyException, AutorException {
        // Lo primero  que hago es guardar como esta ahora mismo el ficheo por si hay que restaurarlo.
        Libros a = super.buscarUnLibro(isbn);
        if (a == null) return;

        // Los datos antiguos  el mismo orden que MemoryController
        String[] datosViejos = {a.getTitol(), String.valueOf(a.getPreu()), a.getAutor(),
                String.valueOf(a.getAny()), a.getGenere()};

        // Actualizo el fichero con los nuevos cambios aplicados.
        super.actualizarLibro(isbn, datosNuevos);

        // Aqui reescribo el fichero con todos los libros que tengamos en el momento.
        try {
            guardarTodo();
        } catch (IOException e) {
            // Si falla el disco, dejamos los datos como estaban.
            super.actualizarLibro(isbn, datosViejos);
            throw e;
        }
    }
    // Lo que hace es escribir todos los libros que tenemos en el fichero.
    private void guardarTodo() throws IOException {
        try (FileWriter fw = new FileWriter(bookFile)) {
            for (Libros l : super.buscarTodosLibros()) {
                fw.write("ISBN: " + "Titol: "+ "Preu: " + "Autor: " + "Any: " + "Genere: " + "\n");
                fw.write(l.getISBN() +
                        l.getTitol() +
                        l.getPreu() +
                        l.getAutor() +
                        l.getAny() +
                        l.getGenere() + "\n");
            }
        }
    }
    // Este lo que hace es leer el fichero y lee todos los datos del libro.
    public void cargarLibros() throws IOException {
        try (Scanner sc = new Scanner(bookFile)) {
            while (sc.hasNextLine()) {
                try {
                    String ISBN = sc.nextLine();
                    String titol = sc.nextLine();
                    float preu = Float.parseFloat(sc.nextLine());
                    String autor = sc.nextLine();
                    int any = Integer.parseInt(sc.nextLine());
                    String genere = sc.nextLine();
                    super.crearLibro(ISBN, titol, preu, autor, any, genere);
                } catch (NoSuchElementException | NumberFormatException e) {
                    System.out.println("Fichero con formato incorrecto, se ignora el resto.");
                    break;
                }
            }
        }
    }
}