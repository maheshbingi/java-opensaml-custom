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

package org.opensaml.saml1.core.impl;

import org.opensaml.common.impl.AbstractSAMLObjectUnmarshaller;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml1.core.StatusCode;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

/**
 * A thread-safe Unmarshaller for {@link org.opensaml.saml1.core.StatusCode} objects.
 */
public class StatusCodeUnmarshaller extends AbstractSAMLObjectUnmarshaller {

    /** Constructor */
    public StatusCodeUnmarshaller() {
        super(SAMLConstants.SAML1P_NS, StatusCode.DEFAULT_ELEMENT_LOCAL_NAME);
    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processChildElement(org.opensaml.xml.XMLObject,
     *      org.opensaml.xml.XMLObject)
     */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {

        StatusCode statusCode = (StatusCode) parentSAMLObject;

        if (childSAMLObject instanceof StatusCode) {
            statusCode.setStatusCode((StatusCode) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }

    }

    /*
     * @see org.opensaml.xml.io.AbstractXMLObjectUnmarshaller#processAttribute(org.opensaml.xml.XMLObject,
     *      org.w3c.dom.Attr)
     */
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {

        StatusCode statusCode = (StatusCode) samlObject;

        if (attribute.getName().equals(StatusCode.VALUE_ATTRIB_NAME)) {
            statusCode.setValue(attribute.getValue());
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}