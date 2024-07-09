package ua.pp.lumivoid;

import ua.pp.lumivoid.util.ToastPositions;
import ua.pp.lumivoid.util.features.AutoWire;
import ua.pp.lumivoid.util.RedstoneHelperConfig;

public class Config {
    public static final RedstoneHelperConfig CONFIG = RedstoneHelperConfig.createAndLoad();

    public Boolean enableUpdateCheck = CONFIG.updateCheckNest.enableUpdateCheck();
    public Boolean checkForRelease = CONFIG.updateCheckNest.checkForRelease();
    public Boolean checkForBeta = CONFIG.updateCheckNest.checkForBeta();
    public Boolean checkForAlpha = CONFIG.updateCheckNest.checkForAlpha();

    public Boolean darkPanels = CONFIG.darkPanels();
    public Boolean enableBackgroundBlur = CONFIG.enableBackgroundBlur();
    public ToastPositions toastPosition = CONFIG.toastPosition();

    public AutoWire defaultAutoWireMode = CONFIG.defaultAutoWireMode();

    public Integer quickTpDistance = CONFIG.quickTp.quickTpDistance();
    public Boolean quickTpIncludeFluids = CONFIG.quickTp.quickTpIncludeFluids();
}
