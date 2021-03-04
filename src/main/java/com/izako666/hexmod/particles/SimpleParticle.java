package com.izako666.hexmod.particles;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SimpleParticle extends TexturedParticle  {

	protected ResourceLocation texture;
	private static final ImmutableList<VertexFormatElement> ELEMENTS = ImmutableList.of(
			DefaultVertexFormats.POSITION_3F, DefaultVertexFormats.TEX_2F, DefaultVertexFormats.COLOR_4UB,
			DefaultVertexFormats.NORMAL_3B, DefaultVertexFormats.PADDING_1B);
	public static final VertexFormat VERTEX_FORMAT = new VertexFormat(ELEMENTS);
	private boolean hasRotation = false;
	private boolean hasMotionDecay = true;

	public SimpleParticle(World world, ResourceLocation texture, double posX, double posY, double posZ, double motionX,
			double motionY, double motionZ) {
		super((ClientWorld) world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		this.texture = texture;
		this.maxAge = 30 + this.rand.nextInt(10);
		this.age = 0;
		this.particleScale = 0.2F;
		this.particleGravity = 0F;
		this.setColor(1.0F, 1.0F, 1.0F);
		this.canCollide = false;

		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}

	@Override
	public void tick() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.particleGravity != 0)
			this.motionY = -0.04D * this.particleGravity;

		this.move(this.motionX, this.motionY, this.motionZ);
		if (this.hasMotionDecay) {
			this.motionX *= 0.99D;
			this.motionY *= 0.99D;
			this.motionZ *= 0.99D;
		}

		if (this.age + 5 >= this.maxAge) {
			if (this.particleScale > 0)
				this.setParticleScale(this.particleScale /= 1.01F);

			if (this.particleAlpha > 0)
				this.particleAlpha -= 0.15;
		}

		if (this.age++ >= this.maxAge || this.onGround)
			this.setExpired();
	}

	public SimpleParticle setParticleAlpha(float f) {
		this.setAlphaF(f);
		return this;
	}

	public SimpleParticle setParticleScale(float f) {
		this.particleScale = f;
		return this;
	}

	public SimpleParticle setParticleGravity(float f) {
		this.particleGravity = f;
		return this;
	}

	public SimpleParticle setParticleAge(int i) {
		this.maxAge = i + this.rand.nextInt(10);
		return this;
	}

	public SimpleParticle setHasRotation() {
		this.hasRotation = true;
		return this;
	}

	public SimpleParticle setHasMotionDecay(boolean flag) {
		this.hasMotionDecay = flag;
		return this;
	}

	public SimpleParticle setParticleTexture(ResourceLocation rs) {
		this.texture = rs;
		return this;
	}

	public BlockPos getPos() {
		return new BlockPos(this.posX, this.posY, this.posZ);
	}

	@Override
	public IParticleRenderType getRenderType() {
		return new CustomParticleRenderType(this);
	}

	@Override
	protected float getMaxU() {
		return 1;
	}

	@Override
	protected float getMaxV() {
		return 1;
	}

	@Override
	protected float getMinU() {
		return 0;
	}

	@Override
	protected float getMinV() {
		return 0;
	}

	public static class Factory implements IParticleFactory<GenericParticleData> {
		public Factory() {
		}

		@Override
		public Particle makeParticle(GenericParticleData data, ClientWorld world, double posX, double posY, double posZ,
				double velX, double velY, double velZ) {
			SimpleParticle particle = new SimpleParticle(world, data.getTexture(), posX, posY, posZ, data.getMotionX(),
					data.getMotionY(), data.getMotionZ());
			particle.setColor(data.getRed(), data.getGreen(), data.getBlue());
			particle.setParticleAlpha(data.getAlpha());
			particle.setParticleScale(data.getSize());
			particle.setParticleAge(data.getLife());
			particle.setHasMotionDecay(data.hasMotionDecay());

			if (data.hasRotation())
				particle.setHasRotation();

			return particle;
		}

	
	}
	
	public static void spawnParticles(IParticleData data, ServerWorld world, double posX, double posY, double posZ)
	{
		IPacket<?> ipacket = new SSpawnParticlePacket(data, true, (float) posX, (float) posY, (float) posZ, 0, 0, 0, 0, 1);

		for (int j = 0; j < world.getPlayers().size(); ++j)
		{
			ServerPlayerEntity player = world.getPlayers().get(j);
			BlockPos blockpos = new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ());
			if (blockpos.withinDistance(new Vector3d(posX, posY, posZ), 512))
			{
				player.connection.sendPacket(ipacket);
			}
		}
	}
}