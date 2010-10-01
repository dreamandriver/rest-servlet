package com.servlet.rest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

/**
 * Servlet��URL������
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public abstract class ServletFactory {
	private static HashBidiMap<String, HttpServlet> servletMap = null;

	protected ServletFactory(String path) {
		servletMap = new HashBidiMap<String, HttpServlet>();
	}

	/**
	 * ��������ʵ��
	 * 
	 * @param path
	 */
	protected abstract void init(String path);

	protected void initMap(Map<String, HttpServlet> resultMap) {
		if (resultMap == null) {
			return;
		}

		servletMap.putAll(resultMap);
	}

	/**
	 * ����url��ַ����ȡ��Ӧ��servletʵ��
	 * 
	 * @param oriUrl
	 * @return
	 */
	public HttpServlet getServletByUrl(String oriUrl) {
		Set<String> urlSet = servletMap.keySet();

		if (urlSet.contains(oriUrl)) {
			return servletMap.get(oriUrl);
		}

		Iterator<String> itern = urlSet.iterator();

		while (itern.hasNext()) {
			String url = itern.next();
			if (Pattern.matches(url, oriUrl)) {
				return servletMap.get(url);
			}
		}

		return null;
	}

	/**
	 * ����servletʵ������ȡ��Ӧ��url
	 * 
	 * @param servletInstance
	 * @return
	 */
	public String getUrlByServlet(HttpServlet servletInstance) {
		if (servletInstance == null) {
			return null;
		}

		return servletMap.reverse().get(servletInstance);
	}

	/**
	 * ����servletʵ������ȡ��������
	 * 
	 * @param servletInstance
	 * @return
	 */
	public String[] getUrlParametersByServlet(HttpServlet servletInstance,
			String oriUrl) {
		String url = getUrlByServlet(servletInstance);

		if (url == null || url.length() == 0) {
			return null;
		}

		// ʹ��������ʽ��ȡ ����

		String[] paramters = analyticsParameters(url, oriUrl);

		if (paramters != null && paramters.length > 0) {
			return paramters;
		}

		return null;
	}

	/**
	 * ��ȡurl�г��ֲ���
	 * 
	 * @param regUrl
	 * @param oriUrl
	 * @return
	 */
	private static String[] analyticsParameters(String regUrl, String oriUrl) {
		Pattern pattern = Pattern.compile(regUrl);

		Matcher matcher = pattern.matcher(oriUrl);

		String[] paramters = null;

		if (matcher.matches()) {
			int count = matcher.groupCount();
			paramters = new String[count];

			for (int i = 1; i <= count; i++) {
				paramters[i - 1] = matcher.group(i);
			}
		}

		return paramters;
	}

	/**
	 * ��̬���servlet��urlӳ���ϵ�����Ŵ˽ӿڣ���Ϊ�������������⣬������Ҫ�˷Ѿ���ȥ��鵱ǰʵ���Ƿ��Ѿ�����
	 * 
	 * @param url
	 * @param servletInstance
	 */
	private synchronized void register(String url, HttpServlet servletInstance) {
		if (url == null || url.length() == 0) {
			return;
		}
		
		if (servletInstance == null) {
			return;
		}
		
		servletMap.put(url, servletInstance);
	}
	
	/**
	 * ��̬���servlet��urlӳ���ϵ
	 * 
	 * @param url
	 * @param servletInstance
	 */
	public void register(String url, Class<HttpServlet> servletClass) {
		if (url == null || url.length() == 0) {
			return;
		}

		if (servletClass == null
				|| servletClass.getSuperclass() != HttpServlet.class) {
			return;
		}

		try {
			register(url, servletClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���servlet�Ͷ��URL��ע��ÿһ��URL����Ӧһ��������servletʵ��
	 * 
	 * @param urls
	 * @param servletInstance
	 */
	public void register(String[] urls, Class<HttpServlet> servletClass) {
		if (urls == null || urls.length == 0) {
			return;
		}

		if (servletClass == null
				|| servletClass.getSuperclass() != HttpServlet.class) {
			return;
		}

		for (String url : urls) {
			register(url, servletClass);
		}
	}

	/**
	 * ��̬ɾ��servlet��urlӳ���ϵ,����ע�⣬������ɾ��һ��HttpServletʵ��
	 * 
	 * @param servletInstance
	 */
	public synchronized void destory(HttpServlet servletInstance) {
		servletMap.removeValue(servletInstance);
	}
	
	/**
	 * �����ɾ����ǰservlet���ж�Ӧ��ϵ
	 * @param servletClass
	 */
	public synchronized void destory(Class<HttpServlet> servletClass) {
		servletMap.removeValueByClassPath(servletClass.getName());
	}

	/**
	 * ��̬ɾ��servlet��urlӳ���ϵ
	 * 
	 * @param servletInstance
	 */
	public synchronized void destory(String url) {
		servletMap.remove(url);
	}

	/**
	 * ��̬�������servlet��urlӳ���ϵ��
	 */
	public synchronized void clear() {
		servletMap.clear();
	}
}