import java.util.ArrayList;
import java.util.List;

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
        String recomendacion = getRecomendacion(estudiante);
        // cambio: mostrar recomendación por popup en GUI
        javax.swing.JOptionPane.showMessageDialog(null, recomendacion, "Recomendación", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    // Nuevo método agregado
    public String getRecomendacion(Estudiante estudiante) {
        String carrera = estudiante.getCarrera();
        if ("Ingenieria".equalsIgnoreCase(carrera)) {
            return "Recomendado: POO y Calculo";
        } else if ("Medicina".equalsIgnoreCase(carrera)) {
            return "Recomendado: Biologia y Quimica";
        } else if ("Arquitectura".equalsIgnoreCase(carrera)) {
            return "Recomendado: Dibujo y Diseño";
        } else {
            return "Materias básicas: Matematicas";
        }
    }

    public void asignarEstudiante(Estudiante e) {
        estudiantesAsignados.add(e);
    }
}
