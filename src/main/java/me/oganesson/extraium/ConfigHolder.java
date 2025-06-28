package me.oganesson.extraium;

import net.minecraftforge.common.config.Config;
import com.cleanroommc.configanytime.ConfigAnytime;

@Config(modid = Tags.MOD_ID)
public class ConfigHolder {

    public static boolean enable = true;

    static {
        ConfigAnytime.register(ConfigHolder.class);
    }

}
