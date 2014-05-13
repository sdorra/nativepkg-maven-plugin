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

import com.google.common.base.Strings;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.freecompany.redline.Builder;
import org.freecompany.redline.header.Architecture;
import org.freecompany.redline.header.Os;
import org.freecompany.redline.header.RpmType;

import static com.google.common.base.Preconditions.*;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import java.util.Locale;

/**
 *
 * @author Sebastian Sdorra
 */
@Mojo(name = "rpm", defaultPhase = LifecyclePhase.PACKAGE)
public class RpmMojo extends NativePkgMojo
{

  /** Field description */
  private static final String TYPE = "rpm";

  //~--- get methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @return
   */
  public String getGroup()
  {
    return group;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getRelease()
  {
    return release;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public String getType()
  {
    return type;
  }

  //~--- set methods ----------------------------------------------------------

  /**
   * Method description
   *
   *
   * @param group
   */
  public void setGroup(String group)
  {
    this.group = group;
  }

  /**
   * Method description
   *
   *
   * @param release
   */
  public void setRelease(String release)
  {
    this.release = release;
  }

  /**
   * Method description
   *
   *
   * @param type
   */
  public void setType(String type)
  {
    this.type = type;
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
    checkNotNull(mappings, "mappings is required");
    checkArgument(!Strings.isNullOrEmpty(name), "name is required");
    checkArgument(!Strings.isNullOrEmpty(version), "version is required");
    checkArgument(!Strings.isNullOrEmpty(release), "release is required");

    Builder builder = new Builder();

    if (platform != null)
    {
      //J-
      builder.setPlatform(
        toEnum(Architecture.class, platform.getArchitecture()), 
        toEnum(Os.class, platform.getOs())
      );
      //J+
    }

    builder.setPackage(name, fixVersion(version), fixVersion(release));
    builder.setType(toEnum(RpmType.class, type));
    builder.setSummary(summary);
    builder.setDescription(description);
    builder.setBuildHost(buildHost);
    builder.setDistribution(destribution);
    builder.setGroup(group);
    builder.setLicense(license);
    builder.setPackager(packager);
    builder.setUrl(url);
    builder.setVendor(vendor);

    try
    {
      if (dependencies != null)
      {
        for (Dependency dep : dependencies)
        {
          dep.attach(builder);
        }
      }

      mappings.attach(builder);

      String filename = builder.build(targetDirectory);
      File rpm = new File(targetDirectory, filename);

      if (!rpm.exists())
      {
        throw new MojoExecutionException("rpm file not found");
      }

      attachArtifact(TYPE, rpm);
    }
    catch (IOException ex)
    {
      throw new MojoExecutionException("could not create rpm", ex);
    }
    catch (NoSuchAlgorithmException ex)
    {
      throw new MojoExecutionException("could not create rpm", ex);
    }
  }

  /**
   * Method description
   *
   *
   * @param version
   *
   * @return
   */
  private String fixVersion(String version)
  {
    return version.replace("-", "");
  }

  /**
   * Method description
   *
   *
   * @param type
   * @param value
   * @param <T>
   *
   * @return
   */
  private <T extends Enum<T>> T toEnum(Class<T> type, String value)
  {
    T e = null;

    if (!Strings.isNullOrEmpty(value))
    {
      e = Enum.valueOf(type, value.toUpperCase(Locale.ENGLISH));
    }

    return e;
  }

  //~--- fields ---------------------------------------------------------------

  /** Field description */
  @Parameter
  protected String group;

  /** Field description */
  @Parameter(defaultValue = "${maven.build.timestamp}")
  protected String release;

  /** Field description */
  @Parameter
  private String type = RpmType.BINARY.toString();
}
