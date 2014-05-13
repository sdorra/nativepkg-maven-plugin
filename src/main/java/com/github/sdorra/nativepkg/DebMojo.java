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

//~--- non-JDK imports --------------------------------------------------------

import com.github.sdorra.nativepkg.deb.ControlFileWriter;
import com.github.sdorra.nativepkg.deb.MappingDataProducer;
import com.github.sdorra.nativepkg.deb.Slf4jConsole;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.vafer.jdeb.DataProducer;
import org.vafer.jdeb.DebMaker;
import org.vafer.jdeb.PackagingException;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Collections;
import java.util.List;
import org.freecompany.redline.payload.Directive;

/**
 *
 * @author Sebastian Sdorra
 */
@Mojo(name = "deb", defaultPhase = LifecyclePhase.PACKAGE)
public class DebMojo extends NativePkgMojo
{

  /** Field description */
  private static final String ARCHITECTURE_ALL = "all";

  /** Field description */
  private static final String ARCHITECTURE_NOARCH = "noarch";

  /** Field description */
  private static final String CONTROL_ARCHITECTURE = "Architecture";

  /** Field description */
  private static final String CONTROL_CONFLICTS = "Conflicts";

  /** Field description */
  private static final String CONTROL_DESCRIPTION = "Description";

  /** Field description */
  private static final String CONTROL_HOMEPAGE = "Homepage";

  /** Field description */
  private static final String CONTROL_MAINTAINER = "Maintainer";

  /** Field description */
  private static final String CONTROL_PACKAGE = "Package";

  /** Field description */
  private static final String CONTROL_PRIORITY = "Priority";

  /** Field description */
  private static final String CONTROL_REPLACES = "Replaces";

  /** Field description */
  private static final String CONTROL_SECTION = "Section";

  /** Field description */
  private static final String CONTROL_VERSION = "Version";

  /** Field description */
  private static final String TYPE = "deb";

  /**
   * the logger for DebMojo
   */
  private static final Logger logger = LoggerFactory.getLogger(DebMojo.class);

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public String getConflicts()
  {
    return conflicts;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getPriority()
  {
    return priority;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getReplaces()
  {
    return replaces;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getSection()
  {
    return section;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getTemporaryDirectory()
  {
    return temporaryDirectory;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getTmpDir()
  {
    return temporaryDirectory;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param conflicts
   */
  public void setConflicts(String conflicts)
  {
    this.conflicts = conflicts;
  }

  /**
   * Method description
   *
   *
   * @param priority
   */
  public void setPriority(String priority)
  {
    this.priority = priority;
  }

  /**
   * Method description
   *
   *
   * @param replaces
   */
  public void setReplaces(String replaces)
  {
    this.replaces = replaces;
  }

  /**
   * Method description
   *
   *
   * @param section
   */
  public void setSection(String section)
  {
    this.section = section;
  }

  /**
   * Method description
   *
   *
   * @param temporaryDirectory
   */
  public void setTemporaryDirectory(File temporaryDirectory)
  {
    this.temporaryDirectory = temporaryDirectory;
  }

  /**
   * Method description
   *
   *
   * @param tmpDir
   */
  public void setTmpDir(File tmpDir)
  {
    this.temporaryDirectory = tmpDir;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @throws MojoExecutionException
   * @throws MojoFailureException
   */
  @Override
  protected void doExecute() throws MojoExecutionException, MojoFailureException
  {
    mkdirs(temporaryDirectory);

    File controldir = new File(temporaryDirectory, "control");

    mkdirs(controldir);
    createControldir(controldir);

    List<DataProducer> dataProducers =
      Lists.<DataProducer>newArrayList(new MappingDataProducer(mappings,
        false));

    List<DataProducer> confProducers =
      Lists.<DataProducer>newArrayList(new MappingDataProducer(mappings, true));

    DebMaker maker = new DebMaker(new Slf4jConsole(logger), dataProducers,
                       confProducers);

    maker.setControl(controldir);
    maker.setSignPackage(false);

    File debFile = new File(targetDirectory, debFileName);

    maker.setDeb(debFile);

    try
    {
      maker.makeDeb();

      if (attach)
      {
        attachArtifact(TYPE, debFile);
      }
    }
    catch (PackagingException ex)
    {
      throw new MojoExecutionException("could not create deb file", ex);
    }
  }

  /**
   * Method description
   *
   *
   * @param controldir
   *
   * @throws MojoExecutionException
   */
  private void createControldir(File controldir) throws MojoExecutionException
  {
    ControlFileWriter writer = null;

    try
    {
      writer = new ControlFileWriter(new File(controldir, "control"));
      writeControlFile(writer);
    }
    catch (FileNotFoundException ex)
    {
      throw new MojoExecutionException("could not find control directory", ex);
    }
    finally
    {
      close(writer);
    }
  }

  /**
   * Method description
   *
   *
   * @param directory
   *
   * @throws MojoExecutionException
   */
  private void mkdirs(File directory) throws MojoExecutionException
  {
    if (!directory.mkdirs())
    {
      throw new MojoExecutionException("could not create tmp dir");
    }
  }

  /**
   * Method description
   *
   *
   * @param writer
   */
  private void writeControlFile(ControlFileWriter writer)
  {
    String desc = description;

    if (Strings.isNullOrEmpty(desc))
    {
      desc = summary;
    }

    writer.add(CONTROL_PACKAGE, name);
    writer.add(CONTROL_VERSION, version);

    if (platform != null)
    {
      if (platform.getArchitecture().equalsIgnoreCase(ARCHITECTURE_NOARCH))
      {
        writer.add(CONTROL_ARCHITECTURE, ARCHITECTURE_ALL);
      }
      else
      {
        writer.add(CONTROL_ARCHITECTURE, platform.getArchitecture());
      }
    }

    writer.add(CONTROL_MAINTAINER, packager);
    writer.add(CONTROL_HOMEPAGE, url);
    writer.add(CONTROL_DESCRIPTION, desc);
    writer.add(CONTROL_SECTION, section);
    writer.add(CONTROL_CONFLICTS, conflicts);
    writer.add(CONTROL_REPLACES, replaces);
    writer.add(CONTROL_PRIORITY, priority);
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  @Parameter
  private String conflicts;

  /** Field description */
  @Parameter(defaultValue = "${project.artifactId}-${project.version}.deb")
  private String debFileName;

  /** Field description */
  @Parameter
  private String priority = "extra";

  /** Field description */
  @Parameter
  private String replaces;

  /** Field description */
  @Parameter
  private String section;

  /** Field description */
  @Parameter(defaultValue = "${project.build.directory}/deb")
  private File temporaryDirectory;
}
