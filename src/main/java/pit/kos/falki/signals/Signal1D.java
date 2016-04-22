/**
 * 
 */
package pit.kos.falki.signals;

import java.util.Arrays;

/**
 * @author Piotr Kosmala 13 kwi 2016 21:10:26
 */
public class Signal1D {

	private double[] signal;
	private int powerOftwo=0;
	
	public Signal1D() 
	{
		this(1);
	}

	public Signal1D(int size) 
	{
		signal =new double[size];
		for(int i=0;i<signal.length;i++)	
			signal[i]=0.0;
		cookingSignal();
	}
	
	public Signal1D(double[] signal) 
	{
		this.signal=signal;
		cookingSignal();
	}
	
	public Signal1D(int[] signal) 
	{
		double[] buf= new double[signal.length];
		for(int i=0;i<signal.length;i++)	
			buf[i]=signal[i];
		this.signal=buf;
		cookingSignal();
	}

	public boolean setElement(int index, double value)
	{
		if (index < signal.length && index >= 0) 
		{
			signal[index] = value;
			return true;
		} 
		else
			return false;
	}

	public void resize(int size) 
	{
		double[] tempSignal = new double[size];
		for(int i=0;i<tempSignal.length;i++)
			tempSignal[i]=0.0;
		for (int i = 0; i < signal.length; i++)
			tempSignal[i] = signal[i];
		signal = tempSignal;
	}

	// signal must power of two 2
	private void cookingSignal() 
	{
		int size = signal.length;
		powerOftwo=0;
		while (size!= 0) 
		{
			size=size >> 1;
			powerOftwo++;
		}
		int newsize = (int) Math.pow(2, powerOftwo);
		resize(newsize);
	}

	@Override
	public String toString() {
		return "Signal1D [signal=" + Arrays.toString(signal) + "]";
	}

	public double[] getSignal() {
		return signal;
	}

	public void setSignal(double[] signal) {
		this.signal = signal;
		
	}

	public int getPowerOftwo() {
		return powerOftwo;
	}
}
