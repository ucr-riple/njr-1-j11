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

import agapi.Individuo;
import agapi.Poblacion;

/**
 * Esta clase representa la función clásica de valor esperado utilizado por
 * David Goldberg (1989). Dicha función no es más que la función de la aptitud
 * de cada individuo de la población.
 * <p>
 * Ver: Goldberg, D. E. (1989).
 * <em>Genetic algorithms in search, optimization, and machine learning.</em>
 * Addison-Wesley Publishing Company.
 * 
 * @see agapi.impl.Funcion
 * @author sebukan
 * 
 */
public class FuncionClasico implements Funcion {

	/**
	 * Devuelve un arreglo de números reales positivos que representan los
	 * valores esperados de los individuos de la población pasada como
	 * parámetro. Dichos valores esperados corresponden con las aptitudes de
	 * cada individuo (Goldber, 1989).
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
		Individuo[] individuos = poblacion.getIndividuos();
		double[] valEsp = new double[individuos.length];

		for (int i = 0; i < individuos.length; i++) {// valEsp de cada uno
			valEsp[i] = individuos[i].getFA();// es igual a la FA!!!
		}
		return valEsp;
	}
}