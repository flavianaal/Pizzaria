package org.pizzaria.utilitario;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class UtilitarioImage {

    public static String salvarImagem(String diretorio, String imagemBase64) {

        // Remove o prefixo "data:image/...;base64," caso venha incluído
        if (imagemBase64.contains(",")) {
            imagemBase64 = imagemBase64.substring(imagemBase64.indexOf(",") + 1);
        }

        // getMimeDecoder ignora quebras de linha, diferente do getDecoder()
        byte[] imageBytes = Base64.getMimeDecoder().decode(imagemBase64);

        String nomeArquivo = UUID.randomUUID() + ".jpg";
        Path destino = Paths.get(diretorio, nomeArquivo);

        try {
            Files.write(destino, imageBytes); // salva a imagem
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return destino.toString();
    }
}