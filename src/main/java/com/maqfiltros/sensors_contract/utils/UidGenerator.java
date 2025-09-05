package com.maqfiltros.sensors_contract.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class UidGenerator {
	 // Instância de SecureRandom para garantir aleatoriedade criptograficamente forte.
    // É recomendado criar apenas uma instância e reutilizá-la.
    private static final SecureRandom secureRandom = new SecureRandom();

    // Codificador Base64 URL-safe e sem padding ('=' no final).
    // URL-safe usa caracteres que são seguros para usar em nomes de arquivos e URLs.
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    /**
     * Gera um Identificador Único (UID) com o comprimento de caracteres especificado.
     *
     * @param length O número de caracteres que o UID deve ter. Deve ser um número
     * positivo.
     * @return Uma string contendo o UID gerado.
     * @throws IllegalArgumentException se o comprimento for menor ou igual a zero.
     */
    public static String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("O comprimento do UID deve ser um número positivo.");
        }

        // Base64 codifica 3 bytes de dados brutos em 4 caracteres.
        // Portanto, calculamos o número de bytes aleatórios necessários para gerar uma string
        // de comprimento 'length' ou um pouco maior.
        // A fórmula (length * 3) / 4 é uma boa aproximação. Adicionamos 1 para garantir.
        int numberOfBytes = (length * 3) / 4;
        if ((length * 3) % 4 != 0) {
            numberOfBytes++;
        }

        // Cria um array de bytes para armazenar os dados aleatórios.
        byte[] randomBytes = new byte[numberOfBytes];
        
        // Preenche o array com bytes aleatórios seguros.
        secureRandom.nextBytes(randomBytes);

        // Codifica os bytes aleatórios para uma string Base64 e a trunca
        // para o tamanho exato solicitado.
        String uid = encoder.encodeToString(randomBytes);
        
        return uid.substring(0, length);
    }
}
