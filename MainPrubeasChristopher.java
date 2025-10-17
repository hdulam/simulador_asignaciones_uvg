public class MainPruebasChristopher {
    public static void main(String[] args) {
        Estudiante e1 = new Estudiante("Laura Pérez", "20251001", "Ingenieria");
        Materia poo = new Materia("POO");
        poo.agregarClase(new Clase("Lunes 8-10", 2));
        poo.agregarClase(new Clase("Martes 10-12", 1));
        
        e1.inscribirClase(poo, poo.getClasesDisponibles().get(0));

        Horario h = e1.getHorario();
        System.out.println("Horario actual:\n" + h.getHorarioComoString());

        Asesor a = new Asesor("Christopher");
        a.recomendarMateria(e1);
    }
}
