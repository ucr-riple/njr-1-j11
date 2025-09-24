package patterns.gof.behavioral.chainOfResponsibility;

import patterns.gof.helpers.Client;

public class ChainOfResponsibilityClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Logger logger, logger1, logger2;
		logger = new ConsoleLogger(LoggingLevel.All.getCode());
		
		logger1 = new EmailLogger(LoggingLevel.FunctionalMessage.getCode() | LoggingLevel.FunctionalError.getCode());
		logger2 = new FileLogger(LoggingLevel.Warning.getCode() | LoggingLevel.Error.getCode());
		
		logger.setNext(logger1);
		logger1.setNext(logger2);
		
		// Handled by ConsoleLogger
        logger.message("entering function ProcessOrder()", LoggingLevel.Debug);
        logger.message("order record retrieved", LoggingLevel.Info);

        // Handled by ConsoleLogger and FileLogger
        logger.message("customer address details missing in Branch DataBase.", LoggingLevel.Warning);
        logger.message("customer address details missing in Organization DataBase.", LoggingLevel.Error);

        // Handled by ConsoleLogger and EmailLogger
        logger.message("unable to process order ORD1 dated D1 for customer C1.", LoggingLevel.FunctionalError);

        // Handled by ConsoleLogger and EmailLogger
        logger.message("order dispatched.", LoggingLevel.FunctionalMessage);
		
		super.main("Chain of Responsibility");
	}
}