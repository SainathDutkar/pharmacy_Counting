import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

	
	String[][] uniqueDrugList = new String[500000][3] ;
	static int count = 0;
	
		
	public void readFile() {
	  
		 String csvFile = "input/testInput.txt";
	        String line = "";
	       
	    
	  
	        try  {
	         	
	        	BufferedReader br = new BufferedReader(new java.io.FileReader(csvFile));

	            while ((line = br.readLine()) != null) {

	            
	            	String amount = line.substring(line.lastIndexOf(',')+1, line.length());
	            	String drugname=null;
	            	String subLine = null ;

	            	if(line.charAt(line.lastIndexOf(',')-1)=='"')
	            		{
	            			subLine = line.substring(0, line.lastIndexOf('"'));
	            			
	            			drugname = subLine.substring(subLine.lastIndexOf('"')+1, subLine.length());	
	            		}
	            	else
	            	{
	            		subLine = line.substring(0, line.lastIndexOf(','));
	            		drugname = subLine.substring(subLine.lastIndexOf(',')+1, subLine.length());	
	            	}
	            			
	            	            	
	                String[] pharmacy = new String[2];
	                pharmacy[1] = amount;
	                pharmacy[0] = drugname;		
	              
	                setDrugValue(pharmacy);
	           
	           
	         
	              
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void setDrugValue(String[] drungEntry)
	{
	 boolean flag = false;
		for (int i = 0; i < uniqueDrugList.length; i++) 
		{
			if(uniqueDrugList[i][0]==null)
			{
				break;
			}
			else if(drungEntry[0].equals(uniqueDrugList[i][0]))
			{
				uniqueDrugList[i][2] = Float.toString(Float.parseFloat(drungEntry[1]) + Float.parseFloat(uniqueDrugList[i][2]));
				uniqueDrugList[i][1] = Integer.toString(Integer.parseInt(uniqueDrugList[i][1])+1);
			//	System.out.println("  "+uniqueDrugList[i][0]+"  "+uniqueDrugList[i][1]+"  "+uniqueDrugList[i][2]);
			
				sortList(i);
				flag = true;
				break;
			}
		}
		if(flag == false)
		{
			for(int i =0 ; i<drungEntry.length;i++)
			{
				uniqueDrugList[count][0] = drungEntry[0];
				uniqueDrugList[count][1] = Integer.toString(1);
				uniqueDrugList[count][2] = drungEntry[1];
			//	System.out.println("  "+uniqueDrugList[count][0]+"  "+uniqueDrugList[count][1]+"  "+uniqueDrugList[count][2]);
		
				sortList(count);
				break;
			}
		count++;
		}
	}
	
	public void sortList(int drugPosition)
	{
		boolean flag = true;
		while(flag && drugPosition>1 )
		{
			
				try {
					if((Float.parseFloat(uniqueDrugList[drugPosition][2]) > Float.parseFloat(uniqueDrugList[drugPosition-1][2])) )
					{
						String[] temp = uniqueDrugList[drugPosition];
						uniqueDrugList[drugPosition] = uniqueDrugList[drugPosition-1];
						uniqueDrugList[drugPosition-1] = temp;
						drugPosition -- ;
					}
					else if((Float.parseFloat(uniqueDrugList[drugPosition][2]) == Float.parseFloat(uniqueDrugList[drugPosition-1][2])) )
					{
						if(((uniqueDrugList[drugPosition][0]).compareTo(uniqueDrugList[drugPosition-1][0]))>0)
						{
							String[] temp = uniqueDrugList[drugPosition];
							uniqueDrugList[drugPosition] = uniqueDrugList[drugPosition-1];
							uniqueDrugList[drugPosition-1] = temp;
							drugPosition -- ;
						}
					}
					else
					{
						flag = false;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
	}
	
	public void writeData()
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			

			//String content = "This is the content to write into file\n";

			fw = new FileWriter("output/output.txt");
			bw = new BufferedWriter(fw);
			
			bw.write("drug_name,num_prescriber,total_cost");
			bw.newLine();
			
			for (int i = 1; i < uniqueDrugList.length; i++) {
				if(uniqueDrugList[i][1]!=null)
				{
					String content = uniqueDrugList[i][0]+" ,  "+uniqueDrugList[i][2]+" , "+uniqueDrugList[i][2];
				bw.write(content);
				bw.newLine();
			
				}
			}

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}
	
}
