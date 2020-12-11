package costomTest.parser;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * @author chinop
 * @version 1.0
 * @date 12/11/2020
 */
public class XPathTest {
  public void parseTest() throws Exception {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    // 开启验证
    documentBuilderFactory.setValidating(true);
    documentBuilderFactory.setNamespaceAware(true);
    documentBuilderFactory.setIgnoringComments(true);
    documentBuilderFactory.setIgnoringElementContentWhitespace(false);
    documentBuilderFactory.setCoalescing(false);
    documentBuilderFactory.setExpandEntityReferences(true);
    // 创建DocumentBuilder
    DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
    // 设置异常处理对象
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
    Document doc = builder.parse("inventory.xml");
    // 创建XPathFactory
    XPathFactory factory = XPathFactory.newInstance();
    // 创建XPath对象
    XPath xPath = factory.newXPath();
    // 编译XPath表达式
    XPathExpression expr = xPath.compile("//book[author='Neal Stephenson']/title/text()");

  }
}
