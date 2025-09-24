package edu.sandbox.collectionsandthreads;
//9-16
/**
 * Created by yurij.pyvovarenko on 16.04.14.
 */

import edu.sandbox.accessmodifiers.ParentClass;

import java.util.*;
import java.io.*;

public class CollectionsTests {
    static final int COUNT = 10000;
    static long lStartTime;
    static long lFinishTime;
    static SynchroHTML synchroHTMLFile;
    final static String outHTMLFilePath = "E:\\Юра\\java(!)\\_Books and info\\synchroHTMLFile.html";
    public static final int MAX_ITERATIONS = 15;

    public static void main(String[] args) {
        /*
        collectionsAddGetRemoveTests();
        equalsTests();
        treeTreadsToStackHTMLDemo();
        */
        hashMapTests();

    }

    static void hashMapTests() {
        //String[] strArr = {"a","b","c","d","e"};
        ParentClass[] parArr = new ParentClass[50];
        Map<ParentClass,String> hashMap = new HashMap();
        int i = 0;
        char c = 'a';
        //for (String s : strArr ) {
        while (i<49) {
            ParentClass p = new ParentClass();
            p.publicInt = i;
            parArr[i] = p;
            i++;
            c++;
            hashMap.put(p,(new Character(c).toString()));
        }
        //System.out.println(hashMap);
        i--;
        do {
            System.out.println("index " + i + " - " + hashMap.get(parArr[i]));
            i--;
        } while (i >= 0);
        i=47;
        System.out.println("BEFORE: index " + i + " - " + hashMap.get(parArr[i]));
        parArr[i].publicInt = 1000;
        System.out.println("AFTER: index " + i + " - " + hashMap.get(parArr[i]));
//        i=0;
//        do {
//            System.out.println("index " + i + " - " + hashMap.get(parArr[i]));
//            i++;
//        } while (i < 49);
    }

    static void treeTreadsToStackHTMLDemo() {
        /*
        collectionsAddGetRemoveTests();
        equalsTests();
        */


        try {
            synchroHTMLFile = new SynchroHTML(outHTMLFilePath);
            addHeaderToHTMLFile(synchroHTMLFile);


            synchroHTMLFile.writingHTML("Current thread: " + Thread.currentThread().getName(), 0);
//            ArrayStack arrayStack = new ArrayStack();
//            Thread t1 = startThread(1, arrayStack);
//            Thread t2 = startThread(2, arrayStack);
//            Thread t3 = startThread(3, arrayStack);

            for (int i = 0; i < 10; i++) {
                HTMLArrayStack arrayStack = new HTMLArrayStack();
                Thread t1 = new HTMLThread(1, arrayStack, synchroHTMLFile);
                Thread t2 = new HTMLThread(2, arrayStack, synchroHTMLFile);
                Thread t3 = new HTMLThread(3, arrayStack, synchroHTMLFile);
                t1.setPriority(10);
                t2.setPriority(5);
                t2.setPriority(1);
                synchroHTMLFile.writingHTML("\n<br/>",-1);
                synchroHTMLFile.writingHTML("Priority = " + t1.getPriority(),1);
                synchroHTMLFile.writingHTML("\n<br/>",-1);
                synchroHTMLFile.writingHTML("Priority = " + t2.getPriority(),2);
                synchroHTMLFile.writingHTML("\n<br/>",-1);
                synchroHTMLFile.writingHTML("Priority = " + t3.getPriority(),3);
                t1.start();
                t2.start();
                t3.start();

    /*            Thread t2 = startHTMLThread(2, arrayStack, synchroHTMLFile);
                Thread t3 = startHTMLThread(3, arrayStack, synchroHTMLFile);

                interruptThread(t1);
                interruptThread(t2);
                interruptThread(t3);
    */
                try {
                    t3.join();
                    t2.join();
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            /*
            http://habrahabr.ru/post/164487/
             bool isInterrupted() объекта потока,
              второй — вызвать статический метод bool Thread.interrupted().
               Первый метод возвращает состояние флага прерывания и оставляет этот флаг нетронутым.
                Второй метод возвращает состояние флага и сбрасывает его.
                 Заметьте что Thread.interrupted() — статический метод класса Thread,
                  и его вызов возвращает значение флага прерывания того потока, из которого он был вызван.
                   Поэтому этот метод вызывается только изнутри потока и позволяет потоку проверить своё состояние прерывания.
             */

            addFooterToHTMLFile(synchroHTMLFile);
            synchroHTMLFile.close();
        } catch (IOException e) {
            System.err.print("ошибка файла");
            e.printStackTrace();
        }
        /*catch (InterruptedException e) {
            System.err.print("ошибка потока");
            e.printStackTrace();
        } */
    }

    static Thread startThread(final int i, final ArrayStack arrayStack) {
        System.out.println("Thread: " + i);
        Thread t = new Thread(
                new Runnable() {
                    public void run() {
                        arrayStackTests(i, arrayStack);
                    }
                }
        );
        t.start();
        return t;
    }

    static void interruptThread(Thread t) {
        t.interrupt();
    }

    void collectionsAddGetRemoveTests() {
        ArrayList<Number> arrayList = new ArrayList<Number>(COUNT);
        LinkedList<Number> linkedList = new LinkedList<Number>();

        //add
        System.out.println("--- add() tests ---");

        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            arrayList.add(new Integer(i));
        }
        lFinishTime = System.nanoTime();
        System.out.println(" arrayList<Number>: " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            linkedList.add(new Integer(i));
        }
        lFinishTime = System.nanoTime();
        System.out.println("linkedList<Number>: " + (lFinishTime - lStartTime) + " milisec.");

        String s = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

        String[] strArray = new String[COUNT];
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArray[i] = (s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("   String[]        :      " + (lFinishTime - lStartTime) + " milisec.");

        ArrayList<String> strArrayList = new ArrayList<String>(COUNT);
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArrayList.add(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("   strArrayList<String>:  " + (lFinishTime - lStartTime) + " milisec.");
        strArrayList.clear();
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArrayList.add(0,s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("0  strArrayList<String>:  " + (lFinishTime - lStartTime) + " milisec.");

        LinkedList<String> strLinkedList = new LinkedList<String>();
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.add(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("  strLinkedList<String>:  " + (lFinishTime - lStartTime) + " milisec.");
        strLinkedList.clear();
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.add(0,s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("0 strLinkedList<String>:  " + (lFinishTime - lStartTime) + " milisec.");

        Map<String, String> hashmap = new HashMap<String, String>();
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            hashmap.put(s + i, s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("hashmap<String, String>:  " + (lFinishTime - lStartTime) + " milisec.");

        TreeSet<String> aTreeSet = new TreeSet<String>();
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            aTreeSet.add(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("       aTreeSet<String>: " + (lFinishTime - lStartTime) + " milisec.");


        //--------------- Get form packageInt collection tests -------------------------
        System.out.println("--- get() tests ---");

        lStartTime = System.nanoTime();
        for (String strValue : strArray) {
        }
        lFinishTime = System.nanoTime();
        System.out.println("   String[]        :      " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        for (String strValue : strArrayList) {
        }
        lFinishTime = System.nanoTime();
        System.out.println("arrayList<String>:       " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        for (String strValue : strLinkedList) {
        }
        lFinishTime = System.nanoTime();
        System.out.println("linkedList<String>:      " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        for (Map.Entry<String, String> entry: hashmap.entrySet()) {
            //strValue = entry.getValue();
        }
        lFinishTime = System.nanoTime();
        System.out.println("hashmap<String, String>: " + (lFinishTime - lStartTime) + " milisec.");
        Iterator<Map.Entry<String, String>> itr = hashmap.entrySet().iterator();
        lStartTime = System.nanoTime();
        //while (itr.hasNext()) {}
        for (int i = 0; i < COUNT; i++) {
            itr.next();
        }
        lFinishTime = System.nanoTime();
        System.out.println("Iterator hashmp<St, St>:  " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        for (String strValue : aTreeSet) {
        }
        lFinishTime = System.nanoTime();
        System.out.println("aTreeSet<String>:       " + (lFinishTime - lStartTime) + " milisec.");


        //--------------- Remove form packageInt collection tests -------------------------
        System.out.println("--- remove(value) tests ---");
        lStartTime = System.nanoTime();
        for (int i = COUNT-1; i >= 0; i--) {
            strArrayList.remove(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strArrayList.size() = " + strArrayList.size());
        System.out.println("arrayList<String>: end    " + (lFinishTime - lStartTime) + " milisec.");
        for (int i = 0; i < COUNT; i++) {
            strArrayList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArrayList.remove(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strArrayList.size() = " + strArrayList.size());
        System.out.println("arrayList<String>: 0      " + (lFinishTime - lStartTime) + " milisec.");


        lStartTime = System.nanoTime();
        for (int i = COUNT-1; i >= 0; i--) {
            strLinkedList.remove(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strLinkedList.size() = " + strLinkedList.size());
        System.out.println("linkedList<String>: end    " + (lFinishTime - lStartTime) + " milisec.");
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.remove(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strLinkedList.size() = " + strLinkedList.size());
        System.out.println("linkedList<String>: 0      " + (lFinishTime - lStartTime) + " milisec.");


        System.out.println("--- remove(index) tests ---");

        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArray[i] = null;
        }
        lFinishTime = System.nanoTime();
        System.out.println("   String[]          :      " + (lFinishTime - lStartTime) + " milisec.");

        for (int i = 0; i < COUNT; i++) {
            strArrayList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArrayList.remove(strArrayList.size()-1);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strArrayList.size() = " + strArrayList.size());
        System.out.println("arrayList<String>: end    " + (lFinishTime - lStartTime) + " milisec.");
        for (int i = 0; i < COUNT; i++) {
            strArrayList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strArrayList.remove(0);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strArrayList.size() = " + strArrayList.size());
        System.out.println("arrayList<String>: 0      " + (lFinishTime - lStartTime) + " milisec.");


        for (int i = 0; i < COUNT; i++) {
            strLinkedList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.remove(strLinkedList.size()-1);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strLinkedList.size() = " + strLinkedList.size());
        System.out.println("linkedList<String>: end    " + (lFinishTime - lStartTime) + " milisec.");
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.add(s + i);
        }
        lStartTime = System.nanoTime();
        for (int i = 0; i < COUNT; i++) {
            strLinkedList.remove(0);
        }
        lFinishTime = System.nanoTime();
        System.out.println("strLinkedList.size() = " + strLinkedList.size());
        System.out.println("linkedList<String>: 0      " + (lFinishTime - lStartTime) + " milisec.");

        lStartTime = System.nanoTime();
        //            for (Map.Entry<String, String> entry: hashmap.entrySet()) {
        //                hashmap.remove(entry.getKey());
        //            }
        for (int i = 0; i < COUNT; i++) {
            hashmap.remove(s + i);
        }
        lFinishTime = System.nanoTime();
        System.out.println("hashmap.size() = " + hashmap.size());
        System.out.println("hashmap<String, String>:  " + (lFinishTime - lStartTime) + " milisec.");

        for (int i = 0; i < COUNT; i++) {
            hashmap.put(s + i, s + i);
        }
        System.out.println("hashmap.size() = " + hashmap.size());
        Iterator<Map.Entry<String, String>> itr1 = hashmap.entrySet().iterator();
        lStartTime = System.nanoTime();
        while (itr1.hasNext()) {
            itr1.next();
            itr1.remove();
        }
//        while (itr.hasNext()) {
//                hashmap.remove(itr.next());
//        }
        lFinishTime = System.nanoTime();
        System.out.println("hashmap.size() = " + hashmap.size());
        System.out.println("Iterator hashmp<St, St>:   " + (lFinishTime - lStartTime) + " milisec.");

        Iterator<String> itrOfTree = aTreeSet.iterator();
        lStartTime = System.nanoTime();
        while (itrOfTree.hasNext()) {
            itrOfTree.next();
            itrOfTree.remove();
        }
//        for (int i = 0; i < COUNT; i++) {
//            aTreeSet.remove(s + i);
//        }
        lFinishTime = System.nanoTime();
        System.out.println("aTreeSet.size() = " + aTreeSet.size());
        System.out.println("Iterator  aTreeSet<Stri>: " + (lFinishTime - lStartTime) + " milisec.");
    }

    void equalsTests() {
        System.out.println("\n---- equals tests ----");
        StringsList stringsList1 = new StringsList();
        StringsList stringsList2 = new StringsList();
        String str1 = "";
        String str2 = "";
        for (Integer i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                str1 += i;
                str2 += i;
            }
            stringsList1.add(str1);
            stringsList2.add(str2);
            str1 = "";
            str2 = "";
        }

        System.out.println("stringsList1 = " + stringsList1);
        System.out.println("stringsList2 = " + stringsList2);
        System.out.println("equals test: " + stringsList1.equals(stringsList2));

        //stringsList2.set(3, "7");
        //stringsList2.add("");
        stringsList2.set(3, "33733");
        System.out.println("stringsList1 = " + stringsList1);
        System.out.println("stringsList2 = " + stringsList2);
        System.out.println("equals test: " + stringsList1.equals(stringsList2));

        System.out.println("iterator usage:");
        Iterator<String> strIter = stringsList2.iterator();
        while (strIter.hasNext()) {
            System.out.print(strIter.next() + ". ");
        }
    }

    public static class StringsList extends ArrayList<String> {
        int count;

        @Override
        public boolean add(String s) {
            super.add(s);
            count++;
            return true;
        }

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject)
                return true;
            if (null == otherObject)
                return false;
            if (getClass() != otherObject.getClass())
                return false;
            StringsList other = (StringsList) otherObject;

            return super.equals(other)
                    && count == other.count;
        }
    }

    /*
    Naftalin
    Java Generics and collections
    Example 11-2. A non-thread-safe stack implementation
    */
    interface Stack {
        public void push(int elt);
        public int pop();
        public boolean isEmpty();
    }

    static class ArrayStack implements Stack{
        private final int MAX_ELEMENTS = 30;
        private final int EMPTY_VAL = -99;
        private int[] stack;
        private int index;

        public ArrayStack() {
            stack = new int[MAX_ELEMENTS];
            Arrays.fill(stack, EMPTY_VAL);
            index = -1;
        }
        public synchronized void push(int elt) {
            if (index != stack.length - 1) {
                index++;                                        //1
                stack[index] = elt;                             //2
            } else {
                throw new IllegalStateException("stack overflow");
            }
        }
        public synchronized int pop() {
            if (index != -1) {
                index--;
                int value = stack[index+1];
                stack[index+1] = EMPTY_VAL;
                return value;
            } else {
                throw new IllegalStateException("stack underflow");
            }
        }
        public synchronized boolean isEmpty() { return index == -1; }
        public synchronized int getIndex() { return index; }
        public synchronized String toString () { return Arrays.toString(stack); }
    }


    /* Java. Промышленное программирование
    пример # 8 : синхронизация записи информации в файл : MyThread.java :
    Synchro.java : SynchroThreads.java */
    public static class Synchro {
        private FileWriter fileWriter;

        public Synchro(String file) throws IOException {
            fileWriter = new FileWriter(file, false);
        }
        public void close() {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public synchronized void writing(String str, int i) {
            try {
                System.out.print(str + i);
                fileWriter.append(str + i);
                Thread.sleep((long)(Math.random() * 50));
                System.out.print("->" + i + " ");
                fileWriter.append("->" + i + " ");
            } catch (IOException e) {
                System.err.print("ошибка файла");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.print("ошибка потока");
                e.printStackTrace();
            }
        }
    }

    static void arrayStackTests(int t, ArrayStack arrayStack) {
        System.out.println("TREAD " + t + " ---- Example 11-2. A non-thread-safe" +
                " stack implementation was improved by adding 'synchronized'" +
                " to methods: push, pop, isEmpty, getIndex ----");
        for (int i = 0; i<9; i++) {
            arrayStack.push(i);
            System.out.println("Tread " + t + ". push(" + i + "). Index = " + arrayStack.getIndex() + " Stack: " + arrayStack);
            for (int j = 0; j<1; j++) {}
        }
        for (int i = 0; i<9; i++) {
            System.out.println("Tread " + t + ". pop(): " + arrayStack.pop() + ". Index = " + arrayStack.getIndex() + " Stack: " + arrayStack);
            for (int j = 0; j<100000; j++) {}
        }
    }

    //html header and styles
    static void addHeaderToHTMLFile(SynchroHTML synchroHTML) {
        synchroHTML.writingHTML(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML Experimental//EN\">\n" +
                "<html>\n" +
                "  <head>\n" +
                "\t<style>\n" +

                "\t\tbody {\n" +
                "\t\t\tfont-weight:bold;\n" +
                "\t\t\tfont-size:10px;\n" +
                "\t\t\tfont-family: Verdana, Tahoma, Arial;\n" +
                "\t\t}\n" +
                "\t\t.thread0 {\n" +
                "\t\t\tfont-weight:normal;\n" +
                "\t\t\tcolor:#dddddd;\n" +
                "\t\t}\n" +
                "\t\t.thread1 {\n" +
                "\t\t\tcolor:red;\n" +
                "\t\t}\n" +
                "\t\t.thread2 {\n" +
                "\t\t\tcolor:green;\n" +
                "\t\t}\n" +
                "\t\t.thread3 {\n" +
                "\t\t\tcolor:blue;\n" +
                "\t\t}" +

                "\t</style>" +
                "  </head>\n" +
                "  <body>\n"
                ,-1
        );
    }

    //html footer
    static void addFooterToHTMLFile(SynchroHTML synchroHTML) {
        synchroHTML.writingHTML(
                "\n  </body>\n" +
                "</html>"
                ,-1
        );
    }

    interface HTMLStack {
        public void push(String elt);
        public String pop();
        public boolean isEmpty();
    }

    static class HTMLArrayStack implements HTMLStack{
        private final int MAX_ELEMENTS = MAX_ITERATIONS * 3;
        private final String EMPTY_VAL = HTMLString("00", "thread" + 0);
        private String[] stack;
        private int index;

        public HTMLArrayStack() {
            stack = new String[MAX_ELEMENTS];
            Arrays.fill(stack, EMPTY_VAL);
            index = -1;
        }
        public synchronized void push(String elt) {
            if (index != stack.length - 1) {
                index++;                                        //1
                stack[index] = elt;                             //2
            } else {
                throw new IllegalStateException("stack overflow");
            }
        }

        public synchronized String pop() {
            if (index != -1) {
                index--;
                String value = stack[index+1];
                stack[index+1] = EMPTY_VAL;
                return value;
            } else {
                throw new IllegalStateException("stack underflow");
            }
        }
        public synchronized boolean isEmpty() { return index == -1; }
        public synchronized int getIndex() { return index; }
        public synchronized String toString () { return Arrays.toString(stack); }
    }

    static Thread startHTMLThread(final int i, final HTMLArrayStack arrayStack, final SynchroHTML synchroHTMLFile) {
        System.out.println("Thread: " + i);
        Thread t = new Thread(
                new Runnable() {
                    public void run() {
                        HTMLArrayStackTests(i, arrayStack, synchroHTMLFile);
                    }
                }
        );
        t.start();
        return t;
    }

    //public static class HTMLThread implements Runnable {
    public static class HTMLThread extends Thread {
        private SynchroHTML synchroHTML;
        HTMLArrayStack arrayStack;
        int threadNumber;

        public HTMLThread(int threadNumber, HTMLArrayStack arrayStack, SynchroHTML synchroHTML) {
            super("T" + threadNumber);
            this.synchroHTML = synchroHTML;
            this.threadNumber = threadNumber;
            this.arrayStack = arrayStack;
        }
        public void run() {
            HTMLArrayStackTests(threadNumber, arrayStack, synchroHTML);
        }
    }

    static synchronized String HTMLString(String str, String cssClass) {
        return "<span class='" +
                cssClass + "'>" + str + "</span>";
    }

    static void HTMLArrayStackTests(int threadNumber, HTMLArrayStack arrayStack, SynchroHTML synchroHTMLFile) {
        //synchroHTMLFile.writingHTML("<br/>---- THREAD " + threadNumber + " STARTED ----", threadNumber);
        for (int i = 0; i < MAX_ITERATIONS; i++) {

            synchronized (arrayStack) {
                arrayStack.push(HTMLString("" + i, "thread" + threadNumber));
                synchroHTMLFile.writingHTML("\n<br/>", -1);
                synchroHTMLFile.writingHTML("Tread " + threadNumber + ". push(" + i + "). Index = "
                        + arrayStack.getIndex()
                        , threadNumber);
                synchroHTMLFile.writingHTML(" Stack: " + arrayStack.toString(),-1);
            }
            for (int j = 0; j<1000000; j++) {}
        }
        String poppedValue = new String();
        for (int i = 0; i < MAX_ITERATIONS; i++) {

            synchronized (arrayStack) {
                poppedValue = arrayStack.pop();
                synchroHTMLFile.writingHTML("\n<br/>",-1);
                synchroHTMLFile.writingHTML("Tread " + threadNumber + ". pop(): "
                        + poppedValue + ". Index = "
                        + arrayStack.getIndex()
                        , threadNumber);
                synchroHTMLFile.writingHTML(" Stack: " + arrayStack.toString(),-1);
            }
            for (int j = 0; j<100000; j++) {}
        }
    }
}
