package stock;


import engine.collection.EngineCollection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * All stocks of the program, wrapped in a special class. Has a {@code
 * Collection} field of all the {@link Stock}s together.
 * <p>
 * annotated with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 * </p>
 *
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD) @XmlRootElement(name = "rse-stocks")
public class Stocks extends EngineCollection<List<Stock>, Stock> {

    /**
     * The <i>default</i> {@code Constructor}.
     */
    public Stocks() {}

    @Override public List<Stock> getCollection() {
        return super.getCollection();
    }

    // there must be at least 1 stock in the system, thus 'required = true':
    @XmlElement(name = "rse-stock", required = true)
    public void setCollection(List<Stock> collection) {
        super.setCollection(collection);
    }

}
