package com.Notifica.controller.tickts;

public record TicktsRequest(
        String resumoProblema,
        String local,
        String outroLocal,
        String tipoProblema,
        String outrovtipoProblema,
        String subtipoProblema,
        String outroSubtipoProblema,
        String caminhoFoto,
        String raAluno,
        String funcionarioResponsavel
) {
}
