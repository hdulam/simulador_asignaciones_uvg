import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton loginBtn, inscribirBtn, verHorarioBtn, recomendarBtn, pdfBtn;
    private Estudiante estudianteActual;
    private JComboBox<String> materiasCombo;

    public PlataformaGUI() {
        plataforma = new Plataforma();

        // --- USUARIOS PREDEFINIDOS ---
        plataforma.agregarUsuario(new Estudiante("Juan Pérez", "25932", "Ingenieria"));
        plataforma.agregarUsuario(new Estudiante("Ana López", "251293", "Medicina"));
        plataforma.agregarUsuario(new Estudiante("Carlos Méndez", "251190", "Ingenieria"));
        // Puedes agregar más usuarios aquí si quieres

        setTitle("Sistema de Inscripcion");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(new JLabel("Codigo:"));
        codigoField = new JTextField();
        loginPanel.add(codigoField);
        loginPanel.add(new JLabel("Contrasena:"));
        passField = new JPasswordField();
        loginPanel.add(passField);
        loginBtn = new JButton("Login");
        loginPanel.add(loginBtn);
        loginPanel.add(new JLabel(""));
        add(loginPanel, BorderLayout.NORTH);

        materiasArea = new JTextArea();
        add(new JScrollPane(materiasArea), BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 5));
        inscribirBtn = new JButton("Inscribir");
        verHorarioBtn = new JButton("Ver Horario");
        recomendarBtn = new JButton("Recomendar");
        pdfBtn = new JButton("Generar PDF");
        botonesPanel.add(inscribirBtn);
        botonesPanel.add(verHorarioBtn);
        botonesPanel.add(recomendarBtn);
        botonesPanel.add(pdfBtn);
        add(botonesPanel, BorderLayout.SOUTH);

        materiasCombo = new JComboBox<>();
        add(materiasCombo, BorderLayout.EAST);

        // --- ACCIONES ---
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = codigoField.getText();
                String pass = new String(passField.getPassword());
                if (plataforma.iniciarSesion(codigo, pass)) {
                    estudianteActual = buscarEstudiante(codigo);
                    mostrarMaterias();
                } else {
                    JOptionPane.showMessageDialog(null, "Login fallido. Usuario o contraseña incorrectos.");
                }
            }
        });

        inscribirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null && materiasCombo.getSelectedItem() != null) {
                    String matSel = (String) materiasCombo.getSelectedItem();
                    Materia mat = buscarMateria(matSel);
                    if (mat != null) {
                        Clase cl = null;
                        for (Clase cc : mat.getClasesDisponibles()) {
                            if (cc.getCuposDisponibles() > 0) {
                                cl = cc;
                                break;
                            }
                        }
                        if (cl != null) {
                            estudianteActual.inscribirClase(mat, cl);
                            mostrarMaterias();
                        } else {
                            JOptionPane.showMessageDialog(null, "No hay cupos disponibles");
                        }
                    }
                }
            }
        });

        verHorarioBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null) {
                    StringBuilder horarioStr = new StringBuilder();
                    horarioStr.append("Horario de ").append(estudianteActual.getNombre()).append(":\n\n");
                    for (String entrada : estudianteActual.getHorario().getEntradas()) {
                        horarioStr.append(entrada).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, horarioStr.toString(), "Horario", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        recomendarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null) {
                    Asesor asesor = new Asesor("Asesor Principal");
                    String carrera = estudianteActual.getCarrera();
                    String mensaje;
                    if ("Ingenieria".equals(carrera)) {
                        mensaje = "Recomendado: POO y Calculo";
                    } else if ("Medicina".equals(carrera)) {
                        mensaje = "Recomendado: Biologia y Quimica";
                    } else {
                        mensaje = "Materias basicas: Matematicas";
                    }
                    JOptionPane.showMessageDialog(null, mensaje, "Recomendación del Asesor", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        pdfBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (estudianteActual != null) {
                    generarPDF();
                }
            }
        });

        setVisible(true);
    }

    private void mostrarMaterias() {
        materiasArea.setText("Materias disponibles:\n");
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

    private Estudiante buscarEstudiante(String codigo) {
        for (Estudiante e : plataforma.getUsuarios()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

    private Materia buscarMateria(String nombre) {
        for (Materia m : plataforma.getMateriasOfertadas()) {
            if (m.getNombre().equals(nombre)) {
                return m;
            }
        }
        return null;
    }

    private void generarPDF() {
        try {
            String file = estudianteActual.getCodigo() + "_horario.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(file));
            doc.open();
            Paragraph titulo = new Paragraph("Horario de " + estudianteActual.getNombre());
            doc.add(titulo);
            doc.add(new Paragraph(" "));
            PdfPTable tabla = new PdfPTable(2);
            PdfPCell c1 = new PdfPCell(new Paragraph("Materia"));
            c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            tabla.addCell(c1);
            c1 = new PdfPCell(new Paragraph("Horario"));
            c1.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            tabla.addCell(c1);
            tabla.setHeaderRows(1);
            for (Map.Entry<Materia, Clase> entry : estudianteActual.getInscripciones().entrySet()) {
                tabla.addCell(entry.getKey().getNombre());
                tabla.addCell(entry.getValue().getHorario());
            }
            doc.add(tabla);
            doc.close();
            JOptionPane.showMessageDialog(null, "PDF generado: " + file);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar PDF");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlataformaGUI());
    }
}
