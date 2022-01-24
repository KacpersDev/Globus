package tech.azuriteservices.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import tech.azuriteservices.check.CheckType;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

public class FlyB extends PacketListenerAbstract {

    private final PlayerData data = new PlayerData();
    private final CheckType checkType = new CheckType();

    private Location from, to;
    private double deltaY, finalDeltaY, deltaX, finalDeltaX, airTicksEast, airTicksMinus, airJumpTick, finalPitch, airTicksSouth
            , minAirTicks, maxAirTicks;

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {

        Location location = event.getPlayer().getLocation();
        Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);

        if (event.getPacketId() == PacketType.Play.Client.FLYING
                || event.getPacketId() == PacketType.Play.Client.POSITION
                || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK
                || event.getPacketId() == PacketType.Play.Client.LOOK) {

            WrappedPacketInFlying wrappedPacketInFlying = new WrappedPacketInFlying(event.getNMSPacket());

            minAirTicks = 1.8;
            maxAirTicks = 2;

            if (data.isPlayerOnGround(event.getPlayer())) return;
            if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
            if (event.getPlayer().getAllowFlight()) return;
            if (data.isPlayerInLiquid(event.getPlayer())) return;

            if (from == null || to == null) {
                from = new Location(event.getPlayer().getWorld(), 0, 0, 0);
                to = new Location(event.getPlayer().getWorld(), 0, 0, 0);
            }

            from = to.clone();
            if (block.getType().equals(Material.ICE)) return;

            if (wrappedPacketInFlying.isPosition()) {
                to.setX(wrappedPacketInFlying.getPosition().getX());
                to.setY(wrappedPacketInFlying.getPosition().getY());
                to.setZ(wrappedPacketInFlying.getPosition().getZ());
                to.setPitch(wrappedPacketInFlying.getPitch());
            }
            finalDeltaX = (to.getX() - from.getX());
            finalDeltaY = (to.getY() - from.getY());
            if (data.isNearGround(to)) return;
            if ((finalDeltaX) > minAirTicks && (finalDeltaX) < maxAirTicks)  {
                new Alert(event.getPlayer(), "FLY", data.addVl(), "B", checkType.possible());
            }
        }
    }
}
