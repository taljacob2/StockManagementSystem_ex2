package application.scene;

// public class SceneOne
//         implements Initializable, PaneReplacer { // TODO kill `Initializable`
//
//     @FXML private Button buttonToScene2;
//     @FXML private Button buttonPrintStocks; // TODO check
//
//     /**
//      * The <i>root</i> of the attached <tt>.fxml</tt> file.
//      */
//     @FXML private AnchorPane anchorRoot;
//
//
//     @Override public void initialize(URL url, ResourceBundle resourceBundle) {}
//
//     @FXML private void loadSecond(ActionEvent event) throws IOException {
//         replaceWith(event, buttonToScene2, parentContainer, anchorRoot,
//                 "/application/scene/Scene2.fxml");
//     }
//
//     @FXML private void printStocks(ActionEvent event) throws IOException {
//         replaceWith(event, buttonPrintStocks, parentContainer, anchorRoot,
//                 "/application/scene/StockTablePane.fxml");
//     }
//
// }