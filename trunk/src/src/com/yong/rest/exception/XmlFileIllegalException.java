package com.yong.rest.exception;

/**
 * ������Ҫɨ��İ������쳣
 * @author y.nie
 * @date 2010-9-16
 * @version 1.0
 */
public class XmlFileIllegalException extends IllegalArgumentException {
	private static final long serialVersionUID = 6324279375643767512L;

	public XmlFileIllegalException() {
		super();
	}

	public XmlFileIllegalException(String exceptionStr) {
		super(exceptionStr);
	}
}