package shotCompiler;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;

import org.w3c.dom.NodeList;

import shotData.ShotData;

import java.util.*;
import java.io.*;

public class ShotCompiler {

	private ArrayList<ShotData> shots;
	
	public ShotCompiler()
	{
		shots = new ArrayList<ShotData>();
	}

	public void main(String filePath) throws Exception 
	{
		String filename = null;

	    if (filename == null) {
	        usage();
	    }
	    
	    readXML(filename);
	    
	}

	
	private static void usage() 
	{
	    System.err.println("Usage: Compiler <file.xml>");
	    System.err.println("       -usage or -help = this message");
	    System.exit(1);
	}
	
	private static String convertToFileURL(String filename) 
	{
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }
	
	private static void readXML(String fileName)
    {
        Document document;
        DocumentBuilder documentBuilder;
        DocumentBuilderFactory documentBuilderFactory;
        NodeList nodeList;
        File xmlInputFile;
        ArrayList<ShotData> shots = new ArrayList<ShotData>();

        try
        {
            xmlInputFile = new File(fileName);
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlInputFile);
            nodeList = document.getElementsByTagName("*");
            ShotData s;

            document.getDocumentElement().normalize();

            for (int index = 0; index < nodeList.getLength(); index++)
            {
                Node node = nodeList.item(index);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;

                    s = getTagValue(element);
                    if (s != null)
                    {
                    	shots.add(s);
                    }
                    //System.out.println("\tcolour : " + getTagValue(element));
                    //System.out.println("\ttest : " + getTagValue(element));
                    //System.out.println("-----");
                }
            }
            
            for (ShotData sd : shots)
            {
            	if (sd.getPlayerName().equals("Kevin  Durant"))
            	{
            		System.out.println(sd.toString());
            	}
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
	
	private static ShotData getTagValue(final Element element)
    {
		String player, description; 
		int quarter,x,y; 
		String madeShot,homeTeam;
		
        if (element.getTagName().equals("Shot"))
        {
            player = element.getAttribute("p");
            description = element.getAttribute("d");
            quarter = Integer.parseInt(element.getAttribute("qtr"));
            x = Integer.parseInt(element.getAttribute("x"));
            y = Integer.parseInt(element.getAttribute("y"));
            madeShot = element.getAttribute("made");
            homeTeam = element.getAttribute("t");

            return new ShotData(player, description, quarter, x, y, madeShot, homeTeam);
            
        }
        else
        {
            return null;
        }
    }
	
	
}
