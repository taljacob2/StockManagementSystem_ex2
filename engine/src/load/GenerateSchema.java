package load;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used for generating a Schema-XML <tt>.xsd</tt> file.
 *
 * <p>
 * Note:<blockquote>Ideally, this class should only be used <i>once per
 * exercise</i>, in order to generate a schema file. but on this project I chose
 * to <i>generate</i> a schema file for each requirement of the user to save a
 * <tt>.xml</tt> file.
 * </blockquote>
 *
 * @version 1.0
 */
public class GenerateSchema {
//FIXME CHECKING UPLOAD
    /**
     * the name of the schema file.
     */
    public static final String fileName = "TAL-RSE-V2.xsd";

    /**
     * the schema {@code File} to generate into.
     */
    public static File file;

    /**
     * a {@code main} method that is used when the <b>programmer</b> wants to
     * generate a schema file: calls {@link #generate(Path)} and generates the
     * file to the <i>root folder</i> of the Project.
     *
     * @param args CLI <i>unused</i> arguments.
     * @throws Exception in case of an error when generating a schema file.
     * @deprecated used only if the <b>programmer</b> wants to force a creation
     * of a schema file.
     */
    @Deprecated public static void main(String[] args) throws Exception {
        generate(Paths.get(fileName));
    }

    /**
     * the method which generates the schema file.
     * <p>the method generates the schema file in the same <i>folder</i> of the
     * {@link Path} of the <tt>.xml</tt> file the user wants to save</p>
     *
     * @param pathOfXML the {@link Path} of the <tt>.xml</tt> file the user
     *                  wants to save.
     * @throws JAXBException in case of a JAXBException Error.
     * @throws IOException   in case of a IOException Error.
     */
    public static void generate(Path pathOfXML)
            throws JAXBException, IOException {
        JAXBContext jc = JAXBContext.newInstance(Descriptor.class);

        jc.generateSchema(new SchemaOutputResolver() {
            @Override public Result createOutput(String namespaceUri,
                                                 String suggestedFileName)
                    throws IOException {

                // get the folderPath:
                Path folderPath = pathOfXML.toAbsolutePath().getParent();
                file = new File(folderPath.toString() + "\\" + fileName);

                // where to print:
                // DEPRECATED:
                // StreamResult result = new StreamResult(System.out);
                StreamResult result = new StreamResult(file);

                // what should be the name of the file:
                // DEPRECATED:
                // result.setSystemId(suggestedFileName);
                result.setSystemId(file.getAbsolutePath());
                return result;
            }
        });
    }

}
