import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Asesor implements Serializable {
    private static final long serialVersionUID = 1L;

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
        JOptionPane.showMessageDialog(null, recomendacion, "Recomendación", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getRecomendacion(Estudiante estudiante) {
        String carrera = estudiante.getCarrera();
        if ("Ingenieria".equalsIgnoreCase(carrera)) {
            return "Recomendado: POO y Calculo";
        } else if ("Medicina".equalsIgnoreCase(carrera)) {
            return "Recomendado: Biologia y Quimica";
        } else if ("Arquitectura".equalsIgnoreCase(carrera)) {
            return "Recomendado: Dibujo y Diseño";
        } else {
            return "Materias básicas: Matemáticas";
        }
    }

    public void asignarEstudiante(Estudiante e) {
        estudiantesAsignados.add(e);
    }
}
