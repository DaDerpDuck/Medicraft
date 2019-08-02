package daderpduck.medicraft.util;

import com.google.gson.JsonSyntaxException;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EnhancedShaderGroup extends ShaderGroup {

	public EnhancedShaderGroup(TextureManager p_i1050_1_, IResourceManager resourceManagerIn, Framebuffer mainFramebufferIn, ResourceLocation p_i1050_4_) throws JsonSyntaxException, IOException {
		super(p_i1050_1_, resourceManagerIn, mainFramebufferIn, p_i1050_4_);
	}

	private static Field shaders = ReflectionHelper.findField(ShaderGroup.class, "listShaders", "field_148031_d");

	public List<Shader> getShaders() {
		try {
			@SuppressWarnings("unchecked")
			List<Shader> list = (List<Shader>) shaders.get(this);

			return list;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return new ArrayList<>();
		}
	}
}
