package club.evon.runtime;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;




public final class IndyRuntime {
    private IndyRuntime() {
    }

    private static final ConcurrentHashMap<String, Method> CACHE = new ConcurrentHashMap<>();

    public static Object invoke(String owner, String bsmName, String bsmDesc, String name, String desc, Object[] args) throws Throwable {
        Class<?> cls = Class.forName(owner.replace('/', '.'), true, IndyRuntime.class.getClassLoader());
        String key = owner + "#" + bsmName + bsmDesc;
        Method bsm = CACHE.get(key);
        if (bsm == null) {
            MethodType bsmType = MethodType.fromMethodDescriptorString(bsmDesc, cls.getClassLoader());
            bsm = cls.getDeclaredMethod(bsmName, bsmType.parameterArray());
            bsm.setAccessible(true);
            CACHE.put(key, bsm);
        }
        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(cls, MethodHandles.lookup());
        MethodType mt = MethodType.fromMethodDescriptorString(desc, cls.getClassLoader());
        CallSite cs = (CallSite) bsm.invoke(null, lookup, name, mt);
        return cs.getTarget().invokeWithArguments(args);
    }
}
