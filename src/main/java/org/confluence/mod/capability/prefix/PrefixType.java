package org.confluence.mod.capability.prefix;

import net.minecraft.util.RandomSource;
import org.confluence.mod.item.ModPrefix;

public enum PrefixType {
    UNIVERSAL(new Enum[][]{ModPrefix.Universal.values()}),
    MELEE(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Melee.values()}),
    RANGED(new Enum[][]{ModPrefix.Common.values(), ModPrefix.Ranged.values()}),
    MAGIC_AND_SUMMING(new Enum[][]{ModPrefix.Common.values(), ModPrefix.MagicAndSumming.values()}),
    CURIO(new Enum[][]{ModPrefix.Curio.values()}),
    UNKNOWN(new Enum[][]{});

    public final Enum<? extends ModPrefix>[][] available;

    PrefixType(Enum<? extends ModPrefix>[][] available) {
        this.available = available;
    }

    public ModPrefix randomPrefix(RandomSource random) {
        Enum<? extends ModPrefix>[] values = available[random.nextInt(available.length)];
        return (ModPrefix) values[random.nextInt(values.length)];
    }
}
