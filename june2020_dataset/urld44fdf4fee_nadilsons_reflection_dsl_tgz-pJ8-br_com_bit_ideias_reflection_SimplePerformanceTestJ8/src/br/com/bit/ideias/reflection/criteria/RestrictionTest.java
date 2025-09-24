package br.com.bit.ideias.reflection.criteria;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.bit.ideias.reflection.core.Introspector;
import br.com.bit.ideias.reflection.criteria.Criterion;
import br.com.bit.ideias.reflection.criteria.Restriction;
import br.com.bit.ideias.reflection.criteria.expression.ComplexExpression;
import br.com.bit.ideias.reflection.exceptions.NoResultException;
import br.com.bit.ideias.reflection.exceptions.TooManyResultException;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominio;
import br.com.bit.ideias.reflection.test.artefacts.ClasseDominioFilha;
import br.com.bit.ideias.reflection.test.artefacts.MyAnnotation;
import br.com.bit.ideias.reflection.test.artefacts.MyAnnotation2;
import br.com.bit.ideias.reflection.type.LikeType;
import br.com.bit.ideias.reflection.type.ModifierType;
import br.com.bit.ideias.reflection.type.TargetType;

/**
 * 
 * @author Nadilson Oliveira da Silva
 * @since 28/07/2009
 */
public class RestrictionTest {

	private final Introspector introspector = Introspector.forClass(ClasseDominio.class);

	private final Introspector introspectorClasseFilha = Introspector.forClass(ClasseDominioFilha.class);

	private Criterion criterion;
	private Criterion criterionClasseFilha;

	@Before
	public void prepare() {
		criterion = introspector.createCriterion();
		criterion.add(Restriction.targetType(TargetType.FIELD));

		criterionClasseFilha = introspectorClasseFilha.createCriterion();
		criterionClasseFilha.add(Restriction.targetType(TargetType.METHOD));
	}

	@Test
	public void testRestrictionEqComTargetTypeNaoEspecificado() throws Exception {
		criterion = introspector.createCriterion();
		criterion.add(Restriction.like("tributoPrivadoString", LikeType.END));
		final List<Member> result = criterion.list();

		Assert.assertEquals(3, result.size());
	}
	
	@Test
    public void testRestrictionTargetTypeConstructor() throws Exception {
        criterion = introspector.createCriterion();
        criterion.add(Restriction.targetType(TargetType.CONSTRUCTOR));
        final List<Member> result = criterion.list();

        Assert.assertEquals(3, result.size());
    }

	@Test
	public void testRestrictionEqDirectCriterion() throws Exception {
		final Field field = ClasseDominio.class.getDeclaredField("atributoPrivadoInt");
		final Criterion localCriterion = Introspector.createCriterion(ClasseDominio.class).add(Restriction.eq("atributoPrivadoInt"));
		localCriterion.add(Restriction.targetType(TargetType.FIELD));
		;
		final List<Field> result = localCriterion.list();

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(field, result.get(0));
	}

	@Test
	public void testRestrictionEq() throws Exception {
		final Field field = ClasseDominio.class.getDeclaredField("atributoPrivadoInt");
		criterion.add(Restriction.eq("atributoPrivadoInt"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(field, result.get(0));
	}

	@Test
	public void shouldReturnEitherWhenUsingDisjunction() throws Exception {
		final Field field = ClasseDominio.class.getDeclaredField("atributoPrivadoInt");
		criterion.add(Restriction.eq("atributoPrivadoInt"));
		final ComplexExpression disjunction = Restriction.disjunction(Restriction.eq("yzxabc"));
		disjunction.add(Restriction.eq("atributoPrivadoInt"));
		criterion.add(disjunction);

		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(field, result.get(0));
	}

	@Test
	public void testRestrictionEqEncadeado() throws Exception {
		criterion.add(Restriction.eq("atributoPrivadoInt"));
		criterion.add(Restriction.eq("isAlive"));
		final List<Field> result = criterion.list();

		Assert.assertTrue(result.isEmpty());
	}

	@Test
	public void testRestrictionNe() throws Exception {
		criterion.add(Restriction.ne("atributoPrivadoInt"));
		criterion.add(Restriction.ne("constante"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(7, result.size());
	}

	@Test
	public void testRestrictionNeEncadead() throws Exception {
		criterion.add(Restriction.ne("atributoPrivadoInt"));
		criterion.add(Restriction.ne("isAlive"));
		criterion.add(Restriction.ne("Privative"));
		criterion.add(Restriction.ne("constante"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testRestrictionLike() throws Exception {
		criterion.add(Restriction.like("atributo"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(4, result.size());
	}

	@Test
	public void testRestrictionLikeEncadeado() throws Exception {
		criterion.add(Restriction.like("atributo"));
		criterion.add(Restriction.ne("atributoPrivadoInt"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(3, result.size());
	}

	@Test
	public void testRestrictionLikeComLikeTypeStart() throws Exception {
		criterion.add(Restriction.like("atributo", LikeType.START));
		final List<Field> result = criterion.list();

		Assert.assertEquals(4, result.size());
	}

	@Test
	public void testRestrictionLikeComLikeTypeEnd() throws Exception {
		criterion.add(Restriction.like("ive", LikeType.END));
		final List<Field> result = criterion.list();

		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testRestrictionLikeComLikeTypeAnywhere() throws Exception {
		criterion.add(Restriction.like("Priva", LikeType.ANYWHERE));
		final List<Field> result = criterion.list();

		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testRestrictionRegex() throws Exception {
		criterion.add(Restriction.regex("comeca[P|p]riva"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testRestrictionIn() throws Exception {
		criterion.add(Restriction.in("atributoPrivadoInt", "comecaPriva"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testRestrictionAnnotatedWith() throws Exception {
		criterion.add(Restriction.annotatedWith(MyAnnotation.class));
		final List<Field> result = criterion.list();

		Assert.assertEquals(3, result.size());
	}

	@Test
	public void testRestrictionAnnotatedWithComDoisAdd() throws Exception {
		criterion.add(Restriction.annotatedWith(MyAnnotation.class));
		criterion.add(Restriction.annotatedWith(MyAnnotation2.class));
		final List<Field> result = criterion.list();

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testRestrictionShowOnlyPublicTrue() throws Exception {
		criterion.add(Restriction.regex("comeca[P|p]riva"));
		criterion.add(Restriction.withModifiers(ModifierType.PUBLIC));
		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
	}

	@Test(expected = TooManyResultException.class)
	public void testRestrictionUniqueShouldThrowAnExceptionIfThereAreMoreThanOneResult() throws Exception {
		criterion.add(Restriction.annotatedWith(MyAnnotation.class));
		criterion.uniqueResult();
	}

	@Test(expected = NoResultException.class)
	public void testRestrictionUniqueShouldThrowAnExceptionIfThereAreNoResults() throws Exception {
		criterion.add(Restriction.eq("xyzu"));
		criterion.uniqueResult();
	}

	@Test
	public void testRestrictionUniqueShouldReturnOnlyOneMember() throws Exception {
		criterion.add(Restriction.regex("comeca[P|p]riva")).add(Restriction.withModifiers(ModifierType.PUBLIC));
		final Field fieldActual = criterion.uniqueResult();

		final Field fieldExpected = ClasseDominio.class.getDeclaredField("comecaPriva");
		Assert.assertEquals(fieldExpected, fieldActual);
	}

	@Test
	public void testRestrictionShowOnlyPublicFalse() throws Exception {
		criterion.add(Restriction.regex("comeca[P|p]riva"));
		criterion.add(Restriction.withModifiers(ModifierType.PRIVATE, ModifierType.PROTECTED));
		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testRestrictionShowOnlyPublicTrueComAtributosNaClassePai() throws Exception {
		criterion = introspectorClasseFilha.createCriterion();
		criterion.add(Restriction.like("atributo", LikeType.ANYWHERE));
		criterion.add(Restriction.withModifiers(ModifierType.PRIVATE, ModifierType.PROTECTED));
		final List<Field> result = criterion.list();

		Assert.assertEquals(5, result.size());
	}

	@Test
	public void testRestrictionEqComPropriedadeNaClassePai() throws Exception {
		criterion = introspectorClasseFilha.createCriterion();
		criterion.add(Restriction.regex("comeca[P|p]riva"));
		final List<Field> result = criterion.list();

		Assert.assertEquals(2, result.size());
	}

	@Test
	public void testRestrictionAnnotatedWithNaClassePai() throws Exception {
		criterion.add(Restriction.annotatedWith(MyAnnotation.class));
		final List<Field> result = criterion.list();

		Assert.assertEquals(3, result.size());
	}

	@Test
	public void testTypeEq() {
		criterion.add(Restriction.fieldClassEq(String.class));
		final List<Field> result = criterion.list();

		Assert.assertEquals(2, result.size());

	}

	@Test
	public void testMethodReturnClass() {
		criterion.add(Restriction.methodReturnClassEq(Integer.class));
		final List<Field> result = criterion.list();

		Assert.assertTrue(result.isEmpty());

	}

	@Test
	public void testWithParams() {
		criterion.add(Restriction.methodWithParams(String.class));
		final List<Field> result = criterion.list();

		Assert.assertTrue(result.isEmpty());

	}

	@Test
	public void testWithParamsComMaisDeUmParametro() {
		criterion.add(Restriction.methodWithParams(String.class, Integer.class, boolean.class));
		final List<Field> result = criterion.list();

		Assert.assertTrue(result.isEmpty());

	}

	@Test
	public void testCriteriaProcuraPorFieldsConstantes() {
		criterion.add(Restriction.withModifiers(ModifierType.FINAL, ModifierType.STATIC));
		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testCriteriaProcuraPorFieldsEstaticos() {
		criterion.add(Restriction.withModifiers(ModifierType.STATIC));
		final List<Field> result = criterion.list();

		Assert.assertEquals(1, result.size());
	}

	@Test
	public void testCriteriaProcuraPorFieldsSynchronized() {
		criterion.add(Restriction.withModifiers(ModifierType.SYNCHRONIZED));
		final List<Field> result = criterion.list();

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testCriterionInCollections() {
		Assert.fail();
	}
	
}
