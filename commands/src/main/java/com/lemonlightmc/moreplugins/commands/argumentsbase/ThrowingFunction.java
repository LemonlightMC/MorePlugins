package com.lemonlightmc.moreplugins.commands.argumentsbase;

import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;

public interface ThrowingFunction<T, R> {

  R apply(T t) throws CommandSyntaxException;

}
