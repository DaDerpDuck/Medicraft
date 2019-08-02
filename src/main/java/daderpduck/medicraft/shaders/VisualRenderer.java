package daderpduck.medicraft.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class VisualRenderer extends VisualHandler {
	private static final Minecraft mc = Minecraft.getMinecraft();

	private static boolean isGuiInventory(GuiScreen gui) {
		try {
			Class.forName("net.minecraft.client.gui.inventory." + gui.getClass().getSimpleName());
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	@SubscribeEvent
	public static void onRenderTick(RenderTickEvent event) {
		if (event.phase == Phase.END) {
			if (mc.currentScreen instanceof GuiGameOver || mc.currentScreen instanceof GuiMainMenu) {
				resetShaders();
				return;
			}

			if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiScreenBook || isGuiInventory(mc.currentScreen)) {
				float partialTicks = event.renderTickTime;

				ScaledResolution resolution = new ScaledResolution(mc);
				Framebuffer frameBuffer = mc.getFramebuffer();

				GlStateManager.pushMatrix();

				RenderHelper.enableStandardItemLighting();
				GlStateManager.clear(256);
				GlStateManager.matrixMode(5889);
				GlStateManager.loadIdentity();
				GlStateManager.ortho(0.0D, resolution.getScaledWidth_double(), resolution.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
				GlStateManager.matrixMode(5888);
				GlStateManager.loadIdentity();
				GlStateManager.translate(0.0F, 0.0F, -2000.0F);

				GlStateManager.enableBlend();
				GlStateManager.disableDepth();
				GlStateManager.depthMask(false);
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableAlpha();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.loadIdentity();

				for (Visual shaderGroup : VisualHandler.overlayShaders) {

					if (!shaderGroup.canRender()) continue;

					GlStateManager.pushMatrix();
					shaderGroup.render(partialTicks);
					GlStateManager.popMatrix();

					shaderGroup.getShaderGroup().createBindFramebuffers(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight);
				}
				for (HashMap<Class, Visual> map : VisualHandler.singleShaders.values()) {
					for (Visual shaderGroup : map.values()) {

						if (!shaderGroup.canRender()) continue;

						GlStateManager.pushMatrix();
						shaderGroup.render(partialTicks);
						GlStateManager.popMatrix();

						shaderGroup.getShaderGroup().createBindFramebuffers(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight);
					}
				}
				for (HashMap<Class, Visual> map : VisualHandler.persistentShaders.values()) {
					for (Visual shaderGroup : map.values()) {

						if (!shaderGroup.canRender()) continue;

						GlStateManager.pushMatrix();
						shaderGroup.render(partialTicks);
						GlStateManager.popMatrix();

						shaderGroup.getShaderGroup().createBindFramebuffers(frameBuffer.framebufferWidth, frameBuffer.framebufferHeight);
					}
				}

				GlStateManager.popMatrix();

				GlStateManager.depthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableAlpha();
				GlStateManager.disableLighting();

				mc.getFramebuffer().bindFramebuffer(false);
				GlStateManager.matrixMode(5888);

				GlStateManager.popMatrix();

				flushShaders();
			}
		}
	}
}
