package br.com.sicredi.canaisdigitais.avaliacaotecnicacanais.api.usuario;

public record Endereco(
        String logradouro,
        int numero,
        String cidade,
        String bairro,
        String estado
) {

    public Endereco {
        stringNotNullOrIsBlank(logradouro, "logradouro");
        numeroPositivo(numero);
        stringNotNullOrIsBlank(cidade, "cidade");
        stringNotNullOrIsBlank(bairro, "bairro");
        stringNotNullOrIsBlank(estado, "estado");
    }

    private void numeroPositivo(int numero) {
        if(numero < 0) {
            throw new IllegalArgumentException("O numero deve ser maior que zero");
        }
    }

    private void stringNotNullOrIsBlank(String field, String nameField) {
        if(field == null || field.isBlank()) {
            throw new IllegalArgumentException("O parâmetro " + nameField + " não pode ser nulo, " +
                    "vazio ou estar em branco");
        }
    }

}
