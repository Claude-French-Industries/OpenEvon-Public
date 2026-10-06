package club.evon.data;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec2f;




public record RotationHitResult(Vec2f rotation, HitResult hitResult) {
}
