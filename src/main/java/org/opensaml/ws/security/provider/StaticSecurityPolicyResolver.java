/*
 * Copyright 2008 University Corporation for Advanced Internet Development, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.ws.security.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.ws.message.MessageContext;
import org.opensaml.ws.security.SecurityPolicy;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.xml.security.SecurityException;

/** A simple security policy resolver implementation that returns a static list of policies. */
public class StaticSecurityPolicyResolver implements SecurityPolicyResolver {

    /** Registered security policies. */
    private ArrayList<SecurityPolicy> securityPolicies;

    /**
     * Constructor.
     * 
     * @param policies the static list of policies returned by this resolver
     */
    public StaticSecurityPolicyResolver(List<SecurityPolicy> policies) {
        securityPolicies = new ArrayList<SecurityPolicy>(policies);
    }

    /** {@inheritDoc} */
    public Iterable<SecurityPolicy> resolve(MessageContext criteria) throws SecurityException {
        return Collections.unmodifiableList(securityPolicies);
    }

    /**
     * {@inheritDoc}
     * 
     * If more than one policy is registered with this resolver this method returns the first policy in the list.
     */
    public SecurityPolicy resolveSingle(MessageContext criteria) throws SecurityException {
        if (!securityPolicies.isEmpty()) {
            return securityPolicies.get(0);
        } else {
            return null;
        }
    }
}