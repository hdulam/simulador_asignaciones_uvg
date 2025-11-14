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
        usuarios = new ArrayList<>();
        materiasOfertadas = new ArrayList<>();

        if (!load()) {
            inicializarMaterias();
            inicializarUsuarios();
            save();
        }
    }

    public static Plataforma getInstance() {
        if (instance == null) instance = new Plataforma();
        return instance;
    }

private void inicializarMaterias() {

    materiasOfertadas = new ArrayList<>();

    // ------- INGENIERÍA -------
    Materia poo = new Materia("POO");
    poo.agregarClase(new Clase("Lunes 8-10", 30));
    poo.agregarClase(new Clase("Miércoles 10-12", 25));
    poo.agregarClase(new Clase("Viernes 14-16", 20));
    materiasOfertadas.add(poo);

    Materia calculo1 = new Materia("Cálculo I");
    calculo1.agregarClase(new Clase("Lunes 14-16", 35));
    calculo1.agregarClase(new Clase("Martes 8-10", 30));
    calculo1.agregarClase(new Clase("Jueves 10-12", 40));
    materiasOfertadas.add(calculo1);

    Materia calculo2 = new Materia("Cálculo II");
    calculo2.agregarClase(new Clase("Martes 14-16", 30));
    calculo2.agregarClase(new Clase("Viernes 10-12", 25));
    materiasOfertadas.add(calculo2);

    Materia matematicas = new Materia("Matemáticas Intermedias");
    matematicas.agregarClase(new Clase("Lunes 10-12", 40));
    matematicas.agregarClase(new Clase("Miércoles 14-16", 35));
    materiasOfertadas.add(matematicas);

    Materia fisica1 = new Materia("Física I");
    fisica1.agregarClase(new Clase("Martes 10-12", 35));
    fisica1.agregarClase(new Clase("Jueves 8-10", 30));
    materiasOfertadas.add(fisica1);

    Materia fisica2 = new Materia("Física II");
    fisica2.agregarClase(new Clase("Miércoles 8-10", 25));
    fisica2.agregarClase(new Clase("Viernes 12-14", 30));
    materiasOfertadas.add(fisica2);

    Materia ed = new Materia("Estructuras de Datos");
    ed.agregarClase(new Clase("Lunes 16-18", 25));
    ed.agregarClase(new Clase("Jueves 14-16", 20));
    materiasOfertadas.add(ed);

    Materia progBasica = new Materia("Programación Básica");
    progBasica.agregarClase(new Clase("Martes 16-18", 40));
    progBasica.agregarClase(new Clase("Viernes 8-10", 40));
    materiasOfertadas.add(progBasica);

    Materia logica = new Materia("Lógica de Sistemas");
    logica.agregarClase(new Clase("Miércoles 16-18", 30));
    materiasOfertadas.add(logica);

    Materia algoritmos = new Materia("Algoritmos");
    algoritmos.agregarClase(new Clase("Martes 12-14", 30));
    algoritmos.agregarClase(new Clase("Jueves 16-18", 25));
    materiasOfertadas.add(algoritmos);

    Materia bd = new Materia("Base de Datos");
    bd.agregarClase(new Clase("Lunes 12-14", 35));
    bd.agregarClase(new Clase("Viernes 10-12", 30));
    materiasOfertadas.add(bd);

    Materia redes = new Materia("Redes I");
    redes.agregarClase(new Clase("Martes 7-9", 25));
    redes.agregarClase(new Clase("Jueves 7-9", 25));
    materiasOfertadas.add(redes);


    // ------- MEDICINA -------
    Materia bio = new Materia("Biología General");
    bio.agregarClase(new Clase("Martes 12-14", 40));
    bio.agregarClase(new Clase("Jueves 8-10", 35));
    materiasOfertadas.add(bio);

    Materia quimica = new Materia("Química General");
    quimica.agregarClase(new Clase("Lunes 10-12", 50));
    quimica.agregarClase(new Clase("Miércoles 12-14", 40));
    materiasOfertadas.add(quimica);

    Materia organica = new Materia("Química Orgánica");
    organica.agregarClase(new Clase("Martes 14-16", 30));
    materiasOfertadas.add(organica);

    Materia anat1 = new Materia("Anatomía I");
    anat1.agregarClase(new Clase("Miércoles 7-9", 35));
    materiasOfertadas.add(anat1);

    Materia anat2 = new Materia("Anatomía II");
    anat2.agregarClase(new Clase("Viernes 14-16", 30));
    materiasOfertadas.add(anat2);

    Materia fisio = new Materia("Fisiología");
    fisio.agregarClase(new Clase("Lunes 16-18", 30));
    materiasOfertadas.add(fisio);


    // ------- ARQUITECTURA -------
    Materia dis1 = new Materia("Diseño I");
    dis1.agregarClase(new Clase("Martes 8-12", 20));  // taller
    materiasOfertadas.add(dis1);

    Materia dis2 = new Materia("Diseño II");
    dis2.agregarClase(new Clase("Jueves 8-12", 20));
    materiasOfertadas.add(dis2);

    Materia mod3d = new Materia("Modelado 3D");
    mod3d.agregarClase(new Clase("Miércoles 14-16", 25));
    materiasOfertadas.add(mod3d);

    Materia geo = new Materia("Geometría Descriptiva");
    geo.agregarClase(new Clase("Lunes 8-10", 35));
    materiasOfertadas.add(geo);

    Materia dibujo = new Materia("Dibujo Técnico");
    dibujo.agregarClase(new Clase("Viernes 8-10", 40));
    materiasOfertadas.add(dibujo);
}


    private void inicializarUsuarios() {
        usuarios.add(new Estudiante("Juan Pérez", "25932", "Ingenieria", "juan25932"));
        usuarios.add(new Estudiante("Ana Gómez", "251293", "Medicina", "ana251293"));
        usuarios.add(new Estudiante("Luis Martínez", "251190", "Arquitectura", "luis251190"));
    }

    public boolean iniciarSesion(String codigo, String contrasena) {
        for (Estudiante e : usuarios) {
            if (e.getCodigo().equals(codigo) && e.checkPassword(contrasena)) return true;
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

    public boolean save() {
        try (ObjectOutputStream oosUsers = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
             ObjectOutputStream oosMaterias = new ObjectOutputStream(new FileOutputStream(MATERIAS_FILE))) {

            oosUsers.writeObject(usuarios);
            oosMaterias.writeObject(materiasOfertadas);
            return true;

        } catch (IOException e) {
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
            return false;
        }
    }
}
