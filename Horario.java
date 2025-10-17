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
            // Cambio: detectar solapamiento parcial (simplificado)
            String horarioExistente = e.split(" - ")[1];
            if (horarioExistente.equals(nuevoHorario)) {
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

    // Nuevo método agregado
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
}
