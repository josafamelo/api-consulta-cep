package org.josafamelo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCep {
    public String cep;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
}
