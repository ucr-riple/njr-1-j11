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
 * Es un selector estocástico que aplica el método <em>de la ruleta</em>
 * (Goldberg, 1989) para la selección de individuos. Es una técnica de selección
 * que consiste en asignar una "rebanada" de una rueda de ruleta de casino a un
 * individuo. La ruleta se hace girar (generando un número aleatorio) <i>N</i>
 * veces, donde <i>N</i> representa el número de individuos seleccionados con
 * chance de reproducirse. En cada giro, el individuo asignado a la rebanada
 * seleccionada es elegido como un padre.
 * <p>
 * La "rebanada" asignada a cada individuo está en función del valor esperado de
 * este. En la versión original de Goldberg este valor es proporcional y depende
 * sólo de la aptitud del individuo en analogía con la idea de individuos más
 * aptos tienen mayores oportunidades de sobrevivir; sin embargo otros trabajos
 * (Tanese 1989) han utilizado métodos de {@linkplain agapi.impl.FuncionTanese
 * escalamiento} que toman en cuenta, no solo la función de aptitud, sino
 * también las medias y las desviaciones estándares. También se han utilizado
 * funciones no proporcionales (Baker 1985) como en la selección
 * <em>{@linkplain agapi.impl.FuncionRanking por jerarquías (ranking)}</em> que
 * no dependen directamente de la aptitud.
 * <p>
 * Ver: Goldberg, D. E. (1989).
 * <em>Genetic algorithms in search, optimization, and machine learning.</em>
 * Addison-Wesley Publishing Company.
 * 
 * @see SelectorEstocastico
 * @author Saul Gonzalez
 */
public class SelectorRuleta extends SelectorEstocastico {

	private static Random gna;

	static {
		gna = new Random(getLongSeed());
	}

	/**
	 * Constructor por defecto utiliza la función clásica (Goldberg, 1989) de
	 * valor esperado basada únicamente en la aptitud de los individuos.
	 */
	public SelectorRuleta() {
		super();
	}

	/**
	 * Constructor que recibe una función de valor esperado especifica.
	 * 
	 * @param funcValEsp
	 *            la funcion de valor esperado
	 */
	public SelectorRuleta(Funcion funcValEsp) {
		super(funcValEsp);
	}

	/**
	 * Devuelve un arreglo de tipo {@code Individuo} que representa el pool de
	 * los individuos seleccionados utilizando el método <em>de la ruleta</em>.
	 * 
	 * @param poblacion
	 *            la población de donde se seleccionarán los individuos.
	 * @param valEspAcum
	 *            el arreglo con los valores esperados acumulados calculados en
	 *            base a la función del valor esperado pasada como parámetro en
	 *            el constructor de esta clase
	 * @return el pool de individuos seleccionados
	 * @see agapi.impl.SelectorEstocastico#generaPool(agapi.Poblacion, double[])
	 */
	@Override
	public Individuo[] generaPool(Poblacion poblacion, double[] valEspAcum) {
		int m = Poblacion.getTamanoPoblacion();
		Individuo[] individuos = poblacion.getIndividuos();
		Individuo[] poolDeSeleccionados = new Individuo[m];
		double rand = 0.0;
		for (int i = 0; i < m; i++) {
			rand = gna.nextDouble() * valEspAcum[m - 1];
			if (rand < valEspAcum[0]) {// elegidos menores a valEspAcum[0]
				poolDeSeleccionados[i] = individuos[0];
			} else {
				for (int j = 1;; j++) {// corre hasta cumplir condicion
					if ((rand >= valEspAcum[j - 1]) && (rand < valEspAcum[j])) {
						poolDeSeleccionados[i] = individuos[j];
						break;
					}
				}
			}
		}
		return poolDeSeleccionados;
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
			sb.append("Ruleta (Goldberg, 1989)");
		} else {
			sb.append("Ruleta con " + super.getFuncValEsp().toString());
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