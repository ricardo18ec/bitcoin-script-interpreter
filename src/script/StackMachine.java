package script;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackMachine {

    private Deque<byte[]> stack;

    public StackMachine() {
        this.stack = new ArrayDeque<>();
    }

    public void push(byte[] data) {
        stack.push(data);
    }

    public byte[] pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack underflow");
        }
        return stack.pop();
    }

    public byte[] peek() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void clear() {
        stack.clear();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
