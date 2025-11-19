package com.blockycraft.blockychat;

import com.blockycraft.blockygroups.api.BlockyGroupsAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import java.io.File;

public class BlockyChat extends JavaPlugin implements Listener {

    private String colorPlayerName;
    private String colorGroupTag;
    private String colorMessage;

    @Override
    public void onEnable() {
        loadConfiguration();
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("[BlockyChat] Plugin ativado com sucesso!");
        System.out.println("[BlockyChat] Integracao com BlockyGroups pronta.");
    }

    @Override
    public void onDisable() {
        System.out.println("[BlockyChat] Plugin desativado.");
    }

    private void loadConfiguration() {
        File configFile = new File(getDataFolder(), "config.yml");
        Configuration config = getConfiguration();

        if (!configFile.exists()) {
            System.out.println("[BlockyChat] Criando config.yml padrao...");
            config.setProperty("chat-colors.player-name", "&f");
            config.setProperty("chat-colors.group-tag", "&b");
            config.setProperty("chat-colors.message", "&7");
            config.save();
        }

        config.load();

        this.colorPlayerName = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.player-name", "&f"));
        this.colorGroupTag = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.group-tag", "&b"));
        this.colorMessage = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.message", "&7"));
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage();

        String finalMessageColor = this.colorMessage;

        // Lógica do Greentext/Redtext (preserva o caractere inicial)
        if (message.startsWith(">") && message.length() > 1) {
            finalMessageColor = "" + ChatColor.GREEN;
            // NÃO remove o ">" da mensagem
        } else if (message.startsWith("<") && message.length() > 1) {
            finalMessageColor = "" + ChatColor.RED;
            // NÃO remove o "<" da mensagem
        }

        // Mantém a mensagem original intacta
        event.setMessage(message);

        // Lógica de formatação com as cores do config.yml
        String groupTag = BlockyGroupsAPI.getPlayerGroupTag(player.getName());

        String format;
        if (groupTag != null) {
            String prefix = this.colorPlayerName + "%1$s [" + this.colorGroupTag + groupTag + this.colorPlayerName + "]: ";
            format = prefix + finalMessageColor + "%2$s";
        } else {
            String prefix = this.colorPlayerName + "%1$s: ";
            format = prefix + finalMessageColor + "%2$s";
        }

        event.setFormat(format);
    }
}