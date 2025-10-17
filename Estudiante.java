import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class Estudiante {

    private String nombre;
    private String codigo;
    private String carrera;
    private Map<Materia, Clase> inscripciones;
    private Horario horario;

    public Estudiante(String nombre, String codigo) {
        this(nombre, codigo, "Ingeniería");
    }

    public Estudiante(String nombre, String codigo, String carrera) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = carrera;
        this.inscripciones = new HashMap<>();
        this.horario = new Horario();
    }

    public void inscribirClase(Materia materia, Clase clase) {
        if (horario.verificarConflictos(clase.getHorario())) {
            if (clase.asignarCupo(this)) {
                inscripciones.put(materia, clase);
                horario.agregarMateria(materia.getNombre(), clase.getHorario());
                JOptionPane.showMessageDialog(null, "Inscrito en: " + materia.getNombre() + " - " + clase.getHorario());
            } else {
                JOptionPane.showMessageDialog(null, "No hay cupos disponibles en " + materia.getNombre());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Conflicto de horario con otra materia al intentar inscribirse en " + materia.getNombre());
        }
    }

    public void verHorario() {
        StringBuilder sb = new StringBuilder();
        sb.append("Horario de ").append(nombre).append(":\n\n");
        for (Map.Entry<Materia, Clase> entry : inscripciones.entrySet()) {
            sb.append(entry.getKey().getNombre())
              .append(" - ")
              .append(entry.getValue().getHorario())
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Horario", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Clase> getClasesInscritas() {
        return new ArrayList<>(inscripciones.values());
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getCarrera() { return carrera; }
    public Map<Materia, Clase> getInscripciones() { return inscripciones; }
    public Horario getHorario() { return horario; }
}
