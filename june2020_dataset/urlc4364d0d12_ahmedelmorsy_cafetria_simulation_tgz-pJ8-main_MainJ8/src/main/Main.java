package main;

import java.io.File;

import simulation.global.Const;
import simulation.global.Statistics;
import simulation.logging.FileLogger;


public class Main {    
    
    public static void main(String[] args) {
        String base = "results50";
        int replications = 50;
        
        FileLogger result = new FileLogger(new File(base + ".data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(1, 1, 2, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "1.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(1, 1, 3, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "2.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(2, 1, 2, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "3.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(1, 2, 2, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "4.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(2, 2, 2, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "5.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(2, 1, 3, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "6.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(1, 2, 3, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
        
        result = new FileLogger(new File(base + "7.data"));
        for (int i = 0; i < replications; i++) {
            Statistics.reset();
            Simulation sim = new Simulation(2, 2, 3, "trace" + i);
            sim.run();
            /*
             * 01 - Average Delay in HotFood Server
             * 02 - Average Delay in Sandwiches Server
             * 03 - Average Delay in Cashier Server
             * 04 - Max Delay in HotFood Server
             * 05 - Max Delay in Sandwiches Server
             * 06 - Max Delay in Cashiers Server
             * 07 - Time Average Number in HotFood Server
             * 08 - Time Average Number in Sandwiches Server
             * 09 - Time Average Number in Cashiers Server
             * 10 - Max Number in HotFood Server
             * 11 - Max Number in Sandwiches Server
             * 12 - Max Number in Cashiers Server
             * 13 - Average Delay for HotFood Customers
             * 14 - Average Delay for Sandwiches Customers
             * 15 - Average Delay for Drinks Customers
             * 16 - Max Delay for HotFood Customers
             * 17 - Max Delay for Sandwiches Customers
             * 18 - Max Delay for Drinks Customers
             * 19 - Overall Average Delay
             * 20 - Overall Average Delay weighted by individual delays
             * 21 - Time Average total number in system
             * 22 - Max total number in system
             */
            result.log(
                    Statistics.getAvgDelayInQueue(Const.HOTFOOD_SERVER)//1
                    + "," + Statistics.getAvgDelayInQueue(Const.SANDWICH_SERVER)//2
                    + "," + Statistics.getAvgDelayInQueue(Const.CASHIER_SERVER)//3
                    + "," + Statistics.getMaxDelayInQueue(Const.HOTFOOD_SERVER)//4
                    + "," + Statistics.getMaxDelayInQueue(Const.SANDWICH_SERVER)//5
                    + "," + Statistics.getMaxDelayInQueue(Const.CASHIER_SERVER)//6
                    + "," + Statistics.getTimeAvgNumInQueue(Const.HOTFOOD_SERVER)//7
                    + "," + Statistics.getTimeAvgNumInQueue(Const.SANDWICH_SERVER)//8
                    + "," + Statistics.getTimeAvgNumInQueue(Const.CASHIER_SERVER)//9
                    + "," + Statistics.getMaxNumInQueue(Const.HOTFOOD_SERVER)//10
                    + "," + Statistics.getMaxNumInQueue(Const.SANDWICH_SERVER)//11
                    + "," + Statistics.getMaxNumInQueue(Const.CASHIER_SERVER)//12
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)//13
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES)//14
                    + "," + Statistics.getAvgDelayForCust(Const.CUST_DRINKS)//15
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_HOTFOOD)//16
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_SANDWICHES)//17
                    + "," + Statistics.getMaxDelayForCust(Const.CUST_DRINKS)//18
                    + "," + Statistics.getOverallAvgDelay()//19
                    + "," + (0.8 * Statistics.getAvgDelayForCust(Const.CUST_HOTFOOD)
                    + 0.15 * Statistics.getAvgDelayForCust(Const.CUST_SANDWICHES) 
                    + 0.05 * Statistics.getAvgDelayForCust(Const.CUST_DRINKS))//20
                    + "," + Statistics.getTimeAvgNumInSystem()//21
                    + "," + Statistics.getMaxNumInSystem()//22
            );
        }
        result.close();
    }

}
