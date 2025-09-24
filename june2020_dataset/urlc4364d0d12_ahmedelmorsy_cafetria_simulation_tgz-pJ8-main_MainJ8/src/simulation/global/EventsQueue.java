package simulation.global;

import java.util.PriorityQueue;

public class EventsQueue {

    private static PriorityQueue<Node> queue = new PriorityQueue<Node>();

    public static void enqueue(int time, Event event) {
        Node n = new Node();
        n.time = time;
        n.e = event;
        queue.add(n);
        System.out.println("[EventsQueue] an event is added for time " + time);
    }
    
    public static int getSize() {
        return queue.size();
    }
    
    public static int peekTime() {
        if (queue.size() == 0) return Integer.MAX_VALUE;
        return queue.peek().time;
    }
    
    private static class Node implements Comparable<Node>{
        public int time;
        public Event e;
        
        @Override
        public int compareTo(Node arg0) {
            if (this.time < arg0.time) return -1;
            if (this.time > arg0.time) return 1;
            return 0;
        }
    }

    public static void executeEvent() {
        SimulationClk.clock = peekTime();
        Event e = queue.poll().e;
        System.out.println("[" + SimulationClk.clock + "] " + e.getDescription());
        e.execute();
    }
}
