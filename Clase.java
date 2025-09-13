public class Clase {
    private String horario;
    private int cuposDisponibles;

    public Clase(String horario, int cuposDisponibles) {
        this.horario = horario;
        this.cuposDisponibles = cuposDisponibles;
    }

    public String getHorario() { return horario; }
    public int getCuposDisponibles() { return cuposDisponibles; }

    public boolean asignarCupo(Estudiante estudiante) {
        if (cuposDisponibles > 0) {
            cuposDisponibles--;
            return true;
        }
        return false;
    }
}
