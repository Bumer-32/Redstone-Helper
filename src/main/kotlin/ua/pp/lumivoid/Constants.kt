package ua.pp.lumivoid

import io.wispforest.owo.network.OwoNetChannel
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.SharedConstants
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Constants {
    const val MOD_ID = "redstone-helper"
    const val MOD_MODRINTH_ID = "cwYR2Bh1"
    @Suppress("DEPRECATION") const val MINECRAFT_VERSION = SharedConstants.VERSION_NAME
    val MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().metadata.version.toString()
    val LOGGER = LoggerFactory.getLogger(MOD_ID)!!
    val NET_CHANNEL: OwoNetChannel = OwoNetChannel.create(Identifier.of(MOD_ID, "main"))
    val aMinecraftClass: Identifier = Identifier.of(MOD_ID, "main")
    val TOAST_ID = Identifier.of(MOD_ID, "toast")!!
    val CONFIG_FOLDER_PATH = "${System.getProperty("user.dir")}\\config\\${MOD_ID}"
    val TEXT_SPACE = Text.literal(" ")!!

    //URLS
    object URLS {
        const val MODRINTH_API_URL = "https://api.modrinth.com/v2/project/${MOD_MODRINTH_ID}/version"
        const val GITHUB_MANUAL_OWO_LIB_URL = "https://github.com/Bumer-32/Redstone-Helper/blob/main/doc/owo%20lib.png?raw=true"
    }

    //TEXTS_ID
    @Suppress("unused")
    object LocalizeIds {
        const val MOD_REDSTONEHELPERTITLE = "redstone-helper.mod.redstone_helper_title"
        const val MOD_SHORTREDSTONEHELPERTITLE = "redstone-helper.mod.short_redstone_helper_title"
        const val MOD_STYLEDSHORTREDSTONEHELPERTITLE = "redstone-helper.mod.styled_short_redstone_helper_title"

        const val KEYBINDING_CATEGORY_BASIC = "redstone-helper.keybinding.category.basic"
        const val KEYBINDING_KEY_CALC = "redstone-helper.keybinding.key.calc"
        const val KEYBINDING_KEY_SWITCHAUTOWIRE = "redstone-helper.keybinding.key.switch_autowire"
        const val KEYBINDING_KEY_PREVIOUSAUTOWIREMODE = "redstone-helper.keybinding.key.previous_autowire_mode"
        const val KEYBINDING_KEY_NEXTAUTOWIREMODE = "redstone-helper.keybinding.key.next_autowire_mode"
        const val KEYBINDING_KEY_QUICKTP = "redstone-helper.keybinding.key.quickTp"

        const val FEATURE_CALCULATOR_TITLE = "redstone-helper.feature.calculator.title"
        const val FEATURE_CALCULATOR_EXPRESSION = "redstone-helper.feature.calculator.expression"
        const val FEATURE_CALCULATOR_RESULT = "redstone-helper.feature.calculator.result"
        const val FEATURE_CALCULATOR_INVALIDEXPRESSION = "redstone-helper.feature.calculator.invalid_expression"

        const val FEATURE_AUTOWIRE_TITLE = "redstone-helper.feature.auto_wire.title"
        const val FEATURE_AUTOWIRE_ENABLEAUTOWIRE = "redstone-helper.feature.auto_wire.enable_auto_wire"
        const val FEATURE_AUTOWIRE_AUTOWIREON = "redstone-helper.feature.auto_wire.auto_wire_on"
        const val FEATURE_AUTOWIRE_AUTOWIREOFF = "redstone-helper.feature.auto_wire.auto_wire_off"
        const val FEATURE_AUTOWIRE_SELECTAUTOWIREMODE = "redstone-helper.feature.auto_wire.select_auto_wire_mode"
        const val FEATURE_AUTOWIRE_CURRENTAUTOWIREMODE = "redstone-helper.feature.auto_wire.current_auto_wire_mode"
        const val FEATURE_AUTOWIRE_MODES_AUTOREDSTONE = "redstone-helper.feature.auto_wire.modes.auto_redstone"
        const val FEATURE_AUTOWIRE_MODES_AUTOLINE = "redstone-helper.feature.auto_wire.modes.auto_line"
        const val FEATURE_AUTOWIRE_MODES_AUTOREPEATER = "redstone-helper.feature.auto_wire.modes.auto_repeater"
        const val FEATURE_AUTOWIRE_MODES_AUTOCOMPARATOR = "redstone-helper.feature.auto_wire.modes.auto_comparator"
        const val FEATURE_AUTOWIRE_MODES_CHEAPAUTOCOMPARATOR = "redstone-helper.feature.auto_wire.modes.cheap_auto_comparator"
        const val FEATURE_AUTOWIRE_DESCRIPTION_AUTOREDSTONE = "redstone-helper.feature.auto_wire.description.auto_redstone"
        const val FEATURE_AUTOWIRE_DESCRIPTION_AUTOLINE = "redstone-helper.feature.auto_wire.description.auto_line"
        const val FEATURE_AUTOWIRE_DESCRIPTION_AUTOREPEATER = "redstone-helper.feature.auto_wire.description.auto_repeater"
        const val FEATURE_AUTOWIRE_DESCRIPTION_AUTOCOMPARATOR = "redstone-helper.feature.auto_wire.description.auto_comparator"
        const val FEATURE_AUTOWIRE_DESCRIPTION_CHEAPAUTOCOMPARATOR = "redstone-helper.feature.auto_wire.description.cheap_auto_comparator"

        const val FEATURE_BITSOPERATIONS_TITLE = "redstone-helper.feature.bits_operations.title"
        const val FEATURE_BITSOPERATIONS_CONVERTER = "redstone-helper.feature.bits_operations.converter"
        const val FEATURE_BITSOPERATIONS_INVALIDEXPRESSION = "redstone-helper.feature.bits_operations.invalid_expression"
        const val FEATURE_BITSOPERATIONS_EXPRESSION = "redstone-helper.feature.bits_operations.expression"
        const val FEATURE_BITSOPERATIONS_TOBIT = "redstone-helper.feature.bits_operations.to_bit"
        const val FEATURE_BITSOPERATIONS_TOHEX = "redstone-helper.feature.bits_operations.to_hex"
        const val FEATURE_BITSOPERATIONS_FROMBIT = "redstone-helper.feature.bits_operations.from_bit"
        const val FEATURE_BITSOPERATIONS_FROMHEX = "redstone-helper.feature.bits_operations.from_hex"
        const val FEATURE_BITSOPERATIONS_BITCALCULATOR = "redstone-helper.feature.bits_operations.bit_calculator"
        const val FEATURE_BITSOPERATIONS_BITRESULT = "redstone-helper.feature.bits_operations.bit_result"
        const val FEATURE_BITSOPERATIONS_RESULT = "redstone-helper.feature.bits_operations.result"

        const val FEATURE_MACRO_TITLE = "redstone-helper.feature.macro.title"
        const val FEATURE_MACRO_EDITMACROTITLE = "redstone-helper.feature.macro.edit_macro_title"
        const val FEATURE_MACRO_SETTINGSMACROSBUTTON = "redstone-helper.feature.macro.settings_macros_button"
        const val FEATURE_MACRO_NEWMACROBUTTON = "redstone-helper.feature.macro.new_macro_button"
        const val FEATURE_MACRO_DONE = "redstone-helper.feature.macro.done"
        const val FEATURE_MACRO_CANCEL = "redstone-helper.feature.macro.cancel"
        const val FEATURE_MACRO_KEYBIND = "redstone-helper.feature.macro.key_bind"
        const val FEATURE_MACRO_KEYBINDNONE = "redstone-helper.feature.macro.key_bind_none"
        const val FEATURE_MACRO_RESET = "redstone-helper.feature.macro.reset"
        const val FEATURE_MACRO_ENABLEDFORKEYBINDS = "redstone-helper.feature.macro.enabled_for_keybinds"
        const val FEATURE_MACRO_ADDCOMMAND = "redstone-helper.feature.macro.add_command"
        const val FEATURE_MACRO_MACRONOTFOUND = "redstone-helper.feature.macro.macro_not_found"

        const val FEATURE_INSTALAMPS_INSTAON = "redstone-helper.feature.insta_lamps.insta_on"
        const val FEATURE_INSTALAMPS_INSTAOFF = "redstone-helper.feature.insta_lamps.insta_off"

        const val FEATURE_AIRPLACE_AIRPLACEON = "redstone-helper.feature.airplace.airplace_on"
        const val FEATURE_AIRPLACE_AIRPLACEOFF = "redstone-helper.feature.airplace.airplace_off"

        const val FEATURE_CALCREDSTONESIGNAL_CALCULATEDSIGNAL = "redstone-helper.feature.calc_redstone_signal.calculated_signal"

        const val FEATURE_COLORCODE_SUCCESS = "redstone-helper.feature.colorcode.success"

        const val FEATURE_UPDATE_SUCCESS = "redstone-helper.feature.update.success"

        const val STUFF_VERSIONCHECKER_UPTODATE = "redstone-helper.stuff.version_checker.up_to_date"
        const val STUFF_VERSIONCHECKER_NEEDUPDATE = "redstone-helper.stuff.version_checker.need_update"
        const val STUFF_VERSIONCHECKER_UNABLETOCHECKVERSION = "redstone-helper.stuff.version_checker.unable_to_check_version"
        const val STUFF_VERSIONCHECKER_TOCHECKVERSION = "redstone-helper.stuff.version_checker.to_check_version"
        const val STUFF_VERSIONCHECKER_SKIPVERSION = "redstone-helper.stuff.version_checker.skip_version"
        const val STUFF_VERSIONCHECKER_SKIPPING = "redstone-helper.stuff.version_checker.skipping"
        const val STUFF_VERSIONCHECKER_CLEARSKIPS = "redstone-helper.stuff.version_checker.clear_skips"

        const val SYSTEM_INFO_SERVERINCOMPATIBLEVERSION = "redstone-helper.system.info.server_incompatible_version"

        const val STUFF_INFO_SUCCESS = "redstone-helper.stuff.info.success"
        const val STUFF_INFO_ERROR_BLOCKNOTFOUND = "redstone-helper.stuff.info.error.block_not_found"
        const val STUFF_INFO_ERROR_INVALIDBLOCKINVENTORY = "redstone-helper.stuff.info.error.invalid_block_inventory"
        const val STUFF_INFO_ERROR_MISSINGARGUMENTS = "redstone-helper.stuff.info.error.missing_arguments"
        const val STUFF_INFO_ERROR_NOPERMISSION = "redstone-helper.stuff.info.error.no_permission"
        const val STUFF_INFO_ERROR_SELECTREGION = "redstone-helper.stuff.info.error.select_region"

        const val MANUAL_MANUAL = "redstone-helper.manual.manual"
        const val MANUAL_PREVIOUS = "redstone-helper.manual.previous"
        const val MANUAL_NEXT = "redstone-helper.manual.next"
        const val MANUAL_MODRINTH = "redstone-helper.manual.modrinth"
        const val MANUAL_GITHUB = "redstone-helper.manual.github"
        const val MANUAL_CROWDIN = "redstone-helper.manual.crowdin"
        const val MANUAL_WELCOME_1 = "redstone-helper.manual.welcome.1"

        val FUNNY_COUNT = Text.translatable("dontlocalize.redstone-helper.stuff.funny_count").string.toInt()

    }
}
