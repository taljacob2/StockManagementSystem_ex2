package load;

import engine.Engine;
import message.Message;
import message.builder.err.BuildError_XML;
import message.print.MessagePrint;
import stock.Stocks;
import user.Users;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * This class loads (unmarshal) and saves (marshal) a <tt>.xml</tt> file.
 * <ul>
 *     <li>Implemented with '.xml' suffix case-insensitive.</li>
 * </ul>
 *
 * @version 2.0
 * @see JAXBContext
 */
public class LoadSaveXML {

    /* XXX USEFUL GUIDE FOR USING JAXB -
         https://howtodoinjava.com/jaxb/jaxb-exmaple-marshalling-and-unmarshalling-list-or-set-of-objects/ */

    /**
     * try-catching:
     * <ul>
     *     <li>{@link NullPointerException}</li>
     *     <li>{@link JAXBException}</li>
     *     <li>{@link IllegalArgumentException}</li>
     * </ul>
     * marshal (= Save) to <tt>.xml</tt> from {@link Engine}'s {@code stocks}.
     *
     * @param stringPathOfXML the path of the desired XML to save.
     * @throws IOException if file doesn't have the correct suffix (= '.xml')
     *                     (with case-insensitive).
     */
    public static void marshal(String stringPathOfXML) throws IOException {

        // check the file's suffix:
        if ((stringPathOfXML.length() >= 4) &&
                (stringPathOfXML.substring(stringPathOfXML.length() - 4)
                        .equalsIgnoreCase(".xml"))) {

            try {
                JAXBContext jaxbContext =
                        JAXBContext.newInstance(Descriptor.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // set in format
                jaxbMarshaller
                        .setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                // generate the Schema '.xsd' file:
                Path pathOfXML = Paths.get(stringPathOfXML);
                GenerateSchema.generate(pathOfXML);

                // set Schema '.xsd' file:
                jaxbMarshaller.setProperty(
                        Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION,
                        GenerateSchema.file.getName());

                // Marshal in file
                jaxbMarshaller.marshal(Engine.createDescriptor(),
                        new File(stringPathOfXML));

                // print Success message:
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.XML.Save.success(stringPathOfXML));
            } catch (NullPointerException e) {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Save.noStocksToSave());
            } catch (JAXBException e) {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Save.writeFail());
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError_XML().getMessage() +
                                e.getLinkedException().getMessage());
            } catch (IllegalArgumentException e) {

                /*
                 * This Exception is probably for programmers only,
                 * and won't be encountered at all, thanks to our previous catches.
                 */
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Save.writeFail());
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError_XML().getMessage() + e.getMessage());
            }
        } else {
            throw new IOException(Message.Err.XML.suffix());
        }

    }

    /**
     * try-catching:
     *  <ul>
     *      <li>{@link IOException}</li>
     *      <li>{@link NullPointerException}</li>
     *  </ul>
     * unmarshal (= Load) from '.xml' to {@link Engine}'s {@code stocks}.
     *
     * @param pathOfXML the path of the desired XML to load.
     * @throws IOException if file doesn't have the correct suffix (= '.xml')
     *                     (with case-insensitive).
     */
    public static void unmarshal(String pathOfXML) throws IOException {

        // check the file's suffix:
        if ((pathOfXML.length() >= 4) &&
                (pathOfXML.substring(pathOfXML.length() - 4)
                        .equalsIgnoreCase(".xml"))) {

            // unmarshalling:
            try {

                // Stocks and Users to check: unmarshal from the given File:
                Stocks stocks = Objects.requireNonNull(
                        unmarshalDescriptor(new File(pathOfXML))).getStocks();

                Users users = Objects.requireNonNull(
                        unmarshalDescriptor(new File(pathOfXML))).getUsers();

                // check these stocks, and set them in engine if they are valid:
                checkAndSetStocksInEngine(stocks);

                // check these users, and set them in engine if they are valid:
                checkAndSetUsersInEngine(users);

                // print Success message:
                MessagePrint.println(MessagePrint.Stream.OUT,
                        Message.Out.XML.Load.success(pathOfXML));

            } catch (IOException e) {
                MessagePrint.println(MessagePrint.Stream.ERR, e.getMessage());
            } catch (NullPointerException e) {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Load.nullPointerException());
                e.printStackTrace();// TODO : check
            }
        } else {

            // an incorrect suffix found:
            throw new IOException(Message.Err.XML.suffix());
        }

    }

    private static void checkAndSetStocksInEngine(Stocks stocks)
            throws IOException {

        // check the validation of the stocks in the File given:
        checkValidStocks(stocks);

        // stocks found as valid, so we are allowed to set the Stocks:
        Engine.setStocks(stocks); // set stocks of Engine.
    }

    private static void checkAndSetUsersInEngine(Users users)
            throws IOException { //TODO: need to implement!!!

        // check the validation of the users in the File given:
        checkValidUsers(users);//TODO: need to UN-NOTE this and implement!!!

        // users found as valid, so we are allowed to set the Users:
        Engine.setUsers(users); // set users of Engine.
    }

    /**
     * this method checks whether the {@link Stocks} unmarshalled are valid or
     * not.
     *
     * @param stocks the {@link Stocks} to check for validity.
     * @throws IOException if the stocks are invalid.
     */
    private static void checkValidStocks(Stocks stocks) throws IOException {
        try {

            // check the validation of the stocks in the File given:
            Engine.checkValidStocks(stocks);
        } catch (IOException e) {

            // stocks are invalid:
            throw new IOException(e.getMessage() + ".");
        }
    }

    /**
     * this method checks whether the {@link Users} unmarshalled are valid or
     * not.
     * <p>Note: this must be invoked only <b>after</b> there are valid
     * {@link Stocks} in the {@link Engine}.</p>
     *
     * @param users the {@link Users} to check for validity.
     * @throws IOException if the users are invalid.
     */
    private static void checkValidUsers(Users users) throws IOException {
        try {

            /*
             * if there are users in the File given,
             * check the validation of the users:
             */
            if (users != null) { Engine.checkValidUsers(users); }
        } catch (IOException e) {

            // users are invalid:
            throw new IOException(e.getMessage() + ".");
        }
    }

    /**
     * try-catching:
     * <ul>
     *     <li>{@link JAXBException}</li>
     *     <li>{@link IllegalArgumentException }</li>
     * </ul>
     *
     * @param fileToUnmarshal a given file to unmarshal.
     * @return {@code Descriptor} extracted from the <tt>.xml</tt> file given.
     * @throws IOException if the provided file does not exist.
     */
    private static Descriptor unmarshalDescriptor(File fileToUnmarshal)
            throws IOException {

        // check if file exists:
        if (fileToUnmarshal.exists()) {
            try {
                JAXBContext jaxbContext =
                        JAXBContext.newInstance(Descriptor.class);
                Unmarshaller jaxbUnmarshaller =
                        jaxbContext.createUnmarshaller();
                return (Descriptor) jaxbUnmarshaller.unmarshal(fileToUnmarshal);
            } catch (JAXBException e) {
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Load.readFail());
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError_XML().getMessage() +
                                e.getLinkedException().getMessage());
            } catch (IllegalArgumentException e) {

                /*
                 * This Exception is probably for programmers only,
                 * and won't be encountered at all, thanks to our previous catches.
                 */
                MessagePrint.println(MessagePrint.Stream.ERR,
                        Message.Err.XML.Load.readFail());
                MessagePrint.println(MessagePrint.Stream.ERR,
                        new BuildError_XML().getMessage() + e.getMessage());
            }
        } else {

            // file does not exist:
            throw (new IOException(Message.Err.XML.Load.fileDoesNotExist()));
        }

        // In case of an error, return null:
        return null;
    }

}
