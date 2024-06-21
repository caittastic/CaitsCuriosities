package strelka.gizmos.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class TeleportPoofParticle extends TextureSheetParticle{
  private final SpriteSet sprites;

  protected TeleportPoofParticle(ClientLevel cLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, float pQuadSizeMultiplier, SpriteSet pSprites){
    super(cLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
    this.friction = 0.96F;
    this.gravity = -0.1F;
    this.speedUpWhenYMotionIsBlocked = true;
    this.sprites = pSprites;
    this.xd *= 0.1F;
    this.yd *= 0.1F;
    this.zd *= 0.1F;
    this.xd += pXSpeed;
    this.yd += pYSpeed;
    this.zd += pZSpeed;
    float rOffset = cLevel.getRandom().nextFloat() / 3;
    float bOffset = cLevel.getRandom().nextFloat() / 3;
    this.rCol = (195f / 255f) + rOffset - (rOffset / 2);
    this.gCol = (80f / 255f);
    this.bCol = (191f / 255f) + bOffset - (bOffset / 2);
    this.quadSize *= 0.75F * pQuadSizeMultiplier;
    this.lifetime = (int)((double)8 / ((double)cLevel.random.nextFloat() * 0.8D + 0.2D) * (double)pQuadSizeMultiplier);
    this.lifetime = Math.max(this.lifetime, 1);
    this.setSpriteFromAge(pSprites);
    this.hasPhysics = true;
  }

  public ParticleRenderType getRenderType(){
    return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
  }

  public float getQuadSize(float pScaleFactor){
    return this.quadSize * Mth.clamp(((float)this.age + pScaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
  }

  public void tick(){
    super.tick();
    this.setSpriteFromAge(this.sprites);
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<SimpleParticleType>{
    private final SpriteSet sprites;

    public Provider(SpriteSet pSprites){
      this.sprites = pSprites;
    }

    public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
      return new TeleportPoofParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, 1.0F, this.sprites);
    }
  }
}