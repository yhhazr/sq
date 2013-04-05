package com.sz7road.web.common.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sz7road.web.common.util.CaptchaCode;

public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("image/jpeg");
		CaptchaCode image = new CaptchaCode();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream os = null;
		try {
			BufferedImage img = image.creatImage();
			request.getSession().setAttribute(KAPTCHA_SESSION_KEY, image.getSRand());
			os = response.getOutputStream();
			ImageIO.write(img, "JPEG", os);
			os.flush();

		} catch (Exception e) {
			System.out.println("errors:" + e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
