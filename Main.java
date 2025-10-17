public class Main {

    public static void main(String[] args) {
        Materia poo = new Materia("POO");
        poo.agregarClase(new Clase("Lunes 8-10", 2));
        poo.agregarClase(new Clase("Martes 10-12", 1));
        poo.agregarClase(new Clase("Viernes 14-16", 3));

        Estudiante e1 = new Estudiante("Juan Pérez", "2025001");
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(1));
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(0));
        e1.verHorario();
    }
}
