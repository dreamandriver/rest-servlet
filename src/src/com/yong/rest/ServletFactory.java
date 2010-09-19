package com.yong.rest;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

/**
 * Servlet��URL��������
 * 
 * @author yong
 * @date 2010-9-19
 * @version 1.0
 */
public abstract class ServletFactory {
	private static MultiHashBidiMap<String, HttpServlet> servletMap = null;

	protected ServletFactory(String path){
		servletMap = new MultiHashBidiMap<String, HttpServlet>();
	}

	/**
	 * ��������ʵ��
	 *
	 * @param path
	 */
	protected abstract void init(String path);

	protected void initMap(Map<String, HttpServlet> resultMap){
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
	public Collection<String> getUrlByServlet(HttpServlet servletInstance) {
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
		Collection<String> urls = getUrlByServlet(servletInstance);

		if (urls == null || urls.isEmpty()) {
			return null;
		}

		// ʹ���������ʽ��ȡ ����

		for(String url : urls){
			String [] paramters =  analyticsParameters(url, oriUrl);

			if(paramters != null && paramters.length > 0){
				return paramters;
			}
		}

		return null;
	}

	/**
	 * ��ȡurl�г��ֲ���
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
	 * ��̬����servlet��urlӳ���ϵ
	 * @param url
	 * @param servletInstance
	 */
	public synchronized void add(String url, HttpServlet servletInstance) {
		if (url == null || url.length() == 0) {
			return;
		}

		if (servletInstance == null) {
			return;
		}

		servletMap.put(url, servletInstance);
	}

	/**
	 * ��̬ɾ��servlet��urlӳ���ϵ
	 * @param servletInstance
	 */
	public synchronized void remove(HttpServlet servletInstance) {
		servletMap.removeValueByClassPath(servletInstance.getClass().getName());
	}
	
	/**
	 * ��̬ɾ��servlet��urlӳ���ϵ
	 * @param servletInstance
	 */
	public synchronized void remove(String url) {
		servletMap.removeKey(url);
	}

	/**
	 * ��̬�������servlet��urlӳ���ϵ��
	 */
	public synchronized void clear(){
		servletMap.clear();
	}
}