import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class MemoryController {

    private HashMap<String, Libros> libres = new HashMap<>();

    // Este metodo es el que crea los libros con los datos solicitados.
    // Luego hacemos una clase para el libro y poner los datos necesarios.
    // Por ultimo añadimos el libro a nuestra biblioteca y lo devolvemos con los dtos correspondientes.
    // el libres.containsKey como es el ISBN si creamos uno como por ejemplo 1 si lo volvemos a repetir te salta el error de que este ISBN ya esta en uso.
    public Libros crearLibro(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws IOException {
        if(libres.containsKey(ISBN)) {
            throw new IllegalArgumentException("ISBN ja existe");
        }
        Libros nuevoLibro = new Libros(ISBN, titol, preu, autor, any, genere);
        libres.put(nuevoLibro.getISBN(), nuevoLibro);
        return nuevoLibro;
    }

    // Este metodo hace que lamba muestre toda la lista de los libros que se encuentran.
    // Aqui basicamente si escoges la opcion 2 y selecionas mostrar todos te aparecen todos los libros que se encuentran actualmente en el fichero.
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
