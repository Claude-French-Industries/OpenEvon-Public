package club.evon.ui;


public interface RenderableElement {
   public void draw(float x, float y);

   public float getWidth();

   public float getHeight();

   public int getPriority();
}
