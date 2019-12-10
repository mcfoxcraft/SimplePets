package simplepets.brainsynder.menu.menuItems.bee;

import simple.brainsynder.api.ItemBuilder;
import simple.brainsynder.utils.SkullType;
import simplepets.brainsynder.api.entity.IEntityPet;
import simplepets.brainsynder.api.entity.passive.IEntityBeePet;
import simplepets.brainsynder.menu.menuItems.base.MenuItemAbstract;
import simplepets.brainsynder.pet.PetDefault;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stung extends MenuItemAbstract<IEntityBeePet> {
    public Stung(PetDefault type, IEntityPet entityPet) {
        super(type, entityPet);
    }
    public Stung(PetDefault type) {
        super(type);
    }

    @Override
    public ItemBuilder getItem() {
        ItemBuilder item = type.getDataItemByName("stung", 0);
        if (item != null) item.withName(item.getName().replace("%value%", String.valueOf(!entityPet.hasStung())));

        return item;
    }

    @Override
    public List<ItemBuilder> getDefaultItems() {
        ItemBuilder item = ItemBuilder.getSkull(SkullType.PLAYER)
                .setTexture("http://textures.minecraft.net/texture/e400223f1fa54741d421d7e8046409d5f3e15c7f4364b1b739940208f3b686d4");
        item.withName("&6Has Stinger: &e%value%");
        return new ArrayList<>(Collections.singleton(item));
    }

    @Override
    public void onLeftClick() {
        entityPet.setHasStung(!entityPet.hasStung());
    }
}
