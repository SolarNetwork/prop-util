/* ==================================================================
 * Modify.java - 1/05/2019 11:46:32 am
 * 
 * Copyright 2019 SolarNetwork.net Dev Team
 * 
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with this program; if not, write to the Free Software 
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 
 * 02111-1307 USA
 * ==================================================================
 */

package net.solarnetwork.proputil.action;

import java.util.List;
import java.util.Map;

import net.solarnetwork.proputil.Action;
import net.solarnetwork.proputil.PropertiesResource;

/**
 * Handler for the {@link Action#Modify} action.
 * 
 * @author matt
 * @version 1.0
 */
public class Modify extends BaseActionHandler {

  /** The option for a property file to modify. */
  public static final String OPTION_FILE = "file";

  /** The option to add property values, as <i>key:value</i> pairs. */
  public static final String OPTION_ADD = "add";

  /** The option to set property values, as <i>key:value</i> pairs. */
  public static final String OPTION_SET = "set";

  /** The option to delete property values, as <i>key:value</i> pairs. */
  public static final String OPTION_DELETE = "delete";

  @Override
  public String helpDoc(Action action, Map<String, List<String>> switches, List<String> arguments) {
    StringBuilder buf = new StringBuilder();
    buf.append("Usage: modify [arguments...]\n");
    buf.append("\nBasic arguments\n");
    buf.append("  --file path        The path to a file to modify. Can be set multiple times.\n");
    buf.append("\nModify arguments\n");
    buf.append(
        "  --add key:val      Set property 'key' to value 'val', only if 'key' does not already\n");
    buf.append("                     exist. Can be set multiple times.\n");
    buf.append("  --delete key       Delete property 'key'.\n");
    buf.append(
        "  --set key:val      Set property 'key' to value 'val'. Can be set multiple times.\n");
    buf.append("\nModify order\n");
    buf.append("  The order of operations is delete, set, then add.\n");
    return buf.toString();
  }

  @Override
  public void handleAction(Action action, Map<String, List<String>> switches,
      List<String> arguments) {
    // load the properties file(s)
    List<PropertiesResource> props = loadPropertyFiles("file", switches);

    if (switches.containsKey(OPTION_DELETE)) {
      switches.get(OPTION_DELETE).stream().forEach(k -> {
        props.stream().forEach(p -> p.getProps().remove(k));
      });
    }
    if (switches.containsKey(OPTION_SET)) {
      switches.get(OPTION_SET).stream().map(PAIR_SPLITTER).filter(kv -> kv != null).forEach(kv -> {
        props.stream().forEach(p -> p.getProps().put(kv.getKey(), kv.getVal()));
      });
    }
    if (switches.containsKey(OPTION_ADD)) {
      switches.get(OPTION_ADD).stream().map(PAIR_SPLITTER).filter(kv -> kv != null).forEach(kv -> {
        props.stream().forEach(p -> p.getProps().putIfAbsent(kv.getKey(), kv.getVal()));
      });
    }
    props.stream().forEach(p -> p.save(false));
  }

}
