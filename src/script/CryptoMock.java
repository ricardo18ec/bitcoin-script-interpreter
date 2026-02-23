package script;

import java.util.Arrays;

public class CryptoMock {

    // Simulación simple de HASH160
    public byte[] hash160(byte[] input) {
        // Simulación: devolvemos el mismo array invertido
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = input[input.length - 1 - i];
        }

        return result;
    }

    // Simulación simple de verificación de firma
    public boolean checkSig(byte[] signature, byte[] pubKey) {
        // Simulación: consideramos válida si ambos arrays son iguales
        return Arrays.equals(signature, pubKey);
    }
}