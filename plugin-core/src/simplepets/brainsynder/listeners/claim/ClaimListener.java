package simplepets.brainsynder.listeners.claim;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import simplepets.brainsynder.api.plugin.SimplePets;
import simplepets.brainsynder.api.user.PetUser;

// FOX
public class ClaimListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (!this.isTrustedIn(event.getPlayer(), event.getPlayer().getLocation())) {
            PetUser user = SimplePets.getPlugin().getUserManager().getPetUser(event.getPlayer()).orElse(null);
            if (user == null) return;
            user.getPetEntities().forEach(iEntityPet -> iEntityPet.setPetVisible(false));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ()) return;
        boolean trustedFrom = this.isTrustedIn(event.getPlayer(), event.getFrom());
        boolean trustedTo = this.isTrustedIn(event.getPlayer(), event.getTo());
        if (trustedTo != trustedFrom) {
            PetUser user = SimplePets.getPlugin().getUserManager().getPetUser(event.getPlayer()).orElse(null);
            if (user == null) return;
            if (trustedTo) {
                user.getPetEntities().forEach(iEntityPet -> iEntityPet.setPetVisible(true));
            } else {
                user.getPetEntities().forEach(iEntityPet -> iEntityPet.setPetVisible(false));
            }
        }
    }

    public boolean isTrustedIn(Player player, Location location) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        if (claim == null) return true;
        return claim.checkPermission(player, ClaimPermission.Access, null) == null;
    }
}
