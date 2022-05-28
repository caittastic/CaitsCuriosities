package caittastic.caitsmod.init;

import caittastic.caitsmod.init.block.BlockInit;
import caittastic.caitsmod.init.item.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabInit {
    public static final CreativeModeTab CURIOSITIES_TAB = new CreativeModeTab("tutorialtab") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(BlockInit.RUBBER_DUCK.get()); }
    };
}
