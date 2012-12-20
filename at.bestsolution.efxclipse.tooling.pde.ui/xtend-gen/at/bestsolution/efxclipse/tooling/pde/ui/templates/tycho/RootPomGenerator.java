package at.bestsolution.efxclipse.tooling.pde.ui.templates.tycho;

import at.bestsolution.efxclipse.tooling.pde.ui.templates.tycho.Repository;
import at.bestsolution.efxclipse.tooling.pde.ui.templates.tycho.RootPomData;
import at.bestsolution.efxclipse.tooling.rrobot.model.task.DynamicFile;
import at.bestsolution.efxclipse.tooling.rrobot.model.task.Generator;
import at.bestsolution.efxclipse.tooling.rrobot.model.task.Variable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class RootPomGenerator implements Generator<DynamicFile> {
  public InputStream generate(final DynamicFile file, final Map<String,Object> data) {
    EList<Variable> _variables = file.getVariables();
    final Function1<Variable,Boolean> _function = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("groupId");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst = IterableExtensions.<Variable>findFirst(_variables, _function);
    final String groupId = _findFirst.getDefaultValue();
    EList<Variable> _variables_1 = file.getVariables();
    final Function1<Variable,Boolean> _function_1 = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("artifactId");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst_1 = IterableExtensions.<Variable>findFirst(_variables_1, _function_1);
    final String artifactId = _findFirst_1.getDefaultValue();
    EList<Variable> _variables_2 = file.getVariables();
    final Function1<Variable,Boolean> _function_2 = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("baseVersion");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst_2 = IterableExtensions.<Variable>findFirst(_variables_2, _function_2);
    String _defaultValue = _findFirst_2.getDefaultValue();
    final String version = this.toPomVersion(_defaultValue);
    EList<Variable> _variables_3 = file.getVariables();
    final Function1<Variable,Boolean> _function_3 = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("name");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst_3 = IterableExtensions.<Variable>findFirst(_variables_3, _function_3);
    final String name = _findFirst_3.getDefaultValue();
    ArrayList<String> _arrayList = new ArrayList<String>();
    final ArrayList<String> modules = _arrayList;
    EList<Variable> _variables_4 = file.getVariables();
    final Function1<Variable,Boolean> _function_4 = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("modules");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst_4 = IterableExtensions.<Variable>findFirst(_variables_4, _function_4);
    String _defaultValue_1 = _findFirst_4.getDefaultValue();
    String[] _split = _defaultValue_1.split(";");
    final Procedure1<String> _function_5 = new Procedure1<String>() {
        public void apply(final String it) {
          modules.add(it);
        }
      };
    IterableExtensions.<String>forEach(((Iterable<String>)Conversions.doWrapArray(_split)), _function_5);
    ArrayList<Repository> _arrayList_1 = new ArrayList<Repository>();
    final ArrayList<Repository> repos = _arrayList_1;
    EList<Variable> _variables_5 = file.getVariables();
    final Function1<Variable,Boolean> _function_6 = new Function1<Variable,Boolean>() {
        public Boolean apply(final Variable e) {
          String _key = e.getKey();
          boolean _equals = _key.equals("repos");
          return Boolean.valueOf(_equals);
        }
      };
    Variable _findFirst_5 = IterableExtensions.<Variable>findFirst(_variables_5, _function_6);
    String _defaultValue_2 = _findFirst_5.getDefaultValue();
    String[] _split_1 = _defaultValue_2.split(";");
    final Function1<String,Repository> _function_7 = new Function1<String,Repository>() {
        public Repository apply(final String it) {
          int _indexOf = it.indexOf("@");
          String _substring = it.substring(0, _indexOf);
          int _indexOf_1 = it.indexOf("@");
          int _plus = (_indexOf_1 + 1);
          int _length = it.length();
          String _substring_1 = it.substring(_plus, _length);
          Repository _repository = new Repository(_substring, _substring_1);
          return _repository;
        }
      };
    List<Repository> _map = ListExtensions.<String, Repository>map(((List<String>)Conversions.doWrapArray(_split_1)), _function_7);
    final Procedure1<Repository> _function_8 = new Procedure1<Repository>() {
        public void apply(final Repository it) {
          repos.add(it);
        }
      };
    IterableExtensions.<Repository>forEach(_map, _function_8);
    String _pomVersion = this.toPomVersion(version);
    RootPomData _rootPomData = new RootPomData(name, groupId, artifactId, 
      null, null, null, null, _pomVersion, "0.16.0", "4.8.1", "1.8.4", "4.2", "0.1.1", "2.2.0-SNAPSHOT", modules, repos);
    final RootPomData pomdata = _rootPomData;
    CharSequence _generate = this.generate(pomdata);
    String _string = _generate.toString();
    byte[] _bytes = _string.getBytes();
    ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(_bytes);
    return _byteArrayInputStream;
  }
  
  public String toPomGroupId(final String bundleId) {
    int _indexOf = bundleId.indexOf(".");
    int _minus = (-1);
    boolean _notEquals = (_indexOf != _minus);
    if (_notEquals) {
      int _lastIndexOf = bundleId.lastIndexOf(".");
      return bundleId.substring(0, _lastIndexOf);
    }
    return bundleId;
  }
  
  public String toPomVersion(final String version) {
    return version.replace(".qualifier", "-SNAPSHOT");
  }
  
  public CharSequence generate(final RootPomData data) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<project");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<modelVersion>4.0.0</modelVersion>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<name>");
    String _description = data.getDescription();
    _builder.append(_description, "	");
    _builder.append("</name>");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<prerequisites>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<maven>3.0</maven>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</prerequisites>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<groupId>");
    String _groupId = data.getGroupId();
    _builder.append(_groupId, "	");
    _builder.append("</groupId>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<artifactId>");
    String _artifactId = data.getArtifactId();
    _builder.append(_artifactId, "	");
    _builder.append("</artifactId>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<version>");
    String _version = data.getVersion();
    _builder.append(_version, "	");
    _builder.append("</version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<packaging>pom</packaging>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<properties>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<tycho-version>");
    String _tychoVersion = data.getTychoVersion();
    _builder.append(_tychoVersion, "		");
    _builder.append("</tycho-version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<junit-version>");
    String _junitVersion = data.getJunitVersion();
    _builder.append(_junitVersion, "		");
    _builder.append("</junit-version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<mockito-version>");
    String _mockitoVersion = data.getMockitoVersion();
    _builder.append(_mockitoVersion, "		");
    _builder.append("</mockito-version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<platform-version>");
    String _platformVersion = data.getPlatformVersion();
    _builder.append(_platformVersion, "		");
    _builder.append("</platform-version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<efx-version>");
    String _efxVersion = data.getEfxVersion();
    _builder.append(_efxVersion, "		");
    _builder.append("</efx-version>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</properties>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<modules>");
    _builder.newLine();
    {
      List<String> _modulePaths = data.getModulePaths();
      for(final String m : _modulePaths) {
        _builder.append("\t\t");
        _builder.append("<module>");
        _builder.append(m, "		");
        _builder.append("</module>");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.append("</modules>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<repositories>");
    _builder.newLine();
    {
      List<Repository> _repositories = data.getRepositories();
      for(final Repository r : _repositories) {
        _builder.append("\t\t");
        _builder.append("<repository>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("<id>");
        String _id = r.getId();
        _builder.append(_id, "			");
        _builder.append("</id>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("<layout>p2</layout>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("<url>");
        String _url = r.getUrl();
        _builder.append(_url, "			");
        _builder.append("</url>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("</repository>");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</repositories>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<pluginRepositories>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<pluginRepository>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<id>tycho</id>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<url>http://repository.sonatype.org/content/groups/sonatype-public-grid</url>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<snapshots>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<enabled>true</enabled>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</snapshots>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</pluginRepository>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</pluginRepositories>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<build>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<!-- build plugins -->");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<plugins>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<groupId>org.eclipse.tycho</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<artifactId>tycho-maven-plugin</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<version>${tycho-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<extensions>true</extensions>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<groupId>org.eclipse.tycho</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<artifactId>target-platform-configuration</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<version>${tycho-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<resolver>p2</resolver>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<pomDependencies>consider</pomDependencies>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<environments>");
    _builder.newLine();
    {
      boolean _isEnvLinux32 = data.isEnvLinux32();
      if (_isEnvLinux32) {
        _builder.append("\t\t\t\t\t\t");
        _builder.append("<environment>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<os>linux</os>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<ws>gtk</ws>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<arch>x86</arch>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("</environment>");
        _builder.newLine();
      }
    }
    {
      boolean _isEnvLinux64 = data.isEnvLinux64();
      if (_isEnvLinux64) {
        _builder.append("\t\t\t\t\t\t");
        _builder.append("<environment>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<os>linux</os>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<ws>gtk</ws>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<arch>x86_64</arch>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("</environment>");
        _builder.newLine();
      }
    }
    {
      boolean _isEnvWin32 = data.isEnvWin32();
      if (_isEnvWin32) {
        _builder.append("\t\t\t\t\t\t");
        _builder.append("<environment>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<os>win32</os>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<ws>win32</ws>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<arch>x86</arch>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("</environment>");
        _builder.newLine();
      }
    }
    {
      boolean _isEnvWin64 = data.isEnvWin64();
      if (_isEnvWin64) {
        _builder.append("\t\t\t\t\t\t");
        _builder.append("<environment>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<os>win32</os>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<ws>win32</ws>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<arch>x86_64</arch>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("</environment>");
        _builder.newLine();
      }
    }
    {
      boolean _isEnvMacOS = data.isEnvMacOS();
      if (_isEnvMacOS) {
        _builder.append("\t\t\t\t\t\t");
        _builder.append("<environment>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<os>macosx</os>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<ws>cocoa</ws>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("\t");
        _builder.append("<arch>x86_64</arch>");
        _builder.newLine();
        _builder.append("\t\t\t\t\t\t");
        _builder.append("</environment>");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t\t\t");
    _builder.append("</environments>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</configuration>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</plugins>");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<!-- defines the default settings for the used plugins -->");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<pluginManagement>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<plugins>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<groupId>org.eclipse.tycho</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<artifactId>tycho-compiler-plugin</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<version>${tycho-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<encoding>UTF-8</encoding>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<source>1.7</source>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<target>1.7</target>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<extraClasspathElements>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("<extraClasspathElement>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("<groupId>javafx</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("<artifactId>javafx.mvn</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("<version>");
    String _javaFXArtifactVersion = data.getJavaFXArtifactVersion();
    _builder.append(_javaFXArtifactVersion, "								");
    _builder.append("</version> ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("</extraClasspathElement>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("</extraClasspathElements>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<groupId>org.eclipse.tycho</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<artifactId>tycho-source-plugin</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<version>${tycho-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<executions>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("<execution>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("<id>plugin-source</id>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("<goals>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("<goal>plugin-source</goal>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("</goals>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("</execution>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("</executions>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<plugin>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<groupId>org.eclipse.tycho</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<artifactId>tycho-packaging-plugin</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<version>${tycho-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<configuration>");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("<archiveSite>true</archiveSite>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("</configuration>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</plugin>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</plugins>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</pluginManagement>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</build>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<dependencyManagement>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<dependencies>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<dependency>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<groupId>junit</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<artifactId>junit</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<version>${junit-version}</version>");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("<scope>test</scope>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("</dependency>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</dependencies>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</dependencyManagement>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<dependencies>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<dependency>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<groupId>org.mockito</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<artifactId>mockito-core</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<version>${mockito-version}</version>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</dependency>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<dependency>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<groupId>junit</groupId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<artifactId>junit</artifactId>");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("<scope>test</scope>");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("</dependency>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</dependencies>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("</project>");
    return _builder;
  }
}
