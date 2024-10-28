package simplepets.brainsynder.nms.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import simplepets.brainsynder.api.pet.PetType;
import simplepets.brainsynder.api.user.PetUser;

public class EntityPetOverride extends EntityPet implements PlayerRideableJumping {
    public EntityPetOverride(EntityType<? extends Mob> entitytypes, PetType type, PetUser user) {
        super(entitytypes, type, user);
    }

    // TODO: Investigate why pet riding is not working on 1.21.3, it wont work using the old travel method or the new tickRidden method

    @Override
    protected void tickRidden(Player entityhuman, Vec3 vec3d) {
        super.tickRidden(entityhuman, vec3d);
        Vec2 vec2f = this.getRiddenRotation(entityhuman);
        this.setRot(vec2f.y, vec2f.x);
        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
    }

    protected Vec2 getRiddenRotation(LivingEntity entityliving) {
        return new Vec2(entityliving.getXRot() * 0.5F, entityliving.getYRot());
    }

    @Override
    public void onPlayerJump(int i) {

    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void handleStartJump(int i) {

    }

    @Override
    public void handleStopJump() {

    }
}
