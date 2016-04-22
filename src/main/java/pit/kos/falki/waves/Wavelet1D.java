/**
 * 
 */
package pit.kos.falki.waves;

import java.util.Arrays;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import pit.kos.falki.signals.Signal1D;
import pit.kos.falki.utils.DecompositionSynthesis;

/**
 * @author Piotr Kosmala
 *14 kwi 2016
 *16:25:04
 */
/**
 * decomposition set family Wavelet signal is signal to input matrix use to
 * store data size deepDecomposition to decomposition when deepDecomposition is
 * greter all values are equal 0
 * 
 * **/
public class Wavelet1D {

	private static final int LAST_INDEX_Synthesis = 0;

	/** you can create or add new wavelet , myst only implements two interfaces basic*/
	private DecompositionSynthesis typeWavelets;
	
	private Signal1D signal;
	/* matrix store signal*/
	private RealMatrix matrixDecomposition;
	
	/* matrix store signal*/
	private RealMatrix matrixSynthesis;
	
	private int deepDecomposition = 0;
	private int actualDecompositionIndex = 0;
	/* optional deepDecomposition is the same,but decrement in loop */
	private int actualSynteseIndex = 0;
	private int orginalSize;
	private double[] actualSignal;
	
	/* when is of all deep decomposition is save in matrix, you can draw function*/
	private boolean performance=true;
	

	/**
	 * matrix save all step decompositions you can look rows 0 is signal column
	 * to start is size is deep + signal rows
	 */
	public Wavelet1D(int deepDecomposition, double[] signal) 
	{
		this.orginalSize=signal.length;
		this.signal = new Signal1D(signal);
		/* set max steep if you set max valu or more*/
		deepDecomposition=(this.signal.getPowerOftwo()<deepDecomposition)?this.signal.getPowerOftwo():deepDecomposition;
		this.deepDecomposition = deepDecomposition;
		this.actualSynteseIndex=deepDecomposition;
	}
	public Wavelet1D(int deepDecomposition) 
	{ 
	// to do
	}
	
	/* signalD must by cooking use class Signal1D  */

	public void runDecomposition() 
	{
		actualSignal = signal.getSignal();
		actualDecompositionIndex = 0;
		if(!isPerformance())
		{
			while (actualDecompositionIndex < deepDecomposition)
			{
				actualSignal = typeWavelets.decomositionSignal(actualSignal,actualDecompositionIndex);
				this.matrixDecomposition.setRow(actualDecompositionIndex, actualSignal);
				actualDecompositionIndex++;
			}
		}
		else 
		{
			while (actualDecompositionIndex < deepDecomposition)
			{
				actualSignal = typeWavelets.decomositionSignal(actualSignal,actualDecompositionIndex);
				actualDecompositionIndex++;
			}
		}
	}
	
	
	public double[] getSignalDecomposition()
	{
		return actualSignal;
	}
	
	public void runSyntese() 
	{
		if(!isPerformance())
		{
			while (actualSynteseIndex > LAST_INDEX_Synthesis)
			{
				actualSignal = typeWavelets.synthesisSignal(actualSignal,actualSynteseIndex);
				actualSynteseIndex--;
				this.matrixDecomposition.setRow(actualDecompositionIndex-1, actualSignal);
			}
		}
		else 
		{
			while (actualSynteseIndex > LAST_INDEX_Synthesis)
			{
				actualSignal = typeWavelets.synthesisSignal(actualSignal,actualSynteseIndex);
				actualSynteseIndex--;
			}
		}
	}

	public boolean setElementSignal(int index, double value)
	{
		boolean status = signal.setElement(index, value);
		// if signal change must set before runDecomposition
		if (status) 
		{
			this.matrixDecomposition.setRow(0, this.signal.getSignal());
		}
		return status;
	}


	public void setWavelet(DecompositionSynthesis decomposition)
	{
		this.typeWavelets = decomposition;
	}
	
	public Signal1D getSignal() 
	{
		return signal;
	}

	@Override
	public String toString() 
	{
		return performance? "enabled performance ":"Wavelet1D [matrixDecomposition=" + matrixDecomposition + "] \\n" + "[matrixSynthesis =" + matrixSynthesis + "] \n";
	}

	public boolean isPerformance() 
	{
		return performance;
	}

	public void setPerformance(boolean performance) 
	{
		// log on steep decomposition in matrix use this when you draw graph
		if(!performance)
		{
			  this.matrixDecomposition = MatrixUtils.createRealMatrix(deepDecomposition + 1,this.signal.getSignal().length); // row cols
			  this.matrixDecomposition.setRow(0, this.signal.getSignal());
			  this.matrixSynthesis=MatrixUtils.createRealMatrix(deepDecomposition,this.signal.getSignal().length); // row cols
		}
		else 
		{
			  this.matrixDecomposition = null;
			  this.matrixSynthesis=null;
		}
		this.performance = performance;
	}
	
	private void setSignal(Signal1D signal) 
	{
		this.signal = signal;
	}
}

