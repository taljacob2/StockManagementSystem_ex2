package transaction;

import engine.collection.EngineCollection;
import engine.collection.list.SortableLinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@link Transaction}s {@code Collection}. wrapped in a special class. Has a
 * {@code Collection} field of all the {@link Transaction}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "rse-successfully-finished-transactions")
public class Transactions
        extends EngineCollection<SortableLinkedList<Transaction>, Transaction> {

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * SortableLinkedList}.
     */
    public Transactions() {
        setCollection(new SortableLinkedList<>());
    }

    @Override public SortableLinkedList<Transaction> getCollection() {
        return super.getCollection();
    }

    @XmlElement(name = "rse-transaction")
    public void setCollection(SortableLinkedList<Transaction> collection) {
        super.setCollection(collection);
    }

}
