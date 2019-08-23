package daderpduck.medicraft.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class KeyBindingInterceptor {
	private static final HashMap<KeyBinding, Boolean> interceptedKeyBindings = new HashMap<>();
	private final KeyBinding interceptedKeyBinding;

	public KeyBindingInterceptor(KeyBinding interceptedKeyBinding) {
		this.interceptedKeyBinding = interceptedKeyBinding;
		interceptedKeyBindings.putIfAbsent(interceptedKeyBinding, false);
	}

	public void setInterceptActive(boolean interceptActive) {
		interceptedKeyBindings.put(interceptedKeyBinding, interceptActive);
	}

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent keyInputEvent) {
		for (HashMap.Entry<KeyBinding, Boolean> entry : interceptedKeyBindings.entrySet()) {
			if (entry.getValue() && entry.getKey().isPressed()) {
				KeyBinding.setKeyBindState(entry.getKey().getKeyCode(), false);
				keyInputEvent.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onMouseInput(InputEvent.MouseInputEvent mouseInputEvent) {
		for (HashMap.Entry<KeyBinding, Boolean> entry : interceptedKeyBindings.entrySet()) {
			if (entry.getValue() && entry.getKey().isPressed()) {
				KeyBinding.setKeyBindState(entry.getKey().getKeyCode(), false);
				mouseInputEvent.setCanceled(true);
			}
		}
	}
}
