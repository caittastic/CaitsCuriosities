package caittastic.caitsmod;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModSoundEvents{
  public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MOD_ID);
  public static final Supplier<SoundEvent> ITEM_USE = registerSoundEvent("destruction_catalyst_use");
  public static final Supplier<SoundEvent> ITEM_CHARGE = registerSoundEvent("item_charge");
  public static final Supplier<SoundEvent> ITEM_TUNE = registerSoundEvent("item_tune");
  public static final Supplier<SoundEvent> POOF = registerSoundEvent("poof");

  private static Supplier<SoundEvent> registerSoundEvent(String name){
    return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, name)));
  }
}
