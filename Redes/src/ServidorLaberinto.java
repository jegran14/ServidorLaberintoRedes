import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorLaberinto {
  private int nJugadores;
  private Socket cliente1 = null, cliente2 = null;
  int puerto;

  //Matrices del juego
  private int[][][] tableros;
  //Casillas iniciales de cada tableros

  //Tableros asignados a los jugadores
  private int tabJ1;
  private int tabJ2;
  //Tableros de juego de cada jugador
  private int[][] matrizJ1;
  private int[][] matrizJ2;

  public void main(String argv[])  throws UnknownHostException, IOException{
    puerto = 8000;
    ServerSocket servidor = new ServerSocket(puerto);

    InitServer();

    cliente1 = servidor.accept();
    nJugadores++;
    System.out.println("Se ha conectado el jugador 1");

    cliente2 = servidor.accept();
    nJugadores++;
    System.out.println("Se ha conectado el jugador 2");
  }

  private void generarMatrices(){
    tableros = new int[4][][];
  }

  private void generarMatriz1(){
    tableros[0] = new int[][]{{0, 0, 0, 0, 0, 0, 0},
                              {1, 1, 1, 0, 0, 1, 0},
                              {0, 0, 1, 0, 0, 1, 0},
                              {0, 1, 1, 0, 1, 1, 0},
                              {0, 1, 0, 0, 1, 0, 0},
                              {0, 1, 0, 0, 1, 0, 0},
                              {0, 1, 1, 1, 1, 0, 0}};
  }

  private void generarMatriz2(){
    tableros[0] = new int[][]{{1, 1, 1, 1, 1, 1, 1},
                              {0, 0, 0, 0, 0, 0, 1},
                              {0, 0, 0, 0, 0, 0, 1},
                              {0, 0, 0, 0, 0, 1, 1},
                              {0, 0, 0, 0, 0, 1, 0},
                              {0, 0, 0, 0, 0, 1, 0},
                              {1, 1, 1, 0, 0, 1, 0},
                              {0, 0, 1, 1, 1, 1, 0}};
  }


  private void InitServer(){
    matrizJ1 = new int[7][7];
    Arrays.fill(matrizJ1, -1);

    System.out.println("El servidor se ha iniciado");
    System.out.println("Puerto: " + puerto);
    System.out.println();
  }
}
