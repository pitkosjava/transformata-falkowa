/**
 * 
 */
package signal1d;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pit.kos.falki.waves.typ.Daubechies2;

/**
 * @author Piotr Kosmala
 *24 kwi 2016
 *12:09:39
 */
public class TestDaubechies2 {
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



	@Test
	public void testalgorithmDecompositionAndSynthese(){
		
		double[] singalOrgin={2,3,1,1,1,3,2,1,2,3,1,1,1,3,2,5};
		
		double[] decompositionSignal=decomositionSignal(singalOrgin);
		logger.info("Orgin TestDaubechies2"+Arrays.toString(singalOrgin));
	
		logger.info("Decomposition TestDaubechies2"+Arrays.toString(decompositionSignal));
		syntheseSignal(decompositionSignal);
		
	}
	
	
	
	
	public static double[] decomositionSignal(double[] signalTab) {
		int orginalLenght = signalTab.length;
		int copyLenght= orginalLenght+hid.length-part;
	
		
		double[] copysignalTab= new double[copyLenght];
		
		for(int  i=part;i<copyLenght;i++)
		{
			copysignalTab[i]=signalTab[i-part];
		}
		copysignalTab[0]=signalTab[orginalLenght-2];
		copysignalTab[1]=signalTab[orginalLenght-1];
		double [] orginalSignal= new double[signalTab.length];
		
		int index = 0;
		for (int i = 0; i < orginalLenght; i = i + part) {
			orginalSignal[index] = copysignalTab[i] * lod[0] + copysignalTab[i+1] * lod[1]+copysignalTab[i+2]*lod[2]+copysignalTab[i+3]*lod[3];
			index++;
		}
		
		for (int i = 0; i < orginalLenght; i = i + part) {
			orginalSignal[index] = copysignalTab[i] * hid[0] + copysignalTab[i+1] * hid[1]+copysignalTab[i+2]*hid[2]+copysignalTab[i+3]*hid[3]; // next
			index++;
		}
	
		return orginalSignal;
	}	
	
	public static double[] syntheseSignal(double[] signalTab) {
	
		
		int scope = part;
		int scopHalf=signalTab.length/scope;
		
		double [] copySignal=new double[signalTab.length];
		
		double[] lowSignal=new double[scopHalf*2+lor.length-part+1];
		double[] higtSignal=new double[scopHalf*2+lor.length-part+1];
	
		for(int i=0;i<scopHalf;i++)
			lowSignal[i*2+1]=signalTab[i];
		
		for(int i=scopHalf;i<scopHalf*2;i++)
			higtSignal[(i-scopHalf)*2+1]=signalTab[i];
		
		lowSignal[signalTab.length+1]=signalTab[0];
		
		higtSignal[signalTab.length+1]=signalTab[scopHalf];
	    logger.debug("low copy"+Arrays.toString(lowSignal));
		
		logger.debug("hi copy"+Arrays.toString(higtSignal));
		
		for(int i=0;i<signalTab.length;i++){
			copySignal[i]=
					lowSignal[i] * lor[0] +
					lowSignal[i+1]* lor[1]+
					lowSignal[i+2]* lor[2]+
					lowSignal[i+3]* lor[3]+
					higtSignal[i] * lir[0] +
					higtSignal[i+1]* lir[1]+
					higtSignal[i+2]* lir[2]+
					higtSignal[i+3]* lir[3];
					
		}
		

		
		logger.debug("syntheseSignal low"+Arrays.toString(lowSignal));
		
		logger.debug("syntheseSignal hi"+Arrays.toString(higtSignal));
		
		logger.debug("output syn "+Arrays.toString(copySignal));
		return copySignal;
	}	
	
	
	
	
}
