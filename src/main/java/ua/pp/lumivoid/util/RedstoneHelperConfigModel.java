package ua.pp.lumivoid.util;

import io.wispforest.owo.config.annotation.*;
import ua.pp.lumivoid.ClientOptions;
import ua.pp.lumivoid.Constants;
import ua.pp.lumivoid.util.features.AutoWire;

@SuppressWarnings("unused")
@Modmenu(modId = Constants.MOD_ID)
@Config(name = "redstone-helper/redstone-helper-config", wrapperName = "RedstoneHelperConfig")
public class RedstoneHelperConfigModel {
    @SectionHeader("general")
    @Nest
    @Expanded
    public UpdateCheckNest updateCheckNest = new UpdateCheckNest();

    public static class UpdateCheckNest {
        public Boolean enableUpdateCheck = true;
        public Boolean checkForRelease = true;
        public Boolean checkForBeta = false;
        public Boolean checkForAlpha = false;
        public Boolean showUpToDateNotification = true;
    }

    @SectionHeader("interface")
    public Boolean darkPanels = true;
    public Boolean enableBackgroundBlur = true;
    public ToastPositions toastPosition = ToastPositions.TOP_MIDDLE_RIGHT;

    @SectionHeader("features")
    @Nest
    @Expanded
    public AutoWireNest autoWireNest = new AutoWireNest();

    public static class AutoWireNest {
        public AutoWire defaultAutoWireMode = ClientOptions.INSTANCE.getAutoWireMode();
        public Boolean rememberLastAutoWireMode = true;
    }

    @Nest
    @Expanded
    public QuickTpNest quickTpNest = new QuickTpNest();

    public static class QuickTpNest {
        @RangeConstraint(min = 1, max = 1000)
        public Integer quickTpDistance = 50;
        public Boolean quickTpIncludeFluids = false;
    }
}