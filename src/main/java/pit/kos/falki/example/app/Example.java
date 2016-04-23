/**
 * 
 */
package pit.kos.falki.example.app;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.math3.analysis.function.Log;
import org.apache.commons.math3.linear.MatrixUtils;

import pit.kos.falki.signals.Signal1D;
import pit.kos.falki.utils.CONVERT_TYP;
import pit.kos.falki.utils.ImageUtils;
import pit.kos.falki.waves.Wavelet1D;
import pit.kos.falki.waves.Wavelet2D;
import pit.kos.falki.waves.typ.Daubechies1;

/**
 * @author Piotr Kosmala 15 kwi 2016 13:59:34
 * test app
 */
public class Example extends JFrame {
	private static final long serialVersionUID = -8830664557771078635L;
	private File file;

	private JFrame frame;
	private PanelPhoto panelPhoto = new PanelPhoto();
	private JButton selectImage = new JButton("select image");

	private JSpinner  jspindedcomositionDeep = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

	/* 0 color 1 gray**/
	private JSpinner  convertColorTyp = new JSpinner(new SpinnerNumberModel(0, 0, 1, 1));
	private JLabel labelColortyp= new JLabel("typ 0 color 1 gray");
	private JLabel labeldeepDec= new JLabel("deep dec 0 to n");
	private JButton convertToGrayMay = new JButton(" convert into typ color ");
	private JButton convertToarraysAndImage = new JButton("image->array ->image");

	private JButton runTransformatdecX = new JButton("transformat x");
	private JButton runTransformatdecY = new JButton("transformat y");
	
	private JButton runTransformatdecYX = new JButton("transformat yx");
	private JButton syntheseX = new JButton(" syn x");
	
	private JButton syntheseY = new JButton(" syn y");
	private JButton syntheseYX = new JButton(" syn yx");
	private BufferedImage master;
	private BufferedImage test;
	
	private Wavelet2D wavelet2D;
	private int color;
	

	public Example() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout());
		frame = this;
		this.setTitle("Transform");
		this.setSize(500, 500);
		jpanel.add(labelColortyp);
		jpanel.add(convertColorTyp);
		jpanel.add(selectImage);
		jpanel.add(convertToGrayMay);
		jpanel.add(convertToarraysAndImage);
		jpanel.add(labeldeepDec);
		jpanel.add(jspindedcomositionDeep);
		jpanel.add(runTransformatdecX);
		jpanel.add(syntheseX);
		jpanel.add(runTransformatdecY);
		jpanel.add(syntheseY);
		jpanel.add(runTransformatdecYX);
		jpanel.add(syntheseYX);
		this.add(jpanel, BorderLayout.NORTH);
		this.add(panelPhoto, BorderLayout.CENTER);
		this.setBounds(10, 10, 500, 400);
		this.setVisible(true);
		initActions();
	}
	
	
	public static void main(String[] args) {
		new Example();
	}

	private void  initActions()
	{
		selectImage.addActionListener(e -> 
		{
			try 
			{
				FileDialog fd = new FileDialog(frame, "Choose a file",FileDialog.LOAD);
				fd.setDirectory("C:\\");
				fd.setFile("*.jpg");
				fd.setVisible(true);
				file = new File(fd.getDirectory(), fd.getFile());
				BufferedImage bi = ImageIO.read(file);
				master = bi;
				color=Integer.parseInt(convertColorTyp.getValue().toString());
				wavelet2D= new Wavelet2D(master, CONVERT_TYP.typ(color), new Daubechies2());
				panelPhoto.setBufferedImage(wavelet2D.getImageOrginal());
				panelPhoto.repaint();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});

		convertToGrayMay.addActionListener(e -> 
		{
				try 
				{
					if (wavelet2D != null) 
					{
						color=Integer.parseInt(convertColorTyp.getValue().toString());
						test = ImageUtils.makeImage(wavelet2D.getImageOrginal(),CONVERT_TYP.typ(color));
						panelPhoto.setBufferedImage(test);
						panelPhoto.repaint();
					}
					panelPhoto.repaint();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
		});
		
		convertToarraysAndImage.addActionListener(e -> 
		{
				try 
				{
					if (wavelet2D != null) 
					{
						color=Integer.parseInt(convertColorTyp.getValue().toString());
						int[][] tables = ImageUtils.convertIntoArrays(wavelet2D.getImageOrginal(), CONVERT_TYP.typ(color));
						test = ImageUtils.convertArrayIntoImage(tables,CONVERT_TYP.typ(color));
						panelPhoto.setBufferedImage(test);
						panelPhoto.repaint();
					}
					panelPhoto.repaint();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
		});
	
		runTransformatdecX.addActionListener(e -> 
		{
				try 
				{
					if (wavelet2D != null) 
					{
						wavelet2D.runDecompositionX(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
						panelPhoto.setBufferedImage(wavelet2D.getImageDec());
						panelPhoto.repaint();
					}
					panelPhoto.repaint();
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
		});
		
		
		runTransformatdecY.addActionListener(e -> 
		{
			try 
			{
				if (wavelet2D != null) 
				{
					wavelet2D.runDecompositionY(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
					panelPhoto.setBufferedImage(wavelet2D.getImageDec());
					panelPhoto.repaint();
				}
				panelPhoto.repaint();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
		
		runTransformatdecYX.addActionListener(e -> 
		{
			try
			{
				if (wavelet2D != null) 
				{
					wavelet2D.runDecompositionYX(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
					panelPhoto.setBufferedImage(wavelet2D.getImageDec());
					panelPhoto.repaint();
				}
				panelPhoto.repaint();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
		
		syntheseX.addActionListener(e -> 
		{
			try 
			{
				if (wavelet2D != null) 
				{
					wavelet2D.runSynthesisX(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
					panelPhoto.setBufferedImage(wavelet2D.getImageDec());
					panelPhoto.repaint();
				}
				panelPhoto.repaint();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
		
		syntheseY.addActionListener(e -> 
		{
			try 
			{
				if (wavelet2D != null) 
				{
					wavelet2D.runSynthesisY(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
					panelPhoto.setBufferedImage(wavelet2D.getImageDec());
					panelPhoto.repaint();
				}
				panelPhoto.repaint();
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		syntheseYX.addActionListener(e -> 
		{
			try 
			{
				if (wavelet2D != null) 
				{
					wavelet2D.runSynthesisYX(Integer.parseInt(jspindedcomositionDeep.getValue().toString()));
					panelPhoto.setBufferedImage(wavelet2D.getImageDec());
					panelPhoto.repaint();
				}
				panelPhoto.repaint();
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		});
	
	}
}
