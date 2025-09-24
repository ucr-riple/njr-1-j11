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
 * Es la clave del framework. Esta clase se debe heredar para diseñar un
 * individuo de forma especifica. Contiene cuatro métodos que deben ser
 * implementados y que definen la estructura del problema a resolver:
 * 
 * <blockquote> 1. {@code calcFA} define la función de aptitud a utilizar. Es
 * <b>muy importante</b> que dicha función sea <b>positiva</b> y de
 * <b>maximizar</b>, para lo cual se deben utilizar los artificios matemáticos
 * necesarios.</blockquote>
 * 
 * <blockquote> 2. {@code aleatorio} transforma el cromosoma de este individuo
 * en un cromosoma aleatorio. Es utilizado por la clase {@link agapi.Poblacion}
 * para generar una población aleatoria en el inicio de una ejecución.
 * </blockquote>
 * 
 * <blockquote> 3. {@code cruce} ejecuta un cruce entre este individuo (padre) y
 * otro (madre). El cruce debe ser algún tipo de combinación entre el cromosoma
 * del padre y el de la madre. </blockquote>
 * 
 * <blockquote> 4. {@code mutacion} modifica la estructura del cromosoma de este
 * individuo. Esta modificación generalmente es pequeña, por ejemplo, cambiar el
 * valor de un único gen. </blockquote>
 * 
 * @author Saul Gonzalez
 */
public abstract class Individuo implements Comparable<Individuo>{

	private static Individuo tipoIndividuo;
	private static int tamanoCromosoma;
	private double fA;

	/**
	 * Devuelve el tipo de individuo del algoritmo. {@code tipoIndividuo} es una
	 * variable estática que representa a un objeto que hereda de la clase
	 * {@link agapi.Individuo}.
	 * 
	 * @return el tipo de individuo del algoritmo
	 */
	public static Individuo getTipoIndividuo() {
		return tipoIndividuo;
	}

	/**
	 * Establece el tipo de individuo especifico a utilizar en el problema. Este
	 * método es llamado por
	 * {@link agapi.Configuracion#setTipoIndividuo(Individuo) setTipoIndividuo}
	 * de {@link agapi.Configuracion} para la establecer el tipo de individuo
	 * concreto a utilizar.
	 * 
	 * @param tipoIndividuo
	 *            el tipo de individuo a utilizar en el problema
	 * @throws NullPointerException
	 *             si {@code tipo} es {@code null}
	 */
	public static void setTipoIndividuo(Individuo tipoIndividuo) {
		if (tipoIndividuo != null) {
			Individuo.tipoIndividuo = tipoIndividuo;
		} else {
			throw new NullPointerException("tipoIndividuo no debe ser null");
		}
	}

	/**
	 * Devuelve un objeto del tipo correspondiente a {@code tipoIndividuo}. Esto
	 * permite utilizar individuos concretos dentro de un contexto abstracto,
	 * donde solo haya variables de tipo {@link agapi.Individuo} (clase
	 * abstracta).
	 * 
	 * @return un individuo concreto (objeto) establecido en
	 *         {@code tipoIndividuo}
	 */
	public static Individuo fabricaIndividuo() {
		Individuo individuoConcreto = null;
		Class<? extends Individuo> claseIndividuo = tipoIndividuo.getClass();
		try {
			individuoConcreto = claseIndividuo.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return individuoConcreto;
	}

	/**
	 * 
	 * Devuelve el tamaño del cromosoma utilizado por este individuo.
	 * <p>
	 * El parámetro {@code tamanoCromosoma} debe utilizarse con cuidado ya que
	 * en ciertos casos el valor del tamaño del cromosoma esta íntimamente
	 * relacionado con la función de aptitud del problema, tal es el caso de los
	 * cromosomas binarios en donde, generalmente, se debe usar un tamaño fijo
	 * de cromosoma que no debe ser modificado, de lo contrario se debe
	 * modificar también la función de aptitud.
	 * 
	 * @return el tamaño del cromosoma del individuo
	 */
	public static int getTamanoCromosoma() {
		return tamanoCromosoma;
	}

	/**
	 * Establece el tamaño del cromosoma a utilizar por este individuo. Este
	 * método es llamado por {@link agapi.Configuracion#setTamanoCromosoma(int)
	 * setTamanoCromosoma} de {@link agapi.Configuracion} para la establecer el
	 * tamaño del cromosoma del individuo del algoritmo.
	 * <p>
	 * El parámetro {@code tamanoCromosoma} debe utilizarse con cuidado ya que
	 * en ciertos casos el valor del tamaño del cromosoma esta íntimamente
	 * relacionado con la función de aptitud del problema, tal es el caso de los
	 * cromosomas binarios en donde, generalmente, se debe usar un tamaño fijo
	 * de cromosoma que no debe ser modificado, de lo contrario se debe
	 * modificar también la función de aptitud.
	 * 
	 * @param tamanoCromosoma
	 *            el tamaño del cromosoma
	 * @throws IllegalArgumentException
	 *             si {@code tamanoCromosoma} es menor a 1
	 */
	public static void setTamanoCromosoma(int tamanoCromosoma) {
		if (tamanoCromosoma > 0) {
			Individuo.tamanoCromosoma = tamanoCromosoma;
		} else {
			throw new IllegalArgumentException(
					"tamanoCromosoma debe ser mayor a cero");
		}
	}

	/**
	 * Devuelve el valor de la función de aptitud del individuo.
	 * 
	 * @return el valor de la función de aptitud del individuo
	 */
	public double getFA() {
		return fA;
	}

	/**
	 * Establece el valor de la función de aptitud del individuo.
	 * 
	 * @param fA
	 *            la función de aptitud a utilizar
	 */
	public void setFA(double fA) {
		this.fA = fA;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Individuo in) {
		return Double.compare(fA, in.fA);
	}

	/**
	 * Calcula el valor de la función de aptitud . Dicho cálculo es especifico
	 * del problema a tratar y es una función cuya variable independiente es el
	 * cromosoma del individuo. Este cromosoma puede ser diseñado por el usuario
	 * o proveniente de alguna clase preconfigurada como
	 * {@link agapi.impl.IndividuoBinario} o
	 * {@link agapi.impl.IndividuoCombinatorio}. Es <b>muy importante</b> que
	 * dicha función sea <b>positiva</b> y de <b>maximizar</b>, para lo cual se
	 * deben utilizar los artificios matemáticos necesarios.
	 * 
	 * @return el valor de la función de aptitud calculado
	 */
	public abstract double calcFA();

	/**
	 * Transforma el cromosoma de un Individuo en un cromosoma aleatorio. Es
	 * utilizado por el método {@link agapi.Poblacion#aleatoria() aleatoria} de
	 * {@link agapi.Poblacion} cuando se debe generar una población aleatoria
	 * para el inicio de una {@linkplain agapi.Ejecucion ejecución}.
	 */
	public abstract void aleatorio();

	/**
	 * Ejecuta un cruce entre este individuo (padre) y otro (madre). El cruce
	 * debe ser algún tipo de combinación entre el cromosoma del padre y el de
	 * la madre.
	 * 
	 * @param madre
	 *            el individuo madre
	 * @return Un par individuos (hijos)
	 */
	public abstract Individuo[] cruce(Individuo madre);

	/**
	 * Modifica la estructura del cromosoma de este individuo. Esta modificación
	 * generalmente es pequeña, como cambiar el valor de un único gen.
	 */
	public abstract void mutacion();
}