/* ==================================================================
 * BaseActionHandler.java - 1/05/2019 3:35:49 pm
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

import static java.util.stream.Collectors.toList;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import net.solarnetwork.proputil.ActionHandler;
import net.solarnetwork.proputil.KeyValuePair;
import net.solarnetwork.proputil.PropertiesResource;

/**
 * Base class for action handlers.
 * 
 * @author matt
 * @version 1.0
 */
public abstract class BaseActionHandler implements ActionHandler {

  /** Function that splits a string on the first equal sign. */
  public static final Function<String, KeyValuePair> PAIR_SPLITTER = pair -> {
    int idx = pair.indexOf('=');
    if (idx < 0) {
      return null;
    }
    String key = pair.substring(0, idx);
    String val = pair.substring(idx + 1);
    return new KeyValuePair(key, val);
  };

  /**
   * Create a deep-copy of a list of {@ink Properties}.
   * 
   * @param orig
   *        the properties to copy
   * @return the copy
   */
  public static List<PropertiesResource> clonePropertiesList(List<PropertiesResource> orig) {
    return orig.stream().map(p -> new PropertiesResource(p)).collect(toList());
  }

  /**
   * Load a set of {@link Properties} instances from a set of file paths.
   * 
   * @param paths
   *        the paths to load
   * @return the list of PropertiesResource
   * @throws IllegalArgumentException
   *         if any file fails to load as {@code Properties}
   */
  public static List<PropertiesResource> loadPropertyFiles(List<String> paths) {
    List<PropertiesResource> orig = paths.stream().map(p -> {
      Properties m = new Properties();
      try (InputStream in = new BufferedInputStream(new FileInputStream(new File(p)))) {
        m.load(in);
      } catch (IOException e) {
        throw new IllegalArgumentException(
            "Error reading property file [" + p + "]: " + e.getMessage());
      }
      return new PropertiesResource(m, p);
    }).collect(toList());
    return orig;
  }

  /**
   * Load a set of {@link Properties} instances from a set of file paths defined in a multi-value
   * map.
   * 
   * @param key
   *        the key in {@code switches} to use as a list of file paths
   * @param switches
   *        the multi-value map with the file paths
   * @return the list of PropertiesResource
   * @throws IllegalArgumentException
   *         if {@code key} is not found in {@code switches} or if any file fails to load as
   *         {@code Properties}
   */
  public static List<PropertiesResource> loadPropertyFiles(String key,
      Map<String, List<String>> switches) {
    List<String> paths = switches.get(key);
    if (paths == null || paths.isEmpty()) {
      throw new IllegalArgumentException("Missing " + key + " option.");
    }
    return loadPropertyFiles(paths);
  }

}
