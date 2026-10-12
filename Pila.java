public class Pila {
    private Nodo cima;
    private int cantidad;

    public void apilar(String token) {
        if (token == null) throw new IllegalArgumentException("Token nulo");
        cima = new Nodo(token, cima);
        cantidad++;
    }
    public String desapilar() {
        if (estaVacia()) throw new IllegalStateException("La pila esta vacia");
        String resultado = cima.token;
        cima = cima.siguiente;
        cantidad--;
        return resultado;
    }
    public String verCima() {
        if (estaVacia()) throw new IllegalStateException("La pila esta vacia");
        return cima.token;
    }
    public boolean estaVacia() { return cima == null; }
    public int tamano() { return cantidad; }
    public void vaciar() { cima = null; cantidad = 0; }
    public String mostrar() {
        StringBuilder texto = new StringBuilder("CIMA -> ");
        for (Nodo actual = cima; actual != null; actual = actual.siguiente) {
            texto.append('[').append(actual.token).append("] ");
        }
        return texto.append("<- FONDO").toString();
    }
}
