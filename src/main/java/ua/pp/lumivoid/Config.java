package ua.pp.lumivoid;

import ua.pp.lumivoid.util.AutoWire;
import ua.pp.lumivoid.util.RedstoneHelperConfig;

public class Config {
    public static final RedstoneHelperConfig CONFIG = RedstoneHelperConfig.createAndLoad();

    public Boolean enableUpdateCheck = CONFIG.updateCheckNest.enableUpdateCheck();
    public Boolean checkForRelease = CONFIG.updateCheckNest.checkForRelease();
    public Boolean checkForBeta = CONFIG.updateCheckNest.checkForBeta();
    public Boolean checkForAlpha = CONFIG.updateCheckNest.checkForAlpha();

    public Boolean darkPanels = CONFIG.darkPanels();
    public Boolean enableBackgroundBlur = CONFIG.enableBackgroundBlur();

    public AutoWire defaultAutoWireMode = CONFIG.defaultAutoWireMode();
}
