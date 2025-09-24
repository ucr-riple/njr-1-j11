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
 * Esta clase tiene como única función suministrar un método {@code cruce} de
 * tipo PMX para la clase {@code IndividuoCombinatorio}. Esto se debe a la
 * complejidad del proceso de cruce tipo PMX, lo que amerita toda una clase para
 * su desarrollo.
 * 
 * @author sebukan
 * 
 */
public class CrucePMX {

	// tamaño de la secuencia (cromosoma)
	private static int n = Individuo.getTamanoCromosoma();

	// generadores de numeros aleatorios para limites
	private static Random gna1;
	private static Random gna2;

	// limites del segmento inmodificable
	private int lim1;
	private int lim2;

	// tipo de caso
	private int caso;
	private static final int PRIMER_CASO = 0;
	private static final int SEGUNDO_CASO = 1;
	private static final int TERCER_CASO = 2;

	private int[] a;// cromosoma madre
	private int[] b;// cromosoma padre
	private int[] c;// cromosoma hijo1
	private int[] d;// cromosoma hijo2
	private int valor1;// valor un gen repetido de hijo1
	private int valor2;// valor un gen repetido de hijo2
	private int posicion1;// posicion de gen repetido de hijo1
	private int posicion2;// posicion de gen repetido de hijo2
	private int posicionInmodificable;// posicion auxiliar

	static {// inicia los generadores de numeros aleatorios
		gna1 = new Random(getLongSeed());
		gna2 = new Random(getLongSeed());
	}

	/**
	 * Retorna un par de individuos combinatorios productos del cruce entre los
	 * individuos combinatorios madre y padre, utilizando un operador PMX.
	 * 
	 * @param madre
	 *            el individuo madre
	 * @param padre
	 *            el individuo padre
	 * @return un par de individuos combinatorios producto del cruce entre madre
	 *         y padre
	 */
	public IndividuoCombinatorio[] cruce(IndividuoCombinatorio madre,
			IndividuoCombinatorio padre) {
		a = madre.getCromosoma();
		b = padre.getCromosoma();
		generaLimites();
		determinaCaso();
		intercambioBloques();
		intercambiaGenesRepetidos();

		IndividuoCombinatorio[] hijos = new IndividuoCombinatorio[2];
		hijos[0] = (IndividuoCombinatorio) Individuo.fabricaIndividuo();
		hijos[0].setCromosoma(c);
		hijos[1] = (IndividuoCombinatorio) Individuo.fabricaIndividuo();
		hijos[1].setCromosoma(d);
		return hijos;
	}

	/*
	 * Genera los límites de los segmentos que se intercambiarán. El lim1 es el
	 * límite inferior y lim2 es el límite superior, ambos inclusives. Dadas las
	 * características del cruce PMX, existen ciertas condiciones para que sean
	 * válidos los valores de los límites:
	 * 
	 * 1. lim1 debe ser diferente a lim2
	 * 
	 * 2. si lim1 vale cero, lim2 no puede valer (n - 1)
	 * 
	 * 3. si lim2 vale (n -1), lim1 no puede valer cero.
	 */
	private void generaLimites() {
		int rand1;
		int rand2;
		do {
			rand1 = gna1.nextInt(n);
			rand2 = gna2.nextInt(n);
		} while ((rand1 == rand2) || (rand1 == 0 && rand2 == n - 1)
				|| (rand1 == n - 1 && rand2 == 0));
		if (rand1 < rand2) {
			lim1 = rand1;
			lim2 = rand2;
		} else {
			lim1 = rand2;
			lim2 = rand1;
		}
	}

	/*
	 * Establece el tipo de caso entre los que se pueden presentar: 1.
	 * 
	 * PRIMER_CASO: el limite inferior vale cero.
	 * 
	 * SEGUNDO_CASO: limite inferior y superior no valen ni cero ni (n-1),
	 * respectivamente.
	 * 
	 * TERCER_CASO: limte superior vale n-1.
	 */
	private void determinaCaso() {
		if (lim1 == 0) {
			caso = PRIMER_CASO;
		} else {
			if (lim2 == n - 1) {
				caso = TERCER_CASO;
			} else {
				caso = SEGUNDO_CASO;
			}
		}
	}

	/*
	 * Crea dos nuevos cromosomas (secuencias) con secuencias intercambiadas de
	 * los cromosomas de los padres, según el caso. Cada nuevo cromosoma posee
	 * una secuencia que es inmodificable (ningún gen puede cambiar de posicion
	 * ni de valor), los genes que están fuera de esta secuencia pueden estar
	 * repetidos.
	 */
	private void intercambioBloques() {
		c = new int[n];
		d = new int[n];
		switch (caso) {
		case PRIMER_CASO:
			System.arraycopy(a, 0, c, 0, lim2 + 1);
			System.arraycopy(b, lim2 + 1, c, lim2 + 1, n - lim2 - 1);
			System.arraycopy(b, 0, d, 0, lim2 + 1);
			System.arraycopy(a, lim2 + 1, d, lim2 + 1, n - lim2 - 1);
			break;
		case SEGUNDO_CASO:
			System.arraycopy(a, 0, c, 0, lim1);
			System.arraycopy(b, lim1, c, lim1, lim2 - lim1 + 1);
			System.arraycopy(a, lim2 + 1, c, lim2 + 1, n - lim2 - 1);
			System.arraycopy(b, 0, d, 0, lim1);
			System.arraycopy(a, lim1, d, lim1, lim2 - lim1 + 1);
			System.arraycopy(b, lim2 + 1, d, lim2 + 1, n - lim2 - 1);
			break;
		case TERCER_CASO:
			System.arraycopy(a, 0, c, 0, lim1);
			System.arraycopy(b, lim1, c, lim1, n - lim1);
			System.arraycopy(b, 0, d, 0, lim1);
			System.arraycopy(a, lim1, d, lim1, n - lim1);
			break;
		}
	}

	/*
	 * Intercambia los genes repetidos que se encuentran fuera de la secuencia
	 * inmodificable.
	 */
	private void intercambiaGenesRepetidos() {
		switch (caso) {
		case PRIMER_CASO:
			for (int i = lim2 + 1; i < n; i++) {
				recorreBloque(i);
			}
			break;
		case SEGUNDO_CASO:
			for (int i = 0; i < lim1; i++) {
				recorreBloque(i);
			}
			for (int i = lim2 + 1; i < n; i++) {
				recorreBloque(i);
			}
			break;
		case TERCER_CASO:
			for (int i = 0; i < lim1; i++) {
				recorreBloque(i);
			}
			break;
		}
	}

	/*
	 * Para cada elemento (i) del cromosoma del hijo1 que este fuera de la
	 * secuencia inmodificable, busca un gen repetido en la secuencia
	 * inmodificable. Si encuentra un repetido, guarda su valor y las posiciones
	 * de este y la del gen de la secuencia inmodificable, luego corre el método
	 * conectar.
	 */
	private void recorreBloque(int i) {
		for (int j = lim1; j <= lim2; j++) {
			if (c[i] == c[j]) {
				valor1 = c[i];
				posicion1 = i;
				posicionInmodificable = j;
				conectar(posicionInmodificable);
			}
		}
	}

	/*
	 * Con la posicion de un gen repetido en la secuencia inmodificable del
	 * hijo1, busca el correspondiente valor del gen de intercambio en la
	 * secuencia inmodificable del hijo2. Este método es recursivo.
	 */
	private void conectar(int posicionBloque) {
		int val = 0;
		for (int i = lim1; i <= lim2; i++) {
			if (i == lim2 && d[posicionBloque] != c[i]) {
				val = d[posicionBloque];
				encuentraPosicion2(val);
				break;
			} else {
				if (d[posicionBloque] == c[i]) {
					conectar(i);// llamada recursiva
				}
			}
		}
	}

	/*
	 * Con el valor del método anterior busca la posicion del gen de intercambio
	 * en el cromosoma del hijo2
	 */
	private void encuentraPosicion2(int val) {
		switch (caso) {
		case PRIMER_CASO:
			for (int i = lim2 + 1; i < n; i++) {
				intercambiaGen(val, i);
			}
			break;
		case SEGUNDO_CASO:
			for (int i = 0; i < lim1; i++) {
				intercambiaGen(val, i);
			}
			for (int i = lim2 + 1; i < n; i++) {
				intercambiaGen(val, i);
			}
			break;
		case TERCER_CASO:
			for (int i = 0; i < lim1; i++) {
				intercambiaGen(val, i);
			}
			break;
		}
	}

	/*
	 * Realiza un intercambio del gen repetido del hijo1 por el gen repetido del
	 * hijo2
	 */
	private void intercambiaGen(int val, int i) {
		if (d[i] == val) {
			posicion2 = i;
			valor2 = d[i];
			c[posicion1] = valor2;
			d[posicion2] = valor1;
		}
	}

	private static long getLongSeed() {
		SecureRandom sec = new SecureRandom();
		byte[] sbuf = sec.generateSeed(8);
		ByteBuffer bb = ByteBuffer.wrap(sbuf);
		return bb.getLong();
	}
}