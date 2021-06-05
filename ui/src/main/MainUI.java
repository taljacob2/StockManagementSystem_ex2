package main;

import application.javafxapp.JavaFXApp;
import javafx.application.Application;

/**
 * Main class of the UI.
 *
 * <p>
 * <b>Note for module dependencies:</b>
 * </p>
 *
 * <ul>
 *
 * <li>there is a mutual dependency between the <tt>ui</tt> module and
 * the <tt>engine</tt> module.</li>
 *
 * <li>the <tt>ui</tt> module is dependent on
 * the <tt>javafx</tt> module.</li>
 *
 * <li>the <tt>javafx</tt> module is dependent on
 * the <tt>ui</tt> module and the <tt>engine</tt> module.</li>
 *
 * </ul>
 *
 * @author Tal Yacob, ID: 208632778.
 * @version Rolling Exercise 2.0.
 */
public class MainUI {

    /**
     * main User-Interface method.
     *
     * @param args CLI arguments of the program.
     */
    public static void main(String[] args) {

        Application.launch(JavaFXApp.class, args);

    }

}
