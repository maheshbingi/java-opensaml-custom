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
package org.opensaml.saml2.core.validator;

import javax.xml.namespace.QName;

import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.NameID;

/**
 *
 */
public class LogoutRequestSchemaTest extends RequestSchemaTestBase {

    /**
     * Constructor
     *
     */
    public LogoutRequestSchemaTest() {
        super();
        targetQName = new QName(SAMLConstants.SAML20P_NS, LogoutRequest.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20P_PREFIX);
        validator = new LogoutRequestSchemaValidator();
    }

    /** {@inheritDoc} */
    protected void populateRequiredData() {
        super.populateRequiredData();
        LogoutRequest request = (LogoutRequest) target;
        NameID nameid = (NameID) buildXMLObject(new QName(SAMLConstants.SAML20_NS, NameID.DEFAULT_ELEMENT_LOCAL_NAME));
        request.setNameID(nameid);
    }
    
    
    public void testIdentifiersFailure() {
        LogoutRequest request = (LogoutRequest) target;
        
        request.setNameID(null);
        assertValidationFail("NameID was null");
        
        // TODO need a minimal BaseID subclass impl to really test the combinations here
    }
    
    // TODO EncryptedID pending encryption implementation.

}