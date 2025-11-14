import java.io.Serializable;
import java.util.*;
import javax.swing.JOptionPane;

public class Estudiante implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String codigo;
    private String carrera;
    private String contraseña;
    private Map<Materia, Clase> inscripciones;
    private Horario horario;

    public Estudiante(String nombre, String codigo, String carrera, String contraseña) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = carrera;
        this.contraseña = contraseña;
        this.inscripciones = new HashMap<>();
        this.horario = new Horario();
    }

    public Estudiante(String nombre, String codigo) {
        this(nombre, codigo, "Ingenieria", "123");
    }

    public boolean checkPassword(String pass) {
        return contraseña.equals(pass);
    }

    public void inscribirClase(Materia materia, Clase clase) {
        if (!horario.verificarConflictos(clase.getHorario())) {
            JOptionPane.showMessageDialog(null,
                    "Conflicto de horario con: " + clase.getHorario());
            return;
        }

        if (clase.asignarCupo(this)) {
            inscripciones.put(materia, clase);
            horario.agregarMateria(materia.getNombre(), clase.getHorario());
            JOptionPane.showMessageDialog(null,
                    "Inscrito en: " + materia.getNombre() + " - " + clase.getHorario());
        } else {
            JOptionPane.showMessageDialog(null,
                    "No hay cupos disponibles en " + materia.getNombre());
        }
    }

    public void desinscribirClase(Materia materia) {
        Clase c = inscripciones.remove(materia);
        if (c != null) {
            c.liberarCupo();
            horario.removerMateria(materia.getNombre());
            JOptionPane.showMessageDialog(null,
                    "Se ha desinscrito de " + materia.getNombre());
        } else {
            JOptionPane.showMessageDialog(null,
                    "No estaba inscrito en esa materia");
        }
    }

    public void verHorario() {
        JOptionPane.showMessageDialog(null,
                horario.getHorarioComoString(),
                "Horario de " + nombre,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getCarrera() { return carrera; }
    public Map<Materia, Clase> getInscripciones() { return inscripciones; }

}
