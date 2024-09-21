package ua.pp.lumivoid.owocomponents

import io.wispforest.owo.ui.component.ButtonComponent
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.Consumer

/**
 * Usual button component BUT with texture
 * Texture MUST BE IN modid/textures/gui/sprites/yourtexture
 * @param texture texture id
 */
class TexturedButton(val texture: Identifier, onPress: Consumer<ButtonComponent>): ButtonComponent(Text.empty(), onPress) {
    override fun renderWidget(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        super.renderWidget(context, mouseX, mouseY, delta)
        context?.drawGuiTexture(texture, this.x + 2, this.y + 2, this.width - 4, this.width - 4)
    }
}