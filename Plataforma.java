
import java.util.List;
import java.util.ArrayList;

public class Plataforma {

    List<Estudiante> usuarios;
    List<Materia> materiasOfertadas;
    boolean sesionActiva;

    public Plataforma() {
        this.usuarios = new ArrayList<>();
        this.materiasOfertadas = new ArrayList<>();
        this.sesionActiva = false;
        inicializarMaterias();
    }

    private void inicializarMaterias() {
        Materia poo = new Materia("POO");
        poo.agregarClase(new Clase("Lunes 8-10", 30));
        materiasOfertadas.add(poo);

        Materia mat = new Materia("Matematicas");
        mat.agregarClase(new Clase("Martes 10-12", 40));
        materiasOfertadas.add(mat);

        Materia fis = new Materia("Fisica");
        fis.agregarClase(new Clase("Miercoles 8-10", 25));
        materiasOfertadas.add(fis);

        Materia cal = new Materia("Calculo");
        cal.agregarClase(new Clase("Lunes 14-16", 35));
        materiasOfertadas.add(cal);
    }

    public boolean iniciarSesion(String codigo, String contrasena) {
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo) && contrasena.equals("123")) {
                sesionActiva = true;
                return true;
            }
        }
        return false;
    }

    public void mostrarOpciones(Estudiante estudiante) {
        System.out.println("Opciones para " + estudiante.getNombre());
        System.out.println("Materias disponibles:");
        for (Materia m : materiasOfertadas) {
            System.out.println(m.getNombre() + " - Clases: ");
            for (Clase c : m.getClasesDisponibles()) {
                System.out.println("  " + c.getHorario() + " Cupos: " + c.getCuposDisponibles());
            }
        }
    }

    public void agregarUsuario(Estudiante e) {
        usuarios.add(e);
    }

    public List<Materia> getMateriasOfertadas() {
        return materiasOfertadas;
    }

    public boolean isSesionActiva() {
        return sesionActiva;
    }

    public List<Estudiante> getUsuarios() {
        return usuarios;
    }
}
