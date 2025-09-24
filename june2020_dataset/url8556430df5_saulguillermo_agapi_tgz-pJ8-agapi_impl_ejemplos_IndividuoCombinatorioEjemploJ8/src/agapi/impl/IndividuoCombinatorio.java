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

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

import agapi.Individuo;

/**
 * Un individuo combinatorio es un individuo con un cromosoma formado por una
 * cadena de números enteros de tamaño {@code tamanoCromosoma}. Los números son
 * una secuencia no ordenada desde 1 hasta <em>n</em>, donde <em>n</em> es igual
 * al tamaño de la población.
 * <p>
 * Esta clase implementa los métodos abstractos
 * {@link agapi.Individuo#aleatorio() aleatorio},
 * {@link agapi.Individuo#cruce(Individuo) cruce} y
 * {@link agapi.Individuo#mutacion() mutacion} de {@link agapi.Individuo}. El
 * único método que no implementa es {@link agapi.Individuo#calcFA() calcFA},
 * esto se debe, obviamente, a que cada función de aptitud es única para cada
 * problema y queda de parte del usuario su implementación al heredar de esta
 * clase.
 * <p>
 * La cadena de números enteros esta representada por la variable
 * {@code cromosoma} que consiste en un arreglo de tipo {@code int}. PMX es el
 * tipo de cruce utilizado por este individuo, PMX son las siglas en ingles para
 * <em>cruce parcialmente mapeado</em> (Partially Matched Crossover), para la
 * implementación de este tipo de recombinación se hace un llamado al método
 * {@link agapi.impl.CrucePMX#cruce(IndividuoCombinatorio, IndividuoCombinatorio)
 * cruce} de la clase {@link agapi.impl.CrucePMX}. PMX se explica con mejor
 * detalle en la descripción del método {@code cruce}.
 * <p>
 * La mutación consiste en el simple intercambio de dos genes (números enteros)
 * dentro de la misma secuencia.
 * 
 * @author sebukan
 * 
 */
public abstract class IndividuoCombinatorio extends Individuo {

	private int[] cromosoma;
	private CrucePMX crucePMX;
	private static Random gnaAleatorio;
	private static Random gnaMutacion;

	static {
		gnaAleatorio = new Random(getLongSeed());
		gnaMutacion = new Random(getLongSeed());
	}

	/**
	 * Devuelve la cadena de números enteros correspondiente al cromosoma de
	 * este individuo.
	 * 
	 * @return el cromosoma de este individuo
	 */
	public int[] getCromosoma() {
		return cromosoma;
	}

	/**
	 * Establece una cadena de números enteros correspondiente al cromosoma de
	 * este individuo
	 * 
	 * @param cromosoma
	 *            el cromosoma a establecer
	 */
	public void setCromosoma(int[] cromosoma) {
		this.cromosoma = cromosoma;
	}

	/**
	 * Transforma el cromosoma de este individuo en una sucesión aleatoria de
	 * números enteros, es decir, en un arreglo de tipo {@code int}.
	 * 
	 */
	@Override
	public void aleatorio() {
		int n = Individuo.getTamanoCromosoma();
		cromosoma = new int[n];
		for (int i = 0; i < n; i++) {
			cromosoma[i] = i + 1;
		}
		int aux = 0;
		int rand = 0;
		for (int i = 0; i < n; i++) {
			rand = gnaAleatorio.nextInt(n);
			aux = cromosoma[i];
			cromosoma[i] = cromosoma[rand];
			cromosoma[rand] = aux;
		}
	}

	/**
	 * Retorna un par individuos combinatorios productos del cruce entre este
	 * individuo combinatorio y el individuo madre, utilizando un operador PMX.
	 * PMX son las siglas en ingles para <em>cruce parcialmente mapeado</em>
	 * (Partially Matched Crossover) y es un operador de reordenamiento, es
	 * decir, que modifica el orden de una secuencia, fue presentado por
	 * Goldberg & Lingle (1985).
	 * <p>
	 * El cruce PMX consiste en tomar dos puntos de cruce aleatoriamente e
	 * intercambiar las secuencias de cada individuo ubicadas entre estos
	 * puntos, estos segmentos son inmodificables. Luego se debe seguir un
	 * proceso explicado por Goldberg (1989) y Haupt & Haupt (2004) donde se
	 * sustituyen los elementos repetidos de un cromosoma por los repetidos del
	 * otro.
	 * 
	 * @param madre
	 *            el individuo madre
	 * @return un par de individuos combinatorios producto del cruce entre este
	 *         individuo y el individuo madre
	 */
	@Override
	public Individuo[] cruce(Individuo madre) {
		crucePMX = new CrucePMX();
		IndividuoCombinatorio[] hijos = crucePMX.cruce(
				(IndividuoCombinatorio) madre, this);
		return hijos;
	}

	/**
	 * La mutación consiste en el simple intercambio de dos genes (números
	 * enteros) dentro de la misma secuencia. Para ello genera dos números
	 * aleatorios correspondientes a las posiciones y luego intercambia los
	 * elementos.
	 */
	@Override
	public void mutacion() {
		int n = Individuo.getTamanoCromosoma();
		int aux = 0;
		int rand1 = 0;
		int rand2 = 0;
		while (rand1 == rand2) {
			rand1 = gnaMutacion.nextInt(n);
			rand2 = gnaMutacion.nextInt(n);
		}
		aux = cromosoma[rand1];
		cromosoma[rand1] = cromosoma[rand2];
		cromosoma[rand2] = aux;
	}

	/*
	 * Genera un número aleatorio aleatorio de tipo long con un alto grado de
	 * entropia. Esta entropia es elegida por el Sistema Operativo. El número
	 * tiene una resolucion de 8 bytes (2<sup>64</sup> bits). Extraido de
	 * http://www.javamex.com/tutorials/random_numbers/seeding_entropy.shtml
	 */
	private static long getLongSeed() {
		SecureRandom sec = new SecureRandom();
		byte[] sbuf = sec.generateSeed(8);
		ByteBuffer bb = ByteBuffer.wrap(sbuf);
		return bb.getLong();
	}
}