import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class GestorEstudiantes {

    public GestorEstudiantes() {
        crearTablaSiNoExiste();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes(nombre, edad, carrera) VALUES(?, ?, ?)";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, estudiante.getNombre());
            pstmt.setInt(2, estudiante.getEdad());
            pstmt.setString(3, estudiante.getCarrera());
            pstmt.executeUpdate();
            System.out.println("✅ Estudiante guardado en la base de datos.");

        } catch (SQLException e) {
            System.out.println("❌ Error al guardar en la base de datos: " + e.getMessage());
        }
    }

    private void crearTablaSiNoExiste() {
        String sql = "CREATE TABLE IF NOT EXISTS estudiantes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "nombre TEXT NOT NULL," +
                     "edad INTEGER NOT NULL," +
                     "carrera TEXT NOT NULL)";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("✅ Tabla 'estudiantes' creada o verificada.");

        } catch (SQLException e) {
            System.out.println("❌ Error al crear la tabla: " + e.getMessage());
        }
    }

    public void mostrarEstudiantesDesdeBD() {
        String sql = "SELECT nombre, edad, carrera FROM estudiantes";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hayResultados = false;

            while (rs.next()) {
                hayResultados = true;
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String carrera = rs.getString("carrera");
                System.out.println("Nombre: " + nombre + ", Edad: " + edad + ", Carrera: " + carrera);
            }

            if (!hayResultados) {
                System.out.println("⚠️ No hay estudiantes registrados en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al leer desde la base de datos: " + e.getMessage());
        }
    }

    public void buscarPorNombreEnBD(String nombreBuscado) {
        String sql = "SELECT nombre, edad, carrera FROM estudiantes WHERE nombre LIKE ?";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nombreBuscado + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean encontrado = false;
            while (rs.next()) {
                encontrado = true;
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String carrera = rs.getString("carrera");
                System.out.println("Nombre: " + nombre + ", Edad: " + edad + ", Carrera: " + carrera);
            }

            if (!encontrado) {
                System.out.println("⚠️ No se encontraron estudiantes con ese nombre.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar en la base de datos: " + e.getMessage());
        }
    }

    public void exportarAArchivo(String nombreArchivo) {
        String sql = "SELECT nombre, edad, carrera FROM estudiantes";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter writer = new FileWriter(nombreArchivo)) {

            while (rs.next()) {
                String linea = String.format("Nombre: %s, Edad: %d, Carrera: %s\n",
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("carrera"));
                writer.write(linea);
            }

            System.out.println("✅ Estudiantes exportados a " + nombreArchivo);

        } catch (SQLException | IOException e) {
            System.out.println("❌ Error al exportar a archivo: " + e.getMessage());
        }
    }

    public void eliminarEstudianteDeBD(String nombre) {
        String sql = "DELETE FROM estudiantes WHERE nombre = ?";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Estudiante eliminado de la base de datos.");
            } else {
                System.out.println("⚠️ No se encontró un estudiante con ese nombre.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar estudiante: " + e.getMessage());
        }
    }

    public int contarEstudiantesEnBD() {
        String sql = "SELECT COUNT(*) AS total FROM estudiantes";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt("total");

        } catch (SQLException e) {
            System.out.println("❌ Error al contar estudiantes: " + e.getMessage());
            return 0;
        }
    }

    public void actualizarEstudiante(String nombreActual, Estudiante actualizado) {
        String sql = "UPDATE estudiantes SET nombre = ?, edad = ?, carrera = ? WHERE nombre = ?";

        try (Connection conn = SQLiteConnection.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, actualizado.getNombre());
            pstmt.setInt(2, actualizado.getEdad());
            pstmt.setString(3, actualizado.getCarrera());
            pstmt.setString(4, nombreActual);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Estudiante actualizado correctamente.");
            } else {
                System.out.println("⚠️ No se encontró ningún estudiante con ese nombre.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar el estudiante: " + e.getMessage());
        }
    }

    public void exportarAPDF(String fecha) {
        String sql = "SELECT nombre, edad, carrera FROM estudiantes";
        String nombreArchivo = "movimientos_" + fecha.replaceAll("[^\\d]", "-") + ".pdf";

        try (Connection conn = SQLiteConnection.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(new File(nombreArchivo)));
            documento.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("📄 Reporte de Estudiantes - " + fecha, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\n"));

            while (rs.next()) {
                String linea = String.format("Nombre: %s\nEdad: %d\nCarrera: %s\n\n",
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("carrera"));
                documento.add(new Paragraph(linea));
            }

            documento.close();
            System.out.println("✅ PDF generado: " + nombreArchivo);

        } catch (Exception e) {
            System.out.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
