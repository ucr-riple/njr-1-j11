package sg.edu.nus.cs5344.spring14.twitter.datastructure;

/**
 * This interface marks an object that can copy itself.
 * Its functionality resembles that of the Clonable interface,
 * but differs in a few ways.
 * 
 * <ul>
 * <li> It has a generic type parameter for type safety.
 * <li> The copy method is always public, which prevents some compile
 * errors due to type erasure.
 * </ul>
 * 
 * The generic return method increases type safety, though not so well
 * when dealing with inheritance.
 * 
 * @author Tobias Bertelsen
 *
 * @param <E> Should be the same type
 */
public interface Copyable<E> {
	E copy();
}
