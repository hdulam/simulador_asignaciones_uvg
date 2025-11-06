import java.io.Serializable;
import java.util.*;

import javax.swing.JOptionPane;

public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String codigo;
    private String carrera;
    private String contraseña; // contraseña individual
    private Map<Materia, Clase> inscripciones;
    private Horario horario;

    public Estudiante(String nombre, String codigo) {
        this(nombre, codigo, "Ingenieria", "123"); // default password (se recomienda cambiar)
    }

    public Estudiante(String nombre, String codigo, String carrera) {
        this(nombre, codigo, carrera, "123");
    }

    public Estudiante(String nombre, String codigo, String carrera, String contraseña) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = carrera;
        this.contraseña = contraseña;
        this.inscripciones = new HashMap<>();
        this.horario = new Horario();
    }

    public boolean checkPassword(String pass) {
        return contraseña != null && contraseña.equals(pass);
    }

    public void setContraseña(String nueva) {
        this.contraseña = nueva;
    }

    public void inscribirClase(Materia materia, Clase clase) {
        if (!horario.verificarConflictos(clase.getHorario())) {
            JOptionPane.showMessageDialog(null, "Conflicto de horario al intentar inscribirse en " + materia.getNombre());
            return;
        }
        if (clase.asignarCupo(this)) {
            inscripciones.put(materia, clase);
            horario.agregarMateria(materia.getNombre(), clase.getHorario());
            JOptionPane.showMessageDialog(null, "Inscrito en: " + materia.getNombre() + " - " + clase.getHorario());
        } else {
            JOptionPane.showMessageDialog(null, "No hay cupos disponibles en " + materia.getNombre());
        }
    }

    public void desinscribirClase(Materia materia) {
        Clase c = inscripciones.remove(materia);
        if (c != null) {
            c.liberarCupo();
            horario.removerMateria(materia.getNombre());
            JOptionPane.showMessageDialog(null, "Se ha desinscrito de " + materia.getNombre());
        } else {
            JOptionPane.showMessageDialog(null, "No está inscrito en " + materia.getNombre());
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
        if (inscripciones.isEmpty()) sb.append("No hay clases inscritas\n");
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
