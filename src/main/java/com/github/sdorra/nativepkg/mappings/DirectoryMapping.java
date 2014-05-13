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
import com.google.common.collect.Lists;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

import java.util.List;

/**
 *
 * @author Sebastian Sdorra
 */
public class DirectoryMapping extends FileMapping
{

  /**
   * Method description
   *
   *
   * @return
   */
  public List<FileMapping> getFiles()
  {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(path));

    List<FileMapping> files = Lists.newArrayList();

    if ((source != null) && source.exists())
    {
      String fpath = path;

      if (!fpath.endsWith("/"))
      {
        fpath = fpath.concat("/");
      }

      for (File file : source.listFiles())
      {
        files.add(new FileMapping(fpath.concat(file.getName()), file, mode,
          dirMode, uname, gname));
      }
    }

    return files;
  }
}
