import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArchivoTexto {
    public static void exportarEstudiantes(List<Estudiante> estudiantes, String
    nombreArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (Estudiante e: estudiantes) {
                writer.write(e.toString());
                writer.newLine();
            }
            System.out.println("Archivo ´ " + nombreArchivo + "  ` creado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al crear archivo:" + e.getMessage());
        }
    }
}