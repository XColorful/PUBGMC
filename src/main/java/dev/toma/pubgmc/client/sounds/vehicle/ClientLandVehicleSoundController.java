package dev.toma.pubgmc.client.sounds.vehicle;

import dev.toma.pubgmc.common.entity.vehicles.EntityLandVehicle;
import dev.toma.pubgmc.common.entity.vehicles.EntityVehicle;
import dev.toma.pubgmc.common.entity.vehicles.util.LandVehicleSoundController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ClientLandVehicleSoundController extends LandVehicleSoundController {

    private VehicleSound<EntityLandVehicle> startingSound;
    private VehicleSound<EntityLandVehicle> startedSound;

    public ClientLandVehicleSoundController(EntityLandVehicle landVehicle) {
        super(landVehicle);
    }

    @Override
    public void update() {
        // TODO update engine sound pitch
    }

    @Override
    public void play(int eventId) {
        switch (eventId) {
            case EntityVehicle.SOUND_ENGINE_STARTING:
                this.playStartingSound();
                break;
            case EntityVehicle.SOUND_ENGINE_STARTED:
                this.playStartedSound();
                break;
        }
    }

    @Override
    public void stop(int eventId) {
        switch (eventId) {
            case EntityVehicle.SOUND_ENGINE_STARTING:
                this.stop(this.startingSound);
                break;
            case EntityVehicle.SOUND_ENGINE_STARTED:
                this.stop(this.startedSound);
                break;
        }
    }

    @Override
    public void playStartingSound() {
        this.startingSound = new VehicleSound<>(EntityVehicle.SOUND_ENGINE_STARTING, this.landVehicle, this.landVehicle.getSoundPack().getStartingSound());
        this.startingSound.setPlayCondition(vehicle -> !vehicle.isDestroyed() && vehicle.isStarting());
        this.startingSound.setRepeating(false);
        this.startingSound.onSoundStopped(sound -> this.startingSound = null);
        this.play(this.startingSound, true);
    }

    @Override
    public void playStartedSound() {
        this.startedSound = new VehicleSound<>(EntityVehicle.SOUND_ENGINE_STARTED, this.landVehicle, this.landVehicle.getSoundPack().getStartedSound());
        this.startedSound.setPlayCondition(vehicle -> !vehicle.isDestroyed());
        this.startedSound.setRepeating(false);
        this.startedSound.onSoundStopped(sound -> this.startedSound = null);
        this.play(this.startedSound, true);
    }

    private void play(ISound sound, boolean force) {
        Minecraft minecraft = Minecraft.getMinecraft();
        SoundHandler handler = minecraft.getSoundHandler();
        if (handler.isSoundPlaying(sound)) {
            if (force) {
                handler.stopSound(sound);
                handler.playSound(sound);
            }
        } else {
            handler.playSound(sound);
        }
    }

    private void stop(ISound sound) {
        if (sound == null)
            return;
        Minecraft minecraft = Minecraft.getMinecraft();
        SoundHandler handler = minecraft.getSoundHandler();
        if (handler.isSoundPlaying(sound)) {
            handler.stopSound(sound);
        }
    }
}
