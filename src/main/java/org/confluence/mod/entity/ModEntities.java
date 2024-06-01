package org.confluence.mod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.entity.hook.*;

@SuppressWarnings("unused")
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Confluence.MODID);

    public static final RegistryObject<EntityType<BaseHookEntity>> BASE_HOOK = registerHook("base_hook", BaseHookEntity::new);
    public static final RegistryObject<EntityType<WebSlingerEntity>> WEB_SLINGER = registerHook("web_slinger", WebSlingerEntity::new);
    public static final RegistryObject<EntityType<SkeletronHandEntity>> SKELETRON_HAND = registerHook("skeletron_hand", SkeletronHandEntity::new);
    public static final RegistryObject<EntityType<SlimeHookEntity>> SLIME_HOOK = registerHook("slime_hook", SlimeHookEntity::new);
    public static final RegistryObject<EntityType<FishHookEntity>> FISH_HOOK = registerHook("fish_hook", FishHookEntity::new);
    public static final RegistryObject<EntityType<IvyWhipEntity>> IVY_WHIP = registerHook("ivy_whip", IvyWhipEntity::new);
    public static final RegistryObject<EntityType<BatHookEntity>> BAT_HOOK = registerHook("bat_hook", BatHookEntity::new);
    public static final RegistryObject<EntityType<CandyCaneHookEntity>> CANDY_CANE_HOOK = registerHook("candy_cane_hook", CandyCaneHookEntity::new);
    public static final RegistryObject<EntityType<DualHookEntity>> DUAL_HOOK = registerHook("dual_hook", DualHookEntity::new);
    public static final RegistryObject<EntityType<HookOfDissonanceEntity>> HOOK_OF_DISSONANCE = registerHook("hook_of_dissonance", HookOfDissonanceEntity::new);
    public static final RegistryObject<EntityType<ThornHookEntity>> THORN_HOOK = registerHook("thorn_hook", ThornHookEntity::new);
    public static final RegistryObject<EntityType<MimicHookEntity>> MIMIC_HOOK = registerHook("mimic_hook", MimicHookEntity::new);
    public static final RegistryObject<EntityType<AntiGravityHookEntity>> ANTI_GRAVITY_HOOK = registerHook("anti_gravity_hook", AntiGravityHookEntity::new);
    public static final RegistryObject<EntityType<SpookyHookEntity>> SPOOKY_HOOK = registerHook("spooky_hook", SpookyHookEntity::new);
    public static final RegistryObject<EntityType<ChristmasHookEntity>> CHRISTMAS_HOOK = registerHook("christmas_hook", ChristmasHookEntity::new);
    public static final RegistryObject<EntityType<LunarHookEntity>> LUNAR_HOOK = registerHook("lunar_hook", LunarHookEntity::new);
    /* todo 静止钩 */

    private static <E extends AbstractHookEntity> RegistryObject<EntityType<E>> registerHook(String id, EntityType.EntityFactory<E> supplier) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(supplier, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("confluence:" + id));
    }
}
