import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PlataformaGUI extends JFrame {

    private Plataforma plataforma;
    private JTextField codigoField;
    private JPasswordField passField;
    private JTextArea materiasArea;
    private JButton loginBtn, inscribirBtn, verHorarioBtn, recomendarBtn, pdfBtn, desinscribirBtn;
    private Estudiante estudianteActual;
    private JComboBox<String> materiasCombo;

    public PlataformaGUI() {
        plataforma = Plataforma.getInstance(); 
        setTitle("Sistema de Inscripción UVG");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // LOGIN PANEL
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        loginPanel.add(codigoField);

        loginPanel.add(new JLabel("Contraseña:"));
        passField = new JPasswordField();
        loginPanel.add(passField);

        loginBtn = new JButton("Iniciar Sesión");
        loginPanel.add(loginBtn);

        add(loginPanel, BorderLayout.NORTH);

        // AREA CENTRAL
        materiasArea = new JTextArea();
        materiasArea.setEditable(false);
        add(new JScrollPane(materiasArea), BorderLayout.CENTER);

        // COMBO MATERIAS
        materiasCombo = new JComboBox<>();
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.add(new JLabel("Selecciona clase:"), BorderLayout.NORTH);
        comboPanel.add(materiasCombo, BorderLayout.CENTER);
        add(comboPanel, BorderLayout.EAST);

        // BOTONES
        JPanel botones = new JPanel(new GridLayout(1, 5));
        inscribirBtn = new JButton("Inscribir");
        desinscribirBtn = new JButton("Desinscribir");
        verHorarioBtn = new JButton("Ver Horario");
        recomendarBtn = new JButton("Recomendación");
        pdfBtn = new JButton("Generar PDF");

        botones.add(inscribirBtn);
        botones.add(desinscribirBtn);
        botones.add(verHorarioBtn);
        botones.add(recomendarBtn);
        botones.add(pdfBtn);

        add(botones, BorderLayout.SOUTH);

        // EVENTOS
        loginBtn.addActionListener(e -> login());
        inscribirBtn.addActionListener(e -> inscribir());
        desinscribirBtn.addActionListener(e -> desinscribir());
        verHorarioBtn.addActionListener(e -> verHorario());
        recomendarBtn.addActionListener(e -> recomendar());
        pdfBtn.addActionListener(e -> generarPDF());

        mostrarMaterias();
        setVisible(true);
    }

    private void login() {
        String codigo = codigoField.getText().trim();
        String pass = new String(passField.getPassword());
        if (plataforma.iniciarSesion(codigo, pass)) {
            estudianteActual = plataforma.buscarEstudiante(codigo);
            JOptionPane.showMessageDialog(this, "Bienvenido: " + estudianteActual.getNombre());
            mostrarMaterias();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }
    }

    private void mostrarMaterias() {
        materiasArea.setText("Materias disponibles:\n\n");
        materiasCombo.removeAllItems();

        for (Materia m : plataforma.getMateriasOfertadas()) {
            materiasArea.append(m.getNombre() + "\n");
            for (Clase c : m.getClasesDisponibles()) {
                materiasArea.append("  - " + c.getHorario() + " (Cupos: " + c.getCuposDisponibles() + ")\n");
                materiasCombo.addItem(m.getNombre() + " - " + c.getHorario());
            }
            materiasArea.append("\n");
        }
    }

    private void inscribir() {
        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión");
            return;
        }

        String sel = (String) materiasCombo.getSelectedItem();
        if (sel == null) return;

        String[] partes = sel.split(" - ", 2);
        Materia m = plataforma.buscarMateria(partes[0]);

        Clase clase = m.getClasesDisponibles().stream()
                .filter(c -> c.getHorario().equals(partes[1]))
                .findFirst().orElse(null);

        estudianteActual.inscribirClase(m, clase);
        plataforma.save();
        mostrarMaterias();
    }

    private void desinscribir() {
        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión");
            return;
        }

        String sel = (String) materiasCombo.getSelectedItem();
        if (sel == null) return;

        String[] partes = sel.split(" - ", 2);
        Materia m = plataforma.buscarMateria(partes[0]);

        estudianteActual.desinscribirClase(m);
        plataforma.save();
        mostrarMaterias();
    }

    private void verHorario() {
        if (estudianteActual != null) estudianteActual.verHorario();
    }

    private void recomendar() {
        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión");
            return;
        }
        Asesor a = new Asesor("Asesor UVG");
        JOptionPane.showMessageDialog(this, a.getRecomendacion(estudianteActual));
    }

    private void generarPDF() {
        try {
            String file = estudianteActual.getCodigo() + "_horario.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();

            doc.add(new Paragraph("Horario de " + estudianteActual.getNombre()));
            doc.add(new Paragraph("\n"));

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Materia");
            tabla.addCell("Horario");

            for (Map.Entry<Materia, Clase> entry : estudianteActual.getInscripciones().entrySet()) {
                tabla.addCell(entry.getKey().getNombre());
                tabla.addCell(entry.getValue().getHorario());
            }

            doc.add(tabla);
            doc.close();

            JOptionPane.showMessageDialog(this, "PDF generado: " + file);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF");
        }
    }
}
