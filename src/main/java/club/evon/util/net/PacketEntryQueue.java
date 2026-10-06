/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  org.json.JSONException
 */
package club.evon.util.net;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public final class PacketEntryQueue {
    private final PacketQueue<?> packetQueue;
    @Nullable
    private final PacketValidator validator;
    private final List<PacketBlockerEntry> entries;
    private static final long KEY = KeyedRandom.create(1921436192998728942L, -271328302396168585L, MethodHandles.lookup().lookupClass()).next(255454477389516L);

    public PacketEntryQueue(PacketQueue<?> networkBlockage, @Nullable PacketValidator packetValidator) {
        block0: {
            long l = KEY ^ 0x40822CE00450L;
            BaseModule[] gQArray = PacketBlocker.getFlowGuard();
            BaseModule[] gQArray2 = gQArray;
            this.entries = new ArrayList<PacketBlockerEntry>();
            this.packetQueue = networkBlockage;
            this.validator = packetValidator;
            if (BaseModule.getStateArray() != null) break block0;
            PacketBlocker.setFlowGuard(new BaseModule[5]);
        }
    }

    public PacketEntryQueue(PacketQueue<?> networkBlockage) {
        this(networkBlockage, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addEntry() {
        block3: {
            BaseModule[] gQArray;
            List<PacketBlockerEntry> list = this.entries;
            synchronized (list) {
                gQArray = PacketBlocker.getFlowGuard();
                this.entries.add(this.packetQueue.createEntry(null, this.validator));
            }
            if (gQArray == null) break block3;
            BaseModule.setStateArray(new BaseModule[1]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseEntries(int count) {
        long l = KEY ^ 0x1EA5A90016CFL;
        List<PacketBlockerEntry> list = this.entries;
        synchronized (list) {
            BaseModule[] gQArray = PacketBlocker.getFlowGuard();
            while (!this.entries.isEmpty() && count > 0) {
                PacketBlockerEntry zp_c22 = this.entries.removeFirst();
                this.packetQueue.releaseEntry(zp_c22);
                --count;
                if (gQArray == null) continue;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void releaseAll() {
        long l = KEY ^ 0x814773D7BF4L;
        List<PacketBlockerEntry> list = this.entries;
        synchronized (list) {
            BaseModule[] gQArray = PacketBlocker.getFlowGuard();
            while (!this.entries.isEmpty()) {
                PacketBlockerEntry zp_c22 = this.entries.removeFirst();
                this.packetQueue.releaseEntry(zp_c22);
                if (gQArray == null) {
                    if (gQArray == null) continue;
                }
                break;
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean hasEntries() {
        long l = KEY ^ 0x63023A5637ADL;
        List<PacketBlockerEntry> list = this.entries;
        synchronized (list) {
            BaseModule[] gQArray = PacketBlocker.getFlowGuard();
            boolean bl = this.entries.isEmpty();
            if (gQArray == null) {
                bl = !bl;
            }
            return bl;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int size() {
        List<PacketBlockerEntry> list = this.entries;
        synchronized (list) {
            return this.entries.size();
        }
    }

    public List<PacketBlockerEntry> getEntries() {
        return this.entries;
    }

    private static JSONException identity(JSONException exception) {
        return exception;
    }
}
