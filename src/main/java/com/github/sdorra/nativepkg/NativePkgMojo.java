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

import com.github.sdorra.nativepkg.mappings.Dependency;
import com.github.sdorra.nativepkg.mappings.Mappings;

import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//~--- JDK imports ------------------------------------------------------------

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 *
 * @author Sebastian Sdorra
 */
public abstract class NativePkgMojo extends Slf4jMojo
{

  /**
   * the logger for NativePkgMojo
   */
  private static final Logger logger =
    LoggerFactory.getLogger(NativePkgMojo.class);

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public String getBuildHost()
  {
    return buildHost;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getClassifier()
  {
    return classifier;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public List<Dependency> getDependencies()
  {
    return dependencies;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getDestribution()
  {
    return destribution;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getLicense()
  {
    return license;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public Mappings getMappings()
  {
    return mappings;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getName()
  {
    return name;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getPackager()
  {
    return packager;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public Platform getPlatform()
  {
    return platform;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public MavenProject getProject()
  {
    return project;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public MavenProjectHelper getProjectHelper()
  {
    return projectHelper;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getSummary()
  {
    return summary;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public File getTargetDirectory()
  {
    return targetDirectory;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getUrl()
  {
    return url;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getVendor()
  {
    return vendor;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getVersion()
  {
    return version;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public boolean isAttach()
  {
    return attach;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param attach
   */
  public void setAttach(boolean attach)
  {
    this.attach = attach;
  }

  /**
   * Method description
   *
   *
   * @param buildHost
   */
  public void setBuildHost(String buildHost)
  {
    this.buildHost = buildHost;
  }

  /**
   * Method description
   *
   *
   * @param classifier
   */
  public void setClassifier(String classifier)
  {
    this.classifier = classifier;
  }

  /**
   * Method description
   *
   *
   * @param dependencies
   */
  public void setDependencies(List<Dependency> dependencies)
  {
    this.dependencies = dependencies;
  }

  /**
   * Method description
   *
   *
   * @param description
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

  /**
   * Method description
   *
   *
   * @param destribution
   */
  public void setDestribution(String destribution)
  {
    this.destribution = destribution;
  }

  /**
   * Method description
   *
   *
   * @param license
   */
  public void setLicense(String license)
  {
    this.license = license;
  }

  /**
   * Method description
   *
   *
   * @param mappings
   */
  public void setMappings(Mappings mappings)
  {
    this.mappings = mappings;
  }

  /**
   * Method description
   *
   *
   * @param name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Method description
   *
   *
   * @param packager
   */
  public void setPackager(String packager)
  {
    this.packager = packager;
  }

  /**
   * Method description
   *
   *
   * @param platform
   */
  public void setPlatform(Platform platform)
  {
    this.platform = platform;
  }

  /**
   * Method description
   *
   *
   * @param project
   */
  public void setProject(MavenProject project)
  {
    this.project = project;
  }

  /**
   * Method description
   *
   *
   * @param projectHelper
   */
  public void setProjectHelper(MavenProjectHelper projectHelper)
  {
    this.projectHelper = projectHelper;
  }

  /**
   * Method description
   *
   *
   * @param summary
   */
  public void setSummary(String summary)
  {
    this.summary = summary;
  }

  /**
   * Method description
   *
   *
   * @param targetDirectory
   */
  public void setTargetDirectory(File targetDirectory)
  {
    this.targetDirectory = targetDirectory;
  }

  /**
   * Method description
   *
   *
   * @param url
   */
  public void setUrl(String url)
  {
    this.url = url;
  }

  /**
   * Method description
   *
   *
   * @param vendor
   */
  public void setVendor(String vendor)
  {
    this.vendor = vendor;
  }

  /**
   * Method description
   *
   *
   * @param version
   */
  public void setVersion(String version)
  {
    this.version = version;
  }

  //~--- methods --------------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param type
   * @param file
   */
  protected void attachArtifact(String type, File file)
  {
    if (attach && file.exists())
    {
      projectHelper.attachArtifact(project, type, classifier, file);
    }
  }

  /**
   * Method description
   *
   *
   * @param closeable
   */
  protected void close(Closeable closeable)
  {
    if (closeable != null)
    {
      try
      {
        closeable.close();
      }
      catch (IOException ex)
      {
        logger.warn("could not close closeable", ex);
      }
    }
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  @Parameter
  protected boolean attach = false;

  /** Field description */
  @Parameter
  protected String buildHost;

  /** Field description */
  @Parameter
  protected List<Dependency> dependencies;

  /** Field description */
  @Parameter
  protected String description;

  /** Field description */
  @Parameter
  protected String destribution;

  /** Field description */
  @Parameter
  protected String license;

  /** Field description */
  @Parameter
  protected Mappings mappings;

  /** Field description */
  @Parameter(defaultValue = "${project.name}")
  protected String name;

  /** Field description */
  @Parameter
  protected String packager;

  /** Field description */
  @Parameter
  protected Platform platform;

  /** Field description */
  @Parameter
  protected String summary;

  /** Field description */
  @Parameter(defaultValue = "${project.build.directory}")
  protected File targetDirectory;

  /** Field description */
  @Parameter(defaultValue = "${projet.url}")
  protected String url;

  /** Field description */
  @Parameter
  protected String vendor;

  /** Field description */
  @Parameter(defaultValue = "${project.version}")
  protected String version;

  /** Field description */
  @Parameter
  private String classifier;

  /** Field description */
  @Component
  private MavenProject project;

  /** Field description */
  @Component
  private MavenProjectHelper projectHelper;
}
