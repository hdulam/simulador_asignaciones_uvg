
import java.util.List;
import java.util.ArrayList;

public class Asesor {

    private String nombre;
    private List<Estudiante> estudiantesAsignados;
    private boolean disponibilidad;

    public Asesor(String nombre) {
        this.nombre = nombre;
        this.estudiantesAsignados = new ArrayList<>();
        this.disponibilidad = true;
    }

    public void guiarInscripcion(Estudiante estudiante) {
        System.out.println("Guia para " + estudiante.getNombre() + ": Sigue los pasos en la plataforma.");
    }

    public void recomendarMateria(Estudiante estudiante) {
        String carrera = estudiante.getCarrera();
        if ("Ingenieria".equals(carrera)) {
            System.out.println("Recomendado: POO y Calculo");
        } else if ("Medicina".equals(carrera)) {
            System.out.println("Recomendado: Biologia y Quimica");
        } else {
            System.out.println("Materias basicas: Matematicas");
        }
    }

    public void asignarEstudiante(Estudiante e) {
        estudiantesAsignados.add(e);
    }
}
