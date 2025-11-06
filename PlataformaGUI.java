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
        plataforma = Plataforma.getInstance(); // singleton accesible
        setTitle("Sistema de Inscripción");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Login panel ---
        JPanel loginPanel = new JPanel(new GridLayout(3, 3));
        loginPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        loginPanel.add(codigoField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(new JLabel("Contraseña:"));
        passField = new JPasswordField();
        loginPanel.add(passField);
        loginBtn = new JButton("Login");
        loginPanel.add(loginBtn);
        loginPanel.add(new JLabel(""));
        loginPanel.add(new JLabel(""));
        loginPanel.add(new JLabel(""));
        add(loginPanel, BorderLayout.NORTH);

        // --- Materias area ---
        materiasArea = new JTextArea();
        materiasArea.setEditable(false);
        add(new JScrollPane(materiasArea), BorderLayout.CENTER);

        // --- Botones ---
        JPanel botonesPanel = new JPanel(new GridLayout(1, 5));
        inscribirBtn = new JButton("Inscribir");
        desinscribirBtn = new JButton("Desinscribir");
        verHorarioBtn = new JButton("Ver Horario");
        recomendarBtn = new JButton("Recomendar");
        pdfBtn = new JButton("Generar PDF");
        botonesPanel.add(inscribirBtn);
        botonesPanel.add(desinscribirBtn);
        botonesPanel.add(verHorarioBtn);
        botonesPanel.add(recomendarBtn);
        botonesPanel.add(pdfBtn);
        add(botonesPanel, BorderLayout.SOUTH);

        // --- Combo materias ---
        materiasCombo = new JComboBox<>();
        JPanel east = new JPanel(new BorderLayout());
        east.add(new JLabel("Seleccionar materia:"), BorderLayout.NORTH);
        east.add(materiasCombo, BorderLayout.CENTER);
        add(east, BorderLayout.EAST);

        // --- Actions ---
        loginBtn.addActionListener(e -> {
            String codigo = codigoField.getText().trim();
            String pass = new String(passField.getPassword());
            if (plataforma.iniciarSesion(codigo, pass)) {
                estudianteActual = plataforma.buscarEstudiante(codigo);
                JOptionPane.showMessageDialog(this, "Login exitoso: " + estudianteActual.getNombre());
                mostrarMaterias();
            } else {
                JOptionPane.showMessageDialog(this, "Login fallido");
            }
        });

        inscribirBtn.addActionListener(e -> {
            if (estudianteActual != null && materiasCombo.getSelectedItem() != null) {
                String matSel = (String) materiasCombo.getSelectedItem();
                Materia m = plataforma.buscarMateria(matSel);
                if (m != null) {
                    Clase cl = m.getClaseDisponible();
                    if (cl != null) {
                        estudianteActual.inscribirClase(m, cl);
                        plataforma.save(); // persistir cambios
                        mostrarMaterias();
                    } else {
                        JOptionPane.showMessageDialog(this, "No hay cupos disponibles");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe iniciar sesión y seleccionar una materia");
            }
        });

        desinscribirBtn.addActionListener(e -> {
            if (estudianteActual != null && materiasCombo.getSelectedItem() != null) {
                String matSel = (String) materiasCombo.getSelectedItem();
                Materia m = plataforma.buscarMateria(matSel);
                if (m != null) {
                    estudianteActual.desinscribirClase(m);
                    plataforma.save();
                    mostrarMaterias();
                    JOptionPane.showMessageDialog(this, "Desinscripción realizada");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Debe iniciar sesión y seleccionar una materia");
            }
        });

        verHorarioBtn.addActionListener(e -> {
            if (estudianteActual != null) {
                estudianteActual.verHorario();
            } else {
                JOptionPane.showMessageDialog(this, "Inicie sesión primero");
            }
        });

        recomendarBtn.addActionListener(e -> {
            if (estudianteActual != null) {
                Asesor asesor = new Asesor("Asesor Principal");
                String recomendacion = asesor.getRecomendacion(estudianteActual);
                JOptionPane.showMessageDialog(this, recomendacion, "Recomendación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Inicie sesión primero");
            }
        });

        pdfBtn.addActionListener(e -> {
            if (estudianteActual != null) {
                generarPDF();
            } else {
                JOptionPane.showMessageDialog(this, "Inicie sesión primero");
            }
        });

        mostrarMaterias();
        setVisible(true);
    }

    private void mostrarMaterias() {
        materiasArea.setText("Materias disponibles:\n\n");
        materiasCombo.removeAllItems();
        for (Materia m : plataforma.getMateriasOfertadas()) {
            String linea = m.getNombre() + "\n";
            for (Clase c : m.getClasesDisponibles()) {
                linea += "  " + c.getHorario() + " - Cupos: " + c.getCuposDisponibles() + "\n";
            }
            materiasArea.append(linea + "\n");
            materiasCombo.addItem(m.getNombre());
        }
    }

    private void generarPDF() {
        try {
            String file = estudianteActual.getCodigo() + "_horario.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            doc.add(new Paragraph("Horario de " + estudianteActual.getNombre()));
            doc.add(new Paragraph(" "));
            PdfPTable tabla = new PdfPTable(2);
            PdfPCell c1 = new PdfPCell(new Paragraph("Materia"));
            c1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(c1);
            c1 = new PdfPCell(new Paragraph("Horario"));
            c1.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            for (Map.Entry<Materia, Clase> entry : estudianteActual.getInscripciones().entrySet()) {
                tabla.addCell(entry.getKey().getNombre());
                tabla.addCell(entry.getValue().getHorario());
            }
            doc.add(tabla);
            doc.close();
            JOptionPane.showMessageDialog(this, "PDF generado: " + file);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar PDF (revisar librería iText).");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlataformaGUI::new);
    }
}
