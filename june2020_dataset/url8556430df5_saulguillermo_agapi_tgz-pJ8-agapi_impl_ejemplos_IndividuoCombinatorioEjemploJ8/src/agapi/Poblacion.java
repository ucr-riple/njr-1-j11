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
 * Esta clase contiene variables que caracterizan a una población. Una población
 * es un conjunto de {@linkplain agapi.Individuo individuos} organizados en un
 * arreglo de tamaño igual a {@code tamanoPoblacion} más las siguientes cuatro
 * variables:
 * 
 * <blockquote><b>{@code media}</b> es la media de los valores dados por la
 * función de aptitud de los individuos de la población.</blockquote>
 * 
 * <blockquote><b>{@code desviacion}</b> es la desviación estándar de los
 * valores dados por la función de aptitud de los individuos de la
 * población.</blockquote>
 * 
 * <blockquote><b>{@code mejorIndividuo}</b> es el individuo con el mayor valor
 * dado por la función de aptitud de la población.</blockquote>
 * 
 * <blockquote><b>{@code peorIndividuo}</b> es el individuo con el menor valor
 * dado por la función de aptitud de la población.</blockquote>
 * 
 * @author Saul Gonzalez
 */
public class Poblacion {

	/**
	 * Especifica al constructor {@code Poblacion(int opcion)} crear un objeto
	 * con una población de individuos con cromosomas aleatorios.
	 */
	public static final int ALEATORIA = 1;

	private static int tamanoPoblacion;
	private Individuo[] individuos;
	private Individuo mejorIndividuo;
	private Individuo peorIndividuo;
	private double media;
	private double desviacion;

	/**
	 * Constructor por defecto.
	 */
	public Poblacion() {
	}

	/**
	 * Crea una población de tamaño
	 * {@link agapi.Configuracion#setTamanoPoblacion(int) tamanoPoblacion} según
	 * las opciones indicadas. Las opciones válidas son:
	 * 
	 * <blockquote><b>{@code ALEATORIA}</b> - crea un objeto con una población
	 * de individuos con cromosomas aleatorios.</blockquote>
	 * 
	 * @param opcion
	 *            puede ser {@code ALEATORIA}
	 * @exception IllegalArgumentException
	 *                si no se selecciona alguna de las opciones válidas.
	 */
	public Poblacion(int opcion) {
		switch (opcion) {
		case Poblacion.ALEATORIA:
			this.aleatoria();
			break;
		default:
			throw new IllegalArgumentException(
					"Constructor de Poblacion: Opcion invalida");
		}
	}

	/**
	 * Retorna un valor entero que representa el tamaño de la población. Es
	 * decir, devuelve el numero de individuos que existe en una población.
	 * 
	 * @return el tamaño de la población.
	 */
	public static int getTamanoPoblacion() {
		return tamanoPoblacion;
	}

	/**
	 * Establece el tamaño de la población. Es decir, establece el numero de
	 * individuos que existe en una población.
	 * 
	 * @param tamanoPoblacion
	 *            el tamaño de la población a utilizar.
	 * @throws IllegalArgumentException
	 *             si <tt>tamanoPoblacion</tt> es menor a 2.
	 */
	public static void setTamanoPoblacion(int tamanoPoblacion) {
		if (tamanoPoblacion > 1) {
			Poblacion.tamanoPoblacion = tamanoPoblacion;
		} else {
			throw new IllegalArgumentException(
					"tamanoPoblacion debe ser mayor a 1.");
		}
	}

	/**
	 * Retorna un arreglo con los individuos de la población. Los individuos
	 * deben ser <em>individuos específicos</em>, es decir, objetos de una clase
	 * que ha heredado de {@link agapi.Individuo Individuo} y ha implementado
	 * todos sus métodos.
	 * 
	 * @return un arreglo con los individuos de la población
	 */
	public Individuo[] getIndividuos() {
		return individuos;
	}

	/**
	 * Establece el arreglo de individuos de la población. Los individuos deben
	 * ser <em>individuos específicos</em>, es decir, objetos de una clase que
	 * ha heredado de {@link agapi.Individuo Individuo} y ha implementado todos
	 * sus métodos.
	 * 
	 * @param individuos
	 *            el arreglo de individuos a establecer
	 */
	protected void setIndividuos(Individuo[] individuos) {
		this.individuos = individuos;
	}

	/**
	 * Devuelve el mejor individuo de la población. Es decir, el individuo con
	 * el mayor valor de la función de aptitud entre todos los miembros de la
	 * población.
	 * 
	 * @return el mejor individuo de la población
	 */
	public Individuo getMejorIndividuo() {
		return mejorIndividuo;
	}

	/**
	 * Establece el mejor individuo de la población. Este método es utilizado
	 * por el método {@link agapi.Generador#generar(Generacion) generar} de la
	 * clase {@link agapi.Generador Generador} donde se determina el individuo
	 * con la función de aptitud más alta de los individuos de esta población.
	 * 
	 * @param mejorIndividuo
	 *            el mejor individuo de la población a establecer
	 */
	protected void setMejorIndividuo(Individuo mejorIndividuo) {
		this.mejorIndividuo = mejorIndividuo;
	}

	/**
	 * Devuelve el peor individuo de la población. Es decir, el individuo con el
	 * menor valor de la función de aptitud entre todos los miembros de la
	 * población.
	 * 
	 * @return el peor individuo de la población
	 */
	public Individuo getPeorIndividuo() {
		return peorIndividuo;
	}

	/**
	 * Establece el peor individuo de la población. Este método es utilizado por
	 * el método {@link agapi.Generador#generar(Generacion) generar} de la clase
	 * {@link agapi.Generador Generador} donde se determina el individuo con la
	 * función de aptitud más baja de los individuos de esta población.
	 * 
	 * @param peorIndividuo
	 *            el peor individuo de la población a establecer
	 */
	protected void setPeorIndividuo(Individuo peorIndividuo) {
		this.peorIndividuo = peorIndividuo;
	}

	/**
	 * Retorna la media de las aptitudes de los individuos. Esta media viene
	 * dada por la ecuación:
	 * 
	 * <blockquote><i>x&#x0305; = ( 1 / n ) &Sigma;x<sub>i</sub>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ;
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i = 1, ..., n</i></blockquote>
	 * 
	 * Donde <i>x&#x0305;</i> es la media, <i>x<sub>i</sub></i> el valor de la
	 * función de aptitud de un individuo <i>i</i> y <i>n</i> es el tamaño de la
	 * población.
	 * <p>
	 * Este valor es calculado en el método
	 * {@link agapi.Generador#generar(Generacion) generar} de la clase
	 * {@link agapi.Generador Generador} y asignado a cada individuo de cada
	 * población en cada generación.
	 * 
	 * @return la media de los valores de las funciones de aptitud de los
	 *         individuos de esta población
	 */
	public double getMedia() {
		return media;
	}

	/**
	 * Establece la media de las aptitudes de los individuos. Este método es
	 * utilizado por el método {@link agapi.Generador#generar(Generacion)
	 * generar} de la clase {@link agapi.Generador Generador} donde se calcula
	 * la media de los valores de las funciones objetivos de los individuos de
	 * esta población.
	 * 
	 * @param media
	 *            la media a establecer
	 */
	protected void setMedia(double media) {
		this.media = media;
	}

	/**
	 * Devuelve la desviación estándar de la población. Esta desviación viene
	 * dada al despejar <i>s</i> de la ecuación:
	 * 
	 * <blockquote><i>s&sup2; = &Sigma;( x<sub>i</sub> - x&#x0305;)&sup2; / n
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ;
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i = 1, ..., n</i></blockquote>
	 * 
	 * Donde <i>s</i> es la desviación estándar, <i>x&#x0305;</i> la media,
	 * <i>x<sub>i</sub></i></i> el valor de la función de aptitud de un
	 * individuo <i>i</i> y <i>n</i> es el tamaño de la población.
	 * <p>
	 * Este valor es calculado en el método
	 * {@link agapi.Generador#generar(Generacion) generar} de la clase
	 * {@link agapi.Generador Generador} y asignado a cada individuo de cada
	 * población en cada generación.
	 * 
	 * @return la desviación estándar de la población
	 */
	public double getDesviacion() {
		return desviacion;
	}

	/**
	 * Establece la desviación estándar de las aptitudes de los individuos. Este
	 * método es utilizado por el método
	 * {@link agapi.Generador#generar(Generacion) generar} de la clase
	 * {@link agapi.Generador Generador} donde se calcula la desviación estándar
	 * de los valores de las funciones objetivos de los individuos de esta
	 * población.
	 * 
	 * @param desviacion
	 *            la desviación estándar de la población a establecer
	 */
	protected void setDesviacion(double desviacion) {
		this.desviacion = desviacion;
	}

	/**
	 * Genera una población de individuos con cromosomas aleatorios. Para ello
	 * crea una arreglo de individuos utilizando el método
	 * {@link agapi.Individuo#fabricaIndividuo() fabricaIndividuo} de
	 * {@link agapi.Individuo} encargado de producir individuos
	 * <em>concretos</em>. Luego, por cada individuo, se hace un llamado al
	 * método {@link agapi.Individuo#aleatorio() aleatorio} de
	 * {@link agapi.Individuo Individuo} que genera cromosomas aleatorios y
	 * finalmente se calcula la media, desviación estándar, mejor y peor
	 * individuo utilizando {@code calcMejorPeorMediaDesviacion}.
	 */
	public void aleatoria() {

		individuos = new Individuo[tamanoPoblacion];
		for (int i = 0; i < tamanoPoblacion; i++) {
			individuos[i] = Individuo.fabricaIndividuo();
			individuos[i].aleatorio();
			individuos[i].setFA(individuos[i].calcFA());
		}
		calcMejorPeorMediaDesviacion();
	}

	/**
	 * Calcula la media, la desviación estándar, el mejor y peor individuo de la
	 * población.
	 * <p>
	 * La media viene dada por la ecuación:
	 * 
	 * <blockquote><i>x&#x0305; = ( 1 / n ) &Sigma;x<sub>i</sub>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ;
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i = 1, ..., n</i></blockquote>
	 * 
	 * Donde <i>x&#x0305;</i> es la media, <i>x<sub>i</sub></i> el valor de la
	 * función de aptitud de un individuo <i>i</i> y <i>n</i> es el tamaño de la
	 * población.
	 * <p>
	 * La desviación viene dada al despejar <i>s</i> de la ecuación:
	 * 
	 * <blockquote><i>s&sup2; = &Sigma;( x<sub>i</sub> - x&#x0305;)&sup2; / n
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ;
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i = 1, ..., n</i></blockquote>
	 * 
	 * Donde <i>s</i> es la desviación estándar de la población,
	 * <i>x&#x0305;</i> la media, <i>x<sub>i</sub></i> el valor de la función de
	 * aptitud de un individuo <i>i</i> y <i>n</i> es el tamaño de la población.
	 * <p>
	 * El mejor y el peor individuo corresponden a aquellos con la función de
	 * aptitud de mayor y menor valor, respectivamente.
	 * <p>
	 * Todos estos cálculos se realizan en un mismo método con la finalidad de
	 * optimizar el desempeño del algoritmo. Si se realizaran en métodos
	 * separados se debería hacer un recorrido de todo el arreglo de individuos
	 * por cada método, lo cual no es conveniente.
	 */
	public void calcMejorPeorMediaDesviacion() {
		mejorIndividuo = individuos[0];
		peorIndividuo = individuos[0];
		double sum = 0.0;
		double sum2 = 0.0;
		for (int i = 0; i < tamanoPoblacion; i++) {
			sum = sum + individuos[i].getFA();
			sum2 = sum2 + individuos[i].getFA() * individuos[i].getFA();
			if (individuos[i].getFA() >= mejorIndividuo.getFA()) {
				mejorIndividuo = individuos[i];
			}
			if (individuos[i].getFA() <= peorIndividuo.getFA()) {
				peorIndividuo = individuos[i];
			}
		}
		media = sum / tamanoPoblacion;
		desviacion = Math.pow((sum2 / tamanoPoblacion) - (media * media), 0.5);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tamanoPoblacion; i++) {
			if (i < 9) {
				sb.append("  ").append(i + 1).append(". ");
				sb.append(individuos[i]);
			} else {
				if (i < 99) {
					sb.append(" ").append(i + 1).append(". ");
					sb.append(individuos[i]);
				} else {
					sb.append(i + 1).append(". ");
					sb.append(individuos[i]);
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}