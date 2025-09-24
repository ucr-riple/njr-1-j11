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
 * Es una corrida independiente de un algoritmo genético. Es un proceso
 * evolutivo completo, consiste en un arreglo de objetos de tipo
 * {@link agapi.Generacion Generacion}. En esta clase se crea una
 * <em>generación i</em> a partir de una <em>generación i-1</em>, esto se
 * realiza tantas veces como se indique en el método
 * {@code setNumeroGeneraciones}.
 * 
 * @author Saul Gonzalez
 */
public class Ejecucion {

	private static int numeroGeneraciones;
	private Generacion[] generaciones;
	private long tiempoEjecucion;
	private boolean banderaEjecutar;

	/**
	 * Devuelve el numero de {@linkplain agapi.Generacion generaciones} por
	 * ejecución.
	 * 
	 * @return el numero de generaciones por ejecución.
	 */
	public static int getNumeroGeneraciones() {
		return numeroGeneraciones;
	}

	/**
	 * Establece el numero de {@linkplain agapi.Generacion generaciones} por
	 * ejecución. Una <em>generación</em> consiste en una <em>población</em> mas
	 * un conjunto de operadores genéticos y otras variables que son aplicadas a
	 * dicha población para crear una nueva generación.
	 * <p>
	 * Una generación produce otra generación y esta a la siguiente y así
	 * sucesivamente hasta alcanzar una cantidad igual al valor establecido en
	 * este método. Una vez se llega a la ultima generación se detiene la
	 * ejecución y se pasa a la siguiente si lo permite el parámetro
	 * {@code numeroEjecuciones}.
	 * 
	 * @param numeroGeneraciones
	 *            el numero de generaciones a establecer
	 * @throws IllegalArgumentException
	 *             si {@code numeroGeneraciones} es menor a 1.
	 */
	public static void setNumeroGeneraciones(int numeroGeneraciones) {
		if (numeroGeneraciones > 0) {
			Ejecucion.numeroGeneraciones = numeroGeneraciones;
		} else {
			throw new IllegalArgumentException(
					"numeroGeneraciones debe ser mayor a cero");
		}
	}

	/**
	 * Devuelve las {@linkplain agapi.Generacion generaciones} de una ejecución.
	 * A partir de estas generaciones se pueden obtener otros datos de interés
	 * como la población asociada a cada generación, los individuos, media y
	 * desviación de las funciones de aptitud.
	 * 
	 * @return la generaciones de la ejecución.
	 */
	public Generacion[] getGeneraciones() {
		return generaciones;
	}

	/**
	 * Devuelve el tiempo que tarda la ejecución en completarse. Es decir,
	 * devuelve el tiempo que tardan en ejecutarse todas las
	 * <em>generaciones</em> del algoritmo genético. Esta método debe llamarse
	 * después del método {@code ejecutar} de lo contrario se genera un
	 * excepción {@code IllegalStateException}.
	 * 
	 * @return el tiempo de la ejecución
	 * 
	 * @throws IllegalStateException
	 *             si no se llama previamente al método {@code ejecutar}.
	 */
	public double getTiempoEjecucion() {
		if (banderaEjecutar) {
			return tiempoEjecucion;
		}
		throw new IllegalStateException(
				"Aun no se ha ejecutado el método procesar");
	}

	/**
	 * Inicia la corrida de un algoritmo genético. Este método crea una
	 * población inicial de individuos con cromosomas aleatorios utilizando el
	 * constructor {@link agapi.Poblacion#Poblacion(int) Poblacion(int)} con la
	 * opción {@link agapi.Poblacion#ALEATORIA}. Esta población inicial es
	 * introducida en el constructor de la primera generación del arreglo y a
	 * partir de este momento, comienza un ciclo en el que una nueva generación
	 * (i) es creada a partir de una anterior (i-1) por medio del método
	 * {@link agapi.Generacion#generar() generar} de {@link agapi.Generacion
	 * Generacion} hasta alcanzar una cantidad de generaciones igual a
	 * {@code numeroGeneraciones}.
	 * 
	 * @see agapi.Generacion#generar()
	 */
	public void ejecutar() {
		long tiempoInicial = System.nanoTime();
		generaciones = new Generacion[numeroGeneraciones];
		Poblacion poblacionInicial = new Poblacion(Poblacion.ALEATORIA);
		generaciones[0] = new Generacion(poblacionInicial);
		Selector selector = null;
		Poblacion poblacionActual = null;
		for (int i = 1; i < numeroGeneraciones; i++) {
			selector = Generacion.getSelector();
			poblacionActual = generaciones[i - 1].getPoblacion();
			selector.prepararSeleccion(poblacionActual);
			generaciones[i] = generaciones[i - 1].generar();
		}
		tiempoEjecucion = System.nanoTime() - tiempoInicial;
		banderaEjecutar = true;
	}

	/**
	 * Devuelve una cadena de caracteres con datos de la ejecución. La cadena
	 * muestra el numero de cada generación y muestra los datos de esta. Este
	 * método <b>no muestra</b> a los individuos de las poblaciones asociadas a
	 * cada generación.
	 * 
	 * @return una cadena de caracteres con datos de la ejecución.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numeroGeneraciones; i++) {
			sb.append("\n").append("GENERACION ").append(i + 1).append("\n");
			sb.append(generaciones[i]);
		}
		return sb.toString();
	}

	/**
	 * Devuelve una cadena de caracteres con datos de la ejecución incluyendo a
	 * los individuos de la población. La cadena muestra el numero de cada
	 * generación y muestra los datos de esta. Este método <b>muestra</b> a los
	 * individuos de las poblaciones asociadas a cada generación.
	 * 
	 * @return una cadena de caracteres con datos de la ejecución incluyendo a
	 *         los individuos de la población.
	 */
	public String toStringConPoblaciones() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numeroGeneraciones; i++) {
			sb.append("\n").append("GENERACION ").append(i + 1).append("\n");
			sb.append(generaciones[i].toStringConPoblaciones());
		}
		return sb.toString();
	}
}