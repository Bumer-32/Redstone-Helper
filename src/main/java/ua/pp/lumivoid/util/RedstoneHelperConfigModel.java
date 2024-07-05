package ua.pp.lumivoid.util;

import io.wispforest.owo.config.annotation.*;
import ua.pp.lumivoid.ClientOptions;
import ua.pp.lumivoid.Constants;

@SuppressWarnings("unused")
@Modmenu(modId = Constants.MOD_ID)
@Config(name = "redstone-helper-config", wrapperName = "RedstoneHelperConfig")
public class RedstoneHelperConfigModel {
    @SectionHeader("general")
    @Nest
    @Expanded
    public UpdateCheckNest updateCheckNest = new UpdateCheckNest();

    public static class  UpdateCheckNest {
        public Boolean enableUpdateCheck = true;
        public Boolean checkForRelease = true;
        public Boolean checkForBeta = false;
        public Boolean checkForAlpha = false;
    }

    @SectionHeader("interface")
    public Boolean darkPanels = true;
    public Boolean enableBackgroundBlur = true;

    @SectionHeader("autoWire")
    public AutoWire defaultAutoWireMode = ClientOptions.INSTANCE.getAutoWireMode();
}