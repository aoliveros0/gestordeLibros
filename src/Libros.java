import excepciones.*;

import java.io.IOException;

public class Libros {
    private String ISBN;
    private String titol;
    private float preu;
    private String autor;
    private int any;
    private String genere;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) throws ISBNException {
        if(ISBN == null || ISBN.equals("") ||ISBN.isEmpty()){
            throw new ISBNException("No es correcto ese ISBN");
        }
        this.ISBN = ISBN;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) throws TitolException {
        if(titol == null || titol.isEmpty()){
            throw new TitolException("Titol nul !");
        }
        this.titol = titol;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) throws PreuException {
        if(preu <= 0 || Float.isNaN(preu)){
            throw new PreuException("Eso no se puede");

        }
        this.preu = preu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) throws AutorException {
        if(autor == null || autor.isEmpty()){
            throw new AutorException("Autor nul !");
        }
        this.autor = autor;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) throws AnyException {
        if(any < 1950 || any > 2026){
            throw new AnyException("Eso no se puede");
        }
        this.any = any;

    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) throws GenereException {
        if(genere == null || genere.isEmpty()){
            throw new GenereException("Eso no se puede");
        }
        this.genere = genere;
    }
    // Constructor.
    public Libros(String ISBN, String titol, float preu, String autor, int any, String genere)
            throws ISBNException, TitolException, PreuException, AutorException, AnyException, GenereException {
        this.setISBN(ISBN);
        this.setTitol(titol);
        this.setPreu(preu);
        this.setAutor(autor);
        this.setAny(any);
        this.setGenere(genere);
    }


}