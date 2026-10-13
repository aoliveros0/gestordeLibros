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

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }
    // Constructor.
    public Libros(String ISBN, String titol, float preu, String autor, int any, String genere) {
        this.ISBN = ISBN;
        this.titol = titol;
        this.preu = preu;
        this.autor = autor;
        this.any = any;
        this.genere = genere;
    }


}