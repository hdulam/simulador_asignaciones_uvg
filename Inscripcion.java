import java.io.Serializable;
import java.util.Date;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date fecha;
    private String estado;

    public void procesarInscripcion(Estudiante estudiante, Materia materia) {
        this.fecha = new Date();
        Clase clase = materia.getClaseDisponible();

        if (clase != null) {
            estudiante.inscribirClase(materia, clase);
            this.estado = "aprobada";
        } else {
            this.estado = "rechazada";
        }

        enviarNotificacion("Inscripción " + estado + " para " + materia.getNombre());
    }

    public void enviarNotificacion(String mensaje) {
        System.out.println("Notificación: " + mensaje);
    }
}
