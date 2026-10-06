package club.evon.data;
import java.awt.Color;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;




public record OreEspEntry(BlockPos pos, BlockState state, Color color) {
}
