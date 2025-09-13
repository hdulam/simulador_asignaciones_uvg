public class Materia {
    private String nombre;
    private int cuposDisponibles;
    private String horario;

    public Materia(String nombre, int cuposDisponibles, String horario) {
        this.nombre = nombre;
        this.cuposDisponibles = cuposDisponibles;
        this.horario = horario;
    }

    public String getNombre() { return nombre; }
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
