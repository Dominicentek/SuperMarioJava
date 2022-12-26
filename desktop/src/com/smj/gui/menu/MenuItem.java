package com.smj.gui.menu;

public abstract class MenuItem {
    public String label;
    private boolean overriddenInput;
    public abstract void update(Menu menu);
    public abstract void updateSelected(Menu menu, int index);
    public final void overrideInput() {
        overriddenInput = true;
    }
    public final void overrideInput(boolean override) {
        overriddenInput = override;
    }
    public final boolean overriddenInput() {
        return overriddenInput;
    }
}
