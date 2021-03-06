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
import com.github.sdorra.nativepkg.mappings.DirectoryMapping;
import com.github.sdorra.nativepkg.mappings.FileMapping;
import com.github.sdorra.nativepkg.mappings.LinkMapping;
import com.github.sdorra.nativepkg.mappings.Mappings;

import static com.google.common.base.Preconditions.*;

//~--- JDK imports ------------------------------------------------------------

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.redline_rpm.Builder;
import org.redline_rpm.header.Architecture;
import org.redline_rpm.header.Os;
import org.redline_rpm.header.RpmType;
import org.redline_rpm.payload.Directive;

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
  public Mappings getRpmMappings()
  {
    if (rpmMappings == null)
    {
      rpmMappings = new Mappings();
    }

    return rpmMappings;
  }

  /**
   * Method description
   *
   *
   * @return
   */
  public Scripts getRpmScripts()
  {
    if (rpmScripts == null)
    {
      rpmScripts = new Scripts();
    }

    return rpmScripts;
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
   * @param rpmScripts
   */
  public void setRpmScripts(Scripts rpmScripts)
  {
    this.rpmScripts = rpmScripts;
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
  public void execute() throws MojoExecutionException, MojoFailureException
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
    builder.setPackager(maintainer);
    builder.setUrl(url);
    builder.setVendor(vendor);

    try
    {
      Scripts scripts = getRpmScripts().merge(getScripts());

      attachScripts(builder, scripts);

      for (Dependency dep : getMergedDependencies())
      {
        attach(builder, dep);
      }

      attach(builder);

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
   * @param builder
   * @param dep
   */
  private void attach(Builder builder, Dependency dep)
  {
    builder.addDependency(dep.getName(), dep.getComparison(), dep.getVersion());
  }

  /**
   * Method description
   *
   *
   * @param builder
   *
   * @throws IOException
   * @throws NoSuchAlgorithmException
   */
  private void attach(Builder builder)
    throws IOException, NoSuchAlgorithmException
  {
    Mappings mergedMappings = getMappings().merge(getRpmMappings());

    for (DirectoryMapping dir : mergedMappings.getDirectories())
    {
      builder.addDirectory(dir.getPath(), dir.getDirMode(), null,
        dir.getUname(), dir.getGname(), dir.isAddParents());
      attach(builder, dir.getFiles());
    }

    attach(builder, mergedMappings.getFiles());

    for (LinkMapping link : mergedMappings.getLinks())
    {
      builder.addLink(link.getSource(), link.getTarget(),
        link.getPermissions());
    }
  }

  /**
   * Method description
   *
   *
   * @param builder
   * @param files
   *
   * @throws IOException
   * @throws NoSuchAlgorithmException
   */
  private void attach(Builder builder, Iterable<FileMapping> files)
    throws IOException, NoSuchAlgorithmException
  {
    for (FileMapping file : files)
    {
      attach(builder, file);
    }
  }

  /**
   * Method description
   *
   *
   * @param builder
   * @param file
   *
   * @throws IOException
   * @throws NoSuchAlgorithmException
   */
  private void attach(Builder builder, FileMapping file)
    throws IOException, NoSuchAlgorithmException
  {
    Directive directive = null;

    if (file.isConfig())
    {
      directive = Directive.CONFIG;
    }

    //J-
    builder.addFile(
      file.getPath(), 
      file.getSource(), 
      file.getMode(),
      file.getDirMode(), 
      directive, 
      file.getUname(), 
      file.getUname(), 
      file.isAddParents()
    );
    //J+
  }

  /**
   * Method description
   *
   *
   * @param builder
   * @param scripts
   *
   * @throws IOException
   * @throws MojoExecutionException
   */
  private void attachScripts(Builder builder, Scripts scripts)
    throws MojoExecutionException, IOException
  {
    if (scripts.getPreInstall() != null)
    {
      if (!scripts.getPreInstall().exists())
      {
        throw new MojoExecutionException("could not find pre install script");
      }

      builder.setPreInstallScript(scripts.getPreInstall());
    }

    if (scripts.getPostInstall() != null)
    {
      if (!scripts.getPostInstall().exists())
      {
        throw new MojoExecutionException("could not find post install script");
      }

      builder.setPostInstallScript(scripts.getPostInstall());
    }

    if (scripts.getPreUninstall() != null)
    {
      if (!scripts.getPreUninstall().exists())
      {
        throw new MojoExecutionException("could not find pre uninstall script");
      }

      builder.setPreUninstallScript(scripts.getPreUninstall());
    }

    if (scripts.getPostUninstall() != null)
    {
      if (!scripts.getPostUninstall().exists())
      {
        throw new MojoExecutionException("could not find post install script");
      }

      builder.setPostUninstallScript(scripts.getPostUninstall());
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
  
  public Iterable<Dependency> getMergedDependencies()
  {
    return Iterables.concat(getDependencies(), getRpmDependencies());
  }

  public List<Dependency> getRpmDependencies()
  {
    if (rpmDependencies == null)
    {
      rpmDependencies = ImmutableList.of();
    }
    return rpmDependencies;
  }

  public void setRpmDependencies(List<Dependency> rpmDependencies)
  {
    this.rpmDependencies = rpmDependencies;
  }
  
  //~--- fields ---------------------------------------------------------------

  /** Field description */
  @Parameter
  private String buildHost;

  /** Field description */
  @Parameter
  private String group;

  /** Field description */
  @Parameter(defaultValue = "${maven.build.timestamp}")
  private String release;

  /** Field description */
  @Parameter
  private Mappings rpmMappings;

  /** Field description */
  @Parameter
  private Scripts rpmScripts;
  
  /** Field description */
  @Parameter
  private List<Dependency> rpmDependencies;

  /** Field description */
  @Parameter
  private String type = RpmType.BINARY.toString();
}
