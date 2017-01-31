package bftcore.exception;

import bftcore.enumeration.ValidationMessageEnum;

public class ValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String elementName;

	public ValidationException(ValidationMessageEnum validationMessageEnum,
			String elementName) {
		super(validationMessageEnum.getMessage()+" ["+elementName+"]");
		this.elementName = elementName;
	}

	public String getElementName() {
		return elementName;
	}

}
