// 
// Decompiled by Procyon v0.6.0
// 

package club.evon.util;
import org.json.JSONException;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.StainedGlassBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.TintedGlassBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.world.BlockView;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import net.minecraft.util.math.Direction;
import java.util.Collection;
import java.util.Comparator;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;
import club.evon.ClientContext;
import club.evon.module.BaseModule;
import club.evon.util.math.PathNode;




public final class PathFinder
{
    private final Vec3d start;
    private final Vec3d end;
    private ArrayList<Vec3d> path;
    private final ArrayList<PathNode> closed;
    private final ArrayList<PathNode> open;
    private final double goalDistanceSq = 9.5;
    private static String guard;
    private static final long KEY;
    
    public PathFinder(final Vec3d startVec3d, final Vec3d endVec3d) {
        final long n = PathFinder.KEY ^ 0x4AAA64E47234L;
        getGuard();
        this.path = new ArrayList<Vec3d>();
        this.closed = new ArrayList<PathNode>();
        this.open = new ArrayList<PathNode>();
        this.start = startVec3d;
        this.end = endVec3d;
        if (BaseModule.getStateArray() == null) {
            setGuard("dVLwmb");
        }
    }
    
    public void findPath() {
        this.search(1000, 4);
    }
    
    public void search(final int maxIterations, final int branchDepth) {
        final long n = PathFinder.KEY ^ 0x47D1BB6F292AL;
        final String m = getGuard();
        this.path.clear();
        this.open.clear();
        final String s = m;
        final ArrayList<Vec3d> list = new ArrayList<Vec3d>();
        list.add(this.start);
        this.open.add(new PathNode(this.start, (PathNode)null, list, this.start.squaredDistanceTo(this.end), 0.0, 0.0));
        int n2 = 0;
        if (s != null && n2 < maxIterations) {
            this.open.sort((Comparator<? super PathNode>)new PathNodeComparator());
            this.open.sort((Comparator<? super PathNode>)new PathNodeComparator());
            this.path = this.closed.get(0).getPath();
            return;
        }
        outer:
        while (n2 < maxIterations) {
            this.open.sort((Comparator<? super PathNode>)new PathNodeComparator());
            int n4 = 0;
            if (this.open.isEmpty()) {
                break;
            }
            final ArrayList<PathNode> list4 = new ArrayList<PathNode>(this.open);
            for (final PathNode next : list4) {
                ++n4;
                if (n4 > branchDepth) {
                    break;
                }
                this.open.remove(next);
                this.closed.add(next);
                for (final Direction direction : Direction.values()) {
                    if (direction != Direction.DOWN && direction != Direction.UP) {
                        final Vec3d v = this.floor(next.getLoc().offset(direction, 1.0));
                        if (isPassable(v, false) && this.visitNode(next, v, 0.0)) {
                            break outer;
                        }
                    }
                }
                final Vec3d v2 = this.floor(next.getLoc().add(0.0, 1.0, 0.0));
                if (isPassable(v2, false) && this.visitNode(next, v2, 0.0)) {
                    break outer;
                }
                final Vec3d v3 = this.floor(next.getLoc().add(0.0, -1.0, 0.0));
                if (isPassable(v3, false) && this.visitNode(next, v3, 0.0)) {
                    break outer;
                }
            }
            ++n2;
        }
        this.closed.sort((Comparator<? super PathNode>)new PathNodeComparator());
        this.path = this.closed.get(0).getPath();
    }
    
    private Vec3d floor(final Vec3d position) {
        return new Vec3d(Math.floor(position.getX()), Math.floor(position.getY()), Math.floor(position.getZ()));
    }
    
    public static boolean isPassable(final Vec3d loc, final boolean checkGround) {
        return isPassableAt((int)loc.getX(), (int)loc.getY(), (int)loc.getZ(), checkGround);
    }
    
    public static boolean isPassableAt(final int x, final int y, final int z, final boolean checkGround) {
        final long n = PathFinder.KEY ^ 0x468567EB6C86L;
        final String m = getGuard();
        final BlockPos blockPos = new BlockPos(x, y, z);
        final String s = m;
        boolean b = isBlocking(blockPos);
        if (s == null) {
            if (b) {
                return false;
            }
            b = isBlocking(blockPos.up());
            if (b) {
                return false;
            }
            b = isBlocking(blockPos.down());
            if (!b && checkGround) {
                return false;
            }
            b = isValidGround(blockPos.down());
            if (!b) {
                return false;
            }
            b = true;
        }
        return b;
    }
    
    private static boolean isBlocking(final BlockPos pos) {
        final long n = PathFinder.KEY ^ 0x5EBA9A7BA217L;
        final String m = getGuard();
        final boolean solid = ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).isSolid();
        if (m == null) {
            if (solid && ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).isFullCube(ClientContext.MINECRAFT_CLIENT.world, pos)) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof SlabBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof StairsBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof CactusBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof ChestBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof EnderChestBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof SkullBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof PaneBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof FenceBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof WallBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof TintedGlassBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof PistonBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof PistonExtensionBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof StainedGlassBlock) {
                return true;
            }
            if (ClientContext.MINECRAFT_CLIENT.world.getBlockState(pos).getBlock() instanceof TrapdoorBlock) {
                return true;
            }
            return false;
        }
        return solid;
    }
    
    private static boolean isValidGround(final BlockPos blockPos) {
        final long n = PathFinder.KEY ^ 0x2496C8ED766AL;
        final String m = getGuard();
        boolean b = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).getBlock() instanceof FenceBlock;
        if (m == null) {
            if (b) {
                return false;
            }
            b = ClientContext.MINECRAFT_CLIENT.world.getBlockState(blockPos).getBlock() instanceof WallBlock;
            if (b) {
                return false;
            }
            b = true;
        }
        return b;
    }
    
    public PathNode findNode(final Vec3d loc) {
        final long n = PathFinder.KEY ^ 0x4D9AF4672EBAL;
        final String m = getGuard();
        final String s = m;
        if (s == null) {
            for (final PathNode z5 : this.closed) {
                if (z5.getLoc().getX() == loc.getX() && z5.getLoc().getY() == loc.getY() && z5.getLoc().getZ() == loc.getZ()) {
                    return z5;
                }
            }
            for (final PathNode z6 : this.open) {
                if (z6.getLoc().getX() == loc.getX() && z6.getLoc().getY() == loc.getY() && z6.getLoc().getZ() == loc.getZ()) {
                    return z6;
                }
            }
            return null;
        }
        final Iterator<PathNode> iterator = this.closed.iterator();
        if (iterator.hasNext()) {
            final PathNode z5 = iterator.next();
            if (z5.getLoc().getX() == loc.getX()) {
                return null;
            }
            final PathNode z7 = iterator.next();
            if (z7.getLoc().getX() == loc.getX()) {
                return z7;
            }
            return null;
        }
        final Iterator<PathNode> iterator2 = this.open.iterator();
        if (iterator2.hasNext()) {
            final PathNode z8 = iterator2.next();
            if (z8.getLoc().getX() == loc.getX()) {
                return z8;
            }
        }
        return null;
    }
    
    public boolean visitNode(final PathNode parent, final Vec3d loc, final double cost) {
        final long n = PathFinder.KEY ^ 0x2C8062E2438EL;
        final PathNode y_r2 = this.findNode(loc);
        final String m = getGuard();
        double n2 = cost;
        final String s = m;
        if (s == null) {
            if (parent != null) {
                n2 += parent.getTotalCost();
            }
            if (y_r2 == null) {
                boolean atTarget = loc.getX() == this.end.getX();
                if (atTarget) {
                    atTarget = loc.getY() == this.end.getY();
                    if (atTarget) {
                        atTarget = loc.getZ() == this.end.getZ();
                    }
                }
                if (!atTarget && !(loc.squaredDistanceTo(this.end) <= 9.5)) {
                    final ArrayList<Vec3d> list = new ArrayList<Vec3d>(parent.getPath());
                    list.add(loc);
                    this.open.add(new PathNode(loc, parent, list, loc.squaredDistanceTo(this.end), cost, n2));
                    return false;
                }
                this.path.clear();
                this.path = parent.getPath();
                this.path.add(loc);
                return true;
            }
            if (y_r2.getCost() > cost) {
                final ArrayList<Vec3d> list2 = new ArrayList<Vec3d>(parent.getPath());
                list2.add(loc);
                y_r2.setLoc(loc);
                y_r2.setParent(parent);
                y_r2.setPath(list2);
                y_r2.setGoalDistanceSq(loc.squaredDistanceTo(this.end));
                y_r2.setCost(cost);
                y_r2.setTotalCost(n2);
            }
            return false;
        }
        return y_r2.getCost() != cost;
    }
    
    public Vec3d getStart() {
        return this.start;
    }
    
    public Vec3d getEnd() {
        return this.end;
    }
    
    public ArrayList<Vec3d> getPath() {
        return this.path;
    }
    
    public ArrayList<PathNode> getClosedNodes() {
        return this.closed;
    }
    
    public ArrayList<PathNode> getOpenNodes() {
        return this.open;
    }
    
    public double getGoalDistanceSq() {
        Objects.requireNonNull(this);
        return 9.5;
    }
    
    public static void setGuard(final String value) {
        PathFinder.guard = value;
    }
    
    public static String getGuard() {
        return PathFinder.guard;
    }
    
    static {
        KEY = KeyedRandom.create(2935054107701506152L, 8682702040783530450L, (Object)MethodHandles.lookup().lookupClass()).next(71981386653375L);
        if (getGuard() != null) {
            setGuard("cIsT");
        }
    }
    
    private static JSONException ignoreException(final JSONException exception) {
        return exception;
    }
}
