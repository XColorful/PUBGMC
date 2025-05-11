package dev.toma.pubgmc.common.entity.vehicles.util;

import dev.toma.pubgmc.common.entity.vehicles.EntityLandVehicle;

public abstract class LandVehicleSoundController extends VehicleSoundController {

    protected final EntityLandVehicle landVehicle;

    public LandVehicleSoundController(EntityLandVehicle landVehicle) {
        this.landVehicle = landVehicle;
    }

}
