'use client'

import { useState } from "react";
import { Item } from "../interfaces/Item";
import { Inspecao } from "../interfaces/Inspecao";
import { Relatorio } from "../interfaces/Relatorio";
import { Clientes } from "../enums/clienteEnum";


export default function RelatorioComponent() {
    const [cliente, setCliente] = useState<string>("");
    const [projeto, setProjeto] = useState<string>("");
    const [itens, setItens] = useState<Item[]>([]);
    const [codigo, setCodigo] = useState<string>("");
    const [imagem, setImagem] = useState<File | null>(null);

    const handleAddItem = () => {
        if (codigo && imagem) {
            setItens([...itens, { id: Date.now(), codigo, imagem, relatorio: {} as Relatorio }]);
            setCodigo("");
            setImagem(null);
        } else {
            alert("Preencha o código e selecione uma imagem antes de adicionar.");
        }
    };

    const handleSave = (e: React.FormEvent) => {
        e.preventDefault();
        const relatorio: Relatorio = { id: Date.now(), inspecao: {} as Inspecao, itens };
        console.log("Relatório salvo:", relatorio);
        alert("Relatório salvo com sucesso!");
        setCliente("");
        setProjeto("");
        setItens([]);
    };

    const handleCancel = () => {
        if (window.confirm("Tem certeza que deseja cancelar? Os dados serão perdidos.")) {
            setCliente("");
            setProjeto("");
            setItens([]);
            setCodigo("");
            setImagem(null);
        }
    };

    return (
        <div className="w-full m-auto flex flex-col text-center justify-center items-center">
            <h1 className="mb-6 text-3xl font-bold text-center">Novo Relatório Fotográfico</h1>
            
            <form className="space-y-6" onSubmit={handleSave}>
                {/* Campos de Cliente e Projeto */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label htmlFor="cliente" className="block text-sm font-medium mb-2">Cliente</label>
                        <select
                            id="cliente"
                            value={cliente}
                            onChange={(e) => setCliente(e.target.value as Clientes)}
                            className="w-full border rounded-lg px-4 py-2"
                        >
                            <option value="" disabled>Selecione um cliente</option>
                            {Object.values(Clientes).map((cli) => (
                                <option key={cli} value={cli}>{cli}</option>
                            ))}
                        </select>
                    </div>
                    <div>
                        <label htmlFor="projeto" className="block text-sm font-medium mb-2">Projeto</label>
                        <input
                            type="text"
                            id="projeto"
                            value={projeto}
                            onChange={(e) => setProjeto(e.target.value)}
                            placeholder="Digite o nome do projeto"
                            className="w-full border rounded-lg px-4 py-2"
                        />
                    </div>
                </div>

                {/* Seção para adicionar itens */}
                <div className="border rounded-lg p-4">
                    <h2 className="text-lg font-semibold mb-4">Itens</h2>

                    <div className="space-y-4">
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 items-center">
                            <div>
                                <label htmlFor="codigo" className="block text-sm font-medium mb-2">Código</label>
                                <input
                                    type="text"
                                    id="codigo"
                                    value={codigo}
                                    onChange={(e) => setCodigo(e.target.value)}
                                    placeholder="Digite o código do item"
                                    className="w-full border rounded-lg px-4 py-2"
                                />
                            </div>
                            <div>
                                <label htmlFor="imagem" className="block text-sm font-medium mb-2">Imagem</label>
                                <input
                                    type="file"
                                    id="imagem"
                                    onChange={(e) => setImagem(e.target.files ? e.target.files[0] : null)}
                                    className="w-full border rounded-lg px-4 py-2"
                                />
                            </div>
                        </div>
                        <button
                            type="button"
                            onClick={handleAddItem}
                            className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition"
                        >
                            Adicionar Item
                        </button>
                    </div>

                    <ul className="mt-4 space-y-2">
                        {itens.map((item, index) => (
                            <li key={index} className="flex justify-between items-center border p-2 rounded-lg">
                                <span>{item.codigo}</span>
                                <span>{item.imagem.name}</span>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Botões de Ação */}
                <div className="flex justify-end gap-4">
                    <button
                        type="button"
                        onClick={handleCancel}
                        className="bg-gray-300 text-gray-700 py-2 px-6 rounded-lg hover:bg-gray-400 transition"
                    >
                        Cancelar
                    </button>
                    <button
                        type="submit"
                        className="bg-green-500 text-white py-2 px-6 rounded-lg hover:bg-green-600 transition"
                    >
                        Salvar Relatório
                    </button>
                </div>
            </form>
        </div>
    );
}
