import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;



class PharmacyCount {

	public static void main(String[] args)
	{
		PharmacyCount obj = new PharmacyCount();
		String inputFile = args[0];
		String outputFile = args[1];
 		obj.readFile(inputFile);
		obj.sort();
		obj.sortAlphabetically();
		obj.writeData(outputFile);
	}
	
	static String[][] uniqueDrugList = new String[5000][3] ;
	static int count = 0;
	static int listLength = 0;
	
	public void showList()
	{
		for (int i = 0; i < uniqueDrugList.length; i++) {
			if(uniqueDrugList[i][0]==null)
			{
				break;
			}
			else
			{
				System.out.println(uniqueDrugList[i][0]+","+uniqueDrugList[i][1]+","+uniqueDrugList[i][2]);
			}
		}
	}
	
	public void readFile(String path) {
	  
		 
		 String csvFile = path;
	        String Cline = "";
        	FileInputStream inputStream = null;
        
        	String amount = null;
	        try  {
	       
	        	
	      	BufferedReader br = new BufferedReader(new java.io.FileReader(csvFile));
	    
	        	
	        		Cline = br.readLine();
	            while (Cline  != null) {
	            	
	            	
	            	String line = Cline;
	            	
	                amount = line.substring(line.lastIndexOf(',')+1, line.length());
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
	                
	                if(!amount.equals("drug_cost"))
	                {
	       	                
	                pharmacy[1] = amount;
	                pharmacy[0] = drugname;		
         
	                setDrugValue(pharmacy);
	         
	                }
	           
	                Cline = br.readLine();   
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
			
				String sum = Double.toString((Double.parseDouble(drungEntry[1])) + (Double.parseDouble(uniqueDrugList[i][2])));
			
				Double tempo = (double) (Double.parseDouble(sum)*100.0)/100.0;
				
			    sum = tempo.toString();
		
				if((sum.substring(sum.lastIndexOf('.')+1, sum.length())).equals("0"))
					{
					uniqueDrugList[i][2] = sum.substring(0,sum.indexOf('.'));
					}
					else if((sum.length() - sum.lastIndexOf('.'))>4)
					{ 
						uniqueDrugList[i][2] = sum.substring(0,sum.indexOf('.')+3);
					}
					else
					{
						uniqueDrugList[i][2] = tempo.toString();
					}
			uniqueDrugList[i][1] = Integer.toString(Integer.parseInt(uniqueDrugList[i][1])+1);
			 			
				flag = true;
				break;
			}
		}
		if(flag == false)
		{
				uniqueDrugList[count][0] = drungEntry[0];
				uniqueDrugList[count][1] = Integer.toString(1);
				uniqueDrugList[count][2] = drungEntry[1];
				count++;
		}
	}
	
	
	public void sortAlphabetically()
	{
		for(int i=0;i < count ; i++)
		{
			if(uniqueDrugList[i][2].equals(uniqueDrugList[i+1][2]))
			{
				if(((uniqueDrugList[i][0]).compareTo(uniqueDrugList[i+1][0]))>0)
				{
					String[] temp = uniqueDrugList[i];
					uniqueDrugList[i] = uniqueDrugList[i+1];
					uniqueDrugList[i+1] = temp;
					
				}
			}
			
		}
		
	}
	
		
	public void writeData(String outpath)
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			
			//String content = "This is the content to write into file\n";

			fw = new FileWriter(outpath);
			bw = new BufferedWriter(fw);
			
			bw.write("drug_name,num_prescriber,total_cost");
			bw.newLine();
			
			for (int i = 0; i < uniqueDrugList.length; i++) {
				if(uniqueDrugList[i][1]!=null)
				{
					String content = uniqueDrugList[i][0]+","+uniqueDrugList[i][1]+","+uniqueDrugList[i][2];
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
	
    static String[][] arr = uniqueDrugList;
	
	public void sort()
    {
		
    	quickSort( 0, count-1);
 
    }
	 
	 public static void quickSort( int low, int high) {
			
			if (low >= high)
				return;
	 
			// pick the pivot
			int middle = low + (high - low) / 2;
			String[] pivot = arr[middle];
	 
			
			int i = low, j = high;
			while (i <= j) {
				while (Double.parseDouble(arr[i][2]) > Double.parseDouble(pivot[2])) 
				{
					i++;
				}
	 
				while (Double.parseDouble(arr[j][2]) < Double.parseDouble(pivot[2])) {
					j--;
				}
	 
				if (j >= i) {
					String[] temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
					i++;
					j--;
				}
			}
	 
			
			if (low < j)
				quickSort(low, j);
	 
			if (high > i)
				quickSort(i, high);
		}
	
		
}
