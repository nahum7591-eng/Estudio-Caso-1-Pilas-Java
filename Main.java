import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final Map<String, Double> VARIABLES = new HashMap<>();
    private static final Pila PILA_DEMO = new Pila();

    public static void menu() {
        while (true) {
            System.out.println("\n=== ANALIZADOR ARITMETICO CON PILAS ===");
            System.out.println("1. Asignar variable numerica");
            System.out.println("2. Analizar y evaluar expresion");
            System.out.println("3. Apilar token (demostracion)");
            System.out.println("4. Desapilar token (demostracion)");
            System.out.println("5. Ver cima y contenido de la pila");
            System.out.println("6. Vaciar pila de demostracion");
            System.out.println("7. Ver variables definidas");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            String opcion = ENTRADA.nextLine().trim();
            try {
                switch (opcion) {
                    case "1":
                        System.out.print("Nombre de variable: ");
                        String nombre = ENTRADA.nextLine().trim();
                        if (!nombre.matches("[a-zA-Z_][a-zA-Z_0-9]*")) throw new IllegalArgumentException("Nombre invalido");
                        System.out.print("Valor numerico: ");
                        double numero = Double.parseDouble(ENTRADA.nextLine().trim());
                        if (!Double.isFinite(numero)) throw new IllegalArgumentException("Valor no finito");
                        VARIABLES.put(nombre, numero);
                        System.out.println("Guardado: " + nombre + " = " + numero); break;
                    case "2":
                        System.out.print("Expresion (ejemplo: (x + 3) * 2): ");
                        String expresion = ENTRADA.nextLine();
                        System.out.println("Postfija: " + String.join(" ", Evaluador.convertir(expresion)));
                        System.out.println("Resultado: " + Evaluador.evaluar(expresion, VARIABLES)); break;
                    case "3":
                        System.out.print("Token a apilar: ");
                        String token = ENTRADA.nextLine().trim();
                        if (token.isEmpty()) throw new IllegalArgumentException("Token vacio");
                        PILA_DEMO.apilar(token);
                        System.out.println(PILA_DEMO.mostrar()); break;
                    case "4": System.out.println("Retirado: " + PILA_DEMO.desapilar()); break;
                    case "5":
                        System.out.println(PILA_DEMO.mostrar());
                        System.out.println("Tamano: " + PILA_DEMO.tamano());
                        if (!PILA_DEMO.estaVacia()) System.out.println("Cima: " + PILA_DEMO.verCima()); break;
                    case "6": PILA_DEMO.vaciar(); System.out.println("Pila vaciada"); break;
                    case "7": System.out.println(VARIABLES); break;
                    case "0": System.out.println("Hasta luego"); return;
                    default: System.out.println("Opcion no valida");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
    public static void main(String[] args) { menu(); }
}
