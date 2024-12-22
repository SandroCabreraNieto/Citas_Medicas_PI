package pe.cibertec.edu.pe.citas.medicas.models;

public class Mensaje {

    private String texto;
    private String emisor;
    private String receptor;

    public Mensaje() {}

    public Mensaje(String texto, String emisor, String receptor) {
        this.texto = texto;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }
}
