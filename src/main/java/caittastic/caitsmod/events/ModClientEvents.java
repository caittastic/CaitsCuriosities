package caittastic.caitsmod.events;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.particles.LocatorParticle;
import caittastic.caitsmod.particles.ModParticles;
import caittastic.caitsmod.particles.TeleportPoofParticle;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = CaitsMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModClientEvents{
  @SubscribeEvent
  public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
    Minecraft.getInstance().particleEngine.register(ModParticles.LOCATOR_PARTICLE.get(),
            LocatorParticle.Provider::new);
    Minecraft.getInstance().particleEngine.register(ModParticles.TELEPORT_POOF_PARTICLE.get(),
            TeleportPoofParticle.Provider::new);
  }
}
