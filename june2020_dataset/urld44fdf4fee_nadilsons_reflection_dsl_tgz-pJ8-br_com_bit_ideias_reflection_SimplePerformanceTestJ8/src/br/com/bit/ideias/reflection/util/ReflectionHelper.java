package br.com.bit.ideias.reflection.util;

import java.lang.reflect.Member;
import java.util.List;

import br.com.bit.ideias.reflection.type.TargetType;

/**
 * @author Nadilson Oliveira da Silva
 * @since 04/12/2009
 */
public class ReflectionHelper {

	private static final ReflectionHelper INSTANCE = new ReflectionHelper();

	private ReflectionHelper() {
	}

	public static ReflectionHelper getInstance() {
		return INSTANCE;
	}

	public boolean isSameParameters(Class<?>[] methodParametersTypes, Class<?>[] parametersTypes) {
		if (methodParametersTypes.length != parametersTypes.length)
			return false;

		for (int i = 0; i < methodParametersTypes.length; i++)
			if (!methodParametersTypes[i].equals(parametersTypes[i]))
				return false;

		return true;
	}

	public List<Member> getMembers(Class<?> classe, TargetType type) {
		List<Member> members = type.obtainMembersInClass(classe);

		while (classe.getSuperclass() != null) {
			classe = classe.getSuperclass();
			members.addAll((List<Member>) type.obtainMembersInClass(classe));
		}

		return members;
	}

}
