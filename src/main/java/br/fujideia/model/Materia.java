package br.fujideia.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "ativa")
    private boolean ativa;
    
    @Column(name = "quantidade_aulas")
    private Integer quantidadeAulas;
    
    @Column(name = "dificuldade")
    @Enumerated(EnumType.STRING)
    private NivelDificuldade nivelDificuldade;
    
    public enum NivelDificuldade {
        FACIL, 
        MEDIO, 
        DIFICIL, 
        AVANCADO
    }
}
