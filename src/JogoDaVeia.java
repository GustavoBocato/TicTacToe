import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class JogoDaVeia {

    private int[][] gameState = new int[3][3];
    private HashMap<String, Integer> valoresDeUtilidade = new HashMap<>(7304);

    public JogoDaVeia(){}

    public void ver(){

        System.out.println("   0  1  2");

        for (int j = 0; j < 3; j++) {

            System.out.print(j + " ");

            for (int k = 0; k < 3; k++) {

                if(this.gameState[j][k] >= 0){

                    System.out.print(" " + this.gameState[j][k] + " ");

                }else{

                    System.out.print(this.gameState[j][k] + " ");

                }

            }

            System.out.println();

        }

    }

    public boolean haUmVencedor(int [][] gameState){

        boolean haUmVencedor = false;

        for (int j = 0; j < 3; j++) {

            int SL = gameState[j][0] + gameState[j][1] + gameState[j][2];
            int SC = gameState[0][j] + gameState[1][j] + gameState[2][j];

            if(SL == 3 || SL == -3 || SC == 3 || SC == -3){

                haUmVencedor = true;
                break;

            }

        }

        int SDP = gameState[0][0] + gameState[1][1] + gameState[2][2];
        int SDS = gameState[2][0] + gameState[1][1] + gameState[0][2];

        if(SDP == 3 || SDP == -3 || SDS == 3 || SDS == -3){

            haUmVencedor = true;

        }

        return haUmVencedor;

    }

    public void jogarDeDois(){

        int i = 0;

        ver();

        while(i < 9){

            Scanner sc = new Scanner(System.in);

            int x = sc.nextInt();
            int y = sc.nextInt();

            if(this.gameState[x][y] != 0){

                System.out.println("Jogada inválida.");
                continue;

            }

            if(i%2 == 0){

                this.gameState[x][y] = 1;

            }else{

                this.gameState[x][y] = -1;

            }

            ver();

            if(haUmVencedor(this.gameState)){

                break;

            }

            i++;

        }

        if(haUmVencedor(this.gameState)){

            if(i%2 == 0){

                System.out.println("O jogador 1 venceu.");

            }else{

                System.out.println("O jogador 2 venceu.");

            }


        }else{

            System.out.println("Deu veia.");

        }

    }

    public void jogarVsComputador(){

        System.out.println("Escolha jogador 1 ou -1.");

        Scanner sc = new Scanner(System.in);

        int jogador = sc.nextInt();

        int i = 0;

        ver();

        while(i < 9){

            if((i%2 == 0 && jogador == 1) || (i%2 != 0 && jogador == -1)){

                int x = sc.nextInt();
                int y = sc.nextInt();

                if(this.gameState[x][y] != 0){

                    System.out.println("Jogada inválida.");
                    continue;

                }

                this.gameState[x][y] = jogador;

            }else{

                int auxMin = 2;
                int auxMax = -2;
                int x = 0;
                int y = 0;

                for (int j = 0; j < 3; j++) {

                    for (int k = 0; k < 3; k++) {

                        if(this.gameState[j][k] == 0){

                            this.gameState[j][k] = -jogador;

                            int miniMax = miniMax(this.gameState, jogador);

                            if(jogador == 1) {

                                if (miniMax < auxMin) {

                                    auxMin = miniMax;
                                    x = j;
                                    y = k;

                                }

                            }else{

                                if(miniMax > auxMax){

                                    auxMax = miniMax;
                                    x = j;
                                    y = k;

                                }

                            }

                            this.gameState[j][k] = 0;

                        }

                    }

                }

                this.gameState[x][y] = -jogador;

            }

            System.out.println();
            ver();

            if(haUmVencedor(this.gameState)){

                break;

            }

            i++;

        }

        if(haUmVencedor(this.gameState)){

            if(i%2 == 0){

                System.out.println("O jogador 1 venceu.");

            }else{

                System.out.println("O jogador 2 venceu.");

            }


        }else{

            System.out.println("Deu veia.");

        }

    }

    public int miniMax(int[][] gameState, int jogador){

        Integer valorDeUtilidade = valoresDeUtilidade.get(gameStateToString(gameState));

        if(valorDeUtilidade != null){

            return valorDeUtilidade.intValue();

        }

        if(haUmVencedor(gameState)){

            valoresDeUtilidade.put(gameStateToString(gameState), -jogador);
            return -jogador;

        }

        boolean deuVeia = true;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if(gameState[i][j] == 0){

                    deuVeia = false;
                    break;

                }

            }

            if(!deuVeia){

                break;

            }

        }

        if(deuVeia){

            valoresDeUtilidade.put(gameStateToString(gameState), 0);
            return 0;

        }

        ArrayList<Integer> array = new ArrayList<Integer>();

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if(gameState[i][j] == 0){

                    gameState[i][j] = jogador;

                    array.add(miniMax(gameState, -jogador));

                    gameState[i][j] = 0;

                }

            }

        }

        if(jogador == 1){

            int max = Collections.max(array);
            valoresDeUtilidade.put(gameStateToString(gameState), max);
            return max;

        }

        int min = Collections.min(array);
        valoresDeUtilidade.put(gameStateToString(gameState), min);
        return min;

    }

    private String gameStateToString(int[][] gameState) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < gameState.length; i++) {

            for (int j = 0; j < gameState[i].length; j++) {

                sb.append(gameState[i][j]);

            }

        }

        return sb.toString();

    }

}
