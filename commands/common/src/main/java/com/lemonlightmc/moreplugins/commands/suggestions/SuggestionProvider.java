package com.lemonlightmc.moreplugins.commands.suggestions;

import java.util.List;

public interface SuggestionProvider<S> {

  public List<String> getSuggestions(SuggestionInfo<S> info);
}
