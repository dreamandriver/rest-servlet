package com.yong.rest.exception;

/**
 * ������Ҫɨ��İ������쳣
 * @author y.nie
 * @date 2010-9-16
 * @version 1.0
 */
public class ScanPackageIllegalException extends IllegalArgumentException {
	private static final long serialVersionUID = 6324279375643767512L;

	public ScanPackageIllegalException() {
		super();
	}

	public ScanPackageIllegalException(String exceptionStr) {
		super(exceptionStr);
	}
}