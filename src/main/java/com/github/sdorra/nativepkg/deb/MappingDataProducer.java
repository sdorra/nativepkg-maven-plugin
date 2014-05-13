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

import com.github.sdorra.nativepkg.mappings.DirectoryMapping;
import com.github.sdorra.nativepkg.mappings.FileMapping;
import com.github.sdorra.nativepkg.mappings.LinkMapping;
import com.github.sdorra.nativepkg.mappings.Mappings;

import org.vafer.jdeb.DataConsumer;
import org.vafer.jdeb.DataProducer;
import org.vafer.jdeb.mapping.Mapper;
import org.vafer.jdeb.mapping.PermMapper;
import org.vafer.jdeb.producers.DataProducerFile;
import org.vafer.jdeb.producers.DataProducerLink;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sebastian Sdorra
 */
public class MappingDataProducer implements DataProducer
{

  /**
   * Constructs ...
   *
   *
   * @param mappings
   * @param conffile
   */
  public MappingDataProducer(Mappings mappings, boolean conffile)
  {
    this.mappings = mappings;
    this.conffile = conffile;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param receiver
   *
   * @throws IOException
   */
  @Override
  public void produce(DataConsumer receiver) throws IOException
  {
    for (DirectoryMapping dir : mappings.getDirectories())
    {
      if (isAppendable(dir))
      {
        appendDirectory(receiver, dir);
      }
    }

    for (FileMapping file : mappings.getFiles())
    {
      if (isAppendable(file))
      {
        appendFile(receiver, file);
      }
    }

    for (LinkMapping link : mappings.getLinks())
    {
      if (!conffile)
      {
        appendLink(receiver, link);
      }
    }
  }

  /**
   * Method description
   *
   *
   * @param receiver
   * @param dir
   *
   * @throws IOException
   */
  private void appendDirectory(DataConsumer receiver, DirectoryMapping dir)
    throws IOException
  {
    File source = dir.getSource();

    if ((source != null) && source.exists())
    {
      String fpath = nonRoot(dir.getPath());

      if (!fpath.endsWith("/"))
      {
        fpath = fpath.concat("/");
      }

      Mapper[] mappers = createMappers(dir);

      for (File file : source.listFiles())
      {
        new DataProducerFile(file, fpath.concat(file.getName()), null, null,
          mappers).produce(receiver);
      }
    }
  }

  /**
   * Method description
   *
   *
   * @param receiver
   * @param file
   *
   * @throws IOException
   */
  private void appendFile(DataConsumer receiver, FileMapping file)
    throws IOException
  {
    new DataProducerFile(file.getSource(), nonRoot(file.getPath()), null, null,
      createMappers(file)).produce(receiver);
  }

  /**
   * Method description
   *
   *
   * @param receiver
   * @param link
   *
   * @throws IOException
   */
  private void appendLink(DataConsumer receiver, LinkMapping link)
    throws IOException
  {
    new DataProducerLink(link.getSource(), link.getTarget(), true, null, null,
      null).produce(receiver);
  }

  /**
   * Method description
   *
   *
   * @param file
   *
   * @return
   */
  private Mapper createFileMapper(FileMapping file)
  {
    return new PermMapper(-1, -1, file.getUname(), file.getGname(),
      file.getPermissions(), file.getDirMode(), -1, null);
  }

  /**
   * Method description
   *
   *
   * @param file
   *
   * @return
   */
  private Mapper[] createMappers(FileMapping file)
  {
    return new Mapper[] { createFileMapper(file) };
  }

  /**
   * Method description
   *
   *
   * @param path
   *
   * @return
   */
  private String nonRoot(String path)
  {
    String nr = path;

    if (nr.startsWith("/"))
    {
      nr = path.substring(1);
    }

    return nr;
  }

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param file
   *
   * @return
   */
  private boolean isAppendable(FileMapping file)
  {
    return (conffile && file.isConfig()) ||!conffile;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  private final boolean conffile;

  /** Field description */
  private final Mappings mappings;
}
