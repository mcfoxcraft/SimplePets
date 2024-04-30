/*
 * Copyright 2023
 * BSDevelopment <https://bsdevelopment.org>
 */

package simplepets.brainsynder.utils;

import lib.brainsynder.ServerVersion;

public enum VersionFields implements FieldValues {
    v1_19 (
            "f", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bs", // net.minecraft.world.entity.EntityType$factory
            "ca", // net.minecraft.core.MappedRegistry$frozen
            "cc", // net.minecraft.core.MappedRegistry$intrusiveHolderCache
            "", // Not in this version
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "r", // net.minecraft.world.entity.Entity$boardingCooldown
            "Q" // net.minecraft.server.MinecraftServer$running
    ),
    v1_19_1 (
            "f", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bs", // net.minecraft.world.entity.EntityType$factory
            "ca", // net.minecraft.core.MappedRegistry$frozen
            "cc", // net.minecraft.core.MappedRegistry$intrusiveHolderCache
            "", // Not in this version
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "r", // net.minecraft.world.entity.Entity$boardingCooldown
            "Q" // net.minecraft.server.MinecraftServer$running
    ),
    v1_19_2 (
            "f", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bs", // net.minecraft.world.entity.EntityType$factory
            "ca", // net.minecraft.core.MappedRegistry$frozen
            "cc", // net.minecraft.core.MappedRegistry$intrusiveHolderCache
            "", // Not in this version
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "r", // net.minecraft.world.entity.Entity$boardingCooldown
            "Q" // net.minecraft.server.MinecraftServer$running
    ),
    v1_19_3 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bu", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "h", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "r", // net.minecraft.world.entity.Entity$boardingCooldown
            "Q" // net.minecraft.server.MinecraftServer$running
    ),
    v1_19_4 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bA", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "h", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bi", // net.minecraft.world.entity.LivingEntity$jumping
            "G", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bA", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "h", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bk", // net.minecraft.world.entity.LivingEntity$jumping
            "I", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_1 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bA", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "h", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bk", // net.minecraft.world.entity.LivingEntity$jumping
            "I", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_2 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bA", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "h", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bj", // net.minecraft.world.entity.LivingEntity$jumping
            "J", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_3 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bC", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "g", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bj", // net.minecraft.world.entity.LivingEntity$jumping
            "J", // net.minecraft.world.entity.Entity$boardingCooldown
            "S" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_4 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bC", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "g", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bj", // net.minecraft.world.entity.LivingEntity$jumping
            "J", // net.minecraft.world.entity.Entity$boardingCooldown
            "S" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_5 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bF", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "g", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "K", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    ),
    v1_20_6 (
            "e", // net.minecraft.network.syncher.SynchedEntityData$itemsById
            "bF", // net.minecraft.world.entity.EntityType$factory
            "l", // net.minecraft.core.MappedRegistry$frozen
            "m", // net.minecraft.core.MappedRegistry$unregisteredIntrusiveHolders
            "g", // net.minecraft.core.registries.BuiltInRegistries#ENTITY_TYPE
            "bn", // net.minecraft.world.entity.LivingEntity$jumping
            "K", // net.minecraft.world.entity.Entity$boardingCooldown
            "R" // net.minecraft.server.MinecraftServer$running
    );

    public static VersionFields fromServerVersion (ServerVersion version) {
        return valueOf(version.name());
    }

    private final String entityDataMap, entityFactory, registryFrozen, registryIntrusive, entityRegistry, entityJump, resetCooldown, isRunning;

    VersionFields (String entityDataMap, String entityFactory, String registryFrozen, String registryIntrusive, String entityRegistry, String entityJump,String resetCooldown, String isRunning) {
        this.entityDataMap = entityDataMap;
        this.entityFactory = entityFactory;
        this.registryFrozen = registryFrozen;
        this.registryIntrusive = registryIntrusive;
        this.entityRegistry = entityRegistry;
        this.entityJump = entityJump;
        this.resetCooldown = resetCooldown;
        this.isRunning = isRunning;
    }

    @Override
    public String getEntityDataMapField() {
        return entityDataMap;
    }

    @Override
    public String getEntityFactoryField() {
        return entityFactory;
    }

    @Override
    public String getRegistryFrozenField() {
        return registryFrozen;
    }

    @Override
    public String getRegistryIntrusiveField() {
        return registryIntrusive;
    }

    @Override
    public String getEntityRegistryField() {
        return entityRegistry;
    }

    @Override
    public String getEntityJumpField() {
        return entityJump;
    }

    @Override
    public String getRideCooldownField() {
        return resetCooldown;
    }

    @Override
    public String getServerRunningField() {
        return isRunning;
    }
}
