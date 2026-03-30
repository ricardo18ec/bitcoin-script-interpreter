package script;

import java.util.List;

public class ScriptInterpreterTest {

    public static void main(String[] args) {

        System.out.println("Running ScriptInterpreter tests...");

        testOpDup();
        testOpEqual();
        testOpEqualVerifyFail();
        testP2PKHSuccess();
        testP2PKHFail();

        // NUEVOS TESTS FASE 2
        testIfTrue();
        testIfFalse();
        testIfElse();

        System.out.println("All ScriptInterpreter tests passed.");
    }

    // ============================
    // TESTS BÁSICOS
    // ============================

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
            // Correcto
        }
    }

    // ============================
    // P2PKH
    // ============================

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
            fail("P2PKH success test failed");
        }
    }

    private static void testP2PKHFail() {

        byte[] pubKey = "clave".getBytes();
        byte[] badSignature = "incorrecta".getBytes();
        byte[] pubKeyHash = new CryptoMock().hash160(pubKey);

        List<Instruction> script = List.of(
                new Instruction(badSignature),
                new Instruction(pubKey),
                new Instruction(Opcode.OP_DUP),
                new Instruction(Opcode.OP_HASH160),
                new Instruction(pubKeyHash),
                new Instruction(Opcode.OP_EQUALVERIFY),
                new Instruction(Opcode.OP_CHECKSIG)
        );

        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        boolean result = interpreter.execute(script);

        if (result) {
            fail("P2PKH fail test did not fail as expected");
        }
    }

    // ============================
    // TESTS IF (FASE 2)
    // ============================

    private static void testIfTrue() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction(new byte[]{1}), // true
                new Instruction(Opcode.OP_IF),
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_ENDIF)
        );

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("IF true test failed");
        }
    }

    private static void testIfFalse() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction(new byte[]{0}), // false
                new Instruction(Opcode.OP_IF),
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_ENDIF)
        );

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("IF false test failed");
        }
    }

    private static void testIfElse() {
        ScriptInterpreter interpreter = new ScriptInterpreter(false);

        List<Instruction> script = List.of(
                new Instruction(new byte[]{0}), // false
                new Instruction(Opcode.OP_IF),
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_ELSE),
                new Instruction("B".getBytes()),
                new Instruction(Opcode.OP_ENDIF)
        );

        boolean result = interpreter.execute(script);

        if (!result) {
            fail("IF ELSE test failed");
        }
    }

    // ============================
    // UTILIDAD
    // ============================

    private static void fail(String message) {
        throw new RuntimeException("Test failed: " + message);
    }
}
