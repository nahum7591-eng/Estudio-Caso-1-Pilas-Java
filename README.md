# Estudio de caso 1 — Pilas en Java
Autor: Malco Nahum Fonseca Cruz. Curso: SOFT-10, Estructuras de Datos.

## Ejecucion
Desde esta carpeta, con JDK 11 o superior:

```bash
javac Nodo.java Pila.java Evaluador.java Main.java
java Main
```

## Pruebas sugeridas
- `(2 + 3) * 4` -> 20.0
- `10 - 6 / 2` -> 7.0
- `2 ^ 3 ^ 2` -> 512.0
- `-(2 + 3)` -> -5.0
- Asignar `x=5` y evaluar `x * (2 + 1)` -> 15.0
- `3 / 0` -> error controlado.
- `((2 + 3)` -> error de parentesis.

La aplicacion incluye una pila enlazada propia (Nodo/Pila), sin recurrir a java.util.Stack. El algoritmo trabaja con pilas de tokens de operadores y operandos. El programa acepta numeros decimales con punto, variables, + - * / ^, parentesis y signos unarios. No admite multiplicacion implicita (`2(3)`).

## GitHub
Crea un repositorio nuevo, sube los cuatro archivos .java y este README, y copia el enlace publico al apartado indicado en el PDF.
