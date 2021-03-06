/*
 * Copyright (c) 2008-2016 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.haulmont.cuba.core.sys;

import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.app.UserSessionsAPI;
import com.haulmont.cuba.security.global.NoUserSessionException;
import com.haulmont.cuba.security.global.UserSession;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(UserSessionSource.NAME)
public class UserSessionSourceImpl extends AbstractUserSessionSource {

    @Inject
    private UserSessionsAPI userSessions;

    @Override
    public boolean checkCurrentUserSession() {
        SecurityContext securityContext = AppContext.getSecurityContext();
        if (securityContext != null
                && securityContext.getSession() != null
                && securityContext.getSession().isSystem()) {
            return true;
        }

        return securityContext != null && userSessions.get(securityContext.getSessionId()) != null;
    }

    @Override
    public UserSession getUserSession() {
        SecurityContext securityContext = AppContext.getSecurityContextNN();
        if (securityContext.getSession() != null
                && securityContext.getSession().isSystem()) {
            return securityContext.getSession();
        }

        UserSession session = userSessions.getAndRefresh(securityContext.getSessionId());
        if (session == null) {
            throw new NoUserSessionException(securityContext.getSessionId());
        }
        return session;
    }
}