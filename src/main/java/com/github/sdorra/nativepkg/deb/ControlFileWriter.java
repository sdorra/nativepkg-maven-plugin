/**
 * The MIT License
 *
 * Copyright (c) 2014, Sebastian Sdorra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */



package com.github.sdorra.nativepkg.deb;

//~--- non-JDK imports --------------------------------------------------------

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

//~--- JDK imports ------------------------------------------------------------

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

/**
 *
 * @author Sebastian Sdorra
 */
public final class ControlFileWriter implements Closeable
{

  /** Field description */
  private static final String SEPARATOR = ": ";

  //~--- constructors ---------------------------------------------------------

  /**
   * Constructs ...
   *
   *
   * @param file
   *
   * @throws FileNotFoundException
   */
  public ControlFileWriter(File file) throws FileNotFoundException
  {
    this.writer = new PrintWriter(file);
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param key
   * @param value
   */
  public void add(String key, String value)
  {
    Preconditions.checkNotNull(key, "key can not be null");
    Preconditions.checkArgument(checkKey(key), "key contains invalid chars");

    if (value != null)
    {
      writer.append(key).append(SEPARATOR).println(prepareValue(value));
    }
  }

  /**
   * Method description
   *
   *
   * @param key
   * @param value
   */
  public void add(String key, Object value)
  {
    if (value != null)
    {
      add(key, value.toString());
    }
  }

  /**
   * Method description
   *
   *
   * @param key
   * @param value
   */
  public void add(String key, Enum<?> value)
  {
    if (value != null)
    {
      add(key, value.toString().toLowerCase(Locale.ENGLISH));
    }
  }

  /**
   * Method description
   *
   *
   * @throws IOException
   */
  @Override
  public void close() throws IOException
  {
    writer.close();
  }

  /**
   * Method description
   *
   *
   * @param key
   *
   * @return
   */
  private boolean checkKey(String key)
  {
    return keyMatcher.matchesNoneOf(key);
  }

  /**
   * Method description
   *
   *
   * @param value
   *
   * @return
   */
  private String prepareValue(String value)
  {
    return value.replace("\n", "\n ");
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private final CharMatcher keyMatcher = CharMatcher.BREAKING_WHITESPACE;

  /** Field description */
  private final PrintWriter writer;
}
