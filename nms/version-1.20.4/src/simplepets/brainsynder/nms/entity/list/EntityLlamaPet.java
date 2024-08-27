package simplepets.brainsynder.nms.entity.list;

import lib.brainsynder.nbt.StorageTagCompound;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.DyeColor;
import simplepets.brainsynder.api.entity.passive.IEntityLlamaPet;
import simplepets.brainsynder.api.pet.PetType;
import simplepets.brainsynder.api.user.PetUser;
import simplepets.brainsynder.api.wrappers.ColorWrapper;
import simplepets.brainsynder.api.wrappers.LlamaColor;
import simplepets.brainsynder.nms.entity.branch.EntityDonkeyAbstractPet;
import simplepets.brainsynder.nms.utils.PetDataAccess;

/**
 * NMS: {@link net.minecraft.world.entity.animal.horse.Llama}
 */
public class EntityLlamaPet extends EntityDonkeyAbstractPet implements IEntityLlamaPet {
    private static final EntityDataAccessor<Integer> STRENGTH = SynchedEntityData.defineId(EntityLlamaPet.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(EntityLlamaPet.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(EntityLlamaPet.class, EntityDataSerializers.INT);

    public EntityLlamaPet(PetType type, PetUser user) {
        this(EntityType.LLAMA, type, user);
    }

    public EntityLlamaPet(EntityType<? extends Mob> llama, PetType type, PetUser user) {
        super(llama, type, user);
        doIndirectAttach = false;
    }

    @Override
    public void populateDataAccess(PetDataAccess dataAccess) {
        super.populateDataAccess(dataAccess);
        dataAccess.define(STRENGTH, 0);
        dataAccess.define(COLOR, -1);
        dataAccess.define(VARIANT, 0);
    }

    @Override
    public StorageTagCompound asCompound() {
        StorageTagCompound object = super.asCompound();
        object.setString("skin", getSkinColor().name());
        object.setString("color", getColorWrapper().name());
        return object;
    }

    @Override
    public void applyCompound(StorageTagCompound object) {
        if (object.hasKey("skin")) setSkinColor(LlamaColor.getByName(object.getString("skin")));
        if (object.hasKey("color"))
            setColorWrapper(ColorWrapper.getByName(object.getString("color")));
        super.applyCompound(object);
    }

    @Override
    public void setSkinColor(LlamaColor skinColor) {
        this.entityData.set(VARIANT, skinColor.ordinal());
    }

    @Override
    public LlamaColor getSkinColor() {
        return LlamaColor.getByID(entityData.get(VARIANT));
    }

    @Override
    public ColorWrapper getColorWrapper() {
        ColorWrapper color = ColorWrapper.getByWoolData((byte) ((int) entityData.get(COLOR)));
        if (color == null) color = ColorWrapper.getByDyeData((byte) ((int) entityData.get(COLOR)));
        return color;
    }

    @Override
    public void setColorWrapper(ColorWrapper color) {
        if (color == ColorWrapper.NONE) {
            entityData.set(COLOR, -1);
            return;
        }
        entityData.set(COLOR, DyeColor.byId(color.getWoolData()).getId());
    }
}
