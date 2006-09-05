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
package org.opensaml.xml.mock;

import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import javolution.util.FastList;

import org.opensaml.xml.ElementExtensibleXMLObject;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.signature.AbstractSignableXMLObject;
import org.opensaml.xml.util.XMLObjectChildrenList;

/**
 * Simple XMLObject that can be used for testing
 */
public class SimpleXMLObject extends AbstractSignableXMLObject implements ElementExtensibleXMLObject {
    
    /** Default namespace */
    public final static String NAMESPACE = "http://www.example.org/testObjects";
    
    /** Default namespace prefix */
    public final static String NAMESPACE_PREFIX = "test";
    
    /** Element local name */
    public final static String LOCAL_NAME = "SimpleElement";
    
    /** Default element name */
    public final static QName ELEMENT_NAME = new QName(NAMESPACE, LOCAL_NAME, NAMESPACE_PREFIX);
    
    /** Local name of encrypted element */
    public final static String ENCRYPTED_NAME = "Encrypted" + LOCAL_NAME;
    
    /** Name attribute name */
    public final static String ID_ATTRIB_NAME = "Id";
    
    /** Name attribute */
    private String id;
    
    /** Value of the object stored as text content in the element */
    private String value;
    
    /** Child SimpleXMLObjects */
    private XMLObjectChildrenList<SimpleXMLObject> simpleXMLObjects;
    
    /** Other children */
    private XMLObjectChildrenList<XMLObject> unknownXMLObjects;
    
    /**
     * Constructor
     */
    public SimpleXMLObject(String namspaceURI, String localName, String namespacePrefix) {
        super(namspaceURI, localName, namespacePrefix);
        
        simpleXMLObjects = new XMLObjectChildrenList<SimpleXMLObject>(this);
        unknownXMLObjects = new XMLObjectChildrenList<XMLObject>(this);
    }
    
    /**
     * Gets the name attribute.
     * 
     * @return the name attribute
     */
    public String getId() {
        return id;
    }
    
    /**
     * Sets the name attribute.
     * 
     * @param newId the name attribute
     */
    public void setId(String newId) {
        id = newId;
    }
    
    /**
     * Gets the value of this object.
     * 
     * @return the value of this object
     */
    public String getValue(){
        return value;
    }
    
    /**
     * Sets the value of this object.
     * 
     * @param newValue the value of this object
     */
    public void setValue(String newValue){
        value = prepareForAssignment(value, newValue);
    }
    
    /**
     * Gets the list of child SimpleXMLObjects.
     * 
     * @return the list of child SimpleXMLObjects
     */
    public List<SimpleXMLObject> getSimpleXMLObjects(){
        return simpleXMLObjects;
    }
    
    /** {@inheritDoc} */
    public List<XMLObject> getUnknownXMLObjects() {
        return unknownXMLObjects;
    }

    /** {@inheritDoc} */
    public List<XMLObject> getOrderedChildren() {
        FastList<XMLObject> children = new FastList<XMLObject>();
        
        children.addAll(simpleXMLObjects);
        children.addAll(unknownXMLObjects);
        children.add(getSignature());
        
        return Collections.unmodifiableList(children);
    }
}