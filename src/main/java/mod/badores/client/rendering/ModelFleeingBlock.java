
// Date: 16.05.2014 17:58:57
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX


package mod.badores.client.rendering;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFleeingBlock extends ModelBase {
    //fields
    ModelRenderer blockBox;

    public ModelFleeingBlock() {
        textureWidth = 64;
        textureHeight = 32;

        blockBox = new ModelRenderer(this, 0, 0);
        blockBox.addBox(-8F, -8F, -8F, 16, 16, 16);
        blockBox.setRotationPoint(0F, 16F, 0F);
        blockBox.setTextureSize(64, 32);
        blockBox.mirror = true;
        setRotation(blockBox, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        blockBox.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}