package club.evon.data;
import net.minecraft.text.Text;




public record ScoreboardLine(Text name, Text score, int scoreWidth) {
   public Text getScore() {
      return this.score;
   }
}
