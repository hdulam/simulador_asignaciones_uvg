import java.io.Serializable;

public class Clase implements Serializable {
    private static final long serialVersionUID = 1L;

    private String horario;
    private int cuposDisponibles;
    private int estudiantesAsignados;

    public Clase(String horario, int cuposDisponibles) {
        this.horario = horario;
        this.cuposDisponibles = cuposDisponibles;
        this.estudiantesAsignados = 0;
    }

    public String getHorario() { return horario; }
    public int getCuposDisponibles() { return cuposDisponibles; }

    public synchronized boolean asignarCupo(Estudiante estudiante) {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            estudiantesAsignados++;
            // Notificar (sin dependencias GUI en la lógica de negocio idealmente)
            System.out.println("Cupo asignado. Quedan " + cuposDisponibles + " cupos.");
            return true;
        } else {
            System.out.println("No hay cupos disponibles en este horario.");
            return false;
        }
    }

    public synchronized void liberarCupo() {
        if (estudiantesAsignados > 0) {
            estudiantesAsignados--;
            cuposDisponibles++;
            System.out.println("Cupo liberado. Quedan " + cuposDisponibles + " cupos.");
        }
    }
}
