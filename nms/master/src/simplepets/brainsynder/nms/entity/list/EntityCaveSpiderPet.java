package simplepets.brainsynder.nms.entity.list;

import net.minecraft.world.entity.EntityType;
import simplepets.brainsynder.api.entity.hostile.IEntityCaveSpiderPet;
import simplepets.brainsynder.api.pet.PetType;
import simplepets.brainsynder.api.user.PetUser;
import simplepets.brainsynder.nms.entity.EntityPetOverride;

/**
 * NMS: {@link net.minecraft.world.entity.monster.CaveSpider}
 */
public class EntityCaveSpiderPet extends EntityPetOverride implements IEntityCaveSpiderPet {
    public EntityCaveSpiderPet(PetType type, PetUser user) {
        super(EntityType.CAVE_SPIDER, type, user);
    }
}
