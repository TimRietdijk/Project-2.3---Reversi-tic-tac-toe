import java.text.DateFormat.Field;

public class Framework {

    int[][] field;
    int numberofstates = 3;

    
    public void setField(int length, int width){
        field = new int[length][width];

    }
    public int getState(int length, int width) {
    	return field[length][width];
    }
    public void setSate(int length, int width, int value) {
    	if(length >= field.length) {
    		System.out.println("error: the given position does not exist on this board");
    	}else {
    		if(width >= field[1].length) {
    			System.out.println("error: the given position does not exist on this board");
    		}else {
		    	if(value >= numberofstates)
		    	{
		    		System.out.println("Error: given state is not supported");
		    	}else { 
		    		if(value == getState(length, width))
		    		{
		    			System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
		    		}else {
		    			field[length][width] = value;
		    		} 		
		    	}
    		}
    	}
    }
    public void showField() {
	      for(int i=0; i<field.length; i++) {
	      for(int j=0; j<field[i].length; j++) {
	          System.out.println("Values at arr["+i+"]["+j+"] is "+field[i][j]);
	      }
	  }
    }
}

class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.setField(3,3);   
        framework.setSate(1, 3, 1);
        framework.showField();
        
    }
}