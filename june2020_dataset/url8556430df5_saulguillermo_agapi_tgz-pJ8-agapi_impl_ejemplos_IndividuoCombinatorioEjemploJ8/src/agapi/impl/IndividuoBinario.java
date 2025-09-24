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
 * Un individuo binario es un individuo con un cromosoma formado por una cadena
 * de ceros y unos. Esta clase implementa los métodos abstractos
 * {@link agapi.Individuo#aleatorio() aleatorio},
 * {@link agapi.Individuo#cruce(Individuo) cruce} y
 * {@link agapi.Individuo#mutacion() mutacion} de {@link agapi.Individuo}. El
 * único método que no implementa es {@link agapi.Individuo#calcFA() calcFA},
 * esto se debe, obviamente, a que cada función de aptitud es única para cada
 * problema y queda de parte del usuario su implementación al heredar de esta
 * clase.
 * <p>
 * La cadena de ceros y unos esta representada por la variable {@code cromosoma}
 * que consiste en un arreglo de tipo boolean.
 * 
 * @author sebukan
 * 
 */
public abstract class IndividuoBinario extends Individuo {

	private boolean[] cromosoma;
	private static Random gnaAleatorio;
	private static Random gnaCruce;
	private static Random gnaMutacion;

	static {
		gnaAleatorio = new Random(getLongSeed());
		gnaCruce = new Random(getLongSeed());
		gnaMutacion = new Random(getLongSeed());
	}

	/**
	 * Devuelve la cadena de ceros y unos correspondiente al cromosoma de este
	 * individuo.
	 * 
	 * @return el cromosoma de este individuo
	 */
	public boolean[] getCromosoma() {
		return cromosoma;
	}

	/**
	 * Establece una cadena de ceros y unos correspondiente al cromosoma de este
	 * individuo.
	 * 
	 * @param cromosoma
	 *            el cromosoma a establecer
	 */
	public void setCromosoma(boolean[] cromosoma) {
		this.cromosoma = cromosoma;
	}

	/**
	 * Transforma el cromosoma de este individuo en una sucesión aleatoria de
	 * ceros y unos, es decir, en un arreglo de tipo {@code boolean}.
	 * 
	 */
	@Override
	public void aleatorio() {
		int n = Individuo.getTamanoCromosoma();
		cromosoma = new boolean[n];
		for (int i = 0; i < n; i++) {
			cromosoma[i] = gnaAleatorio.nextBoolean();
		}
	}

	/**
	 * Retorna un par individuos binarios productos del cruce entre este
	 * individuo binario y el individuo madre. El cruce consiste en el
	 * intercambio de las secuencias de los cromosomas de los individuos padres.
	 * Se selecciona una posición aleatoria en la secuencia de unos y ceros, a
	 * partir de este punto se intercambian las secuencias del padre y la madre
	 * para obtener dos nuevos individuos.
	 * 
	 * @param madre
	 *            el individuo madre
	 * @return un par de individuos binarios productos del cruce entre este
	 *         individuo y el individuo madre
	 */
	@Override
	public Individuo[] cruce(Individuo madre) {
		IndividuoBinario[] hijos = subCruce(this, (IndividuoBinario) madre);
		return hijos;
	}

	/**
	 * Modifica el valor de un elemento en la secuencia de unos y ceros. Escoge
	 * de forma aleatoria una posición e invierte su valor: si es un uno lo
	 * cambia por un cero o viceversa.
	 * 
	 */
	@Override
	public void mutacion() {
		int n = Individuo.getTamanoCromosoma();
		int i = gnaMutacion.nextInt(n);
		if (cromosoma[i]) {
			cromosoma[i] = false;
		} else {
			cromosoma[i] = true;
		}
	}

	private IndividuoBinario[] subCruce(IndividuoBinario madre,
			IndividuoBinario padre) {
		IndividuoBinario[] hijos = new IndividuoBinario[2];
		if (madre == padre) {
			hijos[0] = padre;// puede ser madre, da lo mismo
			hijos[1] = padre;
		} else {
			int n = Individuo.getTamanoCromosoma();
			hijos[0] = (IndividuoBinario) Individuo.fabricaIndividuo();
			hijos[1] = (IndividuoBinario) Individuo.fabricaIndividuo();
			int punto;
			if (n == 1) {
				punto = 1;
			} else {
				punto = gnaCruce.nextInt(n - 1) + 1;
			}
			hijos[0].cromosoma = subSubCruce(punto, madre.cromosoma,
					padre.cromosoma);
			hijos[1].cromosoma = subSubCruce(punto, padre.cromosoma,
					madre.cromosoma);// se invierte el orden del cruce
		}
		return hijos;
	}

	private boolean[] subSubCruce(int punto, boolean[] cro1, boolean[] cro2) {
		int n = Individuo.getTamanoCromosoma();
		boolean[] unCro = new boolean[n];
		System.arraycopy(cro1, 0, unCro, 0, punto);
		System.arraycopy(cro2, punto, unCro, punto, n - punto);
		return unCro;
	}

	/*
	 * Genera un numero aleatorio aleatorio de tipo long con un alto grado de
	 * entropia. Esta entropia es elegida por el Sistema Operativo. El numero
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