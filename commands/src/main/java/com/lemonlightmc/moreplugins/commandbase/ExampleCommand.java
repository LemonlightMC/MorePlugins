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
    super();
    setLabel("example");
    setDescription("An example command");
    withPermission("moreplugins.example");
    setUsageMessage("&cUsage: /example");
    withAliases();
    setHelp(List.of("&e/example &7- &fAn example command"));
    withArguments(new DoubleArgument("amount").applySuggestions((SuggestionInfo<CommandSender> _) -> {
      return List.of("");
    }));
    withExecutor(ExampleCommand::executes);
    withExecutor(ExampleCommand::executesConsole);
  }

  public static void executes(Player sender, CommandArguments args) {
  }

  public static void executesConsole(
      ConsoleCommandSender sender,
      CommandArguments args) throws CommandException {
  }
}
