/**
 * 
 */
package pit.kos.falki.utils;

import java.awt.Color;

/**
 * @author Piotr Kosmala 22 kwi 2016 22:57:05
 * aaa
 */
public class ColorStore {
	private int r;
	private int g;
	private int b;
	private int a;

	public ColorStore() 
	{
	}
	
	public ColorStore(int r, int g, int b, int a) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public ColorStore setR(int r) {
		this.r = r;
		return this;
	}

	public int getG() {
		return g;
	}

	public ColorStore setG(int g) {
		this.g = g;
		return this;
	}

	public int getB() {
		return b;
	}

	public ColorStore setB(int b) {
		this.b = b;
		return this;
	}

	public int getA() {
		return a;
	}

	public ColorStore setA(int a) {
		this.a = a;
		return this;
	}

	public Color getFinallColor(){
		  /* int value = ((a & 0xFF) << 24) |
	                ((r & 0xFF) << 16) |
	                ((g & 0xFF) << 8)  |
	                ((b & 0xFF) << 0);
	        testColorValueRange(r,g,b,a);*/
	        return new Color(r, g, b, a);
	}
/*	 private static void testColorValueRange(int r, int g, int b, int a) {
	        boolean rangeError = false;
	        String badComponentString = "";

	        if ( a < 0 || a > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Alpha";
	        }
	        if ( r < 0 || r > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Red";
	        }
	        if ( g < 0 || g > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Green";
	        }
	        if ( b < 0 || b > 255) {
	            rangeError = true;
	            badComponentString = badComponentString + " Blue";
	        }
	        if ( rangeError == true ) {
	        throw new IllegalArgumentException("Color parameter outside of expected range:"
	                                           + badComponentString);
	        }
	    }*/

}
