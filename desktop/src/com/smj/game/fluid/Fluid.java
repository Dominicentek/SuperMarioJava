package com.smj.game.fluid;

import com.smj.util.Readable;
import com.smj.util.bjson.ObjectElement;

public class Fluid implements Readable {
    public final FluidMovement movement;
    public final FluidType type;
    public Fluid(FluidMovement movement, FluidType type) {
        this.movement = movement;
        this.type = type;
    }
    public Fluid(ObjectElement element) {
        movement = new FluidMovement(
            Short.toUnsignedInt(element.getShort("highTide")),
            Short.toUnsignedInt(element.getShort("lowTide")),
            Short.toUnsignedInt(element.getShort("stayTime")),
            Short.toUnsignedInt(element.getShort("moveTime")),
            Byte.toUnsignedInt(element.getByte("movementType"))
        );
        type = FluidType.values()[element.getByte("type")];
    }
    public String toString() {
        return "h=" + movement.highTide + ";l=" + movement.lowTide + ";s=" + movement.stayTime + ";f=" + movement.moveTime + ";m=" + movement.movementType + ";t=" + type;
    }
}
