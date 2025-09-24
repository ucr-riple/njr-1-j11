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

import java.util.Arrays;

import agapi.Individuo;
import agapi.Poblacion;

/**
 * Esta clase permite el cálculo de valores esperados en función de las
 * <em>jerarquías</em> (<em>ranking</em>)de los individuos dentro de la
 * población. Dichas jerarquías están basadas en las aptitudes de los
 * individuos. Consiste en una función lineal propuesta por James Baker (1985)
 * que utiliza dos constantes: <em>Max</em> determina el valor esperado del
 * individuo ubicado en la jerarquía más alta (<em>jerarquía = N</em>, con
 * <em>N</em> = tamaño de la población); y <em>Min</em> el valor del individuo
 * con la jerarquía más baja (<em>jerarquía = 1</em>). Baker recomienda usar
 * <em>Max</em> en 1.1 y hacer <em>Min = 2.0 - Max</em>.
 * <p>
 * Ver: Baker, J. E. (1985).
 * <em>Adaptive Selection Methods for Genetic Algorithms</em> en
 * <em>Proceedings of the 1st International Conference on Genetic Algorithms</em>
 * (pp 101-111). L. Erlbaum Associates Inc.
 * 
 * @see agapi.impl.Funcion
 * @author sebukan
 * 
 */
public class FuncionRanking implements Funcion {

	private static double max = 0;

	/**
	 * Constructor que acepta como parámetro el valor de la constante
	 * <em>Max</em> utilizado en la función de jerarquías lineal de Baker
	 * (1985).
	 * 
	 * @param max
	 *            constante. Valor del máximo valor para esta función, es decir,
	 *            el valor del mejor individuo en la jerarquía.
	 */
	public FuncionRanking(double max) {
		if (max >= 1.0 && max <= 2.0) {
			FuncionRanking.max = max;
		} else {
			throw new IllegalArgumentException(
					"max debe estar entre 1 y 2, ambos inclusive");
		}
	}

	/**
	 * Devuelve un arreglo de números reales positivos que representan los
	 * valores esperados de los individuos de la población pasada como
	 * parámetro. Dichos valores esperados corresponden con la función de valor
	 * esperado por jerarquías lineal de Baker (1985).
	 * 
	 * @see agapi.impl.Funcion#calcValEsp(agapi.Poblacion)
	 * @param poblacion
	 *            la población a cuyos individuos se les calcularán la función
	 *            de valor esperado.
	 * @return un arreglo con todos los valores esperados de los individuos de
	 *         la población.
	 */
	@Override
	public double[] calcValEsp(Poblacion poblacion) {
		int m = Poblacion.getTamanoPoblacion();
		Individuo[] individuos = poblacion.getIndividuos();
		Arrays.sort(individuos);
		int[] jerarquias = new int[m];
		for (int i = 0; i < m; i++) {
			jerarquias[i] = i + 1;
		}
		double[] valEsp = new double[m];
		double min = 2.0 - max;

		for (int i = 0; i < m; i++) {
			valEsp[i] = min + (max - min) * (jerarquias[i] - 1) / (m - 1);
		}
		return valEsp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		double min = 2.0 - max;
		StringBuilder sb = new StringBuilder();
		sb.append("Ranking (Baker 1985) con Max=" + max + " y Min=2-Max=" + min);
		return sb.toString();
	}

}