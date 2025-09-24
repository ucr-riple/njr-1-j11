package br.com.bit.ideias.reflection.core;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

import br.com.bit.ideias.reflection.exceptions.ConstructorNotExistsException;
import br.com.bit.ideias.reflection.exceptions.FieldNotExistsException;
import br.com.bit.ideias.reflection.exceptions.FieldPrivateException;
import br.com.bit.ideias.reflection.exceptions.InvalidParameterException;
import br.com.bit.ideias.reflection.exceptions.InvalidStateException;
import br.com.bit.ideias.reflection.exceptions.MethodNotExistsException;
import br.com.bit.ideias.reflection.exceptions.MethodPrivateException;
import br.com.bit.ideias.reflection.exceptions.StaticFieldNotExistsException;
import br.com.bit.ideias.reflection.exceptions.StaticMethodNotExistsException;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominio;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominioFilha;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominioSemConstructor;

/**
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
public class IntrospectorTest {

	private static final Integer INTEIRO = 10;

	private static final String STRING = "valor_default";

	private static final Class<ClasseDominio> TARGET_CLASS = ClasseDominio.class;

	private static final ClasseDominio classeDominio = new ClasseDominio(INTEIRO, STRING);
	
	private static final ClasseDominioFilha classeDominioFilha = new ClasseDominioFilha();

	private Introspector introspectorForClass;

	private Introspector introspectorInObject;

	@Before
	public void prepare() {
		introspectorForClass = Introspector.forClass(TARGET_CLASS);
		introspectorForClass.create(INTEIRO, STRING);
		introspectorInObject = Introspector.inObject(classeDominio);

		classeDominio.setAtributoPrivadoInteiro(INTEIRO);
		classeDominio.setAtributoPrivadoString(STRING);
	}
	
	@Test
	public void testOverloadedMethodShouldWorkWithRightParameters() throws Exception {
		Introspector.inObject("aaa").method("substring").invoke(1);
		Introspector.inObject("aaa").method("substring").invoke(1,2);
	}
	
	@Test(expected=InvalidParameterException.class)
	public void testMoreThanOneParameterForAFieldShouldThrowException() throws Exception {
		Introspector.inObject(new ClasseDominio()).field("comecaPriva").invoke(true, true, true);
	}

	@Test
	public void testStaticMethodShouldNotRequireAnInstance() throws Exception {
		String result = Introspector.forClass(String.class).method("valueOf").invoke(1);
		assertEquals("1", result);
	}
	// /////////////////////////////////////////////////////////////////////////
	// classForName
	// /////////////////////////////////////////////////////////////////////////
	@Test
	public void testClassForName() throws Exception {
		introspectorForClass = Introspector.forClass(TARGET_CLASS);
		assertEquals(TARGET_CLASS, introspectorForClass.getTargetClass());
	}

	@Test(expected = InvalidParameterException.class)
	public void testClassForNameComParametroClassNulo() throws Exception {
		final Class<?> classe = null;
		Introspector.forClass(classe);
	}

	@Test
	public void testClassForNameComParametroString() throws Exception {
		final String string = TARGET_CLASS.getName();
		Introspector.forClass(string);
	}

	@Test(expected = InvalidParameterException.class)
	public void testClassForNameComParametroStringNulo() throws Exception {
		final String string = null;
		Introspector.forClass(string);
	}

	// /////////////////////////////////////////////////////////////////////////
	// inObject
	// /////////////////////////////////////////////////////////////////////////
	@Test
	public void testInObject() throws Exception {
		Introspector.inObject(classeDominio);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInObjectComParametroNulo() throws Exception {
		Introspector.inObject(null);
	}

	// /////////////////////////////////////////////////////////////////////////
	// create
	// /////////////////////////////////////////////////////////////////////////
	@Test
	public void testCreateComClasseSemConstructorExplicito() throws Exception {
		introspectorForClass = Introspector.forClass(ClasseDominioSemConstructor.class).create();
		assertNotNull(introspectorForClass.getTargetInstance());
	}

	@Test
	public void testCreate() throws Exception {
		introspectorForClass.create();
		assertNotNull(introspectorForClass.getTargetInstance());

		introspectorForClass = Introspector.forClass(TARGET_CLASS);
		introspectorForClass.create(10);
		assertNotNull(introspectorForClass.getTargetInstance());
		assertEquals(((ClasseDominio) introspectorForClass.getTargetInstance()).getAtributoPrivadoInteiro().intValue(), 10);

		introspectorForClass = Introspector.forClass(TARGET_CLASS);
		introspectorForClass.create(15, "String_ok");
		assertNotNull(introspectorForClass.getTargetInstance());
		assertEquals(((ClasseDominio) introspectorForClass.getTargetInstance()).getAtributoPrivadoInteiro().intValue(), 15);
		assertEquals(((ClasseDominio) introspectorForClass.getTargetInstance()).getAtributoPrivadoString(), "String_ok");
	}

	@Test(expected = ConstructorNotExistsException.class)
	public void testCreateParaConstructorNaoExistente() throws Exception {
		introspectorForClass.create(true, "String_erro1");
		introspectorForClass.create();
	}

	// /////////////////////////////////////////////////////////////////////////
	// invokeField
	// /////////////////////////////////////////////////////////////////////////
	@Test
	public void testeInvokeFieldQueExisteNaClassePai() throws Exception {
		introspectorInObject = Introspector.inObject(classeDominioFilha);		
		Boolean valorBooleano = introspectorInObject.field("comecaPriva").directAccess().invoke();
		
		assertEquals(valorBooleano, Boolean.FALSE);	
	}
	
	
	@Test
	public void testInvokeMemberSemParametroParaField() throws Exception {
		final String valorTeste = "Valor para o teste";
		final Field field = TARGET_CLASS.getDeclaredField("atributoPrivadoString");

		introspectorForClass.field(field).invoke(valorTeste);
		final Object invokeValue = introspectorForClass.member(field).invoke();
		assertEquals(invokeValue, valorTeste);

		introspectorInObject.field(field).invoke(valorTeste);
		final Object invokeValue2 = introspectorForClass.member(field).invoke();
		assertEquals(invokeValue2, valorTeste);
	}

	@Test
	public void testInvokeMemberComParametroParaField() throws Exception {
		final String valorTeste = "200180";
		final Field field = TARGET_CLASS.getDeclaredField("atributoIsolado");

		introspectorForClass.field(field).directAccess().accessPrivateMembers().invoke(valorTeste);
		final Object invokeValue = introspectorForClass.field(field).accessPrivateMembers().directAccess().invoke();
		assertEquals(invokeValue, valorTeste);

		introspectorInObject.field(field).accessPrivateMembers().directAccess().invoke(valorTeste);
		final Object invokeValue2 = introspectorForClass.field(field).accessPrivateMembers().directAccess().invoke();
		assertEquals(invokeValue2, valorTeste);
	}

	@Test
	public void testInvokeFieldUsandoApiReflection() throws Exception {
		final String valorTeste = "Valor para o teste";
		final Field field = TARGET_CLASS.getDeclaredField("atributoPrivadoString");

		introspectorForClass.field(field).invoke(valorTeste);
		final Object invokeValue = introspectorForClass.field(field).invoke();
		assertEquals(invokeValue, valorTeste);

		introspectorInObject.field(field).invoke(valorTeste);
		final Object invokeValue2 = introspectorForClass.field(field).invoke();
		assertEquals(invokeValue2, valorTeste);
	}

	@Test
	public void testInvokeFieldUsandoApiReflectionDiretoComAcesso() throws Exception {
		final String valorTeste = "200180";
		final Field field = TARGET_CLASS.getDeclaredField("atributoIsolado");

		introspectorForClass.field(field).directAccess().accessPrivateMembers().invoke(valorTeste);
		final Object invokeValue = introspectorForClass.field(field).accessPrivateMembers().directAccess().invoke();
		assertEquals(invokeValue, valorTeste);

		introspectorInObject.field(field).accessPrivateMembers().directAccess().invoke(valorTeste);
		final Object invokeValue2 = introspectorForClass.field(field).accessPrivateMembers().directAccess().invoke();
		assertEquals(invokeValue2, valorTeste);
	}

	@Test
	public void testInvokeFieldSemParametro() throws Exception {
		final Object invokeValue = introspectorForClass.field("atributoPrivadoInteiro").invoke();
		assertEquals(invokeValue, INTEIRO);

		final Object invokeValue2 = introspectorInObject.field("atributoPrivadoInteiro").invoke();
		assertEquals(invokeValue2, INTEIRO);
	}

	@Test
	public void testInvokeFieldComParametro() throws Exception {
		final Integer valorTeste = 200180;

		introspectorForClass.field("atributoPrivadoInteiro").invoke(valorTeste);
		final Object invokeValue = introspectorForClass.field("atributoPrivadoInteiro").invoke();
		assertEquals(invokeValue, valorTeste);

		introspectorInObject.field("atributoPrivadoInteiro").invoke(valorTeste);
		final Object invokeValue2 = introspectorForClass.field("atributoPrivadoInteiro").invoke();
		assertEquals(invokeValue2, valorTeste);
	}

	@Test(expected = FieldNotExistsException.class)
	public void testInvokeFieldInexistenteSemParametro() throws Exception {
		final Object invoke = introspectorForClass.field("atributoInexistente").invoke();
		assertEquals(invoke, INTEIRO);
	}

	@Test(expected = FieldNotExistsException.class)
	public void testInvokeFieldInexistenteComParametro() throws Exception {
		final Integer valorTeste = 200180;
		introspectorForClass.field("atributoInexistente").invoke(valorTeste);
	}

	@Test
	public void testInvokeGetFieldPrivadoDiretoComAcesso() throws Exception {
		introspectorForClass.field("atributoIsolado").directAccess().accessPrivateMembers().invoke();
		introspectorInObject.field("atributoIsolado").directAccess().accessPrivateMembers().invoke();
	}

	@Test
	public void testInvokeSetFieldPrivadoDiretoComAcesso() throws Exception {
		introspectorForClass.field("atributoIsolado").directAccess().accessPrivateMembers().invoke("Olá");
		introspectorInObject.field("atributoIsolado").directAccess().accessPrivateMembers().invoke("Olá");
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvokeSetFieldPrivadoDiretoComAcessoComMaisDeUmParametro() throws Exception {
		introspectorForClass.field("atributoIsolado").directAccess().accessPrivateMembers().invoke("Olá", "errado");
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvokeSetFieldPrivadoComMaisDeUmParametro() throws Exception {
		introspectorForClass.field("atributoIsolado").invoke("Olá", "errado");
	}

	@Test(expected = FieldPrivateException.class)
	public void testInvokeFieldPrivadoDiretoSemAcesso() throws Exception {
		introspectorForClass.field("atributoIsolado").directAccess().invoke();
	}

	@Test(expected = StaticFieldNotExistsException.class)
	public void testInvokeFieldParaClasseNaoInstanciada() throws Exception {
		Introspector.forClass(TARGET_CLASS).field("atributoIsolado").invoke(20);
	}

	@Test
	public void testInvokeFieldParaAtributoIntPrimitivo() {
		introspectorForClass.field("atributoPrivadoInt").directAccess().accessPrivateMembers().invoke(10);
		introspectorInObject.field("atributoPrivadoInt").directAccess().accessPrivateMembers().invoke(10);
	}

	@Test
	public void testGetField() throws Exception {
		Field actual = introspectorForClass.field("comecaPriva").get();
		Field expected = TARGET_CLASS.getDeclaredField("comecaPriva");

		assertEquals(expected, actual);
	}

	@Test(expected = InvalidParameterException.class)
	public void testGetFieldComParametros() throws Exception {
		introspectorForClass.field("comecaPriva").get(String.class);
	}

	// /////////////////////////////////////////////////////////////////////////
	// invokeMethod
	// /////////////////////////////////////////////////////////////////////////
	@Test
	public void testeInvokeMethodQueExisteNaClassePai() throws Exception {
		introspectorInObject = Introspector.inObject(classeDominioFilha);
		Object dobro = introspectorInObject.method("getDobro").invoke(INTEIRO);
		
		assertEquals(dobro, INTEIRO * 2);	
	}
	
	@Test
	public void testInvokeMemberSemParametroParaMethod() throws Exception {
		final Method method = TARGET_CLASS.getDeclaredMethod("getDobroAtributoPrivadoInteiro");
		final Object invoke = introspectorForClass.member(method).invoke();

		assertEquals(invoke, INTEIRO * 2);
	}

	@Test
	public void testInvokeMemberComParametroParaMethod() throws Exception {
		final Integer valorTeste = 200180;
		final Method method = TARGET_CLASS.getDeclaredMethod("getDobro", Integer.class);

		final Object invoke = introspectorForClass.member(method).invoke(valorTeste);
		assertEquals(invoke, valorTeste * 2);
	}

	@Test
	public void testInvokeMethodSemParametroUsandoApiReflection() throws Exception {
		final Method method = TARGET_CLASS.getDeclaredMethod("getDobroAtributoPrivadoInteiro");
		final Object invoke = introspectorForClass.method(method).invoke();

		assertEquals(invoke, INTEIRO * 2);
	}

	@Test
	public void testInvokeMethodComParametroUsandoApiReflection() throws Exception {
		final Integer valorTeste = 200180;
		final Method method = TARGET_CLASS.getDeclaredMethod("getDobro", Integer.class);

		final Object invoke = introspectorForClass.method(method).invoke(valorTeste);
		assertEquals(invoke, valorTeste * 2);
	}

	@Test
	public void testInvokeMethodSemParametros() throws Exception {
		final Object invoke = introspectorForClass.method("getDobroAtributoPrivadoInteiro").invoke();
		assertEquals(invoke, INTEIRO * 2);

		final Object invoke2 = introspectorInObject.method("getDobroAtributoPrivadoInteiro").invoke();
		assertEquals(invoke2, INTEIRO * 2);
	}

	@Test
	public void testInvokeMethodComParametros() throws Exception {
		final Integer valorTeste = 200180;

		final Object invoke = introspectorForClass.method("getDobro").invoke(valorTeste);
		assertEquals(invoke, valorTeste * 2);

		final Object invoke2 = introspectorInObject.method("getDobro").invoke(valorTeste);
		assertEquals(invoke2, valorTeste * 2);
	}

	@Test(expected = MethodNotExistsException.class)
	public void testInvokeMethodInexistenteSemParametros() throws Exception {
		introspectorForClass.method("metodoInexistente").invoke(true);
	}

	@Test(expected = MethodNotExistsException.class)
	public void testInvokeMethodInexistenteComParametros() throws Exception {
		introspectorForClass.method("metodoInexistente").invoke(200180);
	}

	@Test(expected = StaticMethodNotExistsException.class)
	public void testInvokeMethodParaClasseNaoInstanciada() throws Exception {
		Introspector.forClass(TARGET_CLASS).method("getDobroAtributoPrivadoInteiro").invoke(20);
	}

	@Test(expected = MethodPrivateException.class)
	public void testInvokeMethodPrivadoSemAcesso() throws Exception {
		introspectorForClass.method("metodoPrivado").invoke("testando");
	}

	@Test
	public void testInvokeMethodPrivadoComAcesso() throws Exception {
		introspectorForClass.method("metodoPrivado").accessPrivateMembers().invoke("testando");
		introspectorInObject.method("metodoPrivado").accessPrivateMembers().invoke("testando");
	}

	@Test
	public void testGetMethod() throws Exception {
		Method actual = introspectorForClass.method("getAtributoPrivadoString").get();
		Method expected = TARGET_CLASS.getDeclaredMethod("getAtributoPrivadoString");

		assertEquals(expected, actual);
	}

	@Test
	public void testGetMethodComParametros() throws Exception {
		// (final String p1, final Integer p2, final Boolean p3)
		Method actual = introspectorForClass.method("metodoComTresParametros").get(String.class, Integer.class, Boolean.class);
		Method expected = TARGET_CLASS.getDeclaredMethod("metodoComTresParametros", String.class, Integer.class, Boolean.class);

		assertEquals(expected, actual);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Checks
	// /////////////////////////////////////////////////////////////////////////
	@Test(expected = InvalidStateException.class)
	public void testSetarDirectAccesSemInstancia() throws Exception {
		introspectorForClass = Introspector.forClass(ClasseDominio.class);
		introspectorForClass.directAccess();
	}

	@Test
	public void testSetarAccessPrivateMembersSemInstancia() throws Exception {
		introspectorForClass = Introspector.forClass(ClasseDominio.class);
		introspectorForClass.accessPrivateMembers();
	}
	
}
