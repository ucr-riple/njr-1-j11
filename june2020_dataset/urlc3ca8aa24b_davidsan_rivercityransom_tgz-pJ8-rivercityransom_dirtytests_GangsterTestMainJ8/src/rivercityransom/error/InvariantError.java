package rivercityransom.error;

public class InvariantError extends ContractError {

	/**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	public InvariantError(String message) {
		super("Invariant failed : "+message);
	}

}
