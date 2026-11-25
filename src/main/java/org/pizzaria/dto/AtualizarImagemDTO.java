
package org.pizzaria.dto;

// DTO utilizado exclusivamente para receber a nova imagem da pizza.
public class AtualizarImagemDTO {

    // Recebe a imagem codificada em Base64.
    private String imagemBase64;

    public AtualizarImagemDTO() {
    }

    // Retorna a imagem recebida.
    public String getImagemBase64() {
        return imagemBase64;
    }

    // Define a imagem recebida.
    public void setImagemBase64(String imagemBase64) {
        this.imagemBase64 = imagemBase64;
    }
}
