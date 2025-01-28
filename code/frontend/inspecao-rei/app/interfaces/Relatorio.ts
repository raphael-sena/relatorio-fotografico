import { Inspecao } from "./Inspecao";
import { Item } from "./Item";

export interface Relatorio {
    id: Number;
    inspecao: Inspecao;
    itens: Item[];
}