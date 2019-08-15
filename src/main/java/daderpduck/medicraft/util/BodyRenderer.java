package daderpduck.medicraft.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import daderpduck.medicraft.entities.EntityUnconsciousBody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class BodyRenderer extends RenderLivingBase<EntityUnconsciousBody> {
	private static final Logger LOGGER = LogManager.getLogger();
	private final ModelPlayer defaultSkinModel;
	private final ModelPlayer slimSkinModel;

	public BodyRenderer(RenderManager renderManagerIn) {
		super(renderManagerIn,  new ModelPlayer(0, true), 0.5F);

		slimSkinModel = (ModelPlayer) mainModel;
		defaultSkinModel = new ModelPlayer(0, false);
	}

	private static ResourceLocation getSkin(GameProfile profile) {
		Minecraft minecraft = Minecraft.getMinecraft();
		Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache = minecraft.getSkinManager().loadSkinFromCache(profile);
		if (loadSkinFromCache.containsKey(MinecraftProfileTexture.Type.SKIN)) {
			return minecraft.getSkinManager().loadSkin(loadSkinFromCache.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
		} else {
			return DefaultPlayerSkin.getDefaultSkin(profile.getId());
		}
	}

	private void setModel(boolean slimSkin) {
		if (slimSkin) {
			mainModel = slimSkinModel;
		} else {
			mainModel = defaultSkinModel;
		}
	}

	private boolean isSlimSkin(EntityUnconsciousBody entity) {
		GameProfile gameProfile = entity.getGameProfile();
		if (gameProfile == null || gameProfile.isLegacy()) return false;

		UUID uuid = gameProfile.getId();
		if (uuid != null) {
			return (uuid.hashCode() & 1) == 1;
		}

		return false;
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(@Nonnull EntityUnconsciousBody entity) {
		GameProfile profile = entity.getGameProfile();

		if (profile != null && profile.getId() != null) {
			return getSkin(profile);
		}

		return DefaultPlayerSkin.getDefaultSkinLegacy();
	}

	@Override
	public void doRender(@Nonnull EntityUnconsciousBody entity, double x, double y, double z, float yaw, float partialTick) {
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<>(entity, this, partialTick, x, y, z)))
			return;

		setModel(isSlimSkin(entity));

		GlStateManager.pushMatrix();
		GlStateManager.disableCull();
		mainModel.swingProgress = 0;
		mainModel.isRiding = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
		mainModel.isChild = entity.isChild();

		try {
			float rotationInterpolation = 0F;
			float headYaw = 0;

			float headPitch = 0;
			renderLivingAt(entity, x, y, z);
			float age = 0.0F;
			applyRotations(entity, age, rotationInterpolation, partialTick);
			float scale = prepareScale(entity, 0F);
			float armSwingAmount = 0.0F;
			float armSwing = 0.0F;

			GlStateManager.enableAlpha();
			mainModel.setLivingAnimations(entity, armSwing, armSwingAmount, 0);
			mainModel.setRotationAngles(armSwing, armSwingAmount, age, headYaw, headPitch, scale, entity);

			if (this.renderOutlines) {
				boolean flag1 = setScoreTeamColor(entity);
				GlStateManager.enableColorMaterial();
				GlStateManager.enableOutlineMode(this.getTeamColor(entity));

				if (!renderMarker) {
					renderModel(entity, armSwing, armSwingAmount, age, headYaw, headPitch, scale);
				}

				renderLayers(entity, armSwing, armSwingAmount, partialTick, age, headYaw, headPitch, scale);

				GlStateManager.disableOutlineMode();
				GlStateManager.disableColorMaterial();

				if (flag1) {
					unsetScoreTeamColor();
				}
			} else {
				boolean flag = setDoRenderBrightness(entity, partialTick);
				renderModel(entity, armSwing, armSwingAmount, age, headYaw, headPitch, scale);

				if (flag) {
					unsetBrightness();
				}

				GlStateManager.depthMask(true);

				renderLayers(entity, armSwing, armSwingAmount, partialTick, age, headYaw, headPitch, scale);
			}

			GlStateManager.disableRescaleNormal();
		} catch (Exception exception) {
			LOGGER.error("Couldn't render entity", exception);
		}

		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.enableCull();
		GlStateManager.popMatrix();

		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<>(entity, this, partialTick, x, y, z));
	}

	@Override
	protected void renderLivingAt(EntityUnconsciousBody entityLivingBaseIn, double x, double y, double z) {
		super.renderLivingAt(entityLivingBaseIn, x, y, z);
		GlStateManager.rotate(90,1F,0F,0F);
		GlStateManager.rotate(entityLivingBaseIn.rotationYaw,0F,0F,1F);
		GlStateManager.translate(0F, -0.85F, -0.125F);
	}
}
