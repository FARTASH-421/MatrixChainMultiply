import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class data{
    String name;
    int row;
    int col;
}

// C:\Users\Fartash\Desktop\file.txt

public class MatrixChainMultiply {
    public static void main(String[] args) {
        try {

            Scanner input = new Scanner(System.in);
            String path = input.nextLine();

            while (path.equals("--help") || path.equals("--h")){
                System.out.println("java MatrixChainMultiply <input file>");
                path = input.nextLine();
            }

            File myObj = new File(path);
            ArrayList<data> list = new ArrayList<>();
            ArrayList<Integer> dime = new ArrayList<>();
            Scanner file = new Scanner(myObj);
            int temp = -1;
            boolean check = false;

            while (file.hasNextLine()){
                String rr = file.nextLine();
                String[] arr = rr.split(" ");
                data d = new data();

                d.name = arr[0];
                d.row = Integer.valueOf(arr[1]);
                d.col = Integer.valueOf(arr[2]);

                if(temp != d.row && temp != -1)
                    check = true;

                temp = d.col;
                list.add(d);
                dime.add(d.row);

            }
            dime.add(temp);

            if(check)
                System.out.println("Not Multi");
            else {
                findPrantis(list, dime);

            }
        } catch (IOException e) {
            System.out.println("Failed to open input file.");
        }
    }

    private static void findPrantis(ArrayList<data> namelist, ArrayList<Integer> dime) {

        int n = dime.size() - 1;
        int[][] cost = new int[n+1][n+1];
        int [][] Best = new int[n+1][n+1];

        for (int i = 0; i < n; i++) {
            cost[i][i] = 0;
            Best[i][i] = 0;
            Best[i][i+1] = i;
        }

        Best[n][n] = 0;
        cost[n][n] = 0;


        for (int l = 2; l <= n; l++) {
            for (int i = 1; i < n - l+2 ; i++) {
                int j = i + l-1 ;
                cost[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int temp = cost[i][k] + cost[k + 1][j] + dime.get(i-1) * dime.get(k) * dime.get(j);
                    if (temp <= cost[i][j]) {
                        cost[i][j] = temp;
                        Best[i][j] = k;
                    }
                }
            }
        }

        System.out.println(cost[1][n]);
        show(1, n, Best, namelist);

    }

    static void show(int i, int j, int [][] arr, ArrayList<data> lis){
        if (i == j){
            System.out.print(lis.get(j-1).name+" ");
            return;
        }

        System.out.print("(");
        show(i, arr[i][j], arr, lis);
        show(arr[i][j]+1, j, arr, lis);
        System.out.print(")");
    }
}
