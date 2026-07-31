import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;



public class BoundedStackTest{

     private static int passed = 0;
     private static int failed = 0;


     private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

     public static void main(String[] args) {

        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea PeopelNumberTest\n");
        }


     System.out.println("===  People Number Test Suite ===\n");


        testCreators();
        testAdd();
        testRemove();
        testObservers();
        testProducer();
        testExposure();




        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");
     }



     private static void testCreators(){


        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack(3);
        check("new() -> empty", empty.size() == 0);
        check("new() -> contains nothing", !empty.contains("anything"));

        BoundedStack n = new BoundedStack(Arrays.asList("Baitong", "Muay", "JJ"));
        check("new(list) -> size 3", n.size() == 3);
        check("new(list) -> contains Baitong", n.contains("Baitong"));
        check("new(list) -> preserves order",
                n.names().equals(Arrays.asList("Baitong", "Muay", "JJ")));

        // boundary: list ว่างคือขอบล่างที่ถูกต้อง
        BoundedStack fromEmpty = new BoundedStack(new ArrayList<String>());
        check("new(empty list) -> empty", fromEmpty.size() == 0);
 
        // input ผิดโยน Exeption
        boolean threwDup = false;
        try {
            new BoundedStack(Arrays.asList("Baitong", "Baitong"));
        } catch (IllegalArgumentException e) {
            threwDup = true;
        }
        check("new(duplicates) -> throws IllegalArgumentException", threwDup);

        boolean threwNull = false;
        try {
            new BoundedStack(Arrays.asList("Baitong", null));
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("new(list with null) -> throws IllegalArgumentException", threwNull);

        boolean threwNullList = false;
        try {
            new BoundedStack(null);
        } catch (IllegalArgumentException e) {
            threwNullList = true;
        }
        check("new(null) -> throws IllegalArgumentException", threwNullList);



     }
     private static void  testAdd() {

        System.out.println("\n-- Add --");

        BoundedStack n = new BoundedStack(3);
        check("add(Muay) -> returns true", n.add("Muay"));
        check("add(Muay) -> size 1", n.size() == 1);
        check("add(Muay) -> found by contains", n.contains("Muay"));

        n.add("Baitong");
        n.add("JJ");
        check("add preserves insertion order",
                n.names().equals(Arrays.asList("Muay", "Baitong", "JJ")));

        // ชื่อซ้ำ คืน false 
        check("add duplicate -> returns false", !n.add("Muay"));
        check("failed add leaves size unchanged", n.size() == 3);

        // input ที่ผิดเงื่อนไขโยน exception
        boolean threwEmpty = false;
        try {
            n.add("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("add(empty string) -> throws IllegalArgumentException", threwEmpty);

        boolean threwNull = false;
        try {
            n.add(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("add(null) -> throws IllegalArgumentException", threwNull);

        check("failed adds leave queue unchanged", n.size() == 3);

        // boundary: เติมจนเต็มพอดีแล้วเติมเพิ่ม
        BoundedStack full = new BoundedStack(BoundedStack.MAX_NAMES);
        for (int i = 0; i < BoundedStack.MAX_NAMES; i++) {
            full.add("Name" + i);
        }
        check("can fill up to MAX_NAMES", full.size() == BoundedStack.MAX_NAMES);
        check("add when full -> returns false", !full.add("one more"));
        check("full Queue stays at MAX_NAMES",
                full.size() == BoundedStack.MAX_NAMES);



     }

      // --- Mutator: remove ทั้งกรณีพบและไม่พบ ---
     private static void testRemove() {

    
        System.out.println("\n-- Remove --");

        BoundedStack n = new BoundedStack(Arrays.asList("Muay", "Baitong", "JJ"));
        check("remove(Baitong) -> returns true", n.remove("Baitong"));
        check("remove -> size decreases", n.size() == 2);
        check("remove -> name is gone", !n.contains("Baitong"));
        check("remove keeps the others in order",
                n.names().equals(Arrays.asList("Muay", "JJ")));

        // ลบรายชื่อ คืน false 
        check("remove missing name-> returns false", !n.remove("nope"));
        check("failed remove leaves size unchanged", n.size() == 2);

        // boundary: ลบจนหมด
        n.remove("Muay");
        n.remove("JJ");
        check("remove all -> empty", n.size() == 0);
        check("remove on empty queue -> returns false", !n.remove("Muay"));
    

     }



     private static void testObservers() {
            System.out.println("\n-- Observers --");
            BoundedStack s = new BoundedStack (3);
            s.enqueue("Muay");
            s.enqueue("Baitong");
            check("peek -> returns front without removing", s.peek().equals("Muay"));
            check("peek -> size unchanged", s.size()==2 );
            check("contains -> finds existing", s.contains("Baitong"));
            check("contains -> rejects missing", !s.contains("JJ"));

        boolean threwPeekEmpty = false;
        try {
            new BoundedStack(1).peek();
        } catch (IllegalArgumentException e) {
            threwPeekEmpty = true;
        }
        check("peek on empty queue -> throws IllegalArgumentException", threwPeekEmpty);
        }



     private static void testProducer() {
        System.out.println("\n-- Producer (shuffled) --");
        
        BoundedStack original = new BoundedStack(Arrays.asList("Muay","BaiTong","JJ","Yokky"));
        BoundedStack shuffled =  original.shuffled();

        
        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<String>(original.names());
        List<String> b = new ArrayList<String>(shuffled.names());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same songs", a.equals(b));
          check("shuffled does not mutate the original",
                original.names().equals(Arrays.asList("Muay","BaiTong","JJ","Yokky")));
        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        shuffled.enqueue("E");
        check("mutating the result does not affect the original",
                original.size() == 4);

                 // boundary: shuffle เพลย์ลิสต์ว่างต้องไม่พัง
        BoundedStack emptyShuffled = new BoundedStack(5).shuffled();
        check("shuffling an empty playlist is safe", emptyShuffled.size() == 0);



     }


     private static void testExposure() {
          System.out.println("\n-- Representation Exposure --");
            // ขาออก
                BoundedStack s = new BoundedStack(3);
                s.enqueue("SomChai") ;
                List<String> got = s.names();
                got.clear();
                check("clearing result of names() does not affect queue",
                s.size() == 1);

                got = s.names();
                got.add("injected");
                check("adding to result of names() does not affect queue",
                s.size() == 1 && !s.contains("injected"));

            // สองครั้งต้องเป็นคนละ object
                 check("name() returns a fresh list each call",
                s.names() != s.names());

            // ขาเข้า: แก้ list ที่ส่งให้ constructor ต้องไม่กระทบ rep
                  List<String> input = new ArrayList<>(Arrays.asList("A", "B"));
                  BoundedStack s2 = new BoundedStack(input);

                  input.clear();
                  check("clearing constructor argument does not affect queue",
                  s2.size() == 2);

                  input.add("injected");
                  check("adding to constructor argument does not affect queue ",
                  !s2.contains("injected"));




                
     }

}