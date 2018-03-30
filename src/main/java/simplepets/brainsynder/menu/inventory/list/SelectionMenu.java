package simplepets.brainsynder.menu.inventory.list;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import simple.brainsynder.storage.IStorage;
import simple.brainsynder.storage.StorageList;
import simple.brainsynder.utils.ObjectPager;
import simplepets.brainsynder.PetCore;
import simplepets.brainsynder.api.event.inventory.PetInventoryOpenEvent;
import simplepets.brainsynder.menu.holders.SelectionHolder;
import simplepets.brainsynder.menu.inventory.CustomInventory;
import simplepets.brainsynder.menu.items.Item;
import simplepets.brainsynder.menu.items.list.Air;
import simplepets.brainsynder.pet.PetDefault;
import simplepets.brainsynder.player.PetOwner;
import simplepets.brainsynder.storage.PetMap;
import simplepets.brainsynder.storage.PetTypeStorage;

import java.io.File;
import java.util.*;

public class SelectionMenu extends CustomInventory {
    private List<PetDefault> availableTypes;
    private Map<String, ObjectPager<PetTypeStorage>> pagerMap;
    private PetMap<String, IStorage<PetTypeStorage>> petMap;

    public SelectionMenu(File file) {
        super(file);
    }

    @Override
    public void loadDefaults() {
        availableTypes = new ArrayList<>();
        pagerMap = new HashMap<>();
        petMap = new PetMap<>();

        defaults.put("size", "54");
        defaults.put("title", "&a&lSelect a Pet:");

        Map<Integer, String> object = new HashMap<> ();
        Arrays.asList(
                11, 12, 13, 14, 15, 16, 17,
                20, 21, 22, 23, 24, 25, 26,
                29, 30, 31, 32, 33, 34, 35,
                38, 39, 40, 41, 42, 43, 44
        ).forEach(slot -> object.put(slot, "air"));
        object.put(4, "saves");
        object.put(6, "name");
        object.put(9, "data");
        object.put(46, "previouspage");
        object.put(49, "ride");
        object.put(50, "remove");
        object.put(51, "hat");
        object.put(54, "nextpage");

        Set<Map.Entry<Integer, String>> set = object.entrySet();
        List<Map.Entry<Integer, String>> list = new ArrayList<>(set);
        Collections.sort(list, Comparator.comparing(o -> (o.getKey())));

        JSONArray array = new JSONArray();
        for (Map.Entry<Integer, String> entry : list) {
            JSONObject json = new JSONObject();
            json.put("slot", entry.getKey());
            json.put("item", entry.getValue());
            array.add(json);
        }

        defaults.put("slots", array);

        for (PetDefault type : PetCore.get().getTypeManager().getTypes()) {
            if (type.isSupported() && type.isEnabled()) {
                availableTypes.add(type);
            }
        }
    }

    @Override
    public void open(PetOwner owner, int page) {
        if (!isEnabled()) return;
        pageSave.put(owner.getPlayer().getName(), page);
        Inventory inv = Bukkit.createInventory(new SelectionHolder(), getSize(), getTitle());
        int placeHolder = inv.getSize();
        int maxPets = 0;
        while (placeHolder > 0) {
            int slot = (placeHolder - 1);
            if (getSlots().containsKey(slot)) {
                Item item = getSlots().get(slot);
                if (item instanceof Air) {
                    maxPets++;
                } else {
                    inv.setItem(placeHolder - 1, PetCore.get().getItemLoaders().PLACEHOLDER.getItemBuilder().build());
                }
            } else {
                inv.setItem(placeHolder - 1, PetCore.get().getItemLoaders().PLACEHOLDER.getItemBuilder().build());
            }
            placeHolder--;
        }

        IStorage<PetTypeStorage> petTypes = new StorageList<>();
        for (PetDefault type : availableTypes) {
            if (type.hasPermission(owner.getPlayer()) && PetCore.get().getConfiguration().getBoolean("Remove-Item-If-No-Permission")) {
                petTypes.add(new PetTypeStorage(type));
            }else{
                petTypes.add(new PetTypeStorage(type));
            }
        }
        if (petTypes.getSize() == 0) {
            owner.getPlayer().sendMessage(PetCore.get().getMessages().getString("No-Permission"));
            return;
        }

        ObjectPager<PetTypeStorage> pages = new ObjectPager<>(maxPets, petTypes.toArrayList());
        if (pagerMap.containsKey(owner.getPlayer().getName())) {
            pages = pagerMap.get(owner.getPlayer().getName());
        } else {
            pagerMap.put(owner.getPlayer().getName(), pages);
        }

        getSlots().forEach((slot, item) -> {
            if (item.isEnabled() && item.addItemToInv(owner, this))
                inv.setItem(slot, item.getItemBuilder().build());
        });

        PetInventoryOpenEvent event = new PetInventoryOpenEvent(pages.getPage(page), owner.getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) return;
        IStorage<ItemStack> types = event.getItems().copy();
        while (types.hasNext()) {
            inv.addItem(types.next());
        }
        petMap.put(owner.getPlayer().getName(), petTypes);
        owner.getPlayer().openInventory(inv);
    }

    public PetMap<String, IStorage<PetTypeStorage>> getPetMap() {
        return petMap;
    }

    public ObjectPager<PetTypeStorage> getPages(PetOwner owner) {
        if (pagerMap.containsKey(owner.getPlayer().getName()))
            return pagerMap.get(owner.getPlayer().getName());
        return null;
    }

    @Override
    public void reset(PetOwner owner) {
        super.reset(owner);

        petMap.remove(owner.getPlayer().getName());
        pagerMap.remove(owner.getPlayer().getName());
    }
}
