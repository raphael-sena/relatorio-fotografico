import { Relatorio } from "./Relatorio";

export interface Inspecao {
    id: Number;
    data: Date;
    cliente: String;
    pedidoCompra: String;
    relatorio: Relatorio;
}