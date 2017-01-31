package bftcore.enumeration;

public enum ValidationMessageEnum {
	PARENT_NOT_FOUND("Parent not found"), VALUE_IS_MISSING("Value is Missing"), CHILD_NOT_FOUND("Child not found"), CHILD_IS_PARENT("The child is the parent"), CHILD_IS_ANCESTOR("The child is its own ancestor");

	private String message;

	private ValidationMessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
