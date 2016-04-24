/**
 * 
 */
package pit.kos.falki.waves.typ;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pit.kos.falki.utils.DecompositionSynthesis;

/**
 * @author Piotr Kosmala 13 kwi 2016 23:08:04
 *  @see http://wavelets.pybytes.com/wavelet/db/  
 */
public final class Daubechies1 implements DecompositionSynthesis {
	private static Logger logger;
	static{
		 logger = LoggerFactory.getLogger(Daubechies1.class);
	}
	private final static int part = 2;
	public final static double[] hid = { -0.7071067812, 0.7071067812 }; // hight decoomposition wavelet
	public final static double[] lod = { 0.7071067812, 0.7071067812 }; // low  decoomposition wavelet

	public final static double[] lir = {0.7071067812, -0.7071067812 }; //hight synthesis wavelet
	public final static double[] lor = {0.7071067812,0.7071067812 }; // low  synthesis wavelet
	
	@Override
	public double[] decomositionSignal(double[] signalTab, int step) {
		int lenghtTab = signalTab.length;
		int levelDecomposition = (int) Math.pow(part, step);
			int scop = lenghtTab / levelDecomposition;
			double[] signal = Arrays.copyOf(signalTab, signalTab.length);
			
			int index = 0;
			for (int i = 0; i < scop; i = i + part) {
				signal[index] = signalTab[i] * lod[0] + signalTab[i + 1]* lod[1];
				index++;
			}
			for (int i = 0; i < scop; i = i + part) {
				signal[index] = signalTab[i] * hid[0] + signalTab[i + 1]* hid[1]; // next
				index++;
			}
			return signal;
	}
	
	@Override
	public double[] synthesisSignal(double[] signalTab, int step) {
		int levelSynthesis = (int) Math.pow(part, step);
		int copyElements=signalTab.length/levelSynthesis;
		/* add zeros */
		int newLenghtTab=(copyElements)*2+1;
		double[] signalLow = new double[newLenghtTab];
		double[] signalHi = new double[newLenghtTab];
		// first low
		for(int i=0;i<copyElements;i++)
			signalLow[i*2+1]=signalTab[i];
		// next hi
		for(int i=copyElements;i<copyElements*2;i++)
			signalHi[(i-copyElements)*2+1]=signalTab[i];
		
		for(int i=0;i<copyElements*2;i++)
		{
			signalTab[i]=signalLow[i] * lor[0] + 
						signalLow[i + 1]* lor[1]+
						signalHi[i] * lir[0] + 
						signalHi[i + 1]* lir[1];// next
			
		}
		return signalTab;
	}

}
