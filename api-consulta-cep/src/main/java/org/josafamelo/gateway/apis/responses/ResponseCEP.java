package org.josafamelo.gateway.apis.responses;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCEP {
    public String cep;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
}
