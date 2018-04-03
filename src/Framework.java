public class Framework {

    int[][] field;


    
    private void generateField(int length, int width){
        field = new int[length][width];
        
        for(int i=0; i<mat.length; i++) {
            for(int j=0; j<mat[i].length; j++) {
                System.out.println("Values at arr["+i+"]["+j+"] is "+mat[i][j]);
            }
        }
    }
}


