/* ==================================================================
 * TestSupport.java - 1/05/2019 4:26:16 pm
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Utilities for tests.
 * 
 * @author matt
 * @version 1.0
 */
public final class TestSupport {

  /**
   * Get the file path to a classpath resource that has been copied to a temporary file.
   * 
   * @param name
   *        the classpath resource name
   * @param clazz
   *        the class to load the resource from
   * @return the path to the copied temporary file
   * @throws RuntimeException
   *         if there is any problem
   */
  public static String getTempResourcePath(String name, Class<?> clazz) {
    try {
      URL url = clazz.getResource(name);
      Path f = Files.createTempFile("ModifyTests-" + name, ".properties");
      Files.copy(Paths.get(url.toURI()), f, StandardCopyOption.REPLACE_EXISTING);
      return f.toString();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get a Properties instance loaded from a file.
   * 
   * @param path
   *        the file path to load
   * @return the properties
   * @throws RuntimeException
   *         if there is any problem
   */
  public static Properties getProperties(String path) {
    try (InputStream in = new BufferedInputStream(new FileInputStream(path))) {
      Properties p = new Properties();
      p.load(in);
      return p;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
