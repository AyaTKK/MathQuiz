package com.hamid.mathquiz;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MathQuiz extends JavaPlugin {

    private QuizManager quizManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.quizManager = new QuizManager(this);
        
        getServer().getPluginManager().registerEvents(new ChatListener(this, quizManager), this);
        quizManager.startScheduler();

        getLogger().info("MathQuiz plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        if (quizManager != null) {
            quizManager.stopScheduler();
        }
        getLogger().info("MathQuiz plugin has been disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mathquiz")) return false;

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            quizManager.stopScheduler();
            quizManager.startScheduler();
            sender.sendMessage(ChatColor.GREEN + "MathQuiz configuration reloaded successfully!");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {
            quizManager.generateNewQuestion();
            sender.sendMessage(ChatColor.GREEN + "A new quiz question has been sent!");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Usage: /mathquiz [reload|start]");
        return true;
    }
}
