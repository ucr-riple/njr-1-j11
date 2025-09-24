/* AGAPI - API para el desarrollo de Algoritmos Geneticos
 * Copyright (C) 2013 Saul Gonzalez
 * 
 * This library is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package agapi.impl;

import agapi.Generacion;
import agapi.Individuo;
import agapi.Poblacion;
import agapi.Selector;

/**
 * Este nombre describe a un grupo de esquemas de selección probabilísticos que
 * simulan una rueda de ruleta de casino con "rebanadas" en función de los
 * valores esperados de los individuos de la población. A este grupo, por
 * ejemplo, pertenecen la técnica de {@linkplain agapi.impl.SelectorRuleta la
 * ruleta} y el {@linkplain agapi.impl.SelectorSUS muestreo estocástico
 * universal}.
 * <p>
 * Un constructor de esta clase acepta como parámetro un tipo de función del
 * valor esperado diferente a la típica probabilidad proporcional de la función
 * de aptitud utilizado por Goldberg (1989). Se puede diseñar una función ad-hoc
 * o utilizar alguna de las que brinda esta librería como
 * {@link agapi.impl.FuncionRanking} o {@link agapi.impl.FuncionTanese}.
 * 
 * @author sebukan
 * 
 */
public abstract class SelectorEstocastico implements Selector {

	private Funcion funcValEsp;// funcion de valor esperado
	private Individuo[] poolDeSeleccionados;// individuos elegidos
	private int contador;// contador de individuos

	/**
	 * Constructor por defecto utiliza la función clásica (Goldberg, 1989) de
	 * valor esperado basada únicamente en la aptitud de los individuos. Es
	 * equivalente a {@code SelectorEstocastico(new FuncionClasico())}.
	 */
	public SelectorEstocastico() {
		this(new FuncionClasico());
	}

	/**
	 * Constructor que recibe una función de valor esperado especifica.
	 * 
	 * @param funcValEsp
	 *            la funcion de valor esperado
	 */
	public SelectorEstocastico(Funcion funcValEsp) {
		this.funcValEsp = funcValEsp;
	}

	/**
	 * Devuelve la función de valor esperado utilizada por este selector.
	 * 
	 * @return la función de valor esperado utilizada por este selector
	 */
	public Funcion getFuncValEsp() {
		return funcValEsp;
	}

	/**
	 * Selecciona una cantidad específica de individuos de la población según la
	 * técnica estocástica que herede de esta clase al implementar el método
	 * {@code generaValEspAcum}. Los individuos provienen de un pool (arreglo)
	 * con todos los seleccionados de esta población. Dicho pool es generado por
	 * el método {@code prepararSeleción}.
	 * <p>
	 * Este método es llamado por {@link agapi.Generador#generar(Generacion)
	 * generar} de {@link agapi.Generador} cada vez que se deseen seleccionar
	 * individuos con oportunidades de cruce.
	 * 
	 * @param poblacion
	 *            la población de donde serán seleccionados los individuos
	 * @param cantidadSeleccionados
	 *            la cantidad de individuos a seleccionar
	 * @return un arreglo de tamaño {@code cantidadSeleccionados} con los
	 *         individuos seleccionados
	 * 
	 * @see agapi.Selector#seleccion(agapi.Poblacion, int)
	 */
	@Override
	public Individuo[] seleccion(Poblacion poblacion, int cantidadSeleccionados) {
		Individuo[] seleccionados = new Individuo[cantidadSeleccionados];
		int i = contador;
		int j = 0;
		while (j < cantidadSeleccionados) {// toma seleccionados del pool
			seleccionados[j] = poolDeSeleccionados[i];
			i++;
			j++;
		}
		// contador mantiene relacion entre seleccionados y poolDeSeleccionados
		contador = contador + cantidadSeleccionados;
		return seleccionados;
	}

	/**
	 * Este método genera un pool de individuos seleccionados según la técnica
	 * estocástica que herede de esta clase al implementar el método
	 * {@code generaValEspAcum}.
	 * <p>
	 * Este método es llamado por {@link agapi.Ejecucion#ejecutar() ejecutar} de
	 * la clase {@link agapi.Ejecucion} una vez por generación, dado que en cada
	 * generación se debe preparar los datos que utilizará la siguiente
	 * generación.
	 * 
	 * @param poblacion
	 *            la población a la que se le aplicará la técnica estocástica
	 * 
	 * @see agapi.Selector#prepararSeleccion(agapi.Poblacion)
	 */
	@Override
	public void prepararSeleccion(Poblacion poblacion) {
		contador = 0;
		double[] valEspAcum = generaValEspAcum(poblacion);
		poolDeSeleccionados = generaPool(poblacion, valEspAcum);
	}

	// devuelve los valores esperados acumulados de cada individuo
	private double[] generaValEspAcum(Poblacion poblacion) {
		int m = Poblacion.getTamanoPoblacion();
		double[] valEspAcum = new double[m];
		double[] valEsp = funcValEsp.calcValEsp(poblacion);
		valEspAcum = new double[m];
		valEspAcum[0] = valEsp[0];
		for (int i = 1; i < m; i++) {
			valEspAcum[i] = valEspAcum[i - 1] + valEsp[i];
		}
		return valEspAcum;
	}

	/**
	 * Devuelve un arreglo de tipo {@code Individuo} que representa el pool de
	 * los individuos seleccionados. La forma en que se seleccionen los
	 * individuos en este método define la técnica estocástica de la clase que
	 * herede de esta clase. Por ejemplo, la clase que implementa este método
	 * usando en criterio de selección <em>de la ruleta</em> se denomina
	 * {@link agapi.impl.SelectorRuleta}.
	 * 
	 * @param poblacion
	 *            la población de donde se seleccionarán los individuos.
	 * @param valEspAcum
	 *            el arreglo con los valores esperados acumulados calculados en
	 *            base a la función del valor esperado pasada como parámetro en
	 *            el constructor de esta clase
	 * @return el pool de individuos seleccionados
	 */
	public abstract Individuo[] generaPool(Poblacion poblacion,
			double[] valEspAcum);
}