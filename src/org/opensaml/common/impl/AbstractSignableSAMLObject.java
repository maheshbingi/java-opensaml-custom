/*
 * Copyright [2005] [University Corporation for Advanced Internet Development, Inc.]
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

/**
 * 
 */
package org.opensaml.common.impl;

import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.xml.AbstractValidatingSignableXMLObject;

/**
 * Abstract SAMLObject implementation that also implements {@link org.opensaml.xml.SignableXMLObject}
 */
public abstract class AbstractSignableSAMLObject extends AbstractValidatingSignableXMLObject implements SAMLObject {

    /** SAML version of this object */
    private SAMLVersion version;

    /**
     * Constructor
     * 
     * @param namespaceURI the namespace the element is in
     * @param elementLocalName the local name of the XML element this Object represents
     */
    protected AbstractSignableSAMLObject(String namespaceURI, String elementLocalName) {
        super(namespaceURI, elementLocalName);
    }

    /*
     * @see org.opensaml.common.SAMLObject#getVersion()
     */
    public SAMLVersion getVersion() {
        return version;
    }

    /**
     * Sets the SAML version for this object.
     * 
     * @param version the SAML version for this object
     */
    protected void setSAMLVersion(SAMLVersion version) {
        this.version = version;
    }
}