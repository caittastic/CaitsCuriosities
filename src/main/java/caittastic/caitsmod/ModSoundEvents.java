package caittastic.caitsmod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModSoundEvents{
  public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
  public static final RegistryObject<SoundEvent> ITEM_USE = registerSoundEvent("destruction_catalyst_use");
  public static final RegistryObject<SoundEvent> ITEM_CHARGE = registerSoundEvent("item_charge");
  public static final RegistryObject<SoundEvent> ITEM_TUNE = registerSoundEvent("item_tune");
  public static final RegistryObject<SoundEvent> POOF = registerSoundEvent("poof");

  private static RegistryObject<SoundEvent> registerSoundEvent(String name){
    return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(MOD_ID, name)));
  }
}
