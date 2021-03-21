/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Time;
import java.time.LocalTime;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;

/**
 * Test class for class {@link JPALocalTimeConverter}.
 *
 * @author Philip Helger
 */
public final class JPALocalTimeConverterTest
{
  @Test
  public void testAll ()
  {
    // Get a time without milliseconds
    // java.sql.Time uses only hour, minute and seconds
    final LocalTime aNow = PDTFactory.getCurrentLocalTime ().withNano (0);
    final JPALocalTimeConverter aConverter = new JPALocalTimeConverter ();
    final Time aDataValue = aConverter.convertObjectValueToDataValue (aNow, null);
    assertNotNull (aDataValue);
    assertEquals (aNow, aConverter.convertDataValueToObjectValue (aDataValue, null));
  }
}
