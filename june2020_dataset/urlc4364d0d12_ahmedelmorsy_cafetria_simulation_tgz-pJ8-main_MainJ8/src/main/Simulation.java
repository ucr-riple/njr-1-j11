package main;

import java.io.File;
import java.util.Random;

import simulation.global.Const;
import simulation.global.Event;
import simulation.global.EventsQueue;
import simulation.global.SimulationClk;
import simulation.global.Statistics;
import simulation.logging.FileLogger;
import simulation.queue.CustomServer;
import simulation.queue.Customer;
import simulation.queue.InfServersQueueSystem;
import simulation.queue.QueueSystem;
import simulation.queue.UniformServer;
import simulation.random.ExponentialRandom;

public class Simulation {

    private static final int MAX_TIME = 6300;
    private static final int INTERARRIVAL_AVG = 30;
    private static Random groupsRand = new Random();
    private static Random routeRand = new Random();

    private QueueSystem hotfoodSys;
    private QueueSystem sandwichesSys;
    private QueueSystem drinkSys;
    private QueueSystem[] cashiers;

    private ExponentialRandom arrivalRand;

    public Simulation(int hotfoodEmp, int sandwichesEmp, int cashiersNum,
            String traceFile) {
        SimulationClk.clock = 0;

        this.arrivalRand = new ExponentialRandom(INTERARRIVAL_AVG);
        init(hotfoodEmp, sandwichesEmp, cashiersNum);
        Statistics.trace = new FileLogger(new File(traceFile));
    }

    private void init(int hotfoodEmp, int sandwichesEmp, int cashiersNum) {
        UniformServer hotfoodServ = new UniformServer(Const.HOTFOOD_SERVER, 50,
                120, 20, 40, hotfoodEmp);
        UniformServer sandwichesServ = new UniformServer(Const.SANDWICH_SERVER,
                60, 180, 5, 15, sandwichesEmp);
        UniformServer drinksServ = new UniformServer(Const.DRINKS_SERVER, 5,
                20, 5, 10, 1);

        this.hotfoodSys = new QueueSystem(Const.HOTFOOD_SERVER, hotfoodServ, -1);
        this.sandwichesSys = new QueueSystem(Const.SANDWICH_SERVER,
                sandwichesServ, -1);
        this.drinkSys = new InfServersQueueSystem(Const.DRINKS_SERVER,
                drinksServ, -1);

        this.cashiers = new QueueSystem[cashiersNum];
        for (int i = 0; i < cashiersNum; i++) {
            CustomServer temp = new CustomServer(Const.CASHIER_SERVER);
            cashiers[i] = new QueueSystem(Const.CASHIER_SERVER, temp, -1);
        }
    }

    public void run() {
        int lastArrival = 0;

        Statistics.console.log("Simulation Starts ....");
        while (true) {
            int nextArrival = arrivalRand.nextInt();
            while (EventsQueue.peekTime() < lastArrival + nextArrival) {
                EventsQueue.executeEvent();
            }
            SimulationClk.clock += nextArrival;
            if (SimulationClk.clock > MAX_TIME)
                break;
            lastArrival = SimulationClk.clock;
            int group = getCustomerNum();
            Statistics.console.log("[" + SimulationClk.clock + "] arrival of "
                    + group + " Customers");
            Statistics.trace.log("[" + SimulationClk.clock + "][Arrival]"
                    + group);
            Statistics.CustomersEnteredSystem(group);
            for (int i = 0; i < group; i++) {
                final Customer cust = new Customer();
                int route = getRoute();
                cust.setType(route);
                switch (route) {
                case Const.CUST_HOTFOOD:
                    Statistics.console.log("Customer " + cust.getId()
                            + " going to hot food");
                    Statistics.UpdateQueueLength(hotfoodSys.getQueueLength(),
                            Const.HOTFOOD_SERVER);
                    // Statistics.CustomerEnteredQueue(cust,
                    // Const.HOTFOOD_SERVER);
                    hotfoodSys.enqueue(cust, new Event() {

                        @Override
                        public void execute() {
                            Statistics.console.log("Customer " + cust.getId()
                                    + " finished hot food and going to drinks");
                            Statistics.UpdateQueueLength(
                                    hotfoodSys.getQueueLength(),
                                    Const.HOTFOOD_SERVER);
                            // Statistics.CustomerQuitQueue(cust,
                            // Const.HOTFOOD_SERVER);
                            // Statistics.CustomerEnteredQueue(cust,
                            // Const.DRINKS_SERVER);
                            drinkSys.enqueue(cust, new JoiningCashierEvent(
                                    cashiers, cust));
                        }

                        @Override
                        public String getDescription() {
                            return "hot food service event";
                        }
                    });
                    break;
                case Const.CUST_SANDWICHES:
                    Statistics.console.log("Customer " + cust.getId()
                            + " going to sandwiches");
                    Statistics.UpdateQueueLength(
                            sandwichesSys.getQueueLength(),
                            Const.SANDWICH_SERVER);
                    // Statistics.CustomerEnteredQueue(cust,
                    // Const.SANDWICH_SERVER);
                    sandwichesSys.enqueue(cust, new Event() {

                        @Override
                        public void execute() {
                            Statistics.console.log("Customer "
                                    + cust.getId()
                                    + " finished sandwiches and going to drinks");
                            Statistics.UpdateQueueLength(
                                    sandwichesSys.getQueueLength(),
                                    Const.SANDWICH_SERVER);
                            // Statistics.CustomerQuitQueue(cust,
                            // Const.SANDWICH_SERVER);
                            // Statistics.CustomerEnteredQueue(cust,
                            // Const.DRINKS_SERVER);
                            drinkSys.enqueue(cust, new JoiningCashierEvent(
                                    cashiers, cust));
                        }

                        @Override
                        public String getDescription() {
                            return "sandwiches service event";
                        }
                    });
                    break;
                case Const.CUST_DRINKS:
                    Statistics.console.log("Customer " + cust.getId()
                            + " going to drinks only");
                    // Statistics.CustomerEnteredQueue(cust,
                    // Const.DRINKS_SERVER);
                    drinkSys.enqueue(cust, new JoiningCashierEvent(cashiers,
                            cust));
                    break;
                default:
                    break;
                }
            }
        }
        while (EventsQueue.getSize() > 0) {
            EventsQueue.executeEvent();
        }
        Statistics.console.log("Simulation Finished ....");
        // average delays in queue for hotfood, sandwiches and cashiers
        Statistics.console.log("Average Delay in Hot Food Server= "
                + Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER));

        Statistics.console.log("Average Delay in Sandwiches Server= "
                + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER));

        Statistics.console.log("Average Delay in Drinks Server= "
                + Statistics.getAvgDelayInQueue(Const.DRINKS_SERVER));

        Statistics.console.log("Average Delay in Cashiers= "
                + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER));
        
        Statistics.console.log("-------------------------------");
        // maximum delays in queue for hotfood, sandwiches and cashiers
        Statistics.console.log("Max Delay in Hot Food Server= "
                + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER));

        Statistics.console.log("Max Delay in Sandwiches Server= "
                + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER));

        Statistics.console.log("Max Delay in Drinks Server= "
                + Statistics.getMaxDelayInQueue(Const.DRINKS_SERVER));

        Statistics.console.log("Max Delay in Cashiers= "
                + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER));

        Statistics.console.log("-------------------------------");
        // time average number in queue for hotfood, sandwiches and cashiers
        Statistics.console
                .log("Time average number in queue in Hot Food Server= "
                        + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER));

        Statistics.console
                .log("Time average number in queue in Sandwiches Server= "
                        + Statistics
                                .getTimeAvgNumInQueue(Const.SANDWICH_SERVER));

        Statistics.console.log("Time average number in queue in Cashiers= "
                + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER));

        Statistics.console.log("-------------------------------");
        // max number in queue for hotfood, sandwiches and cashiers
        Statistics.console.log("Max number in queue in Hot Food Server= "
                + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER));

        Statistics.console.log("max number in queue in Sandwiches Server= "
                + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER));

        Statistics.console.log("max number in queue in Cashiers= "
                + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER));

        Statistics.console.log("-------------------------------");
        // average total delay in all queue for each type f customers
        Statistics.console
                .log("Average delay in all queues for hotfood server Customers= "
                        + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD));

        Statistics.console
                .log("Average delay in all queues for sandwiches server Customers= "
                        + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES));

        Statistics.console
                .log("Average delay in all queues for drinks server Customers= "
                        + Statistics.getAvgDelayForCust(Const.CUST_DRINKS));

        Statistics.console.log("-------------------------------");
        // max total delay in all queue for each type f customers
        Statistics.console
                .log("Max delay in all queues for hotfood server Customers= "
                        + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD));

        Statistics.console
                .log("Max delay in all queues for sandwiches server Customers= "
                        + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES));

        Statistics.console
                .log("Max delay in all queues for drinks server Customers= "
                        + Statistics.getMaxDelayForCust(Const.CUST_DRINKS));

        Statistics.console.log("-------------------------------");
        // overall average delay in all queue for all customers
        Statistics.console
                .log("Overall average delay in queues for all Customers= "
                        + Statistics.getOverallAvgDelay());

        Statistics.console
                .log("Overall average delay in queues for all Customers wieghted by their prob= "
                        + (0.8
                                * Statistics
                                        .getMaxDelayForCust(Const.CUST_HOTFOOD)
                                + 0.15
                                * Statistics
                                        .getMaxDelayForCust(Const.CUST_SANDWICHES) + 0.05 * Statistics
                                .getMaxDelayForCust(Const.CUST_DRINKS)));

        Statistics.console.log("-------------------------------");
        // timeavg total number in system
        Statistics.console.log("Time average number in system = "
                + Statistics.getTimeAvgNumInSystem());
        Statistics.console.log("-------------------------------");
        // max number in system
        Statistics.console.log("max number in system = "
                + Statistics.getMaxNumInSystem());

        Statistics.console
                .log("-----------------------------------------------------");
        Statistics.console.close();
        Statistics.trace.close();
        // Statistics.drawQueueLen();
    }

    private int getRoute() {
        double routeProb = routeRand.nextDouble();
        if (routeProb <= 0.8) {
            return Const.CUST_HOTFOOD;
        } else if (routeProb <= 0.95) {
            return Const.CUST_SANDWICHES;
        } else {
            return Const.CUST_DRINKS;
        }
    }

    private int getCustomerNum() {
        double groupsProb = groupsRand.nextDouble();
        if (groupsProb <= 0.5) {
            return 1;
        } else if (groupsProb <= 0.8) {
            return 2;
        } else if (groupsProb <= 0.9) {
            return 3;
        } else {
            return 4;
        }
    }

    private static class JoiningCashierEvent implements Event {

        private QueueSystem[] cashiers;
        private Customer cust;

        public JoiningCashierEvent(QueueSystem[] cashiers, Customer cust) {
            this.cashiers = cashiers;
            this.cust = cust;
        }

        @Override
        public void execute() {
            String len = "";
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            int sum = 0;
            for (int i = 0; i < cashiers.length; i++) {
                len += " " + cashiers[i].getQueueLength();
                if (cashiers[i].getQueueLength() < min) {
                    min = cashiers[i].getQueueLength();
                    minIndex = i;
                }
                sum += cashiers[i].getQueueLength();
            }
            Statistics.console.log("Customer " + cust.getId()
                    + " want to go to cashier and cashiers have the following:"
                    + len);

            Statistics.console.log("Customer " + cust.getId()
                    + " decided to go to cashier " + minIndex);
            Statistics.UpdateQueueLength(sum, Const.CASHIER_SERVER);
            final int index = minIndex;
            cashiers[minIndex].enqueue(cust, new Event() {

                @Override
                public void execute() {
                    // Do nothing
                    Statistics.console.log("Customer " + cust.getId()
                            + " finished and leaving ...");
                    Statistics.trace.log("[" + SimulationClk.clock
                            + "][Finish]" + cust.getId());
                    int sum = 0;
                    for (int i = 0; i < cashiers.length; i++) {
                        sum += cashiers[i].getQueueLength();
                    }
                    Statistics.UpdateQueueLength(sum, Const.CASHIER_SERVER);
                    Statistics.CustomersQuitSystem(1);
                }

                @Override
                public String getDescription() {
                    return "Cashier " + index + " event";
                }
            });
        }

        @Override
        public String getDescription() {
            return "Joining Cashier event";
        }
    }
}
