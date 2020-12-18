package costomTest.parser;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * @author chinop
 * @version 1.0
 * @date 12/11/2020
 */
public class XPathTest {
  @Test
  public void parseTest() throws Exception {
    InputStream resourceAsStream = XPathTest.class.getClassLoader().getResourceAsStream("inventory.xml");
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    // 开启验证
    documentBuilderFactory.setValidating(true);
    documentBuilderFactory.setNamespaceAware(true);
    documentBuilderFactory.setIgnoringComments(true);
    documentBuilderFactory.setIgnoringElementContentWhitespace(false);
    documentBuilderFactory.setCoalescing(false);
    documentBuilderFactory.setExpandEntityReferences(true);
    // 关闭校验就不会去检车DTD和XML配置是否合法
    // documentBuilderFactory.setValidating(false);
    // 创建DocumentBuilder
    DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
    // 设置异常处理对象
    // 没有设置DTD文件会出现 Document is invalid: no grammar found.
    // Document root element "xxx", must match DOCTYPE root "null".
    builder.setErrorHandler(new ErrorHandler() {
      @Override
      public void warning(SAXParseException exception) throws SAXException {
        System.out.println("WARN: " + exception.getMessage());
      }

      @Override
      public void error(SAXParseException exception) throws SAXException {
        System.out.println("error: " + exception.getMessage());
      }

      @Override
      public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("fatalError: " + exception.getMessage());
      }
    });
    // 将文档加载到一个Document对象中
    Document doc = builder.parse(resourceAsStream);
    // 创建XPathFactory
    XPathFactory factory = XPathFactory.newInstance();
    // 创建XPath对象
    XPath xPath = factory.newXPath();
    // 编译XPath表达式
    XPathExpression expr = xPath.compile("//book[author='Neal Stephenson']/title/text()");
    // 通过XPath表达式得到结果，第一个参数指定了XPath表达式进行查询的上下文节点，也就是在指定
    // 节点下查找符合XPath的节点。本例中的上下文节点是整个文档；第二个参数指定了XPath表达式的返回类型
    Object result = expr.evaluate(doc, XPathConstants.NODESET);
    System.out.println("查询作者为Neal Stephenson 的图书的标题：");
    NodeList nodes = (NodeList) result;
    for (int i = 0; i < nodes.getLength(); i++) {
      System.out.println(nodes.item(i).getNodeValue());
    }
    System.out.println("查询1997年之后的图书的标题");
    nodes = (NodeList) xPath.evaluate("//book[@year>1997]/title/text()", doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      System.out.println(nodes.item(i).getNodeValue());
    }
    System.out.println("查询1997年之后的图书的属性和标题：");
    nodes = (NodeList) xPath.evaluate("//book[@year>1997]/@*|//book[@year>1997]/title/text()", doc, XPathConstants.NODESET);
    for (int i = 0; i < nodes.getLength(); i++) {
      System.out.println(nodes.item(i).getNodeValue());
    }
  }
}
