package tech.azuriteservices.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class PlayerData {

    private boolean onGround;
    private boolean inLiquid;
    private int vl;

    public boolean isPlayerOnGround(Player player) {

        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (!block.getType().equals(Material.AIR)) {
            onGround = true;
        } else {
            onGround = false;
        }
        return onGround;
    }


    public boolean isNearGround(Location location) {

        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (location.clone().add(x, -0.5001, z).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPlayerInLiquid(Player player){

        Location location = player.getLocation();
        location.setY(location.getY() - 1);
        if (location.getBlock().getType() == Material.WATER && location.getBlock().getType() == Material.LAVA) {
            inLiquid = true;
            onGround = true;
        }

        return inLiquid;
    }

    public int getVl() {
        return vl;
    }

    public Integer addVl(){
        return vl = vl + 1;
    }
}