import javax.swing.JOptionPane;

public class Clase {

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

    public boolean asignarCupo(Estudiante estudiante) {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            estudiantesAsignados++;
            JOptionPane.showMessageDialog(null, "Cupo asignado. Quedan " + cuposDisponibles + " cupos.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No hay cupos disponibles en este horario.");
            return false;
        }
    }
}
