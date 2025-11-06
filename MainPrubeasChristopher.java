public class MainPrubeasChristopher {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("Laura Pérez", "20251001", "Ingenieria", "laura123");
        Materia poo = new Materia("POO");
        poo.agregarClase(new Clase("Lunes 8-10", 2));
        poo.agregarClase(new Clase("Martes 10-12", 1));

        // Inscribir
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(0));
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(1));

        // Mostrar horario
        e1.verHorario();

        // Recomendar
        Asesor a = new Asesor("Christopher");
        a.recomendarMateria(e1);

        // Desinscribir
        e1.desinscribirClase(poo);
        e1.verHorario();
    }
}
