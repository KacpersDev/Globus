package tech.azuriteservices.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import tech.azuriteservices.check.CheckType;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

public class FlyD extends PacketListenerAbstract {

    private final CheckType checkType = new CheckType();
    private final PlayerData data = new PlayerData();
    private Location from, to;
    private double minAirTicks, limitAirTicks, finalDeltaZ;

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event){

        Location location = event.getPlayer().getLocation();

        if (event.getPacketId() == PacketType.Play.Client.FLYING
        || event.getPacketId() == PacketType.Play.Client.LOOK
        || event.getPacketId() == PacketType.Play.Client.POSITION
        || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK) {

            WrappedPacketInFlying wrappedPacketInFlying = new WrappedPacketInFlying(event.getNMSPacket());

            if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
            if (event.getPlayer().getAllowFlight()) return;
            if (data.isPlayerOnGround(event.getPlayer())) return;
            if (data.isPlayerInLiquid(event.getPlayer())) return;

            if (from == null || to == null) {
                from = new Location(event.getPlayer().getWorld(), 0,0,0);
                to = new Location(event.getPlayer().getWorld(), 0,0,0);
            }

            minAirTicks = -1.7;
            limitAirTicks = -2.1;

            from = to.clone();

            if (wrappedPacketInFlying.isPosition()) {
                to.setX(wrappedPacketInFlying.getX());
                to.setY(wrappedPacketInFlying.getY());
                to.setZ(wrappedPacketInFlying.getZ());
            }

            finalDeltaZ = (to.getZ() - from.getZ());
            if (data.isNearGround(to)) return;
            if ((finalDeltaZ) < minAirTicks && (finalDeltaZ) > limitAirTicks) {
                new Alert(event.getPlayer(), "FLY", data.addVl(), "D", checkType.possible());
            }
        }
    }
}
