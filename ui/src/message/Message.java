package message;

import message.builder.BuildMessage;
import message.builder.err.*;
import message.builder.out.*;

/**
 * This class organizes all the messages of the program.
 * <blockquote>Uses the Message Building Process in order to produce the
 * messages.</blockquote>
 *
 * @version 1.1
 * @see BuildMessage
 */
public class Message {

    /**
     * Organizes all the {@code Error} messages.
     *
     * <p><b>Suggestion: print all of these
     * messages in the {@link System#err} {@code PrintStream}.</b></p>
     *
     * <p>Reached within {@code Message.Err}</p>
     *
     * @see Err
     */
    public static class Err {

        /**
         * Organizes all {@link BuildError_XML} messages.
         * <p>Reached within {@code Message.Err.XML}</p>
         */
        public static class XML {

            public static String suffix() {
                return new BuildError_XML().suffix();
            }

            /**
             * Organizes all {@link BuildError_Save} messages.
             * <p>Reached within {@code Message.Err.XML.Save}</p>
             */
            public static class Save {

                public static String noStocksToSave() {
                    return new BuildError_Save().noStocksToSave();
                }

                public static String writeFail() {
                    return new BuildError_Save().writeFail();
                }

            }

            /**
             * Organizes all {@link BuildError_Load} messages.
             * <p>Reached within {@code Message.Err.XML.Load}</p>
             */
            public static class Load {

                public static String nullPointerException() {
                    return new BuildError_Load().stocks();
                }

                public static String readFail() {
                    return new BuildError_Load().fail();
                }

                public static String fileDoesNotExist() {
                    return new BuildError_Load().fileDoesNotExist();
                }

                public static String stocksInvalid_SymbolsAmbiguity() {
                    return new BuildError_Load()
                            .stocksInvalid_SymbolsAmbiguity();
                }

                public static String stocksInvalid_CompanyNameAmbiguity() {
                    return new BuildError_Load()
                            .stocksInvalid_CompanyNameAmbiguity();
                }

            }

        }

        /**
         * Organizes all {@link BuildError_Stocks} messages.
         * <p>Reached within {@code Message.Err.Stocks}</p>
         */
        public static class Stocks {

            public static String printEmpty() {
                return new BuildError_Stocks().printEmpty();
            }

            public static String unFoundSymbol(String symbol) {
                return new BuildError_Stocks().unFoundSymbol(symbol);
            }

        }

        /**
         * Organizes all {@link BuildError_Input} messages.
         * <p>Reached within {@code Message.Err.Input}</p>
         */
        public static class Input {

            public static String mismatch(String requiredType,
                                          String providedType) {
                return new BuildError_Input()
                        .mismatch(requiredType, providedType);
            }
        }

        /**
         * Organizes all {@link BuildError_Order} messages.
         * <p>Reached within {@code Message.Err.Order}</p>
         */
        public static class Order {

            public static String buildFail() {
                return new BuildError_Order().buildFail();
            }

            public static String removeFail() {
                return new BuildError_Order().removeFail();
            }

        }

        /**
         * Organizes all {@link BuildError_Transaction} messages.
         * <p>Reached within {@code Message.Err.Transaction}</p>
         */
        public static class Transaction {

            public static String buildFail() {
                return new BuildError_Transaction().buildFail();
            }

        }

    }

    /**
     * Organizes all the {@code Output} messages.
     *
     * <p><b>Suggestion: print all of these
     * messages in the {@link System#out} {@code PrintStream}.</b></p>
     *
     * <p>Reached within {@code Message.Out}</p>
     *
     * @see Out
     */
    public static class Out {

        /**
         * Organizes all {@link BuildOutput_XML} messages.
         * <p>Reached within {@code Message.Out.XML}</p>
         */
        public static class XML {

            /**
             * Organizes all {@link BuildOutput_Save} messages.
             * <p>Reached within {@code Message.Out.XML.Save}</p>
             */
            public static class Save {

                public static String success(String filePath) {
                    return new BuildOutput_Save().success(filePath);
                }

            }

            /**
             * Organizes all {@link BuildOutput_Load} messages.
             * <p>Reached within {@code Message.Out.XML.Load}</p>
             */
            public static class Load {

                public static String success(String filePath) {
                    return new BuildOutput_Load().success(filePath);
                }

            }
        }

        /**
         * Organizes all {@link BuildOutput_Input} messages.
         * <p>Reached within {@code Message.Out.Input}</p>
         */
        public static class Input {

            public static String please(String typeOfVariable,
                                        String nameOfVariable) {
                return new BuildOutput_Input()
                        .please(typeOfVariable, nameOfVariable);
            }

            public static String please(String typeOfVariable,
                                        String nameOfVariable,
                                        String appendMessage) {
                return new BuildOutput_Input()
                        .please(typeOfVariable, nameOfVariable, appendMessage);
            }

            public static String success(String firstMessage) {
                return new BuildOutput_Input().success(firstMessage);
            }
        }

        /**
         * Organizes all {@link BuildOutput_StockDataBase} messages.
         * <p>Reached within {@code Message.Out.StockDataBase}</p>
         */
        public static class StockDataBase {

            public static String printEmpty(
                    BuildOutput_StockDataBase.TypeOfCollection typeOfCollection) {
                return new BuildOutput_StockDataBase()
                        .printEmpty(typeOfCollection);
            }

            /**
             * @param object object that its addition succeeded.
             * @return success message.
             * @see BuildOutput_StockDataBase#newSuccessAdd(Object)
             */
            public static String newSuccessAdd(Object object) {
                return new BuildOutput_StockDataBase().newSuccessAdd(object);
            }

            public static String printOrderPerformedInItsEntirety() {
                return new BuildOutput_StockDataBase()
                        .printOrderPerformedInItsEntirety();
            }
        }

    }

}
