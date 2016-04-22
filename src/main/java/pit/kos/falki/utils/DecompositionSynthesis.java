/**
 * 
 */
package pit.kos.falki.utils;

/**
 * @author Piotr Kosmala
 *14 kwi 2016
 *16:13:29
 *Basic interfaces for all waveletes typ
 */
public interface DecompositionSynthesis {
	public  double[]  decomositionSignal(double[] signalTab,int step);
	public  double[] synthesisSignal(double[] signalTab, int step);
}
