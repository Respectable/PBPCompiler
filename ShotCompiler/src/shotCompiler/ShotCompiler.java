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

	private ArrayList<ArrayList<ShotData>> gameShots;
	
	public ShotCompiler()
	{
		gameShots = new ArrayList<ArrayList<ShotData>>();
	}

	public void main(String filePath) throws Exception 
	{
	    if (filePath == null) {
	        usage();
	    }
	    
	    readXML(filePath);
	    
	}

	
	private static void usage() 
	{
	    System.err.println("Usage: Compiler <file.xml>");
	    System.err.println("       -usage or -help = this message");
	    System.exit(1);
	}
	
	private void readXML(String fileName)
    {
        Document document;
        DocumentBuilder documentBuilder;
        DocumentBuilderFactory documentBuilderFactory;
        NodeList nodeList;
        File folder;
        File[] xmlInputFiles;
        ArrayList<ShotData> shots;
        String currentDoc = "";

        try
        {
        	folder = new File(fileName);
        	xmlInputFiles = folder.listFiles();
        	
        	
        	for(File xmlInputFile : xmlInputFiles)
        	{
        		currentDoc = xmlInputFile.getAbsolutePath();
	            documentBuilderFactory = DocumentBuilderFactory.newInstance();
	            documentBuilder = documentBuilderFactory.newDocumentBuilder();
	            document = documentBuilder.parse(xmlInputFile);
	            nodeList = document.getElementsByTagName("*");
	            ShotData s;
	            shots = new ArrayList<ShotData>();
	
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
	            gameShots.add(shots);
        	}
        }
        catch (Exception exception)
        {
        	System.out.println("Error reading shot data on file " +
        						currentDoc);
            exception.printStackTrace();
            System.exit(-1);
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
	
	public ArrayList<ArrayList<ShotData>> getShots()
	{
		return gameShots;
	}
	
	
}
