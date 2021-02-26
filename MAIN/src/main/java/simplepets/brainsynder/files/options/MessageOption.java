package simplepets.brainsynder.files.options;

import lib.brainsynder.files.options.YamlOption;

import java.util.Arrays;
import java.util.List;

public enum MessageOption implements YamlOption {
    PREFIX ("prefix", "&eSimplePets &6>>"),
    NO_PERMISSION ("no_permission", "{prefix} &cYou do not have permission."),
    SUMMONED_ALL_PETS ("summon.all_pets", "{prefix} &7Successfully summoned {count} pets!"),
    SUMMONED_PET ("summon.pet", "{prefix} &7Successfully summoned the {type} pet!"),

    PURCHASE_ADD("purchased.added", "{prefix} &7{type} was added to the purchased pets of &c{player}"),
    PURCHASE_REMOVE ("purchased.removed", "{prefix} &7{type} was removed from the purchased pets of &c{player}"),
    PURCHASE_LIST_PREFIX ("purchased.list_prefix", "{prefix} &7Owned Pets: "),

    MISSING_PET_TYPE ("pet_type.missing", "{prefix} &cMissing pet type."),
    INVALID_PET_TYPE ("pet_type.invalid", "{prefix} &cSorry, &7'{arg}' &cis not a valid pet type."),
    PET_NOT_REGISTERED ("pet_type.not_registered", "{prefix} &cSorry, {type} is not registered."),
    PET_NOT_REGISTERED_LORE ("pet_type.not_registered_lore", "&cNOT REGISTERED"),
    PET_NOT_SUPPORTED ("pet_type.not_supported", "{prefix} &cSorry, {type} is not supported for this version."),
    PET_NOT_SUPPORTED_LORE ("pet_type.not_supported_lore", "&cNOT SUPPORTED"),

    INVALID_NBT ("nbt.invalid", "{prefix} &cInvalid nbt has been entered."),
    INVALID_NBT_MESSAGE ("nbt.error", "{prefix} &c{message}"),
    FAILED_SUMMON ("summon.failed", "{prefix} &cSorry, the {type} pet was unable to be spawned at the moment."),
    PLAYER_NOT_ONLINE ("player_not_online", "{prefix} &c{player} is not online (spelling?)"),
    REMOVED_PET ("remove.removed_pet", "{prefix} &7Successfully removed the {type} pet!"),
    REMOVED_ALL_PETS ("remove.all_pets", "{prefix} &7Successfully removed {count} pets!"),
    REMOVED_NOT_SPAWNED ("remove.not_spawned", "{prefix} &cSorry, {type} is not spawned."),
    MODIFY_COMPOUND ("modify.compound", "{prefix} &7NBT compound: &e{compound}"),
    MODIFY_APPLIED ("modify.applied", "{prefix} &7Data has been applied to the {type} pet!"),
    RENAME_VIA_CHAT ("rename.via_chat", "{prefix} &7Type your pets new name in chat:"),
    RENAME_VIA_CHAT_CANCEL ("rename.cancel", "{prefix} &cPet renaming has been canceled");

    private final String path;
    private final Object defaultValue;
    private final List<String> oldPaths;

    MessageOption(String path, Object defaultValue) {
        this(path, defaultValue, new String[0]);
    }

    MessageOption(String path, Object defaultValue, String... oldPaths) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.oldPaths = Arrays.asList(oldPaths);
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getDefault() {
        return defaultValue;
    }

    @Override
    public List<String> getOldPaths() {
        return oldPaths;
    }
}