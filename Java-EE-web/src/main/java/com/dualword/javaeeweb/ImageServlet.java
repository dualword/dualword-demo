package com.dualword.javaeeweb;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/imageservlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ImageServlet() {
    	
    }
    
	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int w = 10, h = 10;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for( int i=0;i<w;i++){
			for (int j=0;j<h;j++){
				int a = (int)(Math.random() * 256);
				int r = (int)(Math.random() * 256);
				int g = (int)(Math.random() * 256);
				int b = (int)(Math.random() * 256);
				int p = (a<< 24) | (r<<16) | (g<<8) | b;
				image.setRGB(i, j, p);
			}
		}
		response.setContentType("image/png");
		ImageIO.write(image, "png", response.getOutputStream());

	}

}
