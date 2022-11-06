import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Aidan Dilsavor and Nathan Johnson
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    @Test
    public final void testConstructor() {

        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);

        assertEquals(mExpected, m);
    }

    //Add
    @Test
    public final void testAddEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "hello", "green");
        m.add("green");

        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMultipleNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "hello");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "hello", "green", "yellow");
        m.add("green");
        m.add("yellow");

        assertEquals(mExpected, m);
    }

    //ChangeToExtractionMode
    @Test
    public final void testchangeToExtractionModeEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    @Test
    public final void testchangeToExtractionModeNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "hello", "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "hello", "green", "blue");
        m.changeToExtractionMode();

        assertEquals(mExpected, m);
    }

    //RemoveFirst
    @Test
    public final void testRemoveFirstOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String removed = m.removeFirst();
        String removedExpected = "hi";

        assertEquals(mExpected, m);
        assertEquals(removedExpected, removed);
    }

    @Test
    public final void testRemoveFirstMoreThanOne() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "yo", "xray", "green", "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "yo", "xray", "green");
        String removed = m.removeFirst();
        String removedExpected = "blue";

        assertEquals(mExpected, m);
        assertEquals(removedExpected, removed);
    }

    //IsInInsertionMode
    @Test
    public final void testIsInInsertionModeTrueEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        boolean insertion = m.isInInsertionMode();
        boolean insertionExpected = true;

        assertEquals(mExpected, m);
        assertEquals(insertionExpected, insertion);
    }

    @Test
    public final void testIsInInsertionModeFalseEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        boolean insertion = m.isInInsertionMode();
        boolean insertionExpected = false;

        assertEquals(mExpected, m);
        assertEquals(insertionExpected, insertion);
    }

    @Test
    public final void testIsInInsertionModeTrueNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "yo", "xray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "yo", "xray");
        boolean insertion = m.isInInsertionMode();
        boolean insertionExpected = true;

        assertEquals(mExpected, m);
        assertEquals(insertionExpected, insertion);
    }

    @Test
    public final void testIsInInsertionModeFalseNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "yo", "xray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "yo", "xray");
        boolean insertion = m.isInInsertionMode();
        boolean insertionExpected = false;

        assertEquals(mExpected, m);
        assertEquals(insertionExpected, insertion);
    }

    //Order
    @Test
    public final void testOrderEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        Comparator<String> order = m.order();
        Comparator<String> orderExpected = ORDER;

        assertEquals(mExpected, m);
        assertEquals(orderExpected, order);
    }

    @Test
    public final void testOrderNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "yo", "xray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "yo", "xray");
        Comparator<String> order = m.order();
        Comparator<String> orderExpected = ORDER;

        assertEquals(mExpected, m);
        assertEquals(orderExpected, order);
    }

    //Size
    @Test
    public final void testSizeInsertionEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        int size = m.size();
        int sizeExpected = 0;

        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeInsertionNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "hi",
                "yo", "xray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "hi", "yo", "xray");
        int size = m.size();
        int sizeExpected = 3;

        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeExtractionEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        int size = m.size();
        int sizeExpected = 0;

        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeExtractionNonEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "hi",
                "yo", "xray");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "hi", "yo", "xray");
        int size = m.size();
        int sizeExpected = 3;

        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

}
