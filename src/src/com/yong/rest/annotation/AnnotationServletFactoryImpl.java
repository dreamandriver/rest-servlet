package com.yong.rest.annotation;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.yong.rest.ServletFactory;
import com.yong.rest.exception.ScanPackageIllegalException;

/**
 * ��ȡע�⣬���ServletFactory��������Դ
 *
 * @author y.nie
 * @date 2010-9-16
 * @version 1.0
 */
public class AnnotationServletFactoryImpl extends ServletFactory {

	public AnnotationServletFactoryImpl(String scanPackage) {
		super(scanPackage);

		if (scanPackage == null || scanPackage.length() == 0) {
			throw new ScanPackageIllegalException("Ҫɨ��İ�·������Ϊ��");
		}

		if(scanPackage.indexOf(',') == -1){
			this.init(scanPackage);
		}else{
			String [] packages = scanPackage.split(",");
			for(String pack : packages){
				if(pack == null || pack.length() == 0){
					continue;
				}

				this.init(pack);
			}
		}
	}

	protected void init(String scanPackage) {
		Map<String, HttpServlet> resultMap = AnnonationHelper
				.readServletClasses(scanPackage);

		super.initMap(resultMap);
	}
}