package script;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Clase principal que interpreta y ejecuta scripts tipo Bitcoin Script.
 * Utiliza una pila para procesar instrucciones secuencialmente.
 * Soporta operaciones básicas y control de flujo (IF/ELSE).
 */
public class ScriptInterpreter {

    private StackMachine stackMachine;
    private CryptoMock crypto;
    private boolean trace;

    // Pila para manejar bloques condicionales (IF/ELSE)
    private Stack<Boolean> executionStack = new Stack<>();

    /**
     * Constructor por defecto (sin modo trace).
     */
    public ScriptInterpreter() {
        this(false);
    }

    /**
     * Constructor que permite activar el modo trace.
     *
     * @param trace si es true, muestra el estado de la pila en cada paso
     */
    public ScriptInterpreter(boolean trace) {
        this.stackMachine = new StackMachine();
        this.crypto = new CryptoMock();
        this.trace = trace;
    }

    /**
     * Ejecuta una lista de instrucciones del script.
     *
     * @param script lista de instrucciones a ejecutar
     * @return true si el script es válido, false en caso contrario
     */
    public boolean execute(List<Instruction> script) {

        stackMachine.clear();
        executionStack.clear();

        for (Instruction instruction : script) {
            executeInstruction(instruction);

            if (trace) {
                System.out.println("Stack: " + stackMachine);
            }
        }

        if (stackMachine.isEmpty()) {
            return false;
        }

        byte[] top = stackMachine.pop();
        return isTrue(top);
    }

    /**
     * Ejecuta una instrucción individual del script.
     *
     * @param instruction instrucción a ejecutar
     */
    private void executeInstruction(Instruction instruction) {

        boolean executing = executionStack.isEmpty() || executionStack.peek();

        // Si es dato, se empuja a la pila
        if (instruction.isPushData()) {
            if (executing) {
                stackMachine.push(instruction.getData());
            }
            return;
        }

        Opcode opcode = instruction.getOpcode();

        switch (opcode) {

            // ===== CONTROL DE FLUJO =====
            case OP_IF:
                opIf();
                break;

            case OP_NOTIF:
                opNotIf();
                break;

            case OP_ELSE:
                opElse();
                break;

            case OP_ENDIF:
                opEndIf();
                break;

            // ===== OTROS OPCODES =====
            default:
                if (!executing) return;

                switch (opcode) {
                    case OP_DUP:
                        opDup();
                        break;

                    case OP_DROP:
                        opDrop();
                        break;

                    case OP_EQUAL:
                        opEqual();
                        break;

                    case OP_EQUALVERIFY:
                        opEqualVerify();
                        break;

                    case OP_HASH160:
                        opHash160();
                        break;

                    case OP_CHECKSIG:
                        opCheckSig();
                        break;

                    default:
                        throw new UnsupportedOperationException("Opcode not implemented: " + opcode);
                }
        }
    }

    /**
     * Determina si un valor es considerado verdadero.
     *
     * @param value array de bytes
     * @return true si contiene algún byte distinto de 0
     */
    private boolean isTrue(byte[] value) {
        for (byte b : value) {
            if (b != 0) return true;
        }
        return false;
    }

    // ===== OPCODES =====

    /**
     * Duplica el elemento superior de la pila.
     */
    private void opDup() {
        byte[] top = stackMachine.peek();
        stackMachine.push(top);
    }

    /**
     * Elimina el elemento superior de la pila.
     */
    private void opDrop() {
        stackMachine.pop();
    }

    /**
     * Compara los dos elementos superiores de la pila.
     * Inserta 1 si son iguales, 0 si no.
     */
    private void opEqual() {
        byte[] a = stackMachine.pop();
        byte[] b = stackMachine.pop();

        boolean equal = Arrays.equals(a, b);

        stackMachine.push(equal ? new byte[]{1} : new byte[]{0});
    }

    /**
     * Verifica que los dos elementos superiores sean iguales.
     * Lanza excepción si no lo son.
     */
    private void opEqualVerify() {
        opEqual();
        byte[] result = stackMachine.pop();

        if (!isTrue(result)) {
            throw new RuntimeException("OP_EQUALVERIFY failed");
        }
    }

    /**
     * Aplica una función hash simulada al elemento superior.
     */
    private void opHash160() {
        byte[] value = stackMachine.pop();
        byte[] hash = crypto.hash160(value);
        stackMachine.push(hash);
    }

    /**
     * Verifica una firma de forma simulada.
     */
    private void opCheckSig() {
        byte[] pubKey = stackMachine.pop();
        byte[] signature = stackMachine.pop();

        boolean valid = crypto.checkSig(signature, pubKey);

        stackMachine.push(valid ? new byte[]{1} : new byte[]{0});
    }

    // ===== CONTROL DE FLUJO =====

    /**
     * Ejecuta un bloque IF.
     * Evalúa el valor superior de la pila para decidir si ejecutar el bloque.
     */
    private void opIf() {
        boolean parentExecuting = executionStack.isEmpty() || executionStack.peek();

        if (!parentExecuting) {
            executionStack.push(false);
            return;
        }

        byte[] value = stackMachine.pop();
        boolean condition = isTrue(value);

        executionStack.push(condition);
    }

    /**
     * Ejecuta un bloque NOTIF (condición invertida).
     */
    private void opNotIf() {
        boolean parentExecuting = executionStack.isEmpty() || executionStack.peek();

        if (!parentExecuting) {
            executionStack.push(false);
            return;
        }

        byte[] value = stackMachine.pop();
        boolean condition = !isTrue(value);

        executionStack.push(condition);
    }

    /**
     * Cambia la ejecución entre IF y ELSE.
     */
    private void opElse() {
        if (executionStack.isEmpty()) {
            throw new RuntimeException("OP_ELSE without OP_IF");
        }

        boolean current = executionStack.pop();
        boolean parent = executionStack.isEmpty() || executionStack.peek();

        executionStack.push(parent && !current);
    }

    /**
     * Finaliza un bloque condicional IF.
     */
    private void opEndIf() {
        if (executionStack.isEmpty()) {
            throw new RuntimeException("OP_ENDIF without OP_IF");
        }

        executionStack.pop();
    }
}
