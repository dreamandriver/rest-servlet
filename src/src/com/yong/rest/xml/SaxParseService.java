package com.yong.rest.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxParseService extends DefaultHandler {
	private Map<String, String> hashMap = null;
	private String preTag = null;// �����Ǽ�¼����ʱ����һ���ڵ�����
	private ServletUrl book;

	private static final String SERVLET_NODE = "servlet";
	private static final String CLASS_NODE = "class";
	private static final String URL_NODE = "url";

	public Map<String, String> getResult(InputStream xmlStream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlStream, this);

		return getMap();
	}

	public Map<String, String> getMap() {
		return hashMap;
	}

	@Override
	public void startDocument() throws SAXException {
		hashMap = new HashMap<String, String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (SERVLET_NODE.equals(qName)) {
			book = new ServletUrl();
		}

		preTag = qName;// �����ڽ����Ľڵ����Ƹ���preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (SERVLET_NODE.equals(qName)) {
			
			if(!book.getUrls().isEmpty()){			
				for(String url : book.getUrls()){
					hashMap.put(url, book.getPath());
				}
			}

			book = null;
		}

		preTag = null;
		/**
		 * ����������ʱ��Ϊ�ա��������Ҫ�����磬��ͼ�л�3��λ�ý����󣬻�����������
		 * ��������ﲻ��preTag��Ϊnull������startElement(....)������preTag��ֵ����book�����ĵ�˳�����ͼ
		 * �б��4��λ��ʱ����ִ��characters(char[] ch, int start, int
		 * length)�����������characters(....)��
		 * ���ж�preTag!=null����ִ��if�жϵĴ��룬�����ͻ�ѿ�ֵ��ֵ��book���ⲻ��������Ҫ�ġ�
		 */
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if (CLASS_NODE.equals(preTag)) {
				book.setPath(content);
			} else if (URL_NODE.equals(preTag)) {
				book.setUrl(content);
			}
		}
	}
	
	public static void main(String [] args) throws Exception{
		SaxParseService sax = new SaxParseService();  
        InputStream input = SaxParseService.class.getClassLoader().getResourceAsStream("servlets.xml");  
        Map<String, String> hashMap = sax.getResult(input);
        
        if(hashMap == null){
        	System.out.println("xml is null");
        	return;
        }
        
        for(Map.Entry<String, String> entry : hashMap.entrySet()){
        	System.out.println(entry.getValue() + ":" + entry.getKey());
        }
	}
}

class ServletUrl {
	private String path;
	private List<String> urls;
	
	public ServletUrl(){
		urls = new ArrayList<String>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrl(String url) {
		this.urls.add(url);
	}
}