package br.com.catalisa.GestaoDeEstoque.log;


import br.com.catalisa.GestaoDeEstoque.enums.TipoLogEvento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_log_eventos")
public class LogEventosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "evento", length = 500)
    private String evento;

    @Column(name = "tipo_evento")
    @Enumerated(EnumType.STRING)
    private TipoLogEvento tipoLogEvento;

    @Column(name = "data_hora_evento")
    private LocalDateTime dataHoraEvento = LocalDateTime.now();
}
