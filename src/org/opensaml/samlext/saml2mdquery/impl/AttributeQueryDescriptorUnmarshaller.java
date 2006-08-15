/*
 * Copyright [2006] [University Corporation for Advanced Internet Development, Inc.]
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

package org.opensaml.samlext.saml2mdquery.impl;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.samlext.saml2mdquery.AttributeQueryDescriptor;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.UnmarshallingException;

/**
 * Unmarshaller of {@link AttributeQueryDescriptor} objects.
 */
public class AttributeQueryDescriptorUnmarshaller extends QueryDescriptorTypeUnmarshaller {

    /** Constructor */
    public AttributeQueryDescriptorUnmarshaller(){
        super(SAMLConstants.SAML20MDQUERY_NS, AttributeQueryDescriptor.DEFAULT_ELEMENT_LOCAL_NAME);
    }
    
    /** {@inheritDoc} */
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject)
            throws UnmarshallingException {
        AttributeQueryDescriptor descriptor = (AttributeQueryDescriptor) parentSAMLObject;

        if (childSAMLObject instanceof AttributeConsumingService) {
            descriptor.getAttributeConsumingServices().add((AttributeConsumingService) childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}