import java.io.*;

public class FileController extends MemoryController {
    private File dataFolder = new File("data");
    private File bookFile = new File(dataFolder, "libro.txt");

    public FileController() throws IOException {
        if (!dataFolder.exists()) dataFolder.mkdir();
        if (!bookFile.exists()) bookFile.createNewFile();
        cargarLibros();
    }

    // Formato de cada línea: ISBN;titol;preu;autor;any;genere
    private String aLinea(Libros l) {
        return l.getISBN() + ";" + l.getTitol() + ";" + l.getPreu() + ";"
                + l.getAutor() + ";" + l.getAny() + ";" + l.getGenere();
    }

    // Reescribe el fichero entero con lo que hay en memoria
    private void guardarTodo() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookFile))) {
            for (Libros l : super.buscarTodosLibros()) {
                bw.write(aLinea(l));
                bw.newLine();
            }
        }
    }

    @Override
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException {
        Libros nuevo = super.crearLibro(ISBN, titol, preu, autor, any, genere);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookFile, true))) { // true = añadir al final
            bw.write(aLinea(nuevo));
            bw.newLine();
        } catch (IOException e) {
            super.eliminarLibro(ISBN);   // rollback en memoria
            throw e;
        }
        return nuevo;
    }

    @Override
    public boolean eliminarLibro(String isbn) throws IOException {
        Libros viejo = super.buscarUnLibro(isbn);
        if (viejo == null) return false;

        super.eliminarLibro(isbn);
        try {
            guardarTodo();
        } catch (IOException e) {
            // rollback: lo volvemos a meter en memoria
            super.crearLibro(viejo.getISBN(), viejo.getTitol(), viejo.getPreu(),
                    viejo.getAutor(), viejo.getAny(), viejo.getGenere());
            throw e;
        }
        return true;
    }

    @Override
    public void actualizarLibro(String isbn, String[] datosNuevos) throws IOException {
        Libros a = super.buscarUnLibro(isbn);
        if (a == null) return;

        // Orden igual que en MemoryController: titol, preu, autor, any, genere
        String[] datosViejos = {
                a.getTitol(), String.valueOf(a.getPreu()), a.getAutor(),
                String.valueOf(a.getAny()), a.getGenere()
        };

        try {
            super.actualizarLibro(isbn, datosNuevos);
            guardarTodo();
        } catch (IOException | RuntimeException e) {
            super.actualizarLibro(isbn, datosViejos);   // rollback
            throw e;
        }
    }

    public void cargarLibros() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(bookFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] p = linea.split(";", -1);
                if (p.length < 6) continue;   // línea corrupta
                super.crearLibro(p[0], p[1], Float.parseFloat(p[2]),
                        p[3], Integer.parseInt(p[4]), p[5]);
            }
        }
    }
}