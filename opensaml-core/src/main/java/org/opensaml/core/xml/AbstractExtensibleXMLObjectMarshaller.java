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

package org.opensaml.core.xml;

import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;

import net.shibboleth.utilities.java.support.xml.AttributeSupport;

import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * AbstractExtensibleXMLObjectMarshaller marshalls element of type <code>xs:any</code> and with
 * <code>xs:anyAttribute</code> attributes.
 */
public abstract class AbstractExtensibleXMLObjectMarshaller extends AbstractElementExtensibleXMLObjectMarshaller {
    
    /** Constructor. */
    public AbstractExtensibleXMLObjectMarshaller(){
        super();
    }

    /**
     * Marshalls the <code>xs:anyAttribute</code> attributes.
     * 
     * {@inheritDoc}
     */
    @Override
    protected void marshallAttributes(@Nonnull final XMLObject xmlObject, @Nonnull final Element domElement)
            throws MarshallingException {
        final AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject) xmlObject;
        Attr attribute;
        final Document document = domElement.getOwnerDocument();
        for (final Entry<QName, String> entry : anyAttribute.getUnknownAttributes().entrySet()) {
            attribute = AttributeSupport.constructAttribute(document, entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (XMLObjectProviderRegistrySupport.isIDAttribute(entry.getKey())
                    || anyAttribute.getUnknownAttributes().isIDAttribute(entry.getKey())) {
                attribute.getOwnerElement().setIdAttributeNode(attribute, true);
            }
        }
    }

}