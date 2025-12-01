package com.lemonlightmc.moreplugins.commands.argumentsbase;

public interface LiteralArgument<Impl extends Argument<String, ?>> {
	String getLiteral();
}
