import java.util.Scanner;


public class BattleShipGrupalCorregido {
    static int barcos = 1; // Se mueve la variable 'barcos' a ser un miembro estático de la clase
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED= "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\033[39m";



    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean jugarOtraVez;
        do {
            char[][] tablero = crearTablero();
            introducirBarcos(tablero);
            System.out.println("Bienvenido a hundir la flota: ");
            System.out.println("Este es el tablero de juego: ");

            IMPRIMIRtablero(tablero);
            boolean finPartida;
            do {
                System.out.println("[+] VAMOS A ATACAR");
                disparar(tablero);
                IMPRIMIRtablero(tablero);

                finPartida = finPartida(tablero);
            } while (!finPartida);
            jugarOtraVez = jugarOtraVezFUN(teclado);
        } while (jugarOtraVez); // Corregido la condición del bucle

    }

    public static boolean jugarOtraVezFUN(Scanner teclado) {
        do {
            System.out.print("[?] ¿Quieres volver a jugar? (S/N) -> ");
            char SoN = teclado.next().charAt(0);
            if (SoN == 'S' || SoN == 's') {
                return true;
            } else if (SoN == 'N' || SoN == 'n') {
                return false;
            } else {
                System.out.println("[!] Por favor, introduce un valor válido");
            }
        } while (true); // Se cambio para que el bucle se repita hasta que se reciba una respuesta válida
    }

    public static boolean finPartida(char[][] tablero) {
        for (int filas = 0; filas < 10; filas++) {
            for (int columnas = 0; columnas < 10; columnas++) {
                if (tablero[filas][columnas] == 'B') {
                    return false;
                }
            }
        }
        return true;
    }

    public static char[][] crearTablero() {
        char[][] tablero = new char[10][10];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = ' ';
            }
        }
        return tablero;
    }

    public static void introducirBarcos(char[][] tablero) {
        // La lógica para incrementar 'barcos' y la verificación de la posición se han corregido.
        if (barcos == 1) {
            int fila = (int) (Math.random() * 10);
            int columna = (int) (Math.random() * 10);
            tablero[fila][columna] = 'B';
            barcos++;
        }

        if (barcos == 2) {
            for (int i = 0; i < 2; i++) {
                int fila = (int) (Math.random() * 10);
                int columna = (int) (Math.random() * 8);
                while (tablero[fila][columna] == 'B' || tablero[fila][columna + 1] == 'B') {
                    fila = (int) (Math.random() * 10);
                    columna = (int) (Math.random() * 8);
                }
                tablero[fila][columna] = 'B';
                tablero[fila][columna+1] = 'B';
            }
            barcos++;
        }

    }

    public static void disparar(char[][] tablero) {
        int fila, columna;
        System.out.print("[→] Dame una fila -> ");
        fila = pedirCoords();
        System.out.print("[↓] Dame una columna -> ");
        columna = pedirCoords();
        if (tablero[fila][columna] == ' ') { // dispara al agua
            System.out.println(ANSI_BLUE+"[+] No le hemos dado a nada"+ANSI_WHITE);
            tablero[fila][columna] = 'X';
        } else if (tablero[fila][columna] == 'B') { // dispara a un barco
            System.out.println(ANSI_RED+"[+] Buen tiro capitán"+ANSI_WHITE);
            tablero[fila][columna] ='*';
        } else if (tablero[fila][columna] == '*') { // dispara a un tocado
            System.out.println(ANSI_YELLOW+"¡¡¡Cuidado capitán, estás gastando balas en una parte destruida!!!"+ANSI_WHITE);
        } else if (tablero[fila][columna] == 'X') { // dispara a agua disparada
            System.out.println(ANSI_YELLOW+"¡¡¡Capitán, ya habíamos comprobado ese área!!!"+ANSI_WHITE);
        }
    }



    public static int pedirCoords() {
        Scanner scCoord = new Scanner(System.in);
        int coord;
        do {
            coord = scCoord.nextInt();
            if (coord < 0 || coord > 9) {
                System.out.print("Vuelve a introducir la coordenada:");
            }
        } while (coord < 0 || coord > 9);
        return coord;
    }

    public static void IMPRIMIRtablero(char[][] tablero) {
        System.out.println("-| 0 || 1 || 2 || 3 || 4 || 5 || 6 || 7 || 8 || 9 | ");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print(i);
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == 'B') {
                    System.out.print("| "+" "+" |");
                } else {
                    if (tablero[i][j]=='*'){
                        System.out.print("| "+ANSI_RED+tablero[i][j]+ANSI_WHITE+" |");
                    }else if (tablero[i][j]=='X'){
                        System.out.print("| "+ANSI_BLUE+tablero[i][j]+ANSI_WHITE+" |");
                    }else{
                        System.out.print("| "+tablero[i][j]+" |");
                    }
                }
            }
            System.out.println();
        }
    }
}