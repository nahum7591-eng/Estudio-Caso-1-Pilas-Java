import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Evaluador {
    private static boolean operador(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^") || s.equals("~");
    }
    private static int prioridad(String s) {
        if (s.equals("~")) return 4;
        if (s.equals("^")) return 3;
        if (s.equals("*") || s.equals("/")) return 2;
        return 1;
    }
    private static boolean derecha(String s) { return s.equals("^") || s.equals("~"); }
    private static boolean numero(String s) { return s.matches("(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)"); }
    private static boolean nombre(String s) { return s.matches("[a-zA-Z_][a-zA-Z_0-9]*"); }

    public static List<String> tokenizar(String expresion) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expresion.length()) {
            char c = expresion.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (Character.isDigit(c) || c == '.') {
                int ini = i++;
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) i++;
                String t = expresion.substring(ini, i);
                if (!numero(t)) throw new IllegalArgumentException("Numero invalido: " + t);
                tokens.add(t);
            } else if (Character.isLetter(c) || c == '_') {
                int ini = i++;
                while (i < expresion.length() && (Character.isLetterOrDigit(expresion.charAt(i)) || expresion.charAt(i) == '_')) i++;
                tokens.add(expresion.substring(ini, i));
            } else if ("+-*/^()".indexOf(c) >= 0) { tokens.add(String.valueOf(c)); i++; }
            else throw new IllegalArgumentException("Caracter no admitido: " + c);
        }
        if (tokens.isEmpty()) throw new IllegalArgumentException("La expresion esta vacia");
        return tokens;
    }

    public static List<String> convertir(String expresion) {
        List<String> salida = new ArrayList<>();
        Pila operadores = new Pila();
        boolean esperaOperando = true;
        for (String token : tokenizar(expresion)) {
            if (numero(token) || nombre(token)) {
                if (!esperaOperando) throw new IllegalArgumentException("Falta operador antes de " + token);
                salida.add(token); esperaOperando = false;
            } else if (token.equals("(")) {
                if (!esperaOperando) throw new IllegalArgumentException("Falta operador antes de (");
                operadores.apilar(token);
            } else if (token.equals(")")) {
                if (esperaOperando) throw new IllegalArgumentException("Parentesis vacio o expresion incompleta");
                while (!operadores.estaVacia() && !operadores.verCima().equals("(")) salida.add(operadores.desapilar());
                if (operadores.estaVacia()) throw new IllegalArgumentException("Parentesis de cierre sobrante");
                operadores.desapilar(); esperaOperando = false;
            } else {
                String op = token;
                if (esperaOperando) {
                    if (token.equals("-")) { operadores.apilar("~"); continue; }
                    if (token.equals("+")) continue;
                    throw new IllegalArgumentException("Operador sin operando: " + token);
                }
                while (!operadores.estaVacia() && operador(operadores.verCima()) &&
                        (prioridad(operadores.verCima()) > prioridad(op) ||
                        (prioridad(operadores.verCima()) == prioridad(op) && !derecha(op)))) {
                    salida.add(operadores.desapilar());
                }
                operadores.apilar(op); esperaOperando = true;
            }
        }
        if (esperaOperando) throw new IllegalArgumentException("Expresion incompleta");
        while (!operadores.estaVacia()) {
            String op = operadores.desapilar();
            if (op.equals("(")) throw new IllegalArgumentException("Parentesis sin cerrar");
            salida.add(op);
        }
        return salida;
    }

    public static double evaluar(String expresion, Map<String, Double> variables) {
        Pila valores = new Pila();
        for (String token : convertir(expresion)) {
            if (numero(token)) valores.apilar(token);
            else if (nombre(token)) {
                Double valor = variables.get(token);
                if (valor == null) throw new IllegalArgumentException("Variable sin valor: " + token);
                valores.apilar(Double.toString(valor));
            } else if (token.equals("~")) {
                if (valores.estaVacia()) throw new IllegalArgumentException("Falta operando negativo");
                valores.apilar(Double.toString(-Double.parseDouble(valores.desapilar())));
            } else {
                if (valores.tamano() < 2) throw new IllegalArgumentException("Faltan operandos");
                double b = Double.parseDouble(valores.desapilar());
                double a = Double.parseDouble(valores.desapilar());
                double r;
                switch (token) {
                    case "+": r = a + b; break;
                    case "-": r = a - b; break;
                    case "*": r = a * b; break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("Division por cero");
                        r = a / b; break;
                    case "^": r = Math.pow(a, b); break;
                    default: throw new IllegalArgumentException("Operador desconocido");
                }
                if (!Double.isFinite(r)) throw new ArithmeticException("Resultado no finito");
                valores.apilar(Double.toString(r));
            }
        }
        if (valores.tamano() != 1) throw new IllegalArgumentException("Expresion mal formada");
        return Double.parseDouble(valores.desapilar());
    }
}
