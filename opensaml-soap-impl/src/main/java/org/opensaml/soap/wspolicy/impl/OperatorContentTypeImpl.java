/*
 * Licensed to the University Corporation for Advanced Internet Development,
 * Inc. (UCAID) under one or more contributor license agreements.  See the
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The UCAID licenses this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opensaml.soap.wspolicy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.soap.wspolicy.All;
import org.opensaml.soap.wspolicy.ExactlyOne;
import org.opensaml.soap.wspolicy.OperatorContentType;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyReference;

/**
 * OperatorContentTypeImpl.
 */
public class OperatorContentTypeImpl extends AbstractWSPolicyObject implements OperatorContentType {
    
    /** All child elements. */
    private IndexedXMLObjectChildrenList<XMLObject> xmlObjects;

    /**
     * Constructor.
     *
     * @param namespaceURI The namespace of the element
     * @param elementLocalName The local name of the element
     * @param namespacePrefix The namespace prefix of the element
     */
    public OperatorContentTypeImpl(final String namespaceURI, final String elementLocalName,
            final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        xmlObjects = new IndexedXMLObjectChildrenList<>(this);
    }

    /** {@inheritDoc} */
    public List<All> getAlls() {
        return (List<All>) xmlObjects.subList(All.ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<ExactlyOne> getExactlyOnes() {
        return (List<ExactlyOne>) xmlObjects.subList(ExactlyOne.ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<Policy> getPolicies() {
        return (List<Policy>) xmlObjects.subList(Policy.ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<PolicyReference> getPolicyReferences() {
        return (List<PolicyReference>) xmlObjects.subList(PolicyReference.ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getXMLObjects() {
        return xmlObjects;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>) xmlObjects.subList(typeOrName);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<>();
        children.addAll(xmlObjects);
        return Collections.unmodifiableList(children);
    }

}
