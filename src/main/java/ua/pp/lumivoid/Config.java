package ua.pp.lumivoid;

import ua.pp.lumivoid.util.AutoWire;
import ua.pp.lumivoid.util.RedstoneHelperConfig;

public class Config {
    public static final RedstoneHelperConfig CONFIG = RedstoneHelperConfig.createAndLoad();

    public Boolean darkPanels = CONFIG.darkPanels();
    public Boolean enableBackgroundBlur = CONFIG.enableBackgroundBlur();
    public Boolean enableUpdateCheck = CONFIG.enableUpdateCheck();
    public AutoWire defaultAutoWireMode = CONFIG.defaultAutoWireMode();
}
