
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Estudiante {

    private String nombre;
    private String codigo;
    private String carrera;
    private Map<Materia, Clase> inscripciones;
    private Horario horario;

    public Estudiante(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = "Ingenieria";
        this.inscripciones = new HashMap<>();
        this.horario = new Horario();
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
                System.out.println("Inscrito en: " + materia.getNombre() + " - " + clase.getHorario());
            } else {
                System.out.println("No hay cupos disponibles en " + materia.getNombre());
            }
        } else {
            System.out.println("Conflicto de horario con " + materia.getNombre());
        }
    }

    public void verHorario() {
        System.out.println("Horario de " + nombre);
        horario.mostrarHorario();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCarrera() {
        return carrera;
    }

    public Map<Materia, Clase> getInscripciones() {
        return inscripciones;
    }

    public Horario getHorario() {
        return horario;
    }
}
