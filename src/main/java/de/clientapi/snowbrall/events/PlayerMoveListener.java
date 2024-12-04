package de.clientapi.snowbrall.events;

import de.clientapi.snowbrall.Snowbrall;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private final Snowbrall plugin;

    public PlayerMoveListener(Snowbrall plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location loc = event.getPlayer().getLocation();
        Location corner1 = new Location(event.getPlayer().getWorld(),
                plugin.getConfig().getInt("arena.corner1.x"),
                plugin.getConfig().getInt("arena.corner1.y"),
                plugin.getConfig().getInt("arena.corner1.z"));

        Location corner2 = new Location(event.getPlayer().getWorld(),
                plugin.getConfig().getInt("arena.corner2.x"),
                plugin.getConfig().getInt("arena.corner2.y"),
                plugin.getConfig().getInt("arena.corner2.z"));

        if (isInArena(loc, corner1, corner2)) {
            event.getPlayer().sendActionBar("§4❤❤❤");
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
