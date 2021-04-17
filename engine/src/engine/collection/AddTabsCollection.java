package engine.collection;

/**
 * Interface that adds <i>Tabs</i> to the {@code toString()} method in a {@link
 * java.util.Collection}.
 *
 * @param <E> Element in the {@link java.util.Collection}.
 * @version 1.0
 */
@FunctionalInterface public interface AddTabsCollection<E> {
    public String toString(String addTabs);
}
