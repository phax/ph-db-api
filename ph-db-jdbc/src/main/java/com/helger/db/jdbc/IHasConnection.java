/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.db.jdbc;

import java.io.Serializable;
import java.sql.Connection;

import javax.annotation.Nullable;

/**
 * Simple {@link Connection} provider interface.
 *
 * @author Philip Helger
 */
@FunctionalInterface
public interface IHasConnection extends Serializable
{
  @Nullable
  Connection getConnection ();

  default boolean shouldCloseConnection ()
  {
    return true;
  }
}
