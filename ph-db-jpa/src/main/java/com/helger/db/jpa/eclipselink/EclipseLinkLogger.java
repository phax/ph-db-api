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
package com.helger.db.jpa.eclipselink;

import java.util.List;

import javax.annotation.Nonnull;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.debug.GlobalDebug;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.ENewLineMode;

/**
 * A logging adapter that can be hooked into JPA and forwards all logging
 * requests to SLF4J.
 *
 * @author Philip Helger
 */
public class EclipseLinkLogger extends AbstractSessionLog
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (EclipseLinkLogger.class);

  @Override
  public void log (@Nonnull final SessionLogEntry aSessionLogEntry)
  {
    final int nLogLevel = aSessionLogEntry.getLevel ();
    if (!shouldLog (nLogLevel))
      return;

    // JPA uses the System property for adding line breaks
    final List <String> aMsgLines = StringHelper.getExploded (ENewLineMode.DEFAULT.getText (),
                                                              formatMessage (aSessionLogEntry));
    final int nMaxIndex = aMsgLines.size ();
    for (int i = 0; i < nMaxIndex; ++i)
    {
      final String sMsg = aMsgLines.get (i);
      final Throwable t = i == nMaxIndex - 1 ? aSessionLogEntry.getException () : null;
      if (nLogLevel >= SessionLog.SEVERE)
        s_aLogger.error (sMsg, t);
      else
        if (nLogLevel >= SessionLog.WARNING)
          s_aLogger.warn (sMsg, t);
        else
          if (nLogLevel >= SessionLog.CONFIG || GlobalDebug.isDebugMode ())
          {
            if (s_aLogger.isInfoEnabled ())
              s_aLogger.info (sMsg, t);
          }
          else
          {
            if (s_aLogger.isDebugEnabled ())
              s_aLogger.debug (sMsg, t);
          }
    }
  }
}