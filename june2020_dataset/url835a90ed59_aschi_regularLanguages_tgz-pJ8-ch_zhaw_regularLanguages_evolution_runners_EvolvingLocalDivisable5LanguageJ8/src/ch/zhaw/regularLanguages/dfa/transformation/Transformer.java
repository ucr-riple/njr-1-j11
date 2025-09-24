package ch.zhaw.regularLanguages.dfa.transformation;

public interface Transformer<S, T> {
	public T transform(S input);
}
