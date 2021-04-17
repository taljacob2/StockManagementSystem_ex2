package user.holding;

import engine.collection.EngineCollection;
import engine.collection.list.SortableLinkedList;
import user.holding.item.Item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@link Item}s {@code Collection}. wrapped in a special class. Has a {@code
 * Collection} field of all the {@link Item}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-holdings")
public class Holdings
        extends EngineCollection<SortableLinkedList<Item>, Item> {

    /**
     * <b><i>important:</i></b>
     * The <i>default</i> {@code Constructor} is <i>initializing</i> the {@link
     * SortableLinkedList}.
     */
    public Holdings() {
        setCollection(new SortableLinkedList<>());
    }

    @Override public SortableLinkedList<Item> getCollection() {
        return super.getCollection();
    }

    @XmlElement(name = "rse-item")
    public void setCollection(SortableLinkedList<Item> collection) {
        super.setCollection(collection);
    }

}
