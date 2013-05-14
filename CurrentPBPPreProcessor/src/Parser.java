import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

	private Teams teams;
	
	public Parser()
	{
		teams = new Teams();
	}
	
	public void parse(String fileName)
	{
		int currentIndex = 1;
		int endIndex;
		ArrayList<String> outputData = new ArrayList<String>();
		String tempString;
		String data = this.readFileAsString(fileName);
		this.SetTeamNames(data);
		this.SetThreeLetterNames(fileName);
		
		currentIndex = data.indexOf("nbaGIPBPTeams");
		
		while (currentIndex != -1)
		{
			currentIndex = data.indexOf("<tr", currentIndex);
			endIndex = data.indexOf("</tr>", currentIndex);
			
			if (currentIndex == -1)
			{
				break;
			}
			
			tempString = data.substring(currentIndex, endIndex);
			
			if (IsQuarterRow(tempString))
			{
				outputData.add(ParseQuarter(tempString));
			}
			else if (IsJumpBallRow(tempString))
			{
				outputData.add(ParseJumpBall(tempString));
			}
			else if (IsNormalRow(tempString))
			{
				String temp = ParseNormal(tempString).trim();
				if (!temp.endsWith("]"))
				{
					outputData.add(temp);
				}
			}
			
			currentIndex = endIndex;
		}
		
		WriteFile(outputData, System.getProperty("user.dir") + "/" + ParseFileName(fileName) + ".txt");
		
	}
	
	private void SetTeamNames(String data)
	{
		int start = data.indexOf("nbaGIPBPTeams");
		start = data.indexOf("nbaGIPbPTmTle", start);
		start = data.indexOf(">", start) + 1;
		int end = data.indexOf("(", start);
		
		this.teams.SetAwayTeam(data.substring(start, end).trim());
		
		start = data.indexOf("nbaGIPbPTmTle", end);
		start = data.indexOf(">", start) + 1;
		end = data.indexOf("(", start);
		
		this.teams.SetHomeTeam(data.substring(start, end).trim());
		
	}
	
	private void SetThreeLetterNames(String fileName)
	{
		int awayStart = fileName.length() - 11;
		
		teams.SetAwayThreeLetter(fileName.substring(awayStart,awayStart + 3));
		teams.SetHomeThreeLetter(fileName.substring(awayStart + 3,awayStart + 6));
	}
	
	private Boolean IsQuarterRow(String data)
	{
		return data.indexOf("nbaGIPbPTblHdr") != -1;
	}
	
	private Boolean IsNormalRow(String data)
	{
		return (data.indexOf("nbaGIPbPLft") != -1 | data.indexOf("nbaGIPbPLftScore") != -1);
	}
	
	private Boolean IsJumpBallRow(String data)
	{
		return data.indexOf("nbaGIPbPJBall") != -1;
	}
	
	private String ParseQuarter(String data)
	{
		String temp;
		if (data.indexOf("name") != -1)
		{
			if (data.contains("<span"))
			{
				temp = data.substring(data.indexOf("Start of"), data.indexOf("<span"));
				temp = temp.replace("</a>", "");
				return temp;
			}
			else
			{
				temp = data.substring(data.indexOf("Start of"), data.indexOf("</td>"));
				temp = temp.replace("</a>", "");
				return temp;
			}
		}
		else
		{
			return data.substring(data.indexOf("End of"), data.indexOf("</td>"));
		}
	}
	
	private String ParseNormal(String data)
	{
		String temp = "";
		String result1;
		String result2;
		Boolean scoringPossesion = (data.indexOf("<td class=\"nbaGIPbPMidScore\">") != -1);
		Boolean awayScored = (data.indexOf("<td class=\"nbaGIPbPLftScore\">") != -1);
		final int nonScoreLength = "<td class=\"nbaGIPbPMid\">".length();
		final int ScoreLength = "<td class=\"nbaGIPbPMidScore\">".length();
		
		if (scoringPossesion)
		{
			if (awayScored)
			{
				temp = "[" + data.substring(data.indexOf("<td class=\"nbaGIPbPMidScore\">") + 
						ScoreLength, data.indexOf("<", data.indexOf("td class=\"nbaGIPbPMidScore\">")));
				temp = temp + teams.AwayTeam() + "]";
				temp = temp + data.substring(data.indexOf("<td class=\"nbaGIPbPLftScore\">") + 
						ScoreLength, data.indexOf("&nbsp;", data.indexOf("<td class=\"nbaGIPbPLftScore\">")));
			}
			else
			{
				temp = "[" + data.substring(data.indexOf("<td class=\"nbaGIPbPMidScore\">") + 
						ScoreLength, data.indexOf("<", data.indexOf("td class=\"nbaGIPbPMidScore\">")));
				temp = temp + teams.HomeTeam() + "]";
				temp = temp + data.substring(data.indexOf("<td class=\"nbaGIPbPRgtScore\">") + 
						ScoreLength, data.indexOf("&nbsp;", data.indexOf("<td class=\"nbaGIPbPRgtScore\">")));
			}
		}
		else
		{
			if (data.indexOf("<td class=\"nbaGIPbPLft\">&nbsp;</td>") != -1)
			{
				temp = "[" + data.substring(data.indexOf("<td class=\"nbaGIPbPMid\">") + 
						nonScoreLength, data.indexOf("<", data.indexOf("td class=\"nbaGIPbPMid\">")));
				temp = temp + teams.HomeTeam() + "]";
				temp = temp + data.substring(data.indexOf("<td class=\"nbaGIPbPRgt\">") + 
						nonScoreLength, data.indexOf("&nbsp;", data.indexOf("<td class=\"nbaGIPbPRgt\">")));
			}
			else
			{
				temp = "[" + data.substring(data.indexOf("<td class=\"nbaGIPbPMid\">") + 
						nonScoreLength, data.indexOf("<", data.indexOf("td class=\"nbaGIPbPMid\">")));
				temp = temp + teams.AwayTeam() + "]";
				temp = temp + data.substring(data.indexOf("<td class=\"nbaGIPbPLft\">") + 
						nonScoreLength, data.indexOf("&nbsp;", data.indexOf("<td class=\"nbaGIPbPLft\">")));
			}
		}
		
		result1 = DelayTechnicalParse(temp);
		result2 = RemoveDashes(result1);
		
		return result2.trim();
	}
	
	private String ParseJumpBall(String data)
	{
		String temp = data.substring(data.indexOf("<div class=\"gameEvent\">") + "<div class=\"gameEvent\">".length(), data.indexOf("</div>",
				data.indexOf("<div class=\"gameEvent\">")));
		String leftParen = Pattern.quote("(");
		String rightParen = Pattern.quote(")");
		Pattern pattern;
		Matcher matcher;
		
		if (data.contains("["))
		{
			String tempSplit;
			Boolean isAwayStatement;
			int startIndex, endIndex;
//			isAwayStatement = data.contains("[" + teams.AwayThreeLetter() + "]");
//			
//			if (isAwayStatement)
//				teamPattern = Pattern.quote("[" + teams.AwayThreeLetter() + "]");
//			else
//				teamPattern = Pattern.quote("[" + teams.HomeThreeLetter() + "]");
//			
//			pattern = Pattern.compile(teamPattern);
//			matcher = pattern.matcher(temp);
//			temp = matcher.replaceFirst(" ");
//			
//			pattern = Pattern.compile(leftParen);
//			matcher = pattern.matcher(temp);
//			temp = matcher.replaceFirst("[");
//			
//			pattern = Pattern.compile(rightParen);
//			matcher = pattern.matcher(temp);
//			if (isAwayStatement)
//				temp = matcher.replaceFirst(" " + teams.AwayTeam() + "]");
//			else
//				temp = matcher.replaceFirst(" " + teams.HomeTeam() + "]");
			
			isAwayStatement = temp.contains("[" + teams.AwayThreeLetter());
			startIndex = temp.indexOf("[");
			endIndex = temp.indexOf("]");
			tempSplit = temp.substring(endIndex + 1);
			temp = temp.substring(0, startIndex) + tempSplit;
			
			pattern = Pattern.compile(leftParen);
			matcher = pattern.matcher(temp);
			temp = matcher.replaceFirst("[");
			
			pattern = Pattern.compile(rightParen);
			matcher = pattern.matcher(temp);
			if (isAwayStatement)
				temp = matcher.replaceFirst(" " + teams.AwayTeam() + "]");
			else
				temp = matcher.replaceFirst(" " + teams.HomeTeam() + "]");
				
			return temp;
		}
		else
		{
			pattern = Pattern.compile(leftParen);
			matcher = pattern.matcher(temp);
			temp = matcher.replaceFirst("[");
			pattern = Pattern.compile(rightParen);
			matcher = pattern.matcher(temp);
			temp = matcher.replaceFirst("]");
			return temp;
		}
	}
	
	private String ParseFileName(String fileName)
	{
		int startIndex;
		int endIndex;
		
		startIndex = fileName.lastIndexOf("/") + 1;
		endIndex = fileName.indexOf(".", startIndex);
		
		return fileName.substring(startIndex, endIndex);
	}
	
	private String RemoveDashes(String data)
	{ 
		
		String dash = Pattern.quote(" - ");
		Pattern pattern = Pattern.compile(dash);
		Matcher matcher = pattern.matcher(data);
		
		String tempString = matcher.replaceAll(" ");
		
		
		return tempString;
		
	}
	
	private String DelayTechnicalParse(String data)
	{
		String delayTech = Pattern.quote("Delay Technical");
		Pattern pattern = Pattern.compile(delayTech);
		Matcher matcher = pattern.matcher(data);
		
		if (matcher.find())
		{
			return data.substring(0, data.indexOf('-')).trim();
		}
		else
		{
			return data;
		}
	}
	
	private String readFileAsString( String filePath )
    {
        try {
            StringBuffer fileData = new StringBuffer(50000);
            BufferedReader reader = new BufferedReader(
                                        new FileReader(filePath));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead=reader.read(buf)) != -1)
            {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch (IOException e) {
            System.out.println( "An I/O error occurred." );
        }
        return "";
    }
	
	private void WriteFile(ArrayList<String> machineCode, String FileName)
    {
        try 
        {
            FileWriter outFile = new FileWriter(FileName.replaceAll(".fl", ".tm"));
            PrintWriter out = new PrintWriter(outFile);
            
            for (String s : machineCode)
            {
                out.println(s);
            }
           
            out.close();
        }
        catch (IOException e)
        {
            
        }
     }
	
	
	
}
