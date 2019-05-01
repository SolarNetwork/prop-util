/* ==================================================================
 * PropertiesResourceTests.java - 1/05/2019 5:27:23 pm
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

package net.solarnetwork.proputil.test;

import static net.solarnetwork.proputil.test.TestSupport.getProperties;
import static net.solarnetwork.proputil.test.TestSupport.getTempResourcePath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

import net.solarnetwork.proputil.PropertiesResource;
import net.solarnetwork.proputil.action.test.ModifyTests;

/**
 * Test cases for the {@link PropertiesResource} class.
 * 
 * @author matt
 * @version 1.0
 */
public class PropertiesResourceTests {

  @Test
  public void saveChange() {
    String p1Path = getTempResourcePath("p-01.properties", ModifyTests.class);

    PropertiesResource p = new PropertiesResource(p1Path);
    p.getProps().put("a", "b");
    boolean saved = p.save(false);

    assertThat("Changed props saved", saved, equalTo(true));

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Property set", r1, hasEntry("a", "b"));
    assertThat("Properties size", r1.keySet(), hasSize(2));
  }

  @Test
  public void saveNoChange() {
    String p1Path = getTempResourcePath("p-01.properties", ModifyTests.class);

    PropertiesResource p = new PropertiesResource(p1Path);
    p.getProps().put("foo", "bar");
    boolean saved = p.save(false);

    assertThat("Unchanged props saved", saved, equalTo(false));

    Properties r1 = getProperties(p1Path);
    assertThat("Property kept", r1, hasEntry("foo", "bar"));
    assertThat("Properties size", r1.keySet(), hasSize(1));
  }
}
