package com.lemonlightmc.moreplugins.commands.argumentsbase;

public interface MultiLiteralArgument<Impl extends Argument<String, ?>> {
	String[] getLiterals();
}
