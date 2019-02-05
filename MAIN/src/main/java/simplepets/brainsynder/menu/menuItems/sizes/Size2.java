package simplepets.brainsynder.menu.menuItems.sizes;

import org.bukkit.Material;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.ISizable;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class Size2 extends MenuItemAbstract {

    public Size2(PetDefault type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public Size2(PetDefault type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        return type.getDataItemByName("size2", 0);
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        List<ItemBuilder> items = new ArrayList<>();
        items.add(new ItemBuilder(Material.SLIME_BLOCK).setTexture("&6&lSize: &e2"));
        return items;
    }

    @Override
    public void onLeftClick() {
        if (entityPet instanceof ISizable) {
            ISizable slime = (ISizable) entityPet;
            slime.setSize(2);
        }

    }
}
