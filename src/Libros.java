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
        if(titol == null || titol.isEmpty()){
            throw new RuntimeException("Titol nul !");
        }
        this.titol = titol;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        if(preu <= 0 || Float.isNaN(preu)){
            throw new IllegalArgumentException("Eso no se puede");

        }
        this.preu = preu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if(autor == null || autor.isEmpty()){
            throw new IllegalArgumentException("Autor nul !");
        }
        this.autor = autor;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        if(any < 1950 || any > 2026){
            throw new IllegalArgumentException("Eso no se puede");
        }
        this.any = any;

    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        if(genere == null || genere.isEmpty()){
            throw new IllegalArgumentException("Eso no se puede");
        }
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