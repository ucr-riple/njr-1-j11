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

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Clase auxiliar de la clase {@link agapi.Generacion} cuya única finalidad es
 * alojar al método {@code generar}.
 * 
 * @author Saul Gonzalez
 */
public class Generador {
	private Generacion generacion;
	private static int m;
	private static Random gnaCruce;
	private static Random gnaMutacion;
	private Individuo[] nuevos;
	private Individuo[] padres;
	private Individuo[] hijos;
	private Individuo[] exitosos;
	private Individuo mejor;
	private Individuo peor;
	private double sum;
	private double sum2;
	private Individuo[] adicionales;//individuos adicionales
	private int indice;

	static {
		m = Poblacion.getTamanoPoblacion();
	}

	/**
	 * Inicia la evolución de una generación a otra por medio de la acción de
	 * los operadores genéticos. Recibe un objeto de tipo
	 * {@link agapi.Generacion} y aplica a los individuos de este, los métodos
	 * {@link agapi.Individuo#cruce(Individuo) cruce} y
	 * {@link agapi.Individuo#mutacion() mutacion} de la clase
	 * {@link agapi.Individuo}. También aplica los métodos de las interfaces
	 * {@link agapi.Selector} y {@link agapi.SelectorPostCruce} correspondientes
	 * a las formas de selección previas y posteriores al cruce.
	 * <p>
	 * Además, este método hace uso de las variables {@code probabilidadCruce} y
	 * {@code probabilidadMutacion} de {@link agapi.Generacion} correspondientes
	 * a los valores de las probabilidades de cruce y mutación. También utiliza
	 * el valor lógico de la variable booleana {@code elitismo} de
	 * {@link agapi.Generacion} si se desea (o no) utilizar la herramienta
	 * elitista.
	 * 
	 * @param generacionActual
	 *            la generación a la que se aplicarán los operadores genéticos
	 * @return una nueva generación (evolucionada)
	 */
	public Generacion generar(Generacion generacionActual) {
		gnaCruce = new Random(getSemilla());
		gnaMutacion = new Random(getSemilla());
		this.generacion = generacionActual;
		fase1();
		fase2();
		fase3();
		fase4();
		Generacion nuevaGeneracion = new Generacion();
		nuevaGeneracion.setPoblacion(poblacionGenerada());
		return nuevaGeneracion;
	}

	// se inicializan los individuos que corresponda segun el caso
	// tambien se determina indice
	private void fase1() {
		nuevos = new Individuo[m];
		if (m % 2 == 0) {
			if (Generacion.isElitismo()) {
				nuevos[0] = generacion.getPoblacion().getMejorIndividuo();
				adicionales = Generacion.getSelector().seleccion(
						generacion.getPoblacion(), 1);
				nuevos[1] = adicionales[0];
			} else {
				adicionales = Generacion.getSelector().seleccion(
						generacion.getPoblacion(), 2);
				nuevos[0] = adicionales[0];
				nuevos[1] = adicionales[1];
			}
			indice = 2;
		} else {
			if (Generacion.isElitismo()) {
				nuevos[0] = generacion.getPoblacion().getMejorIndividuo();
			} else {
				adicionales = Generacion.getSelector().seleccion(
						generacion.getPoblacion(), 1);
				nuevos[0] = adicionales[0];
			}
			indice = 1;
		}
	}

	// se inicializan las sumatorias para media y desviacion segun el caso
	private void fase2() {
		sum = 0.0;
		sum2 = 0.0;
		sum = sum + nuevos[0].getFA();
		sum2 = sum2 + nuevos[0].getFA() * nuevos[0].getFA();
		if (indice == 2) {
			sum = sum + nuevos[1].getFA();
			sum2 = sum2 + nuevos[1].getFA() * nuevos[1].getFA();
		}
	}

	// se inicializan mejor y peor individuo segun el caso
	private void fase3() {
		mejor = nuevos[0];
		peor = nuevos[0];
		if (indice == 2) {
			if (nuevos[1].getFA() >= mejor.getFA()) {
				mejor = nuevos[1];
			}
			if (nuevos[1].getFA() <= peor.getFA()) {
				peor = nuevos[1];
			}
		}
	}

	// se calculan los datos para el resto de los individuos
	private void fase4() {
		for (int i = indice; i < m; i = i + 2) {
			// aplicacion de operadores geneticos
			padres = Generacion.getSelector().seleccion(
					generacion.getPoblacion(), 2);
			if (gnaCruce.nextDouble() < Generacion.getProbabilidadCruce()) {
				hijos = padres[0].cruce(padres[1]);
				hijos[0].setFA(hijos[0].calcFA());
				hijos[1].setFA(hijos[1].calcFA());
				if (gnaMutacion.nextDouble() < Generacion
						.getProbabilidadMutacion()) {
					hijos[0].mutacion();
					hijos[0].setFA(hijos[0].calcFA());
				}
				if (gnaMutacion.nextDouble() < Generacion
						.getProbabilidadMutacion()) {
					hijos[1].mutacion();
					hijos[1].setFA(hijos[1].calcFA());
				}
				exitosos = Generacion.getSelectorPostCruce()
						.seleccionPostCruce(padres, hijos);
			} else {
				exitosos = padres;
			}
			nuevos[i] = exitosos[0];
			nuevos[i + 1] = exitosos[1];
			// para usar en el calculo de la media y desviacion
			sum = sum + nuevos[i].getFA() + nuevos[i + 1].getFA();
			sum2 = sum2 + nuevos[i].getFA() * nuevos[i].getFA()
					+ nuevos[i + 1].getFA() * nuevos[i + 1].getFA();
			// calculo del mejor (mayor)
			if (nuevos[i].getFA() >= mejor.getFA()) {
				mejor = nuevos[i];
			}
			if (nuevos[i + 1].getFA() >= mejor.getFA()) {
				mejor = nuevos[i + 1];
			}
			// calculo del peor (menor) y segundoPeor
			if (nuevos[i].getFA() <= peor.getFA()) {
				peor = nuevos[i];
			}
			if (nuevos[i + 1].getFA() <= peor.getFA()) {
				peor = nuevos[i + 1];
			}
		}
	}

	// devuelve una nueva poblacion producto del proceso generacional
	private Poblacion poblacionGenerada() {
		Poblacion pob = new Poblacion();
		pob.setIndividuos(nuevos);
		pob.setMedia(sum / m);
		pob.setDesviacion(Math.pow((sum2 / m) - (pob.getMedia() * pob.getMedia()), 0.5));
		pob.setMejorIndividuo(mejor);
		pob.setPeorIndividuo(peor);
		return pob;
	}

	/*
	 * Genera un numero aleatorio aleatorio de tipo long con un alto grado de
	 * entropia. Esta entropia es elegida por el Sistema Operativo. El numero
	 * tiene una resolucion de 8 bytes (2<sup>64</sup> bits). Extraido de
	 * http://www.javamex.com/tutorials/random_numbers/seeding_entropy.shtml
	 */
	private long getSemilla() {
		SecureRandom sec = new SecureRandom();
		byte[] sbuf = sec.generateSeed(8);
		ByteBuffer bb = ByteBuffer.wrap(sbuf);
		return bb.getLong();
	}
}