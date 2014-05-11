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

import org.freecompany.redline.Builder;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import java.util.List;

/**
 *
 * @author Sebastian Sdorra
 */
public class Mappings implements Mapping
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
    attach(builder, directories);
    attach(builder, files);
    attach(builder, links);
  }

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public List<DirectoryMapping> getDirectories()
  {
    return directories;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public List<FileMapping> getFiles()
  {
    return files;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public List<LinkMapping> getLinks()
  {
    return links;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param directories
   */
  public void setDirectories(List<DirectoryMapping> directories)
  {
    this.directories = directories;
  }

  /**
   * Method description
   *
   *
   * @param files
   */
  public void setFiles(List<FileMapping> files)
  {
    this.files = files;
  }

  /**
   * Method description
   *
   *
   * @param links
   */
  public void setLinks(List<LinkMapping> links)
  {
    this.links = links;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param builder
   * @param mappings
   *
   * @throws IOException
   * @throws NoSuchAlgorithmException
   */
  private void attach(Builder builder, List<? extends Mapping> mappings)
    throws NoSuchAlgorithmException, IOException
  {
    if (mappings != null)
    {
      for (Mapping m : mappings)
      {
        m.attach(builder);
      }
    }
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private List<DirectoryMapping> directories;

  /** Field description */
  private List<FileMapping> files;

  /** Field description */
  private List<LinkMapping> links;
}
