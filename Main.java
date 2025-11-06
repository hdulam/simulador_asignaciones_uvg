public class Main {
    public static void main(String[] args) {
        Plataforma plataforma = Plataforma.getInstance();

        // Mostrar materias
        System.out.println("Materias ofertadas:");
        for (Materia m : plataforma.getMateriasOfertadas()) {
            System.out.println("- " + m.getNombre());
        }

        // Crear un estudiante de prueba (si no existe)
        Estudiante e = plataforma.buscarEstudiante("25932");
        if (e == null) {
            e = new Estudiante("Prueba", "25932", "Ingenieria", "prueba123");
            plataforma.getUsuarios().add(e);
            plataforma.save();
        }

        // Simular inscripcion con Inscripcion
        Materia poo = plataforma.buscarMateria("POO");
        if (poo != null) {
            Inscripcion ins = new Inscripcion();
            ins.procesarInscripcion(e, poo);
            plataforma.save();
        }

        System.out.println("Ejecución finalizada.");
    }
}
