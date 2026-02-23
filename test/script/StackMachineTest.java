package script;

public class StackMachineTest {

    public static void main(String[] args) {

        System.out.println("Running StackMachine tests...");

        testPushAndPop();
        testPeek();
        testUnderflow();

        System.out.println("All StackMachine tests passed.");
    }

    private static void testPushAndPop() {
        StackMachine stack = new StackMachine();

        stack.push("A".getBytes());
        stack.push("B".getBytes());

        if (stack.size() != 2) {
            fail("Push failed");
        }

        byte[] popped = stack.pop();

        if (!new String(popped).equals("B")) {
            fail("Pop returned wrong value");
        }

        if (stack.size() != 1) {
            fail("Size incorrect after pop");
        }
    }

    private static void testPeek() {
        StackMachine stack = new StackMachine();

        stack.push("X".getBytes());

        byte[] top = stack.peek();

        if (!new String(top).equals("X")) {
            fail("Peek returned wrong value");
        }

        if (stack.size() != 1) {
            fail("Peek modified stack");
        }
    }

    private static void testUnderflow() {
        StackMachine stack = new StackMachine();

        try {
            stack.pop();
            fail("Expected exception for empty stack");
        } catch (RuntimeException e) {
            // Expected
        }
    }

    private static void fail(String message) {
        throw new RuntimeException("Test failed: " + message);
    }
}
