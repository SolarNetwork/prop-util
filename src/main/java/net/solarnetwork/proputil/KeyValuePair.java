/* ==================================================================
 * KeyValuePair.java - 1/05/2019 2:27:50 pm
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

package net.solarnetwork.proputil;

/**
 * An immutable key/value pair.
 * 
 * @author matt
 * @version 1.0
 */
public class KeyValuePair {

  private final String key;
  private final String val;

  /**
   * Constructor.
   * 
   * @param key
   *        the key
   * @param val
   *        the value
   */
  public KeyValuePair(String key, String val) {
    super();
    this.key = key;
    this.val = val;
  }

  public String getKey() {
    return key;
  }

  public String getVal() {
    return val;
  }

}
