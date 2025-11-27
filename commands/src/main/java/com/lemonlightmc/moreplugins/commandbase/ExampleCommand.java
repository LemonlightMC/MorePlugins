package com.lemonlightmc.moreplugins.commandbase;

import com.lemonlightmc.moreplugins.commands.arguments.DoubleArgument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.SuggestionInfo;

import java.util.List;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ExampleCommand extends SimpleCommand {

  public ExampleCommand() {
    super("example");
    withShortDescription("An example command");
    withPermission("moreplugins.example");
    withUsage("&cUsage: /example");
    withAliases();
    withHelp(List.of("&e/example &7- &fAn example command"));
    withArguments(new DoubleArgument("amount").applySuggestions((final SuggestionInfo<CommandSender> _) -> {
      return List.of("");
    }));
    withExecutor(ExampleCommand::executes);
    withExecutor(ExampleCommand::executesConsole);
  }

  public static void executes(final Player sender, final CommandArguments args) {
  }

  public static void executesConsole(
      final ConsoleCommandSender sender,
      final CommandArguments args) throws CommandException {
  }
}
