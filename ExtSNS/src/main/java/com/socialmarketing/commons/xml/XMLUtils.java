package com.socialmarketing.commons.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.socialmarketing.commons.objects.ObjectUtils;
import com.socialmarketing.commons.text.StringUtils;

public class XMLUtils {
	private Document document = null;
	  private StringBuffer XMLString = null;
	  private boolean cacheXML = false;
	  private static final Log LOG = LogFactory.getLog(XMLUtils.class);

	  public XMLUtils(String paramString, boolean paramBoolean)
	    throws Exception
	  {
	    if (paramBoolean)
	      parseXML(paramString);
	    else
	      this.document = createDocument(paramString);
	  }

	  public static Document createDocument()
	    throws Exception
	  {
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	    return localDocumentBuilder.newDocument();
	  }

	  public static Document createDocument(String paramString)
	    throws Exception
	  {
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	    Document localDocument = localDocumentBuilder.newDocument();
	    Element localElement = localDocument.createElement(paramString);
	    localDocument.appendChild(localElement);
	    return localDocument;
	  }

	  public XMLUtils(String paramString)
	    throws IOException
	  {
	    parseXML(paramString);
	  }

	  public XMLUtils(InputStream paramInputStream)
	    throws Exception
	  {
	    parseXML(paramInputStream);
	  }

	  public XMLUtils(File paramFile)
	    throws Exception
	  {
	    parseXML(paramFile);
	  }

	  public XMLUtils(URL paramURL)
	    throws Exception
	  {
	    InputStream localInputStream = paramURL.openStream();
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	    this.document = localDocumentBuilder.parse(localInputStream);
	    localInputStream.close();
	  }

	  public static Element getFirstChild(Document paramDocument, String paramString)
	  {
	    Element localElement = paramDocument.getDocumentElement();
	    return getFirstChild(localElement, paramString);
	  }

	  public static Element getFirstChild(Element paramElement, String paramString)
	  {
	    NodeList localNodeList = paramElement.getChildNodes();
	    for (int i = 0; i < localNodeList.getLength(); i++)
	    {
	      Node localNode = localNodeList.item(i);
	      if ((localNode != null) && (localNode.getNodeType() == 1) && (((Element)localNode).getTagName().equals(paramString)))
	        return (Element)localNode;
	    }
	    return null;
	  }

	  public static Element getFirstChild(Element paramElement, String paramString1, String paramString2, String paramString3)
	  {
	    NodeList localNodeList = paramElement.getChildNodes();
	    for (int i = 0; i < localNodeList.getLength(); i++)
	    {
	      Node localNode = localNodeList.item(i);
	      if ((localNode != null) && (localNode.getNodeType() == 1) && (((Element)localNode).getTagName().equals(paramString1)))
	      {
	        Element localElement = (Element)localNode;
	        if ((localElement.hasAttribute(paramString2)) && (paramString3.equals(localElement.getAttribute(paramString2))))
	          return localElement;
	      }
	    }
	    return null;
	  }

	  public static void getAllChildren(Element paramElement, AbstractList paramAbstractList)
	  {
	    NodeList localNodeList = paramElement.getChildNodes();
	    for (int i = 0; i < localNodeList.getLength(); i++)
	    {
	      Node localNode = localNodeList.item(i);
	      if (localNode.getNodeType() == 1)
	        paramAbstractList.add((Element)localNode);
	    }
	  }

	  public static void getAllChildren(Element paramElement, String paramString, AbstractList paramAbstractList)
	  {
	    try
	    {
	      NodeList localNodeList = paramElement.getChildNodes();
	      for (int i = 0; i < localNodeList.getLength(); i++)
	      {
	        Node localNode = localNodeList.item(i);
	        if ((localNode.getNodeType() == 1) && (((Element)localNode).getTagName().equals(paramString)))
	          paramAbstractList.add((Element)localNode);
	      }
	    }
	    catch (Exception localException)
	    {
	      LOG.error("likely null element", localException);
	    }
	  }

	  public static void getAllChildrenText(Element paramElement, String paramString, AbstractList paramAbstractList)
	  {
	    NodeList localNodeList = paramElement.getChildNodes();
	    for (int i = 0; i < localNodeList.getLength(); i++)
	    {
	      Node localNode = localNodeList.item(i);
	      if ((localNode.getNodeType() == 1) && (((Element)localNode).getTagName().equals(paramString)))
	      {
	        String str = getNodeText(localNode);
	        if (str != null)
	          paramAbstractList.add(str);
	      }
	    }
	  }

	  public static Element getFirstElement(Element paramElement, String paramString)
	  {
	    try
	    {
	      XPath localXPath = XPathFactory.newInstance().newXPath();
	      Node localNode = (Node)localXPath.evaluate(paramString, paramElement, XPathConstants.NODE);
	      return (Element)localNode;
	    }
	    catch (Exception localException)
	    {
	      LOG.error("Unhandled exception.", localException);
	    }
	    return null;
	  }

	  public static ArrayList<Element> getElements(Element paramElement, String paramString)
	  {
	    ArrayList localArrayList = new ArrayList();
	    try
	    {
	      XPath localXPath = XPathFactory.newInstance().newXPath();
	      NodeList localNodeList = (NodeList)localXPath.evaluate(paramString, paramElement, XPathConstants.NODESET);
	      for (int i = 0; i < localNodeList.getLength(); i++)
	      {
	        Node localNode = localNodeList.item(i);
	        if ((localNode.getNodeType() == 1) && (((Element)localNode).getTagName().equals(paramString)))
	          localArrayList.add((Element)localNode);
	      }
	    }
	    catch (Exception localException)
	    {
	      LOG.error("Unhandled exception.", localException);
	    }
	    return localArrayList;
	  }

	  public static String getNodeText(Node paramNode)
	  {
	    if (paramNode == null)
	      return null;
	    String str = paramNode.getTextContent();
	    if ("null".equals(str))
	      return null;
	    return str;
	  }

	  public static String getThisNodeText(Node paramNode)
	  {
	    if (paramNode == null)
	      return null;
	    NodeList localNodeList = paramNode.getChildNodes();
	    for (int i = 0; i < localNodeList.getLength(); i++)
	    {
	      Node localNode = localNodeList.item(i);
	      if ((localNode.getNodeType() == 3) || (localNode.getNodeType() == 4))
	        return localNode.getNodeValue();
	    }
	    return null;
	  }

	  public static HashMap<String, String> populateObject(Object paramObject, Element paramElement)
	  {
	    if ((paramObject != null) && (paramElement != null))
	    {
	      HashMap localHashMap = new HashMap();
	      NodeList localNodeList = paramElement.getChildNodes();
	      for (int i = 0; i < localNodeList.getLength(); i++)
	      {
	        Node localNode = localNodeList.item(i);
	        if (localNode.getNodeType() == 1)
	        {
	          String str1 = localNode.getNodeName();
	          String str2 = getNodeText(localNode);
	          if ("null".equals(str2))
	            str2 = null;
	          if ((str2 != null) && (str2.indexOf("$C{") > -1))
	          {
	            localHashMap.put(str1, str2);
	          }
	          else
	          {
	            String str3;
	            if (ObjectUtils.setParam(paramObject, str1, str2))
	            {
	              if (System.getProperty("DEBUG") != null)
	              {
	                str3 = str1.substring(0, 1).toUpperCase() + str1.substring(1);
	                LOG.debug("set" + str3 + "(" + str2 + ")");
	              }
	              str3 = ((Element)localNode).getAttribute("lookup");
	              if (str3 != null)
	                localHashMap.put(str1 + "^" + str3 + "Guid", str2);
	            }
	            else
	            {
	              if ((System.getProperty("DEBUG") != null) || ((System.getProperty("DEBUG-API") != null) && (str2 != null) && (!str2.startsWith("$C{"))))
	              {
	                str3 = str1.substring(0, 1).toUpperCase() + str1.substring(1);
	                LOG.debug(paramObject.getClass().getName() + ".set" + str3 + "(" + str2 + ") **IGNORED");
	              }
	              localHashMap.put(str1, str2);
	            }
	          }
	        }
	      }
	      return localHashMap;
	    }
	    return null;
	  }

	  public static String toXMLValue(String paramString)
	  {
	    if (paramString != null)
	    {
	      String str = paramString.trim();
	      str = StringUtils.replace(str, "&", "&amp;");
	      str = StringUtils.replace(str, "\"", "&quot;");
	      str = StringUtils.replace(str, "<", "&lt;");
	      str = StringUtils.replace(str, ">", "&gt;");
	      return str;
	    }
	    return "";
	  }

	  public static String toString(String paramString)
	  {
	    if (paramString != null)
	    {
	      String str = paramString.trim();
	      str = StringUtils.replace(str, "&quot;", "\"");
	      str = StringUtils.replace(str, "&lt;", "<");
	      str = StringUtils.replace(str, "&gt;", ">");
	      str = StringUtils.replace(str, "&amp;", "&");
	      return str;
	    }
	    return "";
	  }

	  public static String toString(Node paramNode)
	  {
	    return toString(paramNode, "UTF-8");
	  }

	  public static String toString(Node paramNode, String paramString)
	  {
	    try
	    {
	      TransformerFactory localTransformerFactory = TransformerFactory.newInstance();
	      Transformer localTransformer = localTransformerFactory.newTransformer();
	      localTransformer.setOutputProperty("encoding", paramString);
	      localTransformer.setOutputProperty("method", "xml");
	      DOMSource localDOMSource = new DOMSource(paramNode);
	      StringWriter localStringWriter = new StringWriter();
	      StreamResult localStreamResult = new StreamResult(localStringWriter);
	      localTransformer.transform(localDOMSource, localStreamResult);
	      return localStringWriter.toString();
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace(System.out);
	    }
	    return null;
	  }

	  public static void saveXML(Document paramDocument, File paramFile)
	    throws TransformerException
	  {
	    TransformerFactory localTransformerFactory = TransformerFactory.newInstance();
	    Transformer localTransformer = localTransformerFactory.newTransformer();
	    localTransformer.setOutputProperty("indent", "yes");
	    StreamResult localStreamResult = new StreamResult(paramFile);
	    DOMSource localDOMSource = new DOMSource(paramDocument);
	    localTransformer.transform(localDOMSource, localStreamResult);
	  }

	  public static boolean debug(Node paramNode)
	  {
	    try
	    {
	      TransformerFactory localTransformerFactory = TransformerFactory.newInstance();
	      Transformer localTransformer = localTransformerFactory.newTransformer();
	      DOMSource localDOMSource = new DOMSource(paramNode);
	      StreamResult localStreamResult = new StreamResult(System.out);
	      localTransformer.transform(localDOMSource, localStreamResult);
	      return true;
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace(System.out);
	    }
	    return false;
	  }

	  public void setCacheXML(boolean paramBoolean)
	  {
	    this.cacheXML = paramBoolean;
	  }

	  public Document getDocument()
	  {
	    return this.document;
	  }

	  public Element getDocumentElement()
	  {
	    return this.document.getDocumentElement();
	  }

	  public String getXMLString()
	  {
	    if (this.XMLString == null)
	      return null;
	    return this.XMLString.toString();
	  }

	  public Element getFirstChild(String paramString)
	  {
	    return getFirstChild(this.document, paramString);
	  }

	  public Element getFirstElement(String paramString)
	  {
	    return getFirstElement(getDocumentElement(), paramString);
	  }

	  public static Element getElement(Element paramElement, String paramString1, String paramString2, String paramString3)
	  {
	    ArrayList localArrayList = new ArrayList();
	    getAllChildren(paramElement, paramString1, localArrayList);
	    Iterator localIterator = localArrayList.iterator();
	    while (localIterator.hasNext())
	    {
	      Element localElement = (Element)localIterator.next();
	      if (paramString3.equals(localElement.getAttribute(paramString2)))
	        return localElement;
	    }
	    return null;
	  }

	  public String toString()
	  {
	    return toString(this.document);
	  }

	  private void parseXML(String paramString)
	    throws IOException
	  {
	    StringReader localStringReader = new StringReader(paramString);
	    InputSource localInputSource = new InputSource(localStringReader);
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    try
	    {
	      DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	      this.document = localDocumentBuilder.parse(localInputSource);
	    }
	    catch (Exception localException)
	    {
	      LOG.error("XML parsing error", localException);
	      throw new IOException(localException.getMessage());
	    }
	  }

	  private void parseXML(File paramFile)
	    throws Exception
	  {
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	    this.document = localDocumentBuilder.parse(paramFile);
	  }

	  private void parseXML(InputStream paramInputStream)
	    throws Exception
	  {
	    DocumentBuilderFactory localDocumentBuilderFactory = getDocumentBuilderFactoryInstance();
	    DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
	    this.document = localDocumentBuilder.parse(paramInputStream);
	  }

	  private static DocumentBuilderFactory getDocumentBuilderFactoryInstance()
	  {
	    DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
	    localDocumentBuilderFactory.setNamespaceAware(true);
	    return localDocumentBuilderFactory;
	  }
}
