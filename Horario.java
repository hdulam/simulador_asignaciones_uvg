
import java.util.List;
import java.util.ArrayList;

public class Horario {

    private List<String> entradas;

    public Horario() {
        this.entradas = new ArrayList<>();
    }

    public void agregarMateria(String nombre, String horarioStr) {
        entradas.add(nombre + " - " + horarioStr);
    }

    public boolean verificarConflictos(String nuevoHorario) {
        for (String e : entradas) {
            if (e.contains(nuevoHorario)) {
                return false;
            }
        }
        return true;
    }

    public void mostrarHorario() {
        for (String e : entradas) {
            System.out.println(e);
        }
    }
}
