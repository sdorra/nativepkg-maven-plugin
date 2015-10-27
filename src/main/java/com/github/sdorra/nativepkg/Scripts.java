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



package com.github.sdorra.nativepkg;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

/**
 *
 * @author Sebastian Sdorra
 */
public class Scripts
{

  /**
   * Constructs ...
   *
   */
  public Scripts() {}

  /**
   * Constructs ...
   *
   *
   * @param preInstall
   * @param postInstall
   * @param preUninstall
   * @param postUninstall
   */
  public Scripts(File preInstall, File postInstall, File preUninstall,
    File postUninstall)
  {
    this.preInstall = preInstall;
    this.postInstall = postInstall;
    this.preUninstall = preUninstall;
    this.postUninstall = postUninstall;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param scripts
   * @return
   */
  public Scripts merge(Scripts scripts)
  {
    //J-
    return new Scripts(
      firstNonNull(preInstall, scripts.getPreInstall()),
      firstNonNull(postInstall, scripts.getPostInstall()),
      firstNonNull(preUninstall, scripts.getPreUninstall()),
      firstNonNull(postUninstall, scripts.getPostUninstall())
    );
    //J+
  }

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public File getPostInstall()
  {
    return postInstall;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getPostUninstall()
  {
    return postUninstall;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getPreInstall()
  {
    return preInstall;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getPreUninstall()
  {
    return preUninstall;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param postInstall
   */
  public void setPostInstall(File postInstall)
  {
    this.postInstall = postInstall;
  }

  /**
   * Method description
   *
   *
   * @param postUninstall
   */
  public void setPostUninstall(File postUninstall)
  {
    this.postUninstall = postUninstall;
  }

  /**
   * Method description
   *
   *
   * @param preInstall
   */
  public void setPreInstall(File preInstall)
  {
    this.preInstall = preInstall;
  }

  /**
   * Method description
   *
   *
   * @param preUninstall
   */
  public void setPreUninstall(File preUninstall)
  {
    this.preUninstall = preUninstall;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param first
   * @param second
   * @param <T>
   *
   * @return
   */
  private <T> T firstNonNull(T first, T second)
  {
    T result = first;

    if (result == null)
    {
      result = second;
    }

    return result;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private File postInstall;

  /** Field description */
  private File postUninstall;

  /** Field description */
  private File preInstall;

  /** Field description */
  private File preUninstall;
}
