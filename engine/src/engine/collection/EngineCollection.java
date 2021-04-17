package engine.collection;

import message.Message;
import message.print.MessagePrint;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;

/**
 * This class represents a {@link Collection} of {@code classes} to be loaded
 * from a <tt>.xml</tt> file, or to be saved into a <tt>.xml</tt> file, that
 * implements the {@code JAXB} annotations.
 *
 * @param <_Collection> represents a {@link Collection}.
 * @param <E>           the Type of {@code Element} in the {@link Collection}.
 * @version 1.1
 */
public class EngineCollection<_Collection extends Collection<E>, E>
        implements AddTabsCollection<E> {

    /**
     * {@link Collection} of all {@link E}s.
     */
    private _Collection collection = null;

    public EngineCollection() {}

    public _Collection getCollection() {
        return collection;
    }

    @XmlTransient // MUST TRANSIENT HERE!
    public void setCollection(_Collection collection) {
        this.collection = collection;
    }

    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (E i : collection) {
                stringBuilder.append(i.toString()).append("\n");
            }
        } catch (NullPointerException e) {
            MessagePrint.println(MessagePrint.Stream.ERR,
                    Message.Err.Stocks.printEmpty());
        }
        return stringBuilder.toString();
    }

    @Override public String toString(String addTabs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (E i : this.getCollection()) {
            stringBuilder.append(addTabs).append(i.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
