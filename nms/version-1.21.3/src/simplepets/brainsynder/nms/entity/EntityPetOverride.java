package simplepets.brainsynder.nms.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import simplepets.brainsynder.api.event.entity.PetMoveEvent;
import simplepets.brainsynder.api.event.entity.movment.PetJumpEvent;
import simplepets.brainsynder.api.event.entity.movment.PetRideEvent;
import simplepets.brainsynder.api.pet.PetType;
import simplepets.brainsynder.api.plugin.SimplePets;
import simplepets.brainsynder.api.user.PetUser;
import simplepets.brainsynder.nms.VersionTranslator;

import java.lang.reflect.Field;

public class EntityPetOverride extends EntityPet {
    public EntityPetOverride(EntityType<? extends Mob> entitytypes, PetType type, PetUser user) {
        super(entitytypes, type, user);
    }

    // TODO: Investigate why pet riding is not working on 1.21.3, it wont work using the old travel method or the tickRidden method

    @Override
    public void travel(Vec3 vec3d) {
        if ((getPetType() == null) || (getUser() == null)) return;

        if ((passengers == null)
                || (!isOwnerRiding())) {
            super.travel(vec3d);
            return;
        }

        LivingEntity passenger = (LivingEntity) passengers.stream().filter(entity -> entity instanceof LivingEntity).filter(entity -> entity instanceof ServerPlayer).findFirst().orElseGet(null);
        if (passenger == null) {
            System.out.println("- No passenger found");
            return;
        }
        System.out.println("- passenger: " + passenger.getName().toString());

        if (doIndirectAttach) {
            if (getFirstPassenger() instanceof SeatEntity seat) {
                // orient the seat entity correctly. Seems to fix the issue
                // where ridden horses are not oriented properly
                seat.setYRot(passenger.getYRot());
                seat.yRotO = this.getYRot();
                seat.setXRot(passenger.getXRot() * 0.5F);
                seat.setRot(this.getYRot(), this.getXRot());
                seat.yHeadRot = this.yBodyRot = this.getYRot();
            }
        }

        this.setYRot(passenger.getYRot());
        this.yRotO = this.getYRot();
        this.setXRot(passenger.getXRot() * 0.5F);
        this.setRot(this.getYRot(), this.getXRot());
        this.yHeadRot = this.yBodyRot = this.getYRot();

        double strafe = passenger.xxa * 0.5;
        System.out.println("- strafe: " + strafe + " | Passenger: " + passenger.xxa + " | Vec3d: "+vec3d.x);
        double vertical = vec3d.y;
        double forward = passenger.zza;
        if (forward <= 0) {
            forward *= 0.25F;
        }

        PetMoveEvent moveEvent = new PetRideEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(moveEvent);
        if (moveEvent.isCancelled()) return;

        double speed = VersionTranslator.getWalkSpeed(this);

        Field jumpField = VersionTranslator.getJumpField();
        if ((jumpField != null) && (!passengers.isEmpty())) {
            SimplePets.getPetConfigManager().getPetConfig(getPetType()).ifPresent(config -> {
                try {
                    boolean flight = false;
                    double height = getJumpHeight();

                    if (config.canFly(getUser().getPlayer())) {
                        flight = true;
                        height = 0.3;
                    }

                    PetJumpEvent jumpEvent = new PetJumpEvent(this, height);
                    Bukkit.getServer().getPluginManager().callEvent(jumpEvent);
                    if ((!jumpEvent.isCancelled()) && jumpField.getBoolean(passenger)) {
                        System.out.println("- jump field: " + jumpField.getName());
                        if (flight || this.onGround) {
                            setDeltaMovement(getDeltaMovement().x, jumpEvent.getJumpHeight(), getDeltaMovement().z);
                            this.hasImpulse = true;
                        }
                    }
                } catch (IllegalArgumentException | IllegalStateException | IllegalAccessException ignored) {
                }
            });
        }

        this.setSpeed((float) speed);
        System.out.println("- speed: " + speed);
        System.out.println("- forward: " + forward);
        System.out.println("- vertical: " + vertical);
        System.out.println("- strafe: " + strafe);
        super.travel(new Vec3(strafe, vertical, forward));
    }

    @Override
    protected boolean isImmobile() {
        return false;
    }

// TODO: This was my attempt to fix the riding bug by using the code for AbstractHorse
//
//    @Override
//    protected void tickRidden(Player entityhuman, Vec3 vec3d) {
//        super.tickRidden(entityhuman, vec3d);
//        Vec2 vec2f = this.getRiddenRotation(entityhuman);
//        this.setRot(vec2f.y, vec2f.x);
//        this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
//    }
//
//    protected Vec2 getRiddenRotation(LivingEntity entityliving) {
//        return new Vec2(entityliving.getXRot() * 0.5F, entityliving.getYRot());
//    }
//
//    @Override
//    public void onPlayerJump(int i) {
//
//    }
//
//    @Override
//    public boolean canJump() {
//        return true;
//    }
//
//    @Override
//    public void handleStartJump(int i) {
//
//    }
//
//    @Override
//    public void handleStopJump() {
//
//    }
}
