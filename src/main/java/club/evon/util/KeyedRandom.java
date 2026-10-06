package club.evon.util;
import java.util.ArrayList;
import java.util.Vector;




public class KeyedRandom implements EncryptedString {
   private long state;
   private int[] shifts;
   private EncryptedString delegate;
   private long key;
   private long[] masks;
   private static long[] bitMasks = new long[64];
   public static int[] shiftTable;
   private static ArrayList instances;
   private static Vector references;
   private static int poolSize;
   private static Object lock;
   private static int startBit = 52;
   private static int cloneInterval = 128;
   private static int maxDepth = 17;

   public static Object getLock() {
      return lock;
   }

   public static EncryptedString create(long seed, long salt, Object referent) {
      GeneratorPool.setEnabled(seed > 0L);
      EncryptedString newInstance = newInstance(seed);
      EncryptedString newInstance2 = newInstance(salt);
      EncryptedString generator = GeneratorPool.getGenerator(newInstance, newInstance2);
      if (referent != null) {
         references.add(referent);
      }

      return generator;
   }

   public static EncryptedString getInstance(long seed) {
      int value = (int)permuteBits(seed, startBit, 63, shiftTable, bitMasks);
      if (value < poolSize) {
         return (EncryptedString)instances.get(value);
      }

      if (instances.size() % cloneInterval == 0) {
         shiftTable = (int[])shiftTable.clone();
      }

      KeyedRandom keyedRandom = new KeyedRandom(seed);
      instances.add(keyedRandom);
      return keyedRandom;
   }

   private static EncryptedString newInstance(long seed) {
      return new KeyedRandom(seed);
   }

   public static void initialize(GeneratorPool pool) {
      poolSize = instances.size();
      sortPool();
      pool.initialize();
   }

   public static void reset(GeneratorPool pool) {
      sortPool();
      int[] values = shiftTable = new int[64];
      values[0] = -61;
      values[1] = -30;
      values[2] = -57;
      values[3] = -21;
      values[4] = -59;
      values[5] = -27;
      values[6] = -13;
      values[7] = -7;
      values[8] = -19;
      values[9] = -9;
      values[10] = -27;
      values[11] = -33;
      values[12] = -31;
      values[13] = -39;
      values[14] = 7;
      values[15] = -34;
      values[16] = -32;
      values[17] = -9;
      values[18] = 9;
      values[19] = 13;
      values[20] = -1;
      values[21] = 1;
      values[22] = -8;
      values[23] = -28;
      values[24] = 21;
      values[25] = -35;
      values[26] = 9;
      values[27] = 19;
      values[28] = -14;
      values[29] = -5;
      values[30] = 8;
      values[31] = 30;
      values[32] = 27;
      values[33] = -6;
      values[34] = 5;
      values[35] = -6;
      values[36] = -22;
      values[37] = 27;
      values[38] = -16;
      values[39] = 6;
      values[40] = -6;
      values[41] = 6;
      values[42] = 14;
      values[43] = 31;
      values[44] = 33;
      values[45] = -5;
      values[46] = 6;
      values[47] = -15;
      values[48] = 32;
      values[49] = 34;
      values[50] = 5;
      values[51] = 28;
      values[52] = 39;
      values[53] = -4;
      values[54] = 16;
      values[55] = -1;
      values[56] = 1;
      values[57] = 4;
      values[58] = 22;
      values[59] = 57;
      values[60] = 35;
      values[61] = 61;
      values[62] = 15;
      values[63] = 59;
      pool.restoreState();
   }

   private KeyedRandom(long seed) {
      this.state = seed;
      this.shifts = shiftTable;
      this.masks = bitMasks;
   }

   public long next(long longValue) {
      long longValue2 = this.extractBits(8, 55);
      long longValue3 = this.state ^ longValue ^ this.key;
      this.state = longValue3;
      if (this.delegate != null) {
         this.delegate.next(longValue);
      }

      return longValue2;
   }

   @Override
   public void setKey(long longValue) {
      this.key = longValue;
   }

   public void setDelegate(EncryptedString encryptedString) {
      if (this != encryptedString) {
         if (this.delegate == null) {
            this.delegate = encryptedString;
         } else {
            this.delegate.setDelegate(encryptedString);
         }
      }
   }

   @Override
   public int hashCode() {
      return (int)this.nextBits(8);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return object instanceof KeyedRandom ? this.nextBits(56) == ((KeyedRandom)object).nextBits(56) : false;
      }
   }

   public boolean isLessOrEqual(EncryptedString other) {
      if (this == other) {
         return true;
      } else {
         return other instanceof KeyedRandom ? this.extractBits(56, 63) - ((KeyedRandom)other).extractBits(56, 63) <= 0L : true;
      }
   }

   @Override
   public int[] getShifts() {
      return this.shifts;
   }

   private long nextBits(int bits) {
      return this.extractBits(0, bits - 1);
   }

   private long extractBits(int fromBit, int toBit) {
      return permuteBits(this.state, fromBit, toBit, this.shifts, this.masks);
   }

   private static long permuteBits(long longValue, int fromBit, int toBit, int[] values, long[] values2) {
      long longValue2 = 0L;
      int length = values.length;

      for (int i = 0; i < length; i++) {
         long longValue3 = longValue & bitMasks[i];
         long longValue4 = (long)values[i];
         if (longValue3 != 0L) {
            if (longValue4 > 0) {
               longValue3 >>>= longValue4;
            } else if (longValue4 < 0) {
               longValue3 <<= ~longValue4 + 1;
            }

            longValue2 |= longValue3;
         }
      }

      byte byteValue = 64;
      long longValue5 = longValue2;
      long longValue6 = (long)(byteValue - 1 - toBit);
      if (longValue6 > 0) {
         longValue5 <<= longValue6;
      }

      long longValue7 = (long)(fromBit + byteValue - 1 - toBit);
      if (longValue7 > 0) {
         longValue5 >>>= longValue7;
      }

      return longValue5;
   }

   private static void sortPool() {
      byte byteValue = 0;
      sortRecursive(0, instances.size() - 1, instances, new ArrayList(instances), byteValue);
   }

   private static void sortRecursive(int low, int high, ArrayList list, ArrayList buffer, int depth) {
      if (low < high) {
         int value = low + (high - low) / 2;
         if (++depth < maxDepth) {
            sortRecursive(low, value, list, buffer, depth);
            sortRecursive(value + 1, high, list, buffer, depth);
         }

         merge(low, value, high, list, buffer);
      }
   }

   private static void merge(int low, int mid, int high, ArrayList list, ArrayList buffer) {
      int value = low;
      int value2 = mid + 1;

      for (int i = low; i <= high; i++) {
         buffer.set(i, list.get(i));
      }

      while (value <= mid && value2 <= high) {
         EncryptedString encryptedString;
         if (((EncryptedString)buffer.get(value)).isLessOrEqual((EncryptedString)buffer.get(value2))) {
            encryptedString = (EncryptedString)buffer.get(value++);
         } else {
            encryptedString = (EncryptedString)buffer.get(value2++);
         }

         list.set(low, encryptedString);
         low++;
      }

      while (value <= mid) {
         list.set(low, buffer.get(value));
         low++;
         value++;
      }
   }

   static {
      long var0 = 1L;

      for (int var2 = 0; var2 < 64; var2++) {
         bitMasks[var2] = var0;
         var0 <<= 1;
      }

      lock = new Object();
      references = new Vector();
      instances = new ArrayList();
      bootstrap();
      poolSize = instances.size();
      sortPool();
   }

   private static void bootstrap() {
      shiftTable = new int[]{
         -4,
         -33,
         -38,
         -8,
         4,
         -52,
         -13,
         -10,
         -38,
         -16,
         -19,
         8,
         -6,
         -35,
         -16,
         -36,
         -46,
         10,
         6,
         13,
         -35,
         -39,
         -34,
         -24,
         -35,
         16,
         -37,
         -1,
         1,
         19,
         16,
         -6,
         -13,
         -2,
         33,
         2,
         -5,
         6,
         -11,
         -19,
         38,
         5,
         -2,
         -18,
         2,
         13,
         38,
         24,
         35,
         11,
         -3,
         36,
         -2,
         3,
         2,
         35,
         34,
         52,
         19,
         35,
         39,
         18,
         46,
         37
      };
      instances.add(new KeyedRandom(15971454553001582L));
      instances.add(new KeyedRandom(-6936854644598942967L));
      instances.add(new KeyedRandom(-2113644141552534793L));
      instances.add(new KeyedRandom(2286848226923574050L));
      instances.add(new KeyedRandom(-5590609697831816098L));
      instances.add(new KeyedRandom(3421030266876047859L));
      instances.add(new KeyedRandom(-1663293710264281446L));
      instances.add(new KeyedRandom(6385408120887316619L));
      instances.add(new KeyedRandom(-90176687808269768L));
      instances.add(new KeyedRandom(-982350453840728772L));
      instances.add(new KeyedRandom(-3880713390233698749L));
      instances.add(new KeyedRandom(2349083498025345276L));
      instances.add(new KeyedRandom(4675186110482506555L));
      instances.add(new KeyedRandom(-4764258815270514179L));
      instances.add(new KeyedRandom(-2953557883521081346L));
      instances.add(new KeyedRandom(-6555511044564862086L));
      instances.add(new KeyedRandom(1383352305335719505L));
      instances.add(new KeyedRandom(-5489697289460202975L));
      instances.add(new KeyedRandom(6738412875272394259L));
      instances.add(new KeyedRandom(-705167254030185057L));
      instances.add(new KeyedRandom(-7050767887643139250L));
      instances.add(new KeyedRandom(-3822885761776442786L));
      instances.add(new KeyedRandom(5388733686225654305L));
      instances.add(new KeyedRandom(7609212049883200603L));
      instances.add(new KeyedRandom(3074345601098476928L));
      instances.add(new KeyedRandom(2676991149048966275L));
      instances.add(new KeyedRandom(2729470433782787675L));
      instances.add(new KeyedRandom(605951082986659262L));
      instances.add(new KeyedRandom(8141406625398333801L));
      instances.add(new KeyedRandom(2028818466823382883L));
      instances.add(new KeyedRandom(-986223902178351202L));
      instances.add(new KeyedRandom(-7229389417022230255L));
      instances.add(new KeyedRandom(7587184871354624866L));
      instances.add(new KeyedRandom(6849603366097399091L));
      instances.add(new KeyedRandom(-7185946888815889281L));
      instances.add(new KeyedRandom(-7127059443066637348L));
      instances.add(new KeyedRandom(5111605371921943606L));
      instances.add(new KeyedRandom(-864943849969430290L));
      instances.add(new KeyedRandom(-2796777612334462100L));
      instances.add(new KeyedRandom(-4854587125467723758L));
      instances.add(new KeyedRandom(-7965586886027860642L));
      instances.add(new KeyedRandom(-1301799343904209713L));
      instances.add(new KeyedRandom(-1328763044628173798L));
      instances.add(new KeyedRandom(-649802248904838180L));
      instances.add(new KeyedRandom(-30265073156866111L));
      instances.add(new KeyedRandom(6942630444272469223L));
      instances.add(new KeyedRandom(8111997714391074312L));
      instances.add(new KeyedRandom(1203389460456045308L));
      instances.add(new KeyedRandom(2729478680075955803L));
      instances.add(new KeyedRandom(-1739444378888472265L));
      instances.add(new KeyedRandom(7192742667943211988L));
      instances.add(new KeyedRandom(6232226317931622149L));
      instances.add(new KeyedRandom(6087468474220412354L));
      instances.add(new KeyedRandom(-6615775578139261834L));
      instances.add(new KeyedRandom(3938143800664771581L));
      instances.add(new KeyedRandom(-3841355709312191726L));
      instances.add(new KeyedRandom(3836760565314554589L));
      instances.add(new KeyedRandom(-2543858393168967583L));
      instances.add(new KeyedRandom(8372111998205037468L));
      instances.add(new KeyedRandom(-2580367451144638683L));
      instances.add(new KeyedRandom(-70816450968696721L));
      instances.add(new KeyedRandom(4810436447329842037L));
      instances.add(new KeyedRandom(1552860508491840187L));
      instances.add(new KeyedRandom(-1589215965665188740L));
      instances.add(new KeyedRandom(3961494248555150188L));
      instances.add(new KeyedRandom(8787046110889503366L));
      instances.add(new KeyedRandom(-2277699856568731628L));
      instances.add(new KeyedRandom(4603342453619018688L));
      instances.add(new KeyedRandom(4672598980877175332L));
      instances.add(new KeyedRandom(1260662336063395981L));
      instances.add(new KeyedRandom(-7076219980938437027L));
      instances.add(new KeyedRandom(-6539434036555665049L));
      instances.add(new KeyedRandom(6784144858128173004L));
      instances.add(new KeyedRandom(-4911625265982481109L));
      instances.add(new KeyedRandom(-5318712850187705152L));
      instances.add(new KeyedRandom(-3882483726245338714L));
      instances.add(new KeyedRandom(7190304492638973487L));
      instances.add(new KeyedRandom(3029169168617727460L));
      instances.add(new KeyedRandom(463224999267571377L));
      instances.add(new KeyedRandom(6197973643812421766L));
      instances.add(new KeyedRandom(2953743972033492179L));
      instances.add(new KeyedRandom(7318547047792504382L));
      instances.add(new KeyedRandom(-5837719481586037799L));
      instances.add(new KeyedRandom(-2159902606237832759L));
      instances.add(new KeyedRandom(6061260926844523824L));
      instances.add(new KeyedRandom(2694765774830603282L));
      instances.add(new KeyedRandom(4179623507573264906L));
      instances.add(new KeyedRandom(3188456761622190663L));
      instances.add(new KeyedRandom(-7677642737676530374L));
      instances.add(new KeyedRandom(-6750176374387290672L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(-824905020774639535L));
      instances.add(new KeyedRandom(-399457613214036698L));
      instances.add(new KeyedRandom(-7731722376393086633L));
      instances.add(new KeyedRandom(-8619645173044787950L));
      instances.add(new KeyedRandom(5953674220643893253L));
      instances.add(new KeyedRandom(-2290977286490154155L));
      instances.add(new KeyedRandom(124156767791191598L));
      instances.add(new KeyedRandom(-178233523714892032L));
      instances.add(new KeyedRandom(2808579426221649322L));
      instances.add(new KeyedRandom(4046965737980750140L));
      instances.add(new KeyedRandom(7919077752721789008L));
      instances.add(new KeyedRandom(8035493824309323487L));
      instances.add(new KeyedRandom(3054442503949422815L));
      instances.add(new KeyedRandom(-4753043344992787363L));
      instances.add(new KeyedRandom(6373641328334696340L));
      instances.add(new KeyedRandom(7194100459406433538L));
      instances.add(new KeyedRandom(-8052860157421206774L));
      instances.add(new KeyedRandom(4332029142861491131L));
      instances.add(new KeyedRandom(4692180043276020709L));
      instances.add(new KeyedRandom(7270756738738263011L));
      instances.add(new KeyedRandom(577028289290966938L));
      instances.add(new KeyedRandom(-2402532067095331076L));
      instances.add(new KeyedRandom(-3075616494363563121L));
      instances.add(new KeyedRandom(-2201374183455682749L));
      instances.add(new KeyedRandom(-9032626845521403829L));
      instances.add(new KeyedRandom(5761283801335318017L));
      instances.add(new KeyedRandom(-1952884122168771088L));
      instances.add(new KeyedRandom(1518118143716472372L));
      instances.add(new KeyedRandom(-6392929285792269662L));
      instances.add(new KeyedRandom(3108958254746032853L));
      instances.add(new KeyedRandom(-2812866648953858800L));
      instances.add(new KeyedRandom(-2575756267844238265L));
      instances.add(new KeyedRandom(5746663442650683185L));
      instances.add(new KeyedRandom(2507310963761244763L));
      instances.add(new KeyedRandom(6935560111533274716L));
      instances.add(new KeyedRandom(3791497350898955338L));
      instances.add(new KeyedRandom(5879339894284833955L));
      instances.add(new KeyedRandom(-1835408325303056491L));
      instances.add(new KeyedRandom(-4797528106763560335L));
      instances.add(new KeyedRandom(5951634609103194950L));
      instances.add(new KeyedRandom(1807003409500162837L));
      instances.add(new KeyedRandom(-788890824277387932L));
      instances.add(new KeyedRandom(-2625735869136121436L));
      instances.add(new KeyedRandom(-4136115910307462500L));
      instances.add(new KeyedRandom(8455847352897436045L));
      instances.add(new KeyedRandom(2678287732500978270L));
      instances.add(new KeyedRandom(-7882895269985352073L));
      instances.add(new KeyedRandom(-1906489184952056493L));
      instances.add(new KeyedRandom(8372111448461806492L));
      instances.add(new KeyedRandom(6666647217329500779L));
      instances.add(new KeyedRandom(1336961559641967106L));
      instances.add(new KeyedRandom(-4026751587378934524L));
      instances.add(new KeyedRandom(2181398566411568434L));
      instances.add(new KeyedRandom(836939635044866820L));
      instances.add(new KeyedRandom(-5700809652860570198L));
      instances.add(new KeyedRandom(-6128155999296511165L));
      instances.add(new KeyedRandom(1830025340568614832L));
      instances.add(new KeyedRandom(-8584735816775406544L));
      instances.add(new KeyedRandom(-8581476069280613735L));
      instances.add(new KeyedRandom(-6009840096204895763L));
      instances.add(new KeyedRandom(1576607663194996086L));
      instances.add(new KeyedRandom(-1181256851738103255L));
      instances.add(new KeyedRandom(-8075999861130961352L));
      instances.add(new KeyedRandom(-150198827114507459L));
      instances.add(new KeyedRandom(2978480687330010923L));
      instances.add(new KeyedRandom(2662689236767447181L));
      instances.add(new KeyedRandom(-6461296720372525021L));
      instances.add(new KeyedRandom(-3354189774955628520L));
      instances.add(new KeyedRandom(1435835066112473819L));
      instances.add(new KeyedRandom(-5828921105303081981L));
      instances.add(new KeyedRandom(4432530053634549206L));
      instances.add(new KeyedRandom(5606640379435967817L));
      instances.add(new KeyedRandom(1310831753445511721L));
      instances.add(new KeyedRandom(-5343559265048548236L));
      instances.add(new KeyedRandom(7906264324115794516L));
      instances.add(new KeyedRandom(-17588445079117479L));
      instances.add(new KeyedRandom(-8514380216424296575L));
      instances.add(new KeyedRandom(-3715557976846995161L));
      instances.add(new KeyedRandom(-506036674948048915L));
      instances.add(new KeyedRandom(1889371926835547871L));
      instances.add(new KeyedRandom(-7857577093581813728L));
      instances.add(new KeyedRandom(4283899619658252845L));
      instances.add(new KeyedRandom(5589883298022668055L));
      instances.add(new KeyedRandom(-5598899639432734560L));
      instances.add(new KeyedRandom(-8659890505808041753L));
      instances.add(new KeyedRandom(5163527792776485138L));
      instances.add(new KeyedRandom(-4635983478657878517L));
      instances.add(new KeyedRandom(5530380282139853042L));
      instances.add(new KeyedRandom(-4026760933240353532L));
      instances.add(new KeyedRandom(7320404719430581869L));
      instances.add(new KeyedRandom(2017371593147359461L));
      instances.add(new KeyedRandom(-4996208767730071613L));
      instances.add(new KeyedRandom(-2288995389717149000L));
      instances.add(new KeyedRandom(-7697345936442401675L));
      instances.add(new KeyedRandom(-3344852412498938098L));
      instances.add(new KeyedRandom(656704046943840165L));
      instances.add(new KeyedRandom(-589872735370406573L));
      instances.add(new KeyedRandom(-4935175182304850931L));
      instances.add(new KeyedRandom(2652384316927754272L));
      instances.add(new KeyedRandom(-4141077140003084960L));
      instances.add(new KeyedRandom(1190108260993594851L));
      instances.add(new KeyedRandom(-5434611702728937551L));
      instances.add(new KeyedRandom(-2774236807430274659L));
      instances.add(new KeyedRandom(-7054493583760433378L));
      instances.add(new KeyedRandom(295842746128897281L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(-6360969916192451707L));
      instances.add(new KeyedRandom(7239093254542306530L));
      instances.add(new KeyedRandom(6009860563665148517L));
      instances.add(new KeyedRandom(-2129292280125265660L));
      instances.add(new KeyedRandom(3695426481291009582L));
      instances.add(new KeyedRandom(-7773481372403015396L));
      instances.add(new KeyedRandom(-7025593020839004748L));
      instances.add(new KeyedRandom(1951301729436321150L));
      instances.add(new KeyedRandom(6223314901940147957L));
      instances.add(new KeyedRandom(640947731497499292L));
      instances.add(new KeyedRandom(-3916793204830286613L));
      instances.add(new KeyedRandom(-8796233036184427597L));
      instances.add(new KeyedRandom(-480086685589921490L));
      instances.add(new KeyedRandom(-1533484391107517241L));
      instances.add(new KeyedRandom(8586928970741219283L));
      instances.add(new KeyedRandom(461318792195330164L));
      instances.add(new KeyedRandom(7218225405172881256L));
      instances.add(new KeyedRandom(-2777952088896050510L));
      instances.add(new KeyedRandom(-1191592959380549300L));
      instances.add(new KeyedRandom(-1636915012309129330L));
      instances.add(new KeyedRandom(7269347343319941408L));
      instances.add(new KeyedRandom(-5472748327287632924L));
      instances.add(new KeyedRandom(-4506146647404250453L));
      instances.add(new KeyedRandom(-3791149901897580048L));
      instances.add(new KeyedRandom(-6486567398724542290L));
      instances.add(new KeyedRandom(3724286420417562340L));
      instances.add(new KeyedRandom(4494606996670367317L));
      instances.add(new KeyedRandom(7493524265040419628L));
      instances.add(new KeyedRandom(7260012675119154116L));
      instances.add(new KeyedRandom(5191310815228002212L));
      instances.add(new KeyedRandom(-9022190630018388775L));
      instances.add(new KeyedRandom(8202825200273061803L));
      instances.add(new KeyedRandom(6100039952920377576L));
      instances.add(new KeyedRandom(-8076831443599962031L));
      instances.add(new KeyedRandom(798510409019144730L));
      instances.add(new KeyedRandom(6568109611227588757L));
      instances.add(new KeyedRandom(5360644703963120051L));
      instances.add(new KeyedRandom(2240537012274979363L));
      instances.add(new KeyedRandom(-387626317913848269L));
      instances.add(new KeyedRandom(-6801885514471361209L));
      instances.add(new KeyedRandom(-3916670744576052109L));
      instances.add(new KeyedRandom(-387635663758555597L));
      instances.add(new KeyedRandom(-2785515928439883309L));
      instances.add(new KeyedRandom(7693091855053073264L));
      instances.add(new KeyedRandom(-3441729947055594950L));
      instances.add(new KeyedRandom(-73057304210357048L));
      instances.add(new KeyedRandom(3320191948524783266L));
      instances.add(new KeyedRandom(-6529776987259084952L));
      instances.add(new KeyedRandom(6102572649565957073L));
      instances.add(new KeyedRandom(-8033515749034212798L));
      instances.add(new KeyedRandom(-8905538838158256161L));
      instances.add(new KeyedRandom(5673784335972232915L));
      instances.add(new KeyedRandom(2769406310297822401L));
      instances.add(new KeyedRandom(9118803804387621656L));
      instances.add(new KeyedRandom(-8986359745912271360L));
      instances.add(new KeyedRandom(3313497280869146554L));
      instances.add(new KeyedRandom(3378539914368412860L));
      instances.add(new KeyedRandom(4782376290795941150L));
      instances.add(new KeyedRandom(1867689056803229804L));
      instances.add(new KeyedRandom(-5650774388027379404L));
      instances.add(new KeyedRandom(8058736260128685059L));
      instances.add(new KeyedRandom(-775025227887777213L));
      instances.add(new KeyedRandom(1262046536421686579L));
      instances.add(new KeyedRandom(-2520419574092623212L));
      instances.add(new KeyedRandom(1970313949027291747L));
      instances.add(new KeyedRandom(-8749947084175036025L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(8510270464290226215L));
      instances.add(new KeyedRandom(-7473140165881719449L));
      instances.add(new KeyedRandom(-3910695632963472581L));
      instances.add(new KeyedRandom(-8505044097260058565L));
      instances.add(new KeyedRandom(-5155321927584661543L));
      instances.add(new KeyedRandom(-5623843696569228438L));
      instances.add(new KeyedRandom(-2043555116116870773L));
      instances.add(new KeyedRandom(-550866702115804731L));
      instances.add(new KeyedRandom(-5706514503258175323L));
      instances.add(new KeyedRandom(-1636905666481330290L));
      instances.add(new KeyedRandom(-363914480712360490L));
      instances.add(new KeyedRandom(6838769475471848791L));
      instances.add(new KeyedRandom(-1055432166805094083L));
      instances.add(new KeyedRandom(3864982288551130845L));
      instances.add(new KeyedRandom(4686147664996968125L));
      instances.add(new KeyedRandom(4929087174604948716L));
      instances.add(new KeyedRandom(-8940471540940192884L));
      instances.add(new KeyedRandom(3193695891163192584L));
      instances.add(new KeyedRandom(-6236771816545577062L));
      instances.add(new KeyedRandom(6125600600752987867L));
      instances.add(new KeyedRandom(7555307795447493231L));
      instances.add(new KeyedRandom(-6430478341934457354L));
      instances.add(new KeyedRandom(2181287823304088379L));
      instances.add(new KeyedRandom(4072657446906006833L));
      instances.add(new KeyedRandom(3099349538755795873L));
      instances.add(new KeyedRandom(-8230288782998042819L));
      instances.add(new KeyedRandom(-2626426643687341931L));
      instances.add(new KeyedRandom(-381458062087729518L));
      instances.add(new KeyedRandom(-984787329545031029L));
      instances.add(new KeyedRandom(9097450093313326895L));
      instances.add(new KeyedRandom(2013100971376678406L));
      instances.add(new KeyedRandom(-7486138135649051221L));
      instances.add(new KeyedRandom(1759001677421516079L));
      instances.add(new KeyedRandom(5064141409251490045L));
      instances.add(new KeyedRandom(-8885943510171398253L));
      instances.add(new KeyedRandom(-9065113629584716352L));
      instances.add(new KeyedRandom(-1719039036397121633L));
      instances.add(new KeyedRandom(-8379635858467847893L));
      instances.add(new KeyedRandom(3919443871817426242L));
      instances.add(new KeyedRandom(-6864049548058424544L));
      instances.add(new KeyedRandom(4346943194062135779L));
      instances.add(new KeyedRandom(-4413160978824471177L));
      instances.add(new KeyedRandom(5046194389595463706L));
      instances.add(new KeyedRandom(-5266368950621738916L));
      instances.add(new KeyedRandom(2886639168159537910L));
      instances.add(new KeyedRandom(6612125521409841792L));
      instances.add(new KeyedRandom(3440341773097373597L));
      instances.add(new KeyedRandom(-3073174773393613989L));
      instances.add(new KeyedRandom(6088117176370772900L));
      instances.add(new KeyedRandom(-7551467190600044773L));
      instances.add(new KeyedRandom(7557468080915029265L));
      instances.add(new KeyedRandom(5588890326965233158L));
      instances.add(new KeyedRandom(1144773888767907093L));
      instances.add(new KeyedRandom(8954235667945520434L));
      instances.add(new KeyedRandom(4723929447819814535L));
      instances.add(new KeyedRandom(254075114449011964L));
      instances.add(new KeyedRandom(9178421298831988065L));
      instances.add(new KeyedRandom(-7767684391208714376L));
      instances.add(new KeyedRandom(-1298705234653123274L));
      instances.add(new KeyedRandom(7909495426943266420L));
      instances.add(new KeyedRandom(-4854595921632049102L));
      instances.add(new KeyedRandom(-8713035211257808105L));
      instances.add(new KeyedRandom(3661507491347237013L));
      instances.add(new KeyedRandom(-1249461323382759802L));
      instances.add(new KeyedRandom(3805898193477248068L));
      instances.add(new KeyedRandom(4931650601929943275L));
      instances.add(new KeyedRandom(-261876140438023177L));
      instances.add(new KeyedRandom(-4024239049036581574L));
      instances.add(new KeyedRandom(-1040155176774917256L));
      instances.add(new KeyedRandom(-144510000456691740L));
      instances.add(new KeyedRandom(8685962807551781408L));
      instances.add(new KeyedRandom(-6826421831945367020L));
      instances.add(new KeyedRandom(3360050854679437766L));
      instances.add(new KeyedRandom(-3695371003136133353L));
      instances.add(new KeyedRandom(3234410686191534739L));
      instances.add(new KeyedRandom(5153533842484689329L));
      instances.add(new KeyedRandom(370612400952843900L));
      instances.add(new KeyedRandom(-8151111786611257461L));
      instances.add(new KeyedRandom(-8598359637583453843L));
      instances.add(new KeyedRandom(-578126545480355426L));
      instances.add(new KeyedRandom(5014046220868988897L));
      instances.add(new KeyedRandom(6190461587684619631L));
      instances.add(new KeyedRandom(4433607331112272343L));
      instances.add(new KeyedRandom(50236457109736426L));
      instances.add(new KeyedRandom(-2375404214910601027L));
      instances.add(new KeyedRandom(-3047539509344381442L));
      instances.add(new KeyedRandom(-5400134665160919416L));
      instances.add(new KeyedRandom(-780150647199440810L));
      instances.add(new KeyedRandom(6128281574741334500L));
      instances.add(new KeyedRandom(-1019934191782918528L));
      instances.add(new KeyedRandom(-757491654611642150L));
      instances.add(new KeyedRandom(9221210210530937102L));
      instances.add(new KeyedRandom(1590689828501926212L));
      instances.add(new KeyedRandom(-6027536454022009127L));
      instances.add(new KeyedRandom(-808849259778623699L));
      instances.add(new KeyedRandom(8066338401615439948L));
      instances.add(new KeyedRandom(-6082131580178892002L));
      instances.add(new KeyedRandom(-4591564408644192418L));
      instances.add(new KeyedRandom(-4195931281801605580L));
      instances.add(new KeyedRandom(-1675734743986346183L));
      instances.add(new KeyedRandom(-6166183124266950385L));
      instances.add(new KeyedRandom(957342004522843800L));
      instances.add(new KeyedRandom(-1684236868800129006L));
      instances.add(new KeyedRandom(1303243530248505337L));
      instances.add(new KeyedRandom(162428861639665444L));
      instances.add(new KeyedRandom(-3984923917891253725L));
      instances.add(new KeyedRandom(435697442353970308L));
      instances.add(new KeyedRandom(4964733575481752500L));
      instances.add(new KeyedRandom(6146376271013259733L));
      instances.add(new KeyedRandom(191846797825073547L));
      instances.add(new KeyedRandom(4268884176474034241L));
      instances.add(new KeyedRandom(3013315556599300404L));
      instances.add(new KeyedRandom(2873798716493301124L));
      instances.add(new KeyedRandom(-2092134159498448474L));
      instances.add(new KeyedRandom(2664946925654996327L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(-2199868073185670012L));
      instances.add(new KeyedRandom(7999399684041232841L));
      instances.add(new KeyedRandom(5740455863157043345L));
      instances.add(new KeyedRandom(4113500359516049505L));
      instances.add(new KeyedRandom(8614042050486574301L));
      instances.add(new KeyedRandom(4681201311285828761L));
      instances.add(new KeyedRandom(-5595393340880178361L));
      instances.add(new KeyedRandom(1557061159952933236L));
      instances.add(new KeyedRandom(-5484793440509869201L));
      instances.add(new KeyedRandom(1549453282316382222L));
      instances.add(new KeyedRandom(-738483943057114670L));
      instances.add(new KeyedRandom(-6251656800760674595L));
      instances.add(new KeyedRandom(-4040932889296934349L));
      instances.add(new KeyedRandom(-7131094875842510501L));
      instances.add(new KeyedRandom(-1959446382603066988L));
      instances.add(new KeyedRandom(-7803896556370140614L));
      instances.add(new KeyedRandom(-5150820324687237567L));
      instances.add(new KeyedRandom(-4154208138348993803L));
      instances.add(new KeyedRandom(-4870380020909102231L));
      instances.add(new KeyedRandom(5954265629621373139L));
      instances.add(new KeyedRandom(4394534129623820872L));
      instances.add(new KeyedRandom(597334048241533006L));
      instances.add(new KeyedRandom(1147983448560992797L));
      instances.add(new KeyedRandom(-1151105987421858968L));
      instances.add(new KeyedRandom(-7742333693736647334L));
      instances.add(new KeyedRandom(-6069776795504340453L));
      instances.add(new KeyedRandom(-8938980149573360031L));
      instances.add(new KeyedRandom(-7360949775920091861L));
      instances.add(new KeyedRandom(601903267777091717L));
      instances.add(new KeyedRandom(-5933801419564115193L));
      instances.add(new KeyedRandom(-1070477102605453425L));
      instances.add(new KeyedRandom(8594671466011449826L));
      instances.add(new KeyedRandom(-1317730163856745915L));
      instances.add(new KeyedRandom(1111815025450640318L));
      instances.add(new KeyedRandom(-3677357406200109875L));
      instances.add(new KeyedRandom(-5237961212963318957L));
      instances.add(new KeyedRandom(-5084187654610122119L));
      instances.add(new KeyedRandom(6474289649373758632L));
      instances.add(new KeyedRandom(4277784911278717005L));
      instances.add(new KeyedRandom(4842490075430640371L));
      instances.add(new KeyedRandom(5423778056051913520L));
      instances.add(new KeyedRandom(-1141082970595344323L));
      instances.add(new KeyedRandom(-6489719365757356329L));
      instances.add(new KeyedRandom(3431985357655909296L));
      instances.add(new KeyedRandom(2204265601502006886L));
      instances.add(new KeyedRandom(2972440498398388504L));
      instances.add(new KeyedRandom(-4830261678341053769L));
      instances.add(new KeyedRandom(3008355923342952423L));
      instances.add(new KeyedRandom(4301299320376592218L));
      instances.add(new KeyedRandom(8361049550548417127L));
      instances.add(new KeyedRandom(4721246528678603698L));
      instances.add(new KeyedRandom(-2563597050713059308L));
      instances.add(new KeyedRandom(-3358540862332250145L));
      instances.add(new KeyedRandom(-4862767640541588258L));
      instances.add(new KeyedRandom(7885068776398665028L));
      instances.add(new KeyedRandom(2545562691960718403L));
      instances.add(new KeyedRandom(-8233315189505918478L));
      instances.add(new KeyedRandom(-2416653762119074783L));
      instances.add(new KeyedRandom(4074819268932800057L));
      instances.add(new KeyedRandom(2324664225905276820L));
      instances.add(new KeyedRandom(-2775037064236521024L));
      instances.add(new KeyedRandom(-855621734325697321L));
      instances.add(new KeyedRandom(5968404706654160194L));
      instances.add(new KeyedRandom(6350672195578083089L));
      instances.add(new KeyedRandom(-6695025315819820101L));
      instances.add(new KeyedRandom(7994769599712191998L));
      instances.add(new KeyedRandom(6666033760777482077L));
      instances.add(new KeyedRandom(-4144603374244592111L));
      instances.add(new KeyedRandom(2309320258263171086L));
      instances.add(new KeyedRandom(-6396118213038470852L));
      instances.add(new KeyedRandom(-6419991416490149931L));
      instances.add(new KeyedRandom(3614962603220914938L));
      instances.add(new KeyedRandom(-4354436677900669688L));
      instances.add(new KeyedRandom(7450294580155118648L));
      instances.add(new KeyedRandom(-3705314392160264279L));
      instances.add(new KeyedRandom(1609474624475639416L));
      instances.add(new KeyedRandom(-7540915447631539407L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(1125080625358034621L));
      instances.add(new KeyedRandom(-3623144648552382893L));
      instances.add(new KeyedRandom(-868086099337955042L));
      instances.add(new KeyedRandom(8194260028971998222L));
      instances.add(new KeyedRandom(-6028573625320003011L));
      instances.add(new KeyedRandom(-3838955013241587590L));
      instances.add(new KeyedRandom(-4526525281322905232L));
      instances.add(new KeyedRandom(-1521815877418654803L));
      instances.add(new KeyedRandom(7243125648753963525L));
      instances.add(new KeyedRandom(1932851302676082844L));
      instances.add(new KeyedRandom(-4522817997499714509L));
      instances.add(new KeyedRandom(-4416246961552582349L));
      instances.add(new KeyedRandom(-2055238225302181735L));
      instances.add(new KeyedRandom(4197355388357907211L));
      instances.add(new KeyedRandom(7073295040656753688L));
      instances.add(new KeyedRandom(5456012436021365763L));
      instances.add(new KeyedRandom(-4269131523390343383L));
      instances.add(new KeyedRandom(588030718138788162L));
      instances.add(new KeyedRandom(-8486195146228367069L));
      instances.add(new KeyedRandom(-389164448653548400L));
      instances.add(new KeyedRandom(7856087875833321386L));
      instances.add(new KeyedRandom(-547350041912352507L));
      instances.add(new KeyedRandom(1644995125952917710L));
      instances.add(new KeyedRandom(-2493276298280966688L));
      instances.add(new KeyedRandom(6262788392705652413L));
      instances.add(new KeyedRandom(-6386548376283098956L));
      instances.add(new KeyedRandom(1451985776346140462L));
      instances.add(new KeyedRandom(2200893763428934549L));
      instances.add(new KeyedRandom(-1174174037723046000L));
      instances.add(new KeyedRandom(1674093401668878143L));
      instances.add(new KeyedRandom(936043301612905730L));
      instances.add(new KeyedRandom(1573265419132552398L));
      instances.add(new KeyedRandom(4799883078999588485L));
      instances.add(new KeyedRandom(5558539603468309168L));
      instances.add(new KeyedRandom(-4576487469299473089L));
      instances.add(new KeyedRandom(-456997899658051021L));
      instances.add(new KeyedRandom(-4008419546665213830L));
      instances.add(new KeyedRandom(-6568315163682613097L));
      instances.add(new KeyedRandom(-5156181196303784662L));
      instances.add(new KeyedRandom(-9002802907474155265L));
      instances.add(new KeyedRandom(-7617653348115362754L));
      instances.add(new KeyedRandom(3037084044354690700L));
      instances.add(new KeyedRandom(-5173338826610476796L));
      instances.add(new KeyedRandom(-421063999587463696L));
      instances.add(new KeyedRandom(-7630377347911486913L));
      instances.add(new KeyedRandom(-1484825251290748346L));
      instances.add(new KeyedRandom(-3972326101530116541L));
      instances.add(new KeyedRandom(-5766109876959980513L));
      instances.add(new KeyedRandom(-3536715362267224517L));
      instances.add(new KeyedRandom(8520093081809751926L));
      instances.add(new KeyedRandom(2366672300594059502L));
      instances.add(new KeyedRandom(3752291964986258840L));
      instances.add(new KeyedRandom(5532586649710130345L));
      instances.add(new KeyedRandom(1402534682041542517L));
      instances.add(new KeyedRandom(-2832407620453211002L));
      instances.add(new KeyedRandom(8778599957491722826L));
      instances.add(new KeyedRandom(1442363214892825002L));
      instances.add(new KeyedRandom(7464007952045730472L));
      instances.add(new KeyedRandom(-3866493585777007850L));
      instances.add(new KeyedRandom(-357673345966397322L));
      instances.add(new KeyedRandom(-8412172033340245646L));
      instances.add(new KeyedRandom(-6525646090448252234L));
      instances.add(new KeyedRandom(7619520617155040090L));
      instances.add(new KeyedRandom(-3861840213874887876L));
      instances.add(new KeyedRandom(3715894564199196031L));
      instances.add(new KeyedRandom(5619279506367301648L));
      instances.add(new KeyedRandom(4868833874305761354L));
      instances.add(new KeyedRandom(-2109927348417674412L));
      instances.add(new KeyedRandom(564809687804678039L));
      instances.add(new KeyedRandom(-2665186770970101150L));
      instances.add(new KeyedRandom(-4595120734443955155L));
      instances.add(new KeyedRandom(1534021862091994728L));
      instances.add(new KeyedRandom(-2501216016694724492L));
      instances.add(new KeyedRandom(3588432347898929713L));
      instances.add(new KeyedRandom(2318719810406126230L));
      instances.add(new KeyedRandom(-6072774611999010294L));
      instances.add(new KeyedRandom(-1749503791037425736L));
      instances.add(new KeyedRandom(2374524676705003074L));
      instances.add(new KeyedRandom(7201310790002563970L));
      instances.add(new KeyedRandom(-9036597305850630300L));
      instances.add(new KeyedRandom(3024963992514100063L));
      instances.add(new KeyedRandom(-944771263576300171L));
      instances.add(new KeyedRandom(-6251525285144970865L));
      instances.add(new KeyedRandom(2437498365560082257L));
      instances.add(new KeyedRandom(-1200870990693282568L));
      instances.add(new KeyedRandom(5737989328855355059L));
      instances.add(new KeyedRandom(-183205830870835238L));
      instances.add(new KeyedRandom(-4684231424289262345L));
      instances.add(new KeyedRandom(-2199868073198318428L));
      instances.add(new KeyedRandom(-2318213102402191847L));
      instances.add(new KeyedRandom(-4758610750905731745L));
      instances.add(new KeyedRandom(-1063428373502751322L));
      instances.add(new KeyedRandom(-4753345200697036893L));
      instances.add(new KeyedRandom(8897207755671407577L));
      instances.add(new KeyedRandom(1203381214064376540L));
      instances.add(new KeyedRandom(6469985163882561275L));
      instances.add(new KeyedRandom(-1910835320695783778L));
      instances.add(new KeyedRandom(-3872618734318507231L));
      instances.add(new KeyedRandom(2370845894748521245L));
      instances.add(new KeyedRandom(2065650663396599171L));
      instances.add(new KeyedRandom(-4954752927577449910L));
      instances.add(new KeyedRandom(8273069925198601019L));
      instances.add(new KeyedRandom(-6349520764981827529L));
      instances.add(new KeyedRandom(3640108119126369702L));
      instances.add(new KeyedRandom(1797833690601350454L));
      instances.add(new KeyedRandom(-5601447257683556900L));
      instances.add(new KeyedRandom(-4944924279207363040L));
      instances.add(new KeyedRandom(1694429063030363610L));
      instances.add(new KeyedRandom(7042000868212502177L));
      instances.add(new KeyedRandom(-49987763802851971L));
      instances.add(new KeyedRandom(-3031910512386871906L));
      instances.add(new KeyedRandom(-8239879282979513535L));
      instances.add(new KeyedRandom(-9031037469799019048L));
      instances.add(new KeyedRandom(4384527454862516933L));
      instances.add(new KeyedRandom(-2307122849004364508L));
      instances.add(new KeyedRandom(-4783332899128601207L));
      instances.add(new KeyedRandom(-8987546259146924073L));
      instances.add(new KeyedRandom(-2965225940027467973L));
      instances.add(new KeyedRandom(935304665565163728L));
      instances.add(new KeyedRandom(-1994839764860665465L));
      instances.add(new KeyedRandom(-9164220739360171681L));
      instances.add(new KeyedRandom(2939290857286676591L));
      instances.add(new KeyedRandom(-6180204482480023886L));
      instances.add(new KeyedRandom(-6380478717956205760L));
      instances.add(new KeyedRandom(-7174043256347107502L));
      instances.add(new KeyedRandom(3576065969220810872L));
      instances.add(new KeyedRandom(7101252789413956833L));
      instances.add(new KeyedRandom(-8243958725202999615L));
      instances.add(new KeyedRandom(3979107654135745659L));
      instances.add(new KeyedRandom(-1858146817185388827L));
      instances.add(new KeyedRandom(-2991629167735522038L));
      instances.add(new KeyedRandom(-5688789955006709466L));
      instances.add(new KeyedRandom(1134240975663941911L));
      instances.add(new KeyedRandom(-8729263447903979664L));
      instances.add(new KeyedRandom(990603943384021482L));
      instances.add(new KeyedRandom(1601392428015753084L));
      instances.add(new KeyedRandom(-2894373428030320127L));
      instances.add(new KeyedRandom(3178526179693655324L));
      instances.add(new KeyedRandom(4298853372050331678L));
      instances.add(new KeyedRandom(-6171758863968522325L));
      instances.add(new KeyedRandom(5968307851891609894L));
      instances.add(new KeyedRandom(-1612568690115469353L));
      instances.add(new KeyedRandom(377819357260396256L));
      instances.add(new KeyedRandom(3763810451820046609L));
      instances.add(new KeyedRandom(-4232380306049909326L));
      instances.add(new KeyedRandom(-7437789122853983816L));
      instances.add(new KeyedRandom(5680106830184256536L));
      instances.add(new KeyedRandom(-337544721642794312L));
      instances.add(new KeyedRandom(7596172536865105575L));
      instances.add(new KeyedRandom(4491760740889304931L));
      instances.add(new KeyedRandom(-4475889088921288123L));
      instances.add(new KeyedRandom(5403348057416929301L));
      instances.add(new KeyedRandom(-4685343521060123700L));
      instances.add(new KeyedRandom(1530351082757254355L));
      instances.add(new KeyedRandom(-5361906161924628426L));
      instances.add(new KeyedRandom(-2178598247889310267L));
      instances.add(new KeyedRandom(2941156599632589163L));
      instances.add(new KeyedRandom(3350801681171504628L));
      instances.add(new KeyedRandom(-586819325147053537L));
      instances.add(new KeyedRandom(4239703257561233247L));
      instances.add(new KeyedRandom(-2470016765972728842L));
      instances.add(new KeyedRandom(-343790125081156645L));
      instances.add(new KeyedRandom(8303859858674680690L));
      instances.add(new KeyedRandom(-9072655030768353463L));
      instances.add(new KeyedRandom(-872372392100217944L));
      instances.add(new KeyedRandom(-671722916631987876L));
      instances.add(new KeyedRandom(-4167401479505007698L));
      instances.add(new KeyedRandom(7856645825597494046L));
      instances.add(new KeyedRandom(7777287659642257819L));
      instances.add(new KeyedRandom(770905043131749790L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(6957860404328742215L));
      instances.add(new KeyedRandom(1419411871045611746L));
      instances.add(new KeyedRandom(-5260545330534528124L));
      instances.add(new KeyedRandom(-4706142420593507222L));
      instances.add(new KeyedRandom(-8464665739007834216L));
      instances.add(new KeyedRandom(2703270382991596925L));
      instances.add(new KeyedRandom(-7537804305633580753L));
      instances.add(new KeyedRandom(-3488174413194957441L));
      instances.add(new KeyedRandom(-2803263704032703651L));
      instances.add(new KeyedRandom(-2584628457168168796L));
      instances.add(new KeyedRandom(5061242834464618661L));
      instances.add(new KeyedRandom(-7085595461544240914L));
      instances.add(new KeyedRandom(-5875315375233788838L));
      instances.add(new KeyedRandom(-9121962426214618309L));
      instances.add(new KeyedRandom(-5010181900006114364L));
      instances.add(new KeyedRandom(-3978275260230118094L));
      instances.add(new KeyedRandom(-5508384634624043244L));
      instances.add(new KeyedRandom(2036670613328444350L));
      instances.add(new KeyedRandom(-1336154427649227644L));
      instances.add(new KeyedRandom(503704508099103526L));
      instances.add(new KeyedRandom(5590252067138153165L));
      instances.add(new KeyedRandom(-2780539956093745542L));
      instances.add(new KeyedRandom(-585628572393728726L));
      instances.add(new KeyedRandom(-6695547153602230887L));
      instances.add(new KeyedRandom(-2302681311709131370L));
      instances.add(new KeyedRandom(-3269293730502187963L));
      instances.add(new KeyedRandom(3474852907959106356L));
      instances.add(new KeyedRandom(-8453697766411731505L));
      instances.add(new KeyedRandom(6343915078360016759L));
      instances.add(new KeyedRandom(-674389394547861260L));
      instances.add(new KeyedRandom(-8805860210623776255L));
      instances.add(new KeyedRandom(-2029069484749900302L));
      instances.add(new KeyedRandom(2608021914193106855L));
      instances.add(new KeyedRandom(5609721578186839717L));
      instances.add(new KeyedRandom(4271962487232007527L));
      instances.add(new KeyedRandom(3764148892647383590L));
      instances.add(new KeyedRandom(-3231763601454671322L));
      instances.add(new KeyedRandom(-9188222556064637004L));
      instances.add(new KeyedRandom(-5496487749896101476L));
      instances.add(new KeyedRandom(7464395674069991364L));
      instances.add(new KeyedRandom(3756002060313824917L));
      instances.add(new KeyedRandom(7374456391035849551L));
      instances.add(new KeyedRandom(-9121233467021103719L));
      instances.add(new KeyedRandom(5814195615747815123L));
      instances.add(new KeyedRandom(-8110141894723057897L));
      instances.add(new KeyedRandom(1229981522796038062L));
      instances.add(new KeyedRandom(-1752265559591392498L));
      instances.add(new KeyedRandom(4993943207251027208L));
      instances.add(new KeyedRandom(-68975891238864142L));
      instances.add(new KeyedRandom(-3472673048259704115L));
      instances.add(new KeyedRandom(-4036668194202618482L));
      instances.add(new KeyedRandom(-4089923390186177134L));
      instances.add(new KeyedRandom(-6710217569950110337L));
      instances.add(new KeyedRandom(-6745861462213009271L));
      instances.add(new KeyedRandom(-8687499966299156252L));
      instances.add(new KeyedRandom(-3743926049761145671L));
      instances.add(new KeyedRandom(2175173364065646494L));
      instances.add(new KeyedRandom(-6749445703855069159L));
      instances.add(new KeyedRandom(979665859474180023L));
      instances.add(new KeyedRandom(-5198823163549716827L));
      instances.add(new KeyedRandom(-4881483865186443611L));
      instances.add(new KeyedRandom(-1495499148667059316L));
      instances.add(new KeyedRandom(-3848405817461695032L));
      instances.add(new KeyedRandom(-6114864447798473228L));
      instances.add(new KeyedRandom(-8792588421793432781L));
      instances.add(new KeyedRandom(4797754474574122188L));
      instances.add(new KeyedRandom(-3142614937926892580L));
      instances.add(new KeyedRandom(7399592803544160752L));
      instances.add(new KeyedRandom(3199366121479012817L));
      instances.add(new KeyedRandom(-2796140647291536499L));
      instances.add(new KeyedRandom(6728901573678005057L));
      instances.add(new KeyedRandom(7060255578677432457L));
      instances.add(new KeyedRandom(6381764166258652169L));
      instances.add(new KeyedRandom(4193271652947960020L));
      instances.add(new KeyedRandom(-7908089903342760737L));
      instances.add(new KeyedRandom(7397280220860632304L));
      instances.add(new KeyedRandom(-4354436677885924088L));
      instances.add(new KeyedRandom(7801265631890315862L));
      instances.add(new KeyedRandom(-8142709072414130163L));
      instances.add(new KeyedRandom(-8487722042068313019L));
      instances.add(new KeyedRandom(3948354133425718586L));
      instances.add(new KeyedRandom(7588685614602542326L));
      instances.add(new KeyedRandom(3611212498808213364L));
      instances.add(new KeyedRandom(4137559037543842001L));
      instances.add(new KeyedRandom(-3224216074698312528L));
      instances.add(new KeyedRandom(3478450020557486121L));
      instances.add(new KeyedRandom(-3778511375458176899L));
      instances.add(new KeyedRandom(2133767902305616704L));
      instances.add(new KeyedRandom(-7957570171507910677L));
      instances.add(new KeyedRandom(-6191349959111383616L));
      instances.add(new KeyedRandom(3180777864419981590L));
      instances.add(new KeyedRandom(-5911439193847163972L));
      instances.add(new KeyedRandom(-7646732455710571214L));
      instances.add(new KeyedRandom(8881589241568913136L));
      instances.add(new KeyedRandom(198326875254002278L));
      instances.add(new KeyedRandom(-2480600031078351423L));
      instances.add(new KeyedRandom(-1730140688334001435L));
      instances.add(new KeyedRandom(-8648624399589712669L));
      instances.add(new KeyedRandom(224779363156740792L));
      instances.add(new KeyedRandom(-4882526485643772859L));
      instances.add(new KeyedRandom(2474850328423383668L));
      instances.add(new KeyedRandom(3609647753717987823L));
      instances.add(new KeyedRandom(4235763466962491784L));
      instances.add(new KeyedRandom(4546027828526791521L));
      instances.add(new KeyedRandom(1182152936334234004L));
      instances.add(new KeyedRandom(-8890582888954841004L));
      instances.add(new KeyedRandom(-7112256110737440728L));
      instances.add(new KeyedRandom(-7391519661379170173L));
      instances.add(new KeyedRandom(-3789693876289802980L));
      instances.add(new KeyedRandom(2409803280816842589L));
      instances.add(new KeyedRandom(8925888389128097923L));
      instances.add(new KeyedRandom(7751072224833962594L));
      instances.add(new KeyedRandom(-8934226908729875179L));
      instances.add(new KeyedRandom(-6128165345233362077L));
      instances.add(new KeyedRandom(-556488510294956059L));
      instances.add(new KeyedRandom(-2227551460257702870L));
      instances.add(new KeyedRandom(-1631325638127360470L));
      instances.add(new KeyedRandom(-3724221334108471800L));
      instances.add(new KeyedRandom(-1577969729299449960L));
      instances.add(new KeyedRandom(-4508657386479723147L));
      instances.add(new KeyedRandom(-6827906427837360170L));
      instances.add(new KeyedRandom(4261516338954368018L));
      instances.add(new KeyedRandom(-8222507019854736573L));
      instances.add(new KeyedRandom(5913324691290675730L));
      instances.add(new KeyedRandom(2627640472579520544L));
      instances.add(new KeyedRandom(20973079678154593L));
      instances.add(new KeyedRandom(-7166701301734887813L));
      instances.add(new KeyedRandom(7276173560241234979L));
      instances.add(new KeyedRandom(-4951527281945074081L));
      instances.add(new KeyedRandom(-3749751707555055734L));
      instances.add(new KeyedRandom(8211548396266466448L));
      instances.add(new KeyedRandom(-4222700939216367380L));
      instances.add(new KeyedRandom(3036894570548925625L));
      instances.add(new KeyedRandom(520159373472467990L));
      instances.add(new KeyedRandom(-6555090717115385732L));
      instances.add(new KeyedRandom(1101741959597137417L));
      instances.add(new KeyedRandom(-2600251467736397938L));
      instances.add(new KeyedRandom(7251880980567930331L));
      instances.add(new KeyedRandom(8070970539202044182L));
      instances.add(new KeyedRandom(-6426533880118405977L));
      instances.add(new KeyedRandom(3321961587514702220L));
      instances.add(new KeyedRandom(6456396294946459754L));
      instances.add(new KeyedRandom(1690881958575562184L));
      instances.add(new KeyedRandom(6964300791614143963L));
      instances.add(new KeyedRandom(9098866196365249793L));
      instances.add(new KeyedRandom(-7675547809530829187L));
      instances.add(new KeyedRandom(1771705134392227135L));
      instances.add(new KeyedRandom(372959758852267295L));
      instances.add(new KeyedRandom(3942873156846860000L));
      instances.add(new KeyedRandom(8981713683176577110L));
      instances.add(new KeyedRandom(8104126571545486021L));
      instances.add(new KeyedRandom(5233538756256488575L));
      instances.add(new KeyedRandom(5869148894929622392L));
      instances.add(new KeyedRandom(5291642175418052419L));
      instances.add(new KeyedRandom(-4851882170122919376L));
      instances.add(new KeyedRandom(-1187886754316776151L));
      instances.add(new KeyedRandom(5515894639317964828L));
      instances.add(new KeyedRandom(-8659545981415729983L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(-6629616748463042916L));
      instances.add(new KeyedRandom(2380061129660447457L));
      instances.add(new KeyedRandom(5431245313574992945L));
      instances.add(new KeyedRandom(-6207152524105778441L));
      instances.add(new KeyedRandom(-3696668097281612733L));
      instances.add(new KeyedRandom(-3677130269940187736L));
      instances.add(new KeyedRandom(4094443509072168520L));
      instances.add(new KeyedRandom(7275055790906729125L));
      instances.add(new KeyedRandom(1586054675850047058L));
      instances.add(new KeyedRandom(7842088165652314047L));
      instances.add(new KeyedRandom(-2966354706208551083L));
      instances.add(new KeyedRandom(-6269511633706094365L));
      instances.add(new KeyedRandom(3478305132495321667L));
      instances.add(new KeyedRandom(7488490929597567004L));
      instances.add(new KeyedRandom(8195070163247899564L));
      instances.add(new KeyedRandom(2568473951917860380L));
      instances.add(new KeyedRandom(3608073424484530367L));
      instances.add(new KeyedRandom(-327431393145080925L));
      instances.add(new KeyedRandom(2223766061730777668L));
      instances.add(new KeyedRandom(2370836548897588029L));
      instances.add(new KeyedRandom(-7408332116754154833L));
      instances.add(new KeyedRandom(2409372205965359153L));
      instances.add(new KeyedRandom(-8634491178155308264L));
      instances.add(new KeyedRandom(4090904648224444794L));
      instances.add(new KeyedRandom(-5249942783430347539L));
      instances.add(new KeyedRandom(5974034488337126131L));
      instances.add(new KeyedRandom(-1993201968434036246L));
      instances.add(new KeyedRandom(2731711603461646467L));
      instances.add(new KeyedRandom(-4187991224538684004L));
      instances.add(new KeyedRandom(-5716396788187877782L));
      instances.add(new KeyedRandom(4618388598952482693L));
      instances.add(new KeyedRandom(3465195975453969358L));
      instances.add(new KeyedRandom(-2870800678152445189L));
      instances.add(new KeyedRandom(-2238289803662922685L));
      instances.add(new KeyedRandom(-8361023787289306413L));
      instances.add(new KeyedRandom(5689005883756827209L));
      instances.add(new KeyedRandom(4287329622573786992L));
      instances.add(new KeyedRandom(9026954443673021053L));
      instances.add(new KeyedRandom(3185650327741632807L));
      instances.add(new KeyedRandom(8918844258407660641L));
      instances.add(new KeyedRandom(6727413124131428333L));
      instances.add(new KeyedRandom(7424117087968390228L));
      instances.add(new KeyedRandom(-3009738347151615985L));
      instances.add(new KeyedRandom(5334389723990130705L));
      instances.add(new KeyedRandom(7171578750648682963L));
      instances.add(new KeyedRandom(-1590972396936489832L));
      instances.add(new KeyedRandom(5749679788244216061L));
      instances.add(new KeyedRandom(210400585519208922L));
      instances.add(new KeyedRandom(3287262113172578757L));
      instances.add(new KeyedRandom(3300022453818878113L));
      instances.add(new KeyedRandom(515176723738966161L));
      instances.add(new KeyedRandom(-1117457307167058289L));
      instances.add(new KeyedRandom(4669577288527869065L));
      instances.add(new KeyedRandom(-8523382724058727562L));
      instances.add(new KeyedRandom(-4246677121058633132L));
      instances.add(new KeyedRandom(4800169843989241836L));
      instances.add(new KeyedRandom(-3207758777257726507L));
      instances.add(new KeyedRandom(-1870325847822753609L));
      instances.add(new KeyedRandom(7710967513922426228L));
      instances.add(new KeyedRandom(2031531665822464109L));
      instances.add(new KeyedRandom(-9001792459683668526L));
      instances.add(new KeyedRandom(-418691708970449730L));
      instances.add(new KeyedRandom(-4934753078734610441L));
      instances.add(new KeyedRandom(4988269906541551867L));
      instances.add(new KeyedRandom(-1324630136717675503L));
      instances.add(new KeyedRandom(7483126246687095132L));
      instances.add(new KeyedRandom(6100040502592305352L));
      instances.add(new KeyedRandom(2575699363347836121L));
      instances.add(new KeyedRandom(3987092346218333317L));
      instances.add(new KeyedRandom(2417969007677363404L));
      instances.add(new KeyedRandom(-9183321369999978930L));
      instances.add(new KeyedRandom(-8147644644333389514L));
      instances.add(new KeyedRandom(-3674229623768772234L));
      instances.add(new KeyedRandom(2557807770596401903L));
      instances.add(new KeyedRandom(1330794171617330462L));
      instances.add(new KeyedRandom(1246322159023180215L));
      instances.add(new KeyedRandom(-1463992907374248310L));
      instances.add(new KeyedRandom(8657117237896907951L));
      instances.add(new KeyedRandom(-2927084909782323969L));
      instances.add(new KeyedRandom(5757360433847699635L));
      instances.add(new KeyedRandom(-1422456794919922L));
      instances.add(new KeyedRandom(7868892744691856358L));
      instances.add(new KeyedRandom(-7538390857707427652L));
      instances.add(new KeyedRandom(-7312068005714277710L));
      instances.add(new KeyedRandom(5191873882296146859L));
      instances.add(new KeyedRandom(8274525137268232087L));
      instances.add(new KeyedRandom(-2582350942046856674L));
      instances.add(new KeyedRandom(7036540971166983088L));
      instances.add(new KeyedRandom(1851016493733964870L));
      instances.add(new KeyedRandom(4223825282593380843L));
      instances.add(new KeyedRandom(1517610333502620847L));
      instances.add(new KeyedRandom(1262552264976615132L));
      instances.add(new KeyedRandom(5259183272581873536L));
      instances.add(new KeyedRandom(4657957339362954306L));
      instances.add(new KeyedRandom(-451153042852722358L));
      instances.add(new KeyedRandom(-6821837977207221662L));
      instances.add(new KeyedRandom(36308363978090102L));
      instances.add(new KeyedRandom(1874482234468511125L));
      instances.add(new KeyedRandom(-8275571368092931193L));
      instances.add(new KeyedRandom(-4792652839937190748L));
      instances.add(new KeyedRandom(2907778660266253268L));
      instances.add(new KeyedRandom(-6151265135997116252L));
      instances.add(new KeyedRandom(1706167694625964535L));
      instances.add(new KeyedRandom(-3634129016142178872L));
      instances.add(new KeyedRandom(4466239538367167730L));
      instances.add(new KeyedRandom(-4036221807709780724L));
      instances.add(new KeyedRandom(49989565513086209L));
      instances.add(new KeyedRandom(1435726603059072717L));
      instances.add(new KeyedRandom(-1004732923522604118L));
      instances.add(new KeyedRandom(-4884014411563011887L));
      instances.add(new KeyedRandom(-2444034754611502704L));
      instances.add(new KeyedRandom(7287766218250157228L));
      instances.add(new KeyedRandom(4268393584731968732L));
      instances.add(new KeyedRandom(-5673264568682240103L));
      instances.add(new KeyedRandom(-2545309415214537604L));
      instances.add(new KeyedRandom(-6528310551676243841L));
      instances.add(new KeyedRandom(-1047170212370814396L));
      instances.add(new KeyedRandom(3362090802085638293L));
      instances.add(new KeyedRandom(9179857324746921308L));
      instances.add(new KeyedRandom(8789244111814944279L));
      instances.add(new KeyedRandom(7879991270878566393L));
      instances.add(new KeyedRandom(-831849989854865365L));
      instances.add(new KeyedRandom(-8084883211160320343L));
      instances.add(new KeyedRandom(-7639948328183051123L));
      instances.add(new KeyedRandom(1317936464713394515L));
      instances.add(new KeyedRandom(-7800451365500118457L));
      instances.add(new KeyedRandom(-8955027013582157132L));
      instances.add(new KeyedRandom(-1730140138649425179L));
      instances.add(new KeyedRandom(-402818485573825747L));
      instances.add(new KeyedRandom(840294044967130201L));
      instances.add(new KeyedRandom(8371060572785124754L));
      instances.add(new KeyedRandom(4360034646334646955L));
      instances.add(new KeyedRandom(-7627878873358341956L));
      instances.add(new KeyedRandom(4947223571371114504L));
      instances.add(new KeyedRandom(2961828950118485435L));
      instances.add(new KeyedRandom(6767870493588089229L));
      instances.add(new KeyedRandom(-903571157513135182L));
      instances.add(new KeyedRandom(-5147164347571645625L));
      instances.add(new KeyedRandom(4737341598527373667L));
      instances.add(new KeyedRandom(-3936870613226898647L));
      instances.add(new KeyedRandom(-3180012980976702281L));
      instances.add(new KeyedRandom(8113101653089634928L));
      instances.add(new KeyedRandom(5713586767547387691L));
      instances.add(new KeyedRandom(-8713290002045333936L));
      instances.add(new KeyedRandom(923129839609895289L));
      instances.add(new KeyedRandom(-4094434574545598327L));
      instances.add(new KeyedRandom(8690228086251224616L));
      instances.add(new KeyedRandom(-2187884258452561506L));
      instances.add(new KeyedRandom(792619103310903189L));
      instances.add(new KeyedRandom(4519148855723310772L));
      instances.add(new KeyedRandom(3083976537287547967L));
      instances.add(new KeyedRandom(4319063163038931331L));
      instances.add(new KeyedRandom(-2809630400563403400L));
      instances.add(new KeyedRandom(2568583272416604100L));
      instances.add(new KeyedRandom(-3674278961708273849L));
      instances.add(new KeyedRandom(4704649855338697110L));
      instances.add(new KeyedRandom(-8567997036043815704L));
      instances.add(new KeyedRandom(5146865764659404777L));
      instances.add(new KeyedRandom(-202999396331395600L));
      instances.add(new KeyedRandom(-5314722149512520265L));
      instances.add(new KeyedRandom(-5939794737864038881L));
      instances.add(new KeyedRandom(-2951749388028997359L));
      instances.add(new KeyedRandom(-9067765456003000730L));
      shiftTable = (int[])shiftTable.clone();
      instances.add(new KeyedRandom(-3206670976462814004L));
      instances.add(new KeyedRandom(-8639669669134805179L));
      instances.add(new KeyedRandom(5483967964403996941L));
      instances.add(new KeyedRandom(-1396610422113681926L));
      instances.add(new KeyedRandom(-7966003022962494122L));
      instances.add(new KeyedRandom(-8878465816674756020L));
      instances.add(new KeyedRandom(-5197233347185732595L));
      instances.add(new KeyedRandom(8816937985548799922L));
      instances.add(new KeyedRandom(-7121101768223142651L));
      instances.add(new KeyedRandom(-1409428192206253228L));
      instances.add(new KeyedRandom(697991295047853334L));
      instances.add(new KeyedRandom(1021136400113783214L));
      instances.add(new KeyedRandom(-1335709672646072648L));
      instances.add(new KeyedRandom(-8947716049872345750L));
      instances.add(new KeyedRandom(4678403799682764219L));
      instances.add(new KeyedRandom(1958745033377407843L));
      instances.add(new KeyedRandom(-5833178961027517805L));
      instances.add(new KeyedRandom(-5635527902514932862L));
      instances.add(new KeyedRandom(4385407787526772917L));
      instances.add(new KeyedRandom(2324229496567338490L));
      instances.add(new KeyedRandom(2466447178349477411L));
      instances.add(new KeyedRandom(743051087802368479L));
      instances.add(new KeyedRandom(1063492479090761683L));
      instances.add(new KeyedRandom(-36815226099322831L));
      instances.add(new KeyedRandom(-743198302964133875L));
      instances.add(new KeyedRandom(-3320987674291345000L));
      instances.add(new KeyedRandom(4131490490128942008L));
      instances.add(new KeyedRandom(6333869094634534892L));
      instances.add(new KeyedRandom(-7383846770441792439L));
      instances.add(new KeyedRandom(6394402079483683950L));
      instances.add(new KeyedRandom(-6645871109639215897L));
      instances.add(new KeyedRandom(7141350416124306446L));
      instances.add(new KeyedRandom(737536644019848301L));
      instances.add(new KeyedRandom(5078482319366365919L));
      instances.add(new KeyedRandom(7814163291684601129L));
      instances.add(new KeyedRandom(7360015760871293979L));
      instances.add(new KeyedRandom(8372671092218709051L));
      instances.add(new KeyedRandom(-2505670661423040135L));
      instances.add(new KeyedRandom(-3912386863980761133L));
      instances.add(new KeyedRandom(-4387836054010481515L));
      instances.add(new KeyedRandom(-3321352310821625786L));
      instances.add(new KeyedRandom(7315615692505174996L));
      instances.add(new KeyedRandom(-9215076977065212498L));
      instances.add(new KeyedRandom(-3771112108309688605L));
      instances.add(new KeyedRandom(-3373671090130056226L));
      instances.add(new KeyedRandom(-2545717465495390290L));
      instances.add(new KeyedRandom(-7524898334829528930L));
      instances.add(new KeyedRandom(4264554449729197328L));
      instances.add(new KeyedRandom(8517617016677823023L));
      instances.add(new KeyedRandom(2075884847142473509L));
      instances.add(new KeyedRandom(3462946365727104673L));
      instances.add(new KeyedRandom(-453066773690532795L));
      instances.add(new KeyedRandom(1543277897604424180L));
      instances.add(new KeyedRandom(3508584144779890924L));
      instances.add(new KeyedRandom(-3336527913753589679L));
      instances.add(new KeyedRandom(6418859732255388637L));
      instances.add(new KeyedRandom(6066410994201475930L));
      instances.add(new KeyedRandom(-6639262113635446321L));
      instances.add(new KeyedRandom(-4314919453592755599L));
      instances.add(new KeyedRandom(6271950313640048672L));
      instances.add(new KeyedRandom(4179623507489444394L));
      instances.add(new KeyedRandom(6573259645006407007L));
      instances.add(new KeyedRandom(-7927705410442238711L));
      instances.add(new KeyedRandom(2528917892438502316L));
      instances.add(new KeyedRandom(6833837646499441888L));
      instances.add(new KeyedRandom(-9139505750741484875L));
      instances.add(new KeyedRandom(-95820769763662289L));
      instances.add(new KeyedRandom(7482377403266978862L));
      instances.add(new KeyedRandom(6227562442976523270L));
      instances.add(new KeyedRandom(-5353518655996351240L));
      instances.add(new KeyedRandom(-3661109801493671613L));
      instances.add(new KeyedRandom(-6628382569561469542L));
      instances.add(new KeyedRandom(1560435356688768690L));
      instances.add(new KeyedRandom(3088228088982533318L));
      instances.add(new KeyedRandom(6573259644939363711L));
      instances.add(new KeyedRandom(-6270116333885021567L));
      instances.add(new KeyedRandom(-6697466576437752676L));
      instances.add(new KeyedRandom(-6208385134072375436L));
      instances.add(new KeyedRandom(1905227147112044985L));
      instances.add(new KeyedRandom(6529171547614617261L));
      shiftTable = new int[]{
         -16,
         -1,
         1,
         -7,
         -23,
         -46,
         -19,
         -8,
         -47,
         -24,
         7,
         -49,
         -25,
         -35,
         -7,
         8,
         16,
         -23,
         -24,
         -40,
         -37,
         7,
         -14,
         -38,
         -5,
         19,
         -24,
         23,
         -10,
         5,
         -19,
         -25,
         -15,
         24,
         -24,
         -6,
         14,
         25,
         10,
         -14,
         23,
         6,
         24,
         -11,
         -18,
         -18,
         -6,
         15,
         35,
         19,
         24,
         46,
         6,
         14,
         11,
         47,
         25,
         37,
         24,
         40,
         49,
         38,
         18,
         18
      };
   }
}
