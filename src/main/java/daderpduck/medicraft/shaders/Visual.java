package daderpduck.medicraft.shaders;

import com.google.gson.JsonSyntaxException;
import daderpduck.medicraft.util.EnhancedShaderGroup;
import daderpduck.medicraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static net.minecraft.client.Minecraft.getMinecraft;
import static net.minecraft.client.Minecraft.getSystemTime;

public abstract class Visual extends VisualHandler {
	private final ResourceLocation location;
	private EnhancedShaderGroup shaderGroup;
	private boolean deleted = false;
	private final long startTime;
	private final int lifeTime;
	private final Minecraft mc;

	public Visual(String name, int lifeTime) {
		this.location = new ResourceLocation(Reference.MOD_ID, "shaders/post/" + name + ".json");
		this.startTime = getSystemTime();
		this.lifeTime = lifeTime;

		this.mc = getMinecraft();
	}

	protected float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}

	protected float getProgress() {
		if (this.lifeTime < 0) {
			return 0;
		}

		return (getSystemTime() - this.startTime) / (float) this.lifeTime;
	}


	public void delete() {
		if (!this.deleted) {
			removeShader(this);

			this.deleted = true;
		}
	}

	public void render(float partialTicks) {
		if (this.deleted)
			return;

		if (this.shaderGroup == null) {
			//Bind shader group
			try {
				this.shaderGroup = new EnhancedShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), location);
				this.shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
			} catch (JsonSyntaxException | IOException e) {
				e.printStackTrace();
			}

		} else {
			//Delete shader group if lifetime exceeded
			if (lifeTime >= 0 && getProgress() >= 1) {
				delete();
				return;
			}

			//Render shader group
			onRender(partialTicks);
			if (this.shaderGroup != null)
				this.shaderGroup.render(partialTicks);
		}
	}

	public EnhancedShaderGroup getShaderGroup() {
		return shaderGroup;
	}

	public abstract void onRender(float partialTicks);

	public abstract boolean canRender();

	//Change uniform stuff
	private List<ShaderUniform> getShaderUniforms(String uniformName) {
		List<ShaderUniform> shaderUniforms = new LinkedList<>();

		if (this.shaderGroup != null) {
			for (Shader shader : this.shaderGroup.getShaders()) {
				ShaderUniform shaderUniform = shader.getShaderManager().getShaderUniform(uniformName);
				if (shaderUniform != null) {
					shaderUniforms.add(shaderUniform);
				}
			}
		}
		return shaderUniforms;
	}

	protected void changeUniform(String uniformName, float x) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(x);
		}
	}

	protected void changeUniform(String uniformName, float x, float y) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(x, y);
		}
	}

	protected void changeUniform(String uniformName, float x, float y, float z) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(x, y, z);
		}
	}

	protected void changeUniform(String uniformName, float x, float y, float z, float w) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(x, y, z, w);
		}
	}

	protected void changeUniform(String uniformName, int x, int y, int z, int w) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(x, y, z, w);
		}
	}

	protected void changeUniform(String uniformName, float[] f) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(f);
		}
	}

	protected void changeUniform(String uniformName, float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
		}
	}

	protected void changeUniform(String uniformName, Matrix4f matrix) {
		for (ShaderUniform shaderUniform : getShaderUniforms(uniformName)) {
			shaderUniform.set(matrix);
		}
	}
}
