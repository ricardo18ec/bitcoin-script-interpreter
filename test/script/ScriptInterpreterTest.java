package script;

import java.util.List;

public class ScriptInterpreterTest {

    public static void main(String[] args) {

        System.out.println("Running ScriptInterpreter tests...");

        testOpDup();
        testOpEqual();
        testOpEqualVerifyFail();
        testP2PKHSuccess();

        System.out.println("All ScriptInterpreter tests passed.");
    }

    private static void testOpDup() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_DUP)
        );

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("OP_DUP test failed");
        }
    }

    private static void testOpEqual() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction("A".getBytes()),
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_EQUAL)
        );

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("OP_EQUAL test failed");
        }
    }

    private static void testOpEqualVerifyFail() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction("A".getBytes()),
                new Instruction("B".getBytes()),
                new Instruction(Opcode.OP_EQUALVERIFY)
        );

        try {
            interpreter.execute(script);
            fail("Expected OP_EQUALVERIFY to fail");
        } catch (RuntimeException e) {
            // Expected
        }
    }

    private static void testP2PKHSuccess() {

        byte[] pubKey = "clave".getBytes();
        byte[] signature = "clave".getBytes();
        byte[] pubKeyHash = new CryptoMock().hash160(pubKey);

        List<Instruction> script = List.of(
                new Instruction(signature),
                new Instruction(pubKey),
                new Instruction(Opcode.OP_DUP),
                new Instruction(Opcode.OP_HASH160),
                new Instruction(pubKeyHash),
                new Instruction(Opcode.OP_EQUALVERIFY),
                new Instruction(Opcode.OP_CHECKSIG)
        );

        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("P2PKH test failed");
        }
    }

    private static void fail(String message) {
        throw new RuntimeException("Test failed: " + message);
    }
}
