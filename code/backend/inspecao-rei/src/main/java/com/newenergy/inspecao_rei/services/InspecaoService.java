package com.newenergy.inspecao_rei.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.newenergy.inspecao_rei.models.Inspecao;
import com.newenergy.inspecao_rei.models.Item;
import com.newenergy.inspecao_rei.models.dtos.InspecaoCreateDTO;
import com.newenergy.inspecao_rei.models.dtos.InspecaoUpdateDTO;
import com.newenergy.inspecao_rei.repositories.InspecaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    private static final String BASE_URL = "http://localhost:8080/inspecao";

    @Transactional
    public List<Inspecao> findAll() {
        return inspecaoRepository.findAll();
    }

    @Transactional
    public Inspecao findById(Long id) {
        Optional<Inspecao> inspecao = this.inspecaoRepository.findById(id);
        return inspecao.orElseThrow(() -> new EntityNotFoundException(
                "Inspeção não encontrada! Id: " + id + ", Tipo: " + Inspecao.class.getName()));
    }

    @Transactional
    public List<Item> findItemsByInspecao(Long inspecaoId) {
        return findById(inspecaoId).getItens();
    }

    @Transactional
    public Inspecao criarInspecao(InspecaoCreateDTO obj) {
        Inspecao inspecao = new Inspecao();
        inspecao.setCliente(obj.getCliente());
        inspecao.setData(obj.getData());
        inspecao.setPedidoCompra(obj.getPedidoCompra());
        inspecao = inspecaoRepository.save(inspecao);

        inspecaoRepository.save(inspecao);
        return inspecao;
    }

    @Transactional
    public Inspecao updateInspecao(InspecaoUpdateDTO obj) {
        findById(obj.getId());

        Inspecao newObj = findById(obj.getId());

        newObj.setCliente(obj.getCliente());
        newObj.setPedidoCompra(obj.getPedidoCompra());

        return this.inspecaoRepository.save(newObj);
    }

    public void deletarInspecao(Long id) {
        findById(id);
        this.inspecaoRepository.deleteById(id);
    }

    @Transactional
    public void addItem(Long inspecaoId, Item item) {
        Inspecao inspecao = findById(inspecaoId);
        inspecao.getItens().add(item);
        inspecaoRepository.save(inspecao);
    }

    @Transactional
    public void removeItem(Long inspecaoId, Long itemId) {
        Inspecao inspecao = findById(inspecaoId);

        Item itemToRemove = inspecao.getItens().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item não encontrado! Id: " + itemId));

        inspecao.getItens().remove(itemToRemove);

        inspecaoRepository.save(inspecao);
    }

    @Transactional
    public List<Inspecao> findByIds(List<Long> ids) {
        return inspecaoRepository.findAllById(ids);
    }

    @Transactional
    public Inspecao save(Inspecao inspecao) {
        return inspecaoRepository.save(inspecao);
    }

    @Transactional
    public List<Inspecao> buscarInspecaoPorPagina(int pagina, int itensPorPagina) {
        try {
            // Construir a URL com parâmetros de paginação
            String url = BASE_URL + "?page=" + pagina + "&size=" + itensPorPagina;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .build();

            // Realiza a requisição GET
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se a resposta foi bem-sucedida
            if (response.statusCode() == 200) {
                return parseInspecaoJson(response.body());
            } else {
                throw new RuntimeException("Falha ao obter as inspeções. Código de status: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private List<Inspecao> parseInspecaoJson(String json) {
        // Utilizando a biblioteca Gson para converter o JSON para a lista de Inspeções
        JsonArray jsonArray = JsonParser.parseString(json).getAsJsonObject().getAsJsonArray("content");
        List<Inspecao> inspecoes = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();

            LocalDate data = LocalDate.parse(obj.get("data").getAsString());

            JsonArray itensArray = obj.getAsJsonArray("itens");
            List<Item> itens = new ArrayList<>();
            for (JsonElement itemElement : itensArray) {
                JsonObject itemObj = itemElement.getAsJsonObject();

                byte[] imagem = null;
                JsonArray imagemArray = itemObj.getAsJsonArray("imagem");
                if (imagemArray != null && !imagemArray.isEmpty()) {
                    String imagemBase64 = imagemArray.get(0).getAsString();
                    imagem = Base64.getDecoder().decode(imagemBase64);
                }

                JsonObject inspecaoObj = itemObj.getAsJsonObject("inspecao");
                Inspecao inspecao = new Inspecao(
                        inspecaoObj.get("id").getAsLong(),
                        LocalDate.parse(inspecaoObj.get("data").getAsString()),
                        inspecaoObj.get("cliente").getAsString(),
                        inspecaoObj.get("pedidoCompra").getAsString(),
                        new ArrayList<>() // Se necessário, adicione os itens para o objeto inspecao
                );

                Item item = new Item(
                        itemObj.get("id").getAsLong(),
                        itemObj.get("codigo").getAsString(),
                        imagem,
                        inspecao
                );
                itens.add(item);
            }

            Inspecao inspecao = new Inspecao(
                    obj.get("id").getAsLong(),
                    data,
                    obj.get("cliente").getAsString(),
                    obj.get("pedidoCompra").getAsString(),
                    itens
            );
            inspecoes.add(inspecao);
        }
        return inspecoes;
    }
}
