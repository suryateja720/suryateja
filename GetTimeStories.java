import java.net.URL;
import java.util.*;

public class GetTimeStories {
    
    public static void main(String[] args) throws Exception {
        
        int i = 0;
        
        Boolean endFlag = false;
        Boolean appendFlag = false;
        Boolean requiredDataFlag = false;
        
        String currentLine;
        String sourceCode;
        String[] latestStories;
        String baseUrl = "https://time.com";
        
        URL url = new URL(baseUrl);
        Scanner scanner = new Scanner(url.openStream());
        StringBuffer stringBuffer = new StringBuffer();
        
        ArrayList<HashMap> topFiveStories = new ArrayList<HashMap>();
        
//  Parsing the HTML code from the url.
//  Breaking down the code till the <section> .... </section> of Latest Stories.
        
        while(scanner.hasNext()) 
            {
                if(endFlag) break;
                
                if( (currentLine = scanner.next()).contains("</html>") )
                    {
                        endFlag = true;
                    }
                    
                if(appendFlag)
                    {
                        if(currentLine.contains("href")) requiredDataFlag = true;
                        
                        if(requiredDataFlag)
                            {
                                stringBuffer.append(currentLine);
                                stringBuffer.append("\n");
                            }
                    }
                    
                if(currentLine.contains("</a")) requiredDataFlag = false;
                if( currentLine.contains("Stories")) appendFlag = true;
                if( currentLine.contains("</section>")) appendFlag = false;
                
            }
            
        sourceCode = stringBuffer.toString();
        latestStories = sourceCode.split("</a></h2>");
        
//  Spliting the code until the string is left with "title" and "link".
        
        while(latestStories[i]!="" && i<latestStories.length-1)
        {
            
            String[] storyData = latestStories[i].split("/>");
            HashMap<String,String> storyInstance = new HashMap<>();
            
            storyInstance.put("title",storyData[1].replace("\n"," "));
            storyInstance.put("link",storyData[0].replace("href=",baseUrl));
            
            topFiveStories.add(storyInstance);
            
            i++;
            
        }
        
        System.out.println(topFiveStories.toString());
        
    }
    
}