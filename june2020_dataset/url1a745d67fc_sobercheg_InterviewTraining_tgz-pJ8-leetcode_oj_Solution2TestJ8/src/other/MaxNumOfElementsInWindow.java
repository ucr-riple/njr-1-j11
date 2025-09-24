package other;

/**
 * Created by Sobercheg on 12/2/13.
 */
public class MaxNumOfElementsInWindow {

    static class MaxWindow {
        int startIndex;
        int endIndex;
        int num;
        int diff;

        @Override
        public String toString() {
            return "MaxWindow{" +
                    "startIndex=" + startIndex +
                    ", endIndex=" + endIndex +
                    ", num=" + num +
                    ", diff=" + diff +
                    '}';
        }
    }

    public static MaxWindow getMaxWindow(int[] array, int window) {
        int startIndex = 0;
        int currentDiff;
        int currentNum = 0;
        MaxWindow result = new MaxWindow();
        for (int i = 0; i < array.length; i++) {
            currentNum++;
            // move startIndex to the right until the window size is met
            currentDiff = array[i] - array[startIndex];
            while (currentDiff > window) {
                startIndex++;
                currentDiff = array[i] - array[startIndex];
                currentNum--;
            }

            if (currentNum > result.num) {
                result.num = currentNum;
                result.startIndex = startIndex;
                result.endIndex = i;
                result.diff = currentDiff;
            }

        }

        return result;
    }

    public static void main(String[] args) {
        MaxWindow result = MaxNumOfElementsInWindow.getMaxWindow(new int[]{1, 60, 70, 75, 76, 200}, 25);
        System.out.println(result);
    }
}
