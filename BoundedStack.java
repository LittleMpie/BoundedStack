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

    

   
    
}
