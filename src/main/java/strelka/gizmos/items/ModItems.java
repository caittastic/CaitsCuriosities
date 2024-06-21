package strelka.gizmos.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;
import strelka.gizmos.Gizmos;
import strelka.gizmos.data.DestructionCatalystComponent;
import strelka.gizmos.data.ModDataComponents;
import strelka.gizmos.data.ResonatingWandComponent;

import java.util.HashMap;
import java.util.Map;

import static strelka.gizmos.Gizmos.MOD_ID;

public class ModItems{
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

    public static final DeferredItem<Item> EXAMPLITE_INGOT = ITEMS.register("examplite_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_EXAMPLITE = ITEMS.register("raw_examplite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ETERNAL_FUEL = ITEMS.register("eternal_fuel", () -> new Item(new Item.Properties()){
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType){
            return 25600;
        }
    });
    public static final DeferredItem<Item> DOWSING_ROD = ITEMS.register("dowsing_rod", () -> new DowingRodItem(new Item.Properties().durability(16)));
    public static final DeferredItem<Item> PORRIDGE = ITEMS.register("porridge", () -> new PorridgeItem(new Item.Properties().food(Gizmos.PORRIDGE).stacksTo(16)));
    public static final DeferredItem<Item> EXAMPLITE_SWORD = ITEMS.register("examplite_sword", () -> new SwordItem(Tiers.GOLD, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.GOLD, 3, -2.4F))));
    public static final DeferredItem<Item> EXAMPLITE_SHOVEL = ITEMS.register("examplite_shovel", () -> new ShovelItem(Tiers.GOLD, new Item.Properties().attributes(ShovelItem.createAttributes(Tiers.GOLD, 1.5F, -3.0F))));
    public static final DeferredItem<Item> EXAMPLITE_PICKAXE = ITEMS.register("examplite_pickaxe", () -> new PickaxeItem(Tiers.GOLD, new Item.Properties().attributes(PickaxeItem.createAttributes(Tiers.GOLD, 1.0F, -2.8F))));
    public static final DeferredItem<Item> EXAMPLITE_AXE = ITEMS.register("examplite_axe", () -> new AxeItem(Tiers.GOLD, new Item.Properties().attributes(AxeItem.createAttributes(Tiers.GOLD, 6.0F, -3.0F))));
    public static final DeferredItem<Item> EXAMPLITE_HOE = ITEMS.register("examplite_hoe", () -> new HoeItem(Tiers.GOLD, new Item.Properties().attributes(HoeItem.createAttributes(Tiers.GOLD, 0.0F, -3.0F))));
    public static final DeferredItem<Item> EXAMPLITE_HELMET = ITEMS.register("examplite_helmet", () -> new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(7))));
    public static final DeferredItem<Item> EXAMPLITE_CHESTPLATE = ITEMS.register("examplite_chestplate", () -> new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(7))));
    public static final DeferredItem<Item> EXAMPLITE_LEGGINGS = ITEMS.register("examplite_leggings", () -> new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(7))));
    public static final DeferredItem<Item> EXAMPLITE_BOOTS = ITEMS.register("examplite_boots", () -> new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(7))));

    static{
        for(int i = 0; i < OVERWORLD_CARDS.length; i++){
            int cardNumber = i + 1;
            String name = "overworld_card_" + cardNumber;
            String tooltip = "tooltip." + Gizmos.MOD_ID + "." + name;
            OVERWORLD_MAP.put(
                cardNumber,
                ITEMS.register(name, () -> new CardItem(new Item.Properties(), Component.translatable(tooltip), cardNumber)));
        }
    }


}
