package script;

public class Instruction {

    private Opcode opcode;
    private byte[] data;

    // Constructor para opcode
    public Instruction(Opcode opcode) {
        this.opcode = opcode;
        this.data = null;
    }

    // Constructor para pushdata
    public Instruction(byte[] data) {
        this.data = data;
        this.opcode = null;
    }

    public boolean isOpcode() {
        return opcode != null;
    }

    public boolean isPushData() {
        return data != null;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public byte[] getData() {
        return data;
    }
}
