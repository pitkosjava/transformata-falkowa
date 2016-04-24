/**
 * 
 */
package signal1d;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pit.kos.falki.signals.Signal1D;
import pit.kos.falki.waves.Wavelet1D;
import pit.kos.falki.waves.typ.Daubechies1;
import pit.kos.falki.waves.typ.Daubechies2;

/**
 * @author Piotr Kosmala
 *13 kwi 2016
 *21:52:44
 */
public class TestSignal {
	private static Logger logger = LoggerFactory.getLogger(TestSignal.class);
	/**
	 * 
	 */
	private static final int COUNT_SIGNAL = 10;

	/*@Ignore
	@Test
	public void testSingalCookingFunctionAlgo() {
		int maynumber=33; // must be equal 64
		int numberpowtwo=64;
		int count=0;
		while(maynumber!=0){
			maynumber=maynumber>>1;
			count++;
		}
		int newsize=(int) Math.pow(2, count);
		Assert.assertEquals(numberpowtwo, newsize);
	}
	@Ignore
	@Test
	public void testSingal() {
		Signal1D signal= new Signal1D(5);
		signal.setElement(0, 1);
		signal.setElement(1, 1);
		signal.setElement(2, 1);
		signal.setElement(3, 1);
		signal.setElement(4, 1);
		 double[] signalsA= new Daubechies1().decomositionSignal(signal.getSignal(),0);
		// logger.info( Arrays.toString(signalsA));
		 Assert.assertEquals(8,signalsA.length);
	}
	@Ignore
	@Test
	public void testSingalWaveletDecomposition() {
		double[] signal=new double[512];
		Wavelet1D wavelet= new Wavelet1D(2,signal);
		wavelet.setPerformance(true);
		Daubechies1 waveletTyp=new Daubechies1();
		wavelet.setWavelet(waveletTyp);
		wavelet.runDecomposition();
		//logger.info(wavelet.toString());
		wavelet.runSyntese();
	}
	@Ignore
	@Test
	public void testSingalWaveletSynthesis() {
		double[] signal={1,1,1.1,1.2,1.1,3};
		logger.info("Orginal signal "+Arrays.toString(signal));
		Wavelet1D wavelet= new Wavelet1D(2,signal);
		wavelet.setPerformance(true);
		Daubechies1 waveletTyp=new Daubechies1();
		wavelet.setWavelet(waveletTyp);
		wavelet.runDecomposition();
		logger.info(wavelet.toString());
		wavelet.runSyntese();
	}
	@Ignore
	@Test
	public void testSingalWaveletSynthesis2() {
		double[] signal={1,3,1,2,3,1};
		logger.info("Orginal signal "+Arrays.toString(signal));
		Wavelet1D wavelet= new Wavelet1D(1,signal);
		wavelet.setPerformance(true);
		Daubechies2 waveletTyp=new Daubechies2();
		wavelet.setWavelet(waveletTyp);
		wavelet.runDecomposition();
		logger.info(wavelet.toString());
		wavelet.runSyntese();
		
	}*/

}
