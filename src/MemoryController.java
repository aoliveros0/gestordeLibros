import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MemoryController {
    private HashMap<String, Libros> libres = new HashMap<>();

    // Este metodo es el que crea los libros con los datos solicitados.
    // Luego hacemos una clase para el libro y poner los datos necesarios.
    // Por ultimo añadimos el libro a nuestra biblioteca y lo devolvemos.
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException {
        Libros nuevoLibro = new Libros(ISBN, titol, preu, autor, any, genere);
        libres.put(nuevoLibro.getISBN(), nuevoLibro);
        return nuevoLibro;
    }

    // Este metodo hace que lamba muestre toda la lista de los libros que se encuentran.
    public List<Libros> buscarTodosLibros() {
        return libres.values().stream().toList();
    }

    // Este hace algo parecido al anterior pero buscamos uno en concreto gracias al ISBN.
    public Libros buscarUnLibro(String isbnBuscar) {
        return libres.get(isbnBuscar);
    }

    // El metodo actualizar basicamente coge nuestra clave principal que es el ISBN y hace un array para introducir los nuevos datos.
    // Hacemos un if que si el libroactualizar es igual o distinto a null.
    // Y dentro de ese if añadimos los nuevos datos modificados.
    // Al tener un float y un int hay que hacer una conversion a los datos de precio y año.
    public void actualizarLibro(String isbnActualizar, String[] datos)
            throws IOException {
        Libros libroActualizar = libres.get(isbnActualizar);
        if (libroActualizar != null) {
            libroActualizar.setTitol(datos[0]);
            libroActualizar.setPreu(Float.parseFloat(datos[1]));
            libroActualizar.setAutor(datos[2]);
            libroActualizar.setAny(Integer.parseInt(datos[3]));
            libroActualizar.setGenere(datos[4]);
        }
    }

    // En este metodo basicamente escribes el ISBN del libro que quieres eliminar y desaparece de la biblioteca.
    public boolean eliminarLibro(String isbnEliminar) throws IOException {
        Libros borrado = libres.remove(isbnEliminar);
        return borrado != null;
    }
}