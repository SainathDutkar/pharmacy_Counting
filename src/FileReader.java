import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;



class FileReader {

	
	
	String[][] uniqueDrugList = new String[5000][3] ;
	static int count = 0;
	static int listLength = 0;
	
		
	public void readFile() {
	  
		 String csvFile = "../input/testInput.txt";
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
	            System.out.println(amount);
	        //	e.printStackTrace();
	            
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
				String sum = Double.toString(Double.parseDouble(drungEntry[1]) + Double.parseDouble(uniqueDrugList[i][2]));
				
			  Double tempo = Math.round(Double.parseDouble(sum)*100.0)/100.0;
				uniqueDrugList[i][2] = tempo.toString();
				
				uniqueDrugList[i][1] = Integer.toString(Integer.parseInt(uniqueDrugList[i][1])+1);
		
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
		  
				break;
			}
		count++;
		}
	}
		
	public void writeData()
	{
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			
			//String content = "This is the content to write into file\n";

			fw = new FileWriter("../output/top_cost_drug.txt");
			bw = new BufferedWriter(fw);
			
			bw.write("drug_name,num_prescriber,total_cost");
			bw.newLine();
			
			for (int i = 1; i < uniqueDrugList.length; i++) {
				if(uniqueDrugList[i][1]!=null)
				{
					String content = uniqueDrugList[i][0]+" ,  "+uniqueDrugList[i][1]+" , "+uniqueDrugList[i][2];
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
	
    String[][] array = uniqueDrugList;
	
    public void sort()
    {
    	quickSort(array, 1, count-1);
    }
	 
	 public static void quickSort(String[][] arr, int low, int high) {
			if (arr == null || arr.length == 0)
				return;
	 
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
				quickSort(arr, low, j);
	 
			if (high > i)
				quickSort(arr, i, high);
		}
	
	
	public static void main(String[] args)
	{
		FileReader fr = new FileReader();
		
		fr.readFile();
	//	System.out.println("List created");
		fr.sort();
	//	System.out.println("sorted");
		fr.writeData();
	}
	
	
	
}
