/* ==================================================================
 * ActionHandler.java - 1/05/2019 11:46:49 am
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

import java.util.List;
import java.util.Map;

/**
 * API for handling an action.
 * 
 * @author matt
 * @version 1.0
 */
public interface ActionHandler {

  /**
   * Get a help document.
   * 
   * @return help information about command line arguments and general usage
   */
  String helpDoc(Action action, Map<String, List<String>> switches, List<String> arguments);

  /**
   * Handle an action.
   * 
   * @param action
   *        the action to handle
   * @param switches
   *        the command line switches
   * @param arguments
   *        the command line arguments
   */
  void handleAction(Action action, Map<String, List<String>> switches, List<String> arguments);

}
