import java.util.*;

/*
    6821600970 ชุติมา บุตรสอน
    6821601364 รัญชิดา แซ่เตีย

*/


/**
 * 
 * BoundedStack คือ รายชื่อและคิวของลูกค้าร้านชานมไข่มุกท้งหมด
 * 
 */
public class BoundedStack {


    private final List<String> names ;
    private int queue;
    public static final int MAX_NAMES = 150;
    private int head;        
    private int nextQueue ; 
    private final int capacity;


    //AF(names, queue ,head, nextQueue , capacity ) = รายชื่อคิวของลูกค้าที่ร้านชานมไข่มุก ตามลำดับ , หมายเลขคิวของลูกค้าคนปัจจุบัน , หัวคิว  , เลขคิวที่วิ่งไปเรื่อย ๆ , ความจุของคิว


    //RI
    // - names != null
    // - names != String ว่าง
    // - names <= 20 ตัวอักษร 
    // - queue > 0
    // - 0 < capacity <= MAX_NAMES(150)
    // - หมายเลขคิวต้องไม่ซ้ำกัน 
    // - nextQueue > 0
    // - 0 > head < capacity

    // Safety from rep exposure:
    //   ให้ names , capacity เป็น final
    //   คัดลอกทั้งขาเข้าและขาออก

    private void checkRep(){
        assert names != null;
        assert names.size() <= capacity ;


        //Set<String> seen = new HashSet<>();
        Set<String> seen = new LinkedHashSet<>();
        
        for(int i = 0 ; i<queue;i++){
        String s = names.get((head + i) % capacity ) ;
        assert s != null ;
        assert !(s== "") ; 
        assert seen.add(s) : "duplicate" + s;
        }

    }

    
   
    /**
     สร้าง Queue ว่างและระบุขนาดความจุสูงสุด
     *
     * @param capacity ความจุสูงสุดของคิว ต้องมีค่าตั้งแต่ 1 ถึง MAX_NAMES
     * @throws IllegalArgumentException ถ้า capacity น้อยกว่าหรือเท่ากับ 0 หรือมากกว่า MAX_NAMES
     */ 

    public BoundedStack( int capacity ){
        
        if (capacity <= 0 || capacity > MAX_NAMES) {
            throw new IllegalArgumentException("Capacity must be between 1 and " + MAX_NAMES);
        }
        this.names = new ArrayList<>();
        this.queue = 0; 
        this.head = 0;
        this.nextQueue = 1 ;
        this.capacity = capacity ;
        checkRep();
           
    }
   

     /**
     * สร้างเคิวจากรายชื่อลูกค้าที่ให้มา
     *
     *
     * @param initial รายชื่อลูกค้าเริ่มต้น ต้องไม่ซ้ำและไม่เกิน MAX_NAMES
     * @throws IllegalArgumentException ถ้า initial ผิดเงื่อนไข
     */

    public BoundedStack(List<String> initialNames) {
        if (initialNames == null || initialNames.size() > MAX_NAMES) {
        throw new IllegalArgumentException("Invalid initial names list");
    }
        // Defensive Copy ขาเข้า ป้องกัน Input Rep Exposure
        this.names = new ArrayList<>(initialNames);
        this.capacity = MAX_NAMES;
        this.nextQueue = this.names.size() + 1;
        checkRep();
    }


    // ===== Mutators =====

    /**
     *
     * @param name ชื่อลูกค้า ต้องไม่เป็น null และไม่เป็นสตริงว่าง
     * @return true ถ้าเพิ่มสำเร็จ, false ถ้ามีชื่อนี้อยู่แล้วหรือคิวเต็มแล้ว
     * @throws IllegalArgumentException ถ้า name เป็น null หรือสตริงว่าง
     */

    public boolean add(String name) {
        if(name== null || name == "") throw new IllegalArgumentException();
        if(names.contains(name)||names.size()==MAX_NAMES)  return false;
        names.add(name);
        checkRep();
        return true;   
    }

     /**
     *
     * @param name ชื่อลูกค้าที่ต้องการลบ
     * @return true ถ้าลบสำเร็จ, false ถ้าไม่พบชื่อนี้
     */

    public boolean remove(String name) {
        if(!names.contains(name)) return false;
        names.remove(names); 
        checkRep();
        return true;  
    }





    /**
     * 
     * @return
     */
    public BoundedStack shuffled() {
        
        List<String> shuffledNames = new ArrayList<>(this.names);
        Collections.shuffle(shuffledNames);
        checkRep();
        return new BoundedStack(shuffledNames); 

    }
    /**
     * 
     * @return
     */
    public List<String> names() { 
        checkRep();
        return new ArrayList<>(names); 
    }
   /**
    * ตรวจว่าชื่อนี้กำลังรออยู่ในคิวหรือไม่ 
    * @param name = ชื่อลูกค้าที่ต้องการจะตรวจสอบ
    * @return true เมื่อชื่อลูกค้าอยู่ในคิว false เมื่อชื่อลูกค้าเป็น null หรือช่องว่าง
    */
    public boolean contains(String name) {
        
        return names.contains(name) ;  
    }

        // ===== Mutator =====
        /**
         * 
         * @param name ต้องไม่เป็น null
         * @throws IllgalArgumentException เมื่อชื่อเป็นช่องว่างหรือ null
         * @return เลขคิว
         */

       public int enqueue(String name) {
            if (name == null || name.trim().isEmpty()) {
        throw new IllegalArgumentException("ชื่อต้องไม่เป็นช่องว่าง");
        }

        String cleanedName = name.trim();
            if (names.size() == capacity) return -1;
            if (contains(cleanedName)) return -1;
            names.add(cleanedName);
            int ticket = nextQueue++;
            checkRep();
            return ticket;
       }

        /**
         * เรียกคิวถัดไป — เอาคนหัวแถวออกจากคิวและคืนชื่อ
         * @return ชื่อลูกค้า
         * @throws IllgalArgumentException เมื่อคิวว่าง
        */       
        public String dequeue() {
            if (queue == 0) {
            throw new IllegalArgumentException("คิวว่าง ไม่มีลูกค้าให้เรียก"); }
            String name = names.remove(0);
            checkRep(); 
            return name;

           }

        // ===== Observers =====
        /**
         * ดูว่าใครเป็นคิวถัดไป
         * @return ชื่อลูกค้าปัจจุบัน
         * @throws IllgalArgumentException เมื่อคิวว่าง
         */

        public String peek() {

        if (names.isEmpty()) {
            throw new IllegalArgumentException("คิวว่าง");
        }       
        checkRep();
        return names.get(0);
       }

       public int size(){
            return queue ;
       }
        public boolean isEmpty() {
            return queue == 0;
       }

       public boolean isFull() {
            return queue == capacity;
      }

      public int capacity() {
             return capacity;
    }



    



    
}
