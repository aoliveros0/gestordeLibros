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
    public FileController() throws IOException, ISBNException, TitolException, PreuException, AutorException, AnyException, GenereException {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (!bookFile.exists()) bookFile.createNewFile();
        cargarLibros();
    }

    @Override
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException, ISBNException, TitolException, PreuException, AutorException, AnyException, GenereException {

        Libros creador = super.crearLibro(ISBN, titol, preu, autor, any, genere);

        // Hago este boolean para poner en la cabecera los datos antes de abrir el FileWriter por si tengo que escribir la cabecera.
        boolean escribirCabecera = !bookFile.exists() || bookFile.length() == 0;

        try (FileWriter fw = new FileWriter(bookFile, true)) {
            // en el if pongo el boolea creado y escribo en el write las opciones que tengo para los datos.
            if (escribirCabecera) {
                fw.write("ISBN" + " " + "Titol" + " " + "Preu" + " " + "Autor" + " " + "Any" + " " + "Genere"+"\n");
            }
            // Aqui ya creo con normalidad el libro.
            fw.write(ISBN + " ; " + titol + " ; " + preu + " ; " + autor + " ; " + any + " ; " + genere + "\n");
        } catch (IOException e) {
            super.eliminarLibro(ISBN);
            throw e;
        }
        return creador;
    }

    @Override
    public boolean eliminarLibro(String isbn) throws IOException, ISBNException, TitolException, PreuException, AutorException, AnyException, GenereException {
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
    public void actualizarLibro(String isbn, String[] datosNuevos) throws IOException, GenereException, PreuException, TitolException, AnyException, AutorException, ISBNException {
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
                fw.write("ISBN: " + "Titol: " + "Preu: " + "Autor: " + "Any: " + "Genere: " + "\n");
                fw.write(l.getISBN() + ";" + l.getTitol() + ";" + l.getPreu() + l.getAutor() + ";" + l.getAny() + ";" + l.getGenere() + "\n");

            }
        }
    }

    // Este lo que hace es leer el fichero y lee todos los datos del libro.
    public void cargarLibros() throws IOException, TitolException, PreuException,
            AutorException, AnyException, GenereException, ISBNException {
        try (Scanner sc = new Scanner(bookFile)) {
            // Aqui pongo un nextLine para poder saltar la cabacera que he creado anteriormente.
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                if (linea.isEmpty()) continue; // ignora líneas en blanco
                String[] campos = linea.split(";");
                if (campos.length != 6) {
                    System.out.println("Línea con formato incorrecto, se ignora: " + linea);
                    continue;
                }
                // Lo que hago con el trim basicamente es quitar espacios tanto en el inicio como en el final de todos los datos.
                // Por cada campo qu creo tiene una sepracion con el de al lado.

                try {
                    String ISBN = campos[0].trim();
                    String titol = campos[1].trim();
                    // Aqui solo detectra correctamente si por ejemplom pongo 19.99 i pongo 19.99 se ira a la excepcion.
                    float preu = Float.parseFloat(campos[2].trim().replace(',', '.'));
                    String autor = campos[3].trim();
                    int any = Integer.parseInt(campos[4].trim());
                    String genere = campos[5].trim();

                    // super.crearLibro, NO this.crearLibro: así no se vuelve a escribir en el fichero
                    super.crearLibro(ISBN, titol, preu, autor, any, genere);

                } catch (NumberFormatException e) {
                    System.out.println("Línea con número incorrecto, se ignora: " + linea);
                }
            }
        }
    }
}