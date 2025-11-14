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
            return true;
        }
        return false;
    }

    public synchronized void liberarCupo() {
        if (estudiantesAsignados > 0) {
            estudiantesAsignados--;
            cuposDisponibles++;
        }
    }

    @Override
    public String toString() {
        return horario + " - Cupos: " + cuposDisponibles;
    }
}
