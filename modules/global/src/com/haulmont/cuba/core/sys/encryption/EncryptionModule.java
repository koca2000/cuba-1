/*
 * Copyright (c) 2012 Haulmont Technology Ltd. All Rights Reserved.
 * Haulmont Technology proprietary and confidential.
 * Use is subject to license terms.
 */

package com.haulmont.cuba.core.sys.encryption;

import com.haulmont.cuba.core.global.HashDescriptor;
import com.haulmont.cuba.core.global.HashMethod;
import com.haulmont.cuba.security.entity.User;

import java.util.UUID;

/**
 * Encryption algorithm
 *
 * @author artamonov
 * @version $Id$
 */
public interface EncryptionModule {

    HashMethod getHashMethod();

    HashDescriptor getHash(String content);

    String getPasswordHash(UUID userId, String password);

    String getHash(String content, String salt);

    String getPlainHash(String content);

    boolean checkPassword(User user, String givenPassword);
}