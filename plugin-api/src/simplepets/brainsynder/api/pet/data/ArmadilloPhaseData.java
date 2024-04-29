package simplepets.brainsynder.api.pet.data;

import lib.brainsynder.item.ItemBuilder;
import org.bukkit.Material;
import simplepets.brainsynder.api.Namespace;
import simplepets.brainsynder.api.entity.passive.IEntityArmadilloPet;
import simplepets.brainsynder.api.pet.PetData;
import simplepets.brainsynder.api.wrappers.ArmadilloPhase;
import simplepets.brainsynder.api.wrappers.CatType;

@Namespace(namespace = "phase")
public class ArmadilloPhaseData extends PetData<IEntityArmadilloPet> {
    public ArmadilloPhaseData() {
        for (CatType type : CatType.values()) {
            addDefaultItem(type.name(), new ItemBuilder(Material.PLAYER_HEAD)
                    .withName("&#c8c8c8{name}: &a"+type.name())
                    .setTexture(type.getTexture()));
        }
    }

    @Override
    public Object getDefaultValue() {
        return ArmadilloPhase.IDLE;
    }

    @Override
    public void onRightClick(IEntityArmadilloPet entity) {
        entity.setPhase(ArmadilloPhase.getPrevious(entity.getPhase()));
    }

    @Override
    public void onLeftClick(IEntityArmadilloPet entity) {
        entity.setPhase(ArmadilloPhase.getNext(entity.getPhase()));
    }

    @Override
    public Object value(IEntityArmadilloPet entity) {
        return entity.getPhase();
    }
}
