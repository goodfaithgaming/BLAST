package ladysnake.blast.common.world;

import ladysnake.blast.common.util.ProtectionsProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class KnockbackExplosion extends CustomExplosion {
    public KnockbackExplosion(World world, Entity entity, double x, double y, double z, float power) {
        super(world, entity, x, y, z, power, null, DestructionType.KEEP);
    }

    @Override
    public void affectWorld(boolean particles) {
        Vec3d source = getPosition();
        if (particles) {
            for (int i = 0; i < 500; i++) {
                world.addParticle(ParticleTypes.SNEEZE, source.x, source.y, source.z, this.random.nextGaussian() / 5, this.random.nextGaussian() / 5, this.random.nextGaussian() / 5);
            }
        }
        for (Entity entity : affectedEntities) {
            if (ProtectionsProvider.canInteractEntity(entity, damageSource)) {
                double distance = Math.sqrt(entity.squaredDistanceTo(source)) / (getPower() * 2);
                if (distance <= 1.0D) {
                    double dX = entity.getX() - source.x;
                    double dY = entity.getEyeY() - source.y;
                    double dZ = entity.getZ() - source.z;
                    double product = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
                    if (product != 0) {
                        dX /= product;
                        dY /= product;
                        dZ /= product;
                        double strength = (1 - distance) * getExposure(source, entity);
                        if (entity instanceof PlayerEntity player) {
                            getAffectedPlayers().put(player, new Vec3d(dX * strength, dY * strength, dZ * strength));
                        } else {
                            strength *= 2;
                        }
                        if (entity instanceof LivingEntity living) {
                            strength *= 1 - living.getAttributeValue(EntityAttributes.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE);
                        }
                        entity.addVelocity(dX * strength, dY * strength, dZ * strength);
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldDamageEntities() {
        return false;
    }
}
