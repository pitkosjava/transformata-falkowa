/**
 * 
 */
package pit.kos.falki.waves.typ;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pit.kos.falki.utils.DecompositionSynthesis;

/**
 * @author Piotr Kosmala 23 kwi 2016 18:35:16 Wavelet Daubechies 2 (db2)
 * @see http://wavelets.pybytes.com/wavelet/db2/
 */
public final class Daubechies2 implements DecompositionSynthesis {

	private static Logger logger;
	static 
	{
		logger = LoggerFactory.getLogger(Daubechies2.class);
	}

	private final static int part = 2;
	public final static double[] hid = { -0.4829629131, 0.8365163037,-0.2241438680, -0.1294095226 }; // hight decoomposition wavelet
	public final static double[] lod = { -0.1294095226, 0.2241438680,0.8365163037, 0.4829629131 }; // low decoomposition wavelet

	public final static double[] lir = { -0.1294095226, -0.2241438680,	0.8365163037, -0.4829629131 }; // hight synthesis wavelet
	public final static double[] lor = { 0.4829629131, 0.8365163037,0.2241438680, -0.1294095226 }; // low synthesis wavelet

	@Override
	public double[] decomositionSignal(double[] signalTab, int step) {
			int lenghtTab = signalTab.length;
			/*
			 * must create new table for orginal signal , lengt myst always
			 * orrginal.lenght+ wavelet.lengft-2 when result is new table
			 * orginal.slenght/2
			 */
			int newlenght= signalTab.length+hid.length-part;
			double[] copysignalTab= new double[newlenght];
			
			copysignalTab[0]=signalTab[signalTab.length-2];
			copysignalTab[1]=signalTab[signalTab.length-1];
			for(int  i=2;i<newlenght;i++)
			{
				copysignalTab[i]=signalTab[i-2];
			}

		int index = 0;
		for (int i = 0; i < lenghtTab; i = i + part) {
			signalTab[index] = copysignalTab[i] * lod[0] + copysignalTab[i+1] * lod[1]+copysignalTab[i+2]*lod[2]+copysignalTab[i+2]*lod[3];
			index++;
		}
		for (int i = 0; i < lenghtTab; i = i + part) {
			signalTab[index] = copysignalTab[i] * hid[0] + copysignalTab[i+1] * hid[1]+copysignalTab[i+2]*hid[2]+copysignalTab[i+2]*hid[3]; // next
			index++;
		}
		return signalTab;
	}

	@Override
	public double[] synthesisSignal(double[] signalTab, int step) {
		// to do
		return signalTab;
	}
}
