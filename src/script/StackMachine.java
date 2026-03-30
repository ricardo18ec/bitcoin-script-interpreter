package script;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Representa la pila utilizada por el intérprete.
 * Permite almacenar y manipular datos en formato LIFO (último en entrar, primero en salir).
 */
public class StackMachine {

    private Deque<byte[]> stack;

    /**
     * Constructor que inicializa la pila.
     */
    public StackMachine() {
        this.stack = new ArrayDeque<>();
    }

    /**
     * Inserta un elemento en la pila.
     *
     * @param data datos a insertar
     */
    public void push(byte[] data) {
        stack.push(data);
    }

    /**
     * Extrae el elemento superior de la pila.
     *
     * @return elemento superior
     * @throws RuntimeException si la pila está vacía
     */
    public byte[] pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return stack.pop();
    }

    /**
     * Consulta el elemento superior sin eliminarlo.
     *
     * @return elemento superior
     * @throws RuntimeException si la pila está vacía
     */
    public byte[] peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.peek();
    }

    /**
     * Devuelve el tamaño actual de la pila.
     *
     * @return número de elementos en la pila
     */
    public int size() {
        return stack.size();
    }

    /**
     * Indica si la pila está vacía.
     *
     * @return true si está vacía, false en caso contrario
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Limpia completamente la pila.
     */
    public void clear() {
        stack.clear();
    }

    /**
     * Representación en texto de la pila.
     *
     * @return contenido de la pila en formato string
     */
    @Override
    public String toString() {
        return stack.toString();
    }
}
