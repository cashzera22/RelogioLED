
import java.util.Scanner;

public class RelogioLED {

    // Representacao dos segmentos de cada digito
    static int[] segmentos = {
            0b1111110, // 0
            0b0110000, // 1
            0b1101101, // 2
            0b1111001, // 3
            0b0110011, // 4
            0b1011011, // 5
            0b1011111, // 6
            0b1110000, // 7
            0b1111111, // 8
            0b1111011  // 9
    };

    // Conta quantos segmentos mudaram
    static int contarDiferencas(String original, String candidato) {

        int diferencas = 0;

        for (int i = 0; i < 4; i++) {

            int digito1 = original.charAt(i) - '0';
            int digito2 = candidato.charAt(i) - '0';

            int xor = segmentos[digito1] ^ segmentos[digito2];

            diferencas += Integer.bitCount(xor);
        }

        return diferencas;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Ler os dados
        String horario = sc.nextLine().trim();

        int X = sc.nextInt();
        int Y = sc.nextInt();

        // Separar hora e minuto iniciais
        int horaInicial = Integer.parseInt(horario.substring(0, 2));

        int minutoInicial = Integer.parseInt(horario.substring(3, 5));

        String original = horario.replace(":", "");

        // Guardar o menor custo encontrado
        int menorCusto = Integer.MAX_VALUE;
        String melhorHorario = null;

        // Testar todas as horas
        for (int hora = 1; hora <= 12; hora++) {

            // Testar todos os minutos
            for (int minuto = 0; minuto < 60; minuto++) {

                String candidato = String.format("%02d%02d", hora, minuto);

                // Aceitar somente mudancas de um segmento
                if (contarDiferencas(original, candidato) != 1) {
                    continue;
                }

                // Calcular a distancia entre as horas
                int h1 = horaInicial % 12;
                int h2 = hora % 12;

                int distanciaHora = Math.abs(h1 - h2);

                distanciaHora = Math.min(distanciaHora, 12 - distanciaHora);

                // Calcular a distancia entre os minutos
                int distanciaMinuto = Math.abs(minutoInicial - minuto);

                distanciaMinuto = Math.min(distanciaMinuto, 60 - distanciaMinuto);

                // Calcular o custo total
                int custo = distanciaHora * 60 * X + distanciaMinuto * Y;

                // Atualizar a melhor resposta
                if (custo < menorCusto) {

                    menorCusto = custo;
                    melhorHorario = String.format("%02d:%02d", hora, minuto);
                }
            }
        }

        // Mostrar o resultado
        if (melhorHorario == null) {
            System.out.println("Nenhum horário válido mais próximo possível");
        } else {
            System.out.println(melhorHorario);
        }

        sc.close();
    }
}
