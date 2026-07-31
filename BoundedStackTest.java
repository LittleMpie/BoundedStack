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

        BoundedStack empty = new BoundedStack(150);
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
     private static void  testAdd() {}
     private static void testRemove() {}



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
        
        BoundedStack original = new BoundedStack(3);
        BoundedStack shuffled =  original.shuffled();

        
        check("shuffled has the same size", shuffled.size() == original.size());

        List<String> a = new ArrayList<String>(original.names());
        List<String> b = new ArrayList<String>(shuffled.names());
        Collections.sort(a);
        Collections.sort(b);
        check("shuffled contains exactly the same songs", a.equals(b));
          check("shuffled does not mutate the original",
                original.names().equals(Arrays.asList("A", "B", "C", "D")));
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
                  s.size() == 2);

                  input.add("injected");
                  check("adding to constructor argument does not affect queue ",
                  !s.contains("injected"));




                
     }

}