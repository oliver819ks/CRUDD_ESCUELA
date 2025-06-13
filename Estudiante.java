public class Estudiante {
    private String nombre;
    private int edad;
    private String carrera;

    public Estudiante(String nombre, int edad, String carrera) {

        this.nombre = nombre;
        this.edad = edad;
        this.carrera = carrera;
    }

    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }
    public String getCarrera(){
        return carrera;
    }
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Edad: " + edad + ", Carrera: " + carrera;
    }
}