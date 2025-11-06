import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> entradas;

    public Horario() {
        this.entradas = new ArrayList<>();
    }

    public void agregarMateria(String nombre, String horarioStr) {
        entradas.add(nombre + " - " + horarioStr);
    }

    public boolean verificarConflictos(String nuevoHorario) {
        // Implementación simple: evita igualdad exacta; se puede mejorar para solapamientos reales.
        for (String e : entradas) {
            String horarioExistente = e.split(" - ")[1];
            if (horarioExistente.equalsIgnoreCase(nuevoHorario)) {
                return false; // conflicto
            }
        }
        return true; // sin conflicto
    }

    public void mostrarHorario() {
        for (String e : entradas) {
            System.out.println(e);
        }
    }

    public String getHorarioComoString() {
        StringBuilder sb = new StringBuilder();
        for (String e : entradas) {
            sb.append(e).append("\n");
        }
        return sb.toString();
    }

    public List<String> getEntradas() {
        return new ArrayList<>(entradas);
    }

    public void removerMateria(String nombre) {
        entradas.removeIf(s -> s.startsWith(nombre + " - "));
    }
}
