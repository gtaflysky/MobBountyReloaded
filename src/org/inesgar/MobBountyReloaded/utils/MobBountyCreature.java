package org.inesgar.MobBountyReloaded.utils;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public enum MobBountyCreature
{
    BAT("Bat"), BLAZE("Blaze"), CAVESPIDER("CaveSpider"), CHICKEN("Chicken"), COW(
            "Cow"), CREEPER("Creeper"), ELECTRIFIED_CREEPER(
            "ElectrifiedCreeper"), ENDERDRAGON("Enderdragon"), ENDERMAN(
            "Enderman"), GHAST("Ghast"), GIANT("Giant"), IRONGOLEM("IronGolem"), MAGMACUBE(
            "MagmaCube"), MONSTER("Monster"), MOOSHROOM("Mooshroom"), OCELOT(
            "Ocelot"), PIG("Pig"), PIG_ZOMBIE("PigZombie"), PLAYER("Player"), SELF_TAMED_CAT(
            "SelfTamedCat"), SELF_TAMED_WOLF("SelfTamedWolf"), SHEEP("Sheep"), SILVERFISH(
            "Silverfish"), SKELETON("Skeleton"), SLIME("Slime"), SNOWGOLEM(
            "SnowGolem"), SPIDER("Spider"), SQUID("Squid"), TAMED_CAT(
            "TamedCat"), TAMED_WOLF("TamedWolf"), UNKNOWN("Unknown"), VILLAGER(
            "Villager"), WITCH("Witch"), WITHER("Wither"), WITHER_SKELETON(
            "WitherSkeleton"), WOLF("Wolf"), ZOMBIE("Zombie");

    public static MobBountyCreature fromName(String name)
    {
        for (MobBountyCreature id : MobBountyCreature.values())
        {
            if (id.name().equalsIgnoreCase(name))
                return id;
        }

        return null;
    }

    public static MobBountyCreature valueOf(Entity entity, String playerName)
    {
        if (entity instanceof Chicken)
            return MobBountyCreature.CHICKEN;
        else if (entity instanceof Player)
            return MobBountyCreature.PLAYER;
        else if (entity instanceof Witch)
            return MobBountyCreature.WITCH;
        else if (entity instanceof Wither)
            return MobBountyCreature.WITHER;
        else if (entity instanceof Cow)
        {
            if (entity.getType().equals(EntityType.MUSHROOM_COW))
                return MobBountyCreature.MOOSHROOM;
            return MobBountyCreature.COW;
        }
        else if (entity instanceof Creeper)
        {
            Creeper creeper = (Creeper) entity;

            if (creeper.isPowered())
                return MobBountyCreature.ELECTRIFIED_CREEPER;
            return MobBountyCreature.CREEPER;
        }
        else if (entity instanceof Ghast)
            return MobBountyCreature.GHAST;
        else if (entity instanceof Giant)
            return MobBountyCreature.GIANT;
        else if (entity instanceof IronGolem)
            return MobBountyCreature.IRONGOLEM;
        else if (entity instanceof Pig)
            return MobBountyCreature.PIG;
        else if (entity instanceof PigZombie)
            return MobBountyCreature.PIG_ZOMBIE;
        else if (entity instanceof Sheep)
            return MobBountyCreature.SHEEP;
        else if (entity instanceof Skeleton)
        {
            Skeleton skeleton = (Skeleton) entity;
            if (skeleton.getSkeletonType() == SkeletonType.WITHER)
                return MobBountyCreature.WITHER_SKELETON;
            return MobBountyCreature.SKELETON;
        }
        else if (entity instanceof Slime)
        {
            if (entity.getType().equals(EntityType.MAGMA_CUBE))
                return MobBountyCreature.MAGMACUBE;
            return MobBountyCreature.SLIME;
        }
        else if (entity instanceof Spider)
        {
            if (entity.getType().equals(EntityType.CAVE_SPIDER))
                return MobBountyCreature.CAVESPIDER;
            return MobBountyCreature.SPIDER;
        }
        else if (entity instanceof Ocelot)
        {
            Ocelot ocelot = (Ocelot) entity;
            if (!ocelot.isTamed())
                return MobBountyCreature.OCELOT;
            else if (ocelot.getOwner() instanceof Player
                    && ((Player) ocelot.getOwner()).getName().equalsIgnoreCase(
                            playerName))
                return MobBountyCreature.SELF_TAMED_CAT;
            else
                return MobBountyCreature.TAMED_CAT;
        }
        else if (entity instanceof Squid)
            return MobBountyCreature.SQUID;
        else if (entity instanceof Wolf)
        {
            Wolf wolf = (Wolf) entity;
            if (!wolf.isTamed())
                return MobBountyCreature.WOLF;
            else if (wolf.getOwner() instanceof Player
                    && ((Player) wolf.getOwner()).getName().equalsIgnoreCase(
                            playerName))
                return MobBountyCreature.SELF_TAMED_WOLF;
            else
                return MobBountyCreature.TAMED_WOLF;
        }
        else if (entity instanceof Zombie)
            return MobBountyCreature.ZOMBIE;
        else if (entity instanceof Monster)
        {
            if (entity.getType().equals(EntityType.SILVERFISH))
                return MobBountyCreature.SILVERFISH;
            else if (entity.getType().equals(EntityType.ENDERMAN))
                return MobBountyCreature.ENDERMAN;
            else if (entity.getType().equals(EntityType.BLAZE))
                return MobBountyCreature.BLAZE;
            return MobBountyCreature.MONSTER;
        }
        else if (entity instanceof Ocelot)
            return MobBountyCreature.OCELOT;
        else if (entity instanceof Snowman)
            return MobBountyCreature.SNOWGOLEM;
        else if (entity instanceof Villager)
            return MobBountyCreature.VILLAGER;
        else if (entity instanceof EnderDragon)
            return MobBountyCreature.ENDERDRAGON;

        return MobBountyCreature.UNKNOWN;
    }

    private final String _name;

    private MobBountyCreature(final String name)
    {
        _name = name;
    }

    public String getName()
    {
        return _name;
    }
}
