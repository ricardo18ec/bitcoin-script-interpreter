package script;

import java.util.Arrays;

/**
 * Clase que simula operaciones criptográficas.
 * No implementa criptografía real, solo comportamientos básicos para pruebas.
 */
public class CryptoMock {

    /**
     * Simula la función hash160.
     * En este caso simplemente invierte el array de bytes.
     *
     * @param input datos de entrada
     * @return "hash" simulado
     */
    public byte[] hash160(byte[] input) {
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = input[input.length - 1 - i];
        }

        return result;
    }

    /**
     * Simula la verificación de una firma.
     * Considera válida la firma si es igual a la clave pública.
     *
     * @param signature firma a verificar
     * @param pubKey clave pública
     * @return true si coinciden, false en caso contrario
     */
    public boolean checkSig(byte[] signature, byte[] pubKey) {
        return Arrays.equals(signature, pubKey);
    }
}