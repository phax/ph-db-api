/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.db.jpa.h2;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.persistence.platform.database.H2Platform;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.CollectionHelper;
import com.helger.db.api.h2.H2Helper;
import com.helger.db.jpa.AbstractGlobalEntityManagerFactory;

/**
 * JPA Singleton specific for H2 database.
 *
 * @author Philip Helger
 */
public abstract class AbstractGlobalEntityManagerFactoryH2 extends AbstractGlobalEntityManagerFactory
{
  private static final Map <String, String> s_aDefaultConnectionProperties = new HashMap <String, String> ();

  @Nonnull
  @Nonempty
  private static String _buildJDBCString (@Nonnull @Nonempty final String sJdbcURL,
                                          @Nullable final Map <String, String> aConnectionProperties)
  {
    // Build connection properties from default values and the optional ones
    final Map <String, String> aProps = CollectionHelper.newMap (s_aDefaultConnectionProperties);
    if (aConnectionProperties != null)
      aProps.putAll (aConnectionProperties);

    return H2Helper.buildJDBCString (sJdbcURL, aProps);
  }

  /*
   * Constructor. Never initialize manually!
   */
  protected AbstractGlobalEntityManagerFactoryH2 (@Nonnull @Nonempty final String sJdbcURL,
                                                  @Nullable final String sUser,
                                                  @Nullable final String sPassword,
                                                  @Nonnull @Nonempty final String sPersistenceUnitName)
  {
    this (sJdbcURL, null, sUser, sPassword, sPersistenceUnitName, null);
  }

  /*
   * Constructor. Never initialize manually!
   */
  protected AbstractGlobalEntityManagerFactoryH2 (@Nonnull @Nonempty final String sJdbcURL,
                                                  @Nullable final Map <String, String> aConnectionProperties,
                                                  @Nullable final String sUser,
                                                  @Nullable final String sPassword,
                                                  @Nonnull @Nonempty final String sPersistenceUnitName)
  {
    this (sJdbcURL, aConnectionProperties, sUser, sPassword, sPersistenceUnitName, null);
  }

  /**
   * Constructor. Never initialize manually!
   */
  protected AbstractGlobalEntityManagerFactoryH2 (@Nonnull @Nonempty final String sJdbcURL,
                                                  @Nullable final Map <String, String> aConnectionProperties,
                                                  @Nullable final String sUser,
                                                  @Nullable final String sPassword,
                                                  @Nonnull @Nonempty final String sPersistenceUnitName,
                                                  @Nullable final Map <String, Object> aAdditionalFactoryProps)
  {
    super (H2DriverSingleton.getInstance ().getDriverClassName (),
           _buildJDBCString (sJdbcURL, aConnectionProperties),
           sUser,
           sPassword,
           H2Platform.class.getName (),
           sPersistenceUnitName,
           aAdditionalFactoryProps);
  }

  public static final void setJMXEnabledByDefault (final boolean bEnabled)
  {
    if (bEnabled)
      s_aDefaultConnectionProperties.put ("JMX", "TRUE");
    else
      s_aDefaultConnectionProperties.remove ("JMX");
  }
}
