public class Main {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("Juan Pérez", "2025001");
        Materia poo = new Materia("POO", 2, "Lunes 8-10");
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.procesarInscripcion(e1, poo);

        e1.verHorario();
    }
}
