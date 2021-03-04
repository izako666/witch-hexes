package com.izako666.hexmod.particles;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class CustomParticleRenderType implements IParticleRenderType{

	SimpleParticle particle;
	public CustomParticleRenderType(SimpleParticle particle) {
		this.particle = particle;
	}
	@Override
	public void beginRender(BufferBuilder buffer, TextureManager p_217600_2_) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        p_217600_2_.bindTexture(particle.texture);
        buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
        

	}

	@Override
	public void finishRender(Tessellator tess) {
		tess.draw();
		
	}


 }