package caittastic.caitsmod.particles;

import caittastic.caitsmod.CaitsMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles{
  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
          DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CaitsMod.MOD_ID);

  public static final RegistryObject<SimpleParticleType> LOCATOR_PARTICLE =
          PARTICLE_TYPES.register("locator_particle",
                  () -> new SimpleParticleType(true));

  public static final RegistryObject<SimpleParticleType> TELEPORT_POOF_PARTICLE =
          PARTICLE_TYPES.register("teleport_poof_particle",
                  () -> new SimpleParticleType(true));

}
