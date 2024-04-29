package simplepets.brainsynder.api.wrappers;

import org.bukkit.NamespacedKey;

import java.util.Locale;

public enum ArmadilloPhase {
    IDLE ("9164ed0e0ef69b0ce7815e4300b4413a4828fcb0092918543545a418a48e0c3c"),
    ROLLING ("404d9164e8f76dcdde5f8d10ff67deb2d658a6008d23c44fb2f844e08591c01d"),
    SCARED ("541693d604ed8eb31f550d3c8b124ad3f0f4a34f5f83a72def177fce4ac90916");

    private final NamespacedKey key;
    private final String texture;
    ArmadilloPhase(String texture) {
        this.texture = "http://textures.minecraft.net/texture/"+texture;
        this.key = NamespacedKey.minecraft(name().toLowerCase(Locale.ROOT));
    }

    public String getTexture() {
        return texture;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public static ArmadilloPhase getByID(int id) {
        for (ArmadilloPhase phase : values()) if (phase.ordinal() == id) return phase;
        return IDLE;
    }
    public static ArmadilloPhase getByName(String name) {
        for (ArmadilloPhase phase : values()) if (phase.name().equalsIgnoreCase(name)) return phase;
        return IDLE;
    }

    public static ArmadilloPhase getPrevious(ArmadilloPhase current) {
        if (current == IDLE) return SCARED;
        return values()[(current.ordinal() - 1)];
    }
    public static ArmadilloPhase getNext(ArmadilloPhase current) {
        if (current == SCARED) return IDLE;
        return values()[(current.ordinal() + 1)];
    }
}