package Objects;

import java.util.Calendar;
import java.util.Date;

public class Nota {
    String Titulo;
    String Cuerpo;
    Date fecha;
    String Id;

    public Nota(String titulo, String cuerpo) {
        Titulo = titulo;
        Cuerpo = cuerpo;
        this.fecha = Calendar.getInstance().getTime();
    }

    public Nota() {
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getCuerpo() {
        return Cuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setCuerpo(String cuerpo) {
        Cuerpo = cuerpo;
    }

    public String getId() {
        return Id;
    }
}
