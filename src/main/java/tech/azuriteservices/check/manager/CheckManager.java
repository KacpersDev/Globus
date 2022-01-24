package tech.azuriteservices.check.manager;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packettype.PacketType;
import tech.azuriteservices.check.impl.combat.reach.ReachA;
import tech.azuriteservices.check.impl.combat.reach.ReachB;
import tech.azuriteservices.check.impl.combat.reach.ReachC;
import tech.azuriteservices.check.impl.movement.fly.FlyA;
import tech.azuriteservices.check.impl.movement.fly.FlyB;
import tech.azuriteservices.check.impl.movement.fly.FlyC;
import tech.azuriteservices.check.impl.movement.fly.FlyD;
import tech.azuriteservices.check.impl.movement.speed.SpeedA;

public class CheckManager {

    public void registerChecks(){

        PacketEvents.get().registerListener(new FlyA());
        PacketEvents.get().registerListener(new FlyB());
        PacketEvents.get().registerListener(new FlyC());
        PacketEvents.get().registerListener(new FlyD());
        PacketEvents.get().registerListener(new SpeedA());
        PacketEvents.get().registerListener(new ReachA());
        PacketEvents.get().registerListener(new ReachB());
        PacketEvents.get().registerListener(new ReachC());
    }
}
