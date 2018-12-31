package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
    private static int[][] input = new int[9][9];

    static boolean isFullSolution(int[][] board) {
        //System.out.println("isFullSolution");
        for (int i = 8; i >= 0; i--) {
            for(int j = 8; j>=0; j--){
                if (board[i][j] == 0) {
                    return false;
                }
            }
            
        }
        return true;
    }

    static boolean reject(int[][] board) {
        //System.out.println("reject");
        // TODO: Complete this method
        //for each position, if it is zero it is fine,
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]==0){
                    
                }else if(board[i][j] != input[i][j]&&input[i][j]!=0){
                    return true;
                }else if(badRow(board, i, board[i][j])){
                    return true;
                }else if(badCol(board, j, board[i][j])){
                    return true;
                }else if(badSquare(board, i, j, board[i][j])){
                    return true;
                }
            }
        }
        //System.out.println("Not Rejected");
        return false;
    }

    private static boolean badSquare(int[][] board, int i, int j, int value){
        //System.out.println("sq");
        //count amount of that value in square.
        int count = 0;
        int sqi = (i/3)*3;
        int sqj = (j/3)*3;

        for(int a=sqi;a<(sqi+3);a++){
            for(int b = sqj;b<(sqj+3);b++){
                if(board[a][b]==value){
                    count++;
                }
            }
        }
        if(count>1){
            return true;
        }
        return false;
    }
    private static boolean badRow(int[][] board, int row, int value){
        //System.out.println("row");
        int count = 0;
        for(int j = 0; j<9; j++){
            if(board[row][j]==value){
                count++;
            }
        }
        if(count>1){
            return true;
        }
        return false;
    }
    private static boolean badCol(int[][] board, int col, int value){
        //System.out.println("col");
        int count = 0;
        for(int i = 0; i<9; i++){
            if(board[i][col]==value){
                count++;
            }
        }
        if(count>1){
            return true;
        }
        return false;
    }

    static int[][] extend(int[][] board) {
        //System.out.println("extend");
        // TODO: Complete this method
        //get the next 0.
        //System.out.println("board before extend");
        //printBoard(board);
        int[][] temp = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0;j<9;j++){
                if(board[i][j]==0){
                    temp[i][j]=1;
                    //System.out.println("board after extend");
                    //printBoard(temp);
                    return temp;
                }else{
                    temp[i][j] = board[i][j];
                }
            }
        }


        return null;
    }

    static int[][] next(int[][] board) {
        //System.out.println("next");
        // TODO: Complete this method
        //if we at end or.
        //if next one is 0.
        //this the one we add to.
        //otherwise, we add one.
        //System.out.println("board before next");
        //printBoard(board);
        int[][] temp = new int[9][9];
        boolean addOneNow = false;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(i==8&&j==8){
                    addOneNow = true;
                }else if(j==8&&board[i+1][0]==0){
                    addOneNow = true;
                }else if(j<8&&board[i][j+1]==0){
                    addOneNow = true;
                }

                if(addOneNow){
                    if(board[i][j]>=9){
                        return null;
                    }else{
                        temp[i][j] = board[i][j]+1;
                        //System.out.println("board after next");
                        //printBoard(temp);
                        return temp;
                    }                   
                }else{
                    temp[i][j] = board[i][j];
                }
            }
        }
        return null;
    }

    static void testIsFullSolution() {
        int[][] test = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=0;
            }
        }
        //System.out.println("FS: "+isFullSolution(test));
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=1;
            }
        }
        //System.out.println("FS: "+isFullSolution(test));
    }

    static void testReject() {
        // TODO: Complete this method
        int[][] test = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=0;
            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                input[i][j] = test[i][j];
                test[i][j] = 0;
            }
        }
        input[0][0]=1;
        input[0][1]=2;
        input[0][2]=3;
        System.out.println("RJ: "+ reject(test));
        test[0][0]=1;
        System.out.println("RJ: "+ reject(test));
        test[0][1]=3;
        System.out.println("RJ: "+ reject(test));
        test[0][1]=2;
        System.out.println("RJ: "+ reject(test));
        test[0][3]=5;
        System.out.println("RJ: "+ reject(test));
        test[1][0]=1;
        System.out.println("RJ: "+ reject(test));

    }

    static void testExtend() {
        // TODO: Complete this method
        int[][] test = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=0;
            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                input[i][j] = test[i][j];
                test[i][j] = 0;
            }
        }
        System.out.println("EX: ");
        printBoard(extend(test));
        System.out.println("EX: ");
        printBoard(extend(extend(test)));
        test[0][3]=5;
        System.out.println("EX: ");
        printBoard(extend(test));
        test[0][0]=2;
        test[0][1]=4;
        test[0][2]=1;
        test[0][3]=6;
        System.out.println("EX: ");
        printBoard(extend(test));
        

    }

    static void testNext() {
        // TODO: Complete this method
        int[][] test = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=0;
            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                input[i][j] = test[i][j];
                test[i][j] = 0;
            }
        }
        System.out.println("NT: ");
        printBoard(next(next(test)));
        test[0][1] = 8;
        System.out.println("NT: ");
        printBoard(next(test));
        test[0][1] = 9;
        System.out.println("NT: ");
        printBoard(next(test));
        test[0][1] = 10;
        System.out.println("NT: ");
        printBoard(next(test));

        for(int i=0;i<9;i++){
            test[0][i]=8;
        }
        System.out.println("NT: ");
        printBoard(next(test));

        test[0][8]=9;
        System.out.println("NT: ");
        printBoard(next(test));

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=8;
            }
        }
        System.out.println("NT: ");
        printBoard(next(test));

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=9;
            }
        }
        System.out.println("NT: ");
        printBoard(next(test));
    }

    static void testNoSolution(){
        int[][] test = new int[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                test[i][j]=0;
            }
        }
        System.out.println("A solution:");
        printBoard(solve(test));
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                input[i][j] = test[i][j];
                test[i][j] = 1;
            }
        }
        System.out.println("No solution");
        printBoard(solve(test));
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("Not a valid board with a solution.");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        //System.out.println("solve");
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        int[][] attempt = extend(board);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
            testNoSolution();
        } else {
            int[][] board = readBoard(args[0]);
            printBoard(board);
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    input[i][j] = board[i][j];
                    board[i][j] = 0;
                }
            }           
            System.out.println("Solution:");
            printBoard(solve(board));
        }
    }
}