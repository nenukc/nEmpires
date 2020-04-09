package nEmpires;

public class nEmpires 
{
	public static int n = 10, q = 0;
	public static int[][]a = new int[n][n];

	
	public static boolean checkConstraints(int row, int column)
	{
		
		//Row check
		for(int i=0;i<column;i++)
		{
			if(a[row][i]==1)
			{
				return false;
			}
			
		}
		//Column check
		for(int i=0;i<row;i++)
		{
			if(a[i][column]==1)
			{
				return false;
			}
			
		}		
		
		 
		 //Diagonal check
		// Check diagonal on left side 
		for (int i=row, j=column; i>=0 && j>=0; i--, j--) 
		{
        	if (a[i][j]==1)
        	{ 
            	return false;
            }
		}

         // Check diagonal on right side 
		for (int i=row, j=column; i>=0 && j>=0 && i<=row && j<n ; i--, j++)
		{
		
			if (a[i][j]==1)
			{
				return false; 
			}
		}
		
		//Knight check - 8 possible knight like moves.
		if((row-1)>=0 &&(column-2)>=0)
		{
				if (a[row-1][column-2]==1)
				{
				    return false;	
				}
		}
		
		if((row-1)>=0 &&(column+2)<n)
		{
				if (a[row-1][column+2]==1)
				{
				    return false;	
				}
		}
		
		if((row+1)<n &&(column+2)<n)
		{
				if (a[row+1][column+2]==1)
				{
				    return false;	
				}
		}
		
		if((row+1)<n &&(column-2)>=0)
		{
				if (a[row+1][column-2]==1)
				{
				    return false;	
				}
		}
		
		if((row-2)>=0 &&(column-1)>=0)
		{
				if (a[row-2][column-1]==1)
				{
				    return false;	
				}
		}
		
		if((row-2)>=0 &&(column+1)<n)
		{
				if (a[row-2][column+1]==1)
				{
				    return false;	
				}
		}
		
		if((row+2)<n &&(column+1)<n)
		{
				if (a[row+1][column+1]==1)
				{
				    return false;	
				}
		}
		
		if((row+2)<n &&(column-1)>=0)
		{
				if (a[row+2][column-1]==1)
				{
				    return false;	
				}
		}
		
		
				return true;
		
	}
	
	public static void placeEmperors()
		    { 
		        	for(int i=0;i<n;i++)
		        	{
		        		for(int j=0;j<n;j++)
		        		{
		        	  
		        			if(checkConstraints(i,j))
		        			{
		        				a[i][j]=1;
		        				q++;
		        		
		        			}
		        			else
		        			{
		        				a[i][j]=0;
		        			}
		        			
		        			System.out.print(a[i][j]);
		        		}
		        		System.out.println();
		        	}
		    }
	
    public static void main(String[] args)
    {
        placeEmperors();
        System.out.println("Total of "+q+" Emperors can be placed on a board with "+(n*n)+" Squares");
            
    }
        
}


