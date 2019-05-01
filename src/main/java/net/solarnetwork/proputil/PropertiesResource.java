/* ==================================================================
 * PropertiesResource.java - 1/05/2019 5:00:58 pm
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Pairing of a {@link Properties} instance with an associated file path.
 * 
 * @author matt
 * @version 1.0
 */
public class PropertiesResource {

  /** The UTF-8 character set used for reading/writing files. */
  public static final Charset UTF8 = Charset.forName("UTF-8");

  private final Properties props;
  private final String path;
  private final Charset charset;

  private final Properties orig;

  /**
   * Load a {@link Properties} instance from a file.
   * 
   * @param path
   *        the path to the properties file to load
   * @param charset
   *        the charset to use
   * @return the properties instance
   * @throws RuntimeException
   *         if an error occurs
   */
  public static Properties load(String path, Charset charset) {
    Properties m = new Properties();
    try (Reader in = new InputStreamReader(
        new BufferedInputStream(new FileInputStream(new File(path))), charset)) {
      m.load(in);
      return m;
    } catch (IOException e) {
      throw new RuntimeException("Error reading property file [" + path + "]: " + e.getMessage());
    }
  }

  /**
   * Save properties to a file.
   * 
   * @param props
   *        the properties to save
   * @param path
   *        the file path to save to
   * @param charset
   *        the charset to use
   * @throws RuntimeException
   *         if an error occurs
   */
  public static void save(Properties props, String path, Charset charset) {
    try (Writer out = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(path)),
        charset)) {
      props.store(out, null);
    } catch (IOException e) {
      throw new RuntimeException("Error saving properties to " + path + ": " + e.getMessage(), e);
    }
  }

  /**
   * Constructor.
   * 
   * @param path
   *        the path to load properties from
   */
  public PropertiesResource(String path) {
    this(path, UTF8);
  }

  /**
   * Constructor.
   * 
   * @param path
   *        the path to load properties from
   * @param charset
   *        the charset
   */
  public PropertiesResource(String path, Charset charset) {
    this(load(path, charset), path, charset);
  }

  /**
   * Constructor.
   * 
   * @param props
   *        the properties
   * @param path
   *        the path
   */
  public PropertiesResource(Properties props, String path) {
    this(props, path, UTF8);
  }

  /**
   * Constructor.
   * 
   * @param props
   *        the properties
   * @param path
   *        the path
   * @param charset
   *        the charset
   */
  public PropertiesResource(Properties props, String path, Charset charset) {
    super();
    this.props = props;
    this.path = path;
    this.charset = charset;
    this.orig = (Properties) props.clone();
  }

  /**
   * Copy constructor.
   * 
   * @param other
   *        the properties to copy; the {@link Properties} instance will be copied as well
   */
  public PropertiesResource(PropertiesResource other) {
    this(other.props != null ? new Properties(other.props) : null, other.getPath(), other.charset);
  }

  /**
   * Save the properties to {@link #getPath()}.
   * 
   * @param always
   *        if {@literal false} only save the properties if they have changed since the receiver was
   *        created; if {@literal true} always save
   * @returns {@literal true} if the properties were saved
   * @throws RuntimeException
   *         if an error occurs
   */
  public boolean save(boolean always) {
    if (always || !props.equals(orig)) {
      save(props, path, charset);
      return true;
    }
    return false;
  }

  public Properties getProps() {
    return props;
  }

  public String getPath() {
    return path;
  }

  public Charset getCharset() {
    return charset;
  }

}
