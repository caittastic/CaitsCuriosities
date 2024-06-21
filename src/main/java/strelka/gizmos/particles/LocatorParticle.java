package strelka.gizmos.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class LocatorParticle extends TextureSheetParticle {
  private boolean stoppedByCollision;
  private static final double MAXIMUM_COLLISION_VELOCITY_SQUARED = Mth.square(100.0D);

  protected LocatorParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                             SpriteSet spriteSet, double xd, double yd, double zd) {
    super(level, xCoord, yCoord, zCoord, xd, yd, zd);

    this.friction = 1F;
    this.xd = xd;
    this.yd = yd;
    this.zd = zd;
    this.quadSize *= 0.85F;
    this.lifetime = 20;
    this.setSpriteFromAge(spriteSet);

    this.rCol = 1f;
    this.gCol = 1f;
    this.bCol = 1f;
  }

  @Override
  public void tick() {
    this.xo = this.x;
    this.yo = this.y;
    this.zo = this.z;
    if (this.age++ >= this.lifetime) {
      this.remove();
    } else {
      this.move(this.xd, this.yd, this.zd);
    }
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public Provider(SpriteSet spriteSet) {
      this.sprites = spriteSet;
    }

    public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                   double x, double y, double z,
                                   double dx, double dy, double dz) {
      return new LocatorParticle(level, x, y, z, this.sprites, dx, dy, dz);
    }
  }
}