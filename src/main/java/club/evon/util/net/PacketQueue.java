// 
// Decompiled by Procyon v0.6.0
// 

package club.evon.util.net;
import org.json.JSONException;
import java.lang.invoke.MethodHandles;
import java.util.Comparator;
import net.minecraft.network.ClientConnection;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.listener.PacketListener;
import club.evon.ClientContext;
import club.evon.data.QueuedPacket;
import club.evon.module.BaseModule;
import club.evon.util.KeyedRandom;




public abstract class PacketQueue<T extends PacketListener>
{
    private final List<PacketBlockerEntry> entries;
    private final List<QueuedPacket> queuedPackets;
    private long currentId;
    public final Object lock;
    private static String stateString;
    private static final long KEY;
    
    public PacketQueue() {
        this.entries = new ArrayList<PacketBlockerEntry>();
        this.queuedPackets = new ArrayList<QueuedPacket>();
        this.lock = new Object();
    }
    
    public PacketBlockerEntry createEntry() {
        return this.createEntry(null, null);
    }
    
    public PacketBlockerEntry createEntry(final PacketTransformer transformer, final PacketValidator packetValidator) {
        return this.createEntry(transformer, packetValidator, false);
    }
    
    public PacketBlockerEntry createEntry(final PacketTransformer transformer, final PacketValidator packetValidator, final boolean priority) {
        final long n = PacketQueue.KEY ^ 0x6385212851C7L;
        final String s = getStateString();
        final PacketBlockerEntry result;
        synchronized (this.lock) {
            result = new PacketBlockerEntry(transformer, packetValidator, priority, this.resolveEntryId());
            this.entries.add(result);
        }
        if (s == null) {
            BaseModule.setStateArray(new BaseModule[4]);
        }
        return result;
    }
    
    private long resolveEntryId() {
        final long n = PacketQueue.KEY ^ 0x6BA1227A82F4L;
        final String s = getStateString();
        long p = this.currentId;
        final Iterator<PacketBlockerEntry> iterator = this.entries.iterator();
        if (s == null) {
            if (iterator.hasNext()) {
                final PacketBlockerEntry zp_c2 = iterator.next();
                zp_c2.isPriority();
                p = zp_c2.getId();
            }
            return p;
        }
        while (iterator.hasNext()) {
            final PacketBlockerEntry zp_c2 = iterator.next();
            if (zp_c2.isPriority()) {
                final long i = zp_c2.getId();
                if (p >= i) {
                    p = zp_c2.getId();
                }
            }
        }
        return p;
    }
    
    public void releaseEntry(final PacketBlockerEntry networkBlock) {
        final long n = PacketQueue.KEY ^ 0x681E55910270L;
        final String s = getStateString();
        synchronized (this.lock) {
            if (s != null && this.entries.contains(networkBlock)) {
                this.entries.remove(networkBlock);
                this.sortQueues();
                final Long value = this.entries.isEmpty() ? null : this.entries.getFirst().getId();
                this.flushQueuedPackets(value, networkBlock.getTransformer());
            }
        }
    }
    
    private void flushQueuedPackets(@Nullable final Long maxId, @Nullable final PacketTransformer transformer) {
        final long n2 = PacketQueue.KEY ^ 0x540DEE53D0E6L;
        final String s = getStateString();
        final ClientPlayNetworkHandler networkHandler = ClientContext.MINECRAFT_CLIENT.getNetworkHandler();
        final ClientConnection connection;
        if (s != null && networkHandler == null) {
            connection = null;
        }
        else {
            connection = networkHandler.getConnection();
        }
        final ArrayList<Packet<?>> list = new ArrayList<Packet<?>>();
        final Iterator<QueuedPacket> iterator = this.queuedPackets.iterator();
        while (iterator.hasNext()) {
            final QueuedPacket g = iterator.next();
            if (s == null) {
                this.dispatchPacket(connection, (Packet<?>)(Object)maxId);
                return;
            }
            if (maxId == null || g.getId() < maxId) {
                if (connection != null) {
                    Packet<?> packet = g.getPacket();
                    if (s == null || transformer != null) {
                        packet = transformer.transform(packet);
                    }
                    if (packet != null) {
                        list.add(packet);
                    }
                }
                iterator.remove();
            }
        }
        final Iterator<Packet<?>> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            this.dispatchPacket(connection, iterator2.next());
            if (s == null) {
                return;
            }
        }
    }
    
    public abstract void dispatchPacket(final ClientConnection p0, final Packet<?> p1);
    
    public boolean tryQueuePacket(final Packet<?> packet) {
        final long n = PacketQueue.KEY ^ 0x33C8706D92CFL;
        final String s = getStateString();
        synchronized (this.lock) {
            final boolean empty = this.entries.isEmpty();
            if (s == null) {
                return empty;
            }
            if (!empty) {
                this.sortQueues();
                final PacketBlockerEntry first = this.entries.getFirst();
                final PacketValidator validator = first.getValidator();
                boolean b3 = false;
                if (validator == null) {
                    b3 = true;
                }
                else if (validator.shouldBlock(packet)) {
                    b3 = true;
                }
                else {
                    for (final PacketBlockerEntry entry : this.entries) {
                        if (entry.equals(first)) {
                            continue;
                        }
                        final PacketValidator v2 = entry.getValidator();
                        if (v2 == null || v2.shouldBlock(packet)) {
                            b3 = true;
                            break;
                        }
                    }
                }
                if (b3) {
                    this.queuedPackets.add(new QueuedPacket(packet, this.currentId));
                    ++this.currentId;
                    return true;
                }
            }
            return false;
        }
    }
    
    private void sortQueues() {
        synchronized (this.lock) {
            this.entries.sort(Comparator.comparingLong(PacketBlockerEntry::getId));
            this.queuedPackets.sort(Comparator.comparingLong(QueuedPacket::getId));
        }
    }
    
    public void clear() {
        synchronized (this.lock) {
            this.entries.clear();
            this.queuedPackets.clear();
            this.currentId = 0L;
        }
    }
    
    public boolean hasEntries() {
        final long n = PacketQueue.KEY ^ 0x2D67101728BEL;
        final String s = getStateString();
        synchronized (this.lock) {
            final boolean empty = this.entries.isEmpty();
            if (s == null) {
                return empty;
            }
            return !empty;
        }
    }
    
    public List<PacketBlockerEntry> getEntries() {
        return this.entries;
    }
    
    public List<QueuedPacket> getQueuedPackets() {
        return this.queuedPackets;
    }
    
    public long getCurrentId() {
        return this.currentId;
    }
    
    public Object getLock() {
        return this.lock;
    }
    
    public static void setStateString(final String d) {
        PacketQueue.stateString = d;
    }
    
    public static String getStateString() {
        return PacketQueue.stateString;
    }
    
    static {
        KEY = KeyedRandom.create(-710086680365985718L, 6257072623610823175L, (Object)MethodHandles.lookup().lookupClass()).next(84923891464150L);
        if (getStateString() == null) {
            setStateString("PGdYO");
        }
    }
    
    private static JSONException identity(final JSONException exception) {
        return exception;
    }
}
