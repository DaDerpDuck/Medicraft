package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.drugs.Drug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(Side.CLIENT)
public class Deliriozine extends Drug {
	public Deliriozine() {
		super(0);
		setInitialDuration(120*20);
	}

	private static boolean active = false;

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		if (player.world.isRemote) {
			active = drugDuration > 20;
		}
	}

	private final static Map<KeyBinding, KeyBinding> SWAP_KEYS_MAP = new HashMap<>();
	static {
		GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;

		SWAP_KEYS_MAP.put(gameSettings.keyBindForward, gameSettings.keyBindBack);
		SWAP_KEYS_MAP.put(gameSettings.keyBindBack, gameSettings.keyBindForward);
		SWAP_KEYS_MAP.put(gameSettings.keyBindLeft, gameSettings.keyBindRight);
		SWAP_KEYS_MAP.put(gameSettings.keyBindRight, gameSettings.keyBindLeft);

		SWAP_KEYS_MAP.put(gameSettings.keyBindInventory, gameSettings.keyBindDrop);
		SWAP_KEYS_MAP.put(gameSettings.keyBindDrop, gameSettings.keyBindInventory);

		SWAP_KEYS_MAP.put(gameSettings.keyBindAttack, gameSettings.keyBindUseItem);
		SWAP_KEYS_MAP.put(gameSettings.keyBindUseItem, gameSettings.keyBindAttack);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		if (!active) return;

		for (Map.Entry<KeyBinding, KeyBinding> entry : SWAP_KEYS_MAP.entrySet()) {
			KeyBinding inputKey = entry.getKey();
			KeyBinding outputKey = entry.getValue();

			if (Keyboard.getEventKey() == inputKey.getKeyCode()) {
				KeyBinding.setKeyBindState(inputKey.getKeyCode(), false);
				KeyBinding.setKeyBindState(outputKey.getKeyCode(), Keyboard.getEventKeyState());
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onMouseInput(InputEvent.MouseInputEvent event) {
		if (!active) return;

		for (Map.Entry<KeyBinding, KeyBinding> entry : SWAP_KEYS_MAP.entrySet()) {
			KeyBinding inputKey = entry.getKey();
			KeyBinding outputKey = entry.getValue();

			if (Mouse.getEventButton() == inputKey.getKeyCode()+100) {
				inputKey.isPressed();
				KeyBinding.setKeyBindState(inputKey.getKeyCode(), false);
				KeyBinding.setKeyBindState(outputKey.getKeyCode(), Mouse.getEventButtonState());
			}
		}
	}
}
