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
import agapi.Poblacion;

/**
 * Es un selector estocástico que aplica el método de
 * <em>muestreo estocástico universal</em> (
 * <em>stochastic universal sampling, SUS</em>, por sus siglas en ingles) de
 * James Baker (1987) para la selección de individuos. Al igual que el método
 * <em>{@linkplain agapi.impl.SelectorRuleta de la ruleta}</em>, es una técnica
 * de selección que también asigna "rebanadas" a cada individuo en función del
 * valor esperado, pero en vez de girar la rueda de ruleta <em>N</em> veces,
 * solo se hace girar una vez (se genera solo un número aleatorio) utilizando
 * <em>N</em> apuntadores espaciados equidistantemente, la distancia entre cada
 * uno es de <em>Sum / N</em>, donde <em>Sum</em> es igual a la sumatoria de
 * todos los valores esperados de los individuos de la población y <em>N</em> es
 * el número de individuos seleccionados con chance de reproducirse.
 * <p>
 * La "rebanada" asignada a cada individuo está en función del valor esperado de
 * este. Este valor puede ser proporcional y depender sólo de la aptitud del
 * individuo como en la ruleta clásica de Goldberg (1989) o puede ser un valor
 * escalado como el {@linkplain agapi.impl.FuncionTanese escalamiento sigma} de
 * Tanese (1989) donde toman en cuenta, no solo la función de aptitud, sino
 * también las medias y las desviaciones estándares. También se puede utilizar
 * funciones no proporcionales (Baker 1985) como en la selección
 * <em>{@linkplain agapi.impl.FuncionRanking por jerarquías (ranking)}</em> que
 * no dependen directamente de la aptitud.
 * <p>
 * Ver: Baker, J. E. (1987).
 * <em>Reducing bias and inefficiency in the selection algorithm</em>. En
 * <em>Proceedings of the second international conference on genetic algorithms on 
 * genetic algorithms and their application</em> (pp. 14–21). Hillsdale, NJ,
 * USA: L. Erlbaum Associates Inc.
 * 
 * @see SelectorEstocastico
 * @author Saul Gonzalez
 */
public class SelectorSUS extends SelectorEstocastico {

	private static Random gna;

	static {
		gna = new Random(getLongSeed());
	}

	/**
	 * Constructor por defecto utiliza la función clásica (Goldberg, 1989) de
	 * valor esperado basada únicamente en la aptitud de los individuos.
	 */
	public SelectorSUS() {
		super();
	}

	/**
	 * Constructor que recibe una función de valor esperado especifica.
	 * 
	 * @param funcValEsp
	 *            la funcion de valor esperado
	 */
	public SelectorSUS(Funcion funcValEsp) {
		super(funcValEsp);
	}

	/**
	 * Devuelve un arreglo de tipo {@code Individuo} que representa el pool de
	 * los individuos seleccionados utilizando la técnica de
	 * <em>muestreo estocástico universal</em>.
	 * 
	 * @param poblacion
	 *            la población de donde se seleccionarán los individuos
	 * @param valEspAcum
	 *            el arreglo con los valores esperados acumulados calculados en
	 *            base a la función del valor esperado pasada como parámetro en
	 *            el constructor de esta clase
	 * @return el pool de individuos seleccionados
	 * 
	 * @see agapi.impl.SelectorEstocastico#generaPool(agapi.Poblacion, double[])
	 */
	@Override
	public Individuo[] generaPool(Poblacion poblacion, double[] valEspAcum) {
		int m = Poblacion.getTamanoPoblacion();
		Individuo[] individuos = poblacion.getIndividuos();
		Individuo[] poolDeSeleccionados = new Individuo[m];
		double[] puntos = generaPuntos(valEspAcum);
		for (int i = 0; i < m; i++) {
			if (puntos[i] < valEspAcum[0]) {// elegidos menores a valEspAcum[0]
				poolDeSeleccionados[i] = individuos[0];
			} else {
				for (int j = 1;; j++) {// corre hasta cumplir condicion
					if ((puntos[i] >= valEspAcum[j - 1])
							&& (puntos[i] < valEspAcum[j])) {
						poolDeSeleccionados[i] = individuos[j];
						break;
					}
				}
			}
		}
		return poolDeSeleccionados;
	}

	// devuelve puntos equidistantes
	private double[] generaPuntos(double[] valEspAcum) {
		int m = Poblacion.getTamanoPoblacion();
		double[] puntos = new double[m];
		double distancia = valEspAcum[m - 1] / m;// distancia entre puntos
		puntos[0] = gna.nextDouble() * distancia;// pto inicial
		for (int i = 1; i < puntos.length; i++) {// resto de puntos
			puntos[i] = puntos[i - 1] + distancia;
		}
		return puntos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (super.getFuncValEsp() instanceof FuncionClasico) {
			sb.append("Muestreo Estocastico Universal (clasico)");
		} else {
			sb.append("Muestreo Estocastico Universal con "
					+ super.getFuncValEsp().toString());
		}
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