package application.pane.resources.printall.stock.container;

import application.pane.resources.printall.stock.PrintStock;

/**
 * Contains all fields needed in order to transfer the <i>current</i> iterated
 * {@link stock.Stock} from {@link engine.Engine} to a specific iterated {@link
 * PrintStock} {@code Controller}.
 *
 * @see PrintStock
 */
public class PrintStockContainer {

    /**
     * Stores here the <i>current</i> iterated {@link stock.Stock} in the {@link
     * engine.Engine}.
     * <blockquote><b>Warning:</b> the <i>index</i> <b>must</b> be initialized
     * to '0' before/after <b>every</b> call to {@code this} class when
     * <i>initiating</i> a {@link application.pane.resources.printall.PrintAll}
     * {@code Controller}.
     * </blockquote>
     *
     * @see application.pane.resources.printall.PrintAll
     */
    private static int index = 0;

    public PrintStockContainer() {
        index = 0;
    }

    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        PrintStockContainer.index = index;
    }
}
