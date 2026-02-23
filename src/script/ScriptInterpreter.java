package script;

import java.util.Arrays;
import java.util.List;

public class ScriptInterpreter {

    private StackMachine stackMachine;
    private CryptoMock crypto;
    private boolean trace;

    public ScriptInterpreter() {
        this(false);
    }

    public ScriptInterpreter(boolean trace) {
        this.stackMachine = new StackMachine();
        this.crypto = new CryptoMock();
        this.trace = trace;
    }

    public boolean execute(List<Instruction> script) {

        stackMachine.clear();

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

    private void executeInstruction(Instruction instruction) {

        // Si es dato → push directo
        if (instruction.isPushData()) {
            stackMachine.push(instruction.getData());
            return;
        }

        Opcode opcode = instruction.getOpcode();

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

    private boolean isTrue(byte[] value) {
        for (byte b : value) {
            if (b != 0) return true;
        }
        return false;
    }

    // ===== OPCODES =====

    private void opDup() {
        byte[] top = stackMachine.peek();
        stackMachine.push(top);
    }

    private void opDrop() {
        stackMachine.pop();
    }

    private void opEqual() {
        byte[] a = stackMachine.pop();
        byte[] b = stackMachine.pop();

        boolean equal = Arrays.equals(a, b);

        stackMachine.push(equal ? new byte[]{1} : new byte[]{0});
    }

    private void opEqualVerify() {
        opEqual();
        byte[] result = stackMachine.pop();

        if (!isTrue(result)) {
            throw new RuntimeException("OP_EQUALVERIFY failed");
        }
    }

    private void opHash160() {
        byte[] value = stackMachine.pop();
        byte[] hash = crypto.hash160(value);
        stackMachine.push(hash);
    }

    private void opCheckSig() {
        byte[] pubKey = stackMachine.pop();
        byte[] signature = stackMachine.pop();

        boolean valid = crypto.checkSig(signature, pubKey);

        stackMachine.push(valid ? new byte[]{1} : new byte[]{0});
    }
}
