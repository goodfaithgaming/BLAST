package ladysnake.blast.common.item;

import ladysnake.blast.common.entity.BombEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class TriggerBombItem extends BombItem {
    public TriggerBombItem(Item.Settings settings, EntityType<BombEntity> entityType) {
        super(settings, entityType);
    }

    @Override
    protected void playSoundEffects(World world, PlayerEntity playerEntity) {
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.NEUTRAL, 0.8F, 0.5F);
    }
}
