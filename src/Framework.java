public class Framework {

    int[][] field;

<<<<<<< HEAD

    
    private void generateField(int length, int width){
        field = new int[length][width];
        
        for(int i=0; i<mat.length; i++) {
            for(int j=0; j<mat[i].length; j++) {
                System.out.println("Values at arr["+i+"]["+j+"] is "+mat[i][j]);
            }
=======
    public void generateField(int length, int width){
        field = new int[length][width];

        for(int i = 0; i < length; i++){
            for(int j =0; j < width; i++){
                System.out.println(i + " : " + j);
          }
>>>>>>> a46b65aa688cc1265802cd7cdb8ffd2abc876763
        }
    }
}

<<<<<<< HEAD

=======
class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.generateField(12,12);
    }
}
>>>>>>> a46b65aa688cc1265802cd7cdb8ffd2abc876763
