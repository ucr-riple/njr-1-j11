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

package agapi;

/**
 * Es una <em>población</em> más un conjunto de operadores genéticos y otras
 * variables. Una generación esta formada por:
 * 
 * <blockquote>Una {@linkplain agapi.Poblacion población}, a la que se aplicaran
 * los operadores genéticos.</blockquote>
 * 
 * <blockquote>Un {@linkplain agapi.Selector selector}, encargado de determinar
 * que individuos (de la población) tendrán oportunidad de
 * cruzarse.</blockquote>
 * 
 * <blockquote>Un {@linkplain agapi.SelectorPostCruce selector post cruce}, que
 * determina que individuos pasan a la siguiente generación.</blockquote>
 * 
 * <blockquote>La <em>probabilidad de cruce</em>, que determina de forma
 * aleatoria que pareja de individuos se va a cruzar.</blockquote>
 * 
 * <blockquote>La <em>probabilidad de mutación</em>, que determina de forma
 * aleatoria que descendientes van a mutar sus cromosomas.</blockquote>
 * 
 * <blockquote>Un booelan, indica si se va a utilizar <em>elitismo</em>.
 * </blockquote>
 * 
 * <blockquote>Un {@linkplain agapi.Generador generador}, que acciona el proceso
 * evolutivo utilizando todas las variables anteriores.</blockquote>
 * 
 * Cabe destacar que la diferencia entre una generación y otra es la
 * {@code poblacion} y el {@code generador} que tengan asociados, el resto de
 * las características están dadas por variables estáticas que comparten todos
 * 
 * @author Saul Gonzalez
 */
public class Generacion {

	private Poblacion poblacion;
	private static Selector selector;
	private static SelectorPostCruce selectorPostCruce;
	private static double probabilidadCruce;
	private static double probabilidadMutacion;
	private static boolean elitismo;
	private Generador generador;

	/**
     *
     */
	public Generacion() {
	}

	/**
	 * Crea una generación con una {@linkplain agapi.Poblacion población}
	 * 
	 * @param poblacion
	 *            población a asociar.
	 */
	public Generacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve la {@linkplain agapi.Poblacion población} asociada a esta
	 * generación. Esta población es única para esta generación.
	 * 
	 * @return la población de esta generación
	 */
	public Poblacion getPoblacion() {
		return poblacion;
	}

	/**
	 * Establece la {@linkplain agapi.Poblacion población} asociada a esta
	 * generación.
	 * 
	 * @param poblacion
	 *            la población con la que se va a asociar esta generación.
	 */
	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el {@linkplain agapi.Selector selector} utilizado por todas las
	 * generaciones.
	 * 
	 * @return el selector utilizado por todas las generaciones.
	 */
	public static Selector getSelector() {
		return selector;
	}

	/**
	 * Establece el {@linkplain agapi.Selector selector} a utilizar por todas
	 * las generaciones.
	 * 
	 * @param selector
	 *            el selector que será utilizado por todas las generaciones.
	 * @throws NullPointerException
	 *             si {@code selector} es {@code null}.
	 */
	public static void setSelector(Selector selector) {
		if (selector != null) {
			Generacion.selector = selector;
		} else {
			throw new NullPointerException("selector no debe ser null");
		}
	}

	/**
	 * Retorna el {@linkplain agapi.SelectorPostCruce selector post cruce}
	 * utilizado por todas las generaciones.
	 * 
	 * @return el selector post cruce utilizado por todas las generaciones.
	 */
	public static SelectorPostCruce getSelectorPostCruce() {
		return selectorPostCruce;
	}

	/**
	 * Establece el {@linkplain agapi.SelectorPostCruce selector post cruce} a
	 * utilizar por todas las generaciones.
	 * 
	 * @param selectorPostCruce
	 *            el selector post cruce que será utilizado por todas las
	 *            generaciones.
	 * @throws NullPointerException
	 *             si {@code selectorPostCruce} es {@code null}.
	 */
	public static void setSelectorPostCruce(SelectorPostCruce selectorPostCruce) {
		if (selectorPostCruce != null) {
			Generacion.selectorPostCruce = selectorPostCruce;
		} else {
			throw new NullPointerException("selectorPostCruce no debe ser null");
		}
	}

	/**
	 * Retorna el valor de la probabilidad de cruce utilizado en todas las
	 * generaciones. Esta probabilidad determina de forma aleatoria que pareja
	 * de individuos se va a cruzar.
	 * 
	 * @return la probabilidad de cruce utilizada por todas las generaciones.
	 */
	public static double getProbabilidadCruce() {
		return probabilidadCruce;
	}

	/**
	 * Establece el valor de la probabilidad de cruce utilizado en todas las
	 * generaciones. Esta probabilidad determina de forma aleatoria que pareja
	 * de individuos se va a cruzar.
	 * 
	 * @param probabilidadCruce
	 *            la probabilidad de cruce a utilizar por todas las
	 *            generaciones.
	 * @throws IllegalArgumentException
	 *             si {@code probabilidadCruce} sale del rango (0,1).
	 */
	public static void setProbabilidadCruce(double probabilidadCruce) {
		if (probabilidadCruce >= 0.0 && probabilidadCruce <= 1.0) {
			Generacion.probabilidadCruce = probabilidadCruce;
		} else {
			throw new IllegalArgumentException(
					"probabilidadCruce debe estar dentro del rango (0,1)");
		}
	}

	/**
	 * Retorna el valor de la probabilidad de mutación utilizado en todas las
	 * generaciones. Esta probabilidad determina de forma aleatoria que
	 * descendientes van a mutar sus cromosomas
	 * 
	 * @return la probabilidad de mutación utilizada por todas la generaciones.
	 */
	public static double getProbabilidadMutacion() {
		return probabilidadMutacion;
	}

	/**
	 * Establece el valor de la probabilidad de mutación utilizado en todas las
	 * generaciones. Esta probabilidad determina de forma aleatoria que
	 * descendientes van a mutar sus cromosomas
	 * 
	 * @param probabilidadMutacion
	 *            la probabilidad de mutación a utilizar por todas las
	 *            generaciones.
	 * @throws IllegalArgumentException
	 *             si {@code probabilidadMutacion} sale del rango (0,1).
	 */
	public static void setProbabilidadMutacion(double probabilidadMutacion) {
		if (probabilidadMutacion >= 0.0 && probabilidadMutacion <= 1.0) {
			Generacion.probabilidadMutacion = probabilidadMutacion;
		} else {
			throw new IllegalArgumentException(
					"probabilidadMutacion debe estar dentro del rango (0,1)");
		}
	}

	/**
	 * Devuelve un {@code boolean} que indica si se va a utilizar
	 * <em>elitismo</em>. El elitismo es un mecanismo utilizado en algunos
	 * algoritmos genéticos para asegurar que los individuos más aptos de una
	 * población pasen a la siguiente generación sin ser alterados por ningun
	 * operador genético.
	 * <p>
	 * El elitismo asegura que la aptitud máxima de la población nunca se
	 * reducira de una generación a la siguiente. Sin embargo, no necesariamente
	 * mejora la posibilidad de localizar el optimo global de una función.
	 * 
	 * @return un boolean, true si se utiliza elitismo.
	 */
	public static boolean isElitismo() {
		return elitismo;
	}

	/**
	 * Establece si aplica o no el <em>elitismo</em>. Se llama <em>elitismo</em>
	 * al mecanismo utilizado en algunos algoritmos genéticos para asegurar que
	 * los individuos más aptos de una población pasen a la siguiente generación
	 * sin ser alterados por ningun operador genético.
	 * <p>
	 * El elitismo asegura que la aptitud máxima de la población nunca se
	 * reducirá de una generación a la siguiente. Sin embargo, no necesariamente
	 * mejora la posibilidad de localizar el optimo global de una función.
	 * 
	 * @param elitismo
	 *            un boolean, true si se desea utilizar elitismo.
	 */
	public static void setElitismo(boolean elitismo) {
		Generacion.elitismo = elitismo;
	}

	/**
	 * Inicia la evolución de una generación a otra por medio de la acción de
	 * los operadores genéticos. Este método crea un objeto de tipo
	 * {@link agapi.Generador Generador} y hace un llamado al método auxiliar
	 * {@link agapi.Generador#generar(Generacion) generar} donde son aplicados
	 * los operadores genéticos a la población haciendo uso de las
	 * probabilidades de cruce ({@code probabilidadCruce}) y mutación
	 * {@code probabilidadMutacion}.
	 * 
	 * @return una nueva generación producto de esta.
	 */
	public Generacion generar() {
		generador = new Generador();
		Generacion nuevaGeneracion = generador.generar(this);
		return nuevaGeneracion;
	}

	/**
	 * Devuelve una cadena de caracteres con datos de la generación. Estos datos
	 * consisten en variables de la población asociada a esta generación:
	 * 
	 * <blockquote> Media: es la media muestral de la función de aptitud de los
	 * individuos de la población.</blockquote>
	 * 
	 * <blockquote> Desviación Estándar: es la desviación estándar de la función
	 * de aptitud de los individuos de la población.</blockquote>
	 * 
	 * <blockquote> Mejor Individuo: es el individuo con la mayor función de
	 * aptitud de la población.</blockquote>
	 * 
	 * <blockquote> Peor Individuo: es el individuo con la menor función de
	 * aptitud de la población.</blockquote>
	 * 
	 * @return una cadena de caracteres con datos de la generación.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" Mejor: ").append(poblacion.getMejorIndividuo());
		sb.append("\n");
		sb.append(" Peor: ").append(poblacion.getPeorIndividuo());
		sb.append("\n");
		sb.append(" Media: ");
		sb.append(String.format("%, .2f", poblacion.getMedia()));
		sb.append("\n");
		sb.append(" Desviacion: ");
		sb.append(String.format("%, .2f", poblacion.getDesviacion()));
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * Devuelve una cadena de caracteres con datos de la generación incluyendo a
	 * los individuos de la población. Devuelve los mismos datos que
	 * {@code toString} y además muestra a todos los individuos de la población
	 * asociada a esta generación.
	 * 
	 * @return una cadena de caracteres con datos de la generación incluyendo a
	 *         los individuos de la población.
	 */
	protected String toStringConPoblaciones() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.toString());
		sb.append(poblacion);
		return sb.toString();
	}
}