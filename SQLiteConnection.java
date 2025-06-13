import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    public static Connection conectar() {
        try {
            String url = "jdbc:sqlite:estudiantes.db";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }
}
