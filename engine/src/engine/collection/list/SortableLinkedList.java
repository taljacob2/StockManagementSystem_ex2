package engine.collection.list;

import engine.collection.AddTabsCollection;

import java.util.Collection;
import java.util.LinkedList;

/**
 * <p>This class {@code extends} a known {@link java.util.List} <i>{@code
 * implementor}</i>, with the addition of automatic sort of its {@code
 * Elements}.
 * </p>
 *
 * <blockquote><b>
 * Note: this class is currently <i>{@code extending}</i> the {@link LinkedList}
 * {@code class}, but is replaceable by any {@link java.util.List} <i>{@code
 * implementor}</i>.
 * </b></blockquote>
 *
 * <blockquote><p>Extra addition: Also, adding the option to {@link
 * #toString(String)} thanks to the {@link AddTabsCollection} <i>{@code
 * interface}</i>.</p></blockquote>
 *
 * @param <E> the Type of {@code Element} in the {@link java.util.List}.
 * @version 1.2
 */
public class SortableLinkedList<E extends Comparable<? super E>>
        extends LinkedList<E> implements SortableList<E>, AddTabsCollection<E> {

    /**
     * Default Constructor.
     */
    public SortableLinkedList() {}

    public SortableLinkedList(Collection<? extends E> c) {
        super(c);
    }

    @Override public String toString(String addTabs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (E i : this) {
            stringBuilder.append(addTabs).append(i.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
