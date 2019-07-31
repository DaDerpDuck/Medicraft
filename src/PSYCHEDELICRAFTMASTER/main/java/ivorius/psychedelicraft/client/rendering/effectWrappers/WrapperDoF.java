/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.psychedelicraft.client.rendering.effectWrappers;

import ivorius.ivtoolkit.rendering.IvDepthBuffer;
import ivorius.psychedelicraft.Psychedelicraft;
import ivorius.psychedelicraft.client.ClientProxy;
import ivorius.psychedelicraft.client.rendering.shaders.ShaderDoF;
import net.minecraft.client.Minecraft;

/**
 * Created by lukas on 26.04.14.
 */
public class WrapperDoF extends ShaderWrapper<ShaderDoF>
{
    public WrapperDoF(String utils)
    {
        super(new ShaderDoF(Psychedelicraft.logger), getRL("shaderBasic.vert"), getRL("shaderDof.frag"), utils);
    }

    public boolean isActive()
    {
        return (ClientProxy.dofFocalBlurFar > 0.0f || ClientProxy.dofFocalBlurNear > 0.0f)
                && (ClientProxy.dofFocalPointNear > 0.0f || ClientProxy.dofFocalPointFar < getCurrentZFar());
    }

    protected float getCurrentZFar()
    {
        return (float) (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16);
    }

    @Override
    public void setShaderValues(float partialTicks, int ticks, IvDepthBuffer depthBuffer)
    {
        if (depthBuffer != null && isActive())
        {
            shaderInstance.depthTextureIndex = depthBuffer.getDepthTextureIndex();

            shaderInstance.zNear = 0.05f;
            shaderInstance.zFar = getCurrentZFar();

            shaderInstance.focalPointNear = ClientProxy.dofFocalPointNear / shaderInstance.zFar;
            shaderInstance.focalPointFar = ClientProxy.dofFocalPointFar / shaderInstance.zFar;
            shaderInstance.focalBlurFar = ClientProxy.dofFocalBlurFar;
            shaderInstance.focalBlurNear = ClientProxy.dofFocalBlurNear;
        }
        else
        {
            shaderInstance.focalBlurFar = 0.0f;
            shaderInstance.focalBlurNear = 0.0f;
        }
    }

    @Override
    public void update()
    {

    }

    @Override
    public boolean wantsDepthBuffer(float partialTicks)
    {
        return isActive();
    }
}
