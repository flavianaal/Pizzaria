
package org.pizzaria.utilitario;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import javax.imageio.ImageIO;

public class UtilitarioImage {

    public static String salvarImagem(String diretorio, String imagemBase64) {

        // Verifica se a imagem foi enviada.
        if (imagemBase64 == null || imagemBase64.trim().isEmpty()) {
            throw new IllegalArgumentException("Imagem não enviada");
        }

        // Remove o prefixo data:image/...;base64, se existir.
        if (imagemBase64.contains(",")) {
            imagemBase64 = imagemBase64.substring(
                    imagemBase64.indexOf(",") + 1
            );
        }

        // Decodifica o conteúdo Base64.
        byte[] imageBytes = Base64.getMimeDecoder().decode(imagemBase64);

        // Identifica o formato real da imagem.
        String extensao;

        if (imageBytes.length >= 8
                && (imageBytes[0] & 0xFF) == 0x89
                && imageBytes[1] == 0x50
                && imageBytes[2] == 0x4E
                && imageBytes[3] == 0x47) {

            extensao = ".png";

        } else if (imageBytes.length >= 3
                && (imageBytes[0] & 0xFF) == 0xFF
                && (imageBytes[1] & 0xFF) == 0xD8
                && (imageBytes[2] & 0xFF) == 0xFF) {

            extensao = ".jpg";

        } else {
            throw new IllegalArgumentException(
                    "Formato não suportado. Envie uma imagem PNG ou JPEG."
            );
        }

        // Valida se os bytes correspondem a uma imagem legível.
        try {
            if (ImageIO.read(new ByteArrayInputStream(imageBytes)) == null) {
                throw new IllegalArgumentException("Imagem inválida");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Não foi possível ler a imagem", e);
        }

        // Gera um nome único com a extensão correta.
        String nomeArquivo = UUID.randomUUID() + extensao;

        Path destino = Paths.get(diretorio, nomeArquivo);

        try {
            // Cria o diretório caso ainda não exista.
            Files.createDirectories(destino.getParent());

            // Salva os bytes originais, preservando a transparência do PNG.
            Files.write(destino, imageBytes);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }

        return destino.toString();
    }
}
