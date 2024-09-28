package ua.pp.lumivoid.redstonehelper.registration

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.GameRules
import net.minecraft.world.GameRules.BooleanRule


object GamerulesRegistration {
    var DO_CONTAINER_DROPS: GameRules.Key<BooleanRule>? = null
    var INSTANT_LAMPS_TURN_OFF: GameRules.Key<BooleanRule>? = null

    fun register() {
        DO_CONTAINER_DROPS = GameRuleRegistry.register("doContainerDrops", GameRules.Category.DROPS, GameRuleFactory.createBooleanRule(true))
        INSTANT_LAMPS_TURN_OFF = GameRuleRegistry.register("instantLampsTurnOff", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false))
    }
}