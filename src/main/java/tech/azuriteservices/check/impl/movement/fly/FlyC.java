package tech.azuriteservices.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.GameMode;
import org.bukkit.Location;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

public class FlyC extends PacketListenerAbstract {

    private final PlayerData data = new PlayerData();

    private Location from, to;
    private double finalDeltaZ, minAirTicks, maxAirTicks, limitAirTicks;

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event){

        if (event.getPacketId() == PacketType.Play.Client.FLYING
        || event.getPacketId() == PacketType.Play.Client.POSITION
        || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK
        || event.getPacketId() == PacketType.Play.Client.LOOK) {

            WrappedPacketInFlying wrappedPacketInFlying = new WrappedPacketInFlying(event.getNMSPacket());

            if ((data.isPlayerOnGround(event.getPlayer()))) return;
            if (data.isPlayerInLiquid(event.getPlayer())) return;
            if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
            if (event.getPlayer().getAllowFlight()) return;

            if (from == null || to == null) {
                from = new Location(event.getPlayer().getWorld(), 0,0,0);
                to = new Location(event.getPlayer().getWorld(),0,0,0);
            }

            minAirTicks = 1.8;
            maxAirTicks = 1.9;
            limitAirTicks = 2.0;

            from = to.clone();


            if (wrappedPacketInFlying.isPosition()) {
                to.setX(wrappedPacketInFlying.getX());
                to.setY(wrappedPacketInFlying.getY());
                to.setZ(wrappedPacketInFlying.getZ());
            }

            finalDeltaZ = (to.getZ() - from.getZ());
            if ((finalDeltaZ) > minAirTicks && (finalDeltaZ) > maxAirTicks && (finalDeltaZ) < limitAirTicks) {
                new Alert(event.getPlayer(), "FLY", data.addVl(), "C");
            }
        }
    }
}
