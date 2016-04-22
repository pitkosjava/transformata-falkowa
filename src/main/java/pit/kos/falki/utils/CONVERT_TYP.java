/**
 * 
 */
package pit.kos.falki.utils;

/**
 * @author Piotr Kosmala
 *17 kwi 2016
 *13:40:00
 */
public enum CONVERT_TYP {
	GRAY,COLOR;
	
	public static CONVERT_TYP typ(int index)
	{
		return (index ==0)?COLOR:GRAY;
	}
}
