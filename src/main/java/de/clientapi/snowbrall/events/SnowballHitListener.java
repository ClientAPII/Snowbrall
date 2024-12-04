package de.clientapi.snowbrall.events;

import de.clientapi.snowbrall.Snowbrall;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

public class SnowballHitListener implements Listener {

    private final Snowbrall plugin;
    private final Map<Player, Integer> playerHearts = new HashMap<>();

    public SnowballHitListener(Snowbrall plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        // Prüfen, ob das Projektil ein Schneeball ist
        if (event.getEntity() instanceof Snowball snowball) {
            // Prüfen, ob das Ziel ein Spieler ist
            Entity hitEntity = event.getHitEntity();
            if (hitEntity instanceof Player victim) {
                // Prüfen, ob der Werfer ein Spieler ist
                if (snowball.getShooter() instanceof Player attacker) {
                    // Arena-Koordinaten aus der Config laden
                    Location corner1 = new Location(victim.getWorld(),
                            plugin.getConfig().getInt("arena.corner1.x"),
                            plugin.getConfig().getInt("arena.corner1.y"),
                            plugin.getConfig().getInt("arena.corner1.z"));

                    Location corner2 = new Location(victim.getWorld(),
                            plugin.getConfig().getInt("arena.corner2.x"),
                            plugin.getConfig().getInt("arena.corner2.y"),
                            plugin.getConfig().getInt("arena.corner2.z"));

                    // Überprüfen, ob beide Spieler in der Arena sind
                    if (isInArena(victim.getLocation(), corner1, corner2) &&
                            isInArena(attacker.getLocation(), corner1, corner2)) {
                        // Leben des getroffenen Spielers reduzieren
                        int hearts = playerHearts.getOrDefault(victim, 3) - 1;
                        playerHearts.put(victim, hearts);

                        // Actionbar aktualisieren
                        String actionBar = "§4";
                        for (int i = 0; i < hearts; i++) actionBar += "❤";
                        for (int i = 0; i < (3 - hearts); i++) actionBar += "§c✖";
                        victim.sendActionBar(actionBar);

                        // Spieler teleportieren, wenn keine Herzen mehr
                        if (hearts <= 0) {
                            playerHearts.put(victim, 3);
                            Location respawn = new Location(victim.getWorld(),
                                    plugin.getConfig().getInt("respawn.x"),
                                    plugin.getConfig().getInt("respawn.y"),
                                    plugin.getConfig().getInt("respawn.z"));
                            victim.teleport(respawn);
                        }
                    }
                }
            }
        }
    }

    private boolean isInArena(Location loc, Location corner1, Location corner2) {
        double minX = Math.min(corner1.getX(), corner2.getX());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        return loc.getX() >= minX && loc.getX() <= maxX &&
                loc.getY() >= minY && loc.getY() <= maxY &&
                loc.getZ() >= minZ && loc.getZ() <= maxZ;
    }
}
