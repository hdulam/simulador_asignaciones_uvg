import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.Map;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PlataformaGUI extends JFrame {

    private Plataforma plataforma;
    private JTextField codigoField;
    private JPasswordField passField;
    private JTextArea materiasArea;
    private JButton loginBtn, inscribirBtn, verHorarioBtn, recomendarBtn, pdfBtn, desinscribirBtn;
    private JButton logoutBtn;
    private Estudiante estudianteActual;
    private JComboBox<String> materiasCombo;
    private JPanel loginPanel;

    private final Color VERDE_UVG = new Color(30, 130, 70);

    public PlataformaGUI() {
        plataforma = Plataforma.getInstance();
        setTitle("Sistema de Inscripción UVG");
        setSize(920, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        construirLogin();
        construirAreaMaterias();
        construirCombo();
        construirBotones();

        setVisible(true);
        mostrarMaterias();
    }

    private void construirLogin() {
        loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Inicio de Sesión");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(VERDE_UVG);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Código:"), gbc);

        gbc.gridx = 1;
        codigoField = new JTextField(12);
        loginPanel.add(codigoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        passField = new JPasswordField(12);
        loginPanel.add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        loginBtn = crearBoton("Iniciar Sesión");
        loginPanel.add(loginBtn, gbc);

        add(loginPanel, BorderLayout.NORTH);

        loginBtn.addActionListener(e -> login());
    }

    private void construirAreaMaterias() {
        materiasArea = new JTextArea();
        materiasArea.setEditable(false);
        materiasArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        materiasArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(new JScrollPane(materiasArea), BorderLayout.CENTER);
    }

    private void construirCombo() {
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblCombo = new JLabel("Seleccionar Clase:");
        lblCombo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCombo.setForeground(VERDE_UVG);

        materiasCombo = new JComboBox<>();

        comboPanel.add(lblCombo, BorderLayout.NORTH);
        comboPanel.add(materiasCombo, BorderLayout.CENTER);

        add(comboPanel, BorderLayout.EAST);
    }

    private void construirBotones() {
        JPanel botones = new JPanel(new GridLayout(1, 5, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        inscribirBtn = crearBoton("Inscribir");
        desinscribirBtn = crearBoton("Desinscribir");
        verHorarioBtn = crearBoton("Ver Horario");
        recomendarBtn = crearBoton("Recomendación");
        pdfBtn = crearBoton("Generar PDF");

        botones.add(inscribirBtn);
        botones.add(desinscribirBtn);
        botones.add(verHorarioBtn);
        botones.add(recomendarBtn);
        botones.add(pdfBtn);

        add(botones, BorderLayout.SOUTH);

        inscribirBtn.addActionListener(e -> inscribir());
        desinscribirBtn.addActionListener(e -> desinscribir());
        verHorarioBtn.addActionListener(e -> verHorario());
        recomendarBtn.addActionListener(e -> recomendar());
        pdfBtn.addActionListener(e -> generarPDF());
    }

    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(VERDE_UVG);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(20, 100, 55), 2),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        return b;
    }

    private void login() {
        String codigo = codigoField.getText().trim();
        String pass = new String(passField.getPassword());

        if (plataforma.iniciarSesion(codigo, pass)) {
            estudianteActual = plataforma.buscarEstudiante(codigo);
            JOptionPane.showMessageDialog(this, "Bienvenido: " + estudianteActual.getNombre());
            mostrarLogout();
            mostrarMaterias();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
        }
    }

    private void mostrarLogout() {
        if (logoutBtn == null) {
            logoutBtn = crearBoton("Cerrar Sesión");
            logoutBtn.addActionListener(e -> cerrarSesion());
        }

        add(logoutBtn, BorderLayout.NORTH);
        loginPanel.setVisible(false);

        revalidate();
        repaint();
    }

    private void cerrarSesion() {
        estudianteActual = null;
        loginPanel.setVisible(true);
        if (logoutBtn != null) logoutBtn.setVisible(false);

        codigoField.setText("");
        passField.setText("");

        materiasArea.setText("Debe iniciar sesión para ver las materias.");
        materiasCombo.removeAllItems();

        revalidate();
        repaint();

        JOptionPane.showMessageDialog(this, "Sesión cerrada correctamente.");
    }

    private void mostrarMaterias() {
        materiasArea.setText("");
        materiasCombo.removeAllItems();

        for (Materia m : plataforma.getMateriasOfertadas()) {
            materiasArea.append(m.getNombre() + "\n");
            for (Clase c : m.getClasesDisponibles()) {
                materiasArea.append("  ▶ " + c.getHorario() + "   (Cupos: " + c.getCuposDisponibles() + ")\n");
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
        if (estudianteActual == null) {
            JOptionPane.showMessageDialog(this, "Debe iniciar sesión");
            return;
        }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PlataformaGUI::new);
    }
}
