package com.yong.rest.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ServletUtils {	
	
	public static Boolean isFromMobile(HttpServletRequest request) {
		return isFromMobile(request.getHeader("User-Agent"));
	}
	
	public static Boolean isFromMobile(String userAgent) {
		if (userAgent == null || userAgent.length() < 1) {
			return false;
		}

		userAgent = userAgent.toUpperCase();

		if (userAgent.indexOf("NOKIA") != -1
				|| userAgent.indexOf("SYMBIAN") != -1
				|| userAgent.indexOf("MOTO") != -1
				|| userAgent.indexOf("MOBI") != -1
				|| userAgent.indexOf("UCWEB") != -1
				|| userAgent.indexOf("IPHONE") != -1
				|| userAgent.indexOf("CMCC") != -1
				|| userAgent.indexOf("VND") != -1
				|| userAgent.indexOf("WAP") != -1
				|| userAgent.indexOf("BLACKBERRY") != -1
				|| userAgent.indexOf("SYMBIANOS") != -1
				|| userAgent.indexOf("MIDP") != -1
				|| userAgent.indexOf("PPC") != -1) {
			return true;
		}

		return false;
	}
	
//	public static String getRequestId(HttpServletRequest request) {
//		String postStr = StringUtils.substringAfter(request.getRequestURI(), request.getServletPath());
//		
//		if(postStr == null || postStr.length() == 0)
//			return null;
//		
//		return  postStr.substring(1);
//	}
	
	public static String string2ISO(String str) throws UnsupportedEncodingException {
		return new String(str.getBytes(), "ISO8859-1");
	}
	public static String string2UTF8(String str) throws UnsupportedEncodingException {
		return new String(str.getBytes(), "UTF-8");
	}

	/**
	 * �ṩһ�����صĹ�������
	 * 
	 * @param filePath �ļ��ķ���·��
	 * @param fileName Ҫ��ʾ���ļ�����
	 */
	public static void downloadFile(String filePath, String fileName,
			HttpServletResponse response) {
		try {
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			// �����ص��ļ����ƽ��б��룬�������������������
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO-8859-1"));
			int len = -1;
			OutputStream out = response.getOutputStream();
			while ((len = is.read()) != -1) {
				out.write(len);
			}
			is.close();
			out.flush();
			out.close();
		} catch (FileNotFoundException notFound) {
			try {
				response.setContentType("text/html; charset=UTF-8");				
				Writer out = response.getWriter();
				out.write("����������ļ������ڣ�");
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException ioe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���û���������ת��Ϊ�ļ��������ڴ���ϵͳ��
	 * 
	 * @param request
	 * @param response
	 * @param fileSavePath
	 *            Ҫ����Ϊ�ļ���·����eg��c:/da/ht.htm
	 * @param dispatcherPath
	 *            ��ǰServlet��Ӧ��RequestDispatcherת��·��
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void servlet2FileAction(HttpServletRequest request, HttpServletResponse response, String fileSavePath,
			String dispatcherPath) throws ServletException, IOException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();

		final ServletOutputStream stream = new ServletOutputStream() {
			public void write(byte[] data, int offset, int length) {
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException {
				os.write(b);
			}
		};

		// ���������Ҫ�����޸ĳ���Ҫ����
		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os,"UTF-8"));
		HttpServletResponse rep = new HttpServletResponseWrapper(response) {
			public ServletOutputStream getOutputStream() {
				return stream;
			}

			public PrintWriter getWriter() {
				return pw;
			}
		};
		
		RequestDispatcher rd = request.getRequestDispatcher(dispatcherPath);		
		rd.include(request, rep);
		pw.flush();		
		pw.close();
		
		// ��jsp���������д��ָ��·����htm�ļ���
		FileOutputStream fos = new FileOutputStream(fileSavePath);
		os.writeTo(fos);
		fos.close();
		// add by now
		fos.flush();		
		os.close();		

		rd = null;
		fos = null;
	}

	public RequestDispatcher saveDataPaperInfo(HttpServletRequest request, HttpServletResponse response, String url)
			throws ServletException, IOException {
		return request.getRequestDispatcher(url);
	}
}