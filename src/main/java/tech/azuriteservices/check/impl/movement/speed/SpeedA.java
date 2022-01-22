package tech.azuriteservices.check.impl.movement.speed;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.flying.WrappedPacketInFlying;
import org.bukkit.Location;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tech.azuriteservices.data.Alert;
import tech.azuriteservices.data.PlayerData;

import java.util.Collection;

public class SpeedA extends PacketListenerAbstract {

    private final PlayerData data = new PlayerData();
    private Location from, to;
    private double deltaX, minDefaultSpeedTicks, maxDefaultSpeedTicks, jumpSpeedTicks, maxVelocityTicks, minVelocityTicks, finalVelocityTicks
            , finalMaxVelocityTicks;

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event){

        if (event.getPacketId() == PacketType.Play.Client.FLYING
        || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK
        || event.getPacketId() == PacketType.Play.Client.LOOK
        || event.getPacketId() == PacketType.Play.Client.POSITION) {

            WrappedPacketInFlying wrappedPacketInFlying = new WrappedPacketInFlying(event.getNMSPacket());

            if (from == null || to == null) {
                from = new Location(event.getPlayer().getWorld(),0,0,0);
                to = new Location(event.getPlayer().getWorld(),0,0,0);
            }

            minDefaultSpeedTicks = 0.2;
            maxDefaultSpeedTicks = 0.3;
            jumpSpeedTicks = 3.4;
            maxVelocityTicks = -0.38;
            minVelocityTicks = -0.30;
            double velo = event.getPlayer().getVelocity().getY();

            from = to.clone();
            if (!data.isPlayerOnGround(event.getPlayer())) return;
            // -0.373453453462 < ^
            if (event.getPlayer().getVelocity().getY() > maxVelocityTicks && (event.getPlayer().getVelocity().getY() < minVelocityTicks)) return;
            if (event.getPlayer().getVelocity().getY() < velo || event.getPlayer().getVelocity().getY() > velo) {
                return;
            }

            if (wrappedPacketInFlying.isOnGround()) {
                to.setX(wrappedPacketInFlying.getX());
                to.setY(wrappedPacketInFlying.getY());
                to.setZ(wrappedPacketInFlying.getZ());
            }

            deltaX = ((to.getX()) - from.getX());
            if (deltaX == to.getX()) return;
            event.getPlayer().sendMessage("DeltaX " + deltaX);
            event.getPlayer().sendMessage("Velo " + event.getPlayer().getVelocity().getY());

            if (event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                Collection<PotionEffect> potionEffects = event.getPlayer().getActivePotionEffects();
                for (PotionEffect effect : potionEffects) {
                    if (effect.getType().equals(PotionEffectType.SPEED)) {
                        int potionAmplifier = effect.getAmplifier();

                    }
                }
            } else if ((deltaX) > maxDefaultSpeedTicks) {
                new Alert(event.getPlayer(), "SPEED", data.addVl(), "A");
            }
        }
    }
}
