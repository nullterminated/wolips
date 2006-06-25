/* ====================================================================
 *
 * The ObjectStyle Group Software License, Version 1.0
 *
 * Copyright (c) 2002 - 2004 The ObjectStyle Group
 * and individual authors of the software.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        ObjectStyle Group (http://objectstyle.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "ObjectStyle Group" and "Cayenne"
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact andrus@objectstyle.org.
 *
 * 5. Products derived from this software may not be called "ObjectStyle"
 *    nor may "ObjectStyle" appear in their names without prior written
 *    permission of the ObjectStyle Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE OBJECTSTYLE GROUP OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the ObjectStyle Group.  For more
 * information on the ObjectStyle Group, please see
 * <http://objectstyle.org/>.
 *
 */

package org.objectstyle.wolips.preferences;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author uli
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class Preferences {

	/**
	 * Comment for <code>PREF_RUN_WOBUILDER_ON_BUILD</code>
	 */
	public static final String PREF_RUN_WOBUILDER_ON_BUILD = "org.objectstyle.wolips.preference.RunWOBuilderOnBuild";

  /**
   * Comment for <code>PREF_WRITE_XCODE_ON_BUILD</code>
   */
  public static final String PREF_WRITE_XCODE_ON_BUILD = "org.objectstyle.wolips.preference.WriteXcodeOnBuild";

  /**
   * Comment for <code>PREF_WRITE_XCODE21_ON_BUILD</code>
   */
  public static final String PREF_WRITE_XCODE21_ON_BUILD = "org.objectstyle.wolips.preference.WriteXcode21OnBuild";

  /**
   * Comment for <code>PREF_VALIDATE_WOD_ON_BUILD</code>
   */
  public static final String PREF_VALIDATE_WOD_ON_BUILD = "org.objectstyle.wolips.preference.ValidateWODOnBuild";

  /**
   * Comment for <code>PREF_AUTOEOGENERATE_ON_BUILD</code>
   */
  public static final String PREF_AUTOEOGENERATE_ON_BUILD = "org.objectstyle.wolips.preference.AutoEOGenerateOnBuild";

	/**
	 * Comment for <code>PREF_CAPTURE_ANT_OUTPUT</code>
	 */
	public static final String PREF_CAPTURE_ANT_OUTPUT = "org.objectstyle.wolips.preference.CapureAntOutput";

	/**
	 * Comment for <code>PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML</code>
	 */
	public static final String PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML = "org.objectstyle.wolips.Preference.OpenWOComponentActionIncludesOpenHTML";

	/**
	 * Comment for <code>PREF_NS_PROJECT_SEARCH_PATH</code>
	 */
	public static final String PREF_NS_PROJECT_SEARCH_PATH = "org.objectstyle.wolips.Preference.NSProjectSearch";

	/**
	 * Comment for <code>PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH</code>
	 */
	public static final String PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH = "org.objectstyle.wolips.Preference.RebuildWOBuildPropertiesOnNextLaunch";

	/**
	 * Comment for <code>PREF_WOLIPS_VERSION_EARLY_STARTUP</code>
	 */
	public static final String PREF_WOLIPS_VERSION_EARLY_STARTUP = "org.objectstyle.wolips.Preference.WOLipsVersionEarlyStartup";

	/**
	 * Comment for <code>PREF_LAUNCH_GLOBAL</code>
	 */
	public static final String PREF_LAUNCH_GLOBAL = "org.objectstyle.wolips.Preference.Launch_Global";

  public static final String PREF_EOGENERATOR_PATH = "org.objectstyle.wolips.Preference.EOGeneratorPath";

  public static final String PREF_EOGENERATOR_TEMPLATE_DIR = "org.objectstyle.wolips.Preference.EOGeneratorTemplateDir";

  public static final String PREF_EOGENERATOR_JAVA_TEMPLATE = "org.objectstyle.wolips.Preference.EOGeneratorTemplate";

  public static final String PREF_EOGENERATOR_SUBCLASS_JAVA_TEMPLATE = "org.objectstyle.wolips.Preference.EOGeneratorSubclassTemplate";

	/**
	 * Comment for <code>FLAG_INCLUDE_EXCLUDE_RULES_CHANGED</code>
	 */
	public static boolean FLAG_INCLUDE_EXCLUDE_RULES_CHANGED = false;
	/**
	 * Comment for <code>trueString</code>
	 */
	public static final String trueString = "true";

	/**
	 * Comment for <code>falseString</code>
	 */
	public static final String falseString = "false";

	// set this sting to a preferences key and call set defaults to set the
	// default value for this preferences key
	private static String SET_DEFAULTS_STRING = null;

	/**
	 * 
	 */
	public static void save() {
		IPreferenceStore store = getPreferenceStore();
		if (store instanceof IPersistentPreferenceStore) {
			IPersistentPreferenceStore pstore = (IPersistentPreferenceStore) store;
			if (pstore.needsSaving()) {
				try {
					pstore.save();
				} catch (IOException up) {
					// hmm, what should we do?
				}
			}
		}

	}

	/**
	 * Method setDefaults.
	 */
	public static void setDefaults() {
		IPreferenceStore store = getPreferenceStore();
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_CAPTURE_ANT_OUTPUT))
			store.setDefault(Preferences.PREF_CAPTURE_ANT_OUTPUT,
					Preferences.falseString);
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_RUN_WOBUILDER_ON_BUILD))
			store.setDefault(Preferences.PREF_RUN_WOBUILDER_ON_BUILD,
					Preferences.trueString);
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_EOGENERATOR_PATH)) {
      store.setDefault(Preferences.PREF_EOGENERATOR_PATH, "");
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_EOGENERATOR_TEMPLATE_DIR)) {
      store.setDefault(Preferences.PREF_EOGENERATOR_TEMPLATE_DIR, "");
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_EOGENERATOR_JAVA_TEMPLATE)) {
      store.setDefault(Preferences.PREF_EOGENERATOR_JAVA_TEMPLATE, "JavaSourceEOF52.eotemplate");
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_EOGENERATOR_SUBCLASS_JAVA_TEMPLATE)) {
      store.setDefault(Preferences.PREF_EOGENERATOR_SUBCLASS_JAVA_TEMPLATE, "JavaSubclassSourceEOF5.eotemplate");
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_WRITE_XCODE_ON_BUILD)) {
      store.setDefault(Preferences.PREF_WRITE_XCODE_ON_BUILD, Preferences.falseString);
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_WRITE_XCODE21_ON_BUILD)) {
      store.setDefault(Preferences.PREF_WRITE_XCODE21_ON_BUILD, Preferences.trueString);
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_VALIDATE_WOD_ON_BUILD)) {
      store.setDefault(Preferences.PREF_VALIDATE_WOD_ON_BUILD, Preferences.trueString);
    }
    if (Preferences.SET_DEFAULTS_STRING == null || Preferences.SET_DEFAULTS_STRING.equals(Preferences.PREF_AUTOEOGENERATE_ON_BUILD)) {
      store.setDefault(Preferences.PREF_AUTOEOGENERATE_ON_BUILD, Preferences.falseString);
    }
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML))
			store
					.setDefault(
							Preferences.PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML,
							Preferences.falseString);
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_NS_PROJECT_SEARCH_PATH))
			store.setDefault(Preferences.PREF_NS_PROJECT_SEARCH_PATH, "");
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH))
			store.setDefault(
					Preferences.PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH,
					PreferencesMessages.getString(Preferences.falseString));
		if (Preferences.SET_DEFAULTS_STRING == null
				|| Preferences.SET_DEFAULTS_STRING
						.equals(Preferences.PREF_WOLIPS_VERSION_EARLY_STARTUP))
			store.setDefault(Preferences.PREF_WOLIPS_VERSION_EARLY_STARTUP,
					"0.0.0");
		store.setDefault(Preferences.PREF_LAUNCH_GLOBAL, PreferencesMessages
				.getString(Preferences.PREF_LAUNCH_GLOBAL));
		Preferences.SET_DEFAULTS_STRING = null;
	}

	/**
	 * Method getString.
	 * 
	 * @param key
	 * @return String
	 */
	public static String getString(String key) {
		IPreferenceStore store = getPreferenceStore();
		String returnValue = store.getString(key);
		if (returnValue.equals(IPreferenceStore.STRING_DEFAULT_DEFAULT)) {
			Preferences.setDefaults();
			Preferences.SET_DEFAULTS_STRING = key;
			returnValue = store.getString(key);
		}
		return returnValue;
	}

	/**
	 * Method setString.
	 * 
	 * @param key
	 * @param value
	 */
	public static void setString(String key, String value) {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(key, value);
	}

	/**
	 * Method getBoolean.
	 * 
	 * @param key
	 * @return boolean
	 */
	private static boolean getBoolean(String key) {
		IPreferenceStore store = getPreferenceStore();
		return (Preferences.trueString.equals(store.getString(key)));
	}

	/**
	 * Method setBoolean.
	 * 
	 * @param key
	 * @param value
	 */
	private static void setBoolean(String key, boolean value) {
		IPreferenceStore store = getPreferenceStore();
		if (value)
			store.setValue(key, Preferences.trueString);
		else
			store.setValue(key, Preferences.falseString);
	}

	/**
	 * Method getPreferenceStore.
	 * 
	 * @return IPreferenceStore
	 */
	public static IPreferenceStore getPreferenceStore() {
		return PreferencesPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * @param key
	 * @return the launch info for the given key
	 */
	public static ILaunchInfo[] getLaunchInfoForKey(String key) {
		String string = Preferences.getString(key);
		return Preferences.getLaunchInfoFrom(string);
	}

	/**
	 * @param string
	 * @return creates a launch info from the given string
	 */
	public static ILaunchInfo[] getLaunchInfoFrom(String string) {
		if (string == null)
			return new ILaunchInfo[0];
		StringTokenizer stringTokenizer = new StringTokenizer(string, "<>");
		Vector vector = new Vector();
		while (stringTokenizer.hasMoreElements()) {
			Object token = stringTokenizer.nextElement();
			vector.add(token);
		}
		ILaunchInfo[] launchInfo = new ILaunchInfo[vector.size() / 3];
		int j = 0;
		for (int i = 0; i < launchInfo.length; i++) {
			launchInfo[i] = new LaunchInfo((String) vector.elementAt(j),
					(String) vector.elementAt(j + 1), (String) vector
							.elementAt(j + 2));
			j = j + 3;
		}
		return launchInfo;
	}

	/**
	 * @param parameter
	 * @param arguments
	 * @param enabled
	 * @return the launch info converted to a string
	 */
	public static String LaunchInfoToString(String[] parameter,
			String[] arguments, boolean[] enabled) {
		String value = "";
		for (int i = 0; i < parameter.length; i++) {
			value += parameter[i];
			value += "<>";
			value += arguments[i];
			value += "<>";
			value += enabled[i];
			if (i != (parameter.length - 1))
				value += "<>";
		}
		return value;
	}

	/**
	 * @param parameter
	 * @param arguments
	 * @param enabled
	 * @param key
	 */
	public static void setLaunchInfoForKey(String[] parameter,
			String[] arguments, boolean[] enabled, String key) {
		String value = Preferences.LaunchInfoToString(parameter, arguments,
				enabled);
		Preferences.setString(key, value);
	}

	private static class LaunchInfo implements ILaunchInfo {
		/**
		 * Comment for <code>parameter</code>
		 */
		public String parameter;

		/**
		 * Comment for <code>argument</code>
		 */
		public String argument;

		/**
		 * Comment for <code>enabled</code>
		 */
		public boolean enabled;

		/**
		 * @param parameter
		 * @param argument
		 * @param enabled
		 */
		public LaunchInfo(String parameter, String argument, String enabled) {
			this.parameter = parameter;
			this.argument = argument;
			this.enabled = (enabled != null && Preferences.trueString
					.equals(enabled));
		}

		public String getArgument() {
			return this.argument;
		}

		public boolean isEnabled() {
			return this.enabled;
		}

		public String getParameter() {
			return this.parameter;
		}

	}

	/**
	 * @return the global lauch preferences
	 */
	public static String getPREF_LAUNCH_GLOBAL() {
		return Preferences.getString(Preferences.PREF_LAUNCH_GLOBAL);
	}

	/**
	 * @return the nsprojectsearchpath preferences
	 */
	public static String getPREF_NS_PROJECT_SEARCH_PATH() {
		return Preferences.getString(Preferences.PREF_NS_PROJECT_SEARCH_PATH);
	}

	/**
	 * @return the preference
	 */
	public static String getPREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML() {
		return Preferences
				.getString(Preferences.PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML);
	}

	/**
	 * @return the preference
	 */
	public static boolean getPREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH() {
		return Preferences
				.getBoolean(Preferences.PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH);
	}

	/**
	 * @return the preferences
	 */
	public static boolean getPREF_CAPTURE_ANT_OUTPUT() {
		return Preferences.getBoolean(Preferences.PREF_CAPTURE_ANT_OUTPUT);
	}

	/**
	 * @return the preferences
	 */
	public static boolean getPREF_RUN_WOBUILDER_ON_BUILD() {
		return Preferences.getBoolean(Preferences.PREF_RUN_WOBUILDER_ON_BUILD);
	}
  
  public static boolean getPREF_WRITE_XCODE_ON_BUILD() {
    return Preferences.getBoolean(Preferences.PREF_WRITE_XCODE_ON_BUILD);
  }
  
  public static boolean getPREF_WRITE_XCODE21_ON_BUILD() {
    return Preferences.getBoolean(Preferences.PREF_WRITE_XCODE21_ON_BUILD);
  }
  
  public static boolean getPREF_VALIDATE_WOD_ON_BUILD() {
    return Preferences.getBoolean(Preferences.PREF_VALIDATE_WOD_ON_BUILD);
  }

  public static boolean getPREF_AUTOEOGENERATE_ON_BUILD() {
    return Preferences.getBoolean(Preferences.PREF_AUTOEOGENERATE_ON_BUILD);
  }

  public static String getEOGeneratorPath() {
    return Preferences.getString(Preferences.PREF_EOGENERATOR_PATH);
  }
  
  public static void setEOGeneratorPath(String _path) {
    Preferences.setString(Preferences.PREF_EOGENERATOR_PATH, _path);
  }

  public static String getEOGeneratorTemplateDir() {
    return Preferences.getString(Preferences.PREF_EOGENERATOR_TEMPLATE_DIR);
  }
  
  public static void setEOGeneratorTemplateDir(String _path) {
    Preferences.setString(Preferences.PREF_EOGENERATOR_TEMPLATE_DIR, _path);
  }

  public static String getEOGeneratorJavaTemplate() {
    return Preferences.getString(Preferences.PREF_EOGENERATOR_JAVA_TEMPLATE);
  }
  
  public static void setEOGeneratorJavaTemplate(String _path) {
    Preferences.setString(Preferences.PREF_EOGENERATOR_JAVA_TEMPLATE, _path);
  }

  public static String getEOGeneratorSubclassJavaTemplate() {
    return Preferences.getString(Preferences.PREF_EOGENERATOR_SUBCLASS_JAVA_TEMPLATE);
  }
  
  public static void setEOGeneratorSubclassJavaTemplate(String _path) {
    Preferences.setString(Preferences.PREF_EOGENERATOR_SUBCLASS_JAVA_TEMPLATE, _path);
  }
  
	/**
	 * @return the preferences
	 */
	public static String getPREF_WOLIPS_VERSION_EARLY_STARTUP() {
		return Preferences
				.getString(Preferences.PREF_WOLIPS_VERSION_EARLY_STARTUP);
	}

	/**
	 * @param string
	 */
	public static void setPREF_LAUNCH_GLOBAL(String string) {
		Preferences.setString(Preferences.PREF_LAUNCH_GLOBAL, string);
	}

	/**
	 * @param string
	 */
	public static void setPREF_NS_PROJECT_SEARCH_PATH(String string) {
		Preferences.setString(Preferences.PREF_NS_PROJECT_SEARCH_PATH, string);
	}

	/**
	 * @param value
	 */
	public static void setPREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML(
			boolean value) {
		Preferences.setBoolean(
				Preferences.PREF_OPEN_WOCOMPONENT_ACTION_INCLUDES_OPEN_HTML,
				value);
	}

	/**
	 * @param value
	 */
	public static void setPREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH(
			boolean value) {
		Preferences.setBoolean(
				Preferences.PREF_REBUILD_WOBUILD_PROPERTIES_ON_NEXT_LAUNCH,
				value);
	}

	/**
	 * @param value
	 */
	public static void setPREF_CAPTURE_ANT_OUTPUT(boolean value) {
		Preferences.setBoolean(Preferences.PREF_CAPTURE_ANT_OUTPUT, value);
	}

	/**
	 * @param value
	 */
	public static void setPREF_RUN_WOBUILDER_ON_BUILD(boolean value) {
		Preferences.setBoolean(Preferences.PREF_RUN_WOBUILDER_ON_BUILD, value);
	}
  
  public static void setPREF_WRITE_XCODE_ON_BUILD(boolean value) {
    Preferences.setBoolean(Preferences.PREF_WRITE_XCODE_ON_BUILD, value);
  }
  
  public static void setPREF_WRITE_XCODE21_ON_BUILD(boolean value) {
    Preferences.setBoolean(Preferences.PREF_WRITE_XCODE21_ON_BUILD, value);
  }

  public static void setPREF_VALIDATE_WOD_ON_BUILD(boolean value) {
    Preferences.setBoolean(Preferences.PREF_VALIDATE_WOD_ON_BUILD, value);
  }

  public static void setPREF_AUTOEOGENERATE_ON_BUILD(boolean value) {
    Preferences.setBoolean(Preferences.PREF_AUTOEOGENERATE_ON_BUILD, value);
  }

	/**
	 * @param string
	 */
	public static void setPREF_WOLIPS_VERSION_EARLY_STARTUP(String string) {
		Preferences.setString(Preferences.PREF_WOLIPS_VERSION_EARLY_STARTUP,
				string);
	}

}
