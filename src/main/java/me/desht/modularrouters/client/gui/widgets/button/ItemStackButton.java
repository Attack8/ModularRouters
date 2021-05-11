package me.desht.modularrouters.client.gui.widgets.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.desht.modularrouters.client.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class ItemStackButton extends TexturedButton {
    private final ItemStack renderStack;
    private final boolean flat;

    public ItemStackButton(int x, int y, int width, int height, ItemStack renderStack, boolean flat, IPressable pressable) {
        super(x, y, width, height, pressable);
        this.renderStack = renderStack;
        this.flat = flat;
    }

    public ItemStack getRenderStack() {
        return renderStack;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            Minecraft mc = Minecraft.getInstance();
            mc.getTextureManager().bind(TEXTURE);
//            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getYImage(this.isHovered);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            if (!flat) {
                this.blit(matrixStack, this.x, this.y, i * 16, 0, this.width, this.height);
            }
            int x = this.x + (width - 18) / 2;
            int y = this.y + (height - 18) / 2;
            GuiUtil.renderItemStack(matrixStack, mc, getRenderStack(), x, y, "");
        }
    }

    @Override
    protected int getTextureX() {
        return 0;
    }

    @Override
    protected int getTextureY() {
        return 0;
    }

}
