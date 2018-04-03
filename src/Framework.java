public class Framework {

    int[][] field;

    
    public void generateField(int length, int width){
        field = new int[length][width];

    }
    public void getState(int length, int width) {
    	System.out.println(field[length][width]);
    }
    public void setSate(int length, int width, int value) {
    	field[length][width] = value;
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
        framework.generateField(12,12);
        
        framework.setSate(5, 3, 1);
        framework.showField();
        
    }
}

