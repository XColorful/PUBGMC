package dev.toma.pubgmc.proxy;

import dev.toma.pubgmc.common.entity.vehicles.EntityLandVehicle;
import dev.toma.pubgmc.common.entity.vehicles.util.LandVehicleSoundController;
import dev.toma.pubgmc.server.sounds.vehicle.ServerLandVehicleSoundController;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends Proxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    @Override
    public LandVehicleSoundController createLandVehicleSoundController(EntityLandVehicle vehicle) {
        return new ServerLandVehicleSoundController(vehicle);
    }
}
