package com.lemonlightmc.moreplugins.example;

import com.lemonlightmc.moreplugins.commands.SimpleCommand;
import com.lemonlightmc.moreplugins.commands.arguments.NumberArguments.*;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.executors.CommandSource;
import com.lemonlightmc.moreplugins.commands.suggestions.SuggestionInfo;
import com.lemonlightmc.moreplugins.commands.suggestions.Suggestions;

import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ExampleCommand extends SimpleCommand {

  public ExampleCommand() {
    super("example");
    withAliases("example2");
    withShortDescription("An example command");
    withFullDescription("An example command that does random stuff!");
    withUsage("&cUsage: /example <random|player>");
    withPermission("moreplugins.example");
    // withHelp(List.of("&e/example &7- &fAn example command"));
    withArguments(new DoubleArgument("amount").withSuggestions((SuggestionInfo<CommandSender> _) -> {
      return List.of("64", "128");
    }));
    withArguments(new DoubleArgument("amount2").withSuggestions("1", "2", "3"));
    withArguments(new DoubleArgument("amount3").withSuggestions(Suggestions.from("1", "2")));
    executesPlayer(ExampleCommand::executes2);
    executesConsole(ExampleCommand::executesConsole2);
  }

  public static void executes2(final CommandSource<Player> sender, final CommandArguments args)
      throws CommandException {
  }

  public static void executesConsole2(
      final CommandSource<ConsoleCommandSender> sender,
      final CommandArguments args) throws CommandException {
  }
}
