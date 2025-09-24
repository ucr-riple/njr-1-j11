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
 * Esta clase implementa la función de valor esperado con escalamiento sigma
 * presentada por Reiko Tanese(1989). Consiste en una función proporcional que
 * utiliza tres variables: la función de aptitud, la media y la desviación
 * estándar de la población.
 * <p>
 * Ver: Tanese, R. 1989.
 * <em>Distributed Genetic Algorithms for Function Optimization.</em> Ph.D.
 * thesis, University of Michigan. Electrical Engineering and Computer Science
 * Department.
 * 
 * 
 * @author sebukan
 * 
 */
public class FuncionTanese implements Funcion {

	/**
	 * Devuelve un arreglo de números reales que representan los valores
	 * esperados de los individuos de la población pasada como parámetro. Dichos
	 * valores están calculados con la función de escalamiento sigma utilizada
	 * por Tanese (1989).
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
		double media = poblacion.getMedia();
		double desviacion = poblacion.getDesviacion();

		if (desviacion < 0.000000001) {
			for (int i = 0; i < individuos.length; i++) {
				valEsp[i] = 1.0;
			}
		} else {
			for (int i = 0; i < individuos.length; i++) {
				valEsp[i] = 1 + (individuos[i].getFA() - media)
						/ (2.0 * desviacion);
				if (valEsp[i] < 0.0) {// no debe haber valores negativos
					valEsp[i] = 0.1;// este valor es arbitrario
				}
			}
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
		StringBuilder sb = new StringBuilder();
		sb.append("Escalamiento Sigma (Tanese 1989)");
		return sb.toString();
	}

}