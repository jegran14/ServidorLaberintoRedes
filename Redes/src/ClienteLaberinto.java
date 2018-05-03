import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteLaberinto {
    private static Socket s = null;
    private static String nombre;
    private static int puerto;
    private static BufferedReader lee;
    private static BufferedReader leePantalla;
    private static PrintWriter envia;

    public static void main(String args[]) throws UnknownHostException, IOException{
        nombre = args[0];
        puerto = Integer.parseInt(args[1]);

        try{
            leePantalla = new BufferedReader(new InputStreamReader(System.in));

            s = new Socket(nombre, puerto);
            System.out.println("Conexion realizada a " + nombre);

            lee = new BufferedReader(new InputStreamReader(s.getInputStream()));
            envia = new PrintWriter(s.getOutputStream());

            String msg = lee.readLine();
            System.out.println(msg);

            msg = lee.readLine();
            while(msg != null){
                if(msg.equals("JUEGA")){
                    turnoJugador();
                    return;
                }
                else{
                    System.out.println(msg);
                    msg = lee.readLine();
                }
            }
        }
        catch (UnknownHostException er){
            System.out.println("Nombre del servidor desconocido" + er);

        }
        catch(IOException er){
            System.out.println("No es posible realizar la conexion " + er);
        }
    }

    private static void turnoJugador(){
        System.out.println("Inserta la fila y columna donde quieres moverte separado por escpacios");
        System.out.println();
        try{
            String msg = leePantalla.readLine();
            envia.println(msg);
            envia.flush();

            String responce = lee.readLine();
            while(responce.equals("err")){
                System.out.println("Introduce un valor valido");
                System.out.println("Inserta la fila y columna donde quieres moverte separado por escpacios");
                System.out.println();

                msg = leePantalla.readLine();
                envia.println(msg);
                envia.flush();
                responce = lee.readLine();
            }
        }
        catch(IOException err){
            System.out.println("error " + err);
        }
    }
}
