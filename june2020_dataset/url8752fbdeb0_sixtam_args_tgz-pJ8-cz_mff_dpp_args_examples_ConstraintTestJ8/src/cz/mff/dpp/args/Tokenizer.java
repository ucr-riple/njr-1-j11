package cz.mff.dpp.args;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static cz.mff.dpp.args.OptionUtils.OPTION_LIST_TERMINATOR;
import static cz.mff.dpp.args.OptionUtils.LONG_OPTION_PREFIX;
import static cz.mff.dpp.args.OptionUtils.SIMPLE_OPTION_PREFIX;

/**
 * 
 * Parses input parameters to produce {@code String} tokens.
 * 
 * 
 * <p>
 * {@code Tokenizer} processes program's input parameters (or any String[] array
 * for the matter) to produce basic tokens which can be further processed.
 * {@code String} tokens are produced by implementing {@link Iterable}
 * interface.
 * </p>
 * 
 * 
 * <p>
 * {@code Tokenizer} recognizes these types of tokens:
 * 
 * <ul>
 * <li>short option with prefix {@link OptionUtils#SIMPLE_OPTION_PREFIX}</li>
 * <li>long option with prefix {@link OptionUtils#LONG_OPTION_PREFIX}</li>
 * <li>arguments separator {@link OptionUtils#OPTION_LIST_TERMINATOR}</li>
 * <li>parameters of options</li>
 * <li>free arguments</li>
 * </ul>
 * 
 * <br/>
 * For complete set of rules see {@link Parser}
 * </p>
 * 
 * <p>
 * How it works: it moves over arguments splitting options (in form of "-vARG1"
 * or "--long=ARG2" if needed). Extra care must be taken if option is followed
 * by something which might be a negative number!
 * </p>
 * 
 * </p>
 * 
 * 
 * @author Martin Sixta
 * 
 */
final class Tokenizer implements Iterable<String> {

	/** For look-ahead of {@code Option} names */
	private final Introspector introspector;

	/** Parameters to tokenize */
	private final String[] args;

	/**
	 * 
	 * Creates {@code Tokenizer} bind to the {@code args} and the
	 * {@code introspector}
	 * 
	 * 
	 * @param introspector
	 *            {@code Option}s to recognize.
	 * @param args
	 *            Parameters to tokenize.
	 */
	public Tokenizer(final Introspector introspector, final String[] args) {
		this.introspector = introspector;
		this.args = args;
	}

	@Override
	/** {@inheritDoc} */
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			/** position in arguments */
			private int position;

			/** buffer to tokens from split-up options */
			private String buffer;

			/** indication of option list termination */
			boolean options_terminated = false;

			{
				position = 0;
				buffer = null;
			}

			@Override
			/** {@inheritDoc} */
			public boolean hasNext() {
				if (buffer != null || position < args.length) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			/** {@inheritDoc} */
			public String next() {
				String result = null;

				if (buffer != null) {
					result = buffer;
					buffer = null;
				} else if (options_terminated) {
					result = nextArg();
				} else {
					if (position >= args.length) {
						throw new NoSuchElementException();
					}

					result = process(nextArg());
				}

				return result;
			}

			@Override
			/** {@inheritDoc} */
			public void remove() {
				throw new UnsupportedOperationException();
			}

			/**
			 * Processes current argument.
			 * 
			 * If needed the argument is split up.
			 * 
			 * NOTE: SIMPLE_OPTION_PREFIX is itself prefix of LONG_OPTION_PREFIX
			 * this must be handled correctly, i.e. by testing for long option
			 * first
			 * 
			 * @return
			 */
			private String process(String token) {
				String result = null;

				if (token.equals(OPTION_LIST_TERMINATOR)) {
					options_terminated = true;
					result = token;

				} else if (token.startsWith(LONG_OPTION_PREFIX)) {

					result = processLongOption(token);

				} else if (token.startsWith(SIMPLE_OPTION_PREFIX)) {
					result = processSimpleOption(token);
				} else {
					result = token;
				}

				return result;
			}

			/** processes tokens in form of "-o" or "-oVALUE" */
			private String processSimpleOption(String current) {
				String result;

				// FIXME: very ugly ...
				if (isNegativeNumber(current)) {
					
					result = current;

				} else {

					if (current.length() == 2) {
						result = current;
					} else {
						result = current.substring(0, 2);
						buffer = current.substring(2);
					}

				}

				return result;

			}

			/** Processes token in form of --option or --option=value */
			private String processLongOption(String token) {
				String result;

				int equalAt = token.indexOf('=');

				if (equalAt == -1) { // "--option"

					result = token;

				} else { // "--option=value

					result = token.substring(0, equalAt);

					// It's ok for buffer to be empty (in case equalAt + 1
					// == current.length())
					buffer = token.substring(equalAt + 1);
				}

				return result;

			}

			/** Returns next argument to process. */
			private String nextArg() {
				return args[position++];
			}

			/**
			 * Tries to guess whether token is a negative number or a known
			 * option.
			 * 
			 */
			private boolean isNegativeNumber(String token) {

				if (!isOption(token) && token.matches("^-[\\d|.\\d]*")) {
					return true;
				} else {
					return false;
				}

			}

			/**
			 * Decides whether token is a known option.
			 * 
			 */
			private boolean isOption(String token) {
				boolean found = false;

				for (String optionName : introspector.getAllOptionNames()) {
					if (token.startsWith(optionName)) {
						found = true;
						break;
					}
				}

				return found;
			}

		};
	}

}
