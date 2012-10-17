package com.jetbrains.python.testing.unittest;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizerUtil;
import com.intellij.openapi.util.WriteExternalException;
import com.jetbrains.python.testing.AbstractPythonTestRunConfiguration;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * @author Leonid Shalupov
 */
public class PythonUnitTestRunConfiguration extends
                                            AbstractPythonTestRunConfiguration
                                              implements PythonUnitTestRunConfigurationParams {
  private boolean myIsPureUnittest = true;
  protected String myTitle = "Unittest";
  protected String myPluralTitle = "Unittests";

  private String myParams = "";
  private boolean useParam = false;

  public PythonUnitTestRunConfiguration(RunConfigurationModule module,
                                        ConfigurationFactory configurationFactory,
                                        String name) {
    super(module, configurationFactory, name);
  }

  @Override
  protected ModuleBasedConfiguration createInstance() {
    return new PythonUnitTestRunConfiguration(getConfigurationModule(), getFactory(), getName());
  }

  @Override
  protected SettingsEditor<? extends RunConfiguration> createConfigurationEditor() {
    return new PythonUnitTestRunConfigurationEditor(getProject(), this);
  }

  @Override
  public RunProfileState getState(@NotNull final Executor executor, @NotNull final ExecutionEnvironment env) throws ExecutionException {
    return new PythonUnitTestCommandLineState(this, env);
  }

  @Override
  public void readExternal(Element element) throws InvalidDataException {
    super.readExternal(element);
    myIsPureUnittest = Boolean.parseBoolean(JDOMExternalizerUtil.readField(element, "PUREUNITTEST"));
    myParams = JDOMExternalizerUtil.readField(element, "PARAMS");
    useParam = Boolean.parseBoolean(JDOMExternalizerUtil.readField(element, "USE_PARAM"));
  }

  @Override
  public void writeExternal(Element element) throws WriteExternalException {
    super.writeExternal(element);
    JDOMExternalizerUtil.writeField(element, "PUREUNITTEST", String.valueOf(myIsPureUnittest));
    JDOMExternalizerUtil.writeField(element, "PARAMS", myParams);
    JDOMExternalizerUtil.writeField(element, "USE_PARAM", String.valueOf(useParam));
  }

  @Override
  protected String getTitle() {
    return myTitle;
  }

  @Override
  protected String getPluralTitle() {
    return myPluralTitle;
  }

  public static void copyParams(PythonUnitTestRunConfigurationParams source, PythonUnitTestRunConfigurationParams target) {
    copyParams(source.getTestRunConfigurationParams(), target.getTestRunConfigurationParams());
    target.setPureUnittest(source.isPureUnittest());
    target.setParams(source.getParams());
    target.useParam(source.useParam());
  }

  @Override
  public boolean isPureUnittest() {
    return myIsPureUnittest;
  }

  public void setPureUnittest(boolean isPureUnittest) {
    myIsPureUnittest = isPureUnittest;
  }

  public String getParams() {
    return myParams;
  }

  public void setParams(String pattern) {
    myParams = pattern;
  }

  public boolean useParam() {
    return useParam;
  }

  public void useParam(boolean useParam) {
    this.useParam = useParam;
  }
}
