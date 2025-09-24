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

import agapi.Generacion;
import agapi.Individuo;
import agapi.Poblacion;
import agapi.Selector;

/**
 * Es un selector que aplica la técnica de selección
 * <em>por torneo, probabilístico y binario</em>. En términos generales, un
 * método de selección por torneo consiste en escoger aleatoriamente a un grupo
 * de individuos de la población, escoger a los mejores de este grupo y repetir
 * el proceso hasta llenar el pool de seleccionados. En el caso concreto de este
 * selector se escogen aleatoriamente dos individuos , luego se genera un número
 * aleatorio <em>r</em> entre 0 y 1. Si <em>r</em> es menor a <em>k</em> (donde
 * <em>k</em> es un parámetro entre 0.5 y 1), el individuo más apto será
 * seleccionado, de lo contrario el menos apto será seleccionado.
 * <p>
 * Este tipo de específico de selección es llamado binario por realizar el
 * torneo entre dos individuos, y probabilístico, por utilizar un número
 * aleatorio <em>r</em> para determinar el ganador. David E. Golberg y Kalyanmoy
 * Deb (1991) hacen un buen análisis de este método.
 * <p>
 * Ver: Goldberg, D. E. & Deb, K. A. (1991).
 * <em>A comparative analysis of selection schemes used in
 * genetic algorithms</em> en <em>Foundations of Genetic Algorithms</em> (pp.
 * 69-93). Morgan Kaufmann.
 * 
 * @author sebukan
 * 
 */
public class SelectorTorneo implements Selector {
	private int contador;// contador de individuos
	private Individuo[] poolDeSeleccionados;// individuos elegidos
	private static Random gnaVoltear;
	private static Random gnaEleccion;
	private static double k;

	/**
	 * Este constructor recibe como parámetro el valor de la constante
	 * <em>k</em> encargada de establecer el límite entre el individuo ganador y
	 * el perdedor. Este valor debe estar comprendido entre 0.5 y 1 de lo
	 * contrario se produce una excepción de tipo
	 * {@code IllegalArgumentException}
	 * 
	 * @param k
	 *            valor de la constante <em>k</em>
	 * 
	 * @throws IllegalArgumentException
	 *             si {@code k} está fuera del rango [0.5,1]
	 * 
	 */
	public SelectorTorneo(double k) {
		if (k < 0.5 || k > 1) {
			throw new IllegalArgumentException(
					"k debe estar comprendida en el rango [0.5,1]");
		} else {
			SelectorTorneo.k = k;
		}
	}

	static {
		gnaVoltear = new Random(getLongSeed());
		gnaEleccion = new Random(getLongSeed());
	}

	/**
	 * Selecciona una cantidad especifica de individuos de la población según el
	 * la técnica <em>por torneo probabilístico y binario</em>.
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
	 * <em>por torneo probabilístico y binario</em>.
	 * <p>
	 * Este método es llamado por {@link agapi.Ejecucion#ejecutar() ejecutar} de
	 * la clase {@link agapi.Ejecucion} una vez por generación, dado que en cada
	 * generación se debe preparar los datos que utilizara la siguiente
	 * generación.
	 * 
	 * @param poblacion
	 *            la población a la que se le aplicará la técnica
	 *            <em>por torneo</em>
	 */
	@Override
	public void prepararSeleccion(Poblacion poblacion) {
		contador = 0;
		int m = Poblacion.getTamanoPoblacion();
		poolDeSeleccionados = new Individuo[m];
		Individuo[] individuos = poblacion.getIndividuos();
		int posicionA = 0;
		int posicionB = 0;
		Individuo jugadorA = null;
		Individuo jugadorB = null;
		Individuo ganador = null;
		int i = 0;
		while (i < m) {
			posicionA = gnaEleccion.nextInt(m);
			posicionB = gnaEleccion.nextInt(m);
			jugadorA = individuos[posicionA];
			jugadorB = individuos[posicionB];
			ganador = jugadorA;// se realiza el torneo
			if (voltear(k)) {
				ganador = jugadorB;
			}
			poolDeSeleccionados[i] = ganador;// se asigna el ganador
			i++;
		}
	}

	private boolean voltear(double k) {
		boolean result = false;
		double r = gnaVoltear.nextDouble();
		if (r > k) {
			result = true;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Torneo Probabilistico con n=2 y k=" + k);
		return sb.toString();
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