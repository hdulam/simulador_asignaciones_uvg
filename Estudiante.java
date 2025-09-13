import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String nombre;
    private String codigo;
    private List<Clase> clasesInscritas;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.clasesInscritas = new ArrayList<>();
    }

    public void inscribirClase(Materia materia, Clase clase) {
        if (clase.asignarCupo(this)) {
            clasesInscritas.add(clase);
            System.out.println("Inscrito en " + materia.getNombre() + " - " + clase.getHorario());
        } else {
            System.out.println("No hay cupos disponibles en esa clase de " + materia.getNombre());
        }
    }

    public void verHorario() {
        System.out.println("Horario de " + nombre);
        for (Clase c : clasesInscritas) {
            System.out.println(c.getHorario());
        }
    }
}
