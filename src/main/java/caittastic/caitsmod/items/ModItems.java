package caittastic.caitsmod.items;

import caittastic.caitsmod.CaitsMod;
import caittastic.caitsmod.data.DestructionCatalystComponent;
import caittastic.caitsmod.data.ModDataComponents;
import caittastic.caitsmod.data.ResonatingWandComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

import static caittastic.caitsmod.CaitsMod.MOD_ID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final Map<Integer, DeferredItem<Item>> OVERWORLD_MAP = new HashMap<>();

    public static final String[][] OVERWORLD_CARDS = {
            //common
            {"Rocky", "a big bass fan"},
            {"Tree", "used to know applette"},
            {"Applette", "a mobile developer"},
            {"Rose", "an old lass"},
            {"Grassy", "foundational"},
            {"Hutt", "well known everywhere"},
            {"Wheata", "best with friends"},
            {"Porky", "piggy piggy hole"},
            {"Zombo", "not a fan of plants"},
            //uncommon
            {"Ramona", "physically affectionate"},
            {"Woofie", "is a fan of Zombo"},
            {"Slimbo", "wants to be a rancher"},
            {"Barry", "a bit of a hoarder"},
            {"Mushy", "high aspirations"},
            {"Fops The Sleepy", "zzz..."},
            //rare
            {"Gapplette", "very self confident"},
            {"The Enderman", "Watching..."},
            {"Mojang", "thingymabob"}
    };

    public static final DeferredItem<PackItem> OVERWORLD_PACK = ITEMS.register("overworld_pack", () -> new PackItem(new Item.Properties()));

    public static final DeferredItem<DestructionCatalystItem> DESTRUCTION_CATALYST =
            ITEMS.register("destruction_catalyst", () ->
                    new DestructionCatalystItem(new Item.Properties()
                            .durability(1024)
                            .stacksTo(1)
                            .component(ModDataComponents.DESTRUCTION_CATALYST, DestructionCatalystComponent.EMPTY)));
    public static final DeferredItem<ResonatingWandItem> TUNING_WAND =
            ITEMS.register("tuning_wand", () ->
                    new ResonatingWandItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponents.RESONATING_WAND, ResonatingWandComponent.EMPTY)));
    public static final DeferredItem<Item> ROTTEN_BRAIN =
            ITEMS.register("rotten_brain", () ->
                    new Item(new Item.Properties()));

    static {
        for (int i = 0; i < OVERWORLD_CARDS.length; i++) {
            int cardNumber = i + 1;
            String tooltip = "tooltip." + CaitsMod.MOD_ID + ".overworld_card_" + cardNumber;
            OVERWORLD_MAP.put(
                    cardNumber,
                    ITEMS.register("overworld_card_" + cardNumber,
                            () -> new CardItem(
                                    new Item.Properties(),
                                    Component.translatable(tooltip),
                                    cardNumber)));
        }
    }
}
