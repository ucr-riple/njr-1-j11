package br.com.bit.ideias.reflection.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bit.ideias.reflection.cache.Cache;
import br.com.bit.ideias.reflection.cache.CacheProvider;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 28/07/2009
 */
public enum TargetType {
	FIELD {
        @Override
		protected Member[] getMembers(final Class<?> classe) {
		    Map<Class<?>, Member[]> cacheContext = getSubCache(classe, FIELD);
		    
		    Member[] fields = cacheContext.get(classe);
		    if(fields != null) return fields;
		    
			Field[] declared = classe.getDeclaredFields();
			cacheContext.put(classe, declared);
			
            return declared;
		}

		@Override
		public boolean isValidMember(final Member member) {
			return member instanceof Field;
		}
	},
	METHOD {
        @Override
        protected Member[] getMembers(final Class<?> classe) {
            Map<Class<?>, Member[]> cacheContext = getSubCache(classe, METHOD);
            
            Member[] methods = cacheContext.get(classe);
            if(methods != null) return methods;
            
            Method[] declared = classe.getDeclaredMethods();
            cacheContext.put(classe, declared);
            
            return declared;
        }

        @Override
        public boolean isValidMember(final Member member) {
            return member instanceof Method;
        }
    }, CONSTRUCTOR {
        @SuppressWarnings("unchecked")
        @Override
        protected Member[] getMembers(final Class<?> classe) {
            Map<Class<?>, Member[]> cacheContext = getSubCache(classe, CONSTRUCTOR);
            
            Member[] constructors = cacheContext.get(classe);
            if(constructors != null) return constructors;
            
            Constructor[] declared = classe.getDeclaredConstructors();
            cacheContext.put(classe, declared);
            
            return declared;
        }

        @Override
        public boolean isValidMember(final Member member) {
            return member instanceof Constructor<?>;
        }
    }, ANY {
        @Override
        protected Member[] getMembers(Class<?> classe) {
            Member[] members = (Member[]) cache.get(classe);
            if(members != null) return members;
            
            Member[] methods = METHOD.getMembers(classe);
            Member[] fields = FIELD.getMembers(classe);
            
            int methodsLength = methods.length;
            int fieldsLength = fields.length;
            
            members = new Member[(methodsLength + fieldsLength)];
            
            for (int i = 0; i < methodsLength; i++) {
                members[i] = methods[i];
            }
            methods = null;
            
            for (int i = 0; i < fieldsLength; i++) {
                members[i + methodsLength] = fields[i];
            }
            fields = null;
            
            cache.add(classe, members);
            
            return members;
        }

        @Override
        public boolean isValidMember(Member member) {
            return true;
        }
	};
	
	protected Cache cache = CacheProvider.getCache();

	public List<Member> obtainMembersInClass(final Class<?> classe) {
		final Member[] members = getMembers(classe);
		return new ArrayList<Member>(Arrays.asList(members));
	}

	public abstract boolean isValidMember(Member member);

	protected abstract Member[] getMembers(final Class<?> classe);
	
	@SuppressWarnings("unchecked")
    protected Map<Class<?>, Member[]> getSubCache(Class<?> classe, TargetType type) {
	    Map<Class<?>, Member[]> fieldsCache = (Map<Class<?>, Member[]>) cache.get(type);
	    
        if(fieldsCache == null) {
            fieldsCache = new HashMap<Class<?>, Member[]>();
            cache.add(type, fieldsCache);
        }
        
        return fieldsCache;
	}
}
