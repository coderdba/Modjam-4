package mod.badores.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

/**
 * @author Dries007
 */
public class FakePlayerDetection {
    public static final UUID UUID_MINECRAFT_FAKE_PLAYER = UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77");

    /**
     * @param player the player
     * @return true if a fake player
     */
    public static boolean isFakePlayer(EntityPlayer player) {
        if (player instanceof FakePlayer) return true; // Forge & Minecraft & regular fake players
        GameProfile profile = player.getGameProfile();
        if (UUID_MINECRAFT_FAKE_PLAYER.equals(player.getGameProfile().getId()))
            return true; // Any mod using the same ID but not the class for some reason
        String name = profile.getName();
        return (name.length() >= 3 && name.charAt(0) == '[' && name.charAt(name.length() - 1) == ']'); // Anyone using the [NAME] format but not the class for some reason
    }
}
