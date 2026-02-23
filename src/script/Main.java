package script;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Simulamos pubKey
        byte[] pubKey = "clave".getBytes();

        // Para que checkSig sea true, signature debe ser igual
        byte[] signature = "clave".getBytes();

        // Nuestro hash160 invierte el array
        byte[] pubKeyHash = new CryptoMock().hash160(pubKey);

        // Construimos script completo (scriptSig + scriptPubKey)
        List<Instruction> script = List.of(

                // scriptSig
                new Instruction(signature),
                new Instruction(pubKey),

                // scriptPubKey
                new Instruction(Opcode.OP_DUP),
                new Instruction(Opcode.OP_HASH160),
                new Instruction(pubKeyHash),
                new Instruction(Opcode.OP_EQUALVERIFY),
                new Instruction(Opcode.OP_CHECKSIG)
        );

        ScriptInterpreter interpreter = new ScriptInterpreter(true);

        boolean result = interpreter.execute(script);

        System.out.println("Resultado final: " + result);
    }
}
