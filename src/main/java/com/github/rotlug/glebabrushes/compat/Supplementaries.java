package com.github.rotlug.glebabrushes.compat;

import net.mehvahdjukaar.supplementaries.reg.ModParticles;
import net.mehvahdjukaar.supplementaries.reg.ModSounds;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;

public class Supplementaries {
    public static SimpleParticleType soapParticle() {
        return ModParticles.BUBBLE_BLOCK_PARTICLE.get();
    }

    public static SoundEvent soapSound() { return ModSounds.SOAP_WASH.get(); }
}
