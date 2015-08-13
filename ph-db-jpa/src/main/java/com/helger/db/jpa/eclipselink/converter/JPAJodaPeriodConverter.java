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
package com.helger.db.jpa.eclipselink.converter;

import java.sql.Timestamp;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.persistence.internal.helper.ClassConstants;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.mappings.foundation.AbstractDirectMapping;
import org.eclipse.persistence.sessions.Session;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.lang.ClassHelper;

@Immutable
public class JPAJodaPeriodConverter implements Converter
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (JPAJodaPeriodConverter.class);

  public JPAJodaPeriodConverter ()
  {}

  @Nullable
  public Timestamp convertObjectValueToDataValue (final Object aObjectValue, final Session session)
  {
    return aObjectValue == null ? null : new Timestamp (((Period) aObjectValue).getMillis ());
  }

  @Nullable
  public Period convertDataValueToObjectValue (final Object aDataValue, final Session session)
  {
    if (aDataValue != null)
      try
      {
        return new Period (((Timestamp) aDataValue).getTime ());
      }
      catch (final IllegalArgumentException ex)
      {
        // failed to convert
        s_aLogger.warn ("Failed to convert '" +
                        aDataValue +
                        "' of type " +
                        ClassHelper.getSafeClassName (aDataValue) +
                        "to Period!");
      }
    return null;
  }

  public boolean isMutable ()
  {
    return false;
  }

  public void initialize (final DatabaseMapping aMapping, final Session aSession)
  {
    if (aMapping.isDirectToFieldMapping ())
    {
      final AbstractDirectMapping aDirectMapping = (AbstractDirectMapping) aMapping;

      // Allow user to specify field type to override computed value. (i.e.
      // blob, nchar)
      if (aDirectMapping.getFieldClassification () == null)
        aDirectMapping.setFieldClassification (ClassConstants.TIMESTAMP);
    }
  }
}
