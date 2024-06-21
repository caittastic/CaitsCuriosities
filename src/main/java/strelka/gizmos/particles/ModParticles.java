package strelka.gizmos.particles;

import strelka.gizmos.Gizmos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles{
  public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
          DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Gizmos.MOD_ID);

  public static final Supplier<SimpleParticleType> LOCATOR_PARTICLE =
          PARTICLE_TYPES.register("locator_particle",
                  () -> new SimpleParticleType(true));

  public static final Supplier<SimpleParticleType> TELEPORT_POOF_PARTICLE =
          PARTICLE_TYPES.register("teleport_poof_particle",
                  () -> new SimpleParticleType(true));

}
