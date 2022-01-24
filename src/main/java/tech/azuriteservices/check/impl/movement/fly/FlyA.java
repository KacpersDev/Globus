package tech.azuriteservices.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.GameMode;
import org.bukkit.Location;
import tech.azuriteservices.check.CheckType;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

public class FlyA extends PacketListenerAbstract {

    private final CheckType checkType = new CheckType();
    private final PlayerData data = new PlayerData();
    private Location from, to;
    private double lastDeltaY, deltaY, airTicks, jumpTicks, velocityTicks;

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {

        Location location = event.getPlayer().getLocation();

        if (data.isPlayerOnGround(event.getPlayer())) return;
        if (event.getPacketId() == PacketType.Play.Client.FLYING
                || event.getPacketId() == PacketType.Play.Client.POSITION
                || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK
                || event.getPacketId() == PacketType.Play.Client.LOOK) {

            airTicks = 0.5;
            jumpTicks = 0.5;
            velocityTicks = -0.9;

            if (data.isPlayerOnGround(event.getPlayer())) return;
            if (event.getPlayer().getVelocity().getY() > velocityTicks) return;
            if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
            if (event.getPlayer().getAllowFlight()) return;
            if (data.isPlayerInLiquid(event.getPlayer())) return;

            WrappedPacketInFlying wrappedPacketInFlying = new WrappedPacketInFlying(event.getNMSPacket());

            if (from == null || to == null) {
                from = new Location(event.getPlayer().getWorld(), 0, 0, 0);
                to = new Location(event.getPlayer().getWorld(), 0, 0, 0);
            }

            from = to.clone();

            if (wrappedPacketInFlying.isPosition()) {
                to.setX(wrappedPacketInFlying.getPosition().getX());
                to.setY(wrappedPacketInFlying.getPosition().getY());
                to.setZ(wrappedPacketInFlying.getPosition().getZ());
            }

            lastDeltaY = (to.getY() - from.getY());
            deltaY = lastDeltaY;
            if (data.isNearGround(to)) return;
            if ((lastDeltaY) > airTicks && (lastDeltaY) > jumpTicks) {
                new Alert(event.getPlayer(), "FLY", data.addVl(), "A", checkType.possible());
            }
        }
    }
}
