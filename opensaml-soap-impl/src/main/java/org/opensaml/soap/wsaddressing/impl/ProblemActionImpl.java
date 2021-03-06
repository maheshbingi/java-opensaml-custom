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

package org.opensaml.soap.wsaddressing.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wsaddressing.Action;
import org.opensaml.soap.wsaddressing.ProblemAction;
import org.opensaml.soap.wsaddressing.SoapAction;

/**
 * Implementation of {@link ProblemAction}.
 */
public class ProblemActionImpl extends AbstractWSAddressingObject implements ProblemAction {
    
    /** Action child element. */
    private Action action;
    
    /** SoapAction child element. */
    private SoapAction soapAction;
    
    /** Wildcard attributes. */
    private AttributeMap unknownAttributes;

    /**
     * Constructor.
     * 
     * @param namespaceURI The namespace of the element
     * @param elementLocalName The local name of the element
     * @param namespacePrefix The namespace prefix of the element
     */
    public ProblemActionImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        unknownAttributes = new AttributeMap(this);
    }

    /** {@inheritDoc} */
    public Action getAction() {
        return action;
    }

    /** {@inheritDoc} */
    public SoapAction getSoapAction() {
        return soapAction;
    }

    /** {@inheritDoc} */
    public void setAction(final Action newAction) {
        action = prepareForAssignment(action, newAction);
    }

    /** {@inheritDoc} */
    public void setSoapAction(final SoapAction newSoapAction) {
        soapAction = prepareForAssignment(soapAction, newSoapAction);
    }

    /** {@inheritDoc} */
    public AttributeMap getUnknownAttributes() {
        return unknownAttributes;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<>();
        if (action != null) {
            children.add(action);
        }
        if (soapAction != null) {
            children.add(soapAction);
        }

        return Collections.unmodifiableList(children);
    }

}
