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

package org.opensaml.saml2.metadata.impl;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.common.SAMLObjectBaseTestCase;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml2.metadata.AttributeProfile;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml2.metadata.NameIDFormat;
import org.opensaml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml2.metadata.Organization;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.SingleSignOnService;

/**
 * 
 */
public class IDPSSODescriptorTest extends SAMLObjectBaseTestCase {

    /** List of expected supported protocols */
    protected ArrayList<String> expectedSupportedProtocol;

    /** Expected cacheDuration value in miliseconds */
    protected long expectedCacheDuration;

    /** Expected validUntil value */
    protected DateTime expectedValidUntil;

    /** Expected error url */
    protected String expectedErrorURL;

    /** expected value for WantAuthnRequestSigned attribute */
    protected Boolean expectedWantAuthnReqSigned;

    /**
     * Constructor
     */
    public IDPSSODescriptorTest() {
        singleElementFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptor.xml";
        singleElementOptionalAttributesFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptorOptionalAttributes.xml";
        childElementsFile = "/data/org/opensaml/saml2/metadata/impl/IDPSSODescriptorChildElements.xml";
    }

    protected void setUp() throws Exception {
        super.setUp();

        expectedSupportedProtocol = new ArrayList<String>();
        expectedSupportedProtocol.add("urn:foo:bar");
        expectedSupportedProtocol.add("urn:fooz:baz");

        expectedCacheDuration = 90000;
        expectedValidUntil = new DateTime(2005, 12, 7, 10, 21, 0, 0, ISOChronology.getInstanceUTC());

        expectedErrorURL = "http://example.org";

        expectedWantAuthnReqSigned = Boolean.TRUE;
    }

    public void testSingleElementUnmarshall() {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(singleElementFile);

        assertEquals("Supported protocols not equal to expected value", expectedSupportedProtocol, descriptor
                .getSupportedProtocols());
    }

    public void testSingleElementOptionalAttributesUnmarshall() {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(singleElementOptionalAttributesFile);

        assertEquals("Cache duration was not expected value", expectedCacheDuration, descriptor.getCacheDuration()
                .longValue());
        assertEquals("ValidUntil was not expected value", expectedValidUntil, descriptor.getValidUntil());
        assertEquals("WantAuthnRequestsSigned attribute was not expected value", expectedWantAuthnReqSigned, descriptor
                .wantAuthnRequestsSigned());
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsUnmarshall()
     */
    public void testChildElementsUnmarshall() {
        IDPSSODescriptor descriptor = (IDPSSODescriptor) unmarshallElement(childElementsFile);

        assertNotNull("Extensions", descriptor.getExtensions());
        // TODO KeyDescriptor
        assertEquals("KeyDescriptor", 0, descriptor.getKeyDescriptors().size());
        // TODO Attributes
        assertEquals("Attributes", 0, descriptor.getAttributes().size());
        assertNotNull("Organization child", descriptor.getOrganization());
        assertEquals("ContactPerson count", 2, descriptor.getContactPersons().size());

        assertEquals("ArtifactResolutionService count", 1, descriptor.getArtifactResolutionServices().size());
        assertEquals("SingleLogoutService count", 2, descriptor.getSingleLogoutServices().size());
        assertEquals("ManageNameIDService count", 4, descriptor.getManageNameIDServices().size());
        assertEquals("NameIDFormat count", 1, descriptor.getNameIDFormats().size());

        assertEquals("SingleSignOnService count", 3, descriptor.getSingleSignOnServices().size());
        assertEquals("NameIDMappingService count", 2, descriptor.getNameIDMappingServices().size());
        assertEquals("AssertionIDRequestService count", 3, descriptor.getAssertionIDRequestServices().size());
        assertEquals("AttributeProfile count", 3, descriptor.getAttributeProfiles().size());
    }

    public void testSingleElementMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildXMLObject(qname);

        for (String protocol : expectedSupportedProtocol) {
            descriptor.addSupportedProtocol(protocol);
        }
        descriptor.setWantAuthnRequestSigned(expectedWantAuthnReqSigned);

        assertEquals(expectedDOM, descriptor);
    }

    public void testSingleElementOptionalAttributesMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildXMLObject(qname);

        for (String protocol : expectedSupportedProtocol) {
            descriptor.addSupportedProtocol(protocol);
        }

        descriptor.setCacheDuration(expectedCacheDuration);
        descriptor.setValidUntil(expectedValidUntil);
        descriptor.setErrorURL(expectedErrorURL);
        descriptor.setWantAuthnRequestSigned(expectedWantAuthnReqSigned);

        assertEquals(expectedOptionalAttributesDOM, descriptor);
    }

    /*
     * @see org.opensaml.common.SAMLObjectBaseTestCase#testChildElementsMarshall()
     */
    public void testChildElementsMarshall() {
        QName qname = new QName(SAMLConstants.SAML20MD_NS, IDPSSODescriptor.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        IDPSSODescriptor descriptor = (IDPSSODescriptor) buildXMLObject(qname);

        QName extensionsQName = new QName(SAMLConstants.SAML20MD_NS, Extensions.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        descriptor.setExtensions((Extensions) buildXMLObject(extensionsQName));

        // TODO KeyDescriptor
        // TODO Attributes

        QName orgQName = new QName(SAMLConstants.SAML20MD_NS, Organization.LOCAL_NAME, SAMLConstants.SAML20MD_PREFIX);
        descriptor.setOrganization((Organization) buildXMLObject(orgQName));

        QName contactQName = new QName(SAMLConstants.SAML20MD_NS, ContactPerson.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 2; i++) {
            descriptor.getContactPersons().add((ContactPerson) buildXMLObject(contactQName));
        }

        QName artResQName = new QName(SAMLConstants.SAML20MD_NS, ArtifactResolutionService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        descriptor.getArtifactResolutionServices().add((ArtifactResolutionService) buildXMLObject(artResQName));

        QName sloQName = new QName(SAMLConstants.SAML20MD_NS, SingleLogoutService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 2; i++) {
            descriptor.getSingleLogoutServices().add((SingleLogoutService) buildXMLObject(sloQName));
        }

        QName mngNameIDQName = new QName(SAMLConstants.SAML20MD_NS, ManageNameIDService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 4; i++) {
            descriptor.getManageNameIDServices().add((ManageNameIDService) buildXMLObject(mngNameIDQName));
        }

        QName nameIDFormatQName = new QName(SAMLConstants.SAML20MD_NS, NameIDFormat.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        descriptor.getNameIDFormats().add((NameIDFormat) buildXMLObject(nameIDFormatQName));

        QName ssoQName = new QName(SAMLConstants.SAML20MD_NS, SingleSignOnService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 3; i++) {
            descriptor.getSingleSignOnServices().add((SingleSignOnService) buildXMLObject(ssoQName));
        }

        QName nameIDMapQName = new QName(SAMLConstants.SAML20MD_NS, NameIDMappingService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 2; i++) {
            descriptor.getNameIDMappingServices().add((NameIDMappingService) buildXMLObject(nameIDMapQName));
        }

        QName assertIDReqQName = new QName(SAMLConstants.SAML20MD_NS, AssertionIDRequestService.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 3; i++) {
            descriptor.getAssertionIDRequestServices()
                    .add((AssertionIDRequestService) buildXMLObject(assertIDReqQName));
        }

        QName attributeProlfileQName = new QName(SAMLConstants.SAML20MD_NS, AttributeProfile.LOCAL_NAME,
                SAMLConstants.SAML20MD_PREFIX);
        for (int i = 0; i < 3; i++) {
            descriptor.getAttributeProfiles().add((AttributeProfile) buildXMLObject(attributeProlfileQName));
        }
        assertEquals(expectedChildElementsDOM, descriptor);
    }

}