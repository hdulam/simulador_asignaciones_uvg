import java.util.ArrayList;
import java.util.List;

public class Plataforma {

    private List<Estudiante> usuarios;
    private List<Materia> materiasOfertadas;

    public Plataforma() {
        this.usuarios = new ArrayList<>();
        this.materiasOfertadas = new ArrayList<>();
        inicializarMaterias();
        inicializarUsuarios();
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

    private void inicializarUsuarios() {
        usuarios.add(new Estudiante("Juan Pérez", "2025001"));
        usuarios.add(new Estudiante("Ana Gómez", "2025002"));
        usuarios.add(new Estudiante("Luis Martínez", "2025003"));
    }

    public boolean iniciarSesion(String codigo, String contrasena) {
        // Contraseña fija "123"
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo) && "123".equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public Estudiante buscarEstudiante(String codigo) {
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

    public List<Materia> getMateriasOfertadas() {
        return materiasOfertadas;
    }

    public List<Estudiante> getUsuarios() {
        return usuarios;
    }
}
