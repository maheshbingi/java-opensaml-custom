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

package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SubjectName;

/** Concrete implementation of {@link X509Data}. */
public class X509DataImpl extends AbstractXMLObject implements X509Data {

    /** The list of XMLObject child elements. */
    private final IndexedXMLObjectChildrenList indexedChildren;

    /**
     * Constructor.
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     * @param namespacePrefix the prefix for the given namespace
     */
    protected X509DataImpl(final String namespaceURI, final String elementLocalName, final String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        indexedChildren = new IndexedXMLObjectChildrenList(this);
    }

    /** {@inheritDoc} */
    public List<XMLObject> getXMLObjects() {
        return (List<XMLObject>) indexedChildren;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getXMLObjects(final QName typeOrName) {
        return (List<XMLObject>) indexedChildren.subList(typeOrName);
    }

    /** {@inheritDoc} */
    public List<X509IssuerSerial> getX509IssuerSerials() {
        return (List<X509IssuerSerial>) indexedChildren.subList(X509IssuerSerial.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<X509SKI> getX509SKIs() {
        return (List<X509SKI>) indexedChildren.subList(X509SKI.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<X509SubjectName> getX509SubjectNames() {
        return (List<X509SubjectName>) indexedChildren.subList(X509SubjectName.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<X509Certificate> getX509Certificates() {
        return (List<X509Certificate>) indexedChildren.subList(X509Certificate.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<X509CRL> getX509CRLs() {
        return (List<X509CRL>) indexedChildren.subList(X509CRL.DEFAULT_ELEMENT_NAME);
    }

    /** {@inheritDoc} */
    public List<X509Digest> getX509Digests() {
        return (List<X509Digest>) indexedChildren.subList(X509Digest.DEFAULT_ELEMENT_NAME);
    }
    
    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        final ArrayList<XMLObject> children = new ArrayList<>();

        children.addAll((List<XMLObject>) indexedChildren);

        if (children.size() == 0) {
            return null;
        }

        return Collections.unmodifiableList(children);
    }

}
