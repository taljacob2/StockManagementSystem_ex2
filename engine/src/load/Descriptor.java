package load;

import stock.Stocks;
import user.Users;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is the <i>SuperClass</i> of the schema <tt>.xml</tt> file - annotated
 * with JAXB, to marshal / unmarshal a <tt>.xml</tt> file.
 *
 * @version 2.0
 */
@XmlRootElement(name = "rizpa-stock-exchange-descriptor")
@XmlAccessorType(XmlAccessType.FIELD) public class Descriptor {

    /**
     * The {@code Stocks} of the <tt>.xml</tt> file.
     */
    @XmlElement(name = "rse-stocks") private Stocks stocks;

    /**
     * The {@code Users} of the <tt>.xml</tt> file.
     */
    @XmlElement(name = "rse-users") private Users users;

    public Stocks getStocks() {
        return stocks;
    }

    public Users getUsers() {
        return users;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override public String toString() {
        return "Descriptor{" + "stocks=" + stocks + ", users=" + users + '}';
    }
}
