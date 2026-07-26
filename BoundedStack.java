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
    private final int number;

    //AF(names,number) = รายชื่อคิวของลูกค้าที่ร้านชานมไข่มุก ตามลำดับ , หมายเลขคิวของลูกค้า
    //RI
    // - names != null
    // - ชื่อลูกค้าต้องไม่เป็น String ว่าง
    // - หมายเลขคิวต้อง ต้อง >0
    // - หมายเลขคิวต้องไม่ซ้ำกัน

    // Safety from rep exposure:
    //   ให้ ืnames , number เป็น final
    //   คัดลอกทั้งขาเข้าและขาออก
   

    public BoundedStack( int number){




        this.names = new ArrayList<>();
        this.number = number;
        checkRep();       
    
}


    private void checkRep() {
        assert names != null : "songs ต้องไม่เป็น null";

        Set<String> seen = new HashSet<>();
        for (String s : names ) {
          assert s != null;
          assert s != "";
          assert seen.add(s) : "duplicate: " + s;}
     


          }


}

   


