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



package com.github.sdorra.nativepkg.mappings;

//~--- non-JDK imports --------------------------------------------------------

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.freecompany.redline.Builder;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Sebastian Sdorra
 */
public class FileMapping extends AbstractFileMapping
{

  /**
   * Method description
   *
   *
   * @param builder
   *
   * @throws IOException
   * @throws NoSuchAlgorithmException
   */
  @Override
  public void attach(Builder builder)
    throws NoSuchAlgorithmException, IOException
  {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
    Preconditions.checkArgument(source.exists());
    builder.addFile(path, source, mode, dirMode, directive, uname, gname,
      addParents);
  }

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public int getDirMode()
  {
    return dirMode;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public int getMode()
  {
    return mode;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getSource()
  {
    return source;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param dirMode
   */
  public void setDirMode(int dirMode)
  {
    this.dirMode = dirMode;
  }

  /**
   * Method description
   *
   *
   * @param mode
   */
  public void setMode(int mode)
  {
    this.mode = mode;
  }

  /**
   * Method description
   *
   *
   * @param source
   */
  public void setSource(File source)
  {
    this.source = source;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private int dirMode = -1;

  /** Field description */
  private int mode = -1;

  /** Field description */
  private File source;
}
