package br.com.bit.ideias.reflection.test.artefacts;

/**
 * @author Nadilson Oliveira da Silva
 * @date 18/02/2009
 * 
 */
@SuppressWarnings("unused")
public class ClasseDominio {

	@MyAnnotation
	private Integer atributoPrivadoInteiro;

	@MyAnnotation
	private String atributoPrivadoString;

	@MyAnnotation
	private String atributoIsolado;

	private int atributoPrivadoInt;

	private boolean isAlive;

	private boolean Privative;

	public boolean comecaPriva;

	private boolean comecapriva;

	static final Object constante = null;

	public ClasseDominio() {
		_evitaFinal();
	}

	public ClasseDominio(final Integer atributoPrivadoInteiro) {
		this(atributoPrivadoInteiro, "NAO_SETADO");
	}

	public ClasseDominio(final Integer atributoPrivadoInteiro, final String atributoPrivadoString) {
		this();
		this.atributoPrivadoInteiro = atributoPrivadoInteiro;
		this.atributoPrivadoString = atributoPrivadoString;
	}

	@MyAnnotation
	public Integer getAtributoPrivadoInteiro() {
		return atributoPrivadoInteiro;
	}

	public void setAtributoPrivadoInteiro(final Integer atributoPrivadoInteiro) {
		this.atributoPrivadoInteiro = atributoPrivadoInteiro;
	}

	@MyAnnotation
	public String getAtributoPrivadoString() {
		return atributoPrivadoString;
	}

	public void setAtributoPrivadoString(final String atributoPrivadoString) {
		this.atributoPrivadoString = atributoPrivadoString;
	}

	public Integer getDobroAtributoPrivadoInteiro() {
		return getDobro(atributoPrivadoInteiro);
	}

	public Integer getDobro(final Integer value) {
		return value * 2;
	}

	private String metodoPrivado(final String nome) {
		return String.format("%s, %1$s", nome);
	}

	public void metodoQueVaiLancarException() {
		throw new RuntimeException("Excecao de teste");
	}

	public int getAtributoPrivadoInt() {
		return atributoPrivadoInt;
	}

	public void setAtributoPrivadoInt(final int atributoPrivadoInt) {
		this.atributoPrivadoInt = atributoPrivadoInt;
	}

	private void _evitaFinal() {
		this.atributoIsolado = "only";
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(final boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void metodoComTresParametros(final String p1, final Integer p2, final Boolean p3) {

	}

	public void metodoComTresParametrosPrimitivos(final String p1, final Integer p2, final boolean p3) {

	}
}
