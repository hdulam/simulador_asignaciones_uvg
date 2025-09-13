
import java.util.Date;

public class Inscripcion {

    private Date fecha;
    private String estado;

    public void procesarInscripcion(Estudiante estudiante, Materia materia) {
        this.fecha = new Date();
        Clase clase = materia.getClaseDisponible();
        if (clase != null) {
            if (clase.asignarCupo(estudiante)) {
                this.estado = "aprobada";
                estudiante.inscribirClase(materia, clase);
            } else {
                this.estado = "rechazada";
            }
        } else {
            this.estado = "rechazada";
        }
        enviarNotificacion("Inscripción " + estado + " para " + materia.getNombre());
    }

    public void enviarNotificacion(String mensaje) {
        System.out.println("Notificación: " + mensaje);
    }
}
