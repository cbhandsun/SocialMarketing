/**********************************************************************
 * FILE : BarcodeUtil.java
 * CREATE DATE : Mar 9, 2010
 * DESCRIPTION :
 *
 * CHANGE HISTORY LOG
 *---------------------------------------------------------------------
 * NO.|    DATE    |     NAME     |     REASON     | DESCRIPTION
 *---------------------------------------------------------------------
 *          
 **********************************************************************
 */
package com.socialmarketing.util;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarcodeUtil {
	
	public static void generateCode128_JPG_Stream( OutputStream out , String barCode){
		try {
            //Create the barcode bean
        	Code128Bean bean = new Code128Bean();
            
            final int dpi = 150;
            
            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(2.0f / dpi)); //makes the narrow bar 
                                                             //width exactly one pixel
            bean.setBarHeight(10);
            bean.doQuietZone(false);
            
            try {
                //Set up the canvas provider for monochrome JPEG output 
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            
                //Generate the barcode
                bean.generateBarcode(canvas, barCode);
            
                //Signal end of generation
                canvas.finish();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
