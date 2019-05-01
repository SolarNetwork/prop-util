
package net.solarnetwork.proputil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.solarnetwork.proputil.action.Modify;

/**
 * Prop Util main entry point.
 * 
 * @author matt
 */
public class App {

  /**
   * Get help info on the command line options.
   * 
   * @return help doc
   */
  public static String cliHelp() {
    StringBuilder buf = new StringBuilder();
    buf.append("Usage: action [arguments...]\n\n");
    buf.append("Actions\n");
    buf.append("  modify            Update properties.\n");
    buf.append("\nGeneral arguments\n");
    buf.append("  --help            Print this help.\n");
    return buf.toString();
  }

  /**
   * Prop Util command line entry point.
   * 
   * @param args
   *        the command line arguments
   */
  public static void main(String[] args) {
    String action = null;
    Map<String, List<String>> switches = new LinkedHashMap<>();
    List<String> arguments = new ArrayList<>(8);
    boolean onlyArgs = false;
    String currSwitch = null;
    for (String s : args) {
      if (!onlyArgs && s.startsWith("--")) {
        if (s.equals("--")) {
          onlyArgs = true;
          currSwitch = null;
        } else {
          currSwitch = s.substring(2).toLowerCase();
          switches.computeIfAbsent(currSwitch, k -> new ArrayList<>(2));
        }
      } else {
        if (action == null) {
          action = s;
        } else if (currSwitch != null) {
          switches.get(currSwitch).add(s);
        } else {
          arguments.add(s);
        }
      }
    }
    //    System.out.println("Hello, action: " + action);
    //    System.out.println("Hello, switches: " + switches);
    //    System.out.println("Hello, arguments: " + arguments);
    Action act = null;
    ActionHandler handler = null;
    try {
      if (action == null) {
        System.err.println("Action must be provided.");
        System.err.println(cliHelp());
      } else {
        act = Action.forArgument(action);
        switch (act) {
          case Modify:
            handler = new Modify();
            break;

          default:
            throw new UnsupportedOperationException("Action '" + act + "' not supported.");
        }
        if (switches.containsKey("help")) {
          System.err.println(handler.helpDoc(act, switches, arguments));
        } else {
          handler.handleAction(act, switches, arguments);
        }
      }
    } catch (RuntimeException e) {
      System.err.println(e.getMessage());
      if (handler != null) {
        System.err.println(handler.helpDoc(act, switches, arguments));
      } else {
        System.err.println(cliHelp());
      }
    }
  }

}
