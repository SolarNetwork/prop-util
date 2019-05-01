/* ==================================================================
 * ModifyTests.java - 1/05/2019 4:10:58 pm
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

package net.solarnetwork.proputil.action.test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static net.solarnetwork.proputil.test.TestSupport.getProperties;
import static net.solarnetwork.proputil.test.TestSupport.getTempResourcePath;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import net.solarnetwork.proputil.Action;
import net.solarnetwork.proputil.action.Modify;

/**
 * Test cases for the {@link Modify} class.
 * 
 * @author matt
 * @version 1.0
 */
public class ModifyTests {

  @Test
  public void setOne() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("set", asList("a=b"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Property set", r1, hasEntry("a", "b"));
    assertThat("Properties size", r1.keySet(), hasSize(2));
  }

  @Test
  public void setTwo() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("set", asList("a=b", "c=d"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Property set", r1, hasEntry("a", "b"));
    assertThat("Property set", r1, hasEntry("c", "d"));
    assertThat("Properties size", r1.keySet(), hasSize(3));
  }

  @Test
  public void addOne() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("add", asList("a=b"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Property set", r1, hasEntry("a", "b"));
    assertThat("Properties size", r1.keySet(), hasSize(2));
  }

  @Test
  public void addAlreadyPresent() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("add", asList("foo=b"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Properties size", r1.keySet(), hasSize(1));
  }

  @Test
  public void addTwoOneAlreadyPresent() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("add", asList("foo=b", "c=d"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Property set", r1, hasEntry("c", "d"));
    assertThat("Properties size", r1.keySet(), hasSize(2));
  }

  @Test
  public void deleteOne() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("delete", asList("foo"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Properties size", r1.keySet(), hasSize(0));
  }

  @Test
  public void deleteNonExisting() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Modify handler = new Modify();
    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("delete", asList("a"));
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Properties size", r1.keySet(), hasSize(1));
  }

  @Test
  public void allOfTheAbove() {
    String p1Path = getTempResourcePath("p-01.properties", getClass());

    Map<String, List<String>> switches = new LinkedHashMap<>();
    switches.put("file", asList(p1Path));
    switches.put("delete", asList("foo", "c"));
    switches.put("set", asList("e=f", "g=h"));
    switches.put("add", asList("foo=b", "c=d"));
    Modify handler = new Modify();
    handler.handleAction(Action.Modify, switches, emptyList());

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("c", "d"));
    assertThat("Property kept", r1, hasEntry("e", "f"));
    assertThat("Property kept", r1, hasEntry("foo", "b"));
    assertThat("Property kept", r1, hasEntry("g", "h"));
    assertThat("Properties size", r1.keySet(), hasSize(4));
  }
}
