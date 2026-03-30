package script;

public enum Opcode {

    // Literales
    OP_0,
    OP_1, OP_2, OP_3, OP_4, OP_5, OP_6, OP_7, OP_8,
    OP_9, OP_10, OP_11, OP_12, OP_13, OP_14, OP_15, OP_16,

    // Operaciones de pila
    OP_DUP,
    OP_DROP,

    // Comparación
    OP_EQUAL,
    OP_EQUALVERIFY,

    // Criptográficas (simuladas)
    OP_HASH160,
    OP_CHECKSIG,

    // Control de flujo (Fase 2)
    OP_IF,
    OP_NOTIF,
    OP_ELSE,
    OP_ENDIF;

    /**
     * Indica si el opcode es un literal (OP_0 a OP_16)
     */
    public boolean isLiteral() {
        return this.ordinal() <= OP_16.ordinal();
    }
}