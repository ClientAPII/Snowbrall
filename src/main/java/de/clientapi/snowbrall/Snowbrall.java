package de.clientapi.snowbrall;

import de.clientapi.snowbrall.events.PlayerMoveListener;
import de.clientapi.snowbrall.events.SnowballHitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Snowbrall extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Snowbrall Plugin aktiviert!");

        // Event-Listener registrieren
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new SnowballHitListener(this), this);

        // Config laden
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Snowbrall Plugin deaktiviert!");
    }
}
