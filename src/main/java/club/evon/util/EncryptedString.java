package club.evon.util;


public interface EncryptedString {
   public void setDelegate(EncryptedString delegate);

   public int[] getShifts();

   public long next(long key);

   public void setKey(long key);

   public boolean isLessOrEqual(EncryptedString other);
}
