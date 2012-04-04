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

package org.opensaml.xmlsec.keyinfo.impl;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.Collection;

import org.opensaml.core.xml.XMLObjectBaseTestCase;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorManager;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xmlsec.keyinfo.impl.BasicKeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory;

/**
 * Test the NamedKeyInfoGeneratorFactory manager.
 */
public class NamedKeyInfoGeneratorManagerTest extends XMLObjectBaseTestCase {
    private NamedKeyInfoGeneratorManager manager;
    
    private BasicKeyInfoGeneratorFactory basicFactoryFoo;
    private BasicKeyInfoGeneratorFactory basicFactoryFoo2;
    private BasicKeyInfoGeneratorFactory basicFactoryBar;
    private X509KeyInfoGeneratorFactory x509FactoryFoo;
    private X509KeyInfoGeneratorFactory x509FactoryBar;
    
    private String nameFoo = "FOO";
    private String nameBar = "BAR";
    
    /** {@inheritDoc} */
    @BeforeMethod
    protected void setUp() throws Exception {
        manager = new NamedKeyInfoGeneratorManager();
        basicFactoryFoo = new BasicKeyInfoGeneratorFactory();
        basicFactoryFoo2 = new BasicKeyInfoGeneratorFactory();
        basicFactoryBar = new BasicKeyInfoGeneratorFactory();
        x509FactoryFoo = new X509KeyInfoGeneratorFactory();
        x509FactoryBar = new X509KeyInfoGeneratorFactory();
        
    }
    
    /** Test factory registration. */
    @Test
    public void testRegister() {
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameFoo, x509FactoryFoo);
        
        KeyInfoGeneratorManager fooManager = manager.getManager(nameFoo);
        AssertJUnit.assertNotNull("Expected named manager not present/created", fooManager);
        AssertJUnit.assertEquals("Unexpected # of managed factories", 2, fooManager.getFactories().size());
        
        AssertJUnit.assertTrue("Expected factory not found", fooManager.getFactories().contains(basicFactoryFoo));
        AssertJUnit.assertTrue("Expected factory not found", fooManager.getFactories().contains(x509FactoryFoo));
        
        // basicFactoryFoo2 should replace basicFactoryFoo
        manager.registerFactory(nameFoo, basicFactoryFoo2);
        AssertJUnit.assertFalse("Unexpected factory found", fooManager.getFactories().contains(basicFactoryFoo));
        AssertJUnit.assertTrue("Expected factory not found", fooManager.getFactories().contains(basicFactoryFoo2));
    }
    
    /** Test factory de-registration. */
    @Test
    public void testDeregister() {
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameFoo, x509FactoryFoo);
        
        KeyInfoGeneratorManager fooManager = manager.getManager(nameFoo);
        AssertJUnit.assertNotNull("Expected named manager not present/created", fooManager);
        AssertJUnit.assertEquals("Unexpected # of managed factories", 2, fooManager.getFactories().size());
        
        manager.deregisterFactory(nameFoo, x509FactoryFoo);
        AssertJUnit.assertTrue("Expected factory not found", fooManager.getFactories().contains(basicFactoryFoo));
        AssertJUnit.assertFalse("Unexpected factory found", fooManager.getFactories().contains(x509FactoryFoo));
        
        try {
            manager.deregisterFactory("BAZ", x509FactoryFoo);
            Assert.fail("Use of non-existent manager name should have caused an exception");
        } catch (IllegalArgumentException e) {
            // do nothing, should fail
        }        
    }
    
    /** Test access to manager names, and that can not be modified. */
    @Test
    public void testGetManagerNames() {
        Collection<String> names = manager.getManagerNames();
        AssertJUnit.assertTrue("Names was not empty", names.isEmpty());
        
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameBar, basicFactoryBar);
        names = manager.getManagerNames();
        AssertJUnit.assertEquals("Unexpected # of manager names", 2, names.size());
        
        AssertJUnit.assertTrue("Expected manager name not found", names.contains(nameFoo));
        AssertJUnit.assertTrue("Expected manager name not found", names.contains(nameBar));
        
        try {
            names.remove(basicFactoryFoo);
            Assert.fail("Returned names set should be unmodifiable");
        } catch (UnsupportedOperationException e) {
            // do nothing, should fail
        }        
        
    }
    
    /** Test that obtaining a manager by name works. */
    @Test
    public void testGetManagerByName() {
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameBar, basicFactoryBar);
        Collection<String> names = manager.getManagerNames();
        AssertJUnit.assertEquals("Unexpected # of manager names", 2, names.size());
        
        AssertJUnit.assertNotNull("Failed to find manager by name", manager.getManager(nameFoo));
        AssertJUnit.assertNotNull("Failed to find manager by name", manager.getManager(nameBar));
        
        AssertJUnit.assertFalse("Non-existent manager name found in name set", names.contains("BAZ"));
        AssertJUnit.assertNotNull("Failed to create new manager", manager.getManager("BAZ"));
        AssertJUnit.assertTrue("Expected manager name not found", names.contains("BAZ"));
    }
    
    /** Remove a manager by name. */
    @Test
    public void testRemoveManagerByName() {
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameFoo, x509FactoryFoo);
        manager.registerFactory(nameBar, basicFactoryBar);
        Collection<String> names = manager.getManagerNames();
        AssertJUnit.assertEquals("Unexpected # of manager names", 2, names.size());
        
        AssertJUnit.assertNotNull("Failed to find manager by name", manager.getManager(nameFoo));
        AssertJUnit.assertNotNull("Failed to find manager by name", manager.getManager(nameBar));
        AssertJUnit.assertTrue("Expected manager name not found", names.contains(nameFoo));
        AssertJUnit.assertTrue("Expected manager name not found", names.contains(nameBar));
        
        manager.removeManager(nameFoo);
        AssertJUnit.assertEquals("Unexpected # of manager names", 1, names.size());
        AssertJUnit.assertNotNull("Failed to find manager by name", manager.getManager(nameBar));
        AssertJUnit.assertFalse("Unexpected manager name found", names.contains(nameFoo));
        AssertJUnit.assertTrue("Expected manager name not found", names.contains(nameBar));
    }
    
    /** Test registering a factory in the default unnamed manager. */
    @Test
    public void testRegisterDefaultFactory() {
        KeyInfoGeneratorManager defaultManager = manager.getDefaultManager();
        AssertJUnit.assertEquals("Unexpected # of default factories", 0, defaultManager.getFactories().size());
        manager.registerDefaultFactory(basicFactoryFoo);
        manager.registerDefaultFactory(x509FactoryFoo);
        AssertJUnit.assertEquals("Unexpected # of default factories", 2, defaultManager.getFactories().size());
    }
    
    /** Test de-registering a factory in the default unnamed manager. */
    @Test
    public void testDeregisterDefaultFactory() {
        KeyInfoGeneratorManager defaultManager = manager.getDefaultManager();
        AssertJUnit.assertEquals("Unexpected # of default factories", 0, defaultManager.getFactories().size());
        manager.registerDefaultFactory(basicFactoryFoo);
        manager.registerDefaultFactory(x509FactoryFoo);
        AssertJUnit.assertEquals("Unexpected # of default factories", 2, defaultManager.getFactories().size());
        
        manager.deregisterDefaultFactory(x509FactoryFoo);
        AssertJUnit.assertEquals("Unexpected # of default factories", 1, defaultManager.getFactories().size());
    }
    
    /** Test lookup of factory from manager based on a credential instance. */
    @Test
    public void testLookupFactory() {
        manager.registerFactory(nameFoo, basicFactoryFoo);
        manager.registerFactory(nameFoo, x509FactoryFoo);
        manager.registerFactory(nameBar, basicFactoryBar);
        manager.registerFactory(nameBar, x509FactoryBar);
        manager.getManager("BAZ");
        AssertJUnit.assertEquals("Unexpected # of managed factories", 2, manager.getManager(nameFoo).getFactories().size());
        AssertJUnit.assertEquals("Unexpected # of managed factories", 2, manager.getManager(nameBar).getFactories().size());
        AssertJUnit.assertEquals("Unexpected # of managed factories", 0, manager.getManager("BAZ").getFactories().size());
        AssertJUnit.assertEquals("Unexpected # of manager names", 3, manager.getManagerNames().size());
        
        Credential basicCred = new BasicCredential();
        X509Credential x509Cred = new BasicX509Credential();
        
        AssertJUnit.assertNotNull("Failed to find factory based on manager name and credential", 
                manager.getFactory(nameFoo, basicCred));
        AssertJUnit.assertTrue("Found incorrect factory based on name and credential", 
                basicFactoryFoo == manager.getFactory(nameFoo, basicCred));
        
        AssertJUnit.assertNotNull("Failed to find factory based on manager name and credential", 
                manager.getFactory(nameFoo, x509Cred));
        AssertJUnit.assertTrue("Found incorrect factory based on name and credential", 
                x509FactoryFoo == manager.getFactory(nameFoo, x509Cred));
        
        AssertJUnit.assertNotNull("Failed to find factory based on manager name and credential", 
                manager.getFactory(nameBar, x509Cred));
        AssertJUnit.assertTrue("Found incorrect factory based on name and credential", 
                x509FactoryBar == manager.getFactory(nameBar, x509Cred));
        
        AssertJUnit.assertNull("Found non-existent factory based on name and credential", 
                manager.getFactory("BAZ", x509Cred));
        try {
            manager.getFactory("ABC123", x509Cred);
            Assert.fail("Use of non-existent manager name should have caused an exception");
        } catch (IllegalArgumentException e) {
            // do nothing, should fail
        }        
    }
    
    /** Test proper functioning of option to use the default manager for unnamed factories. */
    @Test
    public void testFallThroughToDefaultManager() {
        KeyInfoGeneratorFactory defaultX509Factory = new X509KeyInfoGeneratorFactory();
        manager.registerDefaultFactory(defaultX509Factory);
        manager.registerFactory(nameFoo, basicFactoryFoo);
        
        X509Credential x509Cred = new BasicX509Credential();
        
        manager.setUseDefaultManager(true);
        
        AssertJUnit.assertNotNull("Failed to find factory based on manager name and credential", 
                manager.getFactory(nameFoo, x509Cred));
        AssertJUnit.assertTrue("Found incorrect factory based on name and credential", 
                defaultX509Factory == manager.getFactory(nameFoo, x509Cred));
        
        manager.setUseDefaultManager(false);
        AssertJUnit.assertNull("Found factory in default manager even though useDefaultManager option set to false",
                manager.getFactory(nameFoo, x509Cred));
    }
}
