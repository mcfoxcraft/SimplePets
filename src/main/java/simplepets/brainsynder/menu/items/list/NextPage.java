package simplepets.brainsynder.menu.items.list;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import simple.brainsynder.utils.ObjectPager;
import simplepets.brainsynder.menu.inventory.CustomInventory;
import simplepets.brainsynder.menu.inventory.list.SelectionMenu;
import simplepets.brainsynder.menu.items.Item;
import simplepets.brainsynder.player.PetOwner;
import simplepets.brainsynder.storage.PetTypeStorage;

import java.io.File;

public class NextPage extends Item {
    public NextPage(File file) {
        super(file);
    }

    @Override
    public void loadDefaults() {
        defaults.put(MATERIAL, "ARROW");
        defaults.put(DATA, "0");
        defaults.put(AMOUNT, "1");
        defaults.put(DISPLAY_NAME, "&6&l&m----&6&l>");
        JSONArray lore = new JSONArray();
        lore.add("&7Click Here to go");
        lore.add("&7the next page");
        defaults.put(LORE_ENABLED, "true");
        defaults.put(LORE, lore);
        JSONObject custom = new JSONObject();
        custom.put(ENABLED, "true");
        custom.put(SKULL_OWNER, "SimpleAPI");
        custom.put(TEXTURE_URL, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJmOTEwYzQ3ZGEwNDJlNGFhMjhhZjZjYzgxY2Y0OGFjNmNhZjM3ZGFiMzVmODhkYjk5M2FjY2I5ZGZlNTE2In19fQ==");
        defaults.put(CUSTOM_SKULL, custom);
    }

    @Override
    public void onClick(PetOwner owner, CustomInventory inventory) {
        if (inventory instanceof SelectionMenu) {
            SelectionMenu menu = (SelectionMenu)inventory;
            ObjectPager<PetTypeStorage> pages = menu.getPages(owner);
            int current = menu.getCurrentPage(owner);

            if (pages == null) return;

            if (pages.totalPages() > current) menu.open(owner, (current+1));
        }
    }
}