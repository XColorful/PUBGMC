package dev.toma.pubgmc.common.commands.core;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractNode implements CommandNode {

    private final String key;
    private final CommandNodeExecutor executor;
    private final SuggestionProvider suggestionProvider;
    private final Map<String, CommandNode> children;

    public AbstractNode(String key, CommandNodeExecutor executor, SuggestionProvider suggestionProvider, Map<String, CommandNode> children) {
        this.key = key;
        this.executor = executor;
        this.suggestionProvider = suggestionProvider;
        this.children = children;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public CommandNodeExecutor getExecutor() {
        return executor;
    }

    @Override
    public Map<String, CommandNode> children() {
        return children;
    }

    @Override
    public List<String> suggest(MinecraftServer server, ICommandSender sender, @Nullable BlockPos targetPos) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add(key);
        if (suggestionProvider != null) {
            suggestions.addAll(suggestionProvider.suggest(new SuggestionProvider.Context(server, sender, targetPos)));
        }
        return suggestions;
    }

    protected SuggestionProvider getSuggestionProvider() {
        return suggestionProvider;
    }

    public static abstract class AbstractBuilder<B extends AbstractBuilder<?>> implements CommandNodeProvider {

        protected final String key;
        protected final Map<String, CommandNode> children;
        protected CommandNodeExecutor executor;
        protected SuggestionProvider suggestionProvider;

        protected AbstractBuilder(String key) {
            this.key = key;
            this.children = new HashMap<>();
        }

        public abstract B self();

        public B executes(CommandNodeExecutor executor) {
            this.executor = executor;
            return self();
        }

        public B node(CommandNodeProvider provider) {
            CommandNode node = provider.getNode();
            children.put(node.key(), node);
            return self();
        }

        public B suggests(SuggestionProvider provider) {
            this.suggestionProvider = provider;
            return self();
        }
    }
}
