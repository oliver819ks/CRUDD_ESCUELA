import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.FileOutputStream;
import java.io.File;


public class GeneradorPDF {

    public void exportar(String fecha) {
        String nombreArchivo = "movimientos_" + fecha.replaceAll("[^\\d]", "-") + ".pdf";

        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(nombreArchivo));
            documento.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph titulo = new Paragraph("📄 Reporte de Estudiantes - " + fecha, fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            documento.add(new Paragraph("\nContenido del reporte aquí..."));

            documento.close();
            System.out.println("✅ PDF generado: " + nombreArchivo);

        } catch (Exception e) {
            System.out.println("❌ Error al generar PDF: " + e.getMessage());
        }
    }
}
