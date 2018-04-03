public class Framework {

    public int[][] field;

    
    private void generateField(int length, int width){
        field = new int[length][width];
        
        for(int i=0; i<field.length; i++) {
            for(int j=0; j<field[i].length; j++) {
                System.out.println("Values at arr["+i+"]["+j+"] is "+mat[i][j]);
            }
        }
    }
}

public class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.generateField(12,12);
    }
}

