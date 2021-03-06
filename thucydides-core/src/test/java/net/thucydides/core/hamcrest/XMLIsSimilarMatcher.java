package net.thucydides.core.hamcrest;

import java.io.IOException;

import org.custommonkey.xmlunit.Diff;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.xml.sax.SAXException;

public class XMLIsSimilarMatcher extends TypeSafeMatcher<String> {
    
    private String xmlDocument;
    
    private String errorMessage = "";
    
    public XMLIsSimilarMatcher(String xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    public boolean matchesSafely(String expectedXML) {
        
        boolean xmlIsSimilar = true;
        try {
            Diff difference = new Diff(xmlDocument,expectedXML);
            xmlIsSimilar = difference.similar();
            if (!xmlIsSimilar) {
                recordErrorMessage(difference);
            }
        } catch (SAXException e) {
            xmlIsSimilar = false; 
            e.printStackTrace();
        } catch (IOException e) {
            xmlIsSimilar = false; 
            e.printStackTrace();
        }
        return xmlIsSimilar;
    }

    private void recordErrorMessage(Diff difference) {
        StringBuffer buffer = new StringBuffer();
        buffer = difference.appendMessage(buffer);
        errorMessage = buffer.toString();
    }

    public void describeTo(Description description) {
        description.appendText("an XML document equivalent to ").appendText(xmlDocument);
        if (errorMessage != null) {
            description.appendText("[").appendText(errorMessage).appendText("]");
        }
    }
}
