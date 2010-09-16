package com.yong.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yong.rest.annotation.RestSupport;

@RestSupport("/book/*")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = -983058406804L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();

		out.write("we going to show one book , here ...");

		out.flush();
	}

	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("这里请求删除一本书...");
		PrintWriter out = response.getWriter();

		out.write("we going to delete the book ...");

		out.flush();
	}
}
