import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Materia implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private List<Clase> clases;

    public Materia(String nombre) {
        this.nombre = nombre;
        this.clases = new ArrayList<>();
    }

    public void agregarClase(Clase clase) { clases.add(clase); }

    public List<Clase> getClasesDisponibles() { return new ArrayList<>(clases); }

    public List<Clase> getClasesDisponiblesConCupos() {
        List<Clase> disponibles = new ArrayList<>();
        for (Clase c : clases) {
            if (c.getCuposDisponibles() > 0) {
                disponibles.add(c);
            }
        }
        return disponibles;
    }

    public Clase getClaseDisponible() {
        for (Clase c : clases) {
            if (c.getCuposDisponibles() > 0) return c;
        }
        return null;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}
