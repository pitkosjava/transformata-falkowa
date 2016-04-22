/**
 * 
 */
package pit.kos.falki.waves;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import pit.kos.falki.signals.Signal1D;
import pit.kos.falki.utils.CONVERT_TYP;
import pit.kos.falki.utils.DecompositionSynthesis;
import pit.kos.falki.utils.ImageUtils;


/**
 * @author Piotr Kosmala
 *17 kwi 2016
 *13:06:20
 *Class create to transform only images
 */
public class Wavelet2D {

	/* Wymiary potega dwojki wymog algorytmu**/
	private Signal1D signalW;
	private Signal1D signalH;
	
	private BufferedImage image;
	
	private int[][] orginalSignal;
	private int[][] preparedOrginalSignal;
	
	private CONVERT_TYP convert_typ;
	

	private DecompositionSynthesis waveletType;
	
	public Wavelet2D(BufferedImage image,CONVERT_TYP typ,DecompositionSynthesis waveletType) 
	{
		this.image=ImageUtils.makeImage(image, typ);
		this.convert_typ=typ;
	    orginalSignal=ImageUtils.convertIntoArrays(image, convert_typ);
	    
	    /** nowe wymiary poetega dwojki **/
	    signalW = new Signal1D(orginalSignal[0]); 
	    signalH = new Signal1D(getColumn(orginalSignal,0));
	    
	    // sygnal gotowy do przetworzenia
	    preparedOrginalSignal = new int[signalH.getSignal().length][signalW.getSignal().length];
	
	    // kopiowanie zawartosci dopiero taki sygnal mozemy podawac dekompozycji
	    for (int h = 0; h < orginalSignal.length; h++) 
			for (int w = 0; w < orginalSignal[0].length; w++) 
				preparedOrginalSignal[h][w]=orginalSignal[h][w];
	    
	    this.waveletType=waveletType;
	}
	
	
	/** deepDecomposition for 0 to n dekompozycja pozioma */
	public void runDecompositionX(int deepDecomposition)
	{
		double[] arrayDouble;
		for (int i = 0; i <= deepDecomposition; i++) 
			for (int h = 0; h < orginalSignal[0].length; h++) // dlugosc od 0 do 
			{
				// dalej jest czarne nie musimy transformować i wyjdziemy poza zakres orginalnego sygnalu
				arrayDouble=intArraytoDouble(preparedOrginalSignal[h]);
				arrayDouble= waveletType.decomositionSignal(arrayDouble, i);
				preparedOrginalSignal[h] =doubleArraytoInt(arrayDouble);
			}
	}
	
	/**  deepDecomposition for 0 to n dekompozycja pionowa */
	public void runDecompositionY(int deepDecomposition)
	{
			int [] arrayInt; 
			double[] arrayDouble;
			for (int i = 0; i <= deepDecomposition; i++) 
				for (int h = 0; h < orginalSignal.length; h++)
				{
					arrayInt=getColumn(preparedOrginalSignal,h);
					// convert to int table
					arrayDouble=intArraytoDouble(arrayInt);
					// decomposition signal
					arrayDouble= waveletType.decomositionSignal(arrayDouble,i);
					arrayInt=doubleArraytoInt(arrayDouble);
					preparedOrginalSignal=setColumnIn2DArrays(preparedOrginalSignal,arrayInt,h);
				}
	}
			
		/** run DecompositionYX  pozioma i pionowa razem */
	public void  runDecompositionYX(int deepDecomposition)
	{
			int [][] coutSignal;
			// znormowane sygnaly
			int levelDeepX=preparedOrginalSignal[0].length; 				// punkty graniczne potegi 2 , sygnaly sa juz odpowiednio poprawione
			int levelDeepY=preparedOrginalSignal.length;
			int [] tempArray;
			
			for(int deep=0;deep<=deepDecomposition;deep++)
			{
				int levelecopy=(int) Math.pow(2, deep);
				int scopeY=levelDeepY/levelecopy;
				int scopeX=levelDeepX/levelecopy;
				coutSignal= new int[scopeY][scopeX]; 						// ta tabele wklejamy kopiujemy wartosci i zawsze jeden taz dekompozycja na 0 poziomie
				for(int h=0;h<scopeY;h++)
					for(int w=0;w<scopeX;w++)
						coutSignal[h][w]=preparedOrginalSignal[h][w]; 		// kopiowanie
				
				for(int x=0;x<scopeX;x++)
					coutSignal[x]=runDecomposition(coutSignal[x]); 			// liczenie dekompozycji rozlozenie na dwa
				
				for(int y=0;y<scopeY;y++)
				{
					tempArray=getColumn(coutSignal,y);
					tempArray=runDecomposition(tempArray);
					setColumnIn2DArrays(coutSignal,tempArray,y);
				}
				for(int yy=0;yy<scopeY;yy++)
					for(int xx=0;xx<scopeX;xx++)
						preparedOrginalSignal[yy][xx]=coutSignal[yy][xx]; // wklejamy do naszej starej tabeli 
			}
	}
	
	public void runSynthesisX(int deepSynthesis)
	{ 
			int [] tempArray;
			int levelDeepX=preparedOrginalSignal[0].length; 
			for(int deep=deepSynthesis;deep>=0;deep--) 
			{
				int levelecopy=(int) Math.pow(2, deep); 
				int scopeX=levelDeepX/levelecopy; 
			
				for(int x=0;x<preparedOrginalSignal.length;x++) 
				{
					tempArray=getRow(preparedOrginalSignal,x,scopeX);
				    tempArray=runSynthese(tempArray);
				    preparedOrginalSignal=setRowIn2DArrays(preparedOrginalSignal,tempArray,x);
				}
			}
	}
		
	public void runSynthesisY(int deepSynthesis)
	{ 	
			int [] tempArray;
			int levelDeepY=preparedOrginalSignal.length;
			for(int deep=deepSynthesis;deep>=0;deep--) 	
			{
				int levelecopy=(int) Math.pow(2, deep); 
				int scopeY=levelDeepY/levelecopy; 		
				for (int h = 0; h < preparedOrginalSignal[0].length; h++) 
				{
					tempArray=getColumn(preparedOrginalSignal,h,scopeY); 
					tempArray = runSynthese(tempArray);           
					preparedOrginalSignal=setColumnIn2DArrays(preparedOrginalSignal,tempArray,h); 
				}
			}
	}
		
	public void runSynthesisYX(int deepSynthesis)
	{  

			int [] tempArray;
			int [][] coutSignal;
			int levelDeepX=preparedOrginalSignal[0].length; 
			int levelDeepY=preparedOrginalSignal.length;  	
			for(int deep=deepSynthesis;deep>=0;deep--) 	  
			{
				int levelecopy=(int) Math.pow(2, deep);   
				int scopeY=levelDeepY/levelecopy;         
				int scopeX=levelDeepX/levelecopy;          
				coutSignal= new int[scopeY][scopeX]; 
				
				for(int h=0;h<scopeY;h++)
					for(int w=0;w<scopeX;w++)
						coutSignal[h][w]=preparedOrginalSignal[h][w]; // copy orginal signal
				
				for (int h = 0; h < coutSignal[0].length; h++) {
					tempArray=getColumn(coutSignal,h,scopeY); 
					tempArray = runSynthese(tempArray);            		
					coutSignal=setColumnIn2DArrays(coutSignal,tempArray,h); 
				}
				for(int x=0;x<coutSignal.length;x++) 
				{
					tempArray=getRow(coutSignal,x,scopeX);
				    tempArray=runSynthese(tempArray);
				    coutSignal=setRowIn2DArrays(coutSignal,tempArray,x);
				}
				for(int yy=0;yy<scopeY;yy++)
					for(int xx=0;xx<scopeX;xx++)
						preparedOrginalSignal[yy][xx]=coutSignal[yy][xx]; // wklejamy do naszej starej tabeli 
				
			}
	}
	
	/** main metods transformat */
	private int[] runDecomposition(int [] signal){
		double[] arrayDouble;
		arrayDouble=intArraytoDouble(signal);
		arrayDouble= waveletType.decomositionSignal(arrayDouble,0);
		signal=doubleArraytoInt(arrayDouble);
		return signal;
	}

	private int[] runSynthese(int [] signal){
		double[] arrayDouble;
		arrayDouble=intArraytoDouble(signal);
		arrayDouble= waveletType.synthesisSignal(arrayDouble,1);
		signal=doubleArraytoInt(arrayDouble);
		return signal;
	}
	
	/** Helper metods*/
	public static int[] getColumn(int[][] matrix, int column)
	{
		return IntStream.range(0, matrix.length) .map(i -> matrix[i][column]).toArray();
	}
		
	public static int[] getColumn(int[][] matrix, int column,int to) 
	{
		return IntStream.range(0, to) .map(i -> matrix[i][column]).toArray();
	}
	
	public static int[] getRow(int[][] matrix, int row,int to) 
	{
		 int[] tab= new int[to];
		 for(int i=0;i<to;i++)
			  tab[i]=matrix[row][i];
		 return tab;
	}
		
	public int [][] setColumnIn2DArrays(int [][] arays,int[] column,int index)
	{
			for(int i=0;i<column.length;i++)
					arays[i][index]=column[i];
			return arays;
	}
	
	public int [][] setRowIn2DArrays(int [][] arays,int[] row,int index)
	{
			for(int i=0;i<row.length;i++)
					arays[index][i]=row[i];
			return arays;
	}

	public BufferedImage getImageOrginal()
	{
		return ImageUtils.convertArrayIntoImage(ImageUtils.convertIntoArrays(image,convert_typ),convert_typ);
	}
		
	public BufferedImage getImageDec(){
		 return	ImageUtils.convertArrayIntoImage(preparedOrginalSignal, convert_typ);
	}
		
	public    double[] intArraytoDouble(int[] arrayint)
	{
		double[] arraydouble= new double[arrayint.length];
			for(int i=0;i<arrayint.length;i++)
				arraydouble[i]=arrayint[i];
		return arraydouble;
	}
	
	public    int[] doubleArraytoInt(double[] arraydouble) 
	{
		int[] arrayint= new int[arraydouble.length];
		for(int i=0;i<arraydouble.length;i++)
			arrayint[i]=(int) arraydouble[i];
		return arrayint;
	}
}
