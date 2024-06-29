package ua.pp.lumivoid.util;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import ua.pp.lumivoid.ClientOptions;
import ua.pp.lumivoid.Constants;

@SuppressWarnings("unused")
@Modmenu(modId = Constants.MOD_ID)
@Config(name = "redstone-helper-config", wrapperName = "RedstoneHelperConfig")
public class RedstoneHelperConfigModel {
    @SectionHeader("general")
    public Boolean enableUpdateCheck = true;

    @SectionHeader("interface")
    public Boolean darkPanels = true;
    public Boolean enableBackgroundBlur = true;

    @SectionHeader("autoWire")
    public AutoWire defaultAutoWireMode = ClientOptions.INSTANCE.getAutoWireMode();
}