import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;

public class ServidorLaberinto {
    private static int nJugadores;
    private static Socket cliente1 = null, cliente2 = null;
    private static BufferedReader leeJ1 = null, leeJ2 = null;
    private static PrintWriter enviaJ1 = null, enviaJ2 = null;
    private static int puerto;

    //Matrices del juego
    private static int[][][] tableros;

    //Casillas iniciales y finales de cada tableros
    private static Point[] initCells;
    private static Point[] endCells;

    //Tableros asignados a los jugadores
    private static int tabJ1;
    private static int tabJ2;

    //Tableros de juego y posiciones de cada jugador
    private static int[][] matrizJ1;
    private static Point posJ1, posJ2;
    private static int[][] matrizJ2;

    //Contador de turnos
    private static int turno;

    public static void main(String argv[])  throws UnknownHostException, IOException{
       try{
           puerto = 8000;
           ServerSocket servidor = new ServerSocket(puerto);

           InitServer();

           //Inicializacion del jugador 1
           //Conexion al jugador
           cliente1 = servidor.accept();
           leeJ1 = new BufferedReader(new InputStreamReader(cliente1.getInputStream()));
           enviaJ1 = new PrintWriter(cliente1.getOutputStream());
           enviaJ1.println("JUGADOR 1");
           enviaJ1.flush();

           //Logica del juego
           tabJ1 = 0;
           posJ1 = new Point(initCells[tabJ1].x, initCells[tabJ1].y);
           setJ1Pos(posJ1);
           nJugadores++;
           System.out.println("Se ha conectado el jugador 1");

/*
           //Inicializacion del jugador 2
           //Conexion al jugador
           cliente2 = servidor.accept();
           leeJ2 = new BufferedReader(new InputStreamReader(cliente2.getInputStream()));
           enviaJ2 = new PrintWriter(cliente2.getOutputStream());
           enviaJ2.println("JUGADOR 2");

           //Logica del juego
           tabJ2 = 1;
           posJ2 = new Point(initCells[tabJ2].x, initCells[tabJ2].y);
           nJugadores++;
           System.out.println("Se ha conectado el jugador 2");*/

           initJuego();
       }
       catch (IOException er){
           System.out.println("Error de conexion " + er);
       }
    }

    private static void initJuego(){
        System.out.println("Comienza el juego\n");
        turno = 0;

        //Enviar mensajes de iniciación
        System.out.println("Turno " + turno + ", le toca al jugador 1");
        enviaJ1.println("Turno " + turno + ", le toca al jugador 1");
        enviaJ1.flush();

        enviarMatrices();
        turnoJ1();
    }

    private static void enviarMatrices(){
        System.out.println();
        enviaJ1.println();
        System.out.println("Tablero J1");
        enviaJ1.println("Tablero J1");

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                int value = matrizJ1[i][j];
                if(value == -1){
                    System.out.print("?");
                    enviaJ1.print("?");
                }
                else if(value == 2){
                        System.out.print("X");
                        enviaJ1.print("X");
                }
                else{
                    System.out.print(value);
                    enviaJ1.print(value);
                }

                System.out.print("\t");
                enviaJ1.print("\t");
            }
            System.out.println();
            enviaJ1.println();
        }
        enviaJ1.flush();


    }

    private static boolean turnoJ1(){
        boolean inputOk = false;
        enviaJ1.println("Tu posicion es: " + posJ1.x + ", " + posJ1.y);
        enviaJ1.println("JUEGA");
        enviaJ1.flush();

        while(!inputOk){
            try {
                StringTokenizer st = new StringTokenizer(leeJ1.readLine());
                if (st.countTokens() != 2) {
                    enviaJ1.println("err");
                    enviaJ1.flush();
                }

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                if(x < 1 || x > 7 || y < 1 || y > 7){
                    enviaJ1.println("err");
                    enviaJ1.flush();
                }
            }
            catch(NumberFormatException err){
                enviaJ1.println("err");
                enviaJ1.flush();
            }
            catch (IOException er){
                System.out.println(er);
                return false;
            }
        }

        return false;
    }

    private static boolean turnoJ2(){
        return false;
    }

    private static void setJ1Pos(Point pos){
        matrizJ1[posJ1.x][posJ1.y] = 1;
        matrizJ1[pos.x][pos.y] = 2;
        posJ1 = pos;
    }

    private static void setJ2Pos(Point pos){
        matrizJ2[posJ2.x][posJ2.y] = 1;
        matrizJ2[pos.x][pos.y] = 2;
        posJ2 = pos;
    }

    private static void generarMatrices(){
        tableros = new int[4][][];
        generarMatriz1();
        generarMatriz2();
    }

    private static void generarMatriz1(){
        tableros[0] = new int[][]{  {0, 0, 0, 0, 0, 0, 0},
                                    {1, 1, 1, 0, 0, 1, 0},
                                    {0, 0, 1, 0, 0, 1, 0},
                                    {0, 1, 1, 0, 1, 1, 0},
                                    {0, 1, 0, 0, 1, 0, 0},
                                    {0, 1, 0, 0, 1, 0, 0},
                                    {0, 1, 1, 1, 1, 0, 0}};

        initCells[0] = new Point(1, 0);
        endCells[0] = new Point(5, 1);
    }

    private static void generarMatriz2(){
        tableros[1] = new int[][]{  {1, 1, 1, 1, 1, 1, 1},
                                    {0, 0, 0, 0, 0, 0, 1},
                                    {0, 0, 0, 0, 0, 0, 1},
                                    {0, 0, 0, 0, 0, 1, 1},
                                    {0, 0, 0, 0, 0, 1, 0},
                                    {0, 0, 0, 0, 0, 1, 0},
                                    {1, 1, 1, 0, 0, 1, 0},
                                    {0, 0, 1, 1, 1, 1, 0}};

        initCells[1] = new Point(0, 5);
        endCells[1] = new Point(0, 0);
    }


    private static void InitServer(){

        matrizJ1 = new int[7][7];
        matrizJ2 = new int[7][7];

        for(int row = 0; row < 7; row++){
            for(int col = 0; col < 7; col++){
            matrizJ1[row][col] = -1;
            matrizJ2[row][col] = -1;
            }
        }

        initCells = new Point[4];
        endCells = new Point[4];
        generarMatrices();

        System.out.println("El servidor se ha iniciado");
        System.out.println("Puerto: " + puerto);
        System.out.println();
    }
}
