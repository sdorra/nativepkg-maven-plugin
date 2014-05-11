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

import org.freecompany.redline.payload.Directive;

/**
 *
 * @author Sebastian Sdorra
 */
public abstract class AbstractFileMapping implements Mapping
{

  /**
   * Method description
   *
   *
   * @return
   */
  public Directive getDirective()
  {
    return directive;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getGname()
  {
    return gname;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getPath()
  {
    return path;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public int getPermissions()
  {
    return permissions;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getUname()
  {
    return uname;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public boolean isAddParents()
  {
    return addParents;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param addParents
   */
  public void setAddParents(boolean addParents)
  {
    this.addParents = addParents;
  }

  /**
   * Method description
   *
   *
   * @param directive
   */
  public void setDirective(Directive directive)
  {
    this.directive = directive;
  }

  /**
   * Method description
   *
   *
   * @param gname
   */
  public void setGname(String gname)
  {
    this.gname = gname;
  }

  /**
   * Method description
   *
   *
   * @param path
   */
  public void setPath(String path)
  {
    this.path = path;
  }

  /**
   * Method description
   *
   *
   * @param permissions
   */
  public void setPermissions(int permissions)
  {
    this.permissions = permissions;
  }

  /**
   * Method description
   *
   *
   * @param uname
   */
  public void setUname(String uname)
  {
    this.uname = uname;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  protected boolean addParents;

  /** Field description */
  protected Directive directive;

  /** Field description */
  protected String gname;

  /** Field description */
  protected String path;

  /** Field description */
  protected int permissions = -1;

  /** Field description */
  protected String uname;
}
