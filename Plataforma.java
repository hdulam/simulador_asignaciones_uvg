import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Plataforma implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Plataforma instance;

    private List<Estudiante> usuarios;
    private List<Materia> materiasOfertadas;

    private final String USERS_FILE = "usuarios.dat";
    private final String MATERIAS_FILE = "materias.dat";

    private Plataforma() {
        this.usuarios = new ArrayList<>();
        this.materiasOfertadas = new ArrayList<>();
        // intentar cargar persistencia; si no existe, inicializar por defecto
        if (!load()) {
            inicializarMaterias();
            inicializarUsuarios();
            save();
        }
    }

    public static Plataforma getInstance() {
        if (instance == null) {
            instance = new Plataforma();
        }
        return instance;
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
        usuarios.add(new Estudiante("Juan Pérez", "25932", "Ingenieria", "juan25932"));
        usuarios.add(new Estudiante("Ana Gómez", "251293", "Medicina", "ana251293"));
        usuarios.add(new Estudiante("Luis Martínez", "251190", "Arquitectura", "luis251190"));
    }

    public boolean iniciarSesion(String codigo, String contrasena) {
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo) && e.checkPassword(contrasena)) {
                return true;
            }
        }
        return false;
    }

    public Estudiante buscarEstudiante(String codigo) {
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo)) return e;
        }
        return null;
    }

    public Materia buscarMateria(String nombre) {
        for (Materia m : materiasOfertadas) {
            if (m.getNombre().equalsIgnoreCase(nombre)) return m;
        }
        return null;
    }

    public List<Materia> getMateriasOfertadas() {
        return materiasOfertadas;
    }

    public List<Estudiante> getUsuarios() {
        return usuarios;
    }

    // Persistencia
    public boolean save() {
        try (ObjectOutputStream oosUsers = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
             ObjectOutputStream oosMaterias = new ObjectOutputStream(new FileOutputStream(MATERIAS_FILE))) {
            oosUsers.writeObject(usuarios);
            oosMaterias.writeObject(materiasOfertadas);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean load() {
        try (ObjectInputStream oisUsers = new ObjectInputStream(new FileInputStream(USERS_FILE));
             ObjectInputStream oisMaterias = new ObjectInputStream(new FileInputStream(MATERIAS_FILE))) {
            usuarios = (List<Estudiante>) oisUsers.readObject();
            materiasOfertadas = (List<Materia>) oisMaterias.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            // archivos no existen o fallo: iniciar por defecto
            return false;
        }
    }
}
