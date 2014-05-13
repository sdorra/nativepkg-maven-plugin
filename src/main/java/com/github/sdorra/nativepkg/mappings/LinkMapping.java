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

/**
 *
 * @author Sebastian Sdorra
 */
public class LinkMapping
{

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
  public String getSource()
  {
    return source;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getTarget()
  {
    return target;
  }

  //~--- set methods ----------------------------------------------------------

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
   * @param source
   */
  public void setSource(String source)
  {
    this.source = source;
  }

  /**
   * Method description
   *
   *
   * @param target
   */
  public void setTarget(String target)
  {
    this.target = target;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private int permissions = -1;

  /** Field description */
  private String source;

  /** Field description */
  private String target;
}
