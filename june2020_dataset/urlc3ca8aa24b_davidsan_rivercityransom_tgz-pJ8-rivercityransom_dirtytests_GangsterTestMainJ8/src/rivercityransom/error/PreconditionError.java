package rivercityransom.error;

public class PreconditionError extends ContractError {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	public PreconditionError(String message) {
		super("Precondition failed : "+message);
	}

}
