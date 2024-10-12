package org.josafamelo.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CEP {
    public String cep;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
}
