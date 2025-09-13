import java.util.ArrayList;
import java.util.List;

public class Materia {
    private String nombre;
    private List<Clase> clasesDisponibles;

    public Materia(String nombre) {
        this.nombre = nombre;
        this.clasesDisponibles = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public List<Clase> getClasesDisponibles() { return clasesDisponibles; }

    public void agregarClase(Clase clase) {
        clasesDisponibles.add(clase);
    }
}
