import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String nombre;
    private String codigo;
    private List<Materia> materiasInscritas;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.materiasInscritas = new ArrayList<>();
    }

    public void inscribirMateria(Materia materia) {
        if (materia.asignarCupo(this)) {
            materiasInscritas.add(materia);
            System.out.println("Inscrito en: " + materia.getNombre());
        } else {
            System.out.println("No hay cupos disponibles en " + materia.getNombre());
        }
    }

    public void verHorario() {
        System.out.println("Horario de " + nombre);
        for (Materia m : materiasInscritas) {
            System.out.println(m.getNombre() + " - " + m.getHorario());
        }
    }
}
