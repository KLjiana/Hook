package org.confluence.mod.item.curio;

import net.minecraftforge.registries.RegistryObject;
import org.confluence.mod.Confluence;
import org.confluence.mod.item.ModItems;
import org.confluence.mod.item.curio.combat.*;
import org.confluence.mod.item.curio.construction.ExtendoGrip;
import org.confluence.mod.item.curio.healthandmana.BandOfRegeneration;
import org.confluence.mod.item.curio.healthandmana.BandOfStarpower;
import org.confluence.mod.item.curio.informational.*;
import org.confluence.mod.item.curio.miscellaneous.SpectreGoggles;
import org.confluence.mod.item.curio.movement.*;
import org.confluence.mod.util.EnumRegister;

import java.util.function.Supplier;

public enum CurioItems implements EnumRegister<BaseCurioItem> {
    /* 粘性绷带 */
    /* 十字章护身符 */
    /* 十字章护盾 */
    /* 盔甲背带 */
    /* 盔甲抛光剂 */
    AVENGER_EMBLEM("avenger_emblem", AvengerEmblem::new), // 复仇者勋章
    /* 蜜蜂斗篷 */
    /* 狂战士手套 */
    BEZOAR("bezoar", Bezoar::new), // 牛黄
    BLACK_BELT("black_belt", BlackBelt::new), // 黑腰带
    BLINDFOLD("blindfold", Blindfold::new), // 蒙眼布
    /* 天界徽章 */
    /* 月光护身符 */
    /* 月亮贝壳 */
    /* 天界石 */
    /* 天界贝壳 */
    COBALT_SHIELD("cobalt_shield", CobaltShield::new), // 钴护盾
    /* 反诅咒咒语 */
    CROSS_NECKLACE("cross_necklace", CrossNecklace::new), // 十字项链
    DESTROYER_EMBLEM("destroyer_emblem", DestroyerEmblem::new), // 毁灭者勋章
    EYE_OF_THE_GOLEM("eye_of_the_golem", EyeOfTheGolem::new), // 石巨人之眼
    /* 快走时钟 */
    /* 狂爪手套 */
    /* 烈火手套 */
    FLESH_KNUCKLES("flesh_knuckles", FleshKnuckles::new), // 血肉指虎
    /* 冰冻海龟壳 */
    /* 冰冻护盾 */
    /* 暖手宝 */
    HERO_SHIELD("hero_shield", HeroShield::new), // 英雄护盾
    HONEY_COMB("honey_comb", HoneyComb::new), // 蜂窝 (WIP)
    /* 魔法箭袋 */
    /* 机械手套 */
    /* 药用绷带 */
    /* 扩音器 */
    /* 月亮石 */
    /* 熔火箭袋 */
    /* 邪眼 */
    /* 岩浆石 */
    MAGMA_SKULL("magma_skull", MagmaSkull::new), // 岩浆骷髅头
    LAVA_CHARM("lava_charm", LavaCharm::new), // 熔岩护身符
    OBSIDIAN_ROSE("obsidian_rose", ObsidianRose::new), // 黑曜石玫瑰
    OBSIDIAN_SHIELD("obsidian_shield", ObsidianShield::new), // 黑曜石护盾
    OBSIDIAN_SKULL("obsidian_skull", ObsidianSkull::new), // 黑曜石骷髅头
    MOLTEN_SKULL_ROSE("molten_skull_rose", MoltenSkullRose::new), // 熔火骷髅头玫瑰
    OBSIDIAN_SKULL_ROSE("obsidian_skull_rose", ObsidianSkullRose::new), // 黑曜石骷髅头玫瑰
    PALADINS_SHIELD("paladins_shield", PaladinsShield::new), // 圣骑士护盾
    PANIC_NECKLACE("panic_necklace", PanicNecklace::new), // 恐慌项链
    /* 袖珍镜 */
    /* 强力手套 */
    /* 腐香囊 */
    /* 游侠徽章 */
    /* 侦察镜 */
    /* 步枪瞄准镜 */
    SHACKLE("shackle", Shackle::new), // 脚镣
    /* 鲨牙项链 */
    /* 狙击镜 */
    /* 巫士徽章 */
    /* 潜行者箭袋 */
    /* 星星斗篷 */
    /* 星星面纱 */
    /* 毒刺项链 */
    /* 召唤师徽章 */
    /* 太阳石 */
    /* 甜心项链 */
    /* 计划书 */
    /* 泰坦手套 */
    /* 三折地图 */
    /* 维生素 */
    /* 战士徽章 */
    /* 学徒围巾 */
    /* 侍卫护盾 */
    /* 女猎人圆盾 */
    /* 武僧腰带 */
    /* 大力士甲虫 */
    /* 死灵卷轴 */
    /* 甲虫莎草纸 */
    /* 矮人项链 */


    /* 工具腰带 */
    /* 工具箱 */
    /* 喷漆器 */
    EXTENDO_GRIP("extendo_grip", ExtendoGrip::new), // 加长握爪
    /* 便携式水泥搅拌机 */
    /* 砌砖刀 */
    /* 建筑师发明背包 */
    /* 自动安放器 */
    /* 远古凿子 */
    /* 创造之手 */


    /* 奥术花 */
    BAND_OF_REGENERATION("band_of_regeneration", BandOfRegeneration::new), // 再生手环
    BAND_OF_STARPOWER("band_of_starpower", BandOfStarpower::new), // 星力手环
    /* 天界手铐 */
    /* 天界磁石 */
    /* 天界徽章 */
    /* 神话护身符 */
    /* 魔法手铐 */
    /* 磁花 */
    /* 魔力斗篷 */
    /* 魔力花 */
    /* 魔力再生手环 */
    /* 大自然的恩赐 */
    /* 点金石 */


    COPPER_WATCH("copper_watch", HourWatch::new), // 铜表
    TIN_WATCH("tin_watch", HourWatch::new), // 锡表
    SILVER_WATCH("silver_watch", HalfHourWatch::new), // 银表
    TUNGSTEN_WATCH("tungsten_watch", HalfHourWatch::new), // 钨表
    GOLDEN_WATCH("golden_watch", MinuteWatch::new), // 金表
    PLATINUM_WATCH("platinum_watch", MinuteWatch::new), // 铂金表
    DEPTH_METER("depth_meter", DepthMeter::new), // 深度计
    COMPASS("compass", Compass::new), // 罗盘
    RADAR("radar", Radar::new), // 雷达
    /* 生命体分析机 */
    TALLY_COUNTER("tally_counter", TallyCounter::new), // 杀怪计数器
    /* 金属探测器 */
    /* 秒表 */
    /* 每秒伤害计数器 */
    /* 渔民袖珍宝典 */
    /* 天气收音机 */
    /* 六分仪 */
    /* 全球定位系统 */
    /* R.E.K.3000 */
    /* 哥布林数据仪 */
    /* 探鱼器 */
    /* 个人数字助手 */
    MECHANICAL_LENS("mechanical_lens", MechanicalLens::new), // 机械晶状体
    /* 标尺 */
    /* 机械标尺 */


    /* 梯凳 */
    AGLET("aglet", Aglet::new), // 金属带扣
    ANKLET_OF_THE_WIND("anklet_of_the_wind", AnkletOfTheWind::new), // 疾风脚镯
    MAGILUMINESCENCE("magiluminescence", Magiluminescence::new), // 魔光护符 (WIP)
    FLIPPER("flipper", Flipper::new), // 脚蹼
    /* 潜水装备 */
    /* 水母潜水装备 */
    /* 北极潜水装备 */
    /* 攀爬爪 */
    /* 鞋钉 */
    /* 猛虎攀爬装备 */
    /* 分趾厚底袜 */
    /* 飞毯 */
    /* 浮游圈 */
    CLOUD_IN_A_BOTTLE("cloud_in_a_bottle", CloudInABottle::new), // 云朵瓶
    TSUNAMI_IN_A_BOTTLE("tsunami_in_a_bottle", TsunamiInABottle::new), // 海啸瓶
    BLIZZARD_IN_A_BOTTLE("blizzard_in_a_bottle", BlizzardInABottle::new), // 暴雪瓶
    SANDSTORM_IN_A_BOTTLE("sandstorm_in_a_bottle", SandstormInABottle::new), // 沙暴瓶
    FART_IN_A_BOTTLE("fart_in_a_bottle", FartInABottle::new), // 罐中臭屁
    ICE_SKATES("ice_skates", IceSkates::new), // 溜冰鞋
    HERMES_BOOTS("hermes_boots", BaseSpeedBoots::new), // 赫尔墨斯靴
    FLURRY_BOOTS("flurry_boots", BaseSpeedBoots::new), // 疾风雪靴
    SAILFISH_BOOTS("sailfish_boots", BaseSpeedBoots::new), // 旗鱼靴
    DUNERIDER_BOOTS("dunerider_boots", DuneriderBoots::new), // 沙丘行者靴
    ROCKET_BOOTS("rocket_boots", RocketBoots::new), // 火箭靴
    SPECTRE_BOOTS("spectre_boots", SpectreBoots::new), // 幽灵靴
    /* 仙灵靴 */
    LIGHTNING_BOOTS("lightning_boots", LightningBoots::new), // 闪电靴
    FROSTSPARK_BOOTS("frostspark_boots", FrostSparkBoots::new), // 霜花靴
    /* 水上漂靴 */
    /* 黑曜石水上漂靴 */
    /* 熔岩靴 */
    /* 泰拉闪耀靴 */
    SHINY_RED_BALLOON("shiny_red_balloon", Balloon::new), // 闪亮红气球
    BALLOON_PUFFERFISH("balloon_pufferfish", Balloon::new), // 气球河豚鱼
    CLOUD_IN_A_BALLOON("cloud_in_a_balloon", CloudInABalloon::new), // 云朵气球
    BLIZZARD_IN_A_BALLOON("blizzard_in_a_balloon", BlizzardInABalloon::new), // 暴雪气球
    SANDSTORM_IN_A_BALLOON("sandstorm_in_a_balloon", SandstormInABalloon::new), // 沙暴气球
    FART_IN_A_BALLOON("fart_in_a_balloon", FartInABalloon::new), // 臭屁气球
    SHARKRON_BALLOON("sharkron_balloon", SharkronBalloon::new), // 鲨鱼龙气球
    /* 蜂蜜气球 */
    BUNDLE_OF_BALLOONS("bundle_of_balloons", BundleOfBalloons::new), // 气球束
    FROG_LEG("frog_leg", FrogLeg::new), // 蛙腿
    FROG_FLIPPER("frog_flipper", FrogFlipper::new), // 青蛙脚蹼
    /* 青蛙蹼 */
    /* 青蛙装备 */
    AMBHIPIAN_BOOTS("ambhipian_boots", AmbhipianBoots::new), // 水陆两用靴
    LUCKY_HORSESHOE("lucky_horseshoe", LuckyHorseshoe::new), // 幸运马掌
    BLUE_HORSESHOE_BALLOON("blue_horseshoe_balloon", BlueHorseshoeBalloon::new), // 蓝马掌气球
    WHITE_HORSESHOE_BALLOON("white_horseshoe_balloon", WhiteHorseshoeBalloon::new), // 白马掌气球
    YELLOW_HORSESHOE_BALLOON("yellow_horseshoe_balloon", YellowHorseshoeBalloon::new), // 黄马掌气球
    GREEN_HORSESHOE_BALLOON("green_horseshoe_balloon", GreenHorseshoeBalloon::new), // 绿马掌气球
    PINK_HORSESHOE_BALLOON("pink_horseshoe_balloon", PinkHorseshoeBalloon::new), // 粉马掌气球
    /* 琥珀马掌气球 */
    BUNDLE_OF_HORSESHOE_BALLOONS("bundle_of_horseshoe_balloons", BundleOfHorseshoeBalloons::new), // 马掌气球束
    /* 天界贝壳 */


    /* 服装商巫毒娃娃 */
    /* 钱币戒指 */
    /* 优惠卡 */
    /* 花靴 */
    /* 金戒指 */
    /* 贪婪戒指 */
    /* 植物纤维绳索宝典 */
    /* 向导巫毒娃娃 */
    /* 水母项链 */
    /* 幸运币 */
    /* 收音机 */
    SPECTRE_GOGGLES("spectre_goggles", SpectreGoggles::new), // 幽灵护目镜
    /* 宝藏磁石 */
    /* 炫彩斗篷 */


    /* ** 钓鱼 ** */
    /* ** 悠悠球 ** */
    /* ** 时装 ** */
    /* ** 八音盒 ** */
    /* ** 高尔夫球 ** */
    /* ** 专家模式独有 ** */;

    private final RegistryObject<BaseCurioItem> value;

    CurioItems(String id, Supplier<BaseCurioItem> curio) {
        this.value = ModItems.ITEMS.register(id, curio);
    }

    @Override
    public RegistryObject<BaseCurioItem> getValue() {
        return value;
    }

    public static void init() {
        Confluence.LOGGER.info("Registering curio items");
    }
}
