package caittastic.caitsmod.init.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import caittastic.caitsmod.init.TabInit;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> DESTRUCTION_CATALYST = ITEMS.register("destruction_catalyst",
            () -> new DestructionCatalyst(new Item.Properties().tab(TabInit.CURIOSITIES_TAB).stacksTo(1)));

    //function that registers items
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
