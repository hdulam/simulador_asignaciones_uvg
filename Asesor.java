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

    public void asignarEstudiante(Estudiante e) {
        estudiantesAsignados.add(e);
    }

    public void recomendarMateria(Estudiante estudiante) {
        String recomendacion = getRecomendacion(estudiante);
        JOptionPane.showMessageDialog(null, recomendacion, "Recomendación personalizada", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Recomienda materias basadas en las clases ya inscritas.
     * Si el estudiante no tiene inscripciones, recomienda materias base por carrera.
     */
    public String getRecomendacion(Estudiante estudiante) {
        if (estudiante.getInscripciones().isEmpty()) {
            return recomendacionesBase(estudiante.getCarrera());
        }

        StringBuilder rec = new StringBuilder();
        rec.append("Basado en tus clases actuales:\n\n");

        estudiante.getInscripciones().forEach((materia, clase) -> {
            String m = materia.getNombre().toLowerCase();

            if (m.contains("poo") || m.contains("programación")) {
                rec.append("• Te recomiendo tomar: Estructuras de Datos\n");
                rec.append("  → Complementa POO con teoría de algoritmos.\n\n");
            }

            if (m.contains("matematic")) {
                rec.append("• Te recomiendo tomar: Estadística I\n");
                rec.append("  → Base fundamental para cursos cuantitativos.\n\n");
            }

            if (m.contains("calculo")) {
                rec.append("• Te recomiendo tomar: Física 1\n");
                rec.append("  → Relación directa con funciones y derivadas.\n\n");
            }

            if (m.contains("fisica")) {
                rec.append("• Te recomiendo tomar: Laboratorio de Física\n");
                rec.append("  → Refuerza conceptos con práctica.\n\n");
            }

            if (m.contains("dibujo") || m.contains("diseño")) {
                rec.append("• Te recomiendo tomar: Modelado 3D\n");
                rec.append("  → Ampliación para arquitectura/DI.\n\n");
            }

            if (m.contains("biologia")) {
                rec.append("• Te recomiendo tomar: Anatomía I\n");
                rec.append("  → Complemento esencial para Medicina.\n\n");
            }

            if (m.contains("quimica")) {
                rec.append("• Te recomiendo tomar: Química Orgánica\n");
                rec.append("  → Segundo nivel recomendado.\n\n");
            }
        });

        if (rec.toString().trim().isEmpty()) {
            return "No encontré recomendaciones específicas.\nTe sugiero revisar el plan de estudios.";
        }

        return rec.toString();
    }

    /**
     * Recomendaciones para estudiantes sin materias inscritas aún.
     */
    private String recomendacionesBase(String carrera) {
        carrera = carrera.toLowerCase();

        if (carrera.contains("ingenier")) {
            return """
                Recomendaciones para Ingeniería:
                • Programación Básica
                • Matemática Intermedia
                • Física 1
                • Introducción a Algoritmos
                """;
        }

        if (carrera.contains("medici")) {
            return """
                Recomendaciones para Medicina:
                • Biología General
                • Química General
                • Anatomía I
                """;
        }

        if (carrera.contains("arquit")) {
            return """
                Recomendaciones para Arquitectura:
                • Dibujo Técnico
                • Geometría Descriptiva
                • Diseño Básico
                """;
        }

        return """
            Recomendaciones generales:
            • Matemáticas Básicas
            • Comunicación Escrita
            • Introducción a Computación
            """;
    }
}
