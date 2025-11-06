import java.io.FileWriter;
import java.io.IOException;

public class ExportarHorario {

    public static void toHTML(Estudiante estudiante, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("<html><head><meta charset='utf-8'><title>Horario</title></head><body>");
            writer.write("<h2>Horario del Estudiante</h2>");
            writer.write("<p><b>Nombre:</b> " + estudiante.getNombre() + "</p>");
            writer.write("<p><b>Código:</b> " + estudiante.getCodigo() + "</p>");
            writer.write("<h3>Materias inscritas:</h3>");
            writer.write("<ul>");
            for (Clase clase : estudiante.getClasesInscritas()) {
                writer.write("<li>" + clase.getHorario() + "</li>");
            }
            writer.write("</ul>");
            writer.write("</body></html>");
            System.out.println("Archivo HTML generado en: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
