public class Main {
    public static void main(String[] args) {

        //demo para usarse en la creacion de gui
        // Crear materia con diferentes clases
        Materia poo = new Materia("POO");
        poo.agregarClase(new Clase("Lunes 8-10", 2));
        poo.agregarClase(new Clase("Martes 10-12", 1));
        poo.agregarClase(new Clase("Viernes 14-16", 3));

        // Crear estudiante
        Estudiante e1 = new Estudiante("Juan Pérez", "2025001");

        // Inscribir en una clase específica
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(1)); // martes 10-12
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(0)); // lunes 8-10

        // Ver horario
        e1.verHorario();
    }
}
