package dev.toma.pubgmc.server.sounds.vehicle;

import dev.toma.pubgmc.common.entity.vehicles.EntityLandVehicle;
import dev.toma.pubgmc.common.entity.vehicles.EntityVehicle;
import dev.toma.pubgmc.common.entity.vehicles.util.LandVehicleSoundController;
import dev.toma.pubgmc.network.PacketHandler;
import dev.toma.pubgmc.network.s2c.S2C_VehicleSoundEvent;

public final class ServerLandVehicleSoundController extends LandVehicleSoundController {

    public ServerLandVehicleSoundController(EntityLandVehicle landVehicle) {
        super(landVehicle);
    }

    @Override
    public void update() {
    }

    @Override
    public void play(int eventId) {
        PacketHandler.sendToAllTracking(new S2C_VehicleSoundEvent(true, this.landVehicle.getEntityId(), eventId), this.landVehicle);
    }

    @Override
    public void stop(int eventId) {
        PacketHandler.sendToAllTracking(new S2C_VehicleSoundEvent(false, this.landVehicle.getEntityId(), eventId), this.landVehicle);
    }

    @Override
    public void playStartingSound() {
        this.play(EntityVehicle.SOUND_ENGINE_STARTING);
    }

    @Override
    public void playStartedSound() {
        this.play(EntityVehicle.SOUND_ENGINE_STARTING);
    }
}
