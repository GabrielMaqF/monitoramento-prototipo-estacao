package com.maqfiltros.sensors_contract.services.exceptions;

public class InvalidValueException  extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public InvalidValueException(Object valor) {
		super("Invalid value. Value: " + valor);
	}

}
