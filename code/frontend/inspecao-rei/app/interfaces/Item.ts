import { Relatorio } from "./Relatorio";

export interface Item {
    id: Number;
    codigo: String;
    imagem: File;
    relatorio: Relatorio;
}