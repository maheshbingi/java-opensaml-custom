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

package org.opensaml.xmlsec.encryption.impl;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.opensaml.core.xml.XMLObjectProviderBaseTestCase;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.signature.KeyInfo;

/**
 *
 */
public class EncryptedDataTest extends XMLObjectProviderBaseTestCase {
    
    private String expectedId;
    
    private String expectedType;
    
    private String expectedMimeType;
    
    private String expectedEncoding;

    /**
     * Constructor
     *
     */
    public EncryptedDataTest() {
        singleElementFile = "/data/org/opensaml/xmlsec/encryption/impl/EncryptedData.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/xmlsec/encryption/impl/EncryptedDataOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/xmlsec/encryption/impl/EncryptedDataChildElements.xml";
        
    }

    /** {@inheritDoc} */
    @BeforeMethod
    protected void setUp() throws Exception {
        expectedId = "abc123";
        expectedType = "someType";
        expectedMimeType = "someMimeType";
        expectedEncoding = "someEncoding";
    }

    /** {@inheritDoc} */
    @Test
    public void testSingleElementUnmarshall() {
        EncryptedData ed = (EncryptedData) unmarshallElement(singleElementFile);
        
        AssertJUnit.assertNotNull("EncryptedData", ed);
        AssertJUnit.assertNull("EncryptionMethod child", ed.getEncryptionMethod());
        AssertJUnit.assertNull("KeyInfo child", ed.getKeyInfo());
        AssertJUnit.assertNull("CipherData child", ed.getCipherData());
        AssertJUnit.assertNull("EncryptionProperties child", ed.getEncryptionProperties());
    }

    /** {@inheritDoc} */
    @Test
    public void testChildElementsUnmarshall() {
        EncryptedData ed = (EncryptedData) unmarshallElement(childElementsFile);
        
        AssertJUnit.assertNotNull("EncryptedData", ed);
        AssertJUnit.assertNotNull("EncryptionMethod child", ed.getEncryptionMethod());
        AssertJUnit.assertNotNull("KeyInfo child", ed.getKeyInfo());
        AssertJUnit.assertNotNull("CipherData child", ed.getCipherData());
        AssertJUnit.assertNotNull("EncryptionProperties child", ed.getEncryptionProperties());
    }

    /** {@inheritDoc} */
    @Test
    public void testSingleElementOptionalAttributesUnmarshall() {
        EncryptedData ed = (EncryptedData) unmarshallElement(singleElementOptionalAttributesFile);
        
        AssertJUnit.assertNotNull("EncryptedData", ed);
        AssertJUnit.assertEquals("Id attribute", expectedId, ed.getID());
        AssertJUnit.assertEquals("Type attribute", expectedType, ed.getType());
        AssertJUnit.assertEquals("MimeType attribute", expectedMimeType, ed.getMimeType());
        AssertJUnit.assertEquals("Encoding attribute", expectedEncoding, ed.getEncoding());
        
        AssertJUnit.assertEquals("ID lookup failed", ed, ed.resolveID(expectedId));
    }

    /** {@inheritDoc} */
    @Test
    public void testSingleElementMarshall() {
        EncryptedData ed = (EncryptedData) buildXMLObject(EncryptedData.DEFAULT_ELEMENT_NAME);
        
        assertXMLEquals(expectedDOM, ed);
    }

    /** {@inheritDoc} */
    @Test
    public void testChildElementsMarshall() {
        EncryptedData ed = (EncryptedData) buildXMLObject(EncryptedData.DEFAULT_ELEMENT_NAME);
        
        ed.setEncryptionMethod((EncryptionMethod) buildXMLObject(EncryptionMethod.DEFAULT_ELEMENT_NAME));
        ed.setKeyInfo((KeyInfo) buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME));
        ed.setCipherData((CipherData) buildXMLObject(CipherData.DEFAULT_ELEMENT_NAME));
        ed.setEncryptionProperties((EncryptionProperties) buildXMLObject(EncryptionProperties.DEFAULT_ELEMENT_NAME));
        
        assertXMLEquals(expectedChildElementsDOM, ed);
    }

    /** {@inheritDoc} */
    @Test
    public void testSingleElementOptionalAttributesMarshall() {
        EncryptedData ed = (EncryptedData) buildXMLObject(EncryptedData.DEFAULT_ELEMENT_NAME);
        
        ed.setID(expectedId);
        ed.setType(expectedType);
        ed.setMimeType(expectedMimeType);
        ed.setEncoding(expectedEncoding);
        
        assertXMLEquals(expectedOptionalAttributesDOM, ed);
    }
    
    

}
