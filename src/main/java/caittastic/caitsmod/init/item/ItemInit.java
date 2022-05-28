package caittastic.caitsmod.init.item;

import caittastic.caitsmod.init.TabInit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ItemInit {
    /* utilities */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static final RegistryObject<Item> DESTRUCTION_CATALYST = ITEMS.register("destruction_catalyst",
            () -> new DestructionCatalyst(new Item.Properties().tab(TabInit.CURIOSITIES_TAB).stacksTo(1)));





}
