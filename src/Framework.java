public class Framework {

    int[][] field;

    
    public void generateField(int length, int width){
        field = new int[length][width];
        
// test code
//        for(int i=0; i<field.length; i++) {
//            for(int j=0; j<field[i].length; j++) {
//                System.out.println("Values at arr["+i+"]["+j+"] is "+field[i][j]);
//            }
//        }
    }
}

class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.generateField(12,12);
    }
}

