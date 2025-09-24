package br.com.bit.ideias.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.bit.ideias.reflection.core.Introspector;
import br.com.bit.ideias.reflection.criteria.Criterion;
import br.com.bit.ideias.reflection.criteria.Restriction;
import br.com.bit.ideias.reflection.rql.query.Query;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominio;

/**
 * @author Leonardo Campos
 * @date 24/11/2009
 */
public class SimplePerformanceTest {
    public static void main(String[] args) {
        String methodName = "getAtributoPrivadoString";
        String rql = String.format("from br.com.bit.ideias.reflection.test.artefacts.ClasseDominio where name eq '%s'", methodName);
        ClasseDominio classeDominio = new ClasseDominio();
        int times = 130000;
        long init = 0;
        long difference = 0;
        long finish = 0;
        
        boolean testRegularMethodCall = true;
        boolean testRegularReflectionCall = true;
        boolean testIntrospectorCall = true;
        boolean testCriterionCall = true;
        //========================================
        if(testRegularMethodCall) {
            init = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                classeDominio.getAtributoPrivadoString();
            }
            finish = System.currentTimeMillis();
            
            System.out.println("Regular method call");
            difference = finish - init;
            System.out.println(difference);
        }
        //========================================
        
        //========================================
        if(testRegularReflectionCall) {
            init = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                try {
                    Method declaredMethod = ClasseDominio.class.getDeclaredMethod(methodName);
                    declaredMethod.invoke(classeDominio);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            finish = System.currentTimeMillis();
            
            difference = finish - init;
            System.out.println("Regular reflection lookup and method call");
            System.out.println(difference);
        }
        //========================================
        
        
        //========================================
        if(testIntrospectorCall) {
            init = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                Introspector.inObject(classeDominio).method(methodName).invoke();
            }
            finish = System.currentTimeMillis();
            
            difference = finish - init;
            System.out.println("Introspector reflection lookup and method call");
            System.out.println(difference);
        }
        //========================================
        
        
        
      //========================================
        if(testCriterionCall) {
            Criterion criterion = Introspector.createCriterion(ClasseDominio.class).add(Restriction.eq(methodName));
            init = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                Method result = criterion.uniqueResult();
                try {
                    result.invoke(classeDominio);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            finish = System.currentTimeMillis();
            
            difference = finish - init;
            System.out.println("Criterion method call");
            System.out.println(difference);
        }
        //========================================
        
        
        //========================================
        init = System.currentTimeMillis();
        Introspector introspector = Introspector.forClass(ClasseDominio.class);
        Query query = introspector.query(rql);
        
        for (int i = 0; i < times; i++) {
            Method method = query.uniqueResult();
            try {
                method.invoke(classeDominio);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        finish = System.currentTimeMillis();
        
        difference = finish - init;
        System.out.println("RQL method call");
        System.out.println(difference);
        //========================================
    }
}
