package com.lemonlightmc.moreplugins.commands.executors;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public record ExecutionInfo<S extends CommandSender>(CommandSource<S> source,
                CommandArguments args) {

        public S sender() {
                return source.sender();
        }

        public Location location() {
                return source.location();
        }

        public <T extends CommandSender> ExecutionInfo<T> copyFor(final T newSender) {
                return new ExecutionInfo<>(source.copyFor(newSender), args);
        }

        public ExecutionInfo<S> copy() {
                return new ExecutionInfo<>(source, args);
        }
}