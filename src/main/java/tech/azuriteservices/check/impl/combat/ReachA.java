package tech.azuriteservices.check.impl.combat;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import org.bukkit.entity.Entity;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

public class ReachA extends PacketListenerAbstract {

    private double playerDeltaX, playerDeltaZ, entityDeltaX, entityDeltaZ, deltaX, deltaZ
            , reachXTicks, reachZTicks;

    private final PlayerData data = new PlayerData();

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {

        reachXTicks = 3.7;
        reachZTicks = 3.7;

        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacketInUseEntity wrappedPacketInUseEntity = new WrappedPacketInUseEntity(event.getNMSPacket());
            WrappedPacketInUseEntity.EntityUseAction action = wrappedPacketInUseEntity.getAction();
            Entity entity = wrappedPacketInUseEntity.getEntity(event.getPlayer().getWorld());
            if (action == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {

                playerDeltaX = event.getPlayer().getLocation().getX();
                playerDeltaZ = event.getPlayer().getLocation().getZ();
                entityDeltaX = entity.getLocation().getX();
                entityDeltaZ = entity.getLocation().getZ();

                deltaX = ((playerDeltaX - entityDeltaX));
                deltaZ = ((playerDeltaZ - entityDeltaZ));

                if (deltaX > reachXTicks || deltaZ > reachZTicks) {
                    new Alert(event.getPlayer(), "Reach", data.addVl(), "A", "Possible");
                }
            }
        }

    }
}
