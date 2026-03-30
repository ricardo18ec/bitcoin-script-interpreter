package script;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        ScriptInterpreter interpreter = new ScriptInterpreter(true);

        // =====================================
        // 1. P2PKH VÁLIDO
        // =====================================
        System.out.println("=== P2PKH VALIDO ===");

        byte[] pubKey = "clave".getBytes();
        byte[] signature = "clave".getBytes();
        byte[] pubKeyHash = new CryptoMock().hash160(pubKey);

        List<Instruction> scriptValido = List.of(
                new Instruction(signature),
                new Instruction(pubKey),
                new Instruction(Opcode.OP_DUP),
                new Instruction(Opcode.OP_HASH160),
                new Instruction(pubKeyHash),
                new Instruction(Opcode.OP_EQUALVERIFY),
                new Instruction(Opcode.OP_CHECKSIG)
        );

        boolean resultValido = interpreter.execute(scriptValido);
        System.out.println("Resultado final: " + resultValido);
        System.out.println();


        // =====================================
        // 2. P2PKH INVÁLIDO
        // =====================================
        System.out.println("=== P2PKH INVALIDO ===");

        byte[] badSignature = "firmaIncorrecta".getBytes();

        List<Instruction> scriptInvalido = List.of(
                new Instruction(badSignature),
                new Instruction(pubKey),
                new Instruction(Opcode.OP_DUP),
                new Instruction(Opcode.OP_HASH160),
                new Instruction(pubKeyHash),
                new Instruction(Opcode.OP_EQUALVERIFY),
                new Instruction(Opcode.OP_CHECKSIG)
        );

        boolean resultInvalido = interpreter.execute(scriptInvalido);
        System.out.println("Resultado final: " + resultInvalido);
        System.out.println();


        // =====================================
        // 3. IF / ELSE
        // =====================================
        System.out.println("=== TEST IF / ELSE ===");

        List<Instruction> scriptIf = List.of(
                new Instruction(new byte[]{1}), // cambiar a 0 para probar ELSE
                new Instruction(Opcode.OP_IF),
                new Instruction("A".getBytes()),
                new Instruction(Opcode.OP_ELSE),
                new Instruction("B".getBytes()),
                new Instruction(Opcode.OP_ENDIF)
        );

        boolean resultIf = interpreter.execute(scriptIf);
        System.out.println("Resultado final: " + resultIf);
    }
}