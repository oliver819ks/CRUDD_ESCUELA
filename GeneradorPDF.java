import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;


public class GeneradorPDF {
    public static void generarPDF(String nombreArchivo) {
        Document documento = new Document();
        

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();
            documento.add(new Paragraph("Lista de Estudiantes"));
            documento.add(new Paragraph("---------------------"));

            Connection conn = SQLiteConnection.conectar();
            if (conn == null) {
                documento.add(new Paragraph("❌ No se pudo conectar a la base de datos."));
                documento.close();
                return;
            }

            String sql = "SELECT nombre, edad, carrera FROM estudiantes";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String carrera = rs.getString("carrera");
                documento.add(new Paragraph("Nombre: " + nombre + ", Edad: " + edad + ", Carrera: " + carrera));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (DocumentException | IOException | SQLException e) {
            System.out.println("Error al generar el PDF: " + e.getMessage());
        } finally {
            documento.close();
        }
    }
}
