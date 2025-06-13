import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorEstudiantes gestor = new GestorEstudiantes();
        int opcion;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Agregar estudiante");
            System.out.println("2. Mostrar estudiantes");
            System.out.println("3. Buscar estudiante por nombre");
            System.out.println("4. Exportar lista a archivo .txt");
            System.out.println("5. Salir");
            System.out.println("6. Eliminar estudiante por nombre");
            System.out.println("7. Ver cantidad total de estudiantes");
            System.out.println("8. Exportar estudiantes a PDF");
            System.out.println("9. Actualizar estudiante");
            System.out.print("Elige una opcion: ");

            while (!sc.hasNextInt()) {
                System.out.print("⚠️ Ingresa un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine();  

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Edad: ");
                    int edad;
                    while (!sc.hasNextInt()) {
                        System.out.print("⚠️ Edad inválida. Ingresa un número: ");
                        sc.next();
                    }
                    edad = sc.nextInt();
                    sc.nextLine(); 
                    System.out.print("Carrera: ");
                    String carrera = sc.nextLine();

                    Estudiante nuevo = new Estudiante(nombre, edad, carrera);
                    gestor.agregarEstudiante(nuevo);
                    System.out.println("✅ Estudiante agregado.");
                    break;

                case 2:
                    gestor.mostrarEstudiantesDesdeBD(); 
                    break;

                case 3:
                    System.out.print("Ingresa nombre a buscar: ");
                    String buscar = sc.nextLine();
                    gestor.buscarPorNombreEnBD(buscar);
                    break;

                case 4:
                    System.out.print("Nombre del archivo (con .txt): ");
                    String archivo = sc.nextLine();
                    gestor.exportarAArchivo(archivo);
                    break;

                case 5:
                    System.out.println("¡Hasta pronto!");
                    break;

                case 6:
                    System.out.print("Introduce el nombre del estudiante a eliminar: ");
                    String nombreEliminar = sc.nextLine();
                    gestor.eliminarEstudianteDeBD(nombreEliminar);
                    break;

                case 7:
                    int cantidad = gestor.contarEstudiantesEnBD();
                    System.out.println("Cantidad total de estudiantes: " + cantidad);
                    break;
                case 8:
                    System.out.print("Nombre del archivo PDF (con .pdf): ");
                    String archivoPDF = sc.nextLine();
                    GeneradorPDF.generarPDF(archivoPDF);
                    System.out.println("✅ PDF generado correctamente.");
                    break;
                case 9:
                    System.out.print("¿Conoces el nombre del estudiante a actualizar? (si/no): ");
                    String conoceNombre = sc.nextLine().trim().toLowerCase();

                    if (conoceNombre.equals("no")) {
                    gestor.mostrarEstudiantesDesdeBD();  
                    }

                    System.out.print("Nombre actual del estudiante: ");
                    String nombreActual = sc.nextLine();

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = sc.nextLine();

                    System.out.print("Nueva edad: ");
                    int nuevaEdad = Integer.parseInt(sc.nextLine());

                    System.out.print("Nueva carrera: ");
                    String nuevaCarrera = sc.nextLine();

                    Estudiante actualizado = new Estudiante(nuevoNombre, nuevaEdad, nuevaCarrera);
                    gestor.actualizarEstudiante(nombreActual, actualizado);
                    break;





                default:
                    System.out.println("⚠️ Opción no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }
}
