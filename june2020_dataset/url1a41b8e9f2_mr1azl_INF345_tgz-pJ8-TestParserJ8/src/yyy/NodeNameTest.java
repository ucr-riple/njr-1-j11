 
package yyy;

import javax.xml.namespace.QName;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

 
/**
 */
public class NodeNameTest extends NodeTest {
    private QName qname;
    private String namespaceURI;

 
    /**
     * Constructor for NodeNameTest.
     * @param qname QName
     */
    public NodeNameTest(QName qname) {
        this.qname = qname;
    }
 
    /**
     * Constructor for NodeNameTest.
     * @param qname QName
     * @param namespaceURI String
     */
    public NodeNameTest(QName qname, String namespaceURI) {
        this.qname = qname;
        this.namespaceURI = namespaceURI;
    }

 
    /**
     * Method getNodeQName.
     * @return QName
     */
    public QName getNodeQName() {
        return qname;
    }

  
    /**
     * Method getNamespaceURI.
     * @return String
     * @see org.w3c.dom.Node#getNamespaceURI()
     */
    public String getNamespaceURI() {
        return namespaceURI;
    }

   
    /**
     * Method isWildcard.
     * @return boolean
     */
    public boolean isWildcard() {
        return qname.getLocalPart().equals("*");
    }

    /**
     * Method toString.
     * @return String
     */
    public String toString() {
        return qname.toString();
    }

	/**
	 * Method getNodeName.
	 * @return String
	 * @see org.w3c.dom.Node#getNodeName()
	 */
	@Override
	public String getNodeName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getNodeValue.
	 * @return String
	 * @throws DOMException
	 * @see org.w3c.dom.Node#getNodeValue()
	 */
	@Override
	public String getNodeValue() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setNodeValue.
	 * @param nodeValue String
	 * @throws DOMException
	 * @see org.w3c.dom.Node#setNodeValue(String)
	 */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method getNodeType.
	 * @return short
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	@Override
	public short getNodeType() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Method getParentNode.
	 * @return Node
	 * @see org.w3c.dom.Node#getParentNode()
	 */
	@Override
	public Node getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getChildNodes.
	 * @return NodeList
	 * @see org.w3c.dom.Node#getChildNodes()
	 */
	@Override
	public NodeList getChildNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getFirstChild.
	 * @return Node
	 * @see org.w3c.dom.Node#getFirstChild()
	 */
	@Override
	public Node getFirstChild() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getLastChild.
	 * @return Node
	 * @see org.w3c.dom.Node#getLastChild()
	 */
	@Override
	public Node getLastChild() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getPreviousSibling.
	 * @return Node
	 * @see org.w3c.dom.Node#getPreviousSibling()
	 */
	@Override
	public Node getPreviousSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getNextSibling.
	 * @return Node
	 * @see org.w3c.dom.Node#getNextSibling()
	 */
	@Override
	public Node getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getAttributes.
	 * @return NamedNodeMap
	 * @see org.w3c.dom.Node#getAttributes()
	 */
	@Override
	public NamedNodeMap getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getOwnerDocument.
	 * @return Document
	 * @see org.w3c.dom.Node#getOwnerDocument()
	 */
	@Override
	public Document getOwnerDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method insertBefore.
	 * @param newChild Node
	 * @param refChild Node
	 * @return Node
	 * @throws DOMException
	 * @see org.w3c.dom.Node#insertBefore(Node, Node)
	 */
	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method replaceChild.
	 * @param newChild Node
	 * @param oldChild Node
	 * @return Node
	 * @throws DOMException
	 * @see org.w3c.dom.Node#replaceChild(Node, Node)
	 */
	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method removeChild.
	 * @param oldChild Node
	 * @return Node
	 * @throws DOMException
	 * @see org.w3c.dom.Node#removeChild(Node)
	 */
	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method appendChild.
	 * @param newChild Node
	 * @return Node
	 * @throws DOMException
	 * @see org.w3c.dom.Node#appendChild(Node)
	 */
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method hasChildNodes.
	 * @return boolean
	 * @see org.w3c.dom.Node#hasChildNodes()
	 */
	@Override
	public boolean hasChildNodes() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method cloneNode.
	 * @param deep boolean
	 * @return Node
	 * @see org.w3c.dom.Node#cloneNode(boolean)
	 */
	@Override
	public Node cloneNode(boolean deep) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method normalize.
	 * @see org.w3c.dom.Node#normalize()
	 */
	@Override
	public void normalize() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method isSupported.
	 * @param feature String
	 * @param version String
	 * @return boolean
	 * @see org.w3c.dom.Node#isSupported(String, String)
	 */
	@Override
	public boolean isSupported(String feature, String version) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method getPrefix.
	 * @return String
	 * @see org.w3c.dom.Node#getPrefix()
	 */
	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setPrefix.
	 * @param prefix String
	 * @throws DOMException
	 * @see org.w3c.dom.Node#setPrefix(String)
	 */
	@Override
	public void setPrefix(String prefix) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method getLocalName.
	 * @return String
	 * @see org.w3c.dom.Node#getLocalName()
	 */
	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method hasAttributes.
	 * @return boolean
	 * @see org.w3c.dom.Node#hasAttributes()
	 */
	@Override
	public boolean hasAttributes() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method getBaseURI.
	 * @return String
	 * @see org.w3c.dom.Node#getBaseURI()
	 */
	@Override
	public String getBaseURI() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method compareDocumentPosition.
	 * @param other Node
	 * @return short
	 * @throws DOMException
	 * @see org.w3c.dom.Node#compareDocumentPosition(Node)
	 */
	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Method getTextContent.
	 * @return String
	 * @throws DOMException
	 * @see org.w3c.dom.Node#getTextContent()
	 */
	@Override
	public String getTextContent() throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setTextContent.
	 * @param textContent String
	 * @throws DOMException
	 * @see org.w3c.dom.Node#setTextContent(String)
	 */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method isSameNode.
	 * @param other Node
	 * @return boolean
	 * @see org.w3c.dom.Node#isSameNode(Node)
	 */
	@Override
	public boolean isSameNode(Node other) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method lookupPrefix.
	 * @param namespaceURI String
	 * @return String
	 * @see org.w3c.dom.Node#lookupPrefix(String)
	 */
	@Override
	public String lookupPrefix(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method isDefaultNamespace.
	 * @param namespaceURI String
	 * @return boolean
	 * @see org.w3c.dom.Node#isDefaultNamespace(String)
	 */
	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method lookupNamespaceURI.
	 * @param prefix String
	 * @return String
	 * @see org.w3c.dom.Node#lookupNamespaceURI(String)
	 */
	@Override
	public String lookupNamespaceURI(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method isEqualNode.
	 * @param arg Node
	 * @return boolean
	 * @see org.w3c.dom.Node#isEqualNode(Node)
	 */
	@Override
	public boolean isEqualNode(Node arg) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method getFeature.
	 * @param feature String
	 * @param version String
	 * @return Object
	 * @see org.w3c.dom.Node#getFeature(String, String)
	 */
	@Override
	public Object getFeature(String feature, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setUserData.
	 * @param key String
	 * @param data Object
	 * @param handler UserDataHandler
	 * @return Object
	 * @see org.w3c.dom.Node#setUserData(String, Object, UserDataHandler)
	 */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getUserData.
	 * @param key String
	 * @return Object
	 * @see org.w3c.dom.Node#getUserData(String)
	 */
	@Override
	public Object getUserData(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getTagName.
	 * @return String
	 * @see org.w3c.dom.Element#getTagName()
	 */
	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getAttribute.
	 * @param name String
	 * @return String
	 * @see org.w3c.dom.Element#getAttribute(String)
	 */
	@Override
	public String getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setAttribute.
	 * @param name String
	 * @param value String
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setAttribute(String, String)
	 */
	@Override
	public void setAttribute(String name, String value) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method removeAttribute.
	 * @param name String
	 * @throws DOMException
	 * @see org.w3c.dom.Element#removeAttribute(String)
	 */
	@Override
	public void removeAttribute(String name) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method getAttributeNode.
	 * @param name String
	 * @return Attr
	 * @see org.w3c.dom.Element#getAttributeNode(String)
	 */
	@Override
	public Attr getAttributeNode(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setAttributeNode.
	 * @param newAttr Attr
	 * @return Attr
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setAttributeNode(Attr)
	 */
	@Override
	public Attr setAttributeNode(Attr newAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method removeAttributeNode.
	 * @param oldAttr Attr
	 * @return Attr
	 * @throws DOMException
	 * @see org.w3c.dom.Element#removeAttributeNode(Attr)
	 */
	@Override
	public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getElementsByTagName.
	 * @param name String
	 * @return NodeList
	 * @see org.w3c.dom.Element#getElementsByTagName(String)
	 */
	@Override
	public NodeList getElementsByTagName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getAttributeNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @return String
	 * @throws DOMException
	 * @see org.w3c.dom.Element#getAttributeNS(String, String)
	 */
	@Override
	public String getAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setAttributeNS.
	 * @param namespaceURI String
	 * @param qualifiedName String
	 * @param value String
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setAttributeNS(String, String, String)
	 */
	@Override
	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method removeAttributeNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @throws DOMException
	 * @see org.w3c.dom.Element#removeAttributeNS(String, String)
	 */
	@Override
	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method getAttributeNodeNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @return Attr
	 * @throws DOMException
	 * @see org.w3c.dom.Element#getAttributeNodeNS(String, String)
	 */
	@Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setAttributeNodeNS.
	 * @param newAttr Attr
	 * @return Attr
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setAttributeNodeNS(Attr)
	 */
	@Override
	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method getElementsByTagNameNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @return NodeList
	 * @throws DOMException
	 * @see org.w3c.dom.Element#getElementsByTagNameNS(String, String)
	 */
	@Override
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method hasAttribute.
	 * @param name String
	 * @return boolean
	 * @see org.w3c.dom.Element#hasAttribute(String)
	 */
	@Override
	public boolean hasAttribute(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method hasAttributeNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @return boolean
	 * @throws DOMException
	 * @see org.w3c.dom.Element#hasAttributeNS(String, String)
	 */
	@Override
	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Method getSchemaTypeInfo.
	 * @return TypeInfo
	 * @see org.w3c.dom.Element#getSchemaTypeInfo()
	 */
	@Override
	public TypeInfo getSchemaTypeInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method setIdAttribute.
	 * @param name String
	 * @param isId boolean
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setIdAttribute(String, boolean)
	 */
	@Override
	public void setIdAttribute(String name, boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method setIdAttributeNS.
	 * @param namespaceURI String
	 * @param localName String
	 * @param isId boolean
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setIdAttributeNS(String, String, boolean)
	 */
	@Override
	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method setIdAttributeNode.
	 * @param idAttr Attr
	 * @param isId boolean
	 * @throws DOMException
	 * @see org.w3c.dom.Element#setIdAttributeNode(Attr, boolean)
	 */
	@Override
	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException {
		// TODO Auto-generated method stub
		
	}
}
