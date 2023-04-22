package caittastic.caitsmod.events;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.particles.LocatorParticle;
import caittastic.caitsmod.particles.ModParticles;
import caittastic.caitsmod.particles.TeleportPoofParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaitsMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents{
  @SubscribeEvent
  public static void registerParticleFactories(final RegisterParticleProvidersEvent event){
    Minecraft.getInstance().particleEngine.register(ModParticles.LOCATOR_PARTICLE.get(),
            LocatorParticle.Provider::new);
    Minecraft.getInstance().particleEngine.register(ModParticles.TELEPORT_POOF_PARTICLE.get(),
            TeleportPoofParticle.Provider::new);
  }
}
