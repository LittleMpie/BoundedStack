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



     private static void testCreators(){}
     private static void  testAdd() {}
     private static void testRemove() {}



     private static void testObservers() {
            System.out.println("\n-- Observers --");
            BoundedStack s = new BoundedStack (3);
            s.enqueue("A");
            s.enqueue("B");
            check("peek -> returns front without removing", s.peek().equals("A"));
            check("peek -> size unchanged", s.size()==2 );
            check("contains -> finds existing", s.contains("B"));
            check("contains -> rejects missing", !s.contains("C"));

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

        


     }

     
     private static void testExposure() {
          System.out.println("\n-- Representation Exposure --");

     }



}