public class Framework {

    int[][] field;

    public void generateField(int length, int width){
        field = new int[length][width];

        for(int i = 0; i < length; i++){
            for(int j =0; j < width; i++){
                System.out.println(i + " : " + j);
          }
        }
    }
}

class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.generateField(12,12);
    }
}
