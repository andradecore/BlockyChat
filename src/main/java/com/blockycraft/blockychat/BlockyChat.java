package com.blockycraft.blockychat;

import com.blockycraft.blockyfactions.api.BlockyFactionsAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration; // Import para o sistema de config antigo
import java.io.File; // Import para manipulacao de arquivos

public class BlockyChat extends JavaPlugin implements Listener {

    // Campos para armazenar as cores carregadas do config.yml
    private String colorPlayerName;
    private String colorFactionTag;
    private String colorMessage;

    @Override
    public void onEnable() {
        loadConfiguration(); // Carrega o config.yml
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("[BlockyChat] Plugin ativado com sucesso!");
        System.out.println("[BlockyChat] Integracao com BlockyFactions pronta.");
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
            config.setProperty("chat-colors.faction-tag", "&b");
            config.setProperty("chat-colors.message", "&7");
            config.save();
        }
        
        config.load(); // Garante que o arquivo seja lido do disco
        
        // Carrega as cores e as traduz para o formato do Bukkit
        this.colorPlayerName = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.player-name", "&f"));
        this.colorFactionTag = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.faction-tag", "&b"));
        this.colorMessage = ChatColor.translateAlternateColorCodes('&', config.getString("chat-colors.message", "&7"));
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage();
        
        String finalMessageColor = this.colorMessage; // Usa a cor do config como padrao

        // 1. Lógica do Greentext/Redtext (sobrescreve a cor da mensagem)
        if (message.startsWith(">") && message.length() > 1) {
            finalMessageColor = "" + ChatColor.GREEN; // Usamos "" para garantir que seja String
            message = message.substring(1).trim();
        } else if (message.startsWith("<") && message.length() > 1) {
            finalMessageColor = "" + ChatColor.RED;
            message = message.substring(1).trim();
        }
        
        event.setMessage(message);

        // 2. Lógica de formatação com as cores do config.yml
        String factionTag = BlockyFactionsAPI.getPlayerFactionTag(player.getName());
        
        String format;
        if (factionTag != null) {
            // Jogador COM facção
            String prefix = this.colorPlayerName + "%1$s [" + this.colorFactionTag + factionTag + this.colorPlayerName + "]: ";
            format = prefix + finalMessageColor + "%2$s";
        } else {
            // Jogador SEM facção
            String prefix = this.colorPlayerName + "%1$s: ";
            format = prefix + finalMessageColor + "%2$s";
        }
        
        event.setFormat(format);
    }
}