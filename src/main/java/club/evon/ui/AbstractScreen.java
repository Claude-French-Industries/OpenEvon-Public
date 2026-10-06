package club.evon.ui;
import java.awt.Color;
import club.evon.util.render.ColorUtil;
import club.evon.util.render.RenderUtil;




public abstract class AbstractScreen {
   private static final Color PANEL_BACKGROUND = ColorUtil.withAlpha(Color.WHITE, 0.1F);
   private static final Color PANEL_BORDER = ColorUtil.withAlpha(new Color(239, 224, 227), 0.2F);

   public abstract void render(float floatValue, float floatValue2, int windowWidth, int windowHeight);

   public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
   }

   public void keyPressed(int keyCode) {
   }

   public static void drawPanel(float x, float y, float width, float height, float radius) {
      float floatValue = 1.0F;
      float floatValue2 = 0.5F;
      RenderUtil.strokeRoundedRect(x + 0.5F, y + 0.5F, width - 1.0F, height - 1.0F, radius, 1.0F, PANEL_BORDER);
      RenderUtil.fillRoundedRect(x, y, width, height, radius, PANEL_BACKGROUND);
   }
}
