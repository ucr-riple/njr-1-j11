package mi.stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author caofuxiang
 *         2014-05-13 17:26
 */
public class CombinedStackTest {

    @Test
    public void testPopPush() throws Exception {
        int num = 10000;
        int capacity = num * 10;
        Random random = new Random();

        ArrayList<Integer> expected = new ArrayList<>();
        ArrayStack<Integer> arrayStack = new ArrayStack<>(capacity);
        LinkedStack<Integer> linkedStack = new LinkedStack<>();

        for (int i = 0; i < num; i ++) {
            int data = random.nextInt();
            expected.add(data);
            arrayStack.push(data);
            linkedStack.push(data);
        }

        int num1 = num/2;
        for (int i = 0; i < num1; i ++) {
            Integer e = expected.remove(expected.size() - 1);
            Assert.assertEquals(e, arrayStack.pop());
            Assert.assertEquals(e, linkedStack.pop());
        }

        CombinedStack<Integer> combinedStack1 = new CombinedStack<>(new ArrayForkableStack<Integer>(capacity), arrayStack);
        CombinedStack<Integer> combinedStack2 = new CombinedStack<>(new LinkedStack<Integer>(), arrayStack);
        LinkedStack<Integer> linkedStack1 = linkedStack.fork();

        int num2 = num;
        for (int i = 0; i < num2; i ++) {
            int data = random.nextInt();
            expected.add(data);
            combinedStack1.push(data);
            combinedStack2.push(data);
            linkedStack.push(data);
            linkedStack1.push(data);
        }

        int num3 = num/2;
        for (int i = 0; i < num3; i ++) {
            Integer e = expected.remove(expected.size() - 1);
            Assert.assertEquals(e, combinedStack1.pop());
            Assert.assertEquals(e, combinedStack2.pop());
            Assert.assertEquals(e, linkedStack.pop());
            Assert.assertEquals(e, linkedStack1.pop());
        }

        CombinedStack<Integer> combinedStack3 = combinedStack1.fork();
        LinkedStack<Integer> linkedStack2 = linkedStack1.fork();
        int num4 = expected.size();
        for (int i = 0; i < num4; i ++) {
            Integer e = expected.remove(expected.size() - 1);
            Assert.assertEquals(e, combinedStack1.pop());
            Assert.assertEquals(e, combinedStack2.pop());
            Assert.assertEquals(e, combinedStack3.pop());
            Assert.assertEquals(e, linkedStack.pop());
            Assert.assertEquals(e, linkedStack1.pop());
            Assert.assertEquals(e, linkedStack2.pop());
        }

        Assert.assertTrue(combinedStack1.isEmpty());
        Assert.assertTrue(combinedStack2.isEmpty());
        Assert.assertTrue(combinedStack3.isEmpty());
        Assert.assertEquals(num - num1, arrayStack.size());
        Assert.assertTrue(linkedStack.isEmpty());
        Assert.assertTrue(linkedStack1.isEmpty());
        Assert.assertTrue(linkedStack2.isEmpty());
    }
}
